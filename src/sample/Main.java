package sample;

import dao.CustomerIDaoImpl;
import dao.UserIDaoImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import models.Address;
import models.Customer;
import models.User;
import services.AddressService;
import services.CustomerService;
import utilities.SQLCondition;
import utilities.SQLHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/views/LoginView.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1000, 600));
//        primaryStage.setMaximized(true);
        primaryStage.getScene().getStylesheets().add("/css/globalStyles.css");
        primaryStage.getScene().getStylesheets().add("/css/loginscene.css");
        primaryStage.setMinWidth(600);
        primaryStage.show();
    }


    public static void main(String[] args)  {


            //Test Inserting

        launch(args);
    }
}
