package game;


public abstract class Pokemon {
	// this is a super class of Pokemon
	protected String name;
	protected int hp;
	// This is normal attack power
	protected int power;
	// This is special attack that need to cooldown
	protected int specialAttackPower;

	public Pokemon(String name, int hp, int power) {
		setName(name);
		setHp(hp);
		setPower(power);

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		// Max H
		if (hp < 0) {
			this.hp = 0;
		} else if (hp > 100) {
			this.hp = 100;
		} else {
			this.hp = hp;
		}

	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		// Max normal power is no more than 10
		if (power < 0) {
			this.power = 0;
		} else if (power > 10) {
			this.power = 10;
		} else {
			this.power = power;
		}
	}

	public int getSpecialAttackPower() {
		return specialAttackPower;
	}

	public void setSpecialAttackPower(int specialAttackPower) {
		// Special attack power no more than 30
		if (specialAttackPower > 30) {
			this.specialAttackPower = 30;
		} else if (specialAttackPower < 0) {
			this.specialAttackPower = 0;
		} else {
			this.specialAttackPower = specialAttackPower;
		}
	}
	
	public abstract void attack();

}
