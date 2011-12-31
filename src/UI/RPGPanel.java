package UI;

import javax.swing.*;

import util.SeriesImageLoader;
import Combat.Animation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
//import java.util.Timer;
//import java.util.TimerTask;
import java.util.List;
import java.util.ArrayList;

public class RPGPanel extends JPanel /*implements Runnable*/ implements ActionListener{
	
	private Image backGround;
	private Image person;
	private Timer timer;
	
	private Thread animator;
	
	private int x;
	private int y;
	
	
	private final int DELAY = 50;
	
	private Person p;
	
	private List<AnotherPerson> enemy;
	private boolean ingame;
	private int B_WIDTH;
	private int B_HEIGHT;
	
	private int[][] pos = { 
	        {2380, 29}, {2500, 59}, {1380, 89},
	        {780, 109}, {580, 139}, {680, 239}, 
	        {790, 259}, {760, 50}, {790, 150},
	        {980, 209}, {560, 45}, {510, 70},
	        {930, 159}, {590, 80}, {530, 60},
	        {940, 59}, {990, 30}, {920, 200},
	        {900, 259}, {660, 50}, {540, 90},
	        {810, 220}, {860, 20}, {740, 180},
	        {820, 128}, {490, 170}, {700, 30}
	     };
	
	private static Animation image = new Animation(new SeriesImageLoader("fire_3.png", 5, 5));
	
	
	public RPGPanel() {
		super();
		
		addKeyListener(new TAdapter());
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		setFocusable(true);
		
		
		//ImageIcon ii = new ImageIcon(this.getClass().getResource("3.jpg"));
		ImageIcon ii = new ImageIcon("3.jpg");
		ImageIcon ii2 = new ImageIcon("person.jpg");
		
		person = ii2.getImage();
		backGround = ii.getImage();
		
		setSize(500, 500);
		x = y = 10;
		
		p = new Person();
		ingame = true;
		initEnemy();
		
		
		timer = new Timer(5, this);
		timer.start();
		
		//timer = new Timer();
		//timer.scheduleAtFixedRate(new ScheduleTask(), 100, 10);
		
	}
	
	public void addNotify() {
		super.addNotify();
		B_WIDTH = getWidth();
		B_HEIGHT = getHeight();
	}
	
	public void initEnemy() {
		enemy = new ArrayList<AnotherPerson>();
		
		for(int i = 0; i < pos.length; i++) {
			enemy.add(new AnotherPerson(pos[i][0], pos[i][1]));
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D) g;
		/*
		if(ingame) {
		
			Graphics2D g2 = (Graphics2D) g;
			
			/*
			RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			
			g2.setRenderingHints(rh);
			
			Dimension size = getSize();
			double w = size.getWidth();
			double h = size.getHeight();
			
			Ellipse2D e = new Ellipse2D.Double(0, 0, 80, 130);
			g2.setStroke(new BasicStroke(1));
			g2.setColor(Color.gray);
			//
		
			
			
			g.drawImage(backGround, 10, 10, null);
			g.drawImage(person, x, y, this);
			
			if(p.isVisible())
				g.drawImage(p.getImage(), p.getX(), p.getY(), this);
			
			List<Wave> list = p.getWave();
			
			for(int i = 0; i < list.size(); i++) {
				Wave w = list.get(i);
				g.drawImage(w.getImage(), w.getX(), w.getY(), this);
				
			}
			
			for(int i = 0; i < enemy.size(); i++) {
				AnotherPerson o = enemy.get(i);
				if(o.isVisible())
					g.drawImage(o.getImage(), o.getX(), o.getY(), this);
			}
			
			g.setColor(Color.WHITE);
			g.drawString("person left" + enemy.size(), 5, 15);
			
		} else {
			String msg = "Game Over";
			Font small = new Font("Helvetica", Font.BOLD, 14);
			FontMetrics metr = this.getFontMetrics(small);
			
			g.setColor(Color.white);
			g.setFont(small);
			g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
		}*/
		//Animation temp = new Animation(new SeriesImageLoader("fire_3.png", 5, 5));
		//SeriesImageLoader temp2 = new SeriesImageLoader("fire_3.png", 5, 5);
		
		//g.drawImage(temp2.getSingleImage(), 0, 0, this);
		//g.drawImage(image.getSingleImage(), 0, 0, this);
		//g.drawImage(new ImageIcon("fire_1.png").getImage(), 0, 0, this);
		//g.drawImage(temp2.getSingleImage(0), 10, 10, this);
		
		//AffineTransform ts = new AffineTransform();
		//ts.translate(-50, -50);
		
		//g2.drawImage(temp.getImage(), ts, this);
		
		//g.drawImage(temp.getImage(), -50, -50, this);
		
		
		
		
		
		
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
		/*
		for(double deg = 0; deg < 360; deg += 5) {
			AffineTransform at = AffineTransform.getTranslateInstance(w / 2, h / 2);
			
			at.rotate(Math.toRadians(deg));
			g2.draw(at.createTransformedShape(e));
		}*/
	}
	
	
	@Override
	// timer for swing
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(enemy.size() == 0)
			ingame = false;
		
