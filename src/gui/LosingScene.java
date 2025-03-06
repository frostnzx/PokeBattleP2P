package gui;

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
        
        // Create ImageView for the background image
        Image backgroundImage = new Image(getClass().getResourceAsStream("/losingbackground.jpg"));
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(800);  // Set width to cover the scene
        backgroundView.setFitHeight(600); // Set height to cover the scene

        // Game Over and You Lose Text
        Text gameOverText = new Text("Game Over");
        gameOverText.setFont(pixelFont);
        gameOverText.setFill(Color.DARKRED); // Set the font color to dark red

        Text loseText = new Text("You Lose!!!");
        loseText.setFont(pixelFont);
        loseText.setFill(Color.DARKRED); // Set the font color to dark red

        // Create "Back to Menu" button (initially hidden)
        Text menuText = new Text("Back to Menu");
        menuText.setFont(pixelFont2);
        Button backToMenuButton = new Button();
        backToMenuButton.setGraphic(menuText);
        
        // Make sure the button is invisible by setting both visible and opacity to 0
        backToMenuButton.setOpacity(0);  // Make the button fully transparent
        backToMenuButton.setVisible(false); // Ensure it starts off as invisible
        backToMenuButton.setOnAction(e -> sceneManager.showMainMenu());

        // Layout: VBox to stack texts and button
        VBox vBox = new VBox(20, gameOverText, loseText, backToMenuButton);
        vBox.setAlignment(Pos.CENTER);

        // Add background image and VBox to root layout
        root.getChildren().addAll(backgroundView, vBox);

        // Create Scene
        this.scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        // Apply fade-in animation to text elements
        applyFadeInAnimation(gameOverText, 0.5);
        applyFadeInAnimation(loseText, 1.0);

        // Fade-in transition for the button with a 3-second delay
        fadeInButtonWithDelay(backToMenuButton, 2.0);  // Show button after 3 seconds delay
    }

    // Fade-in transition for text with a delay
    private void applyFadeInAnimation(Text text, double delaySeconds) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), text);
        fadeIn.setFromValue(0);  // Start from invisible
        fadeIn.setToValue(1);    // Fade to fully visible
        fadeIn.setDelay(Duration.seconds(delaySeconds)); // Add delay before fading in
        fadeIn.play();
    }

    // Fade-in transition for the button with a delay
    private void fadeInButtonWithDelay(Button backToMenuButton, double delaySeconds) {
        FadeTransition fadeInTimeline = new FadeTransition(Duration.seconds(1), backToMenuButton);
        fadeInTimeline.setFromValue(0); // Start from invisible
        fadeInTimeline.setToValue(1);   // Fade to fully visible
        fadeInTimeline.setDelay(Duration.seconds(delaySeconds)); // Add delay before fading in
        fadeInTimeline.play();
        backToMenuButton.setVisible(true);  // Ensure button is visible before fading in
    }

    public Scene getScene() {
        return this.scene;
    }
}