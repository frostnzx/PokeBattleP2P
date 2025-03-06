package gui;

import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.Random;

public class WinningScene {
    private Scene scene;
    private SceneManager sceneManager;
    private static final int STAR_COUNT = 11; // Number of blinking stars

    public WinningScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        
        URL soundURL = getClass().getResource("/winningsoundeffect.mp3"); // Ensure the file exists in your 'resources/sounds' folder
        if (soundURL != null) {
            Media winningSound = new Media(soundURL.toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(winningSound);
            mediaPlayer.setVolume(0.8); // Set volume (0.0 to 1.0)
            mediaPlayer.play(); // Play the sound
        } else {
            System.out.println("Error: Winning sound file not found!");
        }

        // Load pixel font
        Font pixelFont = Font.loadFont(getClass().getResourceAsStream("/fonts/PixelifySans-VariableFont_wght.ttf"), 70);
        Font pixelFont2 = Font.loadFont(getClass().getResourceAsStream("/fonts/PixelifySans-VariableFont_wght.ttf"), 30);

        // Root layout
        StackPane root = new StackPane();
        
        Image backgroundImage = new Image(getClass().getResourceAsStream("/winningbackground.jpg"));
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(800);  // Set width of background to fit the scene
        backgroundView.setFitHeight(600); // Set height of background to fit the scene

        // Add background image first to the root
        root.getChildren().add(backgroundView);

        // Winning Text
        Text congratText = new Text("Congratulations");
        congratText.setFont(pixelFont);
        congratText.setFill(Color.WHITE);
        Text winningText = new Text("You Win!!!");
        winningText.setFont(pixelFont);
        winningText.setFill(Color.WHITE);

        // Menu Button (hidden initially)
        Text backText = new Text("Back to Menu");
        backText.setFont(pixelFont2);
        backText.setFill(Color.WHITE);

        Button menuButton = new Button();
        menuButton.setGraphic(backText);
        menuButton.getStyleClass().add("menu2_button");
        menuButton.setVisible(false);
        menuButton.setOnAction(e -> applySceneTransition(() -> sceneManager.showMainMenu()));
        
     // Load Trophy Image
        Image trophyImage = new Image(getClass().getResourceAsStream("/trophy.png"));
        ImageView trophyView = new ImageView(trophyImage);
        trophyView.setFitWidth(100); // Adjust size
        trophyView.setFitHeight(100);
        
        VBox vBox = new VBox(20, congratText, winningText, trophyView, menuButton);
        vBox.setAlignment(Pos.CENTER);

        

        // Add elements to root
        root.getChildren().add(vBox);

        // Create scene
        this.scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        
        FadeTransition fadeInCongratText = new FadeTransition(Duration.seconds(2), congratText);
        fadeInCongratText.setFromValue(0); // Start from invisible
        fadeInCongratText.setToValue(1);   // Fade to fully visible
        fadeInCongratText.setCycleCount(1); // Play it once
        fadeInCongratText.setDelay(Duration.seconds(1)); // Delay before fade-in
        fadeInCongratText.play();

        FadeTransition fadeInWinningText = new FadeTransition(Duration.seconds(2), winningText);
        fadeInWinningText.setFromValue(0); // Start from invisible
        fadeInWinningText.setToValue(1);   // Fade to fully visible
        fadeInWinningText.setCycleCount(1); // Play it once
        fadeInWinningText.setDelay(Duration.seconds(1.5)); // Delay before fade-in
        fadeInWinningText.play();

     // Shake Animation for the Trophy
        Timeline shakeAnimation = new Timeline(
            new KeyFrame(Duration.millis(50), new KeyValue(trophyView.translateXProperty(), -10)),
            new KeyFrame(Duration.millis(100), new KeyValue(trophyView.translateXProperty(), 10)),
            new KeyFrame(Duration.millis(150), new KeyValue(trophyView.translateXProperty(), -8)),
            new KeyFrame(Duration.millis(200), new KeyValue(trophyView.translateXProperty(), 8)),
            new KeyFrame(Duration.millis(250), new KeyValue(trophyView.translateXProperty(), -5)),
            new KeyFrame(Duration.millis(300), new KeyValue(trophyView.translateXProperty(), 5)),
            new KeyFrame(Duration.millis(350), new KeyValue(trophyView.translateXProperty(), 0)) // Back to center
        );
        shakeAnimation.setCycleCount(3); // Shake 3 times
        shakeAnimation.setAutoReverse(true);

        // Delay Shake Animation (Show after 1.5s)
        PauseTransition delayTrophy = new PauseTransition(Duration.seconds(1.5));
        delayTrophy.setOnFinished(e -> shakeAnimation.play());
        delayTrophy.play();

        // Show menu button after 5 seconds using PauseTransition
        PauseTransition pauseMenu = new PauseTransition(Duration.seconds(3));
        pauseMenu.setOnFinished(e -> {
            menuButton.setVisible(true);
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), menuButton);
            fadeIn.setFromValue(0); // Start from invisible
            fadeIn.setToValue(1);   // Fade to fully visible
            fadeIn.play();
        });
        pauseMenu.play();
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