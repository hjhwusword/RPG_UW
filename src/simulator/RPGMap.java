package simulator;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import UI.BattleWindow;

import util.RPGWalkLoader;

public class RPGMap extends Observable {
	public static final int WALKRATE = 10;
	public static final int STEPENCOUNTER = 10000;
	
	public static int FOLLOWSIZE = 20;
	public static final int FOLLOWDISTANCE = 5;
	
	private int x;
	private int y;
	private int dx;
	private int dy;
	
	private int step;
	
	private List<FollowRecord> list;
	private Map<Integer, Image> stillList;
	
	private List<WalkableCharacter> follower;
	
	private List<WalkableEnemy> enemy;
	
	public enum MapEvent {
		BATTLE,
		MAP,
		NON,
	}
	
	
	public RPGMap() {
		
		RPGWalkLoader walkLoader = new RPGWalkLoader("rpg_resource_0.png", 4, 2);
		RPGWalkLoader enemyLoader = new RPGWalkLoader("rpg_resource_8.png", 4, 2);
		
		// player 
		x = y = dx = dy = 0; 
		step = 0;
		
		list = new LinkedList<FollowRecord>();
		stillList = new HashMap<Integer, Image>();
		follower = new ArrayList<WalkableCharacter>();
		
		follower.add(new WalkableCharacter(walkLoader, 0));
		follower.add(new WalkableCharacter(walkLoader, 1));
		follower.add(new WalkableCharacter(walkLoader, 2));
		follower.add(new WalkableCharacter(walkLoader, 3));
		
		FOLLOWSIZE = follower.size() * this.FOLLOWDISTANCE + 1;
		
		for(int i = 0; i < FOLLOWSIZE; i++) {
			storeFollow(follower.get(0));
			
		}
		
		storeStill();
		
		// enemy
		enemy = new ArrayList<WalkableEnemy>();
		
		for(int i = 0; i < 5; i++) {
			enemy.add(new WalkableEnemy(enemyLoader, i));
			enemy.get(i).setLocation(250, 250);
			enemy.get(i).setBound(0, BattleWindow.WIDTH, 0, BattleWindow.HEIGHT);
		}
		
	}
	
	public void paint(Graphics g) {
		
		
		if(dx != 0 || dy != 0) {
			step++;
			this.storeFollow(follower.get(0));
			this.storeStill();
		}
		
		
		this.paintFollower(g);
		this.paintEnemy(g);
	}
	
	public void update() {
		
		x += dx;
		y += dy;
		
		if(step > this.STEPENCOUNTER) {
			specialNotify(MapEvent.BATTLE);
			step = 0;
		}
	}
	
	public void specialNotify(MapEvent me) {
		setChanged();
		this.notifyObservers(me);
		dx = 0; 
		dy = 0;
		clearChanged();
	}
	
	
	public void addDx(int dx) {
		this.dx += dx;
		
	}
	
	public void addDy(int dy) {
		this.dy += dy;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	
	public void setDxDy(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void setDx(int dx) {
		
		if(dx < 0) {
			follower.get(0).setDirection(RPGWalkLoader.Direction.LEFT);
		} else if(dx > 0) {
			follower.get(0).setDirection(RPGWalkLoader.Direction.RIGHT);
		}
		this.dx = dx;
	}
	
	public void setDy(int dy) {
		if(dy < 0) {
			follower.get(0).setDirection(RPGWalkLoader.Direction.UP);
		} else if(dy > 0) {
			follower.get(0).setDirection(RPGWalkLoader.Direction.DOWN);
		}
		this.dy = dy;
	}
	
	public void storeFollow(WalkableCharacter c) {
		list.add(0, new FollowRecord(x, y, c.getDirection()));
		if(list.size() > this.FOLLOWSIZE) {
			list.remove(list.size() - 1);
		}
	}
	
	public void storeStill() {
		for(int i = 0; i < follower.size(); i++) {
			follower.get(i).setDirection(list.get(i * this.FOLLOWDISTANCE).d);
			stillList.put(i, follower.get(i).getImage());
		}
	}
	
	public void paintFollower(Graphics g) {
		for(int i = 0; i < follower.size(); i++) {
			int index = i * this.FOLLOWDISTANCE;
			FollowRecord fr = list.get(index);
			g.drawImage(stillList.get(i), fr.x, fr.y, null);
		}
	}
	
	public void paintEnemy(Graphics g) {
		WalkableEnemy temp = null;
		for(int i = 0; i < enemy.size(); i++) {
			temp = enemy.get(i);
			g.drawImage(temp.getImage(), temp.getX(), temp.getY(), null);
			temp.walk();
			
		}
	}
	
	private class FollowRecord {
		public int x;
		public int y;
		public RPGWalkLoader.Direction d;
	
		public FollowRecord(int x, int y, RPGWalkLoader.Direction d) {
			this.x = x;
			this.y = y;
			this.d = d;
		}
		
	}
	
}
