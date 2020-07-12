package services;

import dao.CustomerIDaoImpl;
import models.Address;
import models.Customer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;

/**
 * CustomerService is a singleton service that handles the Customer business logic.
 * @author Joseph Arellano
 */

public class CustomerService extends Service<Customer> {

    HashMap<Integer,Customer> customers; //Cache list of customers.
    CustomerIDaoImpl dao; //Data Access Object for Customer
    private static CustomerService instance; //Customer Service is a singleton.
    private static AddressService addrService; //Address Service instance.

    protected CustomerService(){
        super(new CustomerIDaoImpl());
        addrService = (AddressService) ServiceFactory.getService(new AddressServiceFactory());
        try {
            this.refreshData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //Add Customer to database.
    public void addCustomer(Customer customer, Address address) throws EmptyInputValue, SQLException{

       validateCustomerInput(customer,address);

        //Insert new address into database and set addressId to returned database Id
        customer.getAddressId().setAddressId(addrService.newAddress(address));

        //Add Customer to database
        //TODO Change out creator with user after login.
        dao.insert(customer,"Test");

    }

    //Update Customer in database
    public void updateCustomer(Customer customer, Address address) throws EmptyInputValue, SQLException{

        //validate rest of customer fields
        validateCustomerInput(customer, address);

        //Check if Address is already in database.
        if(!addrService.dbContainsAddress(address)){
            //Add address to database and set its Id
            customer.setAddressId(addrService.newAddress(address));
        }

        //TODO Change updatedBy to loggedIn user.
        dao.update(customer,"Test");

    }

    public void validateCustomerInput(Customer customer, Address address) throws EmptyInputValue{

        //Validate City and Country are not null.
        try {
            Objects.requireNonNull(address.getCity());
        }catch(NullPointerException ex){
            throw new EmptyInputValue("Must Select a City.");
        }
        try {
            Objects.requireNonNull(address.getCountry());
        }catch(NullPointerException ex){
            throw new EmptyInputValue("Must Select a Country.");
        }
        //Validate Remaining fields are not empty
        if(customer.getName().isEmpty()){
            throw new EmptyInputValue("Must provide a Name.");
        }
        if(address.getAddress().isEmpty()){
            throw new EmptyInputValue("Must provide an Address.");
        }

        if(address.getPostalCode().isEmpty()){
            throw new EmptyInputValue("Must provide a Postal Code.");
        }
        if(address.getPhone().isEmpty()){
            throw new EmptyInputValue("Must provide a Telephone Number.");
        }
    }

    public boolean removeCustomer(int id) {
        try {
            dao.delete(id);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static class EmptyInputValue extends Exception{
        public EmptyInputValue(String msg){
            super(msg);
        }
    }
}
