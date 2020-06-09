package models;

import java.sql.Date;
import java.sql.Timestamp;

public class Address {
    int id;
    String address;
    String addressLine;
    String postalCode;
    String phone;
    String city;
    String country;

    public Address(int id, String address,String addressLine,String postalCode,String phone,String city,String country){
        this.id = id;
        this.address = address;
        this.addressLine = addressLine;
        this.postalCode = postalCode;
        this.phone = phone;
        this.city = city;
        this.country = country;
    }

    //Getters
    public int getId(){
        return this.id;
    }
    public String getAddress(){
        return this.address;
    }
    public String getAddressLine(){
        return this.addressLine;
    }
    public String getPhone(){
        return this.phone;
    }
    public String getPostalCode(){
        return this.postalCode;
    }
    public String getCity(){
        return this.city;
    }
    public String getCountry(){
        return this.country;
    }

    @Override
    public String toString() {
        return "[ ID: " + this.id + " Address: " + this.address + " Line 2: " + this.addressLine + " Phone: " + this.phone +
                " City: " + this.city + " Country: " + this.country + " Postal Code: " + this.postalCode + " ]";

    }
}
