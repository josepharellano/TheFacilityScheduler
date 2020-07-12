package services;

import models.User;

public class ConsultantServiceFactory implements IServiceFactory<ConsultantService> {

    private static ConsultantService instance;
    @Override
    public ConsultantService getInstance() {
        if(instance == null){
            instance = new ConsultantService();
        }
        return instance;
    }
}
