package gui;

import net.PeerServer;

import java.io.File;

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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
		GameSystem.getInstance().getMyPlayer().setFilePath("res/Trainers/trainer6.png");
		// for closing the server
		sceneManager.getStage().setOnCloseRequest(event -> {
			GameSystem.getInstance().getMyPeer().close();
		});

		this.sceneManager = sceneManager;
		String soundFile = "res/button_sound.mp3";
		Media buttonSound = new Media(new File(soundFile).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);

		// Create root container
		StackPane root = new StackPane();

		// Create VBox for centering
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER); // Center content inside VBox

		Font pixelFont = Font.loadFont(getClass().getResourceAsStream("/fonts/PixelifySans-VariableFont_wght.ttf"), 30);
		Font pixelFont2 = Font.loadFont(getClass().getResourceAsStream("/fonts/PixelifySans-VariableFont_wght.ttf"),
				25);

		// Create the text
		Text text = new Text("Waiting for someone to join...");
		Text Ip = new Text("Your ip is: " + server.getIp());
		Text Port = new Text("Your port is: " + server.getPort());
		text.setFont(pixelFont);
		Ip.setFont(pixelFont);
		Port.setFont(pixelFont);

		// for UI updating
		server.setOnClientConnectedListener(client -> {
			Platform.runLater(() -> {
				text.setText("Someone has joined, click ready to start the game!");
				text.setFill(Color.GREEN);
			});
		});

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
		startButton.setOnMouseEntered(event -> {
			mediaPlayer.stop();
			mediaPlayer.seek(Duration.ZERO);
			mediaPlayer.play();
		});

		startButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				// show Battle scene
				// If opponent join it can click this button
				// show start Battle then show Battle scene
				// If opponent join it can click this button
				// maybe disable this button before opponent join ?
				GameSystem.getInstance().startBattle();
			}
		});

		Button menuButton = new Button();
		menuButton.setGraphic(menuText);
		menuButton.getStyleClass().add("menu_button");
		menuButton.setOnMouseEntered(event -> {
			mediaPlayer.stop();
			mediaPlayer.seek(Duration.ZERO);
			mediaPlayer.play();
		});

		menuButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				GameSystem.getInstance().getMyPeer().close();
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
