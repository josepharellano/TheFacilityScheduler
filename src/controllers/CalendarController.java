package controllers;

import customcontrols.WeekTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import models.Appointment;
import services.*;
import sun.security.provider.ConfigFile;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.IsoFields;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class CalendarController implements Initializable {

    public TableView<Appointment> calendarTable;
    public TableColumn<Appointment, ZonedDateTime> appointmentDateCol;
    public TableColumn<Appointment,String> consultantNameCol;
    public TableColumn<Appointment,String> customerNameCol;
    public TableColumn<Appointment,String> typeCol;
    public TableColumn<Appointment,ZonedDateTime> startTimeCol;
    public TableColumn<Appointment,ZonedDateTime> endTimeCol;
    public TableColumn<Appointment,String> descriptionCol;
    public ComboBox<Month> monthsComboBox;
    public WeekTextField weekTextField;
    public Label calendarTitle;


    private FilteredList<Appointment> appointments;
    private AppointmentService appointmentService;
    private CustomerService customerService;
    private ConsultantService consultantService;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Instantiate Services
        appointmentService = (AppointmentService) ServiceFactory.getService(new AppointmentServiceFactory());
        customerService = (CustomerService) ServiceFactory.getService(new CustomerServiceFactory());
        consultantService = (ConsultantService) ServiceFactory.getService(new ConsultantServiceFactory());


        //Get all appointments and wrap in a FilteredList
        appointments = new FilteredList<Appointment>(FXCollections.observableArrayList(appointmentService.getData().values()));
        //Sets Up the Table View
        setUpTable();

        //Populate Month ComboBox
        monthsComboBox.setItems(FXCollections.observableArrayList(Month.values()));

        //Set Callback on WeekTextField
        weekTextField.setCallBack(this::filterByWeek);

        calendarTitle.setText("All Appointments");

    }

    private void setUpTable(){
        //Set Table List Items to appointments
        calendarTable.setItems(appointments);
        appointmentDateCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        //Lambda expression used here that formats the value belonging to the TableView column into a readable Date format.
        appointmentDateCol.setCellFactory(item-> {
            return new TableCell<Appointment,ZonedDateTime>() {
            final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("E,MM-dd-yyyy");
                @Override
                protected void updateItem(ZonedDateTime item, boolean empty) {
                    super.updateItem(item, empty);
                    if (Objects.nonNull(item)) {
                        if (!empty) {
                            setText(dateFormat.format(item.toLocalDate()));
                        } else setText("");
                    }
                }
            };
        });
        //Sets consultantNameCol to the name of the appointments consultant.
        consultantNameCol.setCellValueFactory(item-> {
            int consultantId = item.getValue().getUser();
            //Retrieve consultant name from service.
            String consultantName = consultantService.getItem(consultantId).getName();
            return new SimpleStringProperty(consultantName);
        });
        //Sets customerNameCol to the name of the appointment's customer.
        customerNameCol.setCellValueFactory(item->{
            int customerId = item.getValue().getCustomer();
            String customerName = customerService.getItem(customerId).getName();
            return new SimpleStringProperty(customerName);
        });
        //Sets typeCol to the type of the appointment
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));


        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));

        /*
         * Cell Factory that formats the display text of the Start and End Time as Hours:Minutes AM/PM
         */
        Callback<TableColumn<Appointment,ZonedDateTime>,TableCell<Appointment,ZonedDateTime>> timeCellFactory = new Callback<TableColumn<Appointment, ZonedDateTime>, TableCell<Appointment, ZonedDateTime>>() {
            @Override
            public TableCell<Appointment, ZonedDateTime> call(TableColumn<Appointment, ZonedDateTime> param) {
                return new TableCell<Appointment, ZonedDateTime>() {
                    final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm a");
                    @Override
                    protected void updateItem(ZonedDateTime item, boolean empty) {
                        super.updateItem(item, empty);
                        if (Objects.nonNull(item)) {
                            if (!empty) {
                                setText(item.format(timeFormat));
                            } else setText("");
                        }
                    }
                };
            }
        };
        //Set Cell Factories
        startTimeCol.setCellFactory(timeCellFactory);
        endTimeCol.setCellFactory(timeCellFactory);
        
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("desc"));

    }

    /**
     * Takes a LocalDateTime and formats it into a time string.
     * @param time LocalDateTime object to extract time information from.
     * @return String
     */
    private String formatTime(LocalDateTime time){
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("KK:mm a");
        return timeFormat.format(time);
    };
    //Filters by selectedMonth
    public void filterByMonth(ActionEvent actionEvent) {
        calendarTable.refresh();
        appointments.setPredicate(appointment ->{
            Month month = appointment.getStart().getMonth();
            return month.equals(monthsComboBox.getValue());
        });

        Month month = monthsComboBox.getSelectionModel().getSelectedItem();
        //Check to ensure a selection in the comboBox has been made.
        if(Objects.nonNull(month)) calendarTitle.setText(monthsComboBox.getSelectionModel().getSelectedItem().getDisplayName(TextStyle.FULL,Locale.getDefault())+" Appointments");
    }

    //Filters by selectedWeek
    public void filterByWeek(int week) {
        calendarTable.refresh();
        appointments.setPredicate(appointment -> {
            ZonedDateTime utcTime = appointment.getStart().withZoneSameInstant(ZoneId.of("UTC"));
            int appointmentWeek = utcTime.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
            return appointmentWeek == week;
        });

        calendarTitle.setText("Week " + week + " Appointments");
    }

    //Removes filters from calendar data.
    public void clearFilter() {
        monthsComboBox.getSelectionModel().clearSelection();
        weekTextField.setText("");
        calendarTable.refresh();
        calendarTitle.setText("All Appointments");
        appointments.setPredicate(null);
    }


    //Refreshes data from the Service Cache
    public void refreshData(){
        appointments = new FilteredList<Appointment>(FXCollections.observableArrayList(appointmentService.getData().values()));
        calendarTable.setItems(appointments);
        calendarTable.refresh();

        monthsComboBox.getSelectionModel().clearSelection();
        clearFilter();

    }

}
