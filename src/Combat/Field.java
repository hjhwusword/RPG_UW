package battle;
import java.awt.Button;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

import util.SeriesImageLoader;

import UI.BattleSkillPanel;
import battle.Battle.BattleEvent;

public class Field {
	
	//Character[][] f;
	//public static final int ROW = 3;
	public static int ACTION = 3;
	
	private Character[] f;
	
	private int size;
	private boolean pc;
	private int x, y;
	private int dx, dy;
	
	private List<FieldButton> buttonList;
	
	private int person;
	private Skill.Skills currentSkill;
	private Item.Items currentItem;
	private BattleEvent currentEvent;
	private Character acter;
	
	private List<BattleAction> actionList;
	
	private BattleSkillPanel skillPanel;
	
	private List<Animation> animation;
	private List<BattleAction> animatingAction;
	
	private List<Character> grave;
	
	
	public Field(int size) {
		this(size, false);
	}
	
	public Field(int size, boolean pc) {
		if(size <= 0)
			size = 1;
		
		this.size = size;
		f = new Character[size];
		this.pc = pc;
		x = 0;
		y = 0;
		dx = dy = 0;
		
		// for move
		
		person = -1;
		currentSkill = null;
		currentItem = null;
		currentEvent = BattleEvent.NON;
		acter = null;
		
		
		skillPanel = null;
		
		actionList = new LinkedList<BattleAction>();
		
		buttonList = new ArrayList<FieldButton>();
		for(int i = 0; i < size; i++) {
			buttonList.add(new FieldButton(i));
		}
		
		animation = new LinkedList<Animation>();
		animatingAction = new ArrayList<BattleAction>();
		grave = new LinkedList<Character>();
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setSkillPanel(BattleSkillPanel skillPanel) {
		this.skillPanel = skillPanel;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getDx() {
		return dx;
	}
	
	public int getDy() {
		return dy;
	}
	
	public List<BattleAction> getAction() {
		return actionList;
	}
	
	public boolean finish() {
		boolean end = true;
		for(int i = 0; i < f.length; i++) {
			if(f[i] != null) {
				Character c = f[i];
				if(c.dead()) {
					grave.add(c);
					f[i] = null;
				} else {
					end = false;
				}
			}
		}
		
		return end;
	}

	
	public void setFieldButton(JPanel p) {
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(Battle.SIZE, Battle.SIZE));
		for(FieldButton b : buttonList) {
			p2.add(b);
		}
		p2.setOpaque(false);
		//System.out.println("added");
		p.add(p2);
	}
	
	
	public void put(Character c, int index) {
		if(index < f.length - 1) {
			f[index] = c;
			c.setField(this);
		}
	}
	
	public void put(List<Character> c) {
		if(c.size() > size)
			System.out.println("list is size too big");
		for(int i = 0; i < c.size(); i++) {
			f[i] = c.get(i);
			if(f[i] != null)
				f[i].setField(this);
		}
	}
	
	public void setBattleEvent(BattleEvent e) {
		
		switch(e) {
		case MOVE:
			if(person != -1) {
				acter = getCharacter(person);
			}
			break;
		case ATTACK:

			break;
		case SKILL:
			if(skillPanel != null && (person != -1 && getCharacter(person) != null)) {
				skillPanel.setSkill(getCharacter(person).getSkill());
				acter = getCharacter(person);
			}
			
			break;
		case ITEM:
			if(skillPanel != null && person != -1) {
				skillPanel.setItem(getCharacter(person).getItem());
				acter = getCharacter(person);
			}
			break;
		case GUARD:
		case RUN:
		case NON:
		default :
		}	
		currentEvent = e;
	}
	
	
	public void setDxDy(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void setCharacter(int i, Character c) {
		//if(f[i] != null)
		f[i] = c;
		if(c != null)
			c.setLocation(i);
	}
	
	public void setTarget(int target) {
		
		if(person != -1) {
			Character temp = getCharacter(person);
			if(isSkillSet()) {
				// need skill 
				//actionList.add(new BattleAction(currentSkill))
				System.out.println("skill is set");
				actionList.add(new BattleAction(new Skill(currentSkill), temp, target));
			} else if(isItemSet()) {
				System.out.println("this item " + currentItem);
			} else {
				actionList.add(new BattleAction(temp.getAttack(), temp, target));
			}
			reset();
		}
	}
	
	public void setSkill(Skill.Skills sk) {
		currentSkill = sk;
	}
	
	public void setItem(Item.Items it) {
		currentItem = it;
	}
	
	public void getAction(List<BattleAction> list) {
		if(pc) {
			
		} else {
			
			for(int i = 0; i < actionList.size(); i++) {
				list.add(actionList.get(i));
			}
			actionList.clear();
		}
	}
	
	 
	public void applyAction(List<BattleAction> list) {
		for(BattleAction b : list) {
			applyAction(b);
		}
	}
	
	public void applyAction(BattleAction b) {	
				
				List<Animation> temp = new ArrayList<Animation>();
		
				if(b.isMove()) {
				//	b.getCharacter().getField().changeLocation(b.getLocation(), b.getMoveLocation());
					Character c = b.getCharacter();
					Point start = new Point(getAccurateLocationX(b.getLocation()), getAccurateLocationY(b.getLocation()));
					Point goal = new Point(getAccurateLocationX(b.getMoveLocation()), getAccurateLocationY(b.getMoveLocation()));
					Animation walk = new Animation2(new SeriesImageLoader(c.getWalkImage(), null, 0, 0, 0, 1), start, goal);
					temp.add(walk);
					c.setVisible(false);
					
					if(getCharacter(b.getMoveLocation()) != null) {
						Character c2 = getCharacter(b.getMoveLocation());
						Animation walk2 = new Animation2(new SeriesImageLoader(c2.getWalkImage(), null, 0, 0, 0, 1), goal, start);
						temp.add(walk2);
						c2.setVisible(false);
					}
					
					
					b.setAnimation(temp);
					animatingAction.add(b);
				} else {
					Character person = b.getCharacter();
					
					int firstTarget = b.getTarget().get(0);	
					/*
					for(Integer i : b.getTarget()) {
						Character c = getCharacter(i);
						if(c != null) {
							if(b.getStatus() != Skill.Status.NON) {
								c.addStatus(b.getStatus(), b.getDmg(), 2);
								//c.normalDamage(b.getDmg().getAttack()); 
							} else {
								
								if(b.isItem()) {
									System.out.println("this damage is " + b.getDmg());
									c.damaged(b.getDmg());
									
								} else {
								
									c.normalDamage(b.getDmg());
									System.out.println(b.getDmg().getHP() + " attack");
								}
							}
						}
					}*/
					
					
					if(b.isSkill()) {
						
						
						b.getSkill().setPoint(getAccurateLocationX(firstTarget), getAccurateLocationY(firstTarget));
						temp = b.getSkill().getAnimation();
						
						// newer 
						b.setAnimation(temp);
						
					//	animation.addAll(temp);
						animatingAction.add(b);
						
					} else {
						
						person.animate(getAccurateLocationX(firstTarget)  , getAccurateLocationY(firstTarget));
					
					}
					
				}
		
	}
	
	
	public boolean isPC() {
		return pc;
	}
	
	public int getIndex(Character c) {
		for(int i = 0; i < f.length; i++) {
			if(f[i] == c) {
				return i;
			}
		}
		
		return -1;
	}
	
	public int getAccurateLocationX(int c) {
		return x + (c % Battle.SIZE) * dx;
	}
	
	public int getAccurateLocationY(int c) {
		return y + c / Battle.SIZE * dy;
	}
	
	public Character getCharacter(int i) {
		return f[i];
	}
	
	public List<Character> getCharacters() {
		List<Character> temp = new ArrayList<Character>();
		for(int i = 0; i < f.length; i++) {
			if(f[i] != null) {
				temp.add(f[i]);
			}
			
		}
		return temp;
	}
	
	public void changeLocation(int first, int second) {
		Character temp = getCharacter(first);
		Character temp2 = getCharacter(second);
		setCharacter(first, getCharacter(second));
		setCharacter(second, temp);
		
		
		if(temp != null)
			temp.fixDxDy();
		if(temp2 != null)
			temp2.fixDxDy();
		
	}
	
	public void processAI(List<Character> list) {
		List<Character> temp = getCharacters();
		Random r = new Random();
		//int size = Math.abs(r.nextInt()) % temp.size();
		int size = r.nextInt(temp.size());
		int offset = Math.abs(r.nextInt());
		
		for(int i = 0; i < size; i++) {
			Character acter = temp.get((i + offset) % temp.size());
			int index = Math.abs(r.nextInt()) % list.size();
			int target = list.get(index).getLocation();
			
			
			if(r.nextBoolean()) {	
				actionList.add(new BattleAction(acter.getAttack(), acter, target));
			} else {
				List<Skill.Skills> tempList = acter.getSkill();
				Skill.Skills sk = tempList.get(r.nextInt(tempList.size()));
				actionList.add(new BattleAction(new Skill(sk), acter, target));
			}
		}
	}
	
	
	public Character[] getCharacter() {
		return f;
	}
	
	public boolean isTargetSet() {
		return person != -1;
	}
	
	public boolean isSkillSet() {
		return currentSkill != null;
	}
	
	public boolean isItemSet() {
		return currentItem != null;
	}
	
	public boolean isAnimating() {
		//animation.size() != 0;
		// need to check normal attack
		boolean animating = false;
		for(int i = 0; i < f.length; i++) {
			if(f[i] != null) {
				animating = animating || f[i].isAnimate();
			}
		}
		return animating || animation.size() != 0;
	}
	
	public void targetClear() {
		person = -1;
	}
	
	public int getTarget() {
		return person;
	}	
	
	
	public void paint(Graphics g, JPanel panel, int x, int y, int dx, int dy) {
		Graphics2D g2 = (Graphics2D) g;
		
		for(int i = 0; i < f.length; i++) {
			if(f[i] != null) {
				if(f[i].isVisible()) {
					f[i].animateUpdate();
					g.drawImage(f[i].getImage(), f[i].getDx() , f[i].getDy() , panel);
				}
			}
		}
		
		
		/*
		for(int i = 0; i < animation.size(); i++) {
			Animation anime = animation.get(i);
			Point p = anime.getPoint();
			g.drawImage(anime.getImage(), (int)p.getX(), (int)p.getY(), panel);
			if(anime.isDone()) {
				animation.remove(i);
				i--;
			}
			//System.out.println(i + "animation!!" + p.getX() + " " + p.getY() + " " + anime.getImage());
		}*/
		
		
		for(int i = 0; i < animatingAction.size(); i++) {
			BattleAction action = animatingAction.get(i);
			if(action.hasAnimation()) {
				List<Animation> list = action.getAnimation();
				for(int j = 0; j < list.size(); j++) {
					Animation anime = list.get(j);
					Point p = anime.getPoint();
					g.drawImage(anime.getImage(), (int)p.getX(), (int)p.getY(), panel);
					if(anime.isDone()) {
						list.remove(j);
						j--;
					}
				}
				if(list.size() == 0) {
				
					if(action.isMove()) {
						action.getCharacter().setVisible(true);
						Character c2 = action.getCharacter().getField().getCharacter(action.getMoveLocation());
						if(c2 != null) {
							c2.setVisible(true);
						}
						
						action.getCharacter().getField().changeLocation(action.getLocation(), action.getMoveLocation());
					} else {
						Character person = action.getCharacter();
						
						int firstTarget = action.getTarget().get(0);
						
						for(Integer in : action.getTarget()) {
							Character c = getCharacter(in);
							if(c != null) {
								if(action.getStatus() != Skill.Status.NON) {
									c.addStatus(action.getStatus(), action.getDmg(), 2);
								} else {
									
									if(action.isItem()) {
										System.out.println("this damage is " + action.getDmg());
										c.damaged(action.getDmg());
										
									} else {
										c.normalDamage(action.getDmg());
										System.out.println(action.getDmg().getHP() + " attack");
									}
								}
							}
						}
					}	
					animatingAction.remove(i);
					i--;
					
				}
			}
		}
	}
	
	public void reset() {
		person = -1;
		currentSkill = null;
		currentItem = null;
		acter = null;
	}
	
	private class FieldButton extends JButton implements ActionListener {
		private int index;
		
		public FieldButton(int index) {
			super();
			this.index = index;
			this.setOpaque(false);
			//this.setContentAreaFilled(false);
			//this.setBorderPainted(false);

			this.addActionListener(this);
		}
		
		public int getIndex() {
			return index;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println(index + " choosed");
			
			if(acter == null ) {
				person = index;
				if(skillPanel != null && getCharacter(person) != null) {
					Character temp = getCharacter(person);
					skillPanel.setCharacter(temp);
					Skill.Skills sk = skillPanel.getSkill();
					//System.out.println("here");
					if(sk != null) {
						//System.out.println(sk);
						currentSkill = sk;
					}
					
					skillPanel.clearSkill();
				}
			} else {
				
				if(isSkillSet()) {
					// need skill apply
					// most likely cure or buff
					BattleAction b = new BattleAction(new Skill(currentSkill), acter, index);
					b.setOwnField();
					actionList.add(b);
					
					skillPanel.clearSkill();
				} else if(isItemSet()) {
					BattleAction b = new BattleAction(new Item(currentItem), acter, index);
					b.setOwnField();
					actionList.add(b);
					
				}else {
					// move
					BattleAction b = new BattleAction(person, index, acter);
					b.setOwnField();
					actionList.add(b);
				}
					
				
				reset();
			}
			
		}
	}
	
}
