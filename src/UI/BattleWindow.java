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

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BattleWindow extends JFrame {
	public static int WIDTH = 500;
	public static int HEIGHT = 500;
	
	
	private BattlePanel battlePanel;
	private JPanel bottomPanel;
	private BattleSkillPanel skillPanel;
	
	public BattleWindow() {
		super();
		
		this.setLayout(new GridLayout(2, 1));
		
		battlePanel = new BattlePanel(WIDTH, HEIGHT / 2);
		bottomPanel = new JPanel();
		
		// adding battle command
		battlePanel.connectPanel(bottomPanel);
	
		// adding skill
		skillPanel = new BattleSkillPanel(bottomPanel);
		battlePanel.connectSkillPanel(skillPanel);
		
		
		this.add(battlePanel);
		this.add(bottomPanel);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(WIDTH, HEIGHT));
		this.setTitle("rpg");
		this.setLocationRelativeTo(null);
		
	}
	
	private void init() {
		
	}
	
	public static void main(String arg[]) {
		BattleWindow x = new BattleWindow(); 
		
	}
	
}
