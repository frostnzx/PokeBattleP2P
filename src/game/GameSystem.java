package game;

import net.Mode;
import net.Peer;

public class GameSystem {
	public enum GameState {
		PLAYER_TURN, OPPONENT_TURN, PROCESSING_TURN, CHECK_BATTLE_END, BATTLE_END
	};

	private static GameSystem instance;
	private Thread playingThread;
	private Peer myPeer;
	private Player myPlayer, myOpponent;
	private Battle battle;
	private GameState state;

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

	// FSM switch
	private void processState() {
		switch (state) {
			case PLAYER_TURN -> playerTurnHandler();
			case OPPONENT_TURN -> opponentTurnHandler();
			case PROCESSING_TURN -> processingTurnHandler();
			case CHECK_BATTLE_END -> checkBattleEndHandler();
			case BATTLE_END -> battleEndHandler();
		}
	}

	// all state handler functions
	private void playerTurnHandler() {
		
		
		
		processState();
	}

	private void opponentTurnHandler() {

		
		
		processState();
	}

	private void processingTurnHandler() {

		
		
		processState();
	}

	private void checkBattleEndHandler() {

		
		processState();
	}

	private void battleEndHandler() {

		
		processState();
	}

	// for start battle
	public void startBattle(Player myPlayer, Player myOpponent) {
		this.myPlayer = myPlayer;
		this.myOpponent = myOpponent;
		this.battle = new Battle(myPlayer, myOpponent);
		if (myPeer.getMode() == Mode.CLIENT) { // Client always start first
			state = GameState.PLAYER_TURN;
		} else {
			state = GameState.OPPONENT_TURN;
		}
	}
	
}
