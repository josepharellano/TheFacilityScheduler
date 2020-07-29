package controllers;

import customcontrols.AutoCompleteTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Appointment;
import models.Customer;
import services.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.*;

import utilities.Constants;
import utilities.Exceptions;


public class EditAppointmentController implements Initializable {

    public AutoCompleteTextField<Customer> customers; //List of available customers to setup appointments for
    public TextField titleField;
    public TextField typeField;
    public DatePicker startDate;
    public DatePicker endDate;

    public Spinner<Integer> endHourTimeSpinner;
    public Spinner<Integer> endMinuteTimeSpinner;
    public Spinner<String> endAmPmTimeSpinner;
    public Spinner<String> startAmPmTimeSpinner;
    public Spinner<Integer> startMinuteTimeSpinner;
    public Spinner<Integer> startHourTimeSpinner;
    public TextField descriptionField;
    public Button saveBtn;
    public TextField locationField;
    public TextField contactField;
    public TextField urlField;

    private Appointment appointment;
    private AppointmentService appointmentService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CustomerService customerService = (CustomerService) ServiceFactory.getService(new CustomerServiceFactory());
        appointmentService = (AppointmentService) ServiceFactory.getService(new AppointmentServiceFactory());

        setUpTimeSpinners();

        customers.setAutoFillItems(new ArrayList<Customer>(customerService.getData().values()));
    }

    /**
     * Populate Edit Fields from selected Appointment
     * @param appointment to be edited
     */
    public void setAppointmentEditValues(Appointment appointment){
        this.appointment = appointment;
        titleField.setText(appointment.getTitle());
        typeField.setText(appointment.getType());
        descriptionField.setText(appointment.getDesc());
        locationField.setText(appointment.getLocation());
        contactField.setText(appointment.getContact());
        urlField.setText(appointment.getUrl());

        CustomerService customerService = (CustomerService) ServiceFactory.getService(new CustomerServiceFactory());
        Customer customer = customerService.getItem(appointment.getCustomer());

        LocalDateTime start = appointment.getStart().toLocalDateTime();
        LocalDateTime end = appointment.getEnd().toLocalDateTime();

        customers.setSelection(customer);
        startDate.setValue(start.toLocalDate());
        endDate.setValue(end.toLocalDate());

        //Set the spinners values for the times.
        startHourTimeSpinner.increment(calculateHourFromMilitary(start.getHour()));
        endHourTimeSpinner.increment(calculateHourFromMilitary(end.getHour()));
        startMinuteTimeSpinner.increment(start.getMinute()/ Constants.APPOINT_MINUTE_INCREMENTS);
        endMinuteTimeSpinner.increment(end.getMinute()/Constants.APPOINT_MINUTE_INCREMENTS);

        if(start.getHour() >= 12){
            startAmPmTimeSpinner.increment();
        }else{
            startAmPmTimeSpinner.decrement();
        }
        if(end.getHour() >= 12){
            endAmPmTimeSpinner.increment();
        }else{
            endAmPmTimeSpinner.decrement();
        }

    }

    public void onSaveAppointment(ActionEvent actionEvent) {

        try {
            if(startDate.getValue() == null || endDate.getValue() == null) throw new Exceptions.EmptyInputValue("Must choose a starting and ending date");


            int hour = convertToMilitary(startHourTimeSpinner.getValue(), startAmPmTimeSpinner.getValue());
            LocalDateTime ldt = LocalDateTime.of(startDate.getValue(), LocalTime.of(convertToMilitary(hour,startAmPmTimeSpinner.getValue()), startMinuteTimeSpinner.getValue(), 0, 0));
            ZonedDateTime startZDT = ZonedDateTime.of(ldt, ZoneId.systemDefault());

            hour = convertToMilitary(endHourTimeSpinner.getValue(), endAmPmTimeSpinner.getValue());
            ldt = LocalDateTime.of(endDate.getValue(), LocalTime.of(convertToMilitary(hour,endAmPmTimeSpinner.getValue()), endMinuteTimeSpinner.getValue(), 0, 0));
            ZonedDateTime endZDT = ZonedDateTime.of(ldt, ZoneId.systemDefault());

            //Check to see if this is a new appointment or existing appointment

            try {
                Customer customer = customers.getSelectedModel();
                if (appointment == null) {
                    if(customer == null) throw new Exceptions.EmptyInputValue("Must choose a customer for this appointment");
                    Appointment newAppointment = new Appointment(UserService.getSessionUser().getId(),customer.getId(),titleField.getText(),descriptionField.getText(),contactField.getText(),
                                                typeField.getText(),locationField.getText(),startZDT,endZDT,urlField.getText());
                    appointmentService.addAppointment(newAppointment);
                } else {
                    Appointment editAppointment = new Appointment(this.appointment.getId(), this.appointment.getUser(), customer.getId(), titleField.getText(),
                            descriptionField.getText(), contactField.getText(), typeField.getText(), locationField.getText(), startZDT, endZDT, urlField.getText());
                    appointmentService.updateAppointment(editAppointment);
                }
                onClose(actionEvent);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (Exceptions.AppointmentsOverlap appointmentsOverlap) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Unable to save appointment due to existing appointmentID " + appointmentsOverlap.getAppointment().getId() + " overlapping start and end times.");
                alert.showAndWait();
            } catch (Exceptions.EmptyInputValue | Exceptions.InvalidEndDateTime emptyInputValue) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(emptyInputValue.getMessage());
                alert.showAndWait();
            }
        }catch (Exceptions.EmptyInputValue emptyInputValue) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(emptyInputValue.getMessage());
            alert.showAndWait();
        }



    }

    public void onClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private void setUpTimeSpinners(){
        startHourTimeSpinner.setValueFactory(getHoursSpinnerFactory());
        endHourTimeSpinner.setValueFactory(getHoursSpinnerFactory());
        startMinuteTimeSpinner.setValueFactory(getMinuteSpinnerFactory());
        endMinuteTimeSpinner.setValueFactory(getMinuteSpinnerFactory());
        startAmPmTimeSpinner.setValueFactory(getAMPMSpinnerFactory());
        endAmPmTimeSpinner.setValueFactory(getAMPMSpinnerFactory());

    }

    /**
     * Takes an hour in military time and converts it into AM PM 12 hour time
     * @param hour
     * @return
     */
    private int calculateHourFromMilitary(int hour){
        if(hour > 12) return hour-12;
        if(hour == 0) return 12;
        return hour;
    }

    private int convertToMilitary(int hour,String ampm){
        //Add 12 hours if PM
        if(ampm.equals("PM") && hour < 12){
            return hour + 12;
        }
        if(ampm.equals("AM") && hour == 12) return 0;

        return hour;
    }

    private SpinnerValueFactory<Integer> getHoursSpinnerFactory(){
        return new SpinnerValueFactory.IntegerSpinnerValueFactory(0,12,0,1);
    }

    private SpinnerValueFactory<Integer> getMinuteSpinnerFactory(){
        return new SpinnerValueFactory.IntegerSpinnerValueFactory(0,55,0,5);
    }

    private SpinnerValueFactory<String> getAMPMSpinnerFactory(){
        ObservableList<String> ampm = FXCollections.observableArrayList("AM","PM");
        return new SpinnerValueFactory.ListSpinnerValueFactory<>(ampm);
    }
}
