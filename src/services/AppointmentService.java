package services;


import dao.AppointmentDaoImpl;
import models.Appointment;
import models.User;

import java.sql.SQLException;
import java.util.List;

public class AppointmentService extends Service<Appointment> {

    protected AppointmentService() {

        super(new AppointmentDaoImpl());
        try {
            refreshData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateAppointment(Appointment appointment){

    }

    private void validateAppointmentInput(Appointment appointment){

        //Ensure Appointment is during regular business hours
        //Check If Consultant already has an appointment scheduled for this time frame.
        User consultant = UserService.getSessionUser();
//        AppointmentDaoImpl
//        List<Appointment> consultantAppointments = (AppointmentDaoImpl) dao.se)
        //Check If Customer already has an appointment scheduled for this time frame.
    }

}

