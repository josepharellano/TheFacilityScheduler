package services;

public class AddressServiceFactory implements IServiceFactory<AddressService> {

    private AddressService instance;

    @Override
    public AddressService getInstance() {
        if (instance == null) {
            instance = new AddressService();
        }
        return instance;
    }
}
