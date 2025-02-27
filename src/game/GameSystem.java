package game;

import net.Peer;

public class GameSystem {
	private static GameSystem instance ; 
	private Thread playingThread ; 
	private Peer myPeer ; 
	private Player myPlayer ; 
	
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
	// write all the logic here

}
