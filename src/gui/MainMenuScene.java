package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class MainMenuScene {
	private Scene scene ;
	private SceneManager sceneManager ; 
	
	public MainMenuScene(SceneManager sceneManager) {
		this.sceneManager = sceneManager ; 
		
		// create gui
        GridPane root = new GridPane();
        Button createBtn = new Button("Create");
        createBtn.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		sceneManager.showCreateGameScene();
        	}
        });
        Button joinBtn = new Button("Join");
        joinBtn.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		sceneManager.showJoinGameScene();
        	}
        });
        root.add(createBtn , 0 , 1);
        root.add(joinBtn, 0, 3);
        this.scene = new Scene(root , 300 , 250);
	}
	public Scene getScene() {
		return this.scene ; 
	}
}
