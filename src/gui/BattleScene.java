package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BattleScene extends Application {
	private Scene scene ; 
	private SceneManager sceneManager ; 
	 private BorderPane root;

    @Override
    public void start(Stage primaryStage) {
        // Player and opponent name
        Label playerName = new Label("Player Name");
        Label opponentName = new Label("Opopnnent Name");
        
        // Pokemon images
        Rectangle playerPokemon = new Rectangle(300, 300, Color.GRAY);
        Rectangle opponentPokemon = new Rectangle(300, 300, Color.GRAY);
        Rectangle playerAvatar = new Rectangle(130, 210, Color.GRAY);
        Rectangle opponentAvatar = new Rectangle(130, 210, Color.GRAY);
        
        // Health bars
        //(player)
        Label playerPokemonName = new Label("Pokemonplayer");
        playerPokemonName.setFont(Font.font(14));
        Rectangle playerHpBar = new Rectangle(150, 15, Color.LIMEGREEN);
        Label playerHpText = new Label("100 / 100");
        Label playerStatus = new Label("Status Condition");
        playerStatus.setFont(Font.font(10));
        
        //(opponent)
        Label opponentPokemonName = new Label("PokemonOpponent");
        opponentPokemonName.setFont(Font.font(14));
        Rectangle opponentHpBar = new Rectangle(150, 15, Color.LIMEGREEN);
        Label opponentHpText = new Label("100 / 100");
        Label opponentStatus = new Label("Status Condition");
        opponentStatus.setFont(Font.font(10));

        // Battle Options --------------------------------------------
        Label actionLabel = new Label("What will PokemonName do?");
        actionLabel.setFont(Font.font(20));
        Button fightButton = new Button("Fight");
        Button bagButton = new Button("Bag");
        Button pokemonButton = new Button("Pokemon");
        Button giveUpButton = new Button("Give Up");
        
        fightButton.setMinSize(300, 50);
        bagButton.setMinSize(300, 50);
        pokemonButton.setMinSize(300, 50);
        giveUpButton.setMinSize(300, 50);

        // Layout setup ----------------------------------------------

        VBox opponentPokemonInfo = new VBox(opponentPokemonName, opponentHpBar, opponentHpText, opponentStatus);
        opponentPokemonInfo.setAlignment(Pos.BOTTOM_RIGHT);
        
        HBox opponentInfo = new HBox(10, new VBox(10, opponentPokemon, opponentPokemonInfo), opponentAvatar);
        HBox playerInfo = new HBox(10, playerAvatar, new VBox(10, playerPokemon, new VBox(playerPokemonName, playerHpBar, playerHpText, playerStatus)));
        VBox leftPanel = new VBox(10, playerName, playerInfo);
        VBox rightPanel = new VBox(10, opponentName, opponentInfo);
        opponentName.setAlignment(Pos.TOP_RIGHT);
        HBox battleField = new HBox(50, leftPanel, rightPanel);
        battleField.setAlignment(Pos.CENTER);
        battleField.setPadding(new Insets(5));

        GridPane actionPanel = new GridPane();
        actionPanel.add(fightButton, 0, 0);
        actionPanel.add(bagButton, 1, 0);
        actionPanel.add(pokemonButton, 0, 1);
        actionPanel.add(giveUpButton, 1, 1);
        actionPanel.setHgap(10);
        actionPanel.setVgap(10);
        actionPanel.setAlignment(Pos.CENTER);

        BorderPane actionContainer = new BorderPane();
        actionContainer.setLeft(actionLabel);
        actionContainer.setRight(actionPanel);
        actionContainer.setPadding(new Insets(10));
        actionContainer.setStyle("-fx-background-color: lightgray;");
        //-------------------------------------------------------
        
        //text--------------------------------------------
        Rectangle textbox = new Rectangle(900, 130, Color.LIGHTGRAY);
        Text text = new Text("yae por mueng tai law eiei");
        text.setFont(Font.font(20));
        StackPane stackPanetext = new StackPane(textbox,text);
        stackPanetext.setAlignment(Pos.CENTER);
        stackPanetext.setPadding(new Insets(10));
        stackPanetext.setOnMouseClicked(event -> {
            root.setBottom(actionContainer);
        });
        
        //---------------------------------------------
        
        // Move Selection UI -------------------------------------
        Label moveLabel = new Label("Choose a move:");
        moveLabel.setFont(Font.font(20));
        Button move1Button = new Button("Move 1");
        Button move2Button = new Button("Move 2");
        Button move3Button = new Button("Move 3");
        Button move4Button = new Button("Move 4");
        Button backButton = new Button("Back");

        move1Button.setOnAction(event -> {
        	root.setBottom(stackPanetext);
        });
        move2Button.setOnAction(event -> {
        	root.setBottom(stackPanetext);
        });
        move3Button.setOnAction(event -> {
        	root.setBottom(stackPanetext);
        });
        
        move1Button.setMinSize(300, 50);
        move2Button.setMinSize(300, 50);
        move3Button.setMinSize(300, 50);
        move4Button.setMinSize(300, 50);
        backButton.setMinSize(60, 25);
        
        GridPane moveSelectionPanel = new GridPane();
        moveSelectionPanel.add(move1Button, 1, 0);
        moveSelectionPanel.add(move2Button, 1, 1);
        moveSelectionPanel.add(move3Button, 2, 0);
        moveSelectionPanel.add(move4Button, 2, 1);
        moveSelectionPanel.add(backButton, 0, 1);
        moveSelectionPanel.setHgap(10);
        moveSelectionPanel.setVgap(10);
        moveSelectionPanel.setAlignment(Pos.CENTER);
        
        BorderPane moveSelectionContainer = new BorderPane();
        moveSelectionContainer.setLeft(moveLabel);
        moveSelectionContainer.setRight(moveSelectionPanel);
        moveSelectionContainer.setPadding(new Insets(10));
        moveSelectionContainer.setStyle("-fx-background-color: lightgray;");
        //------------------------------------------
        
        // Pokemon Selection UI -------------------------------------
        Button poke1Button = new Button("Pokemon 1");
        Button poke2Button = new Button("Pokemon 2");
        Button poke3Button = new Button("Pokemon 3");
        Button poke4Button = new Button("Pokemon 4");
        Button poke5Button = new Button("Pokemon 5");
        Button backButton1 = new Button("Back");
        
        poke1Button.setOnAction(event -> {
        	root.setBottom(stackPanetext);
        });
        poke2Button.setOnAction(event -> {
        	root.setBottom(stackPanetext);
        });
        poke3Button.setOnAction(event -> {
        	root.setBottom(stackPanetext);
        });
        poke4Button.setOnAction(event -> {
        	root.setBottom(stackPanetext);
        });
        poke5Button.setOnAction(event -> {
        	root.setBottom(stackPanetext);
        });
        
        poke1Button.setMinSize(300, 50);
        poke2Button.setMinSize(300, 50);
        poke3Button.setMinSize(300, 50);
        poke4Button.setMinSize(300, 50);
        poke5Button.setMinSize(300, 50);
        backButton1.setMinSize(300, 50);
        
        GridPane pokemonSelectionContainer = new GridPane();
        pokemonSelectionContainer.add(poke1Button, 0, 0);
        pokemonSelectionContainer.add(poke2Button, 1, 0);
        pokemonSelectionContainer.add(poke3Button, 2, 0);
        pokemonSelectionContainer.add(poke4Button, 1, 1);
        pokemonSelectionContainer.add(poke5Button, 2, 1);
        pokemonSelectionContainer.add(backButton1, 0, 1);
        pokemonSelectionContainer.setHgap(10);
        pokemonSelectionContainer.setVgap(10);
        pokemonSelectionContainer.setAlignment(Pos.CENTER);
        pokemonSelectionContainer.setPadding(new Insets(10));
        pokemonSelectionContainer.setStyle("-fx-background-color: lightgray;");
        //---------------------------------------------
        
        //Bag selection----------------------------------
        VBox contentBox = new VBox(10);
        contentBox.setPadding(new Insets(10));

        for (int i = 1; i <= 7; i++) {
            Button button = new Button("item" + i);
            button.setStyle("-fx-border-width: 1; -fx-padding: 10;");
            button.setMaxWidth(Double.MAX_VALUE);
            button.setOnAction(event -> {
            	root.setBottom(stackPanetext);
            });

            contentBox.getChildren().add(button);
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(contentBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: lightgray;");
        scrollPane.setPrefSize(200, 200);

        Button backButton3 = new Button("Back");
        backButton3.setMinSize(50, 20);
        backButton3.setStyle("-fx-background-color: lightgray;");
        backButton3.setAlignment(Pos.BOTTOM_LEFT);

        BorderPane bagContainer = new BorderPane();
        bagContainer.setCenter(scrollPane);
        bagContainer.setBottom(backButton3);
        BorderPane.setAlignment(backButton3, Pos.BOTTOM_LEFT);
        BorderPane.setMargin(backButton3, new Insets(10));
        
        //-------------------------------------------
        
        
        // Event Handlers
        fightButton.setOnAction(event -> {
            root.setBottom(moveSelectionContainer);
        });

        backButton.setOnAction(event -> {
            root.setBottom(actionContainer);
        });
        
        pokemonButton.setOnAction(event -> {
            root.setBottom(pokemonSelectionContainer);
        });
        
        backButton1.setOnAction(event -> {
            root.setBottom(actionContainer);
        });
        
        bagButton.setOnAction(event -> {
            root.setBottom(bagContainer);
        });
        
        backButton3.setOnAction(event -> {
            root.setBottom(actionContainer);
        });

        // Main layout
        root = new BorderPane();
        root.setPadding(new Insets(20));

        root.setTop(battleField);

        root.setBottom(actionContainer);

        // set Scene and Stage
        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Pokemon Battle");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}