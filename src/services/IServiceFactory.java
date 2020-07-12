package services;

public interface IServiceFactory<T extends Service<?>>{

    public T getInstance();
}
