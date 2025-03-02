package gui;

import javafx.stage.Stage;

public class SceneManager {
	private Stage stage ; 
	
	public SceneManager(Stage stage) {
		this.stage = stage ; 
	}
	
	public void showMainMenu() {
		MainMenuScene mainMenuScene = new MainMenuScene(this);
		stage.setScene(mainMenuScene.getScene());
		stage.show();
	}
	public void showCreateGameScene() {
		CreateGameScene createGameScene = new CreateGameScene(this);
		stage.setScene(createGameScene.getScene());
		stage.show();
	}
	public void showJoinGameScene() {
		JoinGameScene joinGameScene = new JoinGameScene(this);
		stage.setScene(joinGameScene.getScene());
		stage.show();
	}
	public void showBattleScene() {
		BattleScene battleScene = new BattleScene(this);
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
}