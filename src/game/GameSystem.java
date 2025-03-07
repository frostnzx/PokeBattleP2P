package game;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import entity.Item;
import entity.Potion;
import gui.BattleScene;
import gui.SceneManager;
import javafx.application.Platform;
import net.Mode;
import net.Peer;
import utils.ItemTypeAdapter;

public class GameSystem {
	private static GameSystem instance;
	private Thread playingThread;
	private Peer myPeer;
	private Player myPlayer, myOpponent;
	private Battle battle;
	private boolean lastTurnByPlayer;

	private SceneManager sceneManager;

	private boolean win;

	public GameSystem() {
	}

	public static GameSystem getInstance() {
		if (instance == null) {
			instance = new GameSystem();
		}
		return instance;
	}

	public void setMyPeer(Peer myPeer) {
		this.myPeer = myPeer; // Can get writer & reader from this peer
	}

	public Peer getMyPeer() {
		return this.myPeer;
	}

	public Battle getBattle() {
		return this.battle;
	}

	public void setSceneManager(SceneManager sceneManager) {
		this.sceneManager = sceneManager;
	}

	// Sender
	public void sendFight(Move move) {
		// send via socket
		Map<String, Object> data = new HashMap<>();
		Gson gson = new GsonBuilder().registerTypeAdapter(Item.class, new ItemTypeAdapter()).create(); // Register the custom
																								// adapter for Item
		data.put("Type", "Fight");
		data.put("Move", move);
		data.put("Player", this.myPlayer);
		String json = gson.toJson(data);
		myPeer.getWriter().println(json);
		myPeer.getWriter().flush();
		// edit our own Battle
		boolean isAttackSuccessful = battle.executeMove(this.myPlayer, move);
		// because sending fight means ending our turn so, freeze your button! (if the attack success)
		if(isAttackSuccessful) {
			battle.executeStatus();
			this.battle.freezeTurn();
		}
	}

	public void sendBag(Item item) {
	    Map<String, Object> data = new HashMap<>();
	    Gson gson = new GsonBuilder().registerTypeAdapter(Item.class, new ItemTypeAdapter()).create();
	    
	    // Serialize the Item with its itemType and data
	    JsonObject itemJsonObject = new JsonObject();
	    itemJsonObject.addProperty("itemType", item.getClass().getSimpleName()); // Add the item type
	    itemJsonObject.add("data", gson.toJsonTree(item)); // Serialize the actual Item data

	    data.put("Type", "Bag");
	    data.put("Player", this.myPlayer);
	    data.put("Item", itemJsonObject);  // Add the serialized Item object
	    
	    String json = gson.toJson(data);
	    myPeer.getWriter().println(json);
	    myPeer.getWriter().flush();
	    
	    // Optionally, call battle logic
	    battle.executeItem(this.myPlayer, item);
	}


	public void sendPokemon(int newPokemonIndex) {
		Map<String, Object> data = new HashMap<>();
		Gson gson = new GsonBuilder().registerTypeAdapter(Item.class, new ItemTypeAdapter()).create(); // Register the custom
																								// adapter for Item
				
		data.put("Type", "Pokemon");
		data.put("Player", this.myPlayer);
		data.put("newPokemonIndex", newPokemonIndex);
		String json = gson.toJson(data);
		myPeer.getWriter().println(json);
		myPeer.getWriter().flush();
		// edit our own battle
		battle.changeCurrentPokemon(this.myPlayer, newPokemonIndex);
	}

	public void sendGiveUp() {
		Map<String, Object> data = new HashMap<>();
		Gson gson = new GsonBuilder().registerTypeAdapter(Item.class, new ItemTypeAdapter()) // Register the custom
																								// adapter for Item
				.create();
		data.put("Type", "GiveUp");
		String json = gson.toJson(data);
		myPeer.getWriter().println(json);
		myPeer.getWriter().flush();
		// already edit our own by showing losing scene
	}

