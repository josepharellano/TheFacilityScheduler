package dao;

import java.sql.SQLException;
import java.util.List;

/**
 *Data Access Object interface for all Model implementations.
 * @param <T> Represents a Model
 * @author Joseph Arellano
 */
public interface IDao<T> {
    //Inserts Record into database
    public void insert(T record) throws SQLException;

    //Delete Record from database
    public boolean delete(int id);

    //Update Record in database
    public boolean update(T record);

    //Select Record from database
    public boolean select(int id);

    //Select All Records from database
    public List<T> selectAll() throws SQLException;
}
