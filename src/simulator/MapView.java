package simulator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MapView extends JPanel {
	private static final long serialVersionUID = 1L;
	private final int ROW_S = 4;
	private final int COL_S = 3;
	private final int HEIGHT = 32;
	private final int WIDTH = 24;
	private final int INCREMENT = 24;
	private final int BLOCK = 48;
	
	private char[][] groundMap = null;
	private char[][] terrainMap = null;
	
	private Image imgTree;
	private Image imgMountain;
	private Image imgPlain;
	private Image imgWater;
	
	private BufferedImage[] spriteHero = new BufferedImage[ROW_S*COL_S];
	private BufferedImage imgHero; 
	
	private int row;
	private int col;
	
	private int xMap;
	private int yMap;
	private int stance;
	private int sCount;
	private int xShift;
	private int yShift;
	
	private int xHero;
	private int yHero;
	
	private final char[] FORBID = {'T', 'W', 'M'};
	
	
	public MapView() {
		imgTree = new ImageIcon("images/tree.png").getImage();
		imgWater = new ImageIcon("images/water.png").getImage();
		imgMountain = new ImageIcon("images/mountain.png").getImage();
		imgPlain = new ImageIcon("images/plain.png").getImage();
		
		try {
			imgHero = ImageIO.read(new File("images/Warrior_Male.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < COL_S; i++)	
		    for (int j = 0; j < ROW_S; j++) 
		        spriteHero[(i * ROW_S) + j] = imgHero.getSubimage(i * WIDTH, j * HEIGHT, WIDTH, HEIGHT);
		
		xMap = 0;
		yMap = 0;
		stance = 7;
		sCount = 0;
		xShift = 0;
		yShift = 0;
		xHero = 0;
		yHero = 0;
		
	}
	
	public void setMap(Scanner file) {
		if(file.hasNext()) {
			row = file.nextInt();
			col = file.nextInt();
			groundMap = new char[row][col];
			terrainMap = new char[row][col];
			
			xHero = file.nextInt()*2;
			yHero = file.nextInt()*2;
			
			xShift = 0;
			yShift = 0;
			
			xMap = -1;
			yMap = -1;
			
			if (xHero/2 < 4) {
				xShift = (4-(xHero/2))*-48;
				xMap = 0;
			}
			else if (xHero/2 > col-5) {
				xShift = (5-(col-(xHero/2)))*48;
				xMap = (col-9)*48;
			}
			
			if (yHero/2 < 4) {
				yShift = (4-(yHero/2))*-48;
				yMap = 0;
			}
			else if (yHero/2 > row-5) {
				yShift = (5-(row-(yHero/2)))*48;
				yMap = (row-9)*48;
			}
			
			if (xMap == -1) 
				xMap = (xHero/2-4)*48;
			if (yMap == -1) 
				yMap = (yHero/2-4)*48;
			
			
			
			stance = 7;
			sCount = 0;
			
			for(int i = 0; i < row; i++) {
				String line = file.next();
				for (int j = 0; j < col; j++) {
					groundMap[i][j] = line.charAt(j);
				}
			}
			
			for(int i = 0; i < row; i++) {
				String line = file.next();
				for (int j = 0; j < col; j++) {
					terrainMap[i][j] = line.charAt(j);
				}
			}
		}
		
		for(int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				System.out.print(groundMap[i][j]);
			}
			System.out.println();
		}
	}
	
	public void moveHero(int num) {
		sCount += 1;
		if (sCount == 2)
			sCount = 0;
		 
		if (num == 1) {
			if ((yHero+1)/2 > 0 && checkTerrain((yHero-1)/2,(xHero)/2)) {
				if (yShift == 0) {
					if (yMap <= 0) 
						yShift-=INCREMENT;
					else
						yMap-=INCREMENT;
					yHero--;
				}
				else {
					if (yShift > -240 || yShift > 0) {
						yShift-=INCREMENT;
						yHero--;
					}
				}
			}
			stance = 0;
		}
		if (num == 2) {
			if ((yHero+1)/2 < row && checkTerrain((yHero+1)/2,(xHero)/2)) {
				if (yShift == 0) {
					if (yMap >= (row)*BLOCK-432) 
						yShift+=INCREMENT;
					else 
						yMap+=INCREMENT;
					yHero++;
				}
				else {
					if (yShift < 240-HEIGHT || yShift < 0) {
						yShift+=INCREMENT;
						yHero++;
					}
				}
			}
			stance = 2;
		}
		if (num == 3) {
			if ((xHero+1)/2 > 0 && checkTerrain((yHero)/2,(xHero-1)/2)) {
				if (xShift == 0) {
					if (xMap <= 0) 
						xShift-=INCREMENT;
					else
						xMap-=INCREMENT;
					xHero--;
				}
				else {
					if (xShift > -240 || xShift > 0) {
							xShift-=INCREMENT;
							xHero--;
					}
				}
			}
			stance = 3;
		}
		if (num == 4) {
			if ((xHero+1)/2 < col && checkTerrain((yHero)/2,(xHero+1)/2)) {
				if (xShift == 0) {
					if (xMap >= (col)*BLOCK-432)  
						xShift+=INCREMENT;
					else
						xMap+=INCREMENT;
					xHero++;
				}
				else {
					if (xShift < 240-WIDTH || xShift < 0) {
							xShift+=INCREMENT;
							xHero++;
					}
				}
			}
			stance = 1;
		}
	}
	
	public boolean checkTerrain(int y, int x) {
		for (char c : FORBID)
			if (groundMap[y][x] == c || terrainMap[y][x] == c)
				return false;
		
		return true;
	}
	
	public void paintComponent(Graphics g) {
		if (groundMap != null && terrainMap != null) {
			for(int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					if(groundMap[i][j] == 'P')
						g.drawImage(imgPlain, j*BLOCK-xMap, i*BLOCK-yMap, null);
					else if(groundMap[i][j] == 'W')
						g.drawImage(imgWater, j*BLOCK-xMap, i*BLOCK-yMap, null);

					if (terrainMap[i][j] == 'M')
						g.drawImage(imgMountain, j*BLOCK-xMap, i*BLOCK-yMap, null);
					else if(terrainMap[i][j] == 'T')
						g.drawImage(imgTree, j*BLOCK-xMap, i*BLOCK-yMap, null);
				}
			}
			System.out.println(xHero/2 + " " + yHero/2);
			g.drawImage(spriteHero[stance+(sCount*8)], 192+xShift, 184+yShift, null);
		}
	}	
}
