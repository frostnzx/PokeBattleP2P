package application;

import java.util.ArrayList;

import entity.Antidote;
import entity.Awakening;
import entity.FullRestorePotion;
import entity.Item;
import entity.ParalyzeHeal;
import entity.Potion;
import entity.SuperPotion;
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

        SceneManager sceneManager = new SceneManager(primaryStage);

        String filePath = "res/json/pokemonData.json";
        Pokemon.loadPokemonsFromJson(filePath);
        ArrayList<Pokemon> defaultPokemonsList = initDefaultPokemons();
        String defaultName = "John";
        ArrayList<Item> defaultItems = initDefaultItem();
        GameSystem.getInstance().setMyPlayer(new Player(defaultName, defaultPokemonsList,defaultItems)); 
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
    
    public ArrayList<Item> initDefaultItem () {
        Item p1 = new Antidote(), p2 = new Awakening(), p3 = new FullRestorePotion(), p4 = new ParalyzeHeal(), p5 = new Potion() ,p6 = new SuperPotion();
        ArrayList<Item> defaultItems = new ArrayList<Item>();
        defaultItems.add(p1);
        defaultItems.add(p2);
        defaultItems.add(p3);
        defaultItems.add(p4);
        defaultItems.add(p5);
        defaultItems.add(p6);
        return defaultItems;
    }
}