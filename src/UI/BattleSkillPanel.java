package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import battle.Animation;
import battle.Character;
import battle.Item;
import battle.Skill;

import util.SeriesImageLoader;

public class BattleSkillPanel extends JPanel implements ActionListener {
	
	private JList skillList;
	private Image background;
	private Character c;
	private JScrollPane scroll;
	private DefaultListModel skillListModel;
	private Timer timer;
	private Skill.Skills sk;
	private Item.Items it;
	
	
	// testing
//	private SeriesImageLoader skillAnime;
	
	
	public BattleSkillPanel(JPanel p) {
		super();
		
		setDoubleBuffered(true);
		setFocusable(true);
	
		
		ImageIcon ii = new ImageIcon("background.jpg");
		background = ii.getImage();
		skillListModel = new DefaultListModel();
		
		skillList = new JList(skillListModel);
		scroll = new JScrollPane(skillList);
		skillList.addListSelectionListener(new SkillListListener());
		//scroll.setPreferredSize(new Dimension(100, 100));
		//skillList.add(new JScrollPane());
		//skillList
		
		
		c = null;
		
		p.add(this);
		p.add(scroll);
		
		sk = null;
		it = null;
		
		this.setBackground(Color.BLACK);
		
		
		
		
		timer = new Timer(50, this);
		timer.start();
		
	}
	
	public void setSkill(List<Skill.Skills> list) {
		skillListModel.clear();
		for(int i = 0; i < list.size(); i++) {
			skillListModel.addElement(list.get(i));
		}
	}
	
	public void setItem(List<Item.Items> list) {
		skillListModel.clear();
		for(int i = 0; i < list.size(); i++) {
			skillListModel.addElement(list.get(i));
		}
	}

	
	
	public void setCharacter(Character c) {
		this.c = c;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g.drawImage(background, 0, 0, null);
		
		if(c != null) {
			g.drawImage(c.getFace(), 0, 0, null);
			//g.setColor(Color.WHITE);
			//g.drawRect(0, c.getFace().getHeight(null) + 5, 50, 10);
			g.setColor(Color.GREEN);
			g.fillRect(0, c.getFace().getHeight(null) + 5, c.getHP(), 10);
			g.setColor(Color.CYAN);
			g.fillRect(0, c.getFace().getHeight(null) + 20, c.getMP(), 10);
		}
		
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
	
	public void clearSkill() {
		sk = null;
		skillListModel.clear();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		repaint();
		
	}
	
	public Skill.Skills getSkill() {
		return sk;
	}
	
	public Item.Items getItem() {
		return it;
	}
	
	private class SkillListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			// TODO Auto-generated method stub
			if(!e.getValueIsAdjusting()) {
				//String selection = skillList.getSelectedValue().toString();
				// something
				if(skillList.getSelectedValue() instanceof Skill.Skills) {
					sk = (Skill.Skills)skillList.getSelectedValue();
				} else if(skillList.getSelectedValue() instanceof Item.Items) {
					it = (Item.Items)skillList.getSelectedValue();
				}
				System.out.println(sk + " this");
				
			}
			
		}
		
	}

}
