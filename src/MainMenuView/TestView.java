package MainMenuView;

import javax.swing.JFrame;

public class TestView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestView() {
		this.setTitle("Test View");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(480, 360);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		
		this.add(MainView.getInstance());
	}
	
	public static void main(String[] args) {	
		new TestView();
	}
}
