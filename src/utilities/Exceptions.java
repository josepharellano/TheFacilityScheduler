package utilities;

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
}
