package application;

import java.util.ArrayList;

import game.GameSystem;
import gui.SceneManager;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage; 
import game.Player;
import game.Pokemon;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("PokeBattleP2P");
        
        // initialize important application controller
        SceneManager sceneManager = new SceneManager(primaryStage);

        String filePath = "res/json/pokemonData.json";
        Pokemon.loadPokemonsFromJson(filePath);
        ArrayList<Pokemon> defaultPokemonsList = initDefaultPokemons();
        String defaultName = "John";
        
        // Set items 
      
        // Pokemon.listPokemons(); For testing purpose

        GameSystem.getInstance().setMyPlayer(new Player(defaultName, defaultPokemonsList));; 
        GameSystem.getInstance().setSceneManager(sceneManager);
        
        sceneManager.showMainMenu();
    }
    public static void main(String[] args) {
        launch(args); // Start javafx GUI
        
    }
    
    public ArrayList<Pokemon> initDefaultPokemons () {
    	Pokemon p1 = new Pokemon(1), p2 = new Pokemon(4), p3 = new Pokemon(7), p4 = new Pokemon(25), p5 = new Pokemon(39) ,p6 = new Pokemon(54);
    	ArrayList<Pokemon> defaultPokemons = new ArrayList<Pokemon>();
        defaultPokemons.add(p1);
        defaultPokemons.add(p2);
        defaultPokemons.add(p3);
        defaultPokemons.add(p4);
        defaultPokemons.add(p5);
        defaultPokemons.add(p6);
        
		return defaultPokemons;
    }
}
