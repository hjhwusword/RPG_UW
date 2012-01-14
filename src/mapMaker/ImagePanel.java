package mapMaker;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImagePanel extends JPanel{

    private BufferedImage image;

    public ImagePanel() {
       super();
       image = null;
    }
    
    public void setImage(Icon icon) {
	    image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
	    icon.paintIcon(new JPanel(), image.getGraphics(), 0, 0);
    	
    	//this.image = image;
    	repaint();
    }

    @Override
    public void paintComponent(Graphics g) {	
    	g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters

    }

}
