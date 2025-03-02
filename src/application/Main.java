package application;

import game.GameSystem;
import gui.SceneManager;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage; 

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("PokeBattleP2P");
        
        // initialize important application controller
        SceneManager sceneManager = new SceneManager(primaryStage);
        GameSystem.getInstance() ; 
        
        
        // Set default pokemon team & items
        // ...

        
        sceneManager.showMainMenu();
    }
    public static void main(String[] args) {
        launch(args); // Start javafx GUI
        
    }
}
