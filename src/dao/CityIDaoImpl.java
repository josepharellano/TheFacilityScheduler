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

        HashMap<Integer,Address.City> cities = new HashMap<>();

        final String sqlQuery = "SELECT cityId,city,countryId FROM " + Constants.CITY_TABLE;

        //Query Database
        try(Connection conn = DBConnection.startConnection()){


            PreparedStatement ps = DBQuery.setPreparedStatement(conn,sqlQuery); //Set Query Statement
            DBQuery.makeQuery();

            ResultSet rs = DBQuery.getResultSet();

            while (rs.next()) {

                int cityId = rs.getInt(1);
                String cityName = rs.getString(2);
                int countryId = rs.getInt(3);

                Address.City city = new Address.City(cityId,cityName);
                cities.put(cityId,city);
            }

        }
        return cities;
    }
}
