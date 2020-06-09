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
        Parent root = FXMLLoader.load(getClass().getResource("/views/CustomerView.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setMaximized(true);
        primaryStage.getScene().getStylesheets().add("/css/CustomerScene");
        primaryStage.setMinWidth(600);
        primaryStage.show();
    }


    public static void main(String[] args) {

        List<Customer> customers;
        UserIDaoImpl user = new UserIDaoImpl();
        CustomerService service = CustomerService.getInstance();

           customers = service.getCustomers();

            //Test Inserting

        launch(args);
    }
}
