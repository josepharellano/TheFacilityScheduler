package services;

public class ServiceFactory {

    public static Service<?> getService(IServiceFactory<?> factory){
        return factory.getInstance();
    }
}
