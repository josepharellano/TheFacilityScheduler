package utilities;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // JBDC URL parts
    private static final String protocol = "jdbc:";
    private static final String vendorName = "mySql:";
    private static final String ipAddress = "//3.227.166.251/U06EMb";

    // JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress;

    // Driver and Connection Interface reference
    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    private static Connection conn = null;

    private static final String username ="U06EMb";     //UserName
    private static final String password = "53688739981";    //Password

    public static Connection startConnection() {
        try{
            Class.forName(MYSQLJDBCDriver);
            conn = (Connection) DriverManager.getConnection(jdbcURL,username,password);
            //Log Database Connection
            System.out.println("Connection Successful");
        }catch(ClassNotFoundException | SQLException ex){
            //Log Error
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }//Log error

        return conn;
    }

    public static void closeConnection() throws SQLException{
        conn.close();
    }
}
