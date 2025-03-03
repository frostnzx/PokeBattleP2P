package entity;

import game.Pokemon;

public abstract class Item {
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