package mapMaker;

import javax.swing.Icon;
import javax.swing.JButton;

public class CellButton extends JButton {
	private int x;
    private int y;
    
    private Icon ground;
    private Icon terrain;
    private Icon event;

    public CellButton() {
        this(-1, -1, null);
    }
    
    public CellButton(int x, int y) {
        this(x, y, null);
    }

    public CellButton(Icon icon) {
        this(-1, -1, icon);
    }
    
    public CellButton(int x, int y, Icon icon) {
        super(icon);
        this.x = x;
        this.y = y;
    }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    
    public int getX() { return x; }
    public int getY() { return y; }
    
    public void setGround(Icon g) { ground = g; }
    public void setTerrain(Icon t) { terrain = t; }
    
    public Icon getGround() { return ground; }
    public Icon getTerrain() { return terrain; }

    @Override
    public boolean equals(Object obj) {
    if (obj == null || !this.getClass().equals(obj.getClass())) {
    	return false;
     	}
      	CellButton c = (CellButton)obj;
      	return this.x == c.x && this.y == c.y; 
    }
}
