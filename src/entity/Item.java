package entity;

import game.Pokemon;
import tools.Usable;

public abstract class Item implements Usable {
    protected String name;
    protected String description;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract void use(Pokemon target);

    public String getItemType() {
        return this.getClass().getSimpleName(); // Returns "Antidote", "Potion", etc.
    }

    @Override
    public String toString() {
        return "Item [name=" + name + ", description=" + description + "]" + this.getItemType();
    }

	public String getName() {
		return name;
	}
    
}