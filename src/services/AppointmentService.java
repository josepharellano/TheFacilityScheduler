package services;


import dao.AppointmentDaoImpl;
import models.Appointment;

public class AppointmentService extends Service<Appointment> {

    protected AppointmentService(){
        super(new AppointmentDaoImpl());
    }

}

