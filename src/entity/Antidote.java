package entity;

import game.Pokemon;
import game.Status;
import tools.Usable;

public class Antidote extends Item {
	public Antidote() {
		super("Antidote" , "Cures poison");
	}
	public void use(Pokemon target) {
		if(target.getStatus() == Status.PSN) { // if poison then, cure it
			target.setStatus(null);
			System.out.println(target.getName() + " poison condition has been cured");
		} else {
			System.out.println(target.getName() + "'s current condition can't be cure by Antidote");
		}
	}
}
