package Combat;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import util.SeriesImageLoader;

public class Animation2 extends Animation {

	private Point goal;
	private int pause;
	
	public Animation2(SeriesImageLoader input, Point start, Point goal) {
		super(input);
		this.p = start;
		this.goal = goal;
		this.pause = 0;
	}
	
	@Override
	public Point getPoint() {
		Point temp = p;
		update();
		return p;
	}
	
	@Override
	public Image getImage() {
		if(pause > 0)
			return null;
		return super.getImage();
	}
	
	@Override
	public void setPoint(int x, int y) {
		super.setPoint(x, y);
		goal = new Point((int)goal.getX() + x, (int)goal.getY() + y);
	}
	
	@Override
	public boolean isDone() {
	
		return p.equals(goal);
	}
	
	@Override
	public void shift(int index, int count) {
		pause = count;
	}
	
	private void update() {
		int x = (int) p.getX();
		int y = (int) p.getY();
		int goalX = (int) goal.getX();
		int goalY = (int) goal.getY();
		
		//System.out.println("update is called");
		if(pause <= 0) {
		
			if(x > goalX) {
				x--;
			} else if(x < goalX) {
				x++;
			}
			
			if(y > goalY) {
				y--;
			} else if(y < goalY) {
				y++;
			}
			
			p = new Point(x, y);
		} else {
			pause--;
		}
		
	}
	
}
