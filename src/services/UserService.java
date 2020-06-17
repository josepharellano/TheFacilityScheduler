package services;

import dao.UserIDaoImpl;
import models.User;

import java.sql.SQLException;

public class UserService {

    private static UserService instance; // Singletone instance of this service.
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


    public void login(String userName, String password) throws InvalidPasswordEx, InvalidUserNameEx{

        try {
            //Retreive user from database.
            User user = dao.select(userName);
            //Check if Password matches
            if(user != null){
                if(user.getPassword().equals(password)){
                    //Set session user
                    sessionUser = user;
                }else {
                    throw new InvalidPasswordEx();
                }
            }else{
                throw new InvalidUserNameEx();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static class InvalidPasswordEx extends Exception{
        public InvalidPasswordEx(){
            super("Password does not match!");
        }
    }

    public static class InvalidUserNameEx extends Exception{
        public InvalidUserNameEx(){
            super("Invalid User Name");
        }
    }
}
