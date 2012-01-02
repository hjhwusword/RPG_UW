package map;

import java.awt.Image;
import java.util.List;
import java.util.Random;

import util.RPGWalkLoader;

public class WalkableEnemy extends WalkableCharacter {
	
	public int RANDOMRATE = 12;
	
	private int x;
	private int y;
	private int dx;
	private int dy;
	
	private Random r;
	
	private Bound bound;
	
	public WalkableEnemy(RPGWalkLoader wk, int Character) {
		super(wk, Character);
		r = new Random();
		bound = null;
	}
	
	public void setDx(int dx) {
		this.dx = dx;
	}
	
	public void setDy(int dy) {
		this.dy = dy;
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setBound(int minx, int maxx, int miny, int maxy) {
		this.bound = new Bound(minx, maxx, miny, maxy);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void walk() {
		
		int temp = r.nextInt(RANDOMRATE);
			
		switch(temp) {
		case 0:
			dx = RPGMap.WALKRATE;
			break;
		case 1:
			dx = 0;
			break;
		case 2:
			dx = -RPGMap.WALKRATE;
			break;
		default:
			
		}
		
		temp = r.nextInt(RANDOMRATE);
		
		switch(temp) {
		case 0:
			dy = RPGMap.WALKRATE;
			break;
		case 1:
			dy = 0;
			break;
		case 2:
			dy = -RPGMap.WALKRATE;
			break;
		default:
			
		}
		
		checkBound();
		
		if(dx < 0) {
			this.setDirection(RPGWalkLoader.Direction.LEFT);
		} else if (dx > 0) {
			this.setDirection(RPGWalkLoader.Direction.RIGHT);
		}
		
		if(dy > 0) {
			this.setDirection(RPGWalkLoader.Direction.DOWN);
		} else if(dy < 0){
			this.setDirection(RPGWalkLoader.Direction.UP);
		}
		
		x += dx;
		y += dy;
	
	}
	
	private void checkBound() {
		if(bound != null) {
			if(x < bound.minx) {
				dx = RPGMap.WALKRATE;
			} else if(x + this.getWidth() > bound.maxx) {
				dx = -RPGMap.WALKRATE;
			}
			
			if(y < bound.miny) {
				dy = RPGMap.WALKRATE;
			} else if(y + this.getHeight() > bound.maxy) {
				dy = -RPGMap.WALKRATE;
			}
		}
	}
	
	@Override
	public Image getImage() {
		List<Image> img = walk.get(getDirection());
		Image temp = img.get(counter);
		if(dx > 0 || dy > 0) {
			counter = (counter + 1) % img.size();
		}
		return temp;
	}
	
	private class Bound {
		public int minx;
		public int maxx;
		public int miny;
		public int maxy;
		
		public Bound(int minx, int maxx, int miny, int maxy) {
			this.minx = minx;
			this.maxx = maxx;
			this.miny = miny;
			this.maxy = maxy;
		}
	}
	
}
