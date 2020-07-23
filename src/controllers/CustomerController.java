package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Customer;
import services.AddressService;
import services.CustomerService;
import services.CustomerServiceFactory;
import services.ServiceFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    @FXML
    private TableView<Customer> customerTable; // Displays Customers

    //Columns of the TableView
    @FXML
    private TableColumn<Customer,Integer> customerId;
    @FXML
    private TableColumn<Customer,String> customerName;
    @FXML
    private TableColumn<Customer,String> telephone;
    @FXML
    private TableColumn<Customer,String> address;
    @FXML
    private TableColumn<Customer,String> addressLine;
    @FXML
    private TableColumn<Customer,String> city;
    @FXML
    private TableColumn<Customer,String> country;
    @FXML
    private TableColumn<Customer,String> postalCode;

    @FXML
    private Text lastUpdate;    //TimeStamp of last update from database
    @FXML
    private Text statusMessages; //Status Message Updates.

    private CustomerService service; //Customer Business Logic
    private AddressService addrService; //Address

    private ObservableList<Customer> customers;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = (CustomerService) ServiceFactory.getService(new CustomerServiceFactory());
        customers = FXCollections.observableArrayList(service.getData().values());
        setUpCustomerTable(); //Sets up Customer Table
        updateCustomerData();//Populate Table Data
    }

    /**
     * Setup the Customer Table View
     */
    private void setUpCustomerTable(){
        System.out.println(customers);
        customerTable.setItems(customers);

        //Wire columns to customer properties
        customerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        /*
         * Lambda expressions are needed on the following in order to retrieve the properties of the nested Address Object.
         * setCellValueFactory takes a callback of CellDataFeatures<Customer,String> and returns ObservableValue
         */
        telephone.setCellValueFactory(addressData -> new SimpleStringProperty(addressData.getValue().getAddress().getPhone()));
        address.setCellValueFactory(addressData -> new SimpleStringProperty(addressData.getValue().getAddress().getAddress()));
        addressLine.setCellValueFactory(addressData -> new SimpleStringProperty(addressData.getValue().getAddress().getAddressLine()));
        city.setCellValueFactory(addressData -> new SimpleStringProperty(addressData.getValue().getAddress().getCity().getName()));
        country.setCellValueFactory(addressData -> new SimpleStringProperty(addressData.getValue().getAddress().getCity().getCountry().getName()));
        postalCode.setCellValueFactory(addressData -> new SimpleStringProperty(addressData.getValue().getAddress().getPostalCode()));

    }

    /**
     * Updates the Customer data from the cache.
     */
    private void updateCustomerData(){
            customers = FXCollections.observableArrayList(service.getData().values());
            customerTable.setItems(customers);
            customerTable.refresh();
    }

    /**
     * Updates customer data from the database
     * @param actionEvent
     */
    public void onRefresh(ActionEvent actionEvent) {
        //TODO Add a time limit so database doesn't get spammed by refresh.
        try {
            service.refreshData();
            this.updateCustomerData();
            //TODO Make Time equal local time as hours not military time.
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm a");
            lastUpdate.setText("Last Updated:   " + currentDateTime.format(formatter));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Removes Customer from database.
     * @param actionEvent
     */
    public void onRemove(ActionEvent actionEvent) {
        Customer selected = customerTable.getSelectionModel().getSelectedItem();

        //Try to remove selected customer from database.
        try {
            if(service.removeCustomer(selected.getId())){
                updateCustomerData();
                statusMessages.setText("Customer Removed");
            }else{
                statusMessages.setText("Failed to Remove Customer");
            }
        } catch (CustomerService.AppointmentConstraint appointmentConstraint) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Customer is currently assigned to an appointment.  Delete appointment to remove this customer.");
            alert.show();
        }

    }

    public void onAddCustomer(ActionEvent actionEvent) throws IOException {
        showCustomerDialogue(false,null);
        //Refresh stale data
        updateCustomerData();

        //Status Message
        statusMessages.setText("Customer Added");
    }

    public void onEditCustomer(ActionEvent actionEvent) throws IOException {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();

        if(customer != null) {
            showCustomerDialogue(true, customer);
            //Refresh stale data
            updateCustomerData();

            //Set Status Message
            statusMessages.setText("Customer Edited");
        }else statusMessages.setText("Must Select a Customer to Edit");
    }

    private void showCustomerDialogue(boolean edit, Customer customer) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/CustomerDialogue.fxml"));
        Parent parent = fxmlLoader.load();

        //If edit is true then setup edit dialogue
        if (edit) {
            CustomerDialogueController controller = fxmlLoader.getController();
            controller.setEditDialogue(customer);
        }
        Scene scene = new Scene(parent, 400,500);
        //Set stylesheets for the dialogue
        scene.getStylesheets().add("/css/globalStyles.css");
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();

    }
}
