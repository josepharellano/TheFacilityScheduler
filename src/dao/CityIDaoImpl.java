package dao;

import com.mysql.jdbc.Connection;
import models.Address;
import utilities.Constants;
import utilities.DBConnection;
import utilities.DBQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CityIDaoImpl implements IDao<Address.City> {
    @Override
    public Integer insert(Address.City record, String creator) throws SQLException {
        return null;
    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public void update(Address.City record, String updatedBy) throws SQLException {

    }

    @Override
    public Address.City select(String name) throws SQLException {
        return null;
    }

    @Override
    public HashMap<Integer, Address.City> selectAll() throws SQLException {
        HashMap<Integer, Address.City> cities = new HashMap<>(); //List of cities
        //SQL Query Statement
        String sqlQuery = "SELECT city.cityId, city.city,country.country,country.countryId FROM " + Constants.CITY_TABLE +
                " INNER JOIN country ON city.countryId = country.countryId";


        try (Connection conn = DBConnection.startConnection()) {
            PreparedStatement ps = DBQuery.setPreparedStatement(conn, sqlQuery);
            DBQuery.makeQuery();

            ResultSet rs = DBQuery.getResultSet();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String country = rs.getString(3);
                int countryId = rs.getInt(4);

                Address.City city = new Address.City(id, name, new Address.Country(countryId, country));

                cities.put(id, city);
            }
        }
        return cities;
    }
}
