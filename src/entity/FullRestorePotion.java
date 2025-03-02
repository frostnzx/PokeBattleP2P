package entity;

import game.Pokemon;

public class FullRestorePotion extends Item{
	public FullRestorePotion() {
		super("Full Restore Potion" , "Fully restores HP and removes status conditions");
	}
	// fully heals & remove any condition
	public void use(Pokemon target) {
		target.setHp(target.getMaxHp());
		target.setStatus(null);
		System.out.println(target.getName() + "'s HP fully restored and also cured from the status");
	}
}
