package dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 *Data Access Object interface for all Model implementations.
 * @param <T> Represents a Model
 * @author Joseph Arellano
 */
public interface IDao<T> {
    //Inserts Record into database
    public Integer insert(T record,String creator) throws SQLException;

    //Delete Record from database
    public void delete(int id) throws SQLException;

    //Update Record in database
    public void update(T record,String updatedBy) throws SQLException;

    //Select Record from database
    public T select(String name) throws SQLException;

    //Select All Records from database
    public HashMap<Integer,T> selectAll() throws SQLException;
}
