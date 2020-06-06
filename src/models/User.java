package models;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class User extends ModelRecord {

    private String password;
    private boolean isActive;

    public User(int id, String password, String name,boolean isActive, Date createDate, String creator, Timestamp lastUpdate, String lastUpdateBy) {
        super(id, name, createDate, creator, lastUpdate, lastUpdateBy);

        this.isActive = isActive;
        this.password = password;
    }

    public User(String password,String name,String creator){
        super(name,creator);
        this.isActive = true;
        this.password = password;
    }

    public void toggleIsActive(){
        this.isActive = !this.isActive;
    }

    //Setters
    public void setPassword(String password){
        this.password = password;
    }

    //Getters
    public boolean getIsActive(){
        return this.isActive;
    }
    public String getPassword(){
        return this.password;
    }
}
