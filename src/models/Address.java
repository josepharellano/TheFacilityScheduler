package models;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class Address implements IModel {
    Integer id;
    String address;
    String addressLine;
    String postalCode;
    String phone;
    City city;


    public Address(int id, String address,String addressLine,String postalCode,String phone,City city){
        this.id = id;
        this.address = address;
        this.addressLine = addressLine;
        this.postalCode = postalCode;
        this.phone = phone;
        this.city = city;

    }

    public Address(String address,String addressLine,String postalCode,String phone, City city){
        this.id = null;
        this.address = address;
        this.addressLine = addressLine;
        this.postalCode = postalCode;
        this.phone = phone;
        this.city = city;

    }

    public Address(int id){
        this.id = id;
        this.address = null;
        this.addressLine = null;
        this.postalCode = null;
        this.phone = null;
        this.city = null;
    }

    //Getters
    public Integer getId(){
        return this.id;
    }

    @Override
    public String getLabel() {
        return null;
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
    public City getCity(){
        return this.city;
    }


    //Setters
    public void setId(int id){this.id = id;}
    public void setAddressline(String line){this.addressLine = line;}
    public void setAddress(String address){this.address = address;}
    public void setPhone(String phone){ this.phone = phone;}
    public void setPostalCode(String postalCode){this.postalCode = postalCode;}
    public void setCity(Address.City city){ this.city = city;}


    @Override
    public String toString() {
        return "[ ID: " + this.id + " Address: " + this.address + " Line 2: " + this.addressLine + " Phone: " + this.phone +
                " City: " + this.city + " Postal Code: " + this.postalCode + " ]";

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address1 = (Address) o;
        return id.equals(address1.id) &&
                address.equals(address1.address) &&
                addressLine.equals(address1.addressLine) &&
                postalCode.equals(address1.postalCode) &&
                phone.equals(address1.phone) &&
                city.equals(address1.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class City {
        int id;
        String name;
        Country country;

        public City(int id, String name,Country country){
            this.id = id;
            this.name = name;
            this.country = country;
        }

        //Getters
        public int getId(){return this.id;}
        public String getName(){return this.name;}
        public Country getCountry(){ return this.country;}

        @Override
        public String toString(){
            return this.name;
        }

        @Override
        public boolean equals(Object obj){
            if(!(obj instanceof City)) return false;
            City city = (City) obj;
            return this.name.equals(city.getName());
        }
    }

    public static class Country{
        int id;
        String name;

        public Country(int id, String name){
            this.id = id;
            this.name = name;
        }
        //Getters
        public int getId() {return this.id;}
        public String getName(){return this.name;}

        @Override
        public String toString(){
            return this.name;
        }
    }
}
