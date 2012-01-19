package Map.EventBlock;

import java.util.LinkedList;

import util.Arguments;

import ImgID.CharacterImgID;

public final class BattleBlock extends EventBlock {
	private LinkedList<Character> enemies;
	
	public BattleBlock(CharacterImgID img) {
		super(img);
		this.enemies = new LinkedList<Character>();
	}
	
	public int getNumberOfEnemu() {
		return this.enemies.size();
	}
	
	public void addEnemy(Character enemy) {
		assert(Arguments.isNotNull(enemy));
		this.enemies.add(enemy);
	}
	
	public Character removeEnemy() {
		//TODO finish character ID thing
		return null;
	}
	
	@Override
	public EventType getType() {
		return EventType.BATTLE;
	}
}
