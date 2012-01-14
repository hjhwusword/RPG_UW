package simulator;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

public class Simulator implements KeyListener{
	
	private JFrame frame;
	private JMenuBar menuBar;
	private JMenu menu, submenu;
	private JMenuItem menuItem;
	
	private JFileChooser chooserMap;
	
	private JTextField textStatus;

	//private JScrollPane mapScroll;
	//private JScrollBar hbar;
	//private JScrollBar vbar;
	private MapView map;	
	
	public Simulator() {
		initComponents();
		setupComponents();
		handleEvents();
		doLayout();
		frame.setVisible(true);
	}
	
	public void initComponents() {
		frame = new JFrame("Simulator");
		map = new MapView();
		
		
		
		chooserMap = new JFileChooser();
		textStatus = new JTextField("Awaiting User Action");
	}
	
	private void setupComponents() {	
		//JFrame
		frame.setLocation(300, 100);
		frame.setSize(440, 489);
		// 4 px per border, 57 vertical
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Menu
		menuBar = new JMenuBar();
		
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription(
		        "File system calls");
		menuBar.add(menu);

		menuItem = new JMenuItem("Open",KeyEvent.VK_O);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
		        "Open file");
		menuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuOpenActionPerformed(evt);
            }
        });
		menu.add(menuItem);
		
		//a submenu
		submenu = new JMenu("A submenu");
		menuItem = new JMenuItem("An item in the submenu");
		menu.addSeparator();

		submenu.setMnemonic(KeyEvent.VK_S);

		menuItem = new JMenuItem("Another item");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_2, ActionEvent.ALT_MASK));
		submenu.add(menuItem);


		submenu.add(menuItem);
		menu.add(submenu);

		//Build second menu in the menu bar.
		menu = new JMenu("Another Menu");
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription(
		        "This menu does nothing");
		menuBar.add(menu);
		

		chooserMap.setDialogTitle("Choose Map");
		chooserMap.setFileFilter(new MyCustomFilter());
		
		
		frame.setJMenuBar(menuBar);
		
		//map.setSize(496, 541);
		//map.setLayout(new GridLayout(0, 26));
		//frame.setBackground(Color.GREEN);
		//map.setLocation(100, 100);
		
		
		//Map
		map.setPreferredSize(new Dimension(800, 800));
		//mapScroll = new JScrollPane(map);
		
		//hbar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 40, 0, 496);
	    //vbar = new JScrollBar(JScrollBar.VERTICAL, 0, 40, 0, 541);
	    //hbar.setUnitIncrement(1);
	    //hbar.setBlockIncrement(1);
	    
	    //vbar.setUnitIncrement(1);
	    //vbar.setBlockIncrement(1);
	    
	    //mapScroll.setHorizontalScrollBar(hbar);
	    //mapScroll.setVerticalScrollBar(vbar);
	    
		//mapScroll.setSize(300, 200);
		//mapScroll.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
		//mapScroll.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);
		//frame.getContentPane().add(mapScroll);
		
		frame.setContentPane(map);
		map.addKeyListener(this);
		map.setFocusable(true);
		frame.setVisible(true);		
	}
	
	private void handleEvents() {
		
	}
	
	private void doLayout() {
		
	}
	
	private void MenuOpenActionPerformed(ActionEvent evt) {
		chooserMap.setAcceptAllFileFilterUsed(false);
    	chooserMap.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = chooserMap.showOpenDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooserMap.getSelectedFile();
            try {
				Scanner fileScanner = new Scanner(file);
				textStatus.setText("Selected map is found and valid");
				map.setMap(fileScanner);				
				map.repaint();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				textStatus.setText("invalid map, please select another map");
			}
        } else {
        	textStatus.setText("File access cancelled by user");
        }
	}

	@Override
	public void keyTyped(KeyEvent e) {
		moveHero(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		moveHero(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//moveHero(e);
	}
	
	private void moveHero(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_LEFT) {
			map.moveHero(3);
		}
		else if(keyCode == KeyEvent.VK_RIGHT) {
			map.moveHero(4);
		}
		else if(keyCode == KeyEvent.VK_UP) {
			map.moveHero(1);
		}
		else if(keyCode == KeyEvent.VK_DOWN) {
			map.moveHero(2);
		}
		map.repaint();
		
	}
}
