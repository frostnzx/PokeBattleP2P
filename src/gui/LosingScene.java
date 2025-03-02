package gui;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class LosingScene {
    private Scene scene;
    private SceneManager sceneManager;

    public LosingScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;

        // Load pixel font
        Font pixelFont = Font.loadFont(getClass().getResourceAsStream("/fonts/PixelifySans-VariableFont_wght.ttf"), 70);
        Font pixelFont2 = Font.loadFont(getClass().getResourceAsStream("/fonts/PixelifySans-VariableFont_wght.ttf"), 30);

        // Root layout
        StackPane root = new StackPane();
        
        // Create a rectangle for the background
        Rectangle background = new Rectangle(800, 600);
        background.setFill(Color.web("#8B0000"));  // Dark red color

        // Game Over and You Lose Text
        Text gameOverText = new Text("Game Over");
        gameOverText.setFont(pixelFont);
        Text loseText = new Text("You Lose!!!");
        loseText.setFont(pixelFont);

        // Create "Back to Menu" button (initially hidden)
        
        Text menuText = new Text("Back to Menu");
        menuText.setFont(pixelFont2);
        Button backToMenuButton = new Button();
        backToMenuButton.setGraphic(menuText);
        backToMenuButton.setVisible(false);
        backToMenuButton.setOnAction(e -> sceneManager.showMainMenu());

        // Layout: VBox to stack texts and button
        VBox vBox = new VBox(20, gameOverText, loseText, backToMenuButton);
        vBox.setAlignment(Pos.CENTER);

        // Add rectangle for background and VBox to root layout
        root.getChildren().addAll(background, vBox);

        // Create Scene
        this.scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        // Add background blinking effect with fade
        addBackgroundFadeBlinkEffect(background, 3, backToMenuButton);  // Blink background 3 times
    }

    // Add background color fade blinking effect and show button after blinking
    private void addBackgroundFadeBlinkEffect(Rectangle background, int blinkCount, Button backToMenuButton) {
        // Create fade transitions for each background state
        FadeTransition fadeToWhite = new FadeTransition(Duration.seconds(0.2), background);
        fadeToWhite.setFromValue(1); // Start from fully visible
        fadeToWhite.setToValue(0);   // Fade to transparent (white background will be visible)

        FadeTransition fadeToRed = new FadeTransition(Duration.seconds(0.2), background);
        fadeToRed.setFromValue(0);  // Start from transparent
        fadeToRed.setToValue(1);    // Fade back to fully visible (red background)

        // Set the sequence of the fade-in/out transitions
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0), e -> background.setFill(Color.web("#8B0000;"))), // Dark red
            new KeyFrame(Duration.seconds(0.2), e -> fadeToWhite.play()), // Fade to white
            new KeyFrame(Duration.seconds(0.4), e -> fadeToRed.play())  // Fade back to red
        );

        // Set the timeline to repeat the fade effect 3 times
        timeline.setCycleCount(blinkCount);

        // Add a pause to show the "Back to Menu" button after blinking
        timeline.setOnFinished(e -> {
            // Delay for 0.5 seconds before starting the fade-in of the button
            fadeInButtonWithDelay(backToMenuButton, 0.5);
        });

        // Play the animation
        timeline.play();
    }

    // Fade-in transition for the button with a delay
    private void fadeInButtonWithDelay(Button backToMenuButton, double delaySeconds) {
        Timeline fadeInTimeline = new Timeline(
            new KeyFrame(Duration.seconds(delaySeconds), e -> fadeInButton(backToMenuButton))
        );
        fadeInTimeline.play();
    }

    // Fade-in transition for the button
    private void fadeInButton(Button backToMenuButton) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), backToMenuButton);
        fadeIn.setFromValue(0); // Start from invisible
        fadeIn.setToValue(1);   // Fade to fully visible
        fadeIn.play();
        backToMenuButton.setVisible(true);
    }

    public Scene getScene() {
        return this.scene;
    }
}