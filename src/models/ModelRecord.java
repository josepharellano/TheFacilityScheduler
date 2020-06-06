package models;

import java.sql.Date;
import java.sql.Timestamp;

public abstract class ModelRecord {
    //Following fields are immutable.
    private Integer id;
    private final Date createDate;
    private final String createdBy;

    //Following fields can be updated.
    private String name;
    private Timestamp lastUpdate;
    private String lastUpdateBy;

    public ModelRecord(int id, String name, Date createDate, String creator,Timestamp lastUpdate,String lastUpdateBy){
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.createdBy = creator;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public ModelRecord(String name, String creator) {
        this.id = null;
        this.name = name;
        this.createdBy = creator;
        this.createDate = new Date(System.currentTimeMillis());
        this.lastUpdate = new Timestamp(System.currentTimeMillis());
        this.lastUpdateBy = creator;
    }

    //Setters
    public void setName(String name){
        this.name = name;
    }
    public void setLastUpdate(Timestamp timestamp){
        this.lastUpdate = timestamp;
    }
    public void setLastUpdateBy(String user){
        this.lastUpdateBy = user;
    }

    //Getters
    public Date getCreateDate(){
        return this.createDate;
    }
    public String getCreatedBy(){
        return this.createdBy;
    }
    public Timestamp getLastUpdate(){
        return this.lastUpdate;
    }
    public String getLastUpdateBy(){
        return this.lastUpdateBy;
    }
    public int getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }

    //Temporary helper method delete
    public void setId(int id){
        this.id = id;
    }

}
