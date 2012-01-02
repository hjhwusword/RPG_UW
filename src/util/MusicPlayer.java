package util;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class MusicPlayer implements MetaEventListener {
	
	private Sequence sequence1;
	private Sequence sequence2;
	
	private Sequencer player1;
	private Sequencer player2;
	
	private Sequencer currentPlayer;
	
	public MusicPlayer(String s) {
		this(s, s);
	}
	
	public MusicPlayer(String s1, String s2) {
		try {
			sequence1 = MidiSystem.getSequence(new File(s1));
			sequence2 = MidiSystem.getSequence(new File(s2));
			player1 = MidiSystem.getSequencer();
			player2 = MidiSystem.getSequencer();
			currentPlayer = player1;
			
			
			player1.setSequence(sequence1);
			player2.setSequence(sequence2);
		
			player1.addMetaEventListener(this);
			player2.addMetaEventListener(this);
			
			player1.open();
			player2.open();
			
			//player1.setLoopCount(player1.LOOP_CONTINUOUSLY);
			//player2.setLoopCount(player2.LOOP_CONTINUOUSLY);
			
			
		} catch (InvalidMidiDataException e) {
			System.out.println(s1 + " or " + s2 +  " not a midi file");
		} catch (IOException e) {
			System.out.println(s1 + " or " + s2 + " file can't be found");
		} catch (MidiUnavailableException e) {
			System.out.println("can't create player");
		}
		
	}
	
	public void start() {
		player1.start();
	}
	
	public void change(String s) {
		currentPlayer.stop();
		currentPlayer.setTickPosition(0);
		
		try {
			sequence1 = MidiSystem.getSequence(new File(s));
			sequence2 = MidiSystem.getSequence(new File(s));
			player1.setSequence(sequence1);
			player2.setSequence(sequence2);
			
		} catch (InvalidMidiDataException e) {
			System.out.println("change " + s + " is not a mid");
		} catch (IOException e) {
			System.out.println(s + " can't be found");
		}
		
		currentPlayer.start();
	}
	/*
	public static void main(String[] args) {
	    MusicPlayer mp  = new MusicPlayer("audio/battleXP_2.mid");
	    mp.start();
	}*/
	
	private Sequencer otherPlayer(Sequencer one) {
		if(one == player1) {
			return player2;
		} else {
			return player1;
		}
	}

	@Override
	public void meta(MetaMessage arg0) {
		if(arg0.getType() == 47) { // reach the end
			Sequencer other = otherPlayer(currentPlayer);
			other.start();
			
			currentPlayer.stop();
			currentPlayer.setTickPosition(0);
			
			currentPlayer = other;
		}
		
	}
}
