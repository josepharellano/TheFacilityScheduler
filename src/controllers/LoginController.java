package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.scenemanager.SceneManager;
import services.UserService;
import utilities.Exceptions;

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

    private String invalidPassword; //Localized error Message for invalid password
    private String invalidUserName; //Localized error message for invalid username


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userService = UserService.getInstance();

        invalidPassword = resources.getString("invalid_password");
        invalidUserName = resources.getString("invalid_username");
    }

    public void onLogin(ActionEvent event){
        try {
            userService.login(username.getText(), password.getText());

            //If successful load next screen.
            SceneManager.setScene("mainView");

        }catch(Exceptions.InvalidUserNameEx ex){
            errorStatus.setText(invalidUserName);
        }catch(Exceptions.InvalidPasswordEx ex){
            errorStatus.setText(invalidPassword);
        } catch (SceneManager.MissingScene missingScene) {
            missingScene.printStackTrace();
        }
    }

}
