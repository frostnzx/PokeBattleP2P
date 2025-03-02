package gui;

import javafx.animation.PauseTransition;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.util.Random;

public class WinningScene {
    private Scene scene;
    private SceneManager sceneManager;
    private static final int STAR_COUNT = 11; // Number of blinking stars

    public WinningScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;

        // Load pixel font
        Font pixelFont = Font.loadFont(getClass().getResourceAsStream("/fonts/PixelifySans-VariableFont_wght.ttf"), 70);
        Font pixelFont2 = Font.loadFont(getClass().getResourceAsStream("/fonts/PixelifySans-VariableFont_wght.ttf"), 30);

        // Root layout
        StackPane root = new StackPane();

        // Winning Text
        Text congratText = new Text("Congratulations");
        congratText.setFont(pixelFont);
        Text winningText = new Text("You Win!!!");
        winningText.setFont(pixelFont);

        // Menu Button (hidden initially)
        Text backText = new Text("Back to Menu");
        backText.setFont(pixelFont2);
        backText.setFill(Color.WHITE);

        Button menuButton = new Button();
        menuButton.setGraphic(backText);
        menuButton.getStyleClass().add("menu2_button");
        menuButton.setVisible(false);
        menuButton.setOnAction(e -> sceneManager.showMainMenu());

        // Layout: Text & Button
        VBox vBox = new VBox(20, congratText, winningText, menuButton);
        vBox.setAlignment(Pos.CENTER);

        // Add elements to root
        root.getChildren().add(vBox);

        // Create scene
        this.scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        // Star Image
        Image starImage = new Image(getClass().getResourceAsStream("/star.png"));

        // Create blinking stars
        Random random = new Random();
        for (int i = 0; i < STAR_COUNT; i++) {
            // Create a PauseTransition to introduce a delay before each star is created
            PauseTransition pause = new PauseTransition(Duration.seconds(i * 0.5)); // Delay 0.5 seconds between each star

            pause.setOnFinished(e -> {
                ImageView star = new ImageView(starImage);
                star.setFitWidth(50);
                star.setFitHeight(50);

                // Initial random position
                star.setTranslateX(random.nextInt(800) - 400); // Random X position within screen width
                star.setTranslateY(random.nextInt(600) - 300); // Random Y position within screen height

                root.getChildren().add(star);

                // Random delay before starting the blink (between 0 and 2 seconds)
                double randomDelay = random.nextDouble() * 2;

                // Animate the stars' fade in/out
                FadeTransition blinkTransition = new FadeTransition(Duration.seconds(3), star); // Slower fade in/out
                blinkTransition.setFromValue(0);  // Start from invisible
                blinkTransition.setToValue(1);    // Fade to fully visible
                blinkTransition.setCycleCount(FadeTransition.INDEFINITE);  // Blink indefinitely
                blinkTransition.setAutoReverse(true);  // Make the star blink (fade in and out)
                blinkTransition.setDelay(Duration.seconds(randomDelay)); // Add random delay

                // Update position on each fade cycle (after fade-in and fade-out)
                blinkTransition.setOnFinished(e2 -> {
                    // Move to a new random position on every fade cycle
                    star.setTranslateX(random.nextInt(800) - 400); // Random X position
                    star.setTranslateY(random.nextInt(600) - 300); // Random Y position
                });

                // Play the fade-in and fade-out animation
                blinkTransition.play();
            });

            // Start the pause transition for each star
            pause.play();
        }

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

    public Scene getScene() {
        return this.scene;
    }
}