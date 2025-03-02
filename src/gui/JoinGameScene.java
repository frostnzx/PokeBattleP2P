package gui;

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
//		joinButton.setStyle(
//				"-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 20px; -fx-background-radius: 15; -fx-padding: 10 20;");
//
//		// Button Scaling Animation on Hover
//		joinButton.setOnMouseEntered(event -> {
//			ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), joinButton);
//			scaleTransition.setToX(1.1); // Scale up by 10% in the X direction
//			scaleTransition.setToY(1.1); // Scale up by 10% in the Y direction
//			scaleTransition.play();
//		});
//
//		joinButton.setOnMouseExited(event -> {
//			ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), joinButton);
//			scaleTransition.setToX(1); // Reset to original scale
//			scaleTransition.setToY(1);
//			scaleTransition.play();
//		});

		joinButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				// create client
				String ipAddress = ipTextField.getText().trim();
				int port = Integer.parseInt(portTextField.getText());
				client = new PeerClient(ipAddress, port);
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

		Text menuText = new Text("MENU");
		menuText.setFont(pixelFont != null ? pixelFont : Font.font(18));
		menuText.setFill(Color.WHITE);

		Button menuButton = new Button();
		menuButton.setGraphic(menuText);
		menuButton.getStyleClass().add("menu2_button");
//		menuButton.setStyle(
//				"-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 20px; -fx-background-radius: 15; -fx-padding: 10 20;");
//
//		// Button Scaling Animation on Hover
//
//		menuButton.setOnMouseEntered(event -> {
//			ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), menuButton);
//			scaleTransition.setToX(1.1); // Scale up by 10% in the X direction
//			scaleTransition.setToY(1.1); // Scale up by 10% in the Y direction
//			scaleTransition.play();
//		});
//
//		menuButton.setOnMouseExited(event -> {
//			ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), menuButton);
//			scaleTransition.setToX(1); // Reset to original scale
//			scaleTransition.setToY(1);
//			scaleTransition.play();
//		});

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

//		// create gui
//		GridPane root = new GridPane();
//		Label ipLabel = new Label("IP : ");
//		TextField ipTextField = new TextField();
//		Label portLabel = new Label("PORT : ");
//		TextField portTextField = new TextField();
//		
//		root.addRow(0 , ipLabel , ipTextField);
//		root.addRow(1 , portLabel , portTextField);
//		
//		Button button = new Button("JOIN");
//		button.setOnAction(new EventHandler<ActionEvent>() {
//			public void handle(ActionEvent e) {
//				// create client
//				String ipAddress = ipTextField.getText().trim();
//				int port = Integer.parseInt(portTextField.getText());
//				client = new PeerClient(ipAddress , port);
//				client.start();
//				
//				// game system logic
//				GameSystem.getInstance().setMyPeer(client);
//				// ...
//				// ...
//				
//				// open new scene (gui)
//				// ...
//				// ...
//			}
//		});
//		root.addRow(2, button);
//		this.scene = new Scene(root , 800 , 600);
//	}
	
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
