package models;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Represents a ModelRecord of a User
 * @author Joseph Arellano
 */
public class User {

    private String password;
    private boolean isActive;

    /**
     * Constructor
     * @param id Database Id of the user
     * @param password User password to log into the client
     * @param name Name of the User
     * @param isActive Is the user Active and able to log into the system.
     */
    public User(int id, String password, String name,boolean isActive){
        this.isActive = isActive;
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
