package game;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import entity.Item;
import entity.Potion;
import gui.SceneManager;
import javafx.application.Platform;
import net.Mode;
import net.Peer;

public class GameSystem {
	public enum GameState {
		PLAYER_TURN, OPPONENT_TURN
	};

	private static GameSystem instance;
	private Thread playingThread;
	private Peer myPeer;
	private Player myPlayer, myOpponent;
	private Battle battle;
	private GameState state;
	private boolean lastTurnByPlayer;
	
	private SceneManager sceneManager ; 
	
	private boolean win ; 

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
		return this.myPeer ; 
	}
	
	public Battle getBattle() {
		return this.battle ; 
	}
	
	public void setSceneManager(SceneManager sceneManager) {
		this.sceneManager = sceneManager ; 
	}
	
	// Sender
	public void sendFight(Pokemon pokemon, Move move, Player player) {
		// send via socket
		Map<String, Object> data = new HashMap<>();
		Gson gson = new Gson();
		data.put("Type", "Fight");
		data.put("Pokemon", pokemon);
		data.put("Move", move);
		data.put("Player", player);
		String json = gson.toJson(data);
        myPeer.getWriter().println(json);
        myPeer.getWriter().flush();
		// edit our own Battle
	}
	private void sendBag(Pokemon pokemon, Item item, Player player) {
		Map<String, Object> data = new HashMap<>();
		Gson gson = new Gson();
		data.put("Type", "Bag");
//		data.put("Pokemon", pokemon);
		data.put("Item", item);
		data.put("Player", player);
		String json = gson.toJson(data);
        myPeer.getWriter().println(json);
        myPeer.getWriter().flush();
	}
	private void sendPokemon() {
		
	}
	private void sendGiveUp() {
		
	}
	private void sendTurnEnded() {
		// "Type" : "TurnEnd"
		// Then switch turn (unlock freeze button)
	}
	// Receiver
	private void startReceivingThread() {
		// setup receiving thread
		// continuously accepting data as long as the game still running
		Thread receivingThread = new Thread(() -> {
			try {
				String jsonString ;
				Gson gson = new Gson();
				while((jsonString = myPeer.getReader().readLine()) != null) {
					if(jsonString.isEmpty()) {
						System.out.println("Empty packet");
						continue;
					} 
					Map<String , Object> receiveData = gson.fromJson(jsonString, Map.class);
					String type = (String) receiveData.get("Type");
					// handle from type
					// ...
					if(type == "Fight") { // Got hit
						Pokemon oppoPokemon = gson.fromJson(receiveData.get("Pokemon").toString(), Pokemon.class);
						Move oppoMove = gson.fromJson(receiveData.get("Move").toString(), Move.class);
						Player oppoPlayer = gson.fromJson(receiveData.get("Player").toString(), Player.class); 
						battle.executeMove(oppoPlayer, oppoPokemon, oppoMove);
					}
					else if(type == "Bag") {
//						Pokemon oppoPokemon = gson.fromJson(receiveData.get("Pokemon").toString(), Pokemon.class);
						Player oppoPlayer = gson.fromJson(receiveData.get("Player").toString(), Player.class);
						Item item = gson.fromJson(receiveData.get("Item").toString(), Item.class);
						battle.executeItem(oppoPlayer, item);
					}
					else if(type == "Pokemon") {
						
					}
					else if(type == "TurnEnded") {
						state = GameState.PLAYER_TURN;
						// do something?
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
	    Gson gson = new Gson();

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
	            Player opponent = gson.fromJson(gson.toJson(receiveData.get("Opponent")), Player.class);

	            this.myOpponent = opponent;

	            // Now that opponent data is received, start battle on the main thread
	            Platform.runLater(() -> {
	            	 startBattlePhase();
	            });

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }).start();
	}

	private void startBattlePhase() {
	    this.battle = new Battle(this.myPlayer, this.myOpponent);

	    // Set turn
	    if (myPeer.getMode() == Mode.CLIENT) { // Client always starts first
	        state = GameState.PLAYER_TURN;
	    } else {
	        state = GameState.OPPONENT_TURN;
	    }
	    sceneManager.showBattleScene();
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
		return this.myOpponent ; 
	}
	public void setMyOpponent(Player myOpponent) {
		this.myOpponent = myOpponent ; 
	}
	
	
	
}
