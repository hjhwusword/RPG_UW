package battle;

import java.awt.Point;
import java.util.*;
import util.*;

public class BattleAction implements Comparable<BattleAction> {
	private Stats dmg;
	private Character actioner;
	private List<Integer> targetLocation;
	private List<Animation> animation;
	private Skill.Status status;
	private int original;
	private int move;
	private Field field;
	private Skill sk;
	private Item it;
	private Point location;
	private boolean ownField;
	
	public BattleAction(int original, int move, Character actioner) {
		this(0, actioner);
		this.original = original;
		this.move = move;
	}

	public BattleAction(int dmg, Character actioner, int target) {
		this(dmg, actioner);
		setTarget(target);
	}
	
	public BattleAction(Skill sk, Character actioner, int target) {
		//this(sk.getEffect().getHP(), actioner, target);
		this(sk.getEffect(), actioner, null, Skill.Status.NON);
		System.out.println("hp minus is " + dmg.getHP());
		this.sk = sk;
		this.status = sk.getStatus();
		this.sk.setBattleAction(this);
		setTarget(target);
		targetExpand();
	}
	
	public BattleAction(Item it, Character actioner, int target) {
		this(it.getStats(), actioner, null, it.getStatus());
		setTarget(target);
		this.it = it;
	}
	
	public BattleAction(Stats dmg, Character actioner, Skill.Status status) {
		this(dmg, actioner, null, status);
		
	}
	
	private BattleAction(int dmg, Character actioner) {
		this(new Stats(dmg, 0, 0, 0, 0, 0, 0), actioner, Skill.Status.NON);
	}
	
	
	public BattleAction(Stats dmg, Character actioner, List<Integer> targetLocation, Skill.Status status) {
		
		//System.out.println("this dmg created " + dmg.getAttack());
		
		this.dmg = dmg;
		this.actioner = actioner;
		this.targetLocation = targetLocation;
		this.status = status;
		this.field = actioner.getField();
		original = -1;
		move = -1;
		sk = null;
		it = null;
	//	animation = null;
		location = new Point(0, 0);
		ownField = false;
		//animation = new ArrayList<Animation>();
		animation = null;
	}
	
	public boolean isMove() {
		return move >= 0 && original >= 0;
	}
	
	public boolean isSkill() {
		return sk != null;
	}
	
	public boolean isItem() {
		return it != null;
	}
	
	public boolean isOwnField() {
		return ownField;
	}
	
	public void setTarget(List<Integer> target) {
		this.targetLocation = target;
	}
	
	public void setTarget(int target) {
		List<Integer> temp = new ArrayList<Integer>();
		temp.add(target);
		setTarget(temp);
	}
	
	/*
	public void setField(Field f) {
		field = f;
	}*/
	public void setOwnField() {
		ownField = true;
	}
	
	public void setAnimation(List<Animation> animation) {
		//this.animation.addAll(animation);
		this.animation = animation;
	}
	
	/*
	public void changeLocation(int location) {
		
	}*/
	
	public Stats getDmg() {
		return dmg;
	}
	
	public Skill getSkill() {
		return sk;
	}
	
	public Character getCharacter() {
		return actioner;
	}
	
	public List<Integer> getTarget() {
		return targetLocation;
	}
	
	public List<Animation> getAnimation() {
		return animation;
	}
	
	public int compareTo(BattleAction o) {
		return o.actioner.getSpeed() - this.actioner.getSpeed();
	}
	
	public Skill.Status getStatus() {
		return status;
	}
	
	public int getLocation() {
		return original;
	
	}
	
	public int getMoveLocation() {
		return move;
	}
	
	public boolean hasAnimation() {
		//return animation.size() >= 0;
		return animation != null;
	}
	
	public void targetExpand() {
		int target = targetLocation.get(0);

		switch(sk.getArea()) {
		case SQUARE:
			expandRow(target, 1);
			expandCol(targetLocation, 1);
			break;
		case ALL:
			expandRow(target, 2);
			expandCol(targetLocation, 2);
			break;
		
		case ROW:
			expandRow(target, 2);
			break;
		case COL:
			expandCol(target, 2);
			break;
			/*
		R,
		RTHREE,
		RARROW,
		RROW*/
			
		}
		
		Collections.sort(targetLocation);
		System.out.print("target ++++++++++++++ ");
		for(int i : targetLocation) {
			System.out.print("  " + i);
		}
		System.out.println();
	}
	
	// expand everyting in the target
	
	private void expandRow(List<Integer> target, int num) {
		int size = target.size();
		for(int i = 0; i < size; i++) {
			expandRow(target.get(i), num);
		}
	}
	// this expand target correctly
	// num > 0  expand right
	// num < 0 expand left

	private void expandRow(int target, int num) {	
		
		if(num < Battle.SIZE) {
			int temp = target + 1;
			while(num > 0) {
				if(temp > target && temp % Battle.SIZE != 0) {
					targetLocation.add(temp);
					temp++;
					num--;
				} else if(temp > target && temp % Battle.SIZE == 0) {
					temp = target - 1;
				} else if(temp < target && temp >= 0) {
					targetLocation.add(temp);
					temp--;
					num--;
				} else  {
					System.out.println("someting is wrong with expand row");
				}
		
			}
		}
		
		
		
	}
	
	private void expandCol(List<Integer> target, int num) {
		int size = target.size();
		for(int i = 0; i < size; i++) {
			expandCol(target.get(i), num);
		}
	}
	
	
	private void expandCol(int target, int num) {
		if(num < Battle.SIZE) {
			int temp = target + Battle.SIZE;
			while(num > 0) {
				if(temp > target && temp < Battle.SIZE * Battle.SIZE) {
					targetLocation.add(temp);
					temp += Battle.SIZE;
					num--;
				} else if(temp >= Battle.SIZE * Battle.SIZE) {
					temp = target - Battle.SIZE;
				} else if(temp < target && temp >= 0) {
					targetLocation.add(temp);
					temp -= Battle.SIZE;
					num--;
				} else  {
					System.out.println("someting is wrong with expand COL");
				}
		
			}
		}
	}
	
	
	/*
	public Field getField() {
		return field;
	}*/
	
	
	
}
