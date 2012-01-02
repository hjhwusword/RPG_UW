package battle;

import java.awt.Image;
import java.awt.Point;

import util.SeriesImageLoader;

public class Animation {
	protected SeriesImageLoader anime;
	protected Point p;
	//private int delay;
	
	public Animation(SeriesImageLoader input) {
		this(input, new Point(0, 0));
	}
	
	public Animation(SeriesImageLoader input, Point p) {
		this.anime = input;
		this.p = p;
		//delay = 0;
		anime.setDelay(10);
	}
	
	public Image getImage() {
		return anime.getSingleImage();
	}
	
	public Point getPoint() {
		return p;
	}
	
	public int getSize() {
		return anime.getSize();
	}
	
	public boolean isDone() {
		return anime.isDone();
	}
	
	public void setPoint(int x, int y) {
		p = new Point((int)p.getX() + x, (int)p.getY() + y);
	}
	
	public void save(int[] list) {
		anime.save(list);
	}
	
	public void delay(int times) {
		anime.delay(times);
	}
	
	public void setDelay(int delay) {
		anime.setDelay(delay);
	}
	
	public void shift(int index, int count) {
		anime.shift(index, count);
	}
	
	public Animation share() {
		return new Animation(anime.share());
	}
	
	public Animation shareLess() {
		return new Animation(anime.shareLess());
	}
	
	public void extend(int count) {
		anime.extend(count);
	}
}
