package gui;


import java.io.File;

import game.GameSystem;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class MainMenuScene {
	private Scene scene;
	private SceneManager sceneManager;

	public MainMenuScene(SceneManager sceneManager) {

		this.sceneManager = sceneManager;
		
		String backgroundFile = "res/BackgroundSound.mp3";
        
        Media backgroundSound = new Media(new File(backgroundFile).toURI().toString());
        MediaPlayer backgroundPlayer = new MediaPlayer(backgroundSound);
        backgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundPlayer.setAutoPlay(true);
        backgroundPlayer.setVolume(0.1); // Adjust the volume (0.0 to 1.0)
        
        String soundFile = "res/button_sound.mp3";
        
        Media buttonSound = new Media(new File(soundFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);

		StackPane root = new StackPane();

		String videoPath = new File("res/BackGroundVid2.mp4").toURI().toString();
		Media media = new Media(videoPath);
		MediaPlayer mediaPlayer1 = new MediaPlayer(media);
		mediaPlayer1.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer1.setAutoPlay(true);
		
		
		// Create MediaView and adjust it to fit the scene
		MediaView mediaView = new MediaView(mediaPlayer1);
		mediaView.setFitWidth(800); // Match scene width
		mediaView.setFitHeight(600); // Match scene height
		mediaView.setPreserveRatio(false); // Stretch to fill


		// Add the ImageView as the background to the root node
		root.getChildren().add(0, mediaView);
		
		//Create pokemon logo
		Image pokemonlogo = new Image("pokebattle.png");
		ImageView imageView = new ImageView(pokemonlogo);
		imageView.setFitWidth(350);
	    imageView.setFitHeight(350);
	    imageView.setPreserveRatio(true);
	    
	    //Add font
	    Font pixelFont = Font.loadFont(getClass().getResourceAsStream("/fonts/PixelifySans-VariableFont_wght.ttf"), 35);
	    
        Text joinGameText = new Text("Join Game");
        joinGameText.setFont(pixelFont); // Apply the retro font
        joinGameText.setFill(Color.WHITE); // Set text color
        joinGameText.setCursor(Cursor.HAND); // Set hand cursor for interactivity
   
        
     // Add hover effect (enlarge on hover)
        joinGameText.setOnMouseEntered(event -> {
        	mediaPlayer.stop();
        	mediaPlayer.seek(Duration.ZERO);
        	mediaPlayer.play();
        	mediaPlayer.setVolume(0.05);
            joinGameText.setScaleX(1.1);
            joinGameText.setScaleY(1.1);
       
        });
        joinGameText.setOnMouseExited(event -> {
            joinGameText.setScaleX(1.0);
            joinGameText.setScaleY(1.0);
        });

        // Handle click event
        joinGameText.setOnMouseClicked(event -> {
        	backgroundPlayer.stop(); // Stop the background video
            applySceneTransition(() -> sceneManager.showJoinGameScene());
        });

        // Create the "Create Game" button text with stroke
        Text createGameText = new Text("Create Game");
        createGameText.setFont(pixelFont); // Apply the retro font
        createGameText.setFill(Color.WHITE); // Set text color
        createGameText.setCursor(Cursor.HAND);
        createGameText.setOnMouseEntered(event -> {
        	mediaPlayer.stop();
        	mediaPlayer.seek(Duration.ZERO);
        	mediaPlayer.play();
        	mediaPlayer.setVolume(0.05);
            createGameText.setScaleX(1.1);
            createGameText.setScaleY(1.1);
            
       
        });
        createGameText.setOnMouseExited(event -> {
            createGameText.setScaleX(1.0);
            createGameText.setScaleY(1.0);
        });

        // Handle click event
        createGameText.setOnMouseClicked(event -> {
            backgroundPlayer.stop(); // Stop the background video
            applySceneTransition(() -> sceneManager.showCreateGameScene());
        });
        
        Text SelectPokemonText = new Text("Select Pokemon");
        SelectPokemonText.setFont(pixelFont); // Apply the retro font
        SelectPokemonText.setFill(Color.WHITE); // Set text color
        SelectPokemonText.setCursor(Cursor.HAND);
        SelectPokemonText.setOnMouseEntered(event -> {
        	mediaPlayer.stop();
        	mediaPlayer.seek(Duration.ZERO);
        	mediaPlayer.play();
            SelectPokemonText.setScaleX(1.1);
            SelectPokemonText.setScaleY(1.1);
            
       
        });
        SelectPokemonText.setOnMouseExited(event -> {
            SelectPokemonText.setScaleX(1.0);
            SelectPokemonText.setScaleY(1.0);
        });
        
        
        SelectPokemonText.setOnMouseClicked(event -> {
            backgroundPlayer.stop(); // Stop the background video
            applySceneTransition(() -> sceneManager.showPokemonSelectorScene());
        });

        
        // Position buttons on the screen (e.g., using StackPane)
		StackPane.setMargin(imageView, new javafx.geometry.Insets(-200, 0, 0, 0));
		StackPane.setMargin(joinGameText, new javafx.geometry.Insets(-10, 0, 0, 0)); // Adjust top margin
		StackPane.setMargin(createGameText, new javafx.geometry.Insets(110, 0, 0, 0)); // Adjust top margin
		StackPane.setMargin(SelectPokemonText, new javafx.geometry.Insets(230 , 0, 0, 0));
		
		Button checkCurrentPokemons = new Button("Check current Deck"); // For testing purposes only // don't delete it yet
		checkCurrentPokemons.setOnAction(event -> {
            GameSystem.getInstance().getMyPlayer().listCurrentPokemons();
        });
		
		
		
		// Add buttons to the root node
		root.getChildren().addAll(imageView ,joinGameText, createGameText, SelectPokemonText);
		

		

		// Create the scene with the root node
		scene = new Scene(root, 800, 600); // Adjust the width and height as needed
		// Load CSS file
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
