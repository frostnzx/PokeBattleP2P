package game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Move {
    private String name;  
    private int moveId ; 
    private PokemonType pokemonType;  
    private int damage;  
    private Status moveStatus ; 
    
    // Constructor
    public Move(String name , int moveId , PokemonType pokemonType , int damage , Status moveStatus) {
    	this.name = name ; 
    	this.moveId = moveId ; 
    	this.pokemonType = pokemonType ; 
    	this.damage = damage ; 
    	this.moveStatus = moveStatus ; 
    }

    // Getters
    public String getName() {
        return name;
    }

    public PokemonType getPokemonType() {
        return pokemonType;
    }

    public int getDamage() {
        return damage;
    }

	public Status getMoveStatus() {
		return moveStatus;
	}

	public int getMoveId() {
		return moveId;
	}
    
	
    
}