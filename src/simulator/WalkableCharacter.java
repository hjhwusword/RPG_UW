package simulator;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.RPGWalkLoader;

public class WalkableCharacter {
	private Character c;
	protected Map<RPGWalkLoader.Direction, List<Image>> walk;
	private int w;
	private int h;
	
	private RPGWalkLoader.Direction d;
	
	protected int counter;
	
	
	public WalkableCharacter(RPGWalkLoader wk, int character) {
		w = wk.getWidth();
		h = wk.getHeight();
		walk = new HashMap<RPGWalkLoader.Direction, List<Image>>();
		d = RPGWalkLoader.Direction.LEFT;
		c = null;
		counter = 0;
		wk.setCharacter(character);
		
		for(RPGWalkLoader.Direction d : RPGWalkLoader.Direction.values()) {
			
			walk.put(d , new ArrayList<Image>());
			for(int i = 0 ; i < wk.COL; i++) {
				wk.setDirection(d);
				walk.get(d).add(wk.getImage());
			}
		}
		
	}
	
	public void setDirection(RPGWalkLoader.Direction d) {
		this.d = d;
	}
	
	public void setCharacter(Character c) {
		this.c = c;
	}
	
	
	public Character getCharacter() {
		return c;
	}
	
	public Image getImage() {
		//System.out.println(d);
		
		List<Image> img = walk.get(d);
		Image m = img.get(counter);
		counter = (counter + 1) % img.size();
		return m;
	}
	
	
	public int getWidth() {
		return w;
	}
	
	public int getHeight() {
		return h;
	}
	
	public RPGWalkLoader.Direction getDirection() {
		return d;
	}
	
	
	
}
