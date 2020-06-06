package models;

import java.sql.Date;
import java.sql.Timestamp;

public class Customer extends  ModelRecord{

    Address address; // Address of the Customer
    boolean isActive; // Is customer active

    public Customer(int id, String name, Date createDate, String creator, Timestamp lastUpdate,
                    String lastUpdateBy, Address address,boolean isActive) {
        super(id, name, createDate, creator, lastUpdate, lastUpdateBy);

        this.address = address;
        this.isActive = isActive;
    }

    public Customer(String name,String creator, Address address){
        super(name,creator);

        this.address = address;
        this.isActive = true;
    }

    //Getters
    public Address getAddress(){
        return this.address;
    }

    public boolean isActive(){
        return this.isActive;
    }
}
