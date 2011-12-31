package Character;

import java.awt.Image;
import java.util.*;

import Character.Skill.Skills;
import Character.Skill.Status;
import Combat.Field;
import Item_Equip.Item;
import Item_Equip.Item.Items;


public class Character {
	protected Stats stat;
	protected Stats currentStat;
	protected int location;
	
	protected List<Skill.Skills> skill;
	protected List<Item.Items> item;
	protected Field f;
	
	protected Map<Skill.Status, Integer> status ;
	protected Map<Skill.Status, Stats> effect;
	
	public static int DELAY = 40;
	
	private List<Image> img;
	private int imgCount;
	
	private int dx, dy;
	
	private int goalx, goaly;
	
	private boolean animate;
	private boolean visible;
	
	private Image face;
	
	private AttackStyle attackStyle;
	
	public enum AttackStyle {
		FRONT,
		ONE
	}
	
	public Character(List<Image> img) {
		this(img, 100, 50, 10, 10, 10, 10, 10, AttackStyle.FRONT);
	}
	
	public Character(List<Image> img, int hp, int mp, int mag, int str, int def, int agi, int dex, AttackStyle as) {
		stat = new Stats(hp, mp, mag, str, def, agi, dex);
		currentStat = stat.copy();
		skill = new ArrayList<Skill.Skills>();
		item = new ArrayList<Item.Items>();
		status = new HashMap<Skill.Status, Integer>();
		effect = new HashMap<Skill.Status, Stats>();
		f = null;
		this.img = img;
		imgCount = 0;
		goalx = goaly = 0;
		animate = false;
		visible = true;
		dx = dy = 0;
		location = -1;
		face = null;
		attackStyle = as;
		
		// add some random skill
		Random r = new Random();
		Skills[] temp = Skills.values();
		Items[] temp2 = Items.values();
		int amount = Math.abs(r.nextInt())% (temp.length / 4);
		int amount2 = Math.abs(r.nextInt()) % (temp2.length / 2); 
		
		if(amount == 0)
			amount = 1;
		if(amount2 == 0)
			amount = 1;
		
		/*
		for(int i = 0; i < amount ; i++) {
			skill.add(temp[Math.abs(r.nextInt() % temp.length)]);
		}*/
		
		skill.add(Skill.Skills.FIRE_BALL);
		skill.add(Skill.Skills.FIRE_WAVE);
		skill.add(Skill.Skills.FIRE_EXPLOSDOM);
		skill.add(Skill.Skills.ICE_BALL);
		skill.add(Skill.Skills.ICE_WAVE);
		skill.add(Skill.Skills.ICE_EXPLOSDOM);
		skill.add(Skill.Skills.LIGHTNING_BALL);
		skill.add(Skill.Skills.LIGHTNING_WAVE);
		skill.add(Skill.Skills.LIGHTNING_EXPLOSDOM);
		skill.add(Skill.Skills.DUEL_STRIKE);
		skill.add(Skill.Skills.TRIPLE_STRIKE);
		skill.add(Skill.Skills.QUINT_STRIKE);
		skill.add(Skill.Skills.PIN_POINT_STRIKE);
		skill.add(Skill.Skills.DEHABILITATE);
		skill.add(Skill.Skills.FATALITY);
		skill.add(Skill.Skills.HEAVY_BLOW);
		skill.add(Skill.Skills.CRANIUM_BLOW);
		skill.add(Skill.Skills.HULK_BLOW);
		skill.add(Skill.Skills.WIDE_SLASH);
		skill.add(Skill.Skills.SWEEPING_SLASH);
		skill.add(Skill.Skills.HURRICANE_SWIPE);
		skill.add(Skill.Skills.MAGIC_BOLT);
		skill.add(Skill.Skills.GREATER_MAGIC_BOLT);
		skill.add(Skill.Skills.GINORMAOUS_MAGIC_BOLT);
		skill.add(Skill.Skills.HEALING);
		skill.add(Skill.Skills.GREATER_HEALING);
		skill.add(Skill.Skills.CLEANSE);
		skill.add(Skill.Skills.BACK_STAB);
		skill.add(Skill.Skills.DEATH_FLASH);
		skill.add(Skill.Skills.REAPER);
		skill.add(Skill.Skills.SPDUP);
		skill.add(Skill.Skills.ATKUP);
		skill.add(Skill.Skills.DEFUP);
		skill.add(Skill.Skills.SPDDOWN);
		skill.add(Skill.Skills.ATKDOWN);
		skill.add(Skill.Skills.DEFDOWN);
		skill.add(Skill.Skills.HEAL_ALL);
		skill.add(Skill.Skills.CLEANSE_ALL);
		skill.add(Skill.Skills.REVIVE);
		skill.add(Skill.Skills.PIERCING_ARROW);
		skill.add(Skill.Skills.MASSIVE_DESTRUCTION);
		skill.add(Skill.Skills.HEAD_SHOT);
		/*
		for(int i = 0; i < amount2; i++) {
			item.add(temp2[Math.abs(r.nextInt()) % temp2.length]);
		}*/
		
		item.add(Item.Items.HP_POTION_S);
		item.add(Item.Items.HP_POTION_L);
		item.add(Item.Items.MP_POTION_S);
		item.add(Item.Items.MP_POTION_L);
		
		
		//System.out.println("img size is ++++++++++ " + img.size());
	}
	
