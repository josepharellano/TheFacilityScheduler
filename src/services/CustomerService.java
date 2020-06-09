package services;

import dao.CustomerIDaoImpl;
import models.Customer;

import java.sql.SQLException;
import java.util.List;

/**
 * CustomerService is a singleton service that handles the Customer business logic.
 * @author Joseph Arellano
 */

public class CustomerService {

    List<Customer> customers; //Cache list of customers.
    CustomerIDaoImpl dao; //Data Access Object for Customer
    private static CustomerService instance; //Customer Service is a singleton.

    private CustomerService(){
        dao = new CustomerIDaoImpl();
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
    public void addCustomer(){
        //First check if the customer
        //Must first add address to database if it does not exists
        //Then add Customer
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
}
