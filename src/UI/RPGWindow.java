package UI;
import java.awt.*;
import javax.swing.*;

public class RPGWindow extends JFrame {
	
	
	
	
	public RPGWindow() {
		super();
		this.add(new RPGPanel());
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(500, 500));
		this.setTitle("rpg");
		this.setLocationRelativeTo(null);
	}


	public static void main(String arg[]) {
		RPGWindow x = new RPGWindow(); 
		
	}

}