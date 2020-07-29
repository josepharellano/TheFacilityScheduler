package services;


import dao.AppointmentDaoImpl;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import models.Appointment;
import models.User;
import utilities.Exceptions;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AppointmentService extends Service<Appointment> {

    protected AppointmentService() {

        super(new AppointmentDaoImpl());
        try {
            refreshData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addAppointment(Appointment appointment) throws Exceptions.EmptyInputValue, SQLException, Exceptions.AppointmentsOverlap, Exceptions.InvalidEndDateTime {
        validateAppointmentInput(appointment);
        System.out.println(UserService.getSessionUser().getName());
        //Add appointment to database.
        Integer id = dao.insert(appointment,UserService.getSessionUser().getName());

        //Add appointment to cache
        appointment.setId(id);
        this.data.put(id,appointment);
    }

    public void updateAppointment(Appointment appointment) throws Exceptions.EmptyInputValue, SQLException, Exceptions.AppointmentsOverlap, Exceptions.InvalidEndDateTime {
        User user = UserService.getSessionUser();

        //Set User Id
        appointment.setUser(user.getId());
        validateAppointmentInput(appointment);

        //Update Appointment in database
        this.dao.update(appointment,user.getName());
        //Update cache
        this.data.put(appointment.getId(),appointment);
    }

    public void removeAppointment(Appointment appointment) throws SQLException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you wish to remove appointment " + appointment.getId() +"?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            this.dao.delete(appointment.getId());
            this.data.remove(appointment.getId());
        }
    }

    public List<Appointment> getAppointmentsByConsultant(int consultantId){
        return this.data.values().stream().filter(item->{
            return item.getUser()== consultantId;
        }).collect(Collectors.toList());
    }

    /**
     * Validates appointment to ensure it can be placed into database.
     * @param appointment
     * @throws SQLException
     * @throws Exceptions.AppointmentsOverlap Occurs when the new appointment overlaps with an existing appointment.
     * @throws Exceptions.EmptyInputValue Occurs if a appointment property is empty but required.
     */
    private void validateAppointmentInput(Appointment appointment) throws SQLException, Exceptions.AppointmentsOverlap, Exceptions.EmptyInputValue, Exceptions.InvalidEndDateTime {
        //Ensure Appointment is during regular business hours
        System.out.println(appointment.getEnd().getHour());
        if(appointment.getStart().getHour() < 8 || appointment.getEnd().getHour() > 20) throw new Exceptions.EmptyInputValue("Appointment must be during operation hours.\n 8:00 AM - 5:00PM");

        //Get current user
        User user = UserService.getSessionUser();

        //Query the db to get most up to date information
        this.refreshData();
        //Query db to get Appointments of current User
        List<Appointment> userAppointments = ((AppointmentDaoImpl)dao).selectByConsultant(user.getId());

        //Customer been selected?
        if(appointment.getCustomer() == null) throw new Exceptions.EmptyInputValue("Must select a customer");
        //Type been filled out?
        if(appointment.getType().isEmpty()) throw new Exceptions.EmptyInputValue("Must fill out type of appointment");

        //Following Inputs are not required.  N/A placed in empty spaces.
        if(appointment.getDesc().isEmpty()) appointment.setDesc("N/A");
        if(appointment.getLocation().isEmpty()) appointment.setLocation("N/A");
        if(appointment.getUrl().isEmpty()) appointment.setUrl("N/A");
        if(appointment.getContact().isEmpty()) appointment.setContact("N/A");
        if(appointment.getTitle().isEmpty()) appointment.setTitle("N/A");

        //Check to ensure ending date and time is after start date and time by at least 30 mins.
        if(!appointment.getStart().plusMinutes(29).isBefore(appointment.getEnd())) throw new Exceptions.InvalidEndDateTime();

        //If New Appointment overlaps another appointment throw exception.
        Optional<Appointment> overlapUserAppointments = checkOverlappingAppointments(userAppointments,appointment);
        if(overlapUserAppointments.isPresent()) throw new Exceptions.AppointmentsOverlap(overlapUserAppointments.get());


        //Filter Appointments by customerId by use of a lambda that compares the new appointment's customer ID with current appointment's customer ID
        List<Appointment> customerAppointments = this.data.values().stream().filter(item-> item.getCustomer().equals(appointment.getCustomer())).collect(Collectors.toList());

        //Check if new appointment overlaps with customers current appointments.
        Optional<Appointment> overlapCustAppointments = checkOverlappingAppointments(customerAppointments,appointment);
        if(overlapCustAppointments.isPresent()) throw new Exceptions.AppointmentsOverlap(overlapCustAppointments.get());

    }

    /**
     * Checks to determine if an appointment start and end times overlap a list of current appointments.  A lambda expression is used to filter out
     * any non overlapping appointments.
     * @param appointments List of appointments
     * @param appointment Appointment to check against list of appointments
     * @return An optional is returned possibly containing an overlapping appointment from the list.
     */
    private Optional<Appointment> checkOverlappingAppointments(List<Appointment> appointments, Appointment appointment){
        return appointments.stream().filter(item->{
            LocalDateTime itemStart = item.getStart().toLocalDateTime();
            LocalDateTime itemEnd = item.getEnd().toLocalDateTime();
            LocalDateTime appStart = appointment.getStart().toLocalDateTime();
            LocalDateTime appEnd = appointment.getEnd().toLocalDateTime();

            //Do not include appointment if its being edited.
            if(item.getId().equals(appointment.getId())) return false;

            return checkOverlappingDateTime(appStart,itemStart,itemEnd )|| checkOverlappingDateTime(appEnd,itemStart,itemEnd);
        }).findFirst();
    }

    /**
     * Checks to determine if a supplied LocalDateTime overlaps a period of time between start and end
     * @param dt Point of time
     * @param start of period to check
     * @param end end of period to check
     * @return True if the the time falls within the start and end time and false if not.
     */
    private boolean checkOverlappingDateTime(LocalDateTime dt, LocalDateTime start, LocalDateTime end){
        if(dt.isAfter(start) && dt.isBefore(end)){
            return true;
        }else return dt.equals(start) || dt.equals(end);
    }



}

