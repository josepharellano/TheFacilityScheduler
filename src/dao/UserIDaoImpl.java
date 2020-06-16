package dao;

import com.mysql.jdbc.Connection;
import models.User;
import utilities.Constants;
import utilities.DBConnection;
import utilities.DBQuery;


import java.sql.*;
import java.util.ArrayList;

/**
 * Data Access Object implementation for a User Model.
 * @author Joseph Arellano
 */
public class UserIDaoImpl implements IDao<User> {

    /**
     * Method not Implemented as not needed for this application.
     * @param record User being added to the table.
     * @param creator userName of session user who is adding record to the table.
     * @return The userId of the newly added user.
     * @throws SQLException
     */
    @Override
    public Integer insert(User record,String creator) throws SQLException {
        return null;
    }

    /**
     * Method not Implemented as not needed for this application.
     * @param id
     * @throws SQLException
     */
    @Override
    public void delete(int id) throws SQLException {

    }

    /**
     * Method not Implemented as not needed for this application.
     * @param record User being updated.
     * @param updateBy The userName of the session user.
     */
    @Override
    public void update(User record,String updateBy) {

    }

    /**
     * Selects a user from user table by userName and returns a user to calling class.
     * @param userName is the name of the user stored in the User table.
     * @throws SQLException
     */
    @Override
    public User select(String userName) throws SQLException {
         final String  sqlQuery ="SELECT userId,userName,password,active FROM " + Constants.USER_TABLE + " WHERE userName = " + userName;

         //Open connection to database.
         try(Connection conn = DBConnection.startConnection()) {
             //create a Prepared Statement to Query with.
             PreparedStatement ps = DBQuery.setPreparedStatement(conn, sqlQuery);
            //Query database.
             DBQuery.makeQuery();
             //Get Results set of the query.
             ResultSet rs = DBQuery.getResultSet();
            //Parse Result Set to create new user to return.
             if (rs.next()) {
                 int userId = rs.getInt(1);
                 String name = rs.getString(2);
                 String password = rs.getString(3);
                 boolean isActive = rs.getBoolean(4);

                 //Instantiate new user
                 User user = new User(userId, password, name, isActive);
                 return user;
             }
         }
       return null;
    }

    /**
     * Method not Implemented as not needed for this application.
     * @throws SQLException On failing a database operation.
     * @return  ArrayList of users
     */
    @Override
    public ArrayList<User> selectAll() throws SQLException {
        return null;
    }
}
