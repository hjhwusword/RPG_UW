package MainMenuView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public final class MainView extends JPanel {
	private static MainView mainview;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CharacterStatView charstat_view;
	private SkillView skill_view;
	private InventoryView inven_view;
	private EquipView equip_view;
	private QuestView quest_view;
	private SystemView sys_view;
	private JPanel menu;
	
	private MainView() {
		this.setLayout(new BorderLayout());
		
		this.charstat_view = CharacterStatView.getInstance();
		
		
		initMenuBar();
		this.add(menu, BorderLayout.NORTH);
		this.add(this.charstat_view, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	private void initMenuBar() {
		menu = new JPanel();
		menu.setLayout(new FlowLayout());
		JButton stat = new JButton("Stat");
		JButton skill = new JButton("Skill");
		JButton inven = new JButton("Inven");
		JButton equip = new JButton("Equip");
		JButton quest = new JButton("Quest");
		JButton system = new JButton("System");
		Dimension d = system.getSize();
		stat.setSize(d);
		skill.setSize(d);
		inven.setSize(d);
		equip.setSize(d);
		quest.setSize(d);
		
		menu.add(stat);
		menu.add(skill);
		menu.add(inven);
		menu.add(equip);
		menu.add(quest);
		menu.add(system);
	}
	
	public static MainView getInstance() {
		if (mainview == null)
			mainview = new MainView();
		return mainview;
	}
}
