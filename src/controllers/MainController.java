package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import main.scenemanager.SceneManager;
import models.Appointment;
import models.Customer;
import services.*;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public BorderPane mainPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Initialize Main Scene with Appointment View
        mainPane.setCenter(SceneManager.getSceneRoot("calendarView"));

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
        //Update Scene Data before switching scenes
        ((CalendarController)SceneManager.getScene("calendarView").getController()).refreshData();
        mainPane.setCenter(SceneManager.getSceneRoot("calendarView"));
    }

    public void clickReportTab(ActionEvent actionEvent) {
    }

    public void checkForAppointment(){
        System.out.println("Check For Appoitnment");
        AppointmentService appointmentService = (AppointmentService) ServiceFactory.getService(new AppointmentServiceFactory());
        CustomerService customerService = (CustomerService) ServiceFactory.getService(new CustomerServiceFactory());
        List<Appointment> consultantAppointments = appointmentService.getAppointmentsByConsultant(UserService.getSessionUser().getId());
        System.out.println(consultantAppointments.size());
        /*
         * Filters a list of consultant appointments if an appointment is within 15 minutes.  A lamda expression is used here to simplify the
         * expression.  Otherwise would have had to loop through the list and build up a List of appointments.
         */
        Optional<Appointment> appointment = consultantAppointments.stream().filter(item->{
            //Get current time and add 15 minutes
            LocalDateTime ldt = LocalDateTime.now().plusMinutes(15);
            System.out.println(ldt);
            System.out.println(item.getEnd().isAfter(LocalDateTime.now().atZone(ZoneId.systemDefault())));
            //Ensure Appointment date is after current time and before 15 minutes from now.
            return item.getEnd().isAfter(LocalDateTime.now().atZone(ZoneId.systemDefault())) && item.getStart().isBefore(ldt.atZone(ZoneId.systemDefault()));
        }).findFirst();

        if(appointment.isPresent()){
            Appointment app = appointment.get();
            //Calculate the number a minutes until the appointment.
            long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(),app.getStart());
            Customer customer = customerService.getItem(app.getCustomer());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Upcoming appointment with " + customer.getName() + " in " + minutes + " minutes.");
            alert.showAndWait();
        }
    }
}
