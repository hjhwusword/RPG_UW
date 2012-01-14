package mapMaker;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;

import javax.swing.*;

public class MouseMotion extends JPanel implements MouseListener,
							MouseMotionListener{


  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int mouseX = 0;
	private int mouseY = 0;
	private Image imgMouse;


	public MouseMotion(Image imgMouse) {
		this.imgMouse = imgMouse;
		addMouseMotionListener(this);
		addMouseListener(this);
	
	}
	
	public void mouseClicked( MouseEvent e ){}
	public void mousePressed( MouseEvent e ){    }
	public void mouseReleased( MouseEvent e ){}
	public void mouseEntered( MouseEvent e ){}
	public void mouseExited( MouseEvent e ){}
	public void mouseDragged( MouseEvent e ){}
	
	public void mouseMoved( MouseEvent e ){
		repaint();
		mouseX=e.getX();
	    mouseY=e.getY();
	    repaint();
	}
	
	
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		g.drawImage(imgMouse, mouseX,mouseY,
				imgMouse.getWidth(this),imgMouse.getHeight(this),this);
		}
	}
