package entity;

import game.Pokemon;

public class Potion extends Item {
	private int healAmount ;  
	public Potion() {
		super("Potion" , "Restores a small amount of HP");
		this.healAmount = 30 ; 
	}
	public void use(Pokemon target) {
		target.setHp(Math.min(target.getHp() + healAmount , target.getMax_hp()));
		System.out.println(target.getName() + "Has been healed by 30 hp");
	}
}
