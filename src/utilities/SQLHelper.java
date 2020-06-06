package utilities;

import java.util.List;
import java.util.stream.Collectors;

public class SQLHelper {

    /**
     * Builds a SQL Query to select all records from requested table.
     * @param table The table containing the requested records.
     * @return A SQL Query String.
     */
    public static String selectAllSQL(String table){
        return "SELECT * from " + table;
    }

    /**
     * Builds SQL Query to insert new record into selected table.
     * @param table The table the record is being inserted into.
     * @param columns The column names of the table.
     * @return A SQL Query String.
     */
    public static String insertIntoSQL(String table, List<String> columns) {
        String sqlCommand = "INSERT INTO ";
        String columnHeaders = String.join(",", columns);

        //Create Prepared Statement by taking columns and replacing them with ?
        String values = columns.stream()
                .map(item-> "?")
                .collect(Collectors.joining(", "));

        return sqlCommand + table + "(" + columnHeaders + ") " + "VALUES " + "(" + values + ")";
    }

    /**
     * Build SQL Query to delete recored from table where column = key
     * @param table Target Table.
     * @param column Column to delete records on
     * @param key Column value test.
     * @return
     */
    public static String deleteRecordsSQL(String table, String column, String key ){
        String sqlCommand = "DELETE FROM ";
        return sqlCommand + table + " WHERE " + column + "=" + key;
    }
}
