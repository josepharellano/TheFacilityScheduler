package sample;

import dao.UserDaoImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.User;
import utilities.DBConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {

        ArrayList<User> users;
        UserDaoImpl user = new UserDaoImpl();
        try {
           users = user.selectAll();
            System.out.print(Arrays.toString(new ArrayList[]{users}));
        }catch(SQLException ex){
                ex.printStackTrace();
            }
        launch(args);
    }
}
