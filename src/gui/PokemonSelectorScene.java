package gui;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class PokemonSelectorScene {
    private Scene scene;
    private SceneManager sceneManager;
    private VBox leftBox;
    private GridPane rightGrid;
    private Button[] selectedButtons = new Button[6];
    private int selectedCount = 0;
    private Button submitButton;

    public PokemonSelectorScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;

        HBox root = new HBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        leftBox = new VBox();
        leftBox.setSpacing(5);
        leftBox.setPadding(new Insets(10));
        leftBox.setStyle("-fx-border-width: 1px; -fx-background-color: lightgray;");
        leftBox.setPrefWidth(150);

        rightGrid = new GridPane();
        rightGrid.setHgap(5);
        rightGrid.setVgap(5);
        rightGrid.setPadding(new Insets(10));

        int totalBoxes = 31;
        int columns = 4;
        for (int i = 0; i < totalBoxes; i++) {
            Button button = new Button("Box " + (i + 1));
            button.setPrefSize(130, 85);

            int row = i / columns;
            int col = i % columns;
            rightGrid.add(button, col, row);

            button.setOnAction(event -> handleButtonClick(button));
        }

        ScrollPane scrollPane = new ScrollPane(rightGrid);
        scrollPane.setFitToWidth(true);

        Button menuButton = new Button("Menu");
        menuButton.setPrefSize(70, 20);
        submitButton = new Button("Submit");
        submitButton.setDisable(true);
        submitButton.setPrefSize(70, 20);

        menuButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				 applySceneTransition(() -> sceneManager.showMainMenu());
			}
		});

        submitButton.setOnAction(event -> {
            System.out.println("Submit button clicked!");
        });

        HBox bottomPanel = new HBox(10);
        bottomPanel.setAlignment(Pos.BOTTOM_RIGHT);
        bottomPanel.getChildren().addAll(menuButton, submitButton);

        VBox rightContainer = new VBox(10);
        rightContainer.getChildren().addAll(scrollPane, bottomPanel);

        root.getChildren().addAll(leftBox, rightContainer);

        scene = new Scene(root, 800, 600);
    }

    private void handleButtonClick(Button button) {
        boolean isSelected = button.getStyle().contains("-fx-border-color: gray;");

        if (isSelected) {
            button.setStyle("");
            for (int i = 0; i < selectedButtons.length; i++) {
                if (selectedButtons[i] != null && selectedButtons[i].getText().equals(button.getText())) {
                    leftBox.getChildren().remove(selectedButtons[i]);
                    selectedButtons[i] = null;
                    selectedCount--;
                    break;
                }
            }
        } else {
            if (selectedCount < 6) {
                button.setStyle("-fx-border-color: gray; -fx-border-width: 2px;");
                for (int i = 0; i < selectedButtons.length; i++) {
                    if (selectedButtons[i] == null) {
                        Button newButton = new Button(button.getText());
                        newButton.setPrefSize(150, 85);
                        selectedButtons[i] = newButton;
                        leftBox.getChildren().add(newButton);
                        selectedCount++;
                        break;
                    }
                }
            } else {
                System.out.println("No more slots available!");
            }
        }

        submitButton.setDisable(selectedCount != 6);
    }
	private void applySceneTransition(Runnable sceneSwitch) {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.2), scene.getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> {
            sceneSwitch.run();
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.2), scene.getRoot());
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
    }

    public Scene getScene() {
        return scene;
    }
}