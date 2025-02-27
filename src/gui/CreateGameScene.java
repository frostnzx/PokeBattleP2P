package gui;

import net.PeerServer;
import game.GameSystem;
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
		GameSystem.getInstance().setMyPeer(server);
		
		
		// write some logic that if server got some client , it will open new scene (battle scene)
		// + some game system logic (game start or some shit)
	}
	
	public Scene getScene() {
		return this.scene ; 
	}
}
