package sample;

import dao.CustomerIDaoImpl;
import dao.UserIDaoImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Address;
import models.Customer;
import models.User;

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
        UserIDaoImpl user = new UserIDaoImpl();
        CustomerIDaoImpl customer = new CustomerIDaoImpl() ;
        try {
           users = user.selectAll();
            System.out.print(Arrays.toString(new ArrayList[]{users}));
            Address testAdd = new Address("jake","jason");
            testAdd.setId(1);
            Customer test = new Customer("Jake","John", testAdd);

            customer.insert(test);
        }catch(SQLException ex){
                ex.printStackTrace();
            }
        launch(args);
    }
}
