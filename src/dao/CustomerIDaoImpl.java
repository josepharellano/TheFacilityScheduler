package dao;

import com.mysql.jdbc.Connection;
import models.Address;
import models.Customer;
import models.User;
import utilities.DBConnection;
import utilities.DBQuery;
import utilities.SQLHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Data Access Object implementation for a Customer.
 * @author Joseph Arellano
 */
public class CustomerIDaoImpl implements IDao<Customer> {

    //Associated database table name.
    private static final String table = "customer";

    //Columns used for inserting records.
    private static final List<String> insertColumns = Arrays.asList("customerName","addressId","active","createDate",
            "createdBy","lastUpdate","lastUpdateBy");
    //Columns used for updating records.
    private static final List<String> updateColumns = Arrays.asList("customerName","addressId","active","lastUpdate","lastUpdateBy");

    /**
     * Inserts a new Customer record in the database.
     * @param record Customer data to be inserted into table.
     * @param creator Name of the creating this customer.
     * @throws SQLException
     * @return
     */
    @Override
    public Integer insert(Customer record, String creator) throws SQLException {
     // Use Try With Resources to handle auto closing of resources.
     try(Connection conn = DBConnection.startConnection()){

         //Set PreparedStatement
         PreparedStatement ps = DBQuery.setPreparedStatement(conn, SQLHelper.insertRecordSQL(table,insertColumns));

         //Build Query Statement
         ps.setString(1, record.getName());
         //Testing purposes only
         ps.setInt(2,1);
//         ps.setInt(2,record.getAddress().getId());
         ps.setBoolean(3,record.isActive());
         ps.setDate(4, new Date(System.currentTimeMillis()));
         ps.setString(5,creator);
         ps.setTimestamp(6,new Timestamp(System.currentTimeMillis()));
         ps.setString(7,creator);

         //Complete Query
         DBQuery.makeQuery();

         //Return id of the new Customer record
         ResultSet rs = DBQuery.getPreparedStatement().getGeneratedKeys();
         if(rs.next()){
             return rs.getInt(1);
         }else{
             //Record failed to be inserted return null.
             return null;
         }

     }
    }

    /**
     * Deletes given Customer record from table.
     * @param id   Customer id to delete from database.
     * @throws SQLException
     */
    @Override
    public void delete(int id) throws SQLException {
        try(Connection conn = DBConnection.startConnection()) {
            //Deletions will always occur on customerId
            String condition = "customerId =" + id;
            //Set PreparedStatement
            PreparedStatement ps = DBQuery.setPreparedStatement(conn, SQLHelper.deleteRecordsSQL(table,condition));
            //Complete Query
            DBQuery.makeQuery();
        }
    }

    /**
     * Updates a customer record.
     * @param record Customer Details
     * @param updatedBy User who updated the customer record
     * @throws SQLException
     */
    @Override
    public void update(Customer record, String updatedBy) throws SQLException {
        try(Connection conn = DBConnection.startConnection()){

            //Updates will always occur on customerId
           String condition ="customerId=" + record.getId();

           //Set PreparedStatement
            PreparedStatement ps = DBQuery.setPreparedStatement(conn,SQLHelper.updateRecordSQL(table,updateColumns,condition));

            //Build Query Statement
            ps.setString(1, record.getName());
            //Testing purposes only
            ps.setInt(2,1);
//         ps.setInt(2,record.getAddress().getId());
            ps.setBoolean(3,record.isActive());
            ps.setTimestamp(6,new Timestamp(System.currentTimeMillis()));
            ps.setString(7,updatedBy);

            //Make Query
            DBQuery.makeQuery();
        }
    }

    @Override
    public void select(int id) {

    }

    @Override
    public List<Customer> selectAll() throws SQLException {

        List<Customer> customers = new ArrayList<>();
        // SELECT
        String sqlQuery = "SELECT cust.customerId, cust.customerName,addr.addressId,addr.phone, addr.address, addr.address2,city.city, country.country,addr.postalCode,cust.active " +
                            "FROM customer as cust " +
                            "INNER JOIN address as addr ON cust.customerId = addr.addressId " +
                            "INNER JOIN city ON addr.cityId = city.cityId " +
                            "INNER JOIN country ON city.countryId = country.countryId";

        try(Connection conn = DBConnection.startConnection()) {

            PreparedStatement ps = DBQuery.setPreparedStatement(conn,sqlQuery);
            DBQuery.makeQuery();

            ResultSet rs = DBQuery.getResultSet();

            while(rs.next()){

                int id = rs.getInt(1);
                String name = rs.getString(2);
                int addressId = rs.getInt(3);
                String phone= rs.getString(4);
                String address = rs.getString(5);
                String addressLine = rs.getString(6);
                String city = rs.getString(7);
                String country = rs.getString(8);
                String postalCode = rs.getString(9);
                boolean isActive = rs.getBoolean(10);

                Address addr = new Address(addressId,address,addressLine,postalCode,phone,city,country);
                Customer customer = new Customer(id,name,addr,isActive);

                customers.add(customer);
            }

        }
        return customers;
    }
    }