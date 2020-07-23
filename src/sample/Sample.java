package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Sample extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/views/CustomerView.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
//        primaryStage.setMaximized(true);
//        primaryStage.setMinWidth(600);
        primaryStage.show();
    }


    public static void main(String[] args)  {


        //Test Inserting

        launch(args);
    }
}
