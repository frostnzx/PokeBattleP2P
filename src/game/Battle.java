package game;

import entity.Item;

public class Battle {
	private Player player1 , player2 ; 
	
	public Battle(Player player1 , Player player2) {
		this.player1 = player1 ; 
		this.player2 = player2 ; 
	}
	
	public boolean executeMove(Player player , Pokemon pokemon , Move move) {
		if(pokemon.getStatus() == Status.PAR || pokemon.getStatus() == Status.SLP) {
			return false;
		}
		Player target = (player == player1) ? player2 : player1 ; // target is someone that is not player
		Pokemon targetPokemon = target.getPokemons().get(target.getCurrentPokemon());
		int damage = calculateDamage(pokemon , targetPokemon , move);
		targetPokemon.setHp(Math.max(0, targetPokemon.getHp() - damage));
		targetPokemon.setStatus(move.getMoveStatus());
		// log
		System.out.println(pokemon.getName() + "used " + move.getName() + " and dealt " + damage + " damage!");
		return true;
	}
	private int calculateDamage(Pokemon attacker , Pokemon defender , Move move) {
		return (move.getDamage() * attacker.getAttack()) / defender.getDefense() ; 
	}
	
	public void executeItem(Player player , Item item) {
        item.use(player.getPokemons().get(player.getCurrentPokemon()));
    }
	
	public void changeCurrentPokemon(Player player , int newIndex) {
        player.setCurrentPokemon(newIndex);
    }
	
	public void executeStatus() {
        // effect apply at the end of the turn
        Pokemon p1 = player1.getPokemons().get(player1.getCurrentPokemon());
        Pokemon p2 = player2.getPokemons().get(player2.getCurrentPokemon());
        handleStatusEffects(p1);
        handleStatusEffects(p2);
    }
    
    public void handleStatusEffects(Pokemon pokemon) {
        Status status = pokemon.getStatus();
        if(status == Status.BRN) {
            int dmg = 7;
            pokemon.setHp(pokemon.getHp() - dmg);
        }
        else if(status == Status.FRZ) {
            int dmg = 7;
            pokemon.setHp(pokemon.getHp() - dmg);
        }
        else if(status == Status.PSN) {
            int dmg = 10;
            pokemon.setHp(pokemon.getHp() - dmg);
        }
//        else if(status == Status.SLP) {
//        	pokemon.setStatus(Status.SLP);
//        }
//        else if(status == Status.PAR) {
//        	pokemon.setStatus(Status.PAR);
//        }
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
