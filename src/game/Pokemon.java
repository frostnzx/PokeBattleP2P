package game;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Pokemon {
	// general info
	private String name;
	private int pokemonId;
	private ArrayList<PokemonType> pokemonTypes;
	private String filePath;

	// basic stats
	private int hp; // current hp
	private int max_hp;
	private int attack;
	private int defense;
	private int specialAttack;
	private int specialDefense;
	private int speed;

	// moves
	private ArrayList<Move> moves;

	// status
	private Status status;

	// static list to hold all available pokemon
	private static ArrayList<Pokemon> pokemonList = new ArrayList<>();

	// to create new pokemon
	public Pokemon(int pokemonId) {
		// Loop through the pokemonList to find the pokemon with the given pokemonId
		for (Pokemon existingPokemon : pokemonList) {
			if (existingPokemon.getPokemonId() == pokemonId) {
				// Set this object's properties from the existingPokemon
				this.name = existingPokemon.name;
				this.pokemonId = existingPokemon.pokemonId;
				this.filePath = existingPokemon.filePath;
				this.pokemonTypes = new ArrayList<>(existingPokemon.pokemonTypes); // Deep copy of list
				this.max_hp = existingPokemon.max_hp;
				this.hp = existingPokemon.hp; // Set initial HP (could be full or other logic)
				this.attack = existingPokemon.attack;
				this.defense = existingPokemon.defense;
				this.specialAttack = existingPokemon.specialAttack;
				this.specialDefense = existingPokemon.specialDefense;
				this.speed = existingPokemon.speed;
				this.moves = new ArrayList<>(existingPokemon.moves); // Deep copy of list
				this.status = existingPokemon.status;

				// You can add additional logic if you want the new Pokémon to have any
				// modifications
				break; // Once the Pokémon is found and set, exit the loop
			}
		}
	}

	public boolean isDead() {
		return (this.hp == 0);
	}

	public static void listPokemons() {
		// Loop through all the Pokémon in the pokemonList
		int i = 0;
		for (Pokemon p : pokemonList) {
			// Print the Pokémon name (or any other info you want to display)
			System.out.println("Name: " + p.name);
			System.out.println("ID: " + p.pokemonId);
			System.out.println("FilePath: " + p.filePath);
			System.out.println("Types: " + p.pokemonTypes);
			System.out.println("HP: " + p.max_hp);
			System.out.println("Attack: " + p.attack);
			System.out.println("Defense: " + p.defense);
			System.out.println("Special Attack: " + p.specialAttack);
			System.out.println("Special Defense: " + p.specialDefense);
			System.out.println("Speed: " + p.speed);
			System.out.println("Status " + p.getStatus());
			System.out.println("Moves: ");
			for (Move m : p.moves) {
				System.out.println(
						"  - " + m.getName() + " (Type: " + m.getPokemonType() + ", Damage: " + m.getDamage() + ")");
			}
			System.out.println("-----------------------------------------");
			i++;
			if (i == 5) {
				break;
			}
		}
	}

	@Override
	public String toString() {
		return "Pokemon [name=" + name + ", pokemonId=" + pokemonId + ", pokemonTypes=" + pokemonTypes + ", filePath="
				+ filePath + ", hp=" + hp + ", max_hp=" + max_hp + ", attack=" + attack + ", defense=" + defense
				+ ", specialAttack=" + specialAttack + ", specialDefense=" + specialDefense + ", speed=" + speed
				+ ", moves=" + moves + ", status=" + status + "]";
	}

	public static void loadPokemonsFromJson(String filePath) {
		// TODO :
		// Load pokemon from json file
		// Create a new Gson instance
		Gson gson = new Gson();

		// Define the type of data to parse (ArrayList of Pokemon objects)
		try (FileReader reader = new FileReader(filePath)) {
			// Deserialize the JSON into a list of Pokemon objects
			ArrayList<Pokemon> pokemons = gson.fromJson(reader, new TypeToken<ArrayList<Pokemon>>() {
			}.getType());

			// Populate the pokemonList with the deserialized Pokemon objects
			pokemonList.addAll(pokemons);
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public int getMaxHp() {
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
