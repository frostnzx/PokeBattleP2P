package entity;

import game.Pokemon;
import game.Status;

public class ParalyzeHeal extends Item {
	public ParalyzeHeal() {
		super("ParalyzeHeal" , "Cures paralysis");
	}
	public void use(Pokemon target) {
		if(target.getStatus() == Status.PAR) { // if paralyzed then, cure it
			target.setStatus(null);
			System.out.println(target.getName() + " paralysis condition has been cured");
		} else {
			System.out.println(target.getName() + "'s current condition can't be cure by ParalyzeHeal");
		}
	}
}

