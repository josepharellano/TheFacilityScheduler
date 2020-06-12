package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Address;
import models.Customer;
import services.AddressService;
import services.CustomerService;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerDialogueController implements Initializable {

    //New Customer input values.
    @FXML
    public TextField customerName;
    @FXML
    public TextField address;
    @FXML
    public TextField addressLine;
    @FXML
    public TextField postalCode;
    @FXML
    public TextField telephone;
    @FXML
    public ComboBox<Address.City> citiesCBox;
    @FXML
    private ComboBox<Address.Country> countriesCBox;
    @FXML
    private Button saveBtn;

    private CustomerService custService; //Handles business logic for customers
    private AddressService addrService; //Handles Addresses

    private Customer customer; //Customer being edited.

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        custService = CustomerService.getInstance();
        addrService = AddressService.getInstance();

        //Set combobox Items
        citiesCBox.setItems(FXCollections.observableArrayList(addrService.getCities()));
        countriesCBox.setItems(FXCollections.observableArrayList(addrService.getCountries()));

        //TODO Add listener to telephone textField to ensure only numbers are entered and text is formated as xxx-xxx-xxxx


    }
    //TODO Add Validation Status updates.
    public void onAddCustomer(ActionEvent action) {

        Address newAddress = new Address(address.getText(),addressLine.getText(),postalCode.getText(),telephone.getText(),
                            citiesCBox.getSelectionModel().getSelectedItem(), countriesCBox.getSelectionModel().getSelectedItem());

        Customer customer = new Customer(customerName.getText(),newAddress);

        try {
           custService.addCustomer(customer);
           onClose(action);
        } catch (CustomerService.EmptyInputValue | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void onEditCustomer(ActionEvent action) {
        //Set customer fields based on edits
        customer.setName(customerName.getText());
        customer.getAddress().setAddress(address.getText());
        customer.getAddress().setAddressline(addressLine.getText());
        customer.getAddress().setPostalCode(postalCode.getText());
        customer.getAddress().setPhone(telephone.getText());
        customer.getAddress().setCity(citiesCBox.getSelectionModel().getSelectedItem());
        customer.getAddress().setCountry(countriesCBox.getSelectionModel().getSelectedItem());

        try {
            custService.updateCustomer(customer);
        } catch (CustomerService.EmptyInputValue | SQLException ex) {
            System.out.println(ex.getMessage());
        }
        onClose(action);
    }
    /**
     * Closes the dialogue.
     * @param actionEvent
     */
    public void onClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void setEditDialogue(Customer customer){
        this.customer = customer;

        //Populate Fields based on selected customer
        customerName.setText(customer.getName());
        address.setText(customer.getAddress().getAddress());
        addressLine.setText(customer.getAddress().getAddressLine());
        postalCode.setText(customer.getAddress().getPostalCode());
        telephone.setText(customer.getAddress().getPhone());
        citiesCBox.getSelectionModel().select(customer.getAddress().getCity());
        countriesCBox.getSelectionModel().select(customer.getAddress().getCountry());

        //Set Save button label to Edit
        saveBtn.setText("Save");
        //Set on action method to OnEditCustomer
        saveBtn.setOnAction(this::onEditCustomer);
    }



}
