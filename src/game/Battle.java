package game;

import entity.Antidote;
import entity.Awakening;
import entity.FullRestorePotion;
import entity.Item;
import entity.ParalyzeHeal;
import entity.Potion;
import entity.SuperPotion;
import gui.BattleScene;
import gui.SceneManager;
import javafx.application.Platform;

public class Battle {
	private Player player1, player2;
	private BattleScene battleScene;

	public Battle(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
	}

	public void setBattleScene(BattleScene battleScene) {
		this.battleScene = battleScene;
	}

	public boolean executeMove(Player player, Move move) {
		Pokemon pokemon = player.getPokemons().get(player.getCurrentPokemon());
		if (pokemon.getStatus() == Status.PAR) {
			if(player == GameSystem.getInstance().getMyPlayer()) {
				battleScene.displayActionFeedback("Your current pokemon can't attack while being paralyzed..");
			}
            return false;
        }
        if (pokemon.getStatus() == Status.SLP) {
			if(player == GameSystem.getInstance().getMyPlayer()) {
				battleScene.displayActionFeedback("Your current pokemon can't attack while sleeping..");
			}
            return false;
        }
        if (pokemon.getHp() <= 0) {
			if(player == GameSystem.getInstance().getMyPlayer()) {
				battleScene.displayActionFeedback("Your current pokemon is dead. Change your pokemon now!");
			}
            return false;
        }
		Player target = (player == player1) ? player2 : player1; // target is someone that is not player
		Pokemon targetPokemon = target.getPokemons().get(target.getCurrentPokemon());
		int damage = calculateDamage(pokemon, targetPokemon, move);
		targetPokemon.setHp(Math.max(0, targetPokemon.getHp() - damage));
		if(move.getMoveStatus() != null) {
			targetPokemon.setStatus(move.getMoveStatus());
		}

		// Update UI
		Platform.runLater(() -> {
			battleScene.updatePlayerHp(player1.getPokemons().get(player1.getCurrentPokemon()).getHp(),
					player1.getPokemons().get(player1.getCurrentPokemon()).getMaxHp());
			battleScene.updateOpponentHp(player2.getPokemons().get(player2.getCurrentPokemon()).getHp(),
					player2.getPokemons().get(player2.getCurrentPokemon()).getMaxHp());
			battleScene.updateStatus(target, target.getActualCurrentPokemon().getStatus());

			battleScene.displayActionFeedback(
					pokemon.getName() + " used " + move.getName() + " and dealt " + damage + " damage!");
		});

		// log
		System.out.println(pokemon.getName() + "used " + move.getName() + " and dealt " + damage + " damage!");
		return true;
	}

	private int calculateDamage(Pokemon attacker, Pokemon defender, Move move) {
		return (move.getDamage() * attacker.getAttack()) / defender.getDefense();
	}

	public void executeItem(Player player, Item item) {
		Player updatedPlayer;
		if(player != GameSystem.getInstance().getMyPlayer()) {
			updatedPlayer = GameSystem.getInstance().getMyOpponent();
			item.use(updatedPlayer.getActualCurrentPokemon());
		}
		else {
			updatedPlayer = GameSystem.getInstance().getMyPlayer();
			item.use(updatedPlayer.getActualCurrentPokemon());
		}
		updatedPlayer.removeItem(item);
		// Update UI
		Platform.runLater(() -> {
			battleScene.updatePlayerHp(player1.getPokemons().get(player1.getCurrentPokemon()).getHp(),
					player1.getPokemons().get(player1.getCurrentPokemon()).getMaxHp());
			battleScene.updateOpponentHp(player2.getPokemons().get(player2.getCurrentPokemon()).getHp(),
					player2.getPokemons().get(player2.getCurrentPokemon()).getMaxHp());
			battleScene.updateStatus(updatedPlayer, updatedPlayer.getActualCurrentPokemon().getStatus());
			
			String message = updatedPlayer.getName() + " used " + item.getName() + " on " + updatedPlayer.getActualCurrentPokemon().getName() ;
			battleScene.displayActionFeedback(message);
			battleScene.updateItem();
		});
		
	}

	public void changeCurrentPokemon(Player player, int newIndex) {
		Player updatedPlayer;
		if(player != GameSystem.getInstance().getMyPlayer()) {
			GameSystem.getInstance().getMyOpponent().setCurrentPokemon(newIndex);
			updatedPlayer = GameSystem.getInstance().getMyOpponent();
		}
		else {
			GameSystem.getInstance().getMyPlayer().setCurrentPokemon(newIndex);
			updatedPlayer = GameSystem.getInstance().getMyPlayer();
		}
		// Update UI
		Platform.runLater(() -> {
			battleScene.updateCurrentPokemon(updatedPlayer, updatedPlayer.getActualCurrentPokemon());
		});
	}

	private String statusToStr(Status status) {
		if (status == Status.BRN)
			return "BURN";
		if (status == Status.FRZ)
			return "FREEZE";
		if (status == Status.PAR)
			return "PARALYZED";
		if (status == Status.PSN)
			return "POISONED";
		if (status == Status.SLP)
			return "SLEEP";
		else
			return "";
	}

	public void executeStatus() {
		// effect apply at the end of the turn
		Pokemon p1 = player1.getPokemons().get(player1.getCurrentPokemon());
		Pokemon p2 = player2.getPokemons().get(player2.getCurrentPokemon());
		handleStatusEffects(p1);
		handleStatusEffects(p2);

		// Update UI
		Platform.runLater(() -> {
			battleScene.updatePlayerHp(player1.getPokemons().get(player1.getCurrentPokemon()).getHp(),
					player1.getPokemons().get(player1.getCurrentPokemon()).getMaxHp());
			battleScene.updateOpponentHp(player2.getPokemons().get(player2.getCurrentPokemon()).getHp(),
					player2.getPokemons().get(player2.getCurrentPokemon()).getMaxHp());
			// MIGHT BE BUGGY 
			if (p1.getStatus() != null) {
				String message = p1.getName() + " affected from status " + statusToStr(p1.getStatus());
				battleScene.displayActionFeedback(message);
			}
			if (p2.getStatus() != null) {
				String message = p2.getName() + " affected from status " + statusToStr(p2.getStatus());
				battleScene.displayActionFeedback(message);
			}
		});
	}

	public void handleStatusEffects(Pokemon pokemon) {
		Status status = pokemon.getStatus();
		if (status == Status.BRN) {
			int dmg = 7;
			pokemon.setHp(pokemon.getHp() - dmg);
		} else if (status == Status.FRZ) {
			int dmg = 7;
			pokemon.setHp(pokemon.getHp() - dmg);
		} else if (status == Status.PSN) {
			int dmg = 10;
			pokemon.setHp(pokemon.getHp() - dmg);
		}
	}

	public void freezeTurn() {
		Platform.runLater(() -> {
			this.battleScene.freeze();
		});
	}

	public void unfreezeTurn() {
		Platform.runLater(() -> {
			this.battleScene.unfreeze();
		});
	}
	
	public void StopPlaying() {
		battleScene.stopPlaying();
	}

	public boolean isEnded() {
		if (!player1.isAlive()) {
			System.out.println("player2 won!");
			return true;
		} else if (!player2.isAlive()) {
			System.out.println("player1 won!");
			return true;
		} else {
			return false;
		}
	}

}
