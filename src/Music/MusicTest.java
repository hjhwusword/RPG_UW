package Music;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedList;

import javax.swing.*;

public class MusicTest {
	private MusicManager mm;
	
	private JButton midi_play;
	private JButton midi_mute;
	private JButton midi_change;
	private JCheckBox midi_check;
	private boolean midi_bgm;
	private MusicType currentMidi;
	
	private JButton ogg_play;
	private JButton ogg_mute;
	private JButton ogg_change;
	private JCheckBox ogg_check;
	private boolean ogg_bgm;
	private MusicType currentOgg;
	
	public MusicTest() {
		createcomponents();
		this.midi_bgm = true;
		this.ogg_bgm = true;
		this.currentMidi = MusicMidi.MIDITEST;
		this.currentOgg = MusicOgg.OGGTEST;
		mm = MusicManager.getInstance();
	}

	public void createcomponents() {
		midi_play = new JButton("Play");
		midi_play.addActionListener(new btnListener());
		
		midi_mute = new JButton("Mute/Unmute");
		midi_mute.addActionListener(new btnListener());
		
		midi_change = new JButton("Change");
		midi_change.addActionListener(new btnListener());
		
		midi_check = new JCheckBox("BGM");
		midi_check.addItemListener(new chkListener());
		midi_check.setSelected(true);
		
		ogg_play = new JButton("Play");
		ogg_play.addActionListener(new btnListener());
		
		ogg_mute = new JButton("Mute/Unmute");
		ogg_mute.addActionListener(new btnListener());
		
		ogg_change = new JButton("Change");
		ogg_change.addActionListener(new btnListener());
		
		ogg_check = new JCheckBox("BGM");
		ogg_check.addItemListener(new chkListener());
		ogg_check.setSelected(true);
		
		JFrame frame = new JFrame("Test Music");
		frame.setSize(400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// FlowLayout
/*			
		JPanel north = new JPanel(new FlowLayout());
		frame.add(north, BorderLayout.NORTH);
		north.setBorder(BorderFactory.createTitledBorder("Midi Player"));
		north.add(midi_play);
		north.add(midi_mute);
		north.add(midi_change);
		
		JPanel center = new JPanel(new FlowLayout());
		frame.add(center, BorderLayout.CENTER);
		center.add(midi_filename);*/
		
		// GridBagLayout
		/*
		JPanel main = new JPanel(new GridBagLayout());
		frame.add(main);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		main.add(midi_mute, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		main.add(midi_play, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 0;
		main.add(midi_change, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		main.add(midi_filename, c);
		*/
		
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		frame.add(main);
		
		JPanel midi = new JPanel(new FlowLayout());
		midi.setBorder(BorderFactory.createTitledBorder("Midi Player"));		
		midi.add(midi_play);
		midi.add(midi_mute);
		midi.add(midi_change);
		midi.add(midi_check);
		main.add(midi);
		
		JPanel ogg = new JPanel(new FlowLayout());
		ogg.setBorder(BorderFactory.createTitledBorder("OGG Player"));	
		ogg.add(ogg_play);
		ogg.add(ogg_mute);
		ogg.add(ogg_change);
		ogg.add(ogg_check);
		main.add(ogg);
		
		frame.setVisible(true);
	}
	
	private class btnListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == midi_play) {
				System.out.println("Midi bgm: " + midi_bgm);
				if (midi_bgm)
					mm.playBackgroundMusic(currentMidi);
				else {
					System.out.println("Play midi sound");
					LinkedList<MusicType> ll = new LinkedList<MusicType>();
					ll.add(currentMidi);
					mm.playSound(ll);
				}
			}else if (e.getSource() == midi_mute) {
				boolean onOff = mm.getMusicOnOff();
				mm.setMusicOnOff(!onOff);
			}else if (e.getSource() == midi_change) {
				if (currentMidi == MusicMidi.MIDITEST)
					currentMidi = MusicMidi.MIDITEST2;
				else
					currentMidi = MusicMidi.MIDITEST;
				
				if (midi_bgm)
					mm.playBackgroundMusic(currentMidi);
				else {
					LinkedList<MusicType> ll = new LinkedList<MusicType>();
					ll.add(currentMidi);
					mm.playSound(ll);
				}
			}else if (e.getSource() == ogg_play) {
				if (ogg_bgm)
					mm.playBackgroundMusic(MusicOgg.OGGTEST);
				else {
					LinkedList<MusicType> ll = new LinkedList<MusicType>();
					ll.add(currentOgg);
					mm.playSound(ll);
				}
			}else if (e.getSource() == ogg_mute) {
				boolean onOff = mm.getMusicOnOff();
				mm.setMusicOnOff(!onOff);
			}else if (e.getSource() == ogg_change) {
				if (currentOgg == MusicOgg.OGGTEST)
					currentOgg = MusicOgg.OGGTEST2;
				else
					currentOgg = MusicOgg.OGGTEST;
				
				if (ogg_bgm)
					mm.playBackgroundMusic(currentOgg);
				else {
					System.out.println("Play sound");
					LinkedList<MusicType> ll = new LinkedList<MusicType>();
					ll.add(currentOgg);
					mm.playSound(ll);
				}
			}
		}
	}
	
	private class chkListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			Object o = e.getItemSelectable();
			System.out.println("Checkbox Changed");
			if (o == midi_check) {
				JCheckBox midi = (JCheckBox) o;
				midi_bgm = midi.isSelected();
				System.out.println(midi_bgm);
			}else if (o == ogg_check) {
				JCheckBox ogg = (JCheckBox) o;
				System.out.println("Ogg_check is changed to " + ogg.isSelected());
				ogg_bgm = ogg.isSelected();
			}
		}
		
	}
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */	
	public static void main(String[] args) throws InterruptedException {
		new MusicTest();
	}
}
