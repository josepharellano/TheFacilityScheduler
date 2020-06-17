package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import services.UserService;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public TextField username;
    @FXML
    public TextField password;
    @FXML
    public Label errorStatus;


    UserService userService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userService = UserService.getInstance();
    }

    public void onLogin(ActionEvent event){
        try {
            userService.login(username.getText(), password.getText());

            //If successful load next screen.
            errorStatus.setText("Logged In");

        }catch(UserService.InvalidPasswordEx | UserService.InvalidUserNameEx ex){
            errorStatus.setText(ex.getMessage());
        }
    }
}
