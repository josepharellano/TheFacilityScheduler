package utilities;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Database helper class to handle SQL statements and resultSets.
 *
 * Author: Joseph Arellano
 */
public class DBQuery  {

    private static PreparedStatement preparedStatement; //Statement reference

    //Create Statement Object
    public static PreparedStatement setPreparedStatement(Connection conn, String sqlQuery) throws SQLException {
            System.out.println(sqlQuery);
            preparedStatement = conn.prepareStatement(sqlQuery,PreparedStatement.RETURN_GENERATED_KEYS);
            return preparedStatement;
    }

    //Return Statement Object
    public static PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    //Make a Query
    public static void makeQuery() throws SQLException {
            preparedStatement.execute();
    }

    //Get ResultSet
    public static ResultSet getResultSet() throws SQLException {
            return preparedStatement.getResultSet();
    }
}