	public void setFace(Image face) {
		this.face = face;
	}
	
	public void setField(Field f) {
		this.f = f;
		this.fixDxDy();
		
			System.out.println("this is dx and dy " + dx + " " + dy);
		
		setLocation(f.getIndex(this));
	}
	
	public void setLocation(int location) {
		this.location = location;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public Field getField() {
		return f;
	}
	
	public int getLocation() {
		return location;
	}
	
	
	public Image getFace() {
		return face;
	}
	
	public int getHP() {
		return currentStat.getHP();
	}
	
	public int getMP() {
		return currentStat.getMP();
	}
	
	public Image getImage() {
		Image temp = img.get(imgCount / DELAY);
		
		imgCount = (imgCount + 1) % (img.size() * DELAY);
		//Image temp = img.get(imgCount);
		//imgCount = (imgCount + 1) % img.size();
		
		return temp;	
	}
	
	public List<Image> getWalkImage() {
		return img;
	}
	
	public int getDx() {
		return dx;
	}
	
	public int getDy() {
		return dy;
	}
	
	public List<Skill.Skills> getSkill() {
		return skill;
	}
	
	public List<Item.Items> getItem() {
		return item;
	}
	
	
	public void animate(int goalx, int goaly) {
		if(!animate) {
			
			this.goalx = goalx;
			this.goaly = goaly;
			
			animate = true;
		}
	}
	
	public void animateUpdate(){
		if(animate) {
			if(dx > goalx) {
				dx--;
			} else if(dx < goalx) {
				dx++;
			}
			if(dy > goaly) {
				dy--;
			} else if(dy < goaly) {
				dy++;
			}
			
			if(dx == goalx && dy == goaly) {
				animate = false;
				this.fixDxDy();
				goalx = dx;
				goaly = dy;
			}
		}
	}
	
	
	public boolean isAnimate() {
		animateUpdate();
		return animate;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	// return if this character is dead;
	public boolean dead() {
		return currentStat.dead();
	}
	
	public int getSpeed() {
		return currentStat.getSpeed();
	}
	
	public int getAttack() {
		return currentStat.getStrength();
	}
	
	public	List<Skill.Status> getStatus() {
		List<Skill.Status> list = new ArrayList<Skill.Status>();
		for(Skill.Status st : status.keySet()) {
			list.add(st);
		}
		return list;
	}
	
	
	public void damaged(Stats input) {
		currentStat.add(input);
	}
	
	public void normalDamage(Stats dmg) {
		//currentStat.damage(dmg.getHP(), dmg.getMP());
		currentStat.minus(dmg);
	}
	
	public void fullHeal() {
		currentStat = stat.copy();
	}
	
	
	public void addStatus(Skill.Status st, Stats effect, int term) {
		if(status.containsKey(st)) {
			status.put(st, status.get(st) + term);
		} else {
			this.status.put(st, term);
			this.effect.put(st, effect);
		}
	}
	
	/*
	public void applyBattleAction(BattleAction b) {
		if(b.getStatus() != Skill.Status.NON) {
			addStatus(b.getStatus(), b.getDmg(), 2);
		}
		damaged(b.getDmg());
	}*/
	
	public boolean currentStatus() {
		Random r = new Random();
		/*
		FREEZE,
		POISON,
		BURN,
		PARALYZE,
		STUN
		*/
		for(Skill.Status st : status.keySet()) {
			switch(st) {
				case FREEZE:
					return false;
				case PARALYZE:
					// 50% paralyze
					if(r.nextInt() % 2 == 0)
						return true;
					return false;
				case POISON:
					// - 10% hp
					this.currentStat.damage((int) Math.round(stat.getHP() * 0.1));
					return false;
				
				case BURN:
					// - 10% hp
					this.currentStat.damage((int) Math.round(stat.getHP() * 0.1));
					return false;
				
				case STUN:
					// stun
					return true;
				default:
					System.out.println("unknow: " + st);
					return false;
					
			}
		}
		return false;
	}
	
	public void fixDxDy() {
		int index = f.getIndex(this);
		if(index != -1) {
			dx = f.getAccurateLocationX(index);
			dy = f.getAccurateLocationY(index);
			
			System.out.println(dx + " " + dy);
			
		} else {
			System.out.println("this character does not in field");
		}
	}
	
	public void levelUp() {
		
	}
	
}
