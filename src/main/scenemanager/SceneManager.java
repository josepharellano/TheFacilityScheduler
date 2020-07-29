package main.scenemanager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class SceneManager<Controller> {

    private static HashMap<String, JavaFXScene<?>> scenes; //Holds list of scenes
    private static Stage primaryStage;
    private static Scene main; //Main Scene Wrapper

    static {
        SceneManager.scenes = new HashMap<>();
        SceneManager.main = new Scene(new Pane()); //Initialize Main Screen Root
    }

    /**
     * Loads in a scene to be displayed.
     * @param screen
     * @throws ExistingScene
     * @throws IOException
     */

    public static void loadSceneWithResource(JavaFXScene<?> screen) throws IOException, ExistingScene {
        if(!scenes.containsKey(screen.getName())){
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("../../views/" + screen.getFxmlFileName()),screen.getResourceBundle());
            Pane root = loader.load();
//            Scene scene = new Scene(root); //NEED TO FIX THIS CUSTOMER CONTROLLER DEPENDS ON THESE VALUES FREEZES

            screen.setRoot(root);
            screen.setController(loader.getController());

            SceneManager.scenes.put(screen.getName(),screen);
        }
        else throw new ExistingScene();
    }

    public static void loadScene(JavaFXScene<?> screen) throws IOException, ExistingScene {
        if(!scenes.containsKey(screen.getName())){
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("../../views/" + screen.getFxmlFileName()));
            Pane root = loader.load();

            screen.setRoot(root);
            screen.setController(loader.getController());

            SceneManager.scenes.put(screen.getName(),screen);
        }
        else throw new ExistingScene();
    }

    public static void removeScene(String name) throws MissingScene {
        if(SceneManager.scenes.containsKey(name)) {
            SceneManager.scenes.remove(name);
        }else throw new MissingScene();
    }

    public static void setScene(String name) throws MissingScene {
        //TODO: Add Shutdown Call
        System.out.println(SceneManager.primaryStage);
        if(SceneManager.scenes.containsKey(name)) {
            System.out.println("Load Scene " + name);
            //Set Main scene root to the requested scene root
            System.out.println(SceneManager.scenes.get(name).getRoot());
            SceneManager.main.setRoot(SceneManager.scenes.get(name).getRoot());
//            SceneManager.primaryStage.setScene(scenes.get(name).getScene());
        }else throw new MissingScene();
    }

    public static void showStage(){
        primaryStage.show();
    }

    //Setters
    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
        //Set Main Scene on stage
        primaryStage.setScene(main);
    }
    //Getters
    public static Pane getSceneRoot(String key){
        return scenes.get(key).getRoot();
    }
    public static JavaFXScene<?> getScene(String key){
        return scenes.get(key);
    }

    //Exceptions
    public static class MissingScene extends Exception{
        public MissingScene(){
            super("Scene not loaded!");
        }
    }
    public static class ExistingScene extends Exception{
        public ExistingScene(){
            super("Scene by that name has already been loaded!");
        }
    }


}
