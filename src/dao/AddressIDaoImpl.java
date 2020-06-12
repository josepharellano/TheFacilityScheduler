package dao;

import com.mysql.jdbc.Connection;
import models.Address;
import utilities.Constants;
import utilities.DBConnection;
import utilities.DBQuery;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class AddressIDaoImpl implements  IDao<Address>{



    @Override
    public Integer insert(Address record, String creator) throws SQLException {
        String sqlQuery = "INSERT INTO address (address,address2,cityId,postalCode,phone,createDate,createdBy,lastUpdate,lastUpdateBy) " +
                        "VALUES (?,?,?,?,?,?,?,?,?)";

        try(Connection conn = DBConnection.startConnection()) {
            PreparedStatement ps = DBQuery.setPreparedStatement(conn, sqlQuery);

            ps.setString(1, record.getAddress());
            ps.setString(2, record.getAddressLine());
            ps.setInt(3, record.getCity().getId());
            ps.setString(4, record.getPostalCode());
            ps.setString(5, record.getPhone());
            ps.setDate(6, new Date(System.currentTimeMillis()));
            ps.setString(7, creator);
            ps.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            ps.setString(9, creator);

            DBQuery.makeQuery();


            ResultSet rs = DBQuery.getPreparedStatement().getGeneratedKeys();
            //Return the generated Id of the new address
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return null;
    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public void update(Address record, String updatedBy) throws SQLException {

    }

    @Override
    public void select(int id) {

    }

    @Override
    public List<Address> selectAll() throws SQLException {

        //List of addresses
        List<Address> addresses = new ArrayList<>();

        String sqlQuery = "SELECT addr.addressId,addr.address,addr.address2,addr.postalCode,addr.phone, addr.cityId,city.city, city.countryId, country.country " +
                "FROM address addr " +
                "INNER JOIN city ON addr.cityId = city.cityId " +
                "INNER JOIN country ON city.countryId = country.countryId;";
        try(Connection conn = DBConnection.startConnection()){

            PreparedStatement ps = DBQuery.setPreparedStatement(conn,sqlQuery);
            DBQuery.makeQuery();

            ResultSet rs = DBQuery.getResultSet();

            while(rs.next()){

                //Get data from resultSet
                int id = rs.getInt(1);
                String address = rs.getString(2);
                String addressLine = rs.getString(3);
                String postalCode = rs.getString(4);
                String telephone = rs.getString(5);
                int cityId = rs.getInt(6);
                String city = rs.getString(7);
                int countryId = rs.getInt(8);
                String country = rs.getString(9);

                Address addr = new Address(id,address,addressLine,postalCode,telephone,
                        new Address.City(cityId,city),new Address.Country(countryId,country));
                addresses.add(addr);
            }
        }
        return addresses;
    }

    public List<Address.City> getCities() throws SQLException {

        List<Address.City> cities = new ArrayList<>(); //List of cities
        //SQL Query Statement
        String sqlQuery = "SELECT * FROM " + Constants.cityTable;


        try(Connection conn = DBConnection.startConnection()){
            PreparedStatement ps = DBQuery.setPreparedStatement(conn,sqlQuery);
            DBQuery.makeQuery();

            ResultSet rs = DBQuery.getResultSet();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);

                Address.City city = new Address.City(id,name);

                cities.add(city);
            }
        }

        return cities;
    }

    public List<Address.Country> getCountries() throws SQLException {

        List<Address.Country> countries = new ArrayList<>(); //List of cities
        //SQL Query Statement
        String sqlQuery = "SELECT * FROM " + Constants.countryTable;


        try(Connection conn = DBConnection.startConnection()){
            PreparedStatement ps = DBQuery.setPreparedStatement(conn,sqlQuery);
            DBQuery.makeQuery();

            ResultSet rs = DBQuery.getResultSet();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);

                Address.Country country = new Address.Country(id,name);

                countries.add(country);
            }
        }
        return countries;
    }
}

