package dao;

import com.mysql.jdbc.Connection;
import models.Customer;
import utilities.DBConnection;
import utilities.DBQuery;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Data Access Object implementation for a Customer.
 * @author Joseph Arellano
 */
public class CustomerIDaoImpl implements IDao<Customer> {

    //SQL Query String to insert into Database
    private static final String INSERT_INTO_CUSTOMER = "INSERT INTO customer (customerName,addressId,active,createDate," +
            "createdBy,lastUpdate,lastUpdateBy) VALUES(?,?,?,?,?,?,?)";

    @Override
    public void insert(Customer record) throws SQLException {
     // Use Try With Resources to handle auto closing of resources.
     try(Connection conn = DBConnection.startConnection()){

         //Set PreparedStatement
         PreparedStatement ps = DBQuery.setPreparedStatement(conn,INSERT_INTO_CUSTOMER);

         //Build Query Statement
         ps.setString(1, record.getName());
         //Testing purposes only
         ps.setInt(2,1);
//         ps.setInt(2,record.getAddress().getId());
         ps.setBoolean(3,record.isActive());
         ps.setDate(4,record.getCreateDate());
         ps.setString(5,record.getCreatedBy());
         ps.setTimestamp(6,record.getLastUpdate());
         ps.setString(7,record.getLastUpdateBy());

         //Complete Query
         DBQuery.makeQuery();
     }
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean update(Customer record) {
        return false;
    }

    @Override
    public boolean select(int id) {
        return false;
    }

    @Override
    public List<Customer> selectAll() throws SQLException {
        return null;
    }
}