		List<Wave> list = p.getWave();
		
		x += 1;
		y += 1;
		
		
		if(y > 500) {
			y = -45;
			x = - 45;
		}
		
		
		for(int i = 0; i < list.size(); i++) {
			Wave w = list.get(i);
			if(w.isVisible())
				w.move();
			else
				list.remove(i);
		}
		
		
		for (int i = 0; i < enemy.size(); i++) {
			AnotherPerson o = enemy.get(i);
			if(o.isVisible())
				o.move();
			else
				enemy.remove(i);
		}
		
		p.move();
		checkCollisions();
		
		repaint();
	}
	
	public void checkCollisions() {
		Rectangle r3 = p.getBounds();
		
		for(AnotherPerson o : enemy) {
	
			Rectangle r2 = o.getBounds();
			
			if(r3.intersects(r2)) {
				p.setVisible(false);
				o.setVisible(false);
				ingame = false;
			}
		}
		
		
		for(Wave w : p.getWave()) {
			Rectangle r = w.getBounds();
			
			for(int j =0; j < enemy.size(); j++) {
				AnotherPerson o = enemy.get(j);
				Rectangle r2 = o.getBounds();
				
				if(r.intersects(r2)) {
					o.setVisible(false);
					w.setVisible(false);
				}
			}
			
			
		}
	}
	
	/*
	public void addNotify() {
		super.addNotify();
		animator = new Thread(this);
		animator.start();
	}
	
	public void cycle() {
		x += 1;
		y += 1;
		
		if(y > 500) {
			y = -45;
			x = - 45;
		}
	}*/
	
	private class TAdapter extends KeyAdapter {
		
		public void keyReleased(KeyEvent e) {
			p.keyReleased(e);
		}
		
		public void keyPressed(KeyEvent e) {
			p.keyPressed(e);
		}
	}
	
	/*
	// for task with util timer
	private class ScheduleTask extends TimerTask {
		public void run() {
			x += 1;
			y += 1;
			
			if(y > 500) {
				y = -45;
				x = - 45;
			}
			repaint();
		}
	}*/
	/*
	@Override
	public void run() {
		// TODO Auto-generated method stub
		long beforeTime, timeDiff, sleep;
		beforeTime = System.currentTimeMillis();
		
		while(true) {
			
			cycle();
			repaint();
			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = DELAY - timeDiff;
			
			if(sleep < 0)
				sleep = 2;
			
			try {
				Thread.sleep(sleep);
			} catch(InterruptedException e) {
				System.out.println("interrupted");
			}
			
			beforeTime = System.currentTimeMillis();
		}
		
	}*/
	
}
