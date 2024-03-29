package main;

import controllers.*;
import javafx.application.Application;
import javafx.stage.Stage;
import main.scenemanager.SceneManager;
import main.scenemanager.JavaFXScene;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    private ResourceBundle rb; //Loaded Resource bundled
    private SceneManager sceneManager; //Manages loading and unloading scenes

    @Override
    public void init() throws Exception {

        //Users location
       Locale userLocale = Locale.getDefault(); //Get the users location

        rb = ResourceBundle.getBundle("scheduler", userLocale); //Load appropriate resource bundle

        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Stage Settings
        primaryStage.setTitle(rb.getString("app_title"));
        primaryStage.setMaximized(true);

        SceneManager.setPrimaryStage(primaryStage); //Set SceneManager's stage

        //Create a Login Screen to be used with the sceneManager
        JavaFXScene<LoginController> loginView= new JavaFXScene<>("loginView","LoginView.fxml");
        JavaFXScene<CustomerController> customerView= new JavaFXScene<>("customerView","CustomerView.fxml");
        JavaFXScene<CalendarController> calenderView = new JavaFXScene<>("calendarView","CalendarView.fxml");
        JavaFXScene<MainController> mainView= new JavaFXScene<>("mainView","MainView.fxml");
        JavaFXScene<AppointmentController> appointmentView= new JavaFXScene<>("appointmentView","AppointmentView.fxml");

        //Set Resource Bundle on Scene
        loginView.setResourceBundle(rb);

        //Load in the FXML Views and sets up the different Scenes
        SceneManager.loadSceneWithResource(loginView); // Login Scene
        SceneManager.loadScene(customerView);
        SceneManager.loadScene(calenderView);
        SceneManager.loadScene(appointmentView);
        SceneManager.loadScene(mainView);


        //Set the stage Scene
        SceneManager.setScene("loginView");

        //Show the stage
        SceneManager.showStage();
    }


    public static void main(String[] args)  {
        launch(args);
    }
}
