package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Appointment;
import models.IModel;
import services.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {

    public TableView<Appointment> appointmentTable;
    public TableColumn<Appointment,Integer> appointmentId;
    public TableColumn<Appointment,Integer> consultant;
    public TableColumn<Appointment,Integer> customer;
    public TableColumn<Appointment,String> title;
    public TableColumn<Appointment,String> desc;
    public TableColumn<Appointment,String> type;
    public TableColumn<Appointment, ZonedDateTime> start;
    public TableColumn<Appointment,ZonedDateTime> end;

    AppointmentService service;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = (AppointmentService) ServiceFactory.getService(new AppointmentServiceFactory());
        setUpTable();
        updateAppointments();
    }

    /**
     * Update Appointments from the cache
     */
    private void updateAppointments(){
        appointmentTable.setItems(FXCollections.observableArrayList(service.getData().values()));
        appointmentTable.refresh();
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
        /**
         *      Setup Consultant cell to display Name and not ID A lambda expression is used here so I do not have to create another class in order
         *      to change how the item is displayed when it is updated.  Unlike the start and end cells in which I created a LocalTimeCell class to handle those.
         */
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
        customer.setCellFactory(column-> new ModelLink());
    }

    public void onAddAppointment(ActionEvent actionEvent) {
        try {
            showEditAddAppointmentDialogue(false,null);
            updateAppointments();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onEditAppointment(ActionEvent actionEvent) throws IOException {
        Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();

        if(appointment != null) {
            showEditAddAppointmentDialogue(true, appointment);
            //Refresh stale data
            updateAppointments();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Must select an appointment to edit first.");
            alert.show();
        }
    }

    public void onRemoveAppointment(ActionEvent actionEvent) throws SQLException {
        Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();

        if(appointment != null){
            service.removeAppointment(appointment);
            updateAppointments();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Must choose an appointment to remove");
            alert.showAndWait();
        }
    }

    public void onRefreshData(ActionEvent actionEvent) {
    }

    private void showEditAddAppointmentDialogue(boolean edit, Appointment app) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/EditAppointmentView.fxml"));
        Parent parent = fxmlLoader.load();

        //If edit is true then setup edit dialogue
        if (edit) {
            EditAppointmentController controller = fxmlLoader.getController();
            controller.setAppointmentEditValues(app);
        }
        Scene scene = new Scene(parent, 400,600);
        //Set stylesheets for the dialogue
        scene.getStylesheets().add("/css/globalStyles.css");
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Add/Edit Appointment");
        stage.showAndWait();
    }

    /**
     * Displays ZonedDateTime in a more readable way.
     */
    private static class LocalTimeCell extends TableCell<Appointment,ZonedDateTime>{
        @Override
        protected void updateItem(ZonedDateTime item, boolean empty) {
            super.updateItem(item, empty);

            if (!empty) {
                setText(item.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
            }
        }
    }

    private static class ModelLink extends TableCell<Appointment,Integer>{

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
