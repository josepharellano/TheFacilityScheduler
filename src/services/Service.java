package services;

import dao.IDao;
import models.IModel;

import java.sql.SQLException;
import java.util.HashMap;

public abstract class Service<T extends IModel>{

    protected IDao<T> dao;

    protected HashMap<Integer,T> data;

    public Service(IDao<T> dao){
        this.dao = dao;
    }

   public void refreshData() throws SQLException {
       data = dao.selectAll();
   }

   public HashMap<Integer,T> getData(){
       return data;
   }

   public T getItem(int id){
       return data.get(id);
   }

}
