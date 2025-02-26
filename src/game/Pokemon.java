package game;

import java.util.ArrayList ; 


public class Pokemon {
	// general info
	private String name ; 
	private int pokemonId ; 
	private ArrayList<PokemonType> pokemonTypes ; 
	
	// basic stats
	private int hp ; // current hp
	private int max_hp ; 
	private int attack ; 
	private int defense ; 
	private int specialAttack ; 
	private int specialDefense ; 
	private int speed ; 
	
	// moves
	private ArrayList<Move> moves ; 
	
	// status
	private Status status ; 
	
	// static list to hold all available pokemon
	private static ArrayList<Pokemon> pokemonList = new ArrayList<>();
	
	// to create new pokemon
	public Pokemon(int pokemonId) {
		// TODO:
		// set this pokemon to pokemon that has this id in pokemonList 
	}
	
	public void doMove(Move move , Pokemon enemyPokemon) {
		// TODO -> Do move to enemy's pokemon
	}
	
	public boolean isDead() {
		return (this.hp == 0);
	}
	
	public static void listPokemons() {
		// TODO :
		// show all pokemon in pokemon list
	}
	public static void loadPokemonsFromJson(String filePath) {
		// TODO :
		// Load pokemon from json file
	}
	
	// getters & setters
	public String getName() {
		return name;
	}
	public int getPokemonId() {
		return pokemonId;
	}

	public ArrayList<PokemonType> getPokemonTypes() {
		return pokemonTypes;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getMax_hp() {
		return max_hp;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getSpecialAttack() {
		return specialAttack;
	}

	public void setSpecialAttack(int specialAttack) {
		this.specialAttack = specialAttack;
	}

	public int getSpecialDefense() {
		return specialDefense;
	}

	public void setSpecialDefense(int specialDefense) {
		this.specialDefense = specialDefense;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public ArrayList<Move> getMoves() {
		return moves;
	}
	public void setMoves(ArrayList<Move> moves) {
		this.moves = moves;
	}
	public void addMove(Move move) {
		this.moves.add(move);
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
}
