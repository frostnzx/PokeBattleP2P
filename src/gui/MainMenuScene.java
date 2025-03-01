package gui;


import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class MainMenuScene {
	private Scene scene;
	private SceneManager sceneManager;

	public MainMenuScene(SceneManager sceneManager) {

		this.sceneManager = sceneManager;

		StackPane root = new StackPane();

		// Load the background image
		Image backgroundImage = new Image("pokemon_menu.jpg"); // Use the correct path to your image file
		ImageView backgroundImageView = new ImageView(backgroundImage);

		// Set the ImageView to fill the entire background of the StackPane
		backgroundImageView.setFitWidth(800); // Set the width to match your scene's width
		backgroundImageView.setFitHeight(600); // Set the height to match your scene's height
		backgroundImageView.setPreserveRatio(false); // Optional: Keep the aspect ratio of the image

		// Add the ImageView as the background to the root node
		root.getChildren().add(backgroundImageView);
		
		//Create pokemon logo
		Image pokemonlogo = new Image("pokemon.png");
		ImageView imageView = new ImageView(pokemonlogo);
		imageView.setFitWidth(350);
	    imageView.setFitHeight(350);
	    imageView.setPreserveRatio(true);
	    
	    //Add font
	    Font pixelFont = Font.loadFont(getClass().getResourceAsStream("/fonts/PixelifySans-VariableFont_wght.ttf"), 25);
	    
	 // Create the "Join Game" button text with stroke
        Text joinGameText = new Text("Join Game");
        joinGameText.setFont(pixelFont); // Apply the retro font
        joinGameText.setFill(Color.WHITE); // Set text color
        //joinGameText.setStroke(Color.BLACK); // Set stroke color (border around text)
        joinGameText.setStrokeWidth(0); // Set stroke width

        // Create the "Create Game" button text with stroke
        Text createGameText = new Text("Create Game");
        createGameText.setFont(pixelFont); // Apply the retro font
        createGameText.setFill(Color.WHITE); // Set text color
        
        //createGameText.setStroke(Color.BLACK); // Set stroke color (border around text)
        createGameText.setStrokeWidth(0); // Set stroke width
		

		// Create the "Join Game" button
		Button joinGameButton = new Button();
		joinGameButton.setGraphic(joinGameText);
		joinGameButton.getStyleClass().add("button"); // Apply CSS
		
		//joinGameButton.setStyle("-fx-background-color: #9370DB; -fx-text-fill: white; -fx-font-size: 20px; -fx-border-radius: 40; -fx-background-radius: 15; -fx-padding: 10 20;"); // Customize button style
		joinGameButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// Switch to the Join Game scene when clicked
				applySceneTransition(() -> sceneManager.showJoinGameScene());
			}
		});
		
//		joinGameButton.setOnMouseEntered(event -> {
//		    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), joinGameButton);
//		    scaleTransition.setToX(1.1); // Scale up by 10% in the X direction
//		    scaleTransition.setToY(1.1); // Scale up by 10% in the Y direction
//		    scaleTransition.play();
//		});
//
//		joinGameButton.setOnMouseExited(event -> {
//		    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), joinGameButton);
//		    scaleTransition.setToX(1); // Reset to original scale
//		    scaleTransition.setToY(1);
//		    scaleTransition.play();
//		});

		// Create the "Create Game" button
		Button createGameButton = new Button();
		createGameButton.getStyleClass().add("button");
		createGameButton.setGraphic(createGameText);
		createGameButton.setTranslateY(15);
		//createGameButton.setStyle("-fx-background-color: #9370DB; -fx-text-fill: white; -fx-font-size: 20px; -fx-border-radius: 40; -fx-background-radius: 15; -fx-padding: 10 20;"); // Customize button style
		createGameButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// Switch to the Create Game scene when clicked
				applySceneTransition(() -> sceneManager.showCreateGameScene());
			}
		});
		
//		createGameButton.setOnMouseEntered(event -> {
//		    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), createGameButton);
//		    scaleTransition.setToX(1.1); // Scale up by 10% in the X direction
//		    scaleTransition.setToY(1.1); // Scale up by 10% in the Y direction
//		    scaleTransition.play();
//		});
//
//		createGameButton.setOnMouseExited(event -> {
//		    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), createGameButton);
//		    scaleTransition.setToX(1); // Reset to original scale
//		    scaleTransition.setToY(1);
//		    scaleTransition.play();
//		});

		// Position buttons on the screen (e.g., using StackPane)
		StackPane.setMargin(imageView, new javafx.geometry.Insets(-200, 0, 0, 0));
		StackPane.setMargin(joinGameButton, new javafx.geometry.Insets(0, 0, 0, 0)); // Adjust top margin
		StackPane.setMargin(createGameButton, new javafx.geometry.Insets(130, 0, 0, 0)); // Adjust top margin
		
		
		// Add buttons to the root node
		root.getChildren().addAll(imageView ,joinGameButton, createGameButton);
		

		

		// Create the scene with the root node
		scene = new Scene(root, 800, 600); // Adjust the width and height as needed
		// Load CSS file
		scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

//		// create gui
//		GridPane root = new GridPane();
//		Button createBtn = new Button("Create");
//		createBtn.setOnAction(new EventHandler<ActionEvent>() {
//			public void handle(ActionEvent event) {
//				sceneManager.showCreateGameScene();
//			}
//		});
//		Button joinBtn = new Button("Join");
//		joinBtn.setOnAction(new EventHandler<ActionEvent>() {
//			public void handle(ActionEvent event) {
//				sceneManager.showJoinGameScene();
//			}
//		});
//		root.add(createBtn, 0, 1);
//		root.add(joinBtn, 0, 3);
//		this.scene = new Scene(root, 300, 250);
		
	
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
