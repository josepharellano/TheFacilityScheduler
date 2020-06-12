package services;

import dao.AddressIDaoImpl;
import models.Address;

import java.sql.SQLException;
import java.util.List;

public class AddressService {

    AddressIDaoImpl dao;
    private static AddressService instance;
    List<Address> addresses;
    List<Address.City> cities;
    List<Address.Country> countries;

    private AddressService (){
        dao = new AddressIDaoImpl();
    }

    public static AddressService getInstance() {
        if (instance == null) {
            instance = new AddressService();
        }
        return instance;
    }

    public void refreshAddressData() throws SQLException {
        addresses = dao.selectAll();
        cities = dao.getCities();
        countries = dao.getCountries();
    }

    public int newAddress(Address address) throws SQLException {
        return dao.insert(address,"Test");
    }


    //Checks to see if the address is in the database already.
    //TODO EdgeCase: Address list contains stale data.
    public boolean dbContainsAddress(Address address){
        return addresses.contains(address);
    }
    public List<Address.City> getCities(){
        return cities;
    }

    public List<Address.Country> getCountries(){
        return countries;
    }




}
