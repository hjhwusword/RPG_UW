package Music;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;

import util.Arguments;

public final class MusicManager extends Observable {
	public static final String AUDIO_DIR = "./audio/";
	private static MusicManager mm;
	
	// fields
	private Map<MusicType, String> audioList;
	
	private boolean musicOnOff;
	private Thread background;
	
	private MusicManager() {	
		audioList = createList();
		this.musicOnOff = true;
		background = null;
	}
	
	private static HashMap<MusicType, String> createList() {
		//TODO finish up creating an audio list
		HashMap<MusicType, String> list = new HashMap<MusicType, String>();
/*		for (MusicType type : MusicType.values()) {
			
		}*/
		list.put(MusicOgg.OGGTEST, "030-Door07.ogg");
		list.put(MusicOgg.OGGTEST2, "082-Monster04.ogg");
		list.put(MusicMidi.MIDITEST, "001-Battle01.mid");
		list.put(MusicMidi.MIDITEST2, "063-Slow06.mid");
		return list;
	}
	
	public static MusicManager getInstance() {
		if (mm == null) {
			mm = new MusicManager();
		}
		return mm;
	}
	
	public void playBackgroundMusic(MusicType mt) {
		assert(Arguments.isNotNull(mt));
		if (this.background != null) {
			try {
				setChanged();
				notifyObservers(Signals.MUSIC_CHANGE);
				background.join();
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		MusicPlayer bgm = setupMusicPlayer(mt, true);
		this.addObserver(bgm);
		background = new Thread(bgm);
		background.start();
	}
	
	public void playSound(LinkedList<MusicType> mts) {
		assert(Arguments.isNotNull(mts));
		if (this.musicOnOff) {
			for (MusicType type : mts) {
				new Thread(setupMusicPlayer(type, false)).start();
			}
		}
	}
	
	private MusicPlayer setupMusicPlayer(MusicType mt, boolean loop) {
		if (mt.getClass() == MusicOgg.class)
			return new OggPlayer(AUDIO_DIR + audioList.get(mt), loop);
		else if (mt.getClass() == MusicMidi.class)
			return new MidiPlayer(AUDIO_DIR + audioList.get(mt), loop);
		// safety check
		assert(true);
		return null;
	}
	
	public void setMusicOnOff(boolean onOff) {
		this.musicOnOff = onOff;
		setChanged();
		if (this.musicOnOff)
			notifyObservers(Signals.MUSIC_ON);
		else
			notifyObservers(Signals.MUSIC_OFF);
	}
	
	public boolean getMusicOnOff() {
		return this.musicOnOff;
	}
}
