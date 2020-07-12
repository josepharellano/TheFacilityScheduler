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

public class CountryIDaoImpl implements IDao<Address.Country> {
    @Override
    public Integer insert(Address.Country record, String creator) throws SQLException {
        return null;
    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public void update(Address.Country record, String updatedBy) throws SQLException {

    }

    @Override
    public Address.Country select(String name) throws SQLException {
        return null;
    }

    @Override
    public HashMap<Integer, Address.Country> selectAll() throws SQLException {

        HashMap<Integer, Address.Country> countries = new HashMap<>();

        final String sqlQuery = "SELECT countryId,country FROM " + Constants.COUNTRY_TABLE;

        //Query Database
        try(Connection conn = DBConnection.startConnection()){

            DBQuery.setPreparedStatement(conn,sqlQuery);
            DBQuery.makeQuery();

            ResultSet rs = DBQuery.getResultSet();

            while(rs.next()){

                int countryId = rs.getInt(1);
                String name = rs.getString(2);

                Address.Country country = new Address.Country(countryId,name);

                countries.put(countryId,country);

            }


        }

        return countries;
    }
}
