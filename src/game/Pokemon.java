package game;

<<<<<<< HEAD

public abstract class Pokemon {
	// this is a super class of Pokemon
	protected String name;
	protected int hp;
	// This is normal attack power
	protected int power;
	// This is special attack that need to cooldown
	protected int specialAttackPower;

	public Pokemon(String name, int hp, int power) {
		setName(name);
		setHp(hp);
		setPower(power);

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		// Max H
		if (hp < 0) {
			this.hp = 0;
		} else if (hp > 100) {
			this.hp = 100;
		} else {
			this.hp = hp;
		}

	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		// Max normal power is no more than 10
		if (power < 0) {
			this.power = 0;
		} else if (power > 10) {
			this.power = 10;
		} else {
			this.power = power;
		}
	}

	public int getSpecialAttackPower() {
		return specialAttackPower;
	}

	public void setSpecialAttackPower(int specialAttackPower) {
		// Special attack power no more than 30
		if (specialAttackPower > 30) {
			this.specialAttackPower = 30;
		} else if (specialAttackPower < 0) {
			this.specialAttackPower = 0;
		} else {
			this.specialAttackPower = specialAttackPower;
		}
	}
	
	public abstract void attack();
=======
import java.util.ArrayList ; 
>>>>>>> 876561bf597c55b1aefb968287753da350713967

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
