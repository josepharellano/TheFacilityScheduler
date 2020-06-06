package dao;

import com.mysql.jdbc.Connection;
import models.User;
import utilities.DBConnection;
import utilities.DBQuery;

import javax.xml.transform.Result;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements DaoInterface<User> {

    //Insert Statement
    private static final String INSERT_USERS_SQL = "INSERT INTO user (userName,password,active,createDate,createdBy,lastUpdate,lastUpdateBy) VALUES(?,?,?,?,?,?)";
    //Select All Statement
    private static final String SELECT_ALL_USERS_SQL ="SELECT * from user";
    /**
     * Inserts a user object into the user database
     * @param record user object being inserted into database
     * @throws SQLException
     */
    @Override
    public void insert(User record) throws SQLException {
        //Try with Resources to obtain connection
        try (Connection conn = DBConnection.startConnection()) {

            //Set Statement
            PreparedStatement ps = DBQuery.setPreparedStatement(conn,INSERT_USERS_SQL);

            //Build Query
            ps.setString(0,record.getName());
            ps.setString(1,record.getPassword());
            ps.setBoolean(2, record.getIsActive());
            ps.setDate(3, record.getCreateDate());
            ps.setString(4,record.getCreatedBy());
            ps.setTimestamp(5, record.getLastUpdate());
            ps.setString(6,record.getLastUpdateBy());

            //Make Query
            DBQuery.makeQuery();

            //Query is a success
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //Close connection
        DBConnection.closeConnection();
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean update(User record) {
        return false;
    }

    @Override
    public boolean select(int id) {
        return false;
    }

    /**
     * Retrieves all users from user database.
     * @return  ArrayList of users
     */
    @Override
    public ArrayList<User> selectAll() throws SQLException{

        //List of Users obtained from database.
        ArrayList<User> users = new ArrayList<>();

        //Get Connection from Database
        try(Connection conn = DBConnection.startConnection()){
          //Set Statement
            DBQuery.setPreparedStatement(conn,SELECT_ALL_USERS_SQL);

          //Execute Query
            DBQuery.makeQuery();

          //Get ResultSet
            ResultSet rs = DBQuery.getResultSet();

          //Iterate through the database to create users
            while(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String password = rs.getString(3);
                boolean isActive = rs.getBoolean(4);
                Date createDate = rs.getDate(5);
                String createdBy = rs.getString(6);
                Timestamp lastUpdate = rs.getTimestamp(7);
                String lastUpdateBy = rs.getString(8);

                //Create user and add to list
                users.add(new User(id,password,name,isActive,createDate,createdBy,lastUpdate,lastUpdateBy));
            }
        }
        return users;
    }
}
