package UI;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import util.MusicPlayer;

import map.RPGMap;

public class BattleWindow extends JFrame implements Observer {
	public static int WIDTH = 500;
	public static int HEIGHT = 500;
	
	
	private BattlePanel battlePanel;
	private JPanel bottomPanel;
	private BattleSkillPanel skillPanel;
	
	private MapPanel mapPanel;
	
	private MusicPlayer player;
	
	public BattleWindow() {
		super();
		init();
	}
	
	private void init() {
		
		
		//this.setLayout(new GridLayout(2, 1));
		
		battlePanel = new BattlePanel(WIDTH, HEIGHT / 2);
		bottomPanel = new JPanel();
		
		// adding battle command
		battlePanel.connectPanel(bottomPanel);
	
		// adding skill
		skillPanel = new BattleSkillPanel(bottomPanel);
		battlePanel.connectSkillPanel(skillPanel);
		battlePanel.addObserver(this);
		//this.add(battlePanel);
		//this.add(bottomPanel);
	
		// walk map
		mapPanel = new MapPanel();
		mapPanel.addObserver(this);
		
		this.getContentPane().add(mapPanel);
		
		// default;
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(WIDTH, HEIGHT));
		this.setTitle("rpg");
		this.setLocationRelativeTo(null);
		
		// music
		player = new MusicPlayer("audio/sceneVX_2.mid");
		player.start();
	}
	
	public static void main(String arg[]) {
		BattleWindow x = new BattleWindow(); 
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		RPGMap.MapEvent event = RPGMap.MapEvent.NON;
		
		if(arg1 instanceof RPGMap.MapEvent) {
			event = (RPGMap.MapEvent) arg1;
		}
		
		System.out.println("this is Event");
		
		
		switch(event) {
		case BATTLE:
			
			this.getContentPane().removeAll();
			this.setLayout(new GridLayout(2, 1));
			this.getContentPane().add(battlePanel);
			this.getContentPane().add(bottomPanel);
			this.validate();
			this.repaint();
			player.change("audio/battleXP_2.mid");
			
			
			break;
		case MAP:
			this.getContentPane().removeAll();
			this.setLayout(null);
			//mapPanel.addObserver(this);
			this.getContentPane().add(mapPanel);
			mapPanel.requestFocus();
			
			this.validate();
			this.repaint();
			
			player.change("audio/sceneVX_2.mid");
			break;
				
		case NON:
			System.out.println("no map event?");
			break;
		default:
			System.out.println("what is this " + event);
		}
		
	}
	
}
