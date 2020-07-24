package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import main.scenemanager.SceneManager;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public BorderPane mainPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Initialize Main Scene with Appointment View
        mainPane.setCenter(SceneManager.getSceneRoot("appointmentView"));
    }

    public void clickCustomerTab(ActionEvent actionEvent) {
        //Load Customers
        mainPane.setCenter(SceneManager.getSceneRoot("customerView"));
    }

    public void clickAppointmentsTab(ActionEvent actionEvent) {
        //Load Appointments
        mainPane.setCenter(SceneManager.getSceneRoot("appointmentView"));
    }

    public void clickCalenderTab(ActionEvent actionEvent) {
    }

    public void clickReportTab(ActionEvent actionEvent) {
    }
}
