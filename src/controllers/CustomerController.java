package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import models.Customer;
import services.CustomerService;

import java.net.URL;
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




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = CustomerService.getInstance();

        setUpCustomerTable(); //Sets up Customer Table and populates data.
    }

    /**
     * Setup the Customer Table View
     */
    private void setUpCustomerTable(){

        //Update Customers data from Service
        customerTable.setItems(FXCollections.observableArrayList(service.getCustomers()));

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
        city.setCellValueFactory(addressData -> new SimpleStringProperty(addressData.getValue().getAddress().getCity()));
        country.setCellValueFactory(addressData -> new SimpleStringProperty(addressData.getValue().getAddress().getCountry()));
        postalCode.setCellValueFactory(addressData -> new SimpleStringProperty(addressData.getValue().getAddress().getPostalCode()));

        /*
         * Allows a columns of the table to resize at fixed ratios depending on table width.
         * A lambda is used in place of a Callback class for sake of simplicity.
         */
        customerTable.setColumnResizePolicy(resizeFeatures->{
            TableView<?> table = resizeFeatures.getTable(); //Customer Table View
            double tableWidth = table.getWidth();   //Width of Table
            List<TableColumn<?,?>> columns = new ArrayList<>(table.getColumns()); //Get list of Columns in TableView
            double baseWidth = tableWidth /columns.size();

            //Columns in table
            columns.get(0).setPrefWidth(tableWidth * .10); //Customer ID
            columns.get(1).setPrefWidth(tableWidth * .17); //Customer Name
            columns.get(2).setPrefWidth(tableWidth * .10); //Telephone
            columns.get(3).setPrefWidth(tableWidth * .16); //Address
            columns.get(4).setPrefWidth(tableWidth * .16); //Address Line
            columns.get(5).setPrefWidth(tableWidth * .10); //City
            columns.get(6).setPrefWidth(tableWidth * .10); //Country
            columns.get(7).setPrefWidth(tableWidth * .10); //Postal Code
            return true;
        });
    }

    /**
     * Updates the Customer data from the database.
     */
    private void updateCustomerData(){

        service.refreshCustomerList();
        //Set customerTable to observable list of customers from customer service.
        customerTable.setItems(FXCollections.observableArrayList(service.getCustomers()));

        //Set Last Update Time
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm a");
        lastUpdate.setText("Last Updated:   " + currentDateTime.format(formatter));
    }

    /**
     * Refresh stale Customer Data
     * @param actionEvent
     */
    public void onRefresh(ActionEvent actionEvent) {
        //TODO Add a time limit so database doesn't get spammed by refresh.
        updateCustomerData();
    }

    /**
     * Removes Customer from database.
     * @param actionEvent
     */
    public void onRemove(ActionEvent actionEvent) {
        Customer selected = customerTable.getSelectionModel().getSelectedItem();

        //Try to remove selected customer from database.
        if(service.removeCustomer(selected.getId())){

            //After remove - data becomes stale refresh data.
            updateCustomerData();
            statusMessages.setText("Customer Removed");
        }else{
            statusMessages.setText("Failed to Remove Customer");
        }

    }
}
