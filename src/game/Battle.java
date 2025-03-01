package game;

import entity.Item;

public class Battle {
	private Player player1 , player2 ; 
	
	public Battle(Player player1 , Player player2) {
		this.player1 = player1 ; 
		this.player2 = player2 ; 
	}
	
	public void executeMove(Player player , Pokemon pokemon , Move move) {
		
	}
	public void executeItem(Player player , Item item) {
		
	}
	public void changeCurrentPokemon(Player player , int newIndex) {
		
	}
	public void applyEffectStatus() {
		// effect apply at the end of the turn
		
	}
	public boolean isEnded() {
		if(!player1.isAlive()) {
			System.out.println("player2 won!");
			return true ; 
		} else if(!player2.isAlive()) {
			System.out.println("player1 won!");
			return true ; 
		} else {
			return false ; 
		}
	}
	
	
}
