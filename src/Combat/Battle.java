package Combat;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*; 

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import util.MusicPlayer;
import util.SeriesImageLoader;

import Character.Character;
import UI.BattleSkillPanel;

public class Battle {
	
	public static final int SIZE = 3;
	
	private Field self;
	private Field enemy;
	
	private int selfx, selfy;
	private int enemyx, enemyy;
	
	private Random r;
	
	private boolean inBattle;
	
	private List<BattleAction> action;
	//private List<BattleAction> currentAction;
	private Queue<BattleAction> currentAction;
	
	private BattleSkillPanel bsp;
	
	private MusicPlayer player;
	
	//private BattleEvent currentEvent;
	
	public static enum BattleEvent {
		MOVE,
		ATTACK,
		SKILL,
		ITEM,
		GUARD, 
		RUN,
		NON
	}
	
	public Battle() {
		self = new Field(SIZE * SIZE);
		enemy = new Field(SIZE * SIZE);
		r = new Random();
		inBattle = false;
		action = new LinkedList<BattleAction>();
		currentAction = new LinkedList<BattleAction>();
		//currentEvent = BattleEvent.NON;
		player = new MusicPlayer("audio/battleXP_2.mid");
		player.start();
	}
	
	public void setDxDy(int dx, int dy) {
		self.setDxDy(dx, dy);
		enemy.setDxDy(dx, dy);
	}
		
	private boolean run() {
		//throw new IllegalArgumentException();
		if(r.nextBoolean()) {
			return true;
		}
		
		return false;
	}
	
	
	
	private void applyAction() {
		
		currentAction.addAll(action);
		
		action.clear();
	}
	
	public void setFieldButton(JPanel p) {
		p.setLayout(new GridLayout(0, 3));
		self.setFieldButton(p);
		
		// middle button
		GoButton dummy = new GoButton();
		dummy.setOpaque(false);
		p.add(dummy);
		
		enemy.setFieldButton(p);
	}
	
	public void setBattleButton(JPanel p) {
		p.setLayout(new GridLayout(0, 3));
		
		JPanel temp = new JPanel();
		temp.setLayout(new GridLayout(BattleEvent.values().length - 1, 0));
		
		SeriesImageLoader img = new SeriesImageLoader("item_3.png", 16, 20);
		int[] iconIndex = {95, 4, 176, 112, 86, 185};
		BattleEvent[] tempList = BattleEvent.values();
		for(int i = 0; i < tempList.length - 1; i++) {
			//BattleButton tempButton = new BattleButton(tempList[i]);
			BattleButton tempButton = new BattleButton(tempList[i], new ImageIcon(img.getSingleImage(iconIndex[i])));
			temp.add(tempButton);
		}
		
		p.add(temp);
		
	}
	
	public void setSkillPanel(BattleSkillPanel bsp) {
		this.bsp = bsp;
		self.setSkillPanel(bsp);
		enemy.setSkillPanel(bsp);
	}
	
	
	public void setSelf(Field input) {
		self = input;
	}
	
	public void setEnemy(Field input) {
		enemy = input;
	}
	
	public void setLocation(int selfx, int selfy, int enemyx, int enemyy) {
		
		this.selfx = selfx;
		this.selfy = selfy;
		this.enemyx = enemyx;
		this.enemyy = enemyy;
		
		self.setLocation(selfx, selfy);
		enemy.setLocation(enemyx, enemyy);
	}
	
	public void selfAdd(List<Character> list) {
		self.put(list);
	}
	
	public void enemyAdd(List<Character> list) {
		enemy.put(list);
	}
	
	private void addCharacter(Field input, List<Character> list) {
		input.put(list);
	}
	
	public boolean isTargetSet() {
		return enemy.isTargetSet();
	}
	
	public void update() {
		if(enemy.isTargetSet()) {
			self.setTarget(enemy.getTarget());
			enemy.targetClear();
		}
		
		if(bsp != null) {

			self.setSkill(bsp.getSkill());
			self.setItem(bsp.getItem());
			
		}
		
		if(!currentAction.isEmpty() && !isAnimating()) {
			BattleAction b = currentAction.poll();
			BattleAction b2 = null;
			Field temp = correctField(b);
			List<BattleAction> currentList = new LinkedList<BattleAction>();
			
			currentList.add(b);
			while(!currentAction.isEmpty() && currentAction.peek().getCharacter().getField() == b.getCharacter().getField()) {
				currentList.add(currentAction.poll());
			}
			
			for(BattleAction ba : currentList) {
				correctField(ba).applyAction(ba);
			}
		}
		
		if(self.finish()) {
			System.out.println("game over");
		} else if(enemy.finish()) {
			System.out.println("Win");
		}
	}
	
	
	private Field correctField(BattleAction b) {
		if(b.isOwnField()) {
			return b.getCharacter().getField();
		} else if(b.getCharacter().getField() == enemy) {
			return self;
		} else {
			return enemy;
		}
	}
	
	private boolean isAnimating() {
		return self.isAnimating() || enemy.isAnimating();
	}
	
	
	public void paint(Graphics g, JPanel panel, int dx, int dy) {
		self.paint(g, panel,  selfx, selfy, dx, dy);
		enemy.paint(g, panel, enemyx, enemyy, dx, dy);
	}
	
	
	
	private class BattleButton extends JButton implements ActionListener {
		BattleEvent e;
		
		public BattleButton(BattleEvent e) {
			//super(e.toString());
		//	this.e = e;
		//	this.addActionListener(this);
			this(e, null);
		}
		
		
		public BattleButton(BattleEvent e, Icon i) {
			super(e.toString(), i);
			this.e = e;
			this.addActionListener(this);
		}
		

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("current event: " + e);
			self.setBattleEvent(e);
			enemy.setBattleEvent(e);
			//currentEvent = e; 
		}
	}
	
	private class GoButton extends JButton implements ActionListener {
		
		public GoButton() {
			super("GO!");
			this.addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("GO! ");
			
			//self.processAI(enemy.getCharacters());
			self.getAction(action);
			enemy.processAI(self.getCharacters());
			enemy.getAction(action);

			
			
			Collections.sort(action);
			applyAction();
			
			System.out.println("action size is " + action.size());
			//currentEvent = e; 
		}
	}
	
}
