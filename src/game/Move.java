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
    

    // Static list to hold all available moves
    private static List<Move> moveList = new ArrayList<>();

    // Constructor
    public Move(int moveId) {
    	// TODO:
    	// set this move to move that have this id in moveList
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

    // Static method to list all moves
    public static void listMoves() {
        System.out.println("Available Moves:");
        for (Move move : moveList) {
            System.out.println("- " + move.getName() + " (Type: " + move.getPokemonType() + ", Damage: " + move.getDamage() + ")");
        }
    }

    // Static method to load moves from a JSON file
    // Gotta fix this
    public static void loadMovesFromJson(String filePath) {
        Gson gson = new Gson();
        Type moveListType = new TypeToken<List<Move>>() {}.getType();

        try (FileReader reader = new FileReader(filePath)) {
            List<Move> moves = gson.fromJson(reader, moveListType);
            moveList.addAll(moves); // Add all loaded moves to moveList
            System.out.println("Moves loaded from JSON.");
        } catch (IOException e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        }
    }
}