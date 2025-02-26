package entity;

import game.Pokemon;

public abstract class Item {
	protected String name ;
	protected String description ; 
	
	public Item(String name , String description) {
		this.name = name ; 
		this.description = description ; 
	}
	public abstract void use(Pokemon target);
}
