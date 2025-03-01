package game;

import net.Peer;

public class GameSystem {
	public enum GameState {
		PLAYER_TURN,
		OPPONENT_TURN,
		PROCESSING_TURN,
		CHECK_BATTLE_END,
		BATTLE_END
	};
	private static GameSystem instance ; 
	private Thread playingThread ; 
	private Peer myPeer ; 
	private Player myPlayer , myOpponent ; 
	private GameState state ; 
	
	
	public GameSystem() {

	}
	public static GameSystem getInstance() {
		if(instance == null) {
			instance = new GameSystem();
		}
		return instance ; 
	}
	public void setMyPeer(Peer myPeer) {
		this.myPeer = myPeer ; 
		// can get reader & writer from this peer
	}
	// write battle logic here
	
}
