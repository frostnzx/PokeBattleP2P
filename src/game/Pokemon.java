import java.util.ArrayList ; 


public class Pokemon {
	// general info
	private String name ; 
	private int pokemonId ; 
	private ArrayList<Type> types ; 
	
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
	
	public Pokemon(int pokemonId) {
		this.pokemonId = pokemonId ; 
		// TODO -> Load Pokemon info from JSON file
	}
	
	public void doMove(Move move , Pokemon enemyPokemon) {
		// TODO -> Do move to enemy's pokemon
	}
	
	public boolean isDead() {
		return (this.hp == 0);
	}
	
	// getters & setters
	public String getName() {
		return name;
	}
	public int getPokemonId() {
		return pokemonId;
	}

	public ArrayList<Type> getTypes() {
		return types;
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
