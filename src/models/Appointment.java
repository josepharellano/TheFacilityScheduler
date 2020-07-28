package models;

import java.net.URL;
import java.time.Instant;
import java.time.ZonedDateTime;

public class Appointment implements IModel {

    private Integer id; //Unique Id of the appointment
    private Integer userId; //User associated with appointment
    private Integer customerId; //Customer the appointment is for
    private String title; //Title of appointment
    private String desc; //Description of appointment
    private String contact; //Appointment contact
    private String type; //Type of appointment
    private String location; //Location of the appointment
    private String url; //URL of the appointment
    private ZonedDateTime start; //Start Time of the appointment
    private ZonedDateTime end; //End Time of the appointment

    /**
     * Create an appointment
     * @param userId Id of the user responsible for the appointment
     * @param customerId  Id of the customer the appointment is for
     * @param title of the appointment
     * @param desc description of the appointment
     * @param contact contact for the appointment
     * @param type of appointment
     * @param location of appointment
     * @param start date and time of the appointment
     * @param end date and time of the appointment
     */
    public Appointment(Integer id, Integer userId, Integer customerId, String title, String desc, String contact, String type,String location,ZonedDateTime start, ZonedDateTime end, String url) {
        this.id = id;
        this.userId = userId;
        this.customerId = customerId;
        this.title = title;
        this.desc = desc;
        this.contact = contact;
        this.type = type;
        this.location = location;
        this.start = start;
        this.end = end;
        this.url = url;
    }

    /**
     * Create an appointment without ID as ID will be given after adding to database.
     * @param userId responsible for the appointment
     * @param customerId the appointment is for
     * @param title of the appointment
     * @param desc description of the appointment
     * @param contact contact for the appointment
     * @param type of appointment
     * @param location of appointment
     * @param start date and time of the appointment
     * @param end date and time of the appointment
     */
    public Appointment(Integer userId, Integer customerId, String title, String desc, String contact, String type, String location, ZonedDateTime start, ZonedDateTime end, String url) {
        this.id = null;
        this.userId = userId;
        this.customerId = customerId;
        this.title = title;
        this.desc = desc;
        this.contact = contact;
        this.type = type;
        this.location = location;
        this.start = start;
        this.end = end;
        this.url = url;
    }



    //Getters
    public Integer getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String getLabel() {
        return null;
    }

    public Integer getUser() {
        return userId;
    }

    public Integer getCustomer() {
        return customerId;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getContact() {
        return contact;
    }

    public String getType() {
        return type;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public String getLocation() {
        return location;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    //Setters

    public void setUser(int userId) {
        this.userId = userId;
    }

    public void setCustomer(int customerId) {
        this.customerId = customerId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "[ID:" + id + " Title:" + title + " Customer:[ " + customerId + " ] User:[ " + userId+ " ]" +
                "Description: " + desc + " Location: " + location + " Contact: " + contact + " Type: " + type + " URL: " + "]\n";
    }
}
