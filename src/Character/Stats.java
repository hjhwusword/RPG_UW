package battle;

public class Stats {
	private int hp;
	private int mp;
	
	private int magic;
	private int strength;
	private int defense;
	private int agility;
	private int dexterity;
	
	private int exp;
	
	//private int goalHP;
	//private int goalMP;
	
	
	public Stats(int dmg) {
		this(dmg, 0, 0, 0, 0, 0, 0);
	}
	
	public Stats(int hp, int mp, int mag, int str, int def, int agi, int dex) {
		this.hp = hp;
		this.mp = mp;
		
		this.magic = mag;
		this.strength = str;
		this.defense = def;
		this.agility = agi;
		this.dexterity = dex;
		
		exp = 0;
	//	goalHP = hp;
	//	goalMP = mp;
	}
	
	// post: apply other to this
	//		this + other
	public void add(Stats other) {
		this.hp += other.hp;
		this.mp += other.mp;
		//goalHP = this.hp + other.hp;
		//goalMP = this.mp + other.mp;
		
		
		this.magic += other.magic;
		this.strength += other.strength; 
		this.defense += other.defense;
		this.agility += other.agility;
		this.dexterity += other.dexterity;
	}
	
	public void effect(Stats other) {
		this.magic += other.magic;
		this.strength += other.strength; 
		this.defense += other.defense;
		this.agility += other.agility;
		this.dexterity += other.dexterity;
	}
	
	public void uneffect(Stats other) {
		this.magic -= other.magic;
		this.strength -= other.strength; 
		this.defense -= other.defense;
		this.agility -= other.agility;
		this.dexterity -= other.dexterity;
	}
	
	// post: minus other to this
	//		this - other
	public void minus(Stats other) {
		this.hp -= other.hp;
		this.mp -= other.mp;
		//goalHP = this.hp - other.hp;
		//goalMP = this.mp - other.mp;
		
		
		this.magic -= other.magic;
		this.strength -= other.strength; 
		this.defense -= other.defense;
		this.agility -= other.agility;
		this.dexterity -= other.dexterity;
	}
	
	
	// post: normal damage
	public void damage(int dmg) {
		this.hp -= dmg;
	}
	
	// post: normal damage + magic damge
	public void damage(int dmg, int magicDmg) {
		this.hp -= dmg;
		this.mp -= dmg;
	}
	
	// post: normal heal
	public void heal(int hp) {
		this.hp += hp;
	}
	
	// post: normal heal plus 
	public void heal(int hp, int mp) {
		this.hp += hp;
		this.mp += mp;
	}
	
	// post: true if person with this stats is dead 
	public boolean dead() {
		return hp <= 0;
	}
	
	public int getHP() {
		//update();
		return hp;
	}
	
	public int getMP() {
		return mp;
	}
	
	public int getStrength() {
		return strength;
	}
	
	public int getDefense() {
		return defense;
	}
	
	public int getSpeed() {
		return agility;
	}
	
	public void addExp(int exp) {
		this.exp += exp;
	}
	
	public int getExp() {
		return exp;
	}
	
	
	// make a full copy of current stats
	public Stats copy() {
		return new Stats(this.hp, this.mp, this.magic, this.strength, this.defense, this.agility, this.dexterity);
	}
	
	/*
	private void update() {
		if(goalHP != hp) {
			if(hp < goalHP) {
				hp++;
			} else {
				hp--;
			}
		}
		
		if(goalMP != mp) {
			if(mp < goalMP) {
				mp++;
			} else {
				mp--;
			}
		}
	}*/
	
	
	
	
}
