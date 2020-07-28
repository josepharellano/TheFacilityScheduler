package utilities;

import models.Appointment;

public class Exceptions {


    public static class EmptyInputValue extends Exception{
        public EmptyInputValue(String msg){
            super(msg);
        }
    }

    public static class AppointmentConstraint extends Exception{
        public AppointmentConstraint(){
            super();
        }
    }
    public static class InvalidPasswordEx extends Exception{
        public InvalidPasswordEx(){
            super("Password does not match!");
        }
    }

    public static class InvalidUserNameEx extends Exception{
        public InvalidUserNameEx(){
            super("Invalid User Name");
        }
    }

    public static class InvalidEndDateTime extends Exception{
        public InvalidEndDateTime() {super("End time must be at least 30 minutes after start time");}
    }

    public static class AppointmentsOverlap extends Exception{
        private final Appointment appointment;
        public AppointmentsOverlap(Appointment appointment){
            super("Appointments overlaps with previously scheduled appointment.");
            this.appointment = appointment;
        }

        public Appointment getAppointment() {
            return appointment;
        }
    }
}
