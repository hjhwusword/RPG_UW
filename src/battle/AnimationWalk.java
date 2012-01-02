package battle;

import java.awt.Image;
import java.awt.Point;
import java.util.List;

import util.SeriesImageLoader;

public class AnimationWalk extends Animation2 {
	
	public AnimationWalk(List<Image> walkcycle, Point start, Point goal) {
		super(new SeriesImageLoader(walkcycle, null, 0, 0, 0, 1), start, goal);
	}
	
	
	@Override
	public Point getPoint() {
		return null;
	}
	
	
	
	
	
}
