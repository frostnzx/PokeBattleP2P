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
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.Mode;

public class BattleScene {
	private Scene scene;
	private SceneManager sceneManager;
	private Pane oppoPane;
	private Pane playerPane;
	private Label playerHpText;
	private Label opponentHpText;
	private Label playerStatusText;
	private Label opponentStatusText;
	
	private BorderPane root ; 
	private BorderPane actionContainer ; 
	
	private Rectangle playerPokemon;
	private Rectangle opponentPokemon;
	
	private Label playerPokemonName;
	private Label opponentPokemonName;
	
	private GridPane moveSelectionPanel;
	
	private GridPane pokemonSelectionContainer;
	
	private enum Turn {
		MYTURN , NOTMYTURN
	}
	private Turn turn ;

	public BattleScene(SceneManager sceneManager) {
        if(GameSystem.getInstance().getMyPeer().getMode() == Mode.CLIENT) {
            turn = Turn.MYTURN ; 
        } else {
            turn = Turn.NOTMYTURN ; 
        }
		this.sceneManager = sceneManager;
		this.root = new BorderPane();
		// Player and opponent name
		Player myPlayer = GameSystem.getInstance().getMyPlayer(), oppoPlayer = GameSystem.getInstance().getMyOpponent();
		String strplayerName = myPlayer.getName(), stropponentName = oppoPlayer.getName();
		Label playerName = new Label(strplayerName);
		Label opponentName = new Label(stropponentName);

		// Pokemon images
		playerPokemon = new Rectangle(300, 300, Color.GRAY);
		opponentPokemon = new Rectangle(300, 300, Color.GRAY);
		Rectangle playerAvatar = new Rectangle(130, 210, Color.GRAY);
		Rectangle opponentAvatar = new Rectangle(130, 210, Color.GRAY);
		
		// Add background picture to Avatar
        String playerFilePath = GameSystem.getInstance().getMyPlayer().getFilePath();
        String opponentFilePath = (playerFilePath.equals("res/Trainers/trainer6.png") ? "res/Trainers/trainer7.png" : "res/Trainers/trainer6.png");
        GameSystem.getInstance().getMyOpponent().setFilePath(opponentFilePath);
        System.out.println(opponentFilePath);
        Image playerAvatarImg = new Image("file:" + playerFilePath), opponentAvatarImg = new Image("file:" + opponentFilePath);
        ImagePattern playerImagePattern = new ImagePattern(playerAvatarImg), opponentImagePattern = new ImagePattern(opponentAvatarImg);
        playerAvatar.setFill(playerImagePattern);
        opponentAvatar.setFill(opponentImagePattern);
        
        // Add background picture to Pokemon
        String pokemonPlayerFilePath = GameSystem.getInstance().getMyPlayer().getActualCurrentPokemon().getFilePath();
        String pokemonOpponentFilePath = GameSystem.getInstance().getMyOpponent().getActualCurrentPokemon().getFilePath();
        Image playerPokemonImg = new Image("file:" + pokemonPlayerFilePath), opponentPokemonImg = new Image("file:" + pokemonOpponentFilePath);
        ImagePattern playerPokemonImagePattern = new ImagePattern(playerPokemonImg), opponentPokemonImagePattern = new ImagePattern(opponentPokemonImg);
        playerPokemon.setFill(playerPokemonImagePattern);
        opponentPokemon.setFill(opponentPokemonImagePattern);
        playerPokemon.setStroke(Color.BLACK);
        playerPokemon.setStrokeWidth(1);
        opponentPokemon.setStroke(Color.BLACK);
        opponentPokemon.setStrokeWidth(1);
        
        
		// Health bars
		// (player)
		String playerPokeName = myPlayer.getPokemons().get(myPlayer.getCurrentPokemon()).getName(),
				oppoPokeName = oppoPlayer.getPokemons().get(myPlayer.getCurrentPokemon()).getName();
		playerPokemonName = new Label(playerPokeName);
		playerPokemonName.setFont(Font.font(14));
//		Rectangle playerHpBar = new Rectangle(150, 15, Color.LIMEGREEN);

		Label playerStatus = new Label("No Condition");
		playerStatus.setFont(Font.font(10));
		playerStatus.setTextFill(Color.GRAY);
		this.playerStatusText = playerStatus;

		int currentPlayerPokemonIdx = GameSystem.getInstance().getMyPlayer().getCurrentPokemon();
		int maxHpPlayer = GameSystem.getInstance().getMyPlayer().getPokemons().get(currentPlayerPokemonIdx).getMaxHp();

		playerHpText = new Label(maxHpPlayer + " / " + maxHpPlayer);

		Rectangle backgroundHpBarPlayer = new Rectangle(150, 15, Color.WHITE);
		backgroundHpBarPlayer.setStroke(Color.BLACK); // Set black border
		backgroundHpBarPlayer.setStrokeWidth(1);

		// Foreground HP Bar (Green, shrinks as HP goes down)
		Rectangle foregroundHpBarPlayer = new Rectangle(150, 15, Color.LIMEGREEN);
		foregroundHpBarPlayer.setStroke(Color.BLACK);
		foregroundHpBarPlayer.setStrokeWidth(1);
		;

		// Calculate percentage HP left
		double percentagePlayer = (double) maxHpPlayer / maxHpPlayer;
		foregroundHpBarPlayer.setWidth(150 * percentagePlayer);

		playerPane = new Pane();
		playerPane.getChildren().addAll(backgroundHpBarPlayer, foregroundHpBarPlayer);

		// (opponent)
		opponentPokemonName = new Label(oppoPokeName);
		opponentPokemonName.setFont(Font.font(14));
//		Rectangle opponentHpBar = new Rectangle(150, 15, Color.LIMEGREEN);

		Label opponentStatus = new Label("No Condition");
		opponentStatus.setTextFill(Color.GRAY);
		opponentStatus.setFont(Font.font(10));
		this.opponentStatusText = opponentStatus;

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
		foregroundHpBarOppo.setStrokeWidth(1);
		;

		// Calculate percentage HP left
		double percentageOppo = (double) maxHpOppo / maxHpOppo;
		foregroundHpBarOppo.setWidth(150 * percentageOppo); // Adjust green bar width

		// Pane to stack elements
		oppoPane = new Pane();
		oppoPane.getChildren().addAll(backgroundHpBarOppo, foregroundHpBarOppo);

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

		VBox opponentPokemonInfo = new VBox(opponentPokemonName, oppoPane, opponentHpText, opponentStatus);
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

		actionContainer = new BorderPane();
		actionContainer.setLeft(actionLabel);
		actionContainer.setRight(actionPanel);
		actionContainer.setPadding(new Insets(10));
		actionContainer.setStyle("-fx-background-color: lightgray;");
		this.actionContainer = actionContainer ; 

		// Move Selection UI -------------------------------------
		moveSelectionPanel = new GridPane();
		Pokemon currentPokemon = GameSystem.getInstance().getMyPlayer().getPokemons()
				.get(GameSystem.getInstance().getMyPlayer().getCurrentPokemon());
		ArrayList<Move> moves = currentPokemon.getMoves();
		Label moveLabel = new Label("Choose a move:");
		moveLabel.setFont(Font.font(20));
		moveSelectionPanel.add(moveLabel, 0, 0);

		int rc = 0;
		for (Move move : moves) {
			int col = rc % 2, row = rc / 2;
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
		pokemonSelectionContainer = new GridPane();
		ArrayList<Pokemon> currentPokemonList = GameSystem.getInstance().getMyPlayer().getPokemons();
		Button backButton1 = new Button("Back");
		backButton1.setMinSize(300, 50);

		int idxPokemon = 0;
		for (int i = 0; i < currentPokemonList.size();) {
			Pokemon pokemon = currentPokemonList.get(idxPokemon);
			if (pokemon.equals(currentPokemon)) {
				idxPokemon++;
				continue;
			}
			int col = i % 3, row = i / 3;
			if (col == 0 && row == 1) { // BackButton
				pokemonSelectionContainer.add(backButton1, col, row);
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

	public void updateHpBarAndText(int currentHp, int maxHp, Pane hpPane, Label hpText) {
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
	
	public void updatePokemonName(Pokemon pokemon, Label pokemonName) {
		pokemonName.setText(pokemon.getName());
	}
	
	public void updatePokemonMove(Pokemon pokemon, GridPane moveSelectionPanel) {
		moveSelectionPanel.getChildren().clear();
	
		Pokemon currentPokemon = GameSystem.getInstance().getMyPlayer().getPokemons()
				.get(GameSystem.getInstance().getMyPlayer().getCurrentPokemon());
		ArrayList<Move> moves = currentPokemon.getMoves();
		Label moveLabel = new Label("Choose a move:");
		moveLabel.setFont(Font.font(20));
		moveSelectionPanel.add(moveLabel, 0, 0);

		int rc = 0;
		for (Move move : moves) {
			int col = rc % 2, row = rc / 2;
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
	}
	
	public void updatePokemonSelectionContainer(Pokemon currentPokemon, GridPane pokemonSelectionContainer) {
		pokemonSelectionContainer.getChildren().clear();
		
		ArrayList<Pokemon> currentPokemonList = GameSystem.getInstance().getMyPlayer().getPokemons();
		Button backButton1 = new Button("Back");
		backButton1.setMinSize(300, 50);

		int idxPokemon = 0;
		for (int i = 0; i < currentPokemonList.size();) {
			Pokemon pokemon = currentPokemonList.get(idxPokemon);
			if (pokemon.equals(currentPokemon)) {
				idxPokemon++;
				continue;
			}
			int col = i % 3, row = i / 3;
			if (col == 0 && row == 1) { // BackButton
				pokemonSelectionContainer.add(backButton1, col, row);
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
	}
	
	public void updatePokemonAvatar(Rectangle PokemonRectangle, Pokemon pokemon) {
        Image PokemonImg = new Image("file:" + pokemon.getFilePath());
        ImagePattern playerPokemonImagePattern = new ImagePattern(PokemonImg);
        
        PokemonRectangle.setFill(playerPokemonImagePattern);
	}

	// Method to update the player's HP
	public void updatePlayerHp(int currentHp, int maxHp) {
		System.out.println("change PlayerHp");
		updateHpBarAndText(currentHp, maxHp, playerPane, playerHpText);
	}

	// Method to update the opponent's HP
	public void updateOpponentHp(int currentHp, int maxHp) {
		System.out.println("change OpponentHp");
		updateHpBarAndText(currentHp, maxHp, oppoPane, opponentHpText);
	}

	// Method to update status conditions
	public void updateStatus(Player player, Status status) {
		// validate if updating player is our player ?
		Label statusToUpdate;
		if (player.equals(GameSystem.getInstance().getMyPlayer())) {
			// update our player's status
			statusToUpdate = playerStatusText;
		} else {
			// update oppo's status
			statusToUpdate = opponentStatusText;
		}
		if (status == Status.BRN) {
			statusToUpdate.setText("BURN");
			statusToUpdate.setTextFill(Color.RED);
		} else if (status == Status.FRZ) {
			statusToUpdate.setText("FREEZE");
			statusToUpdate.setTextFill(Color.LIGHTBLUE);
		} else if (status == Status.PAR) {
			statusToUpdate.setText("PARALYZED");
			statusToUpdate.setTextFill(Color.YELLOW);
		} else if (status == Status.PSN) {
			statusToUpdate.setText("POISONED");
			statusToUpdate.setTextFill(Color.PURPLE);
		} else if (status == Status.SLP) {
			statusToUpdate.setText("SLEEP");
			statusToUpdate.setTextFill(Color.BLUE);
		}else {
			statusToUpdate.setText("No Condition");
			statusToUpdate.setTextFill(Color.GRAY);
		}
	}

	public void updateCurrentPokemon(Player player, Pokemon pokemon) {
		if(player == GameSystem.getInstance().getMyPlayer()) {
			updatePokemonAvatar(playerPokemon,pokemon);
			int currentHp = GameSystem.getInstance().getMyPlayer().getActualCurrentPokemon().getHp();
			int maxHp = GameSystem.getInstance().getMyPlayer().getActualCurrentPokemon().getMaxHp();
			updateHpBarAndText(currentHp,maxHp,playerPane,playerHpText);
			updateStatus(player, pokemon.getStatus());
			updatePokemonName(pokemon, playerPokemonName);
			updatePokemonMove(pokemon, moveSelectionPanel);
			updatePokemonSelectionContainer(player.getActualCurrentPokemon(), pokemonSelectionContainer);
		}
		else {
			updatePokemonAvatar(opponentPokemon,pokemon);
			int currentHp = GameSystem.getInstance().getMyOpponent().getActualCurrentPokemon().getHp();
			int maxHp = GameSystem.getInstance().getMyOpponent().getActualCurrentPokemon().getMaxHp();
			updateHpBarAndText(currentHp,maxHp,oppoPane,opponentHpText);
			updateStatus(player, pokemon.getStatus());
			updatePokemonName(pokemon,opponentPokemonName);
		}
	}

	// Method to display action feedback text
	public void displayActionFeedback(String feedback) {
		Rectangle textbox = new Rectangle(900, 130, Color.LIGHTGRAY);
		Text text = new Text(feedback);
		text.setFont(Font.font(20));
		StackPane stackPanetext = new StackPane(textbox, text);
		stackPanetext.setAlignment(Pos.CENTER);
		stackPanetext.setPadding(new Insets(10));
		stackPanetext.setOnMouseClicked(event -> {
			if(this.turn == Turn.MYTURN) {
				root.setBottom(actionContainer);
			}
		});
		root.setBottom(stackPanetext);
	}

	public void freeze() {
		Rectangle textbox = new Rectangle(900, 130, Color.LIGHTGRAY);
		Text text = new Text("It's your opponent turn, wait for his move!");
		text.setFont(Font.font(20));
		StackPane stackPanetext = new StackPane(textbox, text);
		stackPanetext.setAlignment(Pos.CENTER);
		stackPanetext.setPadding(new Insets(10));
		
		if(root.getBottom() == this.actionContainer) {
			root.setBottom(stackPanetext);
		} else {
			root.getBottom().setOnMouseClicked(event -> {
				root.setBottom(stackPanetext);
			});
		}
		
		this.turn = Turn.NOTMYTURN ; 
	}

	public void unfreeze() {
		root.getBottom().setOnMouseClicked(event -> {
			root.setBottom(actionContainer);
		});
		this.turn = Turn.MYTURN ; 
	}

	public Scene getScene() {
		return this.scene;
	}

}