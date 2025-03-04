package game;

import java.util.ArrayList;

import entity.Item;

public class Player {
    private String name ; 
    private ArrayList<Pokemon> pokemons ; 
    private int currentPokemon ; // index of current pokemon
    private ArrayList<Item> items;
    
    public Player(String name , ArrayList<Pokemon> pokemons, ArrayList<Item> items) {
        this.name = name ; 
        this.pokemons = pokemons ; 
        this.currentPokemon = 0 ; // 0-5
        this.items = items;
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

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
    
    @Override
    public String toString() {
        return "Player [name=" + name + ", currentPokemon=" + currentPokemon ;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Pokemon getActualCurrentPokemon() {
		return this.getPokemons().get(this.getCurrentPokemon());
	}
    
    
    
}