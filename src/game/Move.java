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
    private String name;  // Name of the move
    private String type;  // Type of the move (e.g., Flying, Fire, Water)
    private int damage;   // Damage dealt by the move

    // Static list to hold all available moves
    private static List<Move> moveList = new ArrayList<>();

    // Constructor
    public Move(String name, String type, int damage) {
        this.name = name;
        this.type = type;
        this.damage = damage;

        // Add the created move to the moveList
        moveList.add(this);
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getDamage() {
        return damage;
    }

    // Static method to list all moves
    public static void listMoves() {
        System.out.println("Available Moves:");
        for (Move move : moveList) {
            System.out.println("- " + move.getName() + " (Type: " + move.getType() + ", Damage: " + move.getDamage() + ")");
        }
    }

    // Static method to load moves from a JSON file
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