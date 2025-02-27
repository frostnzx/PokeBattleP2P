package gui;

import game.GameSystem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import net.Peer;
import net.PeerClient;

public class JoinGameScene {
	private Scene scene ; 
	private SceneManager sceneManager ; 
	private PeerClient client ;
	
	public JoinGameScene(SceneManager sceneManager) {
		this.sceneManager = sceneManager ; 
		
		// create gui
		GridPane root = new GridPane();
		Label ipLabel = new Label("IP : ");
		TextField ipTextField = new TextField();
		Label portLabel = new Label("PORT : ");
		TextField portTextField = new TextField();
		
		root.addRow(0 , ipLabel , ipTextField);
		root.addRow(1 , portLabel , portTextField);
		
		Button button = new Button("JOIN");
		button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				// create client
				String ipAddress = ipTextField.getText().trim();
				int port = Integer.parseInt(portTextField.getText());
				client = new PeerClient(ipAddress , port);
				client.start();
				
				// game system logic
				GameSystem.getInstance().setMyPeer(client);
				// ...
				// ...
				
				// open new scene (gui)
				// ...
				// ...
			}
		});
		root.addRow(2, button);
		this.scene = new Scene(root , 300 , 250);
	}
	
	public Scene getScene() {
		return this.scene ; 
	}
}
