package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import main.scenemanager.SceneManager;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public BorderPane mainPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainPane.setCenter(SceneManager.getSceneRoot("customerView"));
    }
}
