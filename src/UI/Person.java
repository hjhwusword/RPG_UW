package UI;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import util.RPGWalkLoader;

import java.util.*;

public class Person {
	
	private String person = "person.jpg";
	
	private int dx;
	private int dy;
	private int x;
	private int y;
	private Image image;
	
	private int width;
	private int height;
	
	private boolean visible;
	
	private List<Wave> wave;
	
	private final int PERSON_SIZE = 20;
	
	private RPGWalkLoader wk;
	private boolean walk;
	
	public Person() {
		//ImageIcon ii = new ImageIcon("person.png");
		
		//image = ii.getImage();
		walk = false;
		wk = new RPGWalkLoader("rpg_resource_1.png", 4, 2);
		wk.setCharacter(1);
		image = wk.getImage();
		
		wave = new ArrayList<Wave>(); 
		
		width = image.getWidth(null);
		height = image.getHeight(null);
		
		
		System.out.println("width: " + width + " height: " + height);
		
		visible = true;
		
		x = 40;
		y = 60;
	}
	
	public void move() {
		x += dx;
		y += dy;
		
		if(x < 1)
			x = 1;
		if(y < 1)
			y = 1;
		
		if(walk)
			image = wk.getImage();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Image getImage() {
		return image;
	}
	
	public List<Wave> getWave() {
		return wave;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		walk = true;
		switch(key) {
		case KeyEvent.VK_SPACE :
			slash();
			walk = false;
			break;
		case KeyEvent.VK_LEFT :
			wk.setDirection(RPGWalkLoader.Direction.LEFT);
			dx = -1;
			break;
		case KeyEvent.VK_RIGHT :
			wk.setDirection(RPGWalkLoader.Direction.RIGHT);
			dx = 1;
			break;
		case KeyEvent.VK_UP :
			wk.setDirection(RPGWalkLoader.Direction.UP);
			dy = -1;
			break;
		case KeyEvent.VK_DOWN :
			wk.setDirection(RPGWalkLoader.Direction.DOWN);
			dy = 1;
			break;
		default :
			break;
		}
		
	}
	
	public void slash() {
		wave.add(new Wave(x + PERSON_SIZE, y + PERSON_SIZE / 2));
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_LEFT :
			dx = 0;
			break;
		case KeyEvent.VK_RIGHT :
			dx = 0;
			break;
		case KeyEvent.VK_UP :
			dy = 0;
			break;
		case KeyEvent.VK_DOWN :
			dy = 0;
			break;
		default :
			break;
		}
		
		walk = false;
	}
}
