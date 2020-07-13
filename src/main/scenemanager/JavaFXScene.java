package main.scenemanager;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.ResourceBundle;

public class JavaFXScene<T> {

    private final String name; //Name of scene
    private final String fxmlFileName; //Scene File name
    private T controller; //Scene controller
    SceneShutdownI shutdown = ()->{}; //Lambda that is called when the scene is unloaded.
    ResourceBundle rb; //Resource Bundle of the Scene

    private Pane root; //Root node of the Scene

    public JavaFXScene(String name, String fxmlFileName){
        this.name = name;
        this.fxmlFileName = fxmlFileName;
    }

    public JavaFXScene(String name, String fxmlFileName, SceneShutdownI shutdown){
        this.name = name;
        this.fxmlFileName = fxmlFileName;
        this.shutdown = shutdown;
    }

    //Getters
    public String getFxmlFileName() {
        return this.fxmlFileName;
    }
    public T getController() {
        return this.controller;
    }
    public Pane getRoot() {
        return this.root;
    }
    public String getName() {
        return this.name;
    }
    public ResourceBundle getResourceBundle() {
        return rb;
    }

    //Setters
    public void setController(T controller) {
        this.controller = controller;
    }
    public void setRoot(Pane root) {
        this.root = root;
    }
    public void setResourceBundle(ResourceBundle rb) {
        this.rb = rb;
    }

    //Call provided special shutdown procedures for a scene.
    public void Shutdown(){
        shutdown.shutdown();
    }
}
