package game;

import java.util.ArrayList;

public class Player {
	private String name ; 
	private ArrayList<Pokemon> pokemons ; 
	private int currentPokemon ; // index of current pokemon
	
	public Player(String name , ArrayList<Pokemon> pokemons) {
		this.name = name ; 
		this.pokemons = pokemons ; 
		this.currentPokemon = 0 ; // 0-5
	}
	
	public ArrayList<Pokemon> getPokemons() {
		return this.pokemons ;
	}
	public int getCurrentPokemon() {
		return this.currentPokemon ; 
	}
	public void setCurrentPokemon(int currentPokemon) {
		this.currentPokemon = currentPokemon ; 
	}
	
	public boolean isAlive() {
		int countAlivePokemon = 0 ; 
		for(Pokemon pokemon : pokemons) {
			if(!pokemon.isDead()) {
				countAlivePokemon++;
			}
		}
		if(countAlivePokemon == 0)  
			return false ;
		else return true ; 
	}

	public void setPokemons(ArrayList<Pokemon> pokemons) {
		this.pokemons = pokemons;
	}
	
	public void listCurrentPokemons() {
	    for(Pokemon p : pokemons) {
	        System.out.println("Pokemon: " + p);  // Correct way to concatenate strings and objects
	    }
	}

	
	
	
}
