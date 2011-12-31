package UI;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import util.RPGWalkLoader;
import util.SeriesImageLoader;

//import UI.RPGPanel.TAdapter;
import Character.Character;
import Combat.Animation;
import Combat.Battle;
import Combat.Field;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BattlePanel extends JPanel implements ActionListener {
	
	
	private Image background;
	private Timer timer;
	private int width;
	private int height;
	
	private int dx;
	private int dy;
	
	private RPGWalkLoader wk;
	
	private Battle battle;
	
	
	private class BattleAdapter extends KeyAdapter {
		
		public void keyReleased(KeyEvent e) {
			//p.keyReleased(e);
		}
		
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			switch(key) {
		
			case KeyEvent.VK_UP :
				//dy = 0;
				//battle.testMove();
				break;
			case KeyEvent.VK_DOWN :
			
			default :
				break;
			}
			
		}
		
	}
	
	public BattlePanel(int width, int height) {
		super();
		
		addKeyListener(new BattleAdapter());
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		setFocusable(true);
		
		ImageIcon ii = new ImageIcon("3.jpg");
		
		background = ii.getImage();
		this.width = width;
		this.height = height;
		
		
		setSize(width, height);
		
		dx = width / 3 / Battle.SIZE;
		dy = height / Battle.SIZE;
		
		
		
		timer = new Timer(5, this);
		timer.start();
		
		wk = new RPGWalkLoader("rpg_resource_0.png", 4, 2);
		
		
		battle = new Battle();
		battle.setDxDy(dx, dy);
		battle.setLocation(0, 0, width - Battle.SIZE * dx, 0);
		
		//dx = wk.getWidth();
		//dy = wk.getHeight();
		
		wk.setDirection(RPGWalkLoader.Direction.RIGHT);
		
		List<Character> one = new ArrayList<Character>();
		one.add(new Character(getThree(0)));
		one.add(new Character(getThree(1)));
		one.add(new Character(getThree(2)));
		one.add(new Character(getThree(3)));
		one.add(new Character(getThree(4)));
		one.add(new Character(getThree(5)));
		//one.add(new Character(getThree(5)));
		one.add(new Character(getThree(6)));
		one.add(new Character(getThree(7)));
	
		
		
		
		wk.setDirection(RPGWalkLoader.Direction.LEFT);
		
		List<Character> two = new ArrayList<Character>();
		
		two.add(new Character(getThree(5)));
		two.add(new Character(getThree(6)));
		two.add(new Character(getThree(7)));
		
		/*
		two.add(new Character(getThree(6)));
		two.add(new Character(getThree(7)));
		two.add(new Character(getThree(6)));
		two.add(new Character(getThree(7)));
		two.add(new Character(getThree(6)));
		two.add(new Character(getThree(7)));
		two.add(new Character(getThree(6)));
		two.add(new Character(getThree(7)));
		two.add(new Character(getThree(6)));
		*/
		
		
		
		
		SeriesImageLoader faces = new SeriesImageLoader("face_00.png", 4, 2);
		for(int i = 0; i < one.size(); i++) {
			one.get(i).setFace(faces.getSingleImage(i));
		}
		
		for(int i = 0; i < two.size(); i++) {
			two.get(i).setFace(faces.getSingleImage(i + 5));
		}
		
		
		battle.selfAdd(one);
		battle.enemyAdd(two);
		
		
		
		//init();
		battle.setFieldButton(this);
				
		
	}
	
	private void init() {
		setLayout(new GridLayout(6, 1));
		JButton attack = new JButton();
		attack.setText("attack");
		//attack.setBackground(Color.BLUE);
		add(attack);
		attack.setLocation(200, 200);
		
		JButton move = new JButton();
		move.setText("move");
		add(move);
	}
	
	private List<Image> getThree(int i) {
		List<Image> temp = new ArrayList<Image>();
		
		
		for(int j = 0; j < 3; j++) {
			temp.add(wk.getImage(i));
		}
		
		return temp;
	}
	
	public void connectSkillPanel(BattleSkillPanel bsp) {
		battle.setSkillPanel(bsp);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		g.drawImage(background, 0, 0, null);
		
		battle.paint(g, this, dx, dy);
		
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
			
	}
	
	public void connectPanel(JPanel p) {
		battle.setBattleButton(p);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(battle != null)
			battle.update();
		repaint();
	}
	/*
	private class BattleMouse implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		private static BufferedImage resizeImage(BufferedImage originalImage, int type){
			BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
			Graphics2D g = resizedImage.createGraphics();
			g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
			g.dispose();
 
			return resizedImage;
    	}
		
	}*/

}
