package gui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import game.GameSystem;
import game.Pokemon;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
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

        String backgroundPath = "file:res/selector.png";
        Image backgroundImage = new Image(backgroundPath);

        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize);

        root.setBackground(new Background(background));

        leftBox = new VBox();
        leftBox.setSpacing(5);
        leftBox.setPadding(new Insets(10));
        leftBox.setStyle(
        	    "-fx-background-color: rgba(255, 255, 255, 0.7); " +
        	    "-fx-background-radius: 10; " +
        	    "-fx-border-radius: 10; " +
        	    "-fx-border-color: #cccccc; " +
        	    "-fx-border-width: 1px; " +
        	    "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 5, 0, 0, 1);" // เงา
        	);
        leftBox.setPrefWidth(150);

        rightGrid = new GridPane();
        rightGrid.setHgap(5);
        rightGrid.setVgap(5);
        rightGrid.setPadding(new Insets(10));
        
        String soundFile = "res/button_sound.mp3";
        
        Media buttonSound = new Media(new File(soundFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(buttonSound);
        
        int columns = 4;
        ArrayList<Pokemon> pokemonList = Pokemon.getPokemonList();
        HashSet<Integer> pokemonToBeInit = new HashSet<Integer>(); 
        ArrayList<Button> buttonToBeFire = new ArrayList<Button>(); 
        for(Pokemon p : GameSystem.getInstance().getMyPlayer().getPokemons()) {
            pokemonToBeInit.add(p.getPokemonId());
        }
        
        for (int i = 0; i < pokemonList.size(); i++) {
            Pokemon pokemon = pokemonList.get(i);
            String imgPath = "file:" + pokemon.getFilePath();
            Button button = new Button();
            button.setPrefSize(130, 85);
            
            Image image = new Image(imgPath);
            BackgroundSize backgroundSizeButton = new BackgroundSize(130, 85, false, false, true, false);
            BackgroundImage backgroundImageButton = new BackgroundImage(image, 
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
                    BackgroundPosition.CENTER, 
                    backgroundSizeButton);
            button.setBackground(new Background(backgroundImageButton));
            
            button.setId(String.valueOf(pokemon.getPokemonId()));

            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(5);
            dropShadow.setOffsetX(0);
            dropShadow.setOffsetY(3);
            dropShadow.setColor(Color.BLACK);
            button.setEffect(dropShadow);

            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), button);
            scaleUp.setToX(1.1);
            scaleUp.setToY(1.1);

            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), button);
            scaleDown.setToX(1.0);
            scaleDown.setToY(1.0);

            button.setOnMouseEntered(event -> {
                mediaPlayer.stop();
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
                mediaPlayer.setVolume(0.05);
                scaleUp.play();
            });

            button.setOnMouseExited(event -> {
                scaleDown.play();
            });

            Tooltip tooltip = new Tooltip(pokemon.getName());
            button.setTooltip(tooltip);

            int row = i / columns;
            int col = i % columns;
            rightGrid.add(button, col, row);

            button.setOnAction(event -> handleButtonClick(button));
            
            if(pokemonToBeInit.contains(pokemon.getPokemonId())) {
                buttonToBeFire.add(button);
            }
        }

        ScrollPane scrollPane = new ScrollPane(rightGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7);");
        
        Button menuButton = new Button("Menu");
        menuButton.setPrefSize(120, 20);
        submitButton = new Button("Submit");
        submitButton.setDisable(true);
        submitButton.setPrefSize(120, 20);

        ScaleTransition menuScaleUp = new ScaleTransition(Duration.millis(200), menuButton);
        menuScaleUp.setToX(1.1);
        menuScaleUp.setToY(1.1);

        ScaleTransition menuScaleDown = new ScaleTransition(Duration.millis(200), menuButton);
        menuScaleDown.setToX(1.0);
        menuScaleDown.setToY(1.0);

        ScaleTransition submitScaleUp = new ScaleTransition(Duration.millis(200), submitButton);
        submitScaleUp.setToX(1.1);
        submitScaleUp.setToY(1.1);

        ScaleTransition submitScaleDown = new ScaleTransition(Duration.millis(200), submitButton);
        submitScaleDown.setToX(1.0);
        submitScaleDown.setToY(1.0);

        menuButton.setOnMouseEntered(event -> {
            mediaPlayer.stop();
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
            mediaPlayer.setVolume(0.05);
            menuScaleUp.play();
        });

        menuButton.setOnMouseExited(event -> {
            menuScaleDown.play();
        });

        submitButton.setOnMouseEntered(event -> {
            mediaPlayer.stop();
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
            mediaPlayer.setVolume(0.05);
            submitScaleUp.play();
        });

        submitButton.setOnMouseExited(event -> {
            submitScaleDown.play();
        });
        
        menuButton.setId("menuButton");
        submitButton.setId("submitButton");

        menuButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                 applySceneTransition(() -> sceneManager.showMainMenu());
            }
        });

        submitButton.setOnAction(event -> {
            System.out.println("Submit button clicked!");
            ArrayList<Pokemon> newpokemonList = new ArrayList<Pokemon>();
             for (int i = 0; i < selectedButtons.length; i++) {
                 Button button = selectedButtons[i];
                 int pokemonId = Integer.parseInt(button.getId());
                 Pokemon pokemon = new Pokemon(pokemonId);
                 newpokemonList.add(pokemon);
             }
             GameSystem.getInstance().getMyPlayer().setPokemons(newpokemonList);
             sceneManager.showMainMenu();
        });

        HBox bottomPanel = new HBox(10);
        bottomPanel.setAlignment(Pos.BOTTOM_RIGHT);
        bottomPanel.getChildren().addAll(menuButton, submitButton);

        VBox rightContainer = new VBox(10);
        rightContainer.getChildren().addAll(scrollPane, bottomPanel);

        root.getChildren().addAll(leftBox, rightContainer);
        
        scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("pokemonselector.css").toExternalForm());
        for(Button b : buttonToBeFire) {
            b.fire();
        }
    }
    
    private void removeFromSelect(Button selectBtn) {
        for (int i = 0; i < selectedButtons.length; i++) {
            if (selectedButtons[i] != null && selectedButtons[i].getId().equals(selectBtn.getId())) {
                leftBox.getChildren().remove(selectedButtons[i]);
                selectedButtons[i] = null;
                selectedCount--;
                break;
            }
        }
    }

    private void handleButtonClick(Button button) {
        boolean isSelected = button.getStyle().contains("-fx-border-color: gray;");

        if (isSelected) {
            button.setStyle("");
            removeFromSelect(button);
        } else {
            if (selectedCount < 6) {
                button.setStyle("-fx-border-color: gray; -fx-border-width: 2px;");
                for (int i = 0; i < selectedButtons.length; i++) {
                    if (selectedButtons[i] == null) {
                        Button newButton = new Button();
                        newButton.setBackground(button.getBackground());
                        newButton.setId(button.getId());
                        newButton.setPrefSize(150, 85);
                        newButton.setOnMouseClicked(event -> {
                            removeFromSelect(newButton);
                            handleButtonClick(button);
                        });
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