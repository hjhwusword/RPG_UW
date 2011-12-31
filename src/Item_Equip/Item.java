package Item_Equip;

import Character.Skill;
import Character.Stats;

public class Item {
	
	public static enum Items {
		CLOTH,
		HELMET,
		BOOT,
		GAUNTLETS,
		PANTS,
		
		HP_POTION_S,
		HP_POTION_L,
		MP_POTION_S,
		MP_POTION_L,
		
		STR_POTION,
		AGI_POTION,
		DEX_POTION,
		MAG_POTION,
		DEF_POTION,
		
		ATK_PLUS,
		DEF_PLUS,
		SPD_PLUS,
		
		FREEZE_CURE,
		POISON_CURE,
		BURN_CURE,
		PARALYZE_CURE,
		
		
		REVIVE_POTION,
		SMOKE_BOMB,
		TP,
		ENEMY_ATTRACT,
		ENEMMY_REPELL,
		
		MONEY,
	}
	
	public static enum ItemType {
		IRON,
		GOLD,
		LEAGHER,
		TIGHTER,
		ROBE,
		ARCANE,
		NON
	}
	
	private int num;
	private Items item;
	private ItemType type;
	private Skill.Status status;
	private Stats st;
	
	public Item(Items it) {
		this(it, 1);
	}
	
	public Item(Items it, ItemType type) {
		this(it);
		this.type = type;
	}
	
	public Item(Items it, int num) {
		this.item = it;
		this.num = num;
		this.type = ItemType.NON;
		this.status = Skill.Status.NON;
		convertItem(it);
	}
	
	public boolean hasItem() {
		return num > 0;
	}
	
	private void convertItem(Item.Items t) {
		//Stats(int hp, int mp, int mag, int str, int def, int agi, int dex)
		switch(t) {
		case CLOTH:
			st = new Stats(0, 0, 0, 0, 15, 0, 0);
			break;
		case HELMET:
			st =  new Stats(0, 0, 0, 0, 5, 0, 0);
			break;
		case BOOT:
			st =  new Stats(0, 0, 0, 0, 2, 5, 0);
			break;
		case GAUNTLETS:
			st =  new Stats(0, 0, 0, 0, 2, 0, 5);
			break;
		case PANTS:
			st =  new Stats(0, 0, 0, 0, 10, 0, 0);
			break;
		
		case HP_POTION_S:
			st =  new Stats(30, 0, 0, 0, 0, 0, 0);
			break;
		case HP_POTION_L:
			st =  new Stats(50, 0, 0, 0, 0, 0, 0);
			break;
		case MP_POTION_S:
			st =  new Stats(0, 20, 0, 0, 0, 0, 0);
			break;
		case MP_POTION_L:
			st =  new Stats(0, 50, 0, 0, 0, 0, 0);
			break;
		
		case STR_POTION:
			st =  new Stats(0, 0, 0, 1, 0, 0, 0);
			break;
		case AGI_POTION:
			st =  new Stats(0, 0, 0, 0, 0, 1, 0);
			break;
		case DEX_POTION:
			st =  new Stats(0, 0, 0, 0, 0, 0, 1);
			break;
		case MAG_POTION:
			st =  new Stats(0, 0, 1, 0, 0, 0, 0);
			break;
		case DEF_POTION:
			st =  new Stats(0, 0, 0, 0, 1, 0, 0);
			break;
		
		case ATK_PLUS:
			status = Skill.Status.ATKUP;
			break;
		case DEF_PLUS:
			status = Skill.Status.DEFUP;
			break;
		case SPD_PLUS:
			status = Skill.Status.SPDUP;
			break;
		
		case FREEZE_CURE:
			status = Skill.Status.FREEZE;
			break;
		case POISON_CURE:
			status = Skill.Status.POISON;
			break;
		case BURN_CURE:
			status = Skill.Status.BURN;
			break;
		case PARALYZE_CURE:
			status = Skill.Status.PARALYZE;
			break;
		default:
			System.out.println("still need " + t);
			break;
		/*
		REVIVE_POTION,
		SMOKE_BOMB,
		TP,
		ENEMY_ATTRACT,
		ENEMMY_REPELL,
		*/
		//MONEY,
		}
		
	
		if(st != null) {
			switch(type) {
			//Stats(int hp, int mp, int mag, int str, int def, int agi, int dex)
			case IRON:
				st.add(new Stats(0, 0, 0, 0, 20, 0, 0));
				break;
			case GOLD:
				st.add(new Stats(0, 0, 0, 0, 35, 0, 0));
				break;
			case LEAGHER:
				st.add(new Stats(0, 0, 0, 0, 10, 5, 5));
				break;
			case TIGHTER:
				st.add(new Stats(0, 0, 0, 0, 15, 10, 10));
				break;
			case ROBE:
				st.add(new Stats(0, 0, 15, 0, 5, 0, 0));
				break;
			case ARCANE:
				st.add(new Stats(0, 0, 25, 0, 10, 0, 0));
				break;
			
			}
		}
	}
	
	public Stats getStats() {
		return st;
	}
	
	public Skill.Status getStatus() {
		return status;
	}
}
