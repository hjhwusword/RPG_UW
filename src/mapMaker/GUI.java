package mapMaker;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.tree.DefaultMutableTreeNode;

import simulator.MyCustomFilter;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class GUI {
	
	private final int xTile = 48;
	private final int yTile = 48;
	private final Dimension TILE = new Dimension(xTile, yTile);
	private final Dimension TOOL = new Dimension(30, 30);
	private final String imagesPath = "images/";
	
	private String[] groundList;// = {"Nature", "Water", "Path"};
	private String[] terrainList;// = {"Nature", "Earth", "Building"};
	
	private int row = 20;
	private int col = 20;
		
	private JFrame frame;
	private JPanel panelTools;	// Tools NORTH
	private JPanel panelEast;	// panel EAST
	private JPanel panelItems;	// Items / Hierarchy 
	private JPanel panelMap;	// Map CENTER
	private JSplitPane splitX;
	private JSplitPane splitY;
	private JPanel panelPalette;	// Palette SOUTH
	private ImagePanel curGround;
	private ImagePanel curTerrain;
	
	
	/* Buttons for Tools */
	private JButton buttonNew;		// New Map
	private JButton buttonOpen;		// Open Map
	private JButton buttonSave;		// Save Map
	private JButton buttonSelect;	// Select Tile
	private JButton buttonMove;		// Move Tile
	private JButton buttonGroup;	// Group Tiles
	private JButton buttonErasor;	// Erase Tile
	private JButton buttonFill;		// Fill tiles
	
	private JButton buttonLayer0;	// Layer 0
	private JButton buttonLayer1;	// Layer 1
	private JButton buttonLayer2;	// Layer 2
	
	/* Components for EAST panel */
	private JComboBox dropCat;
	private JScrollPane scrollItems;
	private JScrollPane scrollArchy;
	private JTree treeArchy;
	private JButton buttonItem;
	
	/* Components for map panel*/
	private JScrollPane scrollMap;
	private JButton buttonMap;
	
	/* Menu */
	private JMenuBar menuBar;
	private JMenu menu, submenu;
	private JMenuItem menuItem;
	
	private JFileChooser chooserMap;
	
	//...//
	private Icon imgLeft;
	private Icon imgRight;
	
	//private Icon imgSelected;
	
	private boolean mouse = false; 
	
	private int curLayer;
	
	/*
	private ImageIcon groundImg[][];
	private ImageIcon terrainImg[][];
	*/
	
	
	
	
	
	// Constructs the GUI and displays it on the screen.
	public GUI() {
		setupComponents();
		handleEvents();
		doLayout();
	}
	
	public void setupComponents() {
		frame = new JFrame("Map Maker");
		frame.setLocation(300, 50);
		frame.setSize(850, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panelTools = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelTools.setPreferredSize(new Dimension(800, 40));

		panelEast = new JPanel(new BorderLayout());
		//panelEast.setPreferredSize(new Dimension(300, 550));
		
		panelItems = new JPanel(new GridLayout(0, 5));
		//panelItems.setPreferredSize(new Dimension(300, 350));	
		//buttonItem = new JButton(new ImageIcon("images/water.png"));
		//buttonItem.setPreferredSize(TILE);
		
		scrollItems = new JScrollPane(panelItems);
		scrollItems.getHorizontalScrollBar().setUnitIncrement(xTile/2);
		scrollItems.getVerticalScrollBar().setUnitIncrement(yTile/2);
		//scrollItems.setPreferredSize(new Dimension(300, 350));
		
		panelMap = new JPanel(new GridLayout(row, col, 0, -5));
		panelMap.setPreferredSize(new Dimension(row*yTile, col*xTile));
		/*
		buttonMap = new JButton(new ImageIcon("images/plain.png"));
		buttonMap.setPreferredSize(new Dimension(48, 48));
		*/
		
		JPanel temp = new JPanel();
		temp.add(panelMap);
		
		scrollMap = new JScrollPane(temp);
		//scrollMap.setPreferredSize(new Dimension(500, 550));
		//scrollMap.setMaximumSize(new Dimension(row*48, col*48));
		scrollMap.getHorizontalScrollBar().setUnitIncrement(xTile/2);
		scrollMap.getVerticalScrollBar().setUnitIncrement(yTile/2);
		
		//ImageIcon new = createImageIcon("images/right.gif");
				
		buttonNew = new JButton(new ImageIcon("images/layer_Terrain/Nature/tree.png"));
		buttonNew.setPreferredSize(TOOL);
		buttonOpen = new JButton(new ImageIcon("images/layer_Ground/Water/water.png"));
		buttonOpen.setPreferredSize(TOOL);
		buttonSave = new JButton(new ImageIcon("images/layer_Ground/Nature/plain.png"));
		buttonSave.setPreferredSize(TOOL);
		buttonSelect = new JButton(new ImageIcon("images/layer_Terrain/Nature/tree.png"));
		buttonSelect.setPreferredSize(TOOL);
		buttonMove = new JButton(new ImageIcon("images/layer_Ground/Nature/plain.png"));
		buttonMove.setPreferredSize(TOOL);
		buttonGroup = new JButton(new ImageIcon("images/layer_Terrain/Earth/mountain.png"));
		buttonGroup.setPreferredSize(TOOL);
		buttonErasor = new JButton(new ImageIcon("images/layer_Terrain/Nature/tree.png"));
		buttonErasor.setPreferredSize(TOOL);
		buttonFill = new JButton(new ImageIcon("images/layer_Ground/Water/water.png"));
		buttonFill.setPreferredSize(TOOL);
		
		LayerListener layerListener = new LayerListener();
		buttonLayer0 = new JButton(new ImageIcon("images/layer_Ground/Nature/tree.png"));
		buttonLayer0.setPreferredSize(TOOL);
		buttonLayer0.setName("0");
		buttonLayer0.addActionListener(layerListener);
		buttonLayer1 = new JButton(new ImageIcon("images/layer_Terrain/Nature/plain.png"));
		buttonLayer1.setPreferredSize(TOOL);
		buttonLayer1.setName("1");
		buttonLayer1.addActionListener(layerListener);
		buttonLayer2 = new JButton(new ImageIcon("images/layer_Ground/Earth/mountain.png"));
		buttonLayer2.setPreferredSize(TOOL);
		buttonLayer2.setName("2");
		buttonLayer2.addActionListener(layerListener);
		
		File dir = new File("images/layer_Ground");
		groundList = dir.list();
		dir = new File("images/layer_Terrain");
		terrainList = dir.list();
		
		dropCat = new JComboBox();
		for (String s : groundList)
			dropCat.addItem(s);
		
		dropCat.setPreferredSize(new Dimension(300, 20));
		ComboListener comboListener = new ComboListener();
		dropCat.addActionListener(comboListener);
		
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("The Java Series");
	    //createNodes(top);
	    treeArchy = new JTree(top);
	    treeArchy.setPreferredSize(new Dimension(300, 150));
	    scrollArchy = new JScrollPane(treeArchy);
	    scrollArchy.setPreferredSize(new Dimension(300, 150));
	    
	    splitX = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelEast, scrollArchy);
	    splitX.setDividerLocation(450);
	    splitX.setPreferredSize(new Dimension(300, 550));
	    
		splitY = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollMap, splitX);
		splitY.setDividerLocation(500);		
		splitY.setPreferredSize(new Dimension(850, 550));
		splitY.setResizeWeight(0.8);
		
		splitY.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent pce) {
				if (scrollItems.getWidth() != 0) {
					//System.out.println((int)(Math.ceil((800-splitY.getDividerLocation())/(double)(xTile+15))));
					panelItems.setLayout(new GridLayout(0, 
							(int)(Math.ceil((frame.getWidth()-splitY.getDividerLocation())/(double)((xTile*2)+15))), 0, 0));
					
				}
				else
					panelItems.setLayout(new GridLayout(0, 5, 0, 0));
			}
		});
		
		panelPalette = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelPalette.setPreferredSize(new Dimension(800, 60));
		curGround = new ImagePanel();
		curGround.setPreferredSize(TILE);
		//curGround.setVisible(true);
		curTerrain = new ImagePanel();
		curTerrain.setPreferredSize(TILE);
		//curTerrain.setVisible(true);
		
		
		
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
		chooserMap = new JFileChooser();
		chooserMap.setDialogTitle("Choose Map");
		chooserMap.setFileFilter(new simulator.MyCustomFilter());
		
		
		/* ... */
		imgLeft = null;
		imgRight = null;
		//imgSelected = null;
		curLayer = 0;
		
		/*
		groundImg = new ImageIcon[row][col];
		terrainImg = new ImageIcon[row][col];
		*/
		
	}
	
	public void handleEvents() {
		
	}
	
	public void doLayout() {
		frame.setJMenuBar(menuBar);
		panelTools.add(buttonNew);
		panelTools.add(buttonOpen);
		panelTools.add(buttonSave);
		panelTools.add(buttonSelect);
		panelTools.add(buttonMove);
		panelTools.add(buttonGroup);
		panelTools.add(buttonErasor);
		panelTools.add(buttonFill);

		panelTools.add(new JButton());
		
		panelTools.add(buttonLayer0);
		panelTools.add(buttonLayer1);
		panelTools.add(buttonLayer2);
		
		updateImages("images/layer_Ground/Nature", "item");
		
		for(int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				buttonMap = new JButton();
				buttonMap.setPreferredSize(TILE);
				MouseListener listener = new MouseListener();
				//listener.setXY(j, i);
				buttonMap.addMouseListener(listener);
				buttonMap.setName("map");
				
				JPanel maxPanel = new JPanel();
				maxPanel.add(buttonMap);
				panelMap.add(maxPanel);
			}
		}
		
		panelEast.add(dropCat, BorderLayout.NORTH);
		panelEast.add(scrollItems, BorderLayout.CENTER);
		
		panelPalette.add(curGround);
		panelPalette.add(curTerrain);
		panelPalette.add(new JButton());

		frame.add(panelTools, BorderLayout.NORTH);
		frame.add(splitY, BorderLayout.CENTER);
		frame.add(panelPalette, BorderLayout.SOUTH);
		frame.pack();
		
	}
	
	/**
     * Method to overlay Images
     *
     * @param bgImage --> The background Image
     * @param fgImage --> The foreground Image
     * @return --> overlayed image (fgImage over bgImage)
     */
    public BufferedImage overlayImages(BufferedImage bgImage,
            BufferedImage fgImage) {
 
        /**
         * Doing some preliminary validations.
         * Foreground image height cannot be greater than background image height.
         * Foreground image width cannot be greater than background image width.
         *
         * returning a null value if such condition exists.
         */
        if (fgImage.getHeight() > bgImage.getHeight()
                || fgImage.getWidth() > fgImage.getWidth()) {
            JOptionPane.showMessageDialog(null,
                    "Foreground Image Is Bigger In One or Both Dimensions"
                            + "\nCannot proceed with overlay."
                            + "\n\n Please use smaller Image for foreground");
            return null;
        }
 
        /**Create a Graphics  from the background image**/
        BufferedImage image = new BufferedImage(bgImage.getWidth(), bgImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        /**Set Antialias Rendering**/
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        /**
         * Draw background image at location (0,0)
         * You can change the (x,y) value as required
         */
       	g.drawImage(bgImage, 0, 0, null);
 
        /**
         * Draw foreground image at location (0,0)
         * Change (x,y) value as required.
         */
        g.drawImage(fgImage, 0, 0, null);
        g.dispose();
        return image;
    }
    
    private void iconToImage(BufferedImage img, Icon imgSelected) {
    	Icon icon = new SafeIcon(imgSelected);
	    img = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
	    icon.paintIcon(new JPanel(), img.getGraphics(), 0, 0);
    }
	
	// This listener handles mouse events in the canvas.
	private class MouseListener extends MouseInputAdapter {
		//private int x;
		//private int y;
		
		public BufferedImage ground;
		public BufferedImage terrain;
		
		public MouseListener() {
			super();
			ground = null;
			terrain = null;
		}
		/*
		public MouseListener(int x, int y) {
			super();
			setXY(x,y);
			ground = null;
			terrain = null;
		}
		
		public void setXY(int x, int y) {
			this.x = x;
			this.y = y;
		}
		*/
		
		/**
		 * Handles mouse clicks by drawing a new line to the point clicked.
		 */
		public void mousePressed(MouseEvent e) {
			JButton o = (JButton)e.getSource();
			if (o.getName() == "item") {
				//imgSelected = o.getIcon();

				
				if (e.getModifiers()==16) { 	// left click
					imgLeft = o.getIcon();
					curGround.setImage(imgLeft);
				}
				else if (e.getModifiers()==4) { // right click
					imgRight = o.getIcon();
					curTerrain.setImage(imgRight);
				}
				
					
			} 
			else if (o.getName() == "map") {		
				mouse = true;	
				mouseEntered(e);
				o.setSelected(false);	
			}
		}

		/**
		 * Handles mouse clicks by showing a red hovering line from the
		 * previous click point to the most recent point clicked.
		 */
		public void mouseEntered(MouseEvent e) {
			JButton o = (JButton)e.getSource();
			if (mouse) { 
				if (o.getName() == "map") {
					if (e.getModifiers()==16) { 	// left click
						if (imgLeft != null) {
							if (curLayer == 0) {
								Icon icon = new SafeIcon(imgLeft);
							    ground = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
							    icon.paintIcon(new JPanel(), ground.getGraphics(), 0, 0);
							    
							    if (terrain == null)
							    	o.setIcon(imgLeft);
							    else
							    	o.setIcon(new ImageIcon(overlayImages(ground, terrain)));
							}
							
							else if (curLayer == 1) {
								Icon icon = new SafeIcon(imgLeft);
							    terrain = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
							    icon.paintIcon(new JPanel(), terrain.getGraphics(), 0, 0);
							    
							    if (ground == null) 
							    	o.setIcon(imgLeft);
							    else {
							    	o.setIcon(new ImageIcon(overlayImages(ground, terrain)));
							    }	
							}
						}
					}
					else if (e.getModifiers()==4) { 	// right click
						if (imgRight != null) {
							if (curLayer == 0) {
								Icon icon = new SafeIcon(imgRight);
							    ground = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
							    icon.paintIcon(new JPanel(), ground.getGraphics(), 0, 0);
							    
							    if (terrain == null)
							    	o.setIcon(imgRight);
							    else
							    	o.setIcon(new ImageIcon(overlayImages(ground, terrain)));
							}
							
							else if (curLayer == 1) {
								Icon icon = new SafeIcon(imgRight);
							    terrain = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
							    icon.paintIcon(new JPanel(), terrain.getGraphics(), 0, 0);
							    
							    if (ground == null) 
							    	o.setIcon(imgRight);
							    else {
							    	o.setIcon(new ImageIcon(overlayImages(ground, terrain)));
							    }	
							}
						}
					}
					
					
				}
			}
		}
		
		public void mouseReleased(MouseEvent e) {
			//JButton o = (JButton)e.getSource();
			if (mouse) { 
				mouse = false;
			}
		}
	}
	
	public class ComboListener implements ActionListener {
	    public void actionPerformed(ActionEvent e) {
	        JComboBox cb = (JComboBox)e.getSource();
	        String category = (String)cb.getSelectedItem();
	        updateItems(category);
	    }
	}
	
	public class LayerListener implements ActionListener {
	    public void actionPerformed(ActionEvent e) {
	        JButton o = (JButton)e.getSource();
	        String layer = o.getName();
	        
	        curLayer = Integer.parseInt(layer);
	        
	        DefaultComboBoxModel comboModel = (DefaultComboBoxModel) dropCat.getModel();
	        comboModel.removeAllElements();
	        if (curLayer == 0) {
	        	for (String s : groundList)
	        		if (!s.equals("Thumbs.db"))
	        			comboModel.addElement(s);
	        }
	        else if (curLayer == 1) {
	        	for (String s : terrainList)
	        		if (!s.equals("Thumbs.db"))
	        			comboModel.addElement(s);
	        }
	        else {
	        	for (String s : terrainList)
	        		if (!s.equals("Thumbs.db"))	
	        			comboModel.addElement(s);	
	        }
	        	
	        updateItems("Nature");
	        dropCat.setSelectedIndex(0);	
	    }
	}
	
	private void updateItems(String cat) {
		String path = "images/";
		if (curLayer == 0) {
			path += "layer_Ground/";
			/*
			if (cat == "Nature"){
				File dir = new File(path+cat);
				String[] children = dir.list();
				if (children == null) {
				    // Either dir does not exist or is not a directory
				} else {
					updateImages(path+cat, "item");
				}
			}
			else if (cat == "Water") {
				
			}
			*/
			updateImages(path+cat, "item");
		}
		else if (curLayer == 1) {
			/*
			if (cat == "Tree"){
				
			}
			else if (cat == "Earth") {
				
			}
			*/
			path += "layer_Terrain/";
			updateImages(path+cat, "item");
		}
	}
	
	private void updateImages(String path, String type) {	
		File dir = new File(path);
		String[] children = dir.list();
		MouseListener listener = new MouseListener();

		if (children != null) {
			panelItems.removeAll();
		    for (int i=0; i<children.length; i++) {
		        // Get filename of file or directory
		        String filename = children[i];
		        if (!filename.equals("Thumbs.db")) {
			        //System.out.println(path+"/"+filename);
			        buttonItem = new JButton(new ImageIcon(path+"/"+filename));
					buttonItem.setPreferredSize(TILE);
					buttonItem.addMouseListener(listener);
					buttonItem.setName(type);
					JPanel maxPanel = new JPanel();
					maxPanel.add(buttonItem);
					panelItems.add(maxPanel);
		    	}   
		    }
		    panelItems.updateUI();
		}
	    
	}
/*
	// This class handles "action events", aka mouse clicks, in buttons.
	public class ItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton o = (JButton)e.getSource();
			if (o.getName() == "item") {
				imgSelected = o.getIcon();
			} 
			else if (o.getName() == "map") {
				if (imgSelected != null)
					o.setIcon(imgSelected);
					
			}
				
		}
	}
	*/
	
	private void MenuOpenActionPerformed(ActionEvent evt) {
		chooserMap.setAcceptAllFileFilterUsed(false);
    	chooserMap.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = chooserMap.showOpenDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooserMap.getSelectedFile();
            try {
				Scanner fileScanner = new Scanner(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
			}
        } else {

        }
	}
	
	public void start() {
		frame.setVisible(true);
	}
}
