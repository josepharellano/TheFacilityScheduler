package models;

import java.sql.Date;
import java.sql.Timestamp;

public class Customer {

    private Integer id;
    private String name;
    private Address address; // Address of the Customer
    private boolean isActive; // Is customer active

    public Customer(Integer id, String name, Address address, boolean isActive) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.isActive = isActive;
    }

    public Customer(String name, Address address){
        //Id is provided by the database
        this.id = null;
        this.name = name; //Name of Customer
        this.address = address; //Address of Customer
        this.isActive = true; //Is the customer active?
    }

    //Getters
    public String getName() {return this.name;}
    public Address getAddress(){
        return this.address;
    }
    public int getId() {return this.id;}
    public boolean isActive(){
        return this.isActive;
    }

    //Setters
    public void setName(String name) {this.name = name;}
    public void setAddress(Address address){ this.address = address;}


    @Override
    public String toString() {
        return "[ID:" + this.id + " Name:" + this.name + " Address[ " + this.address.toString() + " ] Active: " + this.isActive;
    }
}
