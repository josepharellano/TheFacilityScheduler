package services;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import dao.CustomerIDaoImpl;
import models.Address;
import models.Appointment;
import models.Customer;
import utilities.Exceptions;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;

/**
 * CustomerService is a singleton service that handles the Customer business logic.
 * @author Joseph Arellano
 */

public class CustomerService extends Service<Customer> {

    HashMap<Integer,Customer> customers; //Cache list of customers.
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
    public void addCustomer(Customer customer) throws Exceptions.EmptyInputValue, SQLException{

       validateCustomerInput(customer);

        //Insert new address into database and set addressId to returned database Id
        customer.getAddress().setId(addrService.newAddress(customer.getAddress()));

        //Add Customer to database
        //TODO Change out creator with user after login.
        customer.setId(this.dao.insert(customer,"Test"));
        //Add Customer to cache
        this.data.put(customer.getId(),customer);

    }

    //Update Customer in database
    public void updateCustomer(Customer customer) throws Exceptions.EmptyInputValue, SQLException {
        Address address = customer.getAddress(); //Customer Address
        //validate rest of customer fields
        validateCustomerInput(customer);

        //Check if Address is already in database.
        if(!addrService.dbContainsAddress(address)){
            //Add address to database and set its Id
            address.setId(addrService.newAddress(address));
        }

        //TODO Change updatedBy to loggedIn user.
        this.dao.update(customer,"Test");
        //Update Cache
        data.put(customer.getId(),customer);
    }

    public void validateCustomerInput(Customer customer) throws Exceptions.EmptyInputValue {

        Address address = customer.getAddress();
        //Validate City and Country are not null.
        try {
            Objects.requireNonNull(address.getCity());
        }catch(NullPointerException ex){
            throw new Exceptions.EmptyInputValue("Must Select a City.");
        }
        //Validate Remaining fields are not empty
        if(customer.getName().isEmpty()){
            throw new Exceptions.EmptyInputValue("Must provide a Name.");
        }
        if(address.getAddress().isEmpty()){
            throw new Exceptions.EmptyInputValue("Must provide an Address.");
        }

        if(address.getPostalCode().isEmpty()){
            throw new Exceptions.EmptyInputValue("Must provide a Postal Code.");
        }
        if(address.getPhone().isEmpty()){
            throw new Exceptions.EmptyInputValue("Must provide a Telephone Number.");
        }
    }

    public boolean removeCustomer(int id) throws Exceptions.AppointmentConstraint {

        try {
            this.dao.delete(id);
            this.data.remove(id);
            return true;
        } catch (MySQLIntegrityConstraintViolationException e) {
            throw new Exceptions.AppointmentConstraint();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Customer getCustomerFromID(int id){
        return this.data.get(id);
    }


}
