package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Appointment;
import models.IModel;
import services.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {

    @FXML
    public TableView<IModel> appointmentTable;
    @FXML
    public TableColumn<Appointment,Integer> appointmentId;
    @FXML
    public TableColumn<Appointment,Integer> consultant;
    @FXML
    public TableColumn<Appointment,Integer> customer;
    @FXML
    public TableColumn<Appointment,String> title;
    @FXML
    public TableColumn<Appointment,String> desc;
    @FXML
    public TableColumn<Appointment,String> type;
    @FXML
    public TableColumn<Appointment, ZonedDateTime> start;
    @FXML
    public TableColumn<Appointment,ZonedDateTime> end;

    AppointmentService service;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = (AppointmentService) ServiceFactory.getService(new AppointmentServiceFactory());
        setUpTable();
        updateAppointments();
    }

    private void updateAppointments(){
        try {
            service.refreshData();
            appointmentTable.setItems(FXCollections.observableArrayList(service.getData().values()));
        } catch (SQLException throwables) {
            //TODO Add error messages to UI
            throwables.printStackTrace();
        }
    }

    private void setUpTable(){
        appointmentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        desc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
        consultant.setCellValueFactory(new PropertyValueFactory<>("user"));
        customer.setCellValueFactory(new PropertyValueFactory<>("customer"));

        //Set Format for Start and End Times.
        start.setCellFactory(column-> new LocalTimeCell());
        end.setCellFactory(column-> new LocalTimeCell());
//        end.setCellFactory(column-> new LocalTimeCell());
        //Setup Consultant cell to display Name and not ID
        consultant.setCellFactory((column)->{
            return new TableCell<Appointment,Integer>(){
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if(!empty) {
                        ConsultantService service = (ConsultantService) ServiceFactory.getService(new ConsultantServiceFactory());
                        setText(service.getItem(item).getName());
                    }
                }
            };
        });

        //Setup Customer Cell as a link to customer.
        customer.setCellFactory(column->new ModelLink());
    }

    /**
     * Displays ZonedDateTime in a more readable way.
     */
    private class LocalTimeCell extends TableCell<Appointment,ZonedDateTime>{
        @Override
        protected void updateItem(ZonedDateTime item, boolean empty) {
            super.updateItem(item, empty);

            if (!empty) {
                setText(item.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
            }
        }
    }

    private class ModelLink extends TableCell<Appointment,Integer>{

        private final Hyperlink link;
        private IModel model;

        public ModelLink(){

            link = new Hyperlink();

            link.setOnAction((evt)->{
                System.out.println("Clicked Link");
            });
        }

        @Override
        protected void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);

            if(!empty){
                CustomerService service = (CustomerService) ServiceFactory.getService(new CustomerServiceFactory());
                link.setText(service.getItem(item).getName());
                setGraphic(link);
            }
        }
    }
}
