package gui;

import java.util.ArrayList;

import entity.Item;
import game.GameSystem;
import game.Move;
import game.Player;
import game.Pokemon;
import game.Status;
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

public class BattleScene {
	private Scene scene;
	private SceneManager sceneManager;
	private Pane oppohpPane;
	private Pane playerPane;
	private Label playerHpText;
	private Label opponentHpText;
	
	public BattleScene(SceneManager sceneManager) {
		this.sceneManager = sceneManager ; 
		BorderPane root = new BorderPane();
		// Player and opponent name
		Player myPlayer = GameSystem.getInstance().getMyPlayer(), oppoPlayer = GameSystem.getInstance().getMyOpponent();
		String strplayerName = myPlayer.getName(), stropponentName = oppoPlayer.getName();
		Label playerName = new Label(strplayerName);
		Label opponentName = new Label(stropponentName);

		// Pokemon images
		Rectangle playerPokemon = new Rectangle(300, 300, Color.GRAY);
		Rectangle opponentPokemon = new Rectangle(300, 300, Color.GRAY);
		Rectangle playerAvatar = new Rectangle(130, 210, Color.GRAY);
		Rectangle opponentAvatar = new Rectangle(130, 210, Color.GRAY);

		// Health bars
		// (player)
		String playerPokeName = myPlayer.getPokemons().get(myPlayer.getCurrentPokemon()).getName(), oppoPokeName = oppoPlayer.getPokemons().get(myPlayer.getCurrentPokemon()).getName();
		Label playerPokemonName = new Label(playerPokeName);
		playerPokemonName.setFont(Font.font(14));
//		Rectangle playerHpBar = new Rectangle(150, 15, Color.LIMEGREEN);
		
		
		Label playerStatus = new Label("No Condition");
		playerStatus.setFont(Font.font(10));
		
		int currentPlayerPokemonIdx = GameSystem.getInstance().getMyPlayer().getCurrentPokemon();
		int maxHpPlayer = GameSystem.getInstance().getMyPlayer().getPokemons().get(currentPlayerPokemonIdx).getMaxHp();
		
		playerHpText = new Label(maxHpPlayer + " / " + maxHpPlayer);
		
		Rectangle backgroundHpBarPlayer = new Rectangle(150, 15, Color.WHITE);
		backgroundHpBarPlayer.setStroke(Color.BLACK); // Set black border
		backgroundHpBarPlayer.setStrokeWidth(1);

        // Foreground HP Bar (Green, shrinks as HP goes down)
		Rectangle foregroundHpBarPlayer = new Rectangle(150, 15, Color.LIMEGREEN);
		foregroundHpBarPlayer.setStroke(Color.BLACK);
		foregroundHpBarPlayer.setStrokeWidth(1);;

        // Calculate percentage HP left
        double percentagePlayer = (double) maxHpPlayer / maxHpPlayer;
        foregroundHpBarPlayer.setWidth(150 * percentagePlayer);
		
		playerPane = new Pane();
		playerPane.getChildren().addAll(backgroundHpBarPlayer, foregroundHpBarPlayer);

		// (opponent)
		Label opponentPokemonName = new Label(oppoPokeName);
		opponentPokemonName.setFont(Font.font(14));
//		Rectangle opponentHpBar = new Rectangle(150, 15, Color.LIMEGREEN);
		
		Label opponentStatus = new Label("No Condition");
		opponentStatus.setFont(Font.font(10));
		
		int currentOppoPokemonIdx = GameSystem.getInstance().getMyPlayer().getCurrentPokemon();
		int maxHpOppo = GameSystem.getInstance().getMyOpponent().getPokemons().get(currentOppoPokemonIdx).getMaxHp();
		
		opponentHpText = new Label(maxHpOppo + " / " + maxHpOppo);
		
        // Background HP Bar (Gray or Red)
		Rectangle backgroundHpBarOppo = new Rectangle(150, 15, Color.WHITE);
		backgroundHpBarOppo.setStroke(Color.BLACK); // Set black border
		backgroundHpBarOppo.setStrokeWidth(1);

        // Foreground HP Bar (Green, shrinks as HP goes down)
		Rectangle foregroundHpBarOppo = new Rectangle(150, 15, Color.LIMEGREEN);
		foregroundHpBarOppo.setStroke(Color.BLACK);
		foregroundHpBarOppo.setStrokeWidth(1);;

        // Calculate percentage HP left
        double percentageOppo = (double) maxHpOppo / maxHpOppo;
        foregroundHpBarOppo.setWidth(150 * percentageOppo); // Adjust green bar width

        // Pane to stack elements
        oppohpPane = new Pane();
        oppohpPane.getChildren().addAll(backgroundHpBarOppo, foregroundHpBarOppo);
        
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

		VBox opponentPokemonInfo = new VBox(opponentPokemonName, oppohpPane, opponentHpText, opponentStatus);
		opponentPokemonInfo.setAlignment(Pos.BOTTOM_RIGHT);

		HBox opponentInfo = new HBox(10, new VBox(10, opponentPokemon, opponentPokemonInfo), opponentAvatar);
		HBox playerInfo = new HBox(10, playerAvatar,
				new VBox(10, playerPokemon, new VBox(playerPokemonName, playerPane, playerHpText, playerStatus)));
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
		// -------------------------------------------------------

		// text--------------------------------------------
		Rectangle textbox = new Rectangle(900, 130, Color.LIGHTGRAY);
		Text text = new Text("yae por mueng tai law eiei");
		text.setFont(Font.font(20));
		StackPane stackPanetext = new StackPane(textbox, text);
		stackPanetext.setAlignment(Pos.CENTER);
		stackPanetext.setPadding(new Insets(10));
		stackPanetext.setOnMouseClicked(event -> {
			root.setBottom(actionContainer);
		});

		// ---------------------------------------------

		// Move Selection UI -------------------------------------
		GridPane moveSelectionPanel = new GridPane();
		Pokemon currentPokemon = GameSystem.getInstance().getMyPlayer().getPokemons().get(GameSystem.getInstance().getMyPlayer().getCurrentPokemon());
		ArrayList<Move> moves = currentPokemon.getMoves();
		Label moveLabel = new Label("Choose a move:");
		moveLabel.setFont(Font.font(20));
		moveSelectionPanel.add(moveLabel, 0, 0);
		
		int rc = 0;
		for(Move move : moves) {
			int col = rc % 2, row = rc/2;
			Button moveButton = new Button(move.getName());
			moveButton.setId(String.valueOf(move.getMoveId()));
			moveButton.setOnAction(event -> {
				GameSystem.getInstance().sendFight(move); // update oppo UI
			});
			moveButton.setMinSize(300, 50);
			moveSelectionPanel.add(moveButton, col, row);
			rc++;
		}
		
		Button backButton = new Button("Back");
		backButton.setMinSize(60, 25);

		moveSelectionPanel.add(backButton, 0, rc / 2 + 1);
		moveSelectionPanel.setHgap(10);
		moveSelectionPanel.setVgap(10);
		moveSelectionPanel.setAlignment(Pos.CENTER);

		BorderPane moveSelectionContainer = new BorderPane();
		moveSelectionContainer.setLeft(moveLabel);
		moveSelectionContainer.setRight(moveSelectionPanel);
		moveSelectionContainer.setPadding(new Insets(10));
		moveSelectionContainer.setStyle("-fx-background-color: lightgray;");
		// ------------------------------------------

		// Pokemon Selection UI -------------------------------------
		GridPane pokemonSelectionContainer = new GridPane();
		ArrayList<Pokemon> currentPokemonList = GameSystem.getInstance().getMyPlayer().getPokemons();
		Button backButton1 = new Button("Back");
		backButton1.setMinSize(300, 50);

		
		int idxPokemon = 0;
		for(int i=0;i < currentPokemonList.size();) {
			Pokemon pokemon = currentPokemonList.get(idxPokemon);
			if(pokemon.equals(currentPokemon)) {
				idxPokemon++;
				continue;
			}
			int col = i % 3, row = i/3;
			if(col == 0 && row == 1) { // BackButton
				pokemonSelectionContainer.add(backButton1, col,row);
				i++;
				continue;
			}
			
			final int index = idxPokemon;
			Button pokeButton = new Button(pokemon.getName());
			pokeButton.setId(String.valueOf(pokemon.getPokemonId()));
			
			pokeButton.setOnAction(event -> {
				System.out.println(index);
				GameSystem.getInstance().sendPokemon(index);
			});
			pokeButton.setMinSize(300, 50);
			pokemonSelectionContainer.add(pokeButton, col, row);
			
			idxPokemon++;
			i++;
		}

		pokemonSelectionContainer.setHgap(10);
		pokemonSelectionContainer.setVgap(10);
		pokemonSelectionContainer.setAlignment(Pos.CENTER);
		pokemonSelectionContainer.setPadding(new Insets(10));
		pokemonSelectionContainer.setStyle("-fx-background-color: lightgray;");
		// ---------------------------------------------

		// Bag selection----------------------------------
		ArrayList<Item> itemList = GameSystem.getInstance().getMyPlayer().getItems();
		VBox contentBox = new VBox(10);
		contentBox.setPadding(new Insets(10));

		for (int i = 0; i < itemList.size(); i++) {
			Item item = itemList.get(i);
			Button button = new Button(item.getName());
			button.setStyle("-fx-border-width: 1; -fx-padding: 10;");
			button.setMaxWidth(Double.MAX_VALUE);
			button.setOnAction(event -> {
				GameSystem.getInstance().sendBag(item);
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

		// -------------------------------------------

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
		
		giveUpButton.setOnAction(event -> {
			GameSystem.getInstance().sendGiveUp();
			System.out.println("You lose");
			sceneManager.showLosingScene();
			// do something?
		});

		// Main layout
		root.setPadding(new Insets(20));
		root.setTop(battleField);
		root.setBottom(actionContainer);

		// set Scene
		scene = new Scene(root, 1200, 700);
	}
	
	public void updateHpBarAndText(int currentHp, int maxHp, Pane hpPane, Label hpText ) {
	    // Clear the current content of the hpPane
	    hpPane.getChildren().clear();

	    // Background HP Bar
	    Rectangle backgroundHpBar = new Rectangle(150, 15, Color.WHITE);
	    backgroundHpBar.setStroke(Color.BLACK); // Set black border
	    backgroundHpBar.setStrokeWidth(1);

	    // Foreground HP Bar (Green, shrinks as HP goes down)
	    Rectangle foregroundHpBar = new Rectangle(150, 15, Color.LIMEGREEN);
	    foregroundHpBar.setStroke(Color.BLACK);
	    foregroundHpBar.setStrokeWidth(1);

	    // Calculate percentage HP left
	    double percentage = (double) currentHp / maxHp;
	    foregroundHpBar.setWidth(150 * percentage); // Adjust green bar width

	    // Add both bars to the hpPane
	    hpPane.getChildren().addAll(backgroundHpBar, foregroundHpBar);
	    hpText.setText(currentHp + " / " + maxHp);
	}

	
	// Method to update the player's HP
    public void updatePlayerHp(int currentHp, int maxHp) {
    	System.out.println("change PlayerHp");
    	updateHpBarAndText(currentHp, maxHp, playerPane, playerHpText);
    }

    // Method to update the opponent's HP
    public void updateOpponentHp(int currentHp, int maxHp) {
    	System.out.println("change OpponentHp");
    	updateHpBarAndText(currentHp, maxHp, oppohpPane, opponentHpText);
    }

    // Method to update status conditions
    public void updateStatus(Player player, Player opponent) {
    }

    // Method to display action feedback text
    public void displayActionFeedback(String feedback) {
    }
    
    public void freeze() {
    	
    }
    public void unfreeze() {
    	
    }
	
	public Scene getScene() {
		return this.scene ; 
	}

}