package gui;

import net.PeerServer;
import game.GameSystem;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class CreateGameScene {
	private Scene scene;
	private SceneManager sceneManager;
	private PeerServer server;

	public CreateGameScene(SceneManager sceneManager) {
		// create server
		server = new PeerServer();
		server.start();
		GameSystem.getInstance().setMyPeer(server);

		this.sceneManager = sceneManager;

		// Create root container
		StackPane root = new StackPane();

		// Create VBox for centering
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER); // Center content inside VBox

		Font pixelFont = Font.loadFont(getClass().getResourceAsStream("/fonts/PixelifySans-VariableFont_wght.ttf"), 30);
		Font pixelFont2 = Font.loadFont(getClass().getResourceAsStream("/fonts/PixelifySans-VariableFont_wght.ttf"), 25);

		// Create the text
		Text text = new Text("Waiting for someone to join...");
		Text Ip = new Text("Your ip is: " + server.getIp());
		Text Port = new Text("Your port is: " + server.getPort());
		text.setFont(pixelFont);
		Ip.setFont(pixelFont);
		Port.setFont(pixelFont);

		HBox hBox = new HBox(20);
		hBox.setAlignment(Pos.CENTER);
		
		Text menuText = new Text("MENU");
		menuText.setFont(pixelFont2);
		menuText.setFill(Color.WHITE);

		Text startText = new Text("START");
		startText.setFont(pixelFont2);
		startText.setFill(Color.WHITE);
		
		Button startButton = new Button();
        startButton.setGraphic(startText);
        startButton.getStyleClass().add("start_button");
//        startButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 20px; -fx-background-radius: 15; -fx-padding: 10 20;");
//        startButton.setOnMouseEntered(event -> {
//		    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), startButton);
//		    scaleTransition.setToX(1.1); // Scale up by 10% in the X direction
//		    scaleTransition.setToY(1.1); // Scale up by 10% in the Y direction
//		    scaleTransition.play();
//		});
//
//		startButton.setOnMouseExited(event -> {
//		    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), startButton);
//		    scaleTransition.setToX(1); // Reset to original scale
//		    scaleTransition.setToY(1);
//		    scaleTransition.play();
//		});
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            	//show Battle scene
            	//If opponent join it can click this button
            	        	
          
            }
        });
        
        
        Button menuButton = new Button();
        menuButton.setGraphic(menuText);
        menuButton.getStyleClass().add("menu_button");
        
//        menuButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 20px; -fx-background-radius: 15; -fx-padding: 10 20;");
//        menuButton.setOnMouseEntered(event -> {
//		    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), menuButton);
//		    scaleTransition.setToX(1.1); // Scale up by 10% in the X direction
//		    scaleTransition.setToY(1.1); // Scale up by 10% in the Y direction
//		    scaleTransition.play();
//		});
//
//		menuButton.setOnMouseExited(event -> {
//		    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), menuButton);
//		    scaleTransition.setToX(1); // Reset to original scale
//		    scaleTransition.setToY(1);
//		    scaleTransition.play();
//		});
		
		menuButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
            	 applySceneTransition(() -> sceneManager.showMainMenu());            	
            }
        });
        
        hBox.getChildren().addAll(menuButton, startButton);
        
        hBox.setTranslateY(20);

		// Add text to VBox
		vbox.getChildren().addAll(text, Ip, Port, hBox);

		// Add VBox to StackPane
		root.getChildren().add(vbox);

		// Create scene
		this.scene = new Scene(root, 800, 600);
		
		scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), text);
		fadeTransition.setFromValue(1.0); // Fully visible
		fadeTransition.setToValue(0.0); // Fully transparent
		fadeTransition.setCycleCount(FadeTransition.INDEFINITE); // Repeat indefinitely
		fadeTransition.setAutoReverse(true); // Reverse the animation (fade in and out)
		fadeTransition.play(); // Start the animation

		// create gui
		// GridPane root = new GridPane();
//		Text text = new Text("Waiting for someone to join...");
//		root.add(text, 0, 0);
//		this.scene = new Scene(root, 300, 250);

		// write some logic that if server got some client , it will open new scene
		// (battle scene)
		// + some game system logic (game start or some shit)
	}
	
	private void applySceneTransition(Runnable sceneSwitch) {
        // Apply fade out transition on the current scene
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.2), scene.getRoot());
        fadeOut.setFromValue(1.0); // Fully visible
        fadeOut.setToValue(0.0); // Fully transparent
        fadeOut.setOnFinished(event -> {
            // Once fade-out is complete, switch scenes
            sceneSwitch.run();
            // Apply fade in transition on the new scene
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.2), scene.getRoot());
            fadeIn.setFromValue(0.0); // Fully transparent
            fadeIn.setToValue(1.0); // Fully visible
            fadeIn.play();
        });
        fadeOut.play();
    }

	public Scene getScene() {
		return this.scene;
	}
}
