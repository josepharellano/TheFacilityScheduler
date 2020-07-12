package models;

/**
 * Represents a ModelRecord of a User
 * @author Joseph Arellano
 */
public class User implements IModel {

    private int id;
    private String name;
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

    public User(int id, String userName, boolean isActive){
        this.id = id;
        this.name = userName;
        this.isActive = isActive;
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

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public Integer getId() {
        return this.id;
    }
}