	private void startReceivingThread() {
		// setup receiving thread
		// continuously accepting data as long as the game still running
		Thread receivingThread = new Thread(() -> {
			try {
				String jsonString;
				Gson gson = new GsonBuilder().registerTypeAdapter(Item.class, new ItemTypeAdapter()).create(); // Register our
																										// adapter
						
				while ((jsonString = myPeer.getReader().readLine()) != null) {
					if (jsonString.isEmpty()) {
						System.out.println("Empty packet");
						continue;
					}
					Map<String, Object> receiveData = gson.fromJson(jsonString, Map.class);
					String type = (String) receiveData.get("Type");
					// handle from type
					// ...
					if (type.equals("Fight")) { // Got hit
						System.out.println("Receive Fight");
						JsonElement moveJsonElement = gson.toJsonTree(receiveData.get("Move"));
						JsonElement playerJsonElement = gson.toJsonTree(receiveData.get("Player"));
						JsonElement pokemonJsonElement = gson.toJsonTree(receiveData.get("Pokemon"));

						Pokemon oppoPokemon = gson.fromJson(pokemonJsonElement, Pokemon.class);
						Move oppoMove = gson.fromJson(moveJsonElement, Move.class);
						Player oppoPlayer = gson.fromJson(playerJsonElement, Player.class);

						boolean isAttackSuccessful = battle.executeMove(oppoPlayer, oppoMove);
						// receive fight means the opponent turn just ended, so unfreeze ourself
						if(isAttackSuccessful) {
							battle.executeStatus();
							this.battle.unfreezeTurn();
							
							if(!this.myPlayer.isAlive()) {
								Platform.runLater(() -> {
									sceneManager.showLosingScene();
								});
								sendGiveUp();
							}
						}
					} else if (type.equals("Bag")) {
						JsonElement playerJsonElement = gson.toJsonTree(receiveData.get("Player"));
                        JsonElement itemJsonElement = gson.toJsonTree(receiveData.get("Item"));

                        Player oppoPlayer = gson.fromJson(playerJsonElement, Player.class);
                        Item chosenItem = gson.fromJson(itemJsonElement, Item.class);
						battle.executeItem(oppoPlayer, chosenItem);
					} else if (type.equals("Pokemon")) {
						Number newPokemonIndexNumber = (Number) receiveData.get("newPokemonIndex");
						JsonElement playerJsonElement = gson.toJsonTree(receiveData.get("Player"));

						Player oppoPlayer = gson.fromJson(playerJsonElement , Player.class);
						int newPokemonIndex = newPokemonIndexNumber.intValue(); 
						System.out.println("Send successful");
						battle.changeCurrentPokemon(oppoPlayer, newPokemonIndex);

					} else if (type.equals("GiveUp")) {
						// end game?
						battle.StopPlaying();
; 						System.out.println("You win");
						Platform.runLater(() -> {
							sceneManager.showWinningScene();
						});	
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		receivingThread.start();
	}

	// for start battle
	// start battle when peers connected so we have opponent
	public void startBattle() {
		Gson gson = new GsonBuilder().registerTypeAdapter(Item.class, new ItemTypeAdapter()).create(); // Register the custom
																								// adapter for Item
				
		// Send myPlayer data via socket to peer
		try {
			Map<String, Object> data = new HashMap<>();
			data.put("Type", "InitOpponentData");
			data.put("Opponent", this.myPlayer);
			String json = gson.toJson(data);
			myPeer.getWriter().println(json);
			myPeer.getWriter().flush();
			System.out.println("Data sended Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Start a thread to receive opponent data
		new Thread(() -> {
			try {
				String jsonString;
				while ((jsonString = myPeer.getReader().readLine()) == null) {
					// Keep waiting (blocking, but in a separate thread)
				}
				System.out.println("Data received Successfully");
				System.out.println(jsonString);
				// BUG HERE
				Map<String, Object> receiveData = gson.fromJson(jsonString, Map.class);
                JsonElement opponentJsonElement = gson.toJsonTree(receiveData.get("Opponent"));
                Player opponent = gson.fromJson(opponentJsonElement, Player.class);

				this.myOpponent = opponent;

				// Now that opponent data is received, start battle on the main thread
				Platform.runLater(() -> {
					try {
						startBattlePhase();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});

			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}
	

	private void startBattlePhase() throws IOException {
		this.battle = new Battle(this.myPlayer, this.myOpponent);

		sceneManager.showBattleScene();
		// Set turn
		if (myPeer.getMode() == Mode.SERVER) { // Client always start first
			this.battle.freezeTurn();
		}
		// Start receiver thread
		startReceivingThread();
	}

	public Player getMyPlayer() {
		return this.myPlayer;
	}

	public void setMyPlayer(Player myPlayer) {
		this.myPlayer = myPlayer;
	}

	public Player getMyOpponent() {
		return this.myOpponent;
	}

	public void setMyOpponent(Player myOpponent) {
		this.myOpponent = myOpponent;
	}

}
