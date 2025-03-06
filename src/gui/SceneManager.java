package gui;

import java.io.IOException;

import game.GameSystem;
import javafx.stage.Stage;

public class SceneManager {
	private Stage stage ; 
	
	public SceneManager(Stage stage) {
		this.stage = stage ; 
	}
	
	public void showMainMenu() throws IOException {
		MainMenuScene mainMenuScene = new MainMenuScene(this);
		stage.setScene(mainMenuScene.getScene());
		stage.show();
	}
	public void showCreateGameScene() throws IOException {
		CreateGameScene createGameScene = new CreateGameScene(this);
		stage.setScene(createGameScene.getScene());
		stage.show();
	}
	public void showJoinGameScene() throws IOException {
		JoinGameScene joinGameScene = new JoinGameScene(this);
		stage.setScene(joinGameScene.getScene());
		stage.show();
	}
	public void showBattleScene() throws IOException {
		BattleScene battleScene = new BattleScene(this);
		GameSystem.getInstance().getBattle().setBattleScene(battleScene);
		stage.setScene(battleScene.getScene());
		stage.show();
	}
	
	public void showWinningScene() {
		WinningScene winningScene = new WinningScene(this);
		stage.setScene(winningScene.getScene());
		stage.show();
	}
	
	public void showLosingScene() {
		LosingScene losingScene = new LosingScene(this);
		stage.setScene(losingScene.getScene());
		stage.show();
	}
	public void showPokemonSelectorScene() throws IOException {
		PokemonSelectorScene pokemonSelectorScene = new PokemonSelectorScene(this);
		stage.setScene(pokemonSelectorScene.getScene());
		stage.show();
	}
	
	public Stage getStage() {
		return this.stage ; 
	}
}