package MainMenuView;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

public final class CharacterStatView extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static CharacterStatView charstat;

	private CharacterStatView() {
		this.setLayout(new BorderLayout());
		createComponent();
	}
	
	private void createComponent() {
		DefaultListModel charList = new DefaultListModel();
		// TODO Add character list
		charList.addElement("test");
		charList.addElement("test2");
		this.add(new JList(charList), BorderLayout.WEST);
		this.setVisible(true);
	}
	
	public static CharacterStatView getInstance() {
		if (charstat == null)
			charstat = new CharacterStatView();
		return charstat;
	}
}
