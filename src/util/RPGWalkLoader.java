package util;


import java.awt.Image;
import java.awt.Rectangle;

import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import javax.swing.JPanel;

public class RPGWalkLoader extends JPanel{
	
	public static final int ROW = 4;
	public static final int COL = 3;
	
	private int x;
	private int y;
	private Image source;
	private int width;
	private int height;
	
	private int w, h;
	
	private List<Image[][]> data;
	private int character;
	private int count;
	private Direction direction;
	
	public static enum Direction {
		UP,
		DOWN,
		LEFT, 
		RIGHT
	}
	
	public RPGWalkLoader(String img, int x, int y) {
		ImageIcon s = new ImageIcon(img);
		source = s.getImage();
		width = s.getIconWidth();
		height = s.getIconHeight();
		
		
		
		data = new ArrayList<Image[][]>();
		character = 0;
		count = 0;
		
		
		
		this.x = x;
		this.y = y;
		direction = Direction.DOWN;
		
		w = width / x / COL;
		h = height / y / ROW;
		
		init();
	}
	
	private void init() {
		Image person;
		Image[][] walk;
		for(int i = 0; i < y; i++) {
			for(int j = 0; j < x; j++) {
				person = createImage(new FilteredImageSource(source.getSource(), new CropImageFilter(j*width/x, i*height/y, (width / x) + 1, height/y)));
				
				int w2 = width / x;
				int h2 = height / y;
				
				
				walk = new Image[ROW][COL]; 
				
				for(int l = 0; l < ROW; l++) {
					for(int k = 0; k < COL; k++) {
						walk[l][k] = createImage(new FilteredImageSource(person.getSource(), new CropImageFilter(k*w2/COL, l*h2/ROW, (w2 / COL) + 1, h2/ROW)));
					}
				}
				
				data.add(walk);
			}
		}
	}
	
	
	
	public Image getImage() {
		count = (count + 1) % COL;
		Image[][] img = data.get(character);
		switch(direction) {
		case UP :
			return img[3][count];
		case DOWN :
			return img[0][count];
		case LEFT :
			return img[1][count];
		case RIGHT :
			return img[2][count];
		default :
			return null;
		}
		
		
	}
	
	public Image getImage(int character) {
		int old = this.character;
		setCharacter(character);
		Image m = getImage();
		this.character = old;
		return m;
	}
	
	public int getWidth() {
		return w;
	}
	
	public int getHeight() {
		return h;
	}
	
	
	public void setDirection(Direction d) {
		direction = d;
	}
	
	public void setCharacter(int index) {
		if(index < data.size())
			character = index;
	}
	
}
