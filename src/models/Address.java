package models;

import java.sql.Date;
import java.sql.Timestamp;

public class Address {
    Integer id;
    String address;
    String addressLine;
    String postalCode;
    String phone;
    City city;
    Country country;

    public Address(int id, String address,String addressLine,String postalCode,String phone,City city,Country country){
        this.id = id;
        this.address = address;
        this.addressLine = addressLine;
        this.postalCode = postalCode;
        this.phone = phone;
        this.city = city;
        this.country = country;
    }

    public Address(String address,String addressLine,String postalCode,String phone, City city, Country country){
        this.id = null;
        this.address = address;
        this.addressLine = addressLine;
        this.postalCode = postalCode;
        this.phone = phone;
        this.city = city;
        this.country = country;
    }

    public Address(int id){
        this.id = id;
        this.address = null;
        this.addressLine = null;
        this.postalCode = null;
        this.phone = null;
        this.city = null;
        this.country = null;
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
    public City getCity(){
        return this.city;
    }
    public Country getCountry(){
        return this.country;
    }

    //Setters
    public void setId(int id){this.id = id;}
    public void setAddressline(String line){this.addressLine = line;}
    public void setAddress(String address){this.address = address;}
    public void setPhone(String phone){ this.phone = phone;}
    public void setPostalCode(String postalCode){this.postalCode = postalCode;}
    public void setCity(Address.City city){ this.city = city;}
    public void setCountry(Address.Country country){ this.country = country;}

    @Override
    public String toString() {
        return "[ ID: " + this.id + " Address: " + this.address + " Line 2: " + this.addressLine + " Phone: " + this.phone +
                " City: " + this.city + " Country: " + this.country + " Postal Code: " + this.postalCode + " ]";

    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Address)) return false;
        Address otherAddress = (Address) obj;

        return this.address.equals(otherAddress.getAddress()) && this.phone.equals(otherAddress.getPhone()) && (this.city == otherAddress.getCity())
                && this.getAddressLine().equals(otherAddress.getAddressLine()) && this.postalCode.equals(otherAddress.getPostalCode());
    }

    public static class City {
        int id;
        String name;

        public City(int id, String name){
            this.id = id;
            this.name = name;
        }

        //Getters
        public int getId(){return this.id;}
        public String getName(){return this.name;}

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
