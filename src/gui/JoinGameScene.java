package gui;

import java.io.File;

import game.GameSystem;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import net.Peer;
import net.PeerClient;

public class JoinGameScene {
	private Scene scene;
	private SceneManager sceneManager;
	private PeerClient client;

	public JoinGameScene(SceneManager sceneManager) {

		this.sceneManager = sceneManager;

		StackPane root = new StackPane();

		// Load and set background image
		Image backgroundImage = new Image("joingamescene.png"); // Ensure correct path
		ImageView backgroundImageView = new ImageView(backgroundImage);
		backgroundImageView.setFitWidth(800);
		backgroundImageView.setFitHeight(600);
		backgroundImageView.setPreserveRatio(false);

		// Custom Font
		Font pixelFont = Font.loadFont(getClass().getResourceAsStream("/fonts/PixelifySans-VariableFont_wght.ttf"), 18);
		String soundFile = "res/button_sound.mp3";
	    Media buttonSound = new Media(new File(soundFile).toURI().toString());
	    MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);

		// UI Elements in a VBox
		VBox vbox = new VBox(20);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(20));

		// Create GridPane for input fields
		GridPane inputGrid = new GridPane();
		inputGrid.setAlignment(Pos.CENTER);
		inputGrid.setHgap(10);
		inputGrid.setVgap(10);

		Label ipLabel = new Label("IP:");
		ipLabel.setFont(pixelFont);
		TextField ipTextField = new TextField();
		ipTextField.setFont(pixelFont);
		ipTextField.setStyle("-fx-background-radius: 15; ");
		Label portLabel = new Label("PORT:");
		portLabel.setFont(pixelFont);
		TextField portTextField = new TextField();
		portTextField.setFont(pixelFont);

		inputGrid.addRow(0, ipLabel, ipTextField);
		inputGrid.addRow(1, portLabel, portTextField);

		// Button Styling

		Text buttonText = new Text("JOIN");
		buttonText.setFont(pixelFont != null ? pixelFont : Font.font(18));
		buttonText.setFill(Color.WHITE);

		Button joinButton = new Button();
		joinButton.setGraphic(buttonText);
		joinButton.getStyleClass().add("join_button");

		
		joinButton.setOnMouseEntered(event -> {
			mediaPlayer.stop();
        	mediaPlayer.seek(Duration.ZERO);
        	mediaPlayer.play();
        	mediaPlayer.setVolume(0.2);
		});

		joinButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				 // create client
               String ipAddress = ipTextField.getText().trim();
               int port = Integer.parseInt(portTextField.getText());
               client = new PeerClient(ipAddress, port);
               client.start();

               // game system logic
               GameSystem.getInstance().setMyPeer(client);
               GameSystem.getInstance().getMyPlayer().setFilePath("res/Trainers/trainer7.png");
               
               // start battle & show battleScene
               GameSystem.getInstance().startBattle();
			}
		});

		Text menuText = new Text("MENU");
		menuText.setFont(pixelFont != null ? pixelFont : Font.font(18));
		menuText.setFill(Color.WHITE);

		Button menuButton = new Button();
		menuButton.setGraphic(menuText);
		menuButton.getStyleClass().add("menu2_button");
		menuButton.setOnMouseEntered(event -> {
			mediaPlayer.stop();
        	mediaPlayer.seek(Duration.ZERO);
        	mediaPlayer.play();
        	mediaPlayer.setVolume(0.2);
		});


		menuButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {
				 applySceneTransition(() -> sceneManager.showMainMenu());
			}
		});

		HBox buttonContainer = new HBox(20);
		buttonContainer.setAlignment(Pos.CENTER);
		joinButton.setTranslateX(30);
		menuButton.setTranslateX(30);
		buttonContainer.getChildren().addAll(menuButton, joinButton);

		// Add elements to VBox
		vbox.getChildren().addAll(inputGrid, buttonContainer);

		// Add everything to StackPane
		root.getChildren().addAll(vbox);

		// Create the scene with the same size as the main menu
		scene = new Scene(root, 800, 600);
		scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

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
