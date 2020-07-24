package models;

public class Customer implements IModel {

    private Integer id; //Unique Id of the customer
    private String name; //Name of customer
    private Address address; // Key to address Record associated with this customer
    private boolean isActive; // Is customer active

    /**
     * Create new Customer
     * @param id of customer
     * @param name of customer
     * @param address Address associated with customer
     * @param isActive Customer is active
     */
    public Customer(Integer id, String name, Address address, boolean isActive) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.isActive = isActive;
    }

    /**
     * Create a new customer without ID as ID will be provided by database. New customers are considered active.
     * @param name of customer
     * @param address Address associated with customer
     */
    public Customer(String name, Address address){
        this.id = null;
        this.name = name;
        this.address = address;
        this.isActive = true;
    }

    //Getters
    public String getName() {return this.name;}
    public Address getAddress(){
        return this.address;
    }
    public Integer getId() {return this.id;}

    @Override
    public String getLabel() {
        return this.name;
    }

    public boolean isActive(){
        return this.isActive;
    }

    //Setters
    public void setName(String name) {this.name = name;}
    public void setAddress(Address address){ this.address = address;}
    public void setId(int id){this.id = id;}



    @Override
    public String toString() {
        return "[ID:" + this.id + " Name:" + this.name + " Address[ " + this.address.toString() + " ] Active: " + this.isActive;
    }
}
