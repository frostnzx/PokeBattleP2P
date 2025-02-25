package gui;

import net.PeerServer;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class CreateGameScene {
	private Scene scene ; 
	private SceneManager sceneManager ; 
	private PeerServer server ; 
	
	public CreateGameScene(SceneManager sceneManager) {
		this.sceneManager = sceneManager ; 
		
		// create gui
		GridPane root = new GridPane();
		Text text = new Text("Waiting for someone to join...");
		root.add(text, 0, 0);
		this.scene = new Scene(root , 300 , 250);
		
		// create server
		server = new PeerServer();
		server.start();
	}
	
	public Scene getScene() {
		return this.scene ; 
	}
}
