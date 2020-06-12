package models;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Represents a ModelRecord of a User
 * @author Joseph Arellano
 */
public class User extends ModelRecord {

    private String password;
    private boolean isActive;

    /**
     * Constructor
     * @param id Database Id of the user
     * @param password User password to log into the client
     * @param name Name of the User
     * @param isActive Is the user Active and able to log into the system.
     * @param createDate Date when the user was created.
     * @param creator Name of the user who created this user.
     * @param lastUpdate Timestamp when this user was last updated.
     * @param lastUpdateBy Name of the user who last updated this user.
     */
    public User(int id, String password, String name,boolean isActive, Date createDate, String creator, Timestamp lastUpdate, String lastUpdateBy) {
        super(id, name, createDate, creator, lastUpdate, lastUpdateBy);

        this.isActive = isActive;
        this.password = password;
    }

    /**
     *
     * @param password User Password
     * @param name Name of this user
     * @param creator Name of the user that created this user.
     */
    public User(String password,String name,String creator){

        super(name,creator);
        this.isActive = true;
        this.password = password;
    }

    /**
     * Toggles the Active state of this user.
     */
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
