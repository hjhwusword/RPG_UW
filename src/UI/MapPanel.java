package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


import simulator.RPGMap;
import simulator.WalkableCharacter;
import util.RPGWalkLoader;


public class MapPanel extends JPanel implements ActionListener{
	
	private Timer timer;
	private RPGMap map;
	
	private Image background;
	
	//private boolean keyed;
	
	
	public MapPanel() {
		super();
		
		map = new RPGMap();
		
		timer = new Timer(50, this);
		timer.start();
		
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		setFocusable(true);
		this.addKeyListener(new WalkKeyListener());
		
		background = new ImageIcon("3.jpg").getImage();
	}
	
	public void addObserver(Observer ob) {
		map.addObserver(ob);
	//	map.addObserver(this);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(background, 0, 0, this);
		
		//map.paint(g, keyed);
		map.paint(g);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
		map.update();
	}
	
	/*
	@Override
	public void update(Observable o, Object arg) {
		keyed = false;
	}*/
	
	
	private class WalkKeyListener implements KeyListener {
		
		
		@Override
		public void keyPressed(KeyEvent arg0) {
				
				char ch = arg0.getKeyChar();
				switch(ch){
				case('w'):
					map.setDy(-RPGMap.WALKRATE);
					break;
				case('s') :
					map.setDy(RPGMap.WALKRATE);
					break;
				case('a') :
					
					map.setDx(-RPGMap.WALKRATE);
					break;
				case('d') :
					map.setDx(RPGMap.WALKRATE);
					break;
				default:
					
				}
			//	keyed = true;
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			char ch = arg0.getKeyChar();
			
			switch(ch){
			case('w'):
				map.addDy(RPGMap.WALKRATE);
				break;
			case('s') :
				map.addDy(-RPGMap.WALKRATE);
				break;
			case('a') :
				
				map.addDx(RPGMap.WALKRATE);
				break;
			case('d') :
				map.addDx(-RPGMap.WALKRATE);
				break;
			default:
				
			}
			//keyed = false;
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
	
		}
		
		
	}

 }
