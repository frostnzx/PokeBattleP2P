package entity;

import game.Pokemon;

public class SuperPotion extends Item {
	private int healAmount ;  
	public SuperPotion() {
		super("SuperPotion" , "Restores a big amount of HP");
		this.healAmount = 60 ; 
	}
	public void use(Pokemon target) {
		target.setHp(Math.min(target.getHp() + healAmount , target.getMaxHp()));
		System.out.println(target.getName() + "Has been healed by 30 hp");
	}
}

