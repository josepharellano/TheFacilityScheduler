package services;

import dao.CustomerIDaoImpl;
import models.Address;
import models.Customer;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * CustomerService is a singleton service that handles the Customer business logic.
 * @author Joseph Arellano
 */

public class CustomerService {

    List<Customer> customers; //Cache list of customers.
    CustomerIDaoImpl dao; //Data Access Object for Customer
    private static CustomerService instance; //Customer Service is a singleton.
    private static AddressService addrService; //Address Service instance.

    private CustomerService(){
        dao = new CustomerIDaoImpl();
        addrService = AddressService.getInstance();
        refreshCustomerList();
    }

    public static CustomerService getInstance(){
        if(instance == null){
            instance = new CustomerService();
        }
        return instance;
    }

    //Return cached list of customers.
    public List<Customer> getCustomers(){
    return customers;
    }

    /**
     *
     */
    public void refreshCustomerList(){
        try {
            customers = dao.selectAll();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    //Add Customer to database.
    public void addCustomer(Customer customer) throws EmptyInputValue, SQLException{

        int addressId; //New address Id in database.

       validateCustomerInput(customer);

        //Insert new address into database and set addressId to returned database Id
        customer.getAddress().setId(addrService.newAddress(customer.getAddress()));

        //Add Customer to database
        //TODO Change out creator with user after login.
        dao.insert(customer,"Test");

    }

    //Update Customer in database
    public void updateCustomer(Customer customer) throws EmptyInputValue, SQLException{

        //validate rest of customer fields
        validateCustomerInput(customer);

        //Check if Address is already in database.
        if(!addrService.dbContainsAddress(customer.getAddress())){
            //Add address to database and set its Id
            customer.getAddress().setId(addrService.newAddress(customer.getAddress()));
        }

        //TODO Change updatedBy to loggedIn user.
        dao.update(customer,"Test");

    }

    public void validateCustomerInput(Customer customer) throws EmptyInputValue{

        //Validate City and Country are not null.
        try {
            Objects.requireNonNull(customer.getAddress().getCity());
        }catch(NullPointerException ex){
            throw new EmptyInputValue("Must Select a City.");
        }
        try {
            Objects.requireNonNull(customer.getAddress().getCountry());
        }catch(NullPointerException ex){
            throw new EmptyInputValue("Must Select a Country.");
        }
        //Validate Remaining fields are not empty
        if(customer.getName().isEmpty()){
            throw new EmptyInputValue("Must provide a Name.");
        }
        if(customer.getAddress().getAddress().isEmpty()){
            throw new EmptyInputValue("Must provide an Address.");
        }

        if(customer.getAddress().getPostalCode().isEmpty()){
            throw new EmptyInputValue("Must provide a Postal Code.");
        }
        if(customer.getAddress().getPhone().isEmpty()){
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
