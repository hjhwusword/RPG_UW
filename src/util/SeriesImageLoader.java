package util;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import util.RPGWalkLoader.Direction;

public class SeriesImageLoader{
	private List<Image> data;
	private BufferedImage source;
	//private BufferedImage source;
	private int width;
	private int height;
	
	private int count;
	
	private int delay;
	
	public SeriesImageLoader(String img, int x, int y) {
		this(img, x, y, false);
	}
	
	public SeriesImageLoader(List<Image> data, BufferedImage source, int width, int height, int count, int delay) {
		this.data = data;
		this.source = source;
		this.width = width;
		this.height = height;
		this.count = count;
		this.delay = delay;
	}
	
	public SeriesImageLoader(String img, int x, int y, boolean reflect) {
		data = new ArrayList<Image>();
		delay = 1;
		//ImageIcon s = new ImageIcon(img);

		
		try {
			FileInputStream file = new FileInputStream(new File(img));
			source = ImageIO.read(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("file can't be find");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("file image can't be read");
		}
		
		
		
		//source = s.getImage();
		width = source.getWidth();
		height = source.getHeight();
		
		int bufWidth = width / x;
		int bufHeight = height / y;
		
		Image temp;
		for(int i = 0; i < y; i++) {
			for(int j = 0; j < x; j++) {
				//temp = createImage(new FilteredImageSource(source.getSource(), new CropImageFilter(j*width/x, i*height/y, (width / x) + 1, height/y)));
				BufferedImage buffer = new BufferedImage(bufWidth, bufHeight, source.getType()); 
				Graphics2D g = buffer.createGraphics();
				
				if(reflect) {
					g.translate(bufWidth, 0);
					g.scale(-1, 1);
				}
				g.drawImage(source, 0, 0, bufWidth, bufHeight, bufWidth * j, bufHeight * i, bufWidth * (j + 1), bufHeight * (i + 1), null);
				g.dispose();
				
				//gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);  
                //gr.dispose(); 
				data.add(buffer);
			}
		}
		
		count = 0;
	}
	
	public int animationSize() {
		return data.size();
	}
	
	public void add(SeriesImageLoader list) {
		for(int i = 0; i < list.data.size(); i++) {
			data.add(list.data.get(i));
		}
	}
		 
	public Image getSingleImage() {
		Image temp = data.get(count / delay);
		count = (count + 1) % (data.size() * delay);
		
		return temp;
	}
	
	public boolean isDone() {
		return count == 0;
	}
	
	
	public Image getSingleImage(int i) {
		return data.get(i);
	}
	
	public List<Image> getImage() {
		return data;
	}
	
	public int getSize() {
		return data.size();
	}
	
	
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	public void save(int[] list) {
		List<Image> temp = new ArrayList<Image>();
		int j = 0;
		
		for(int i = 0; i < list.length; i++) {
			if(list[i] < 0) {
				temp.add(null);
			} else {
				temp.add(data.get(list[i]));
			}
		}
		data = temp;
	}
	
	public void delay(int times) {
		List<Image> temp = new ArrayList<Image>();
		for(int i = 0; i < data.size(); i++) {
			for(int j = 0; j < times; j++) {
				temp.add(data.get(i));
			}
		}
		data = temp;
	}
	
	public void shift(int index, int count) {
		List<Image> temp = new ArrayList<Image>();
		
		for(int i = 0; i < data.size(); i++) {
			if(i == index) {
				for(int j = 0; j <count; j++) {
					temp.add(null);
				}
			}
			temp.add(data.get(i));
		}
		data = temp;
	}
	
	public SeriesImageLoader share() {
		return new SeriesImageLoader(data, source, width, height, count, delay);
	}
	
	public SeriesImageLoader shareLess() {
		List<Image> temp = new ArrayList<Image>();
		temp.addAll(data);
		return new SeriesImageLoader(temp, source, width, height, count, delay);
	}
	
	public void extend(int count) {
		for(int i = 0; i < count; i++) {
			data.add(data.get(data.size()- 1));
		}
	}
	
}
