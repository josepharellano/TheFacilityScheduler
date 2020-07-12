package services;

public class AppointmentServiceFactory implements IServiceFactory<AppointmentService> {

    private static AppointmentService instance;

    @Override
    public AppointmentService getInstance() {
        if(instance == null){
            instance = new AppointmentService();
        }
        return instance;
    }
}
