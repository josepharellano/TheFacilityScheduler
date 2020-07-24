package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.scenemanager.SceneManager;
import models.Appointment;
import models.Customer;
import models.IModel;
import services.CustomerService;
import services.CustomerServiceFactory;
import services.ServiceFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import utilities.Constants;
import utilities.Constants.*;

public class EditAppointmentController implements Initializable {

    public AutoCompleteTextField<Customer> customers; //List of available customers to setup appointments for
    public TextField titleField;
    public TextField typeField;
    public DatePicker startDate;
    public DatePicker endDate;

    public Spinner<Integer> endHourTimeSpinner;
    public Spinner<Integer> endMinuteTimeSpinner;
    public Spinner<String> endAmPmTimeSpinner;
    public Spinner<String> startAmPmTimeSpinner;
    public Spinner<Integer> startMinuteTimeSpinner;
    public Spinner<Integer> startHourTimeSpinner;
    public TextField descriptionField;
    public Button saveBtn;
    public TextField locationField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CustomerService customerService = (CustomerService) ServiceFactory.getService(new CustomerServiceFactory());

        setUpTimeSpinners();

        customers.setAutoFillItems(new ArrayList<Customer>(customerService.getData().values()));
    }

    /**
     * Populate Edit Fields from selected Appointment
     * @param appointment to be edited
     */
    public void setAppointmentEditValues(Appointment appointment){
        titleField.setText(appointment.getTitle());
        typeField.setText(appointment.getType());
        descriptionField.setText(appointment.getDesc());
        locationField.setText(appointment.getLocation());

        CustomerService customerService = (CustomerService) ServiceFactory.getService(new CustomerServiceFactory());
        Customer customer = customerService.getItem(appointment.getCustomer());

        LocalDateTime start = appointment.getStart().toLocalDateTime();
        LocalDateTime end = appointment.getEnd().toLocalDateTime();

        customers.setSelection(customer);
        startDate.setValue(start.toLocalDate());
        endDate.setValue(end.toLocalDate());
//        endHourTimeSpinner.increment();
        System.out.println(start.getHour());
        startHourTimeSpinner.increment(calculateHourFromMilitary(start.getHour()));
        endHourTimeSpinner.increment(calculateHourFromMilitary(end.getHour()));
        startMinuteTimeSpinner.increment(start.getMinute()/ Constants.APPOINT_MINUTE_INCREMENTS);
        endMinuteTimeSpinner.increment(end.getMinute()/Constants.APPOINT_MINUTE_INCREMENTS);

        if(start.getHour() > 12){
            startAmPmTimeSpinner.increment();
        }else{
            startAmPmTimeSpinner.decrement();
        }
        if(end.getHour() > 12){
            endAmPmTimeSpinner.increment();
        }else{
            endAmPmTimeSpinner.decrement();
        }

    }

    public void onSaveAppointment(ActionEvent actionEvent) {
    }

    public void onClose(ActionEvent actionEvent) {
    }

    private void setUpTimeSpinners(){

        startHourTimeSpinner.setValueFactory(getHoursSpinnerFactory());
        endHourTimeSpinner.setValueFactory(getHoursSpinnerFactory());
        startMinuteTimeSpinner.setValueFactory(getMinuteSpinnerFactory());
        endMinuteTimeSpinner.setValueFactory(getMinuteSpinnerFactory());
        startAmPmTimeSpinner.setValueFactory(getAMPMSpinnerFactory());
        endAmPmTimeSpinner.setValueFactory(getAMPMSpinnerFactory());
    }

    /**
     * Takes an hour in military time and converts it into AM PM 12 hour time
     * @param hour
     * @return
     */
    private int calculateHourFromMilitary(int hour){
        if(hour > 12) return hour-12;
        if(hour == 0) return 12;
        return hour;
    }

    private SpinnerValueFactory<Integer> getHoursSpinnerFactory(){
        return new SpinnerValueFactory.IntegerSpinnerValueFactory(1,12,0,1);
    }

    private SpinnerValueFactory<Integer> getMinuteSpinnerFactory(){
        return new SpinnerValueFactory.IntegerSpinnerValueFactory(0,55,0,5);
    }

    private SpinnerValueFactory<String> getAMPMSpinnerFactory(){
        ObservableList<String> ampm = FXCollections.observableArrayList("AM","PM");
        return new SpinnerValueFactory.ListSpinnerValueFactory<>(ampm);
    }
}
