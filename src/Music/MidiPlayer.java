package Music;

import java.io.File;
import java.io.IOException;
import java.util.Observable;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import util.Debug;

public final class MidiPlayer extends MusicPlayer implements MetaEventListener {
	public static final boolean DEBUG = true;
	
	public static final int END_OF_TRACK_MESSAGE = 47;
	
	private boolean paused;
	private Sequencer sequencer;
	
	public MidiPlayer(String fileName, boolean loop) {
		super(fileName, loop);
		this.paused = false;
		initSequencer();
	}
	
	private void initSequencer() {
		try {
			sequencer = MidiSystem.getSequencer();
			Sequence seq = MidiSystem.getSequence(new File(super.getFileName()));
			sequencer.setSequence(seq);
			sequencer.open();
			sequencer.addMetaEventListener(this);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		sequencer.setTickPosition(0);
		if (!this.isPaused())
			sequencer.start();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (this.isSequencerValid()) {
			Signals signal = (Signals) arg;
			switch (signal) {
			case MUSIC_ON:
				Debug.debugOutput("midi is on", DEBUG);
				if (this.isPaused()) {
					this.paused = false;
					this.run();
				}
				break;

			case MUSIC_OFF:
				Debug.debugOutput("midi is off", DEBUG);
				if (!this.isPaused()) {
					this.paused = true;
					this.stop();
				}
				break;

			case MUSIC_CHANGE:
				this.stop();
				sequencer.setTickPosition(sequencer.getSequence().getTickLength());
				o.deleteObserver(this);
				break;
			}
			
		}
	}

	@Override
	public void stop() {
		if (!this.paused) {
			this.paused = true;
		}
		sequencer.stop();
	}

	@Override
	public boolean isPaused() {
		return this.paused;
	}
	
	public void clean() {
		sequencer.stop();
		sequencer.close();
		sequencer.removeMetaEventListener(this);
	}

	@Override
	public void meta(MetaMessage meta) {
		if (meta.getType() == END_OF_TRACK_MESSAGE) {
			Debug.debugOutput("Midi finish playing: loop count " + sequencer.getLoopCount(), DEBUG);
			if (this.isSequencerValid() && super.isLoop()) {
				sequencer.setTickPosition(0);
				sequencer.start();
			}else {
				this.clean();
			}
		}
	}
	
	private boolean isSequencerValid() {
		return sequencer != null && sequencer.isOpen();
	}
}
