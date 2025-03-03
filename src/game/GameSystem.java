package game;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import entity.Item;
import entity.Potion;
import gui.SceneManager;
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
	
	// Sender
	private void sendFight() {
		// send via socket
		
		// edit our own Battle
	}
	private void sendBag() {
		
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
		// send myPlayer data via socket to peer
		Map<String , Object> data = new HashMap<>();
		data.put("Type" , "InitOpponentData");
		data.put("Opponent", this.myPlayer);
		String json = gson.toJson(data);
		myPeer.getWriter().println(json);
		// receive myOpponent data via socket from peer
		try {
			String jsonString = myPeer.getReader().readLine();
			Map<String , Object> receiveData = gson.fromJson(jsonString, Map.class);
			Player opponent = gson.fromJson(receiveData.get("Opponent").toString(), Player.class);
			this.myOpponent = opponent ; 
		} catch(Exception e) {
			e.printStackTrace();
		}

		// Start battle
		this.battle = new Battle(this.myPlayer, this.myOpponent);
		// Set turn
		if (myPeer.getMode() == Mode.CLIENT) { // Client always start first
			state = GameState.PLAYER_TURN;
		} else {
			state = GameState.OPPONENT_TURN;
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
		return this.myOpponent ; 
	}
	public void setMyOpponent(Player myOpponent) {
		this.myOpponent = myOpponent ; 
	}
	
	
	
}
