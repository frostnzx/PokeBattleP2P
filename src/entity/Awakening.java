package entity;

import game.Pokemon;
import game.Status;

public class Awakening extends Item {
	public Awakening() {
		super("Awakening" , "Cures sleep");
	}
	public void use(Pokemon target) {
		if(target.getStatus() == Status.SLP) { // if sleepy then, cure it
			target.setStatus(null);
			System.out.println(target.getName() + " sleep condition has been cured");
		} else {
			System.out.println(target.getName() + "'s current condition can't be cure by Awakening");
		}
	}
}
