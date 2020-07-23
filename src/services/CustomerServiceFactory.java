package services;

import models.IModel;

public class CustomerServiceFactory implements IServiceFactory {

    private static CustomerService instance;

    @Override
    public Service<? extends IModel> getInstance() {
        if(instance == null){
            instance = new CustomerService();
        }
        return instance;
    }
}
