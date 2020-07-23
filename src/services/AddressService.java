package services;

import dao.AddressIDaoImpl;
import dao.CityIDaoImpl;
import dao.CountryIDaoImpl;
import models.Address;

import java.sql.SQLException;
import java.util.HashMap;

public class AddressService extends Service<Address> {

    CityIDaoImpl citydao;
    CountryIDaoImpl countrydao;

    private static AddressService instance;
    HashMap<Integer,Address> addresses;
    HashMap<Integer,Address.City> cities;
    HashMap<Integer,Address.Country> countries;

    protected AddressService (){
        super(new AddressIDaoImpl());
        citydao = new CityIDaoImpl();
        countrydao = new CountryIDaoImpl();


        try {
            refreshAddressData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void refreshAddressData() throws SQLException {
        addresses = this.dao.selectAll();
        cities = citydao.selectAll();
        countries = countrydao.selectAll();
    }

    //TODO: Change out creator with user
    public int newAddress(Address address) throws SQLException {
        return dao.insert(address,"Test");
    }


    //Checks to see if the address is in the database already.
    //TODO EdgeCase: Address list contains stale data.
    public boolean dbContainsAddress(Address address){
        return addresses.containsValue(address);
    }

    //Getters
    public HashMap<Integer,Address.City> getCities(){
        return cities;
    }
    public HashMap<Integer, Address.Country> getCountries() {
        return countries;
    }
}
