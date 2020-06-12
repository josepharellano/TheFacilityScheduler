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
    public static String insertRecordSQL(String table, List<String> columns) {
        String sqlCommand = "INSERT INTO ";
        String columnHeaders = String.join(",", columns);

        //Create Prepared Statement by taking columns and replacing them with ?
        String values = columns.stream()
                .map(item-> "?")
                .collect(Collectors.joining(", "));

        return sqlCommand + table + "(" + columnHeaders + ") " + "VALUES " + "(" + values + ")";
    }

    /**
     * Build SQL Query to delete record from table where conditions are met
     * @param table Target Table.
     * @param conditions Determines at which columns to delete the record.
     * @return
     */
    public static String deleteRecordsSQL(String table, String conditions){
        return "DELETE FROM " + table + " WHERE " + conditions;
    }

    /**
     * Returns the SQLCommand to update a record in a table.
     * @param table Target table
     * @param columns Columns to update on.
     * @param condition On which column and value to complete update on.
     * @return
     */
    public static String updateRecordSQL(String table, List<String> columns, String condition){
        String sqlCommand = "UPDATE " + table + " SET ";
        String values = columns.stream().map(column-> column + "=?").collect(Collectors.joining(", "));
        System.out.println(sqlCommand + values + " WHERE " + condition);
        return sqlCommand + values + " WHERE " + condition;
    }

    public static String selectRecordSQL(String table, String condition){
        return "SELECT * FROM " + table + "WHERE " + condition;
    }

}
