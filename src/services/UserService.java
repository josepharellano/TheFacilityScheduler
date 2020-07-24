package services;

import dao.UserIDaoImpl;
import models.User;
import utilities.Exceptions;

import java.sql.SQLException;
import java.util.Locale;

public class UserService {

    private static UserService instance; // Singleton instance of this service.
    private static User sessionUser; //User of the session

    private UserIDaoImpl dao;


    private UserService(){

        dao = new UserIDaoImpl();
    }

    public static UserService getInstance(){
        if(instance == null){
            instance = new UserService();
        }
        return instance;
    }


    public void login(String userName, String password) throws Exceptions.InvalidPasswordEx, Exceptions.InvalidUserNameEx {

        try {
            //Retrieve user from database.
            User user = dao.select(userName);
            //Check if Password matches
            if(user != null){
                if(user.getPassword().equals(password)){
                    //Set session user
                    sessionUser = user;
                }else {
                    throw new Exceptions.InvalidPasswordEx();
                }
            }else{
                throw new Exceptions.InvalidUserNameEx();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static User getSessionUser() {
        return sessionUser;
    }
}
