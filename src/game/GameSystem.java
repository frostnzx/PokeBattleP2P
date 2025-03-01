package game;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import entity.Item;
import entity.Potion;
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
		Choice choice = Choice.Fight ; // Mocking data
		Gson gson = new Gson();
		Map<String , Object> data = new HashMap<>();
		String json ;
		// --- Fight ---
		if(choice == Choice.Fight) {
			Move chosenMove = new Move("Test Move", 9999 , PokemonType.Dragon , 20 , Status.BRN) ; // Mocking data
			// Edit our own Battle
			this.battle.executeMove(myPlayer, myPlayer.getPokemons().get(myPlayer.getCurrentPokemon()), chosenMove);
			// Send JSON to edit their Battle
			data.put("Type" , "Fight");
			data.put("Player", myPlayer);
			data.put("Pokemon", myPlayer.getPokemons().get(myPlayer.getCurrentPokemon()));
			data.put("Move", chosenMove);
		}
		
		// --- Bag ---
		if(choice == Choice.Bag) {
			Item chosenItem = new Potion(); // Mocking data (real data from UI button)
			// Edit our own Battle
			this.battle.executeItem(myPlayer , chosenItem);
			// Send JSON to edit their Battle
			data.put("Type", "Bag");
			data.put("Player", myPlayer);
			data.put("Item", chosenItem);
		}
		
		// --- Pokemon ---
		if(choice == Choice.Pokemon) {
			int newPokemonIndex = 1 ; // Mocking data
			// Edit our own Battle
			this.battle.changeCurrentPokemon(myPlayer, newPokemonIndex);
			// Send JSON
			data.put("Type", "Pokemon");
			data.put("Player", myPlayer);
			data.put("Index", newPokemonIndex);
		}
		
		// --- Give up ---
		if(choice == Choice.GiveUp) {
			// do something that will make myPlayer lose 
			this.win = false;
            data.put("Type", "GiveUp");
            data.put("Win", "True");
            state = GameState.BATTLE_END;
		}
		
		json = gson.toJson(data);
		myPeer.getWriter().println(json);
		System.out.println("Sent: " + json);
		if(choice == Choice.Fight) {
			state = GameState.PROCESSING_TURN ; 
		}
		processState();
	}

	private void opponentTurnHandler() {
        try {
            String jsonString = myPeer.getReader().readLine();
            
            if(jsonString != null) {
                Gson gson = new Gson();
                Map<String, Object> data = gson.fromJson(jsonString, Map.class);
                
                String type = (String) data.get("Type");
                
                if(type == "Fight") {
                    Player opponentPlayer = gson.fromJson(data.get("Player").toString(), Player.class);
                    Pokemon opponentPokemon = gson.fromJson(data.get("Pokemon").toString(), Pokemon.class);
                    Move chosenMove = gson.fromJson(data.get("Move").toString(), Move.class);
                    
                    this.battle.executeMove(opponentPlayer, opponentPokemon, chosenMove);
                }
                else if(type == "Bag") {
                    Player opponentPlayer = gson.fromJson(data.get("Player").toString(), Player.class);
                    Item chosenItem = gson.fromJson(data.get("Item").toString(), Item.class);
                    
                    this.battle.executeItem(opponentPlayer , chosenItem);
                }
                else if(type == "Pokemon") {
                    Player opponentPlayer = gson.fromJson(data.get("Player").toString(), Player.class);
                    int newPokemonIndex = ((Double) data.get("Index")).intValue();

                    this.battle.changeCurrentPokemon(opponentPlayer, newPokemonIndex);
                }
                else if(type == "GiveUp") {
                	win = (Boolean) data.get("Win");
                	state = GameState.BATTLE_END ; 
                }
                if(type != "GiveUp") {
                	this.state = GameState.PROCESSING_TURN;
                }
            }    
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        processState();
    }

	private void processingTurnHandler() {
		
		
		// do status
		// change turn
		processState();
	}

	private void checkBattleEndHandler() {

		
		processState();
	}

	private void battleEndHandler() {
		if(win) {
			
		} else {
			
		}
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
