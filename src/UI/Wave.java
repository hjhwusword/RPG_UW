package UI;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Wave {
	
	private int x, y;
	private Image image;
	
	boolean visible;
	
	private final int BOARD_WIDTH = 500;
	private final int WAVE_SPEED = 2;
	
	private int width, height;

	public Wave(int x, int y) {
		ImageIcon ii = new ImageIcon("wave.png");
		image = ii.getImage();
		
		visible = true;
		this.x = x;
		this.y = y;
		
		width = image.getWidth(null);
		height = image.getHeight(null);
		
			
	}
	
	public Image getImage() {
		return image;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
	public void move() {
		x += WAVE_SPEED;
		if(x > BOARD_WIDTH)
			visible = false;
	}
}
