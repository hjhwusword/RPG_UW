package Music;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;

import util.Arguments;

public final class MusicManager extends Observable {
	public static final String audioDir = "./audio";
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
		list.put(MusicType.WAVTEST, "test.wav");
		return list;
	}
	
	public static MusicManager getInstance() {
		if (mm == null) {
			mm = new MusicManager();
		}
		return mm;
	}
	
	public void playBackgroundMusic(MusicType mt) {
		assert(!Arguments.isNotNull(mt));
		if (this.background != null) {
			try {
				setChanged();
				notifyObservers(Signals.MUSIC_CHANGE);
				background.join();
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		MusicPlayer bgm = new MusicPlayer(audioList.get(mt), true);
		this.addObserver(bgm);
		background = new Thread(bgm);
	}
	
	public void playSound(LinkedList<MusicType> mts) {
		assert(!Arguments.isNotNull(mts));
		if (this.musicOnOff) {
			for (MusicType type : mts) {
				new Thread(new MusicPlayer(audioList.get(type), false)).start();
			}
		}
	}
	
	public void setMusicOnOff(boolean onOff) {
		this.musicOnOff = onOff;
		setChanged();
		if (this.musicOnOff)
			notifyObservers(Signals.MUSIC_ON);
		else
			notifyObservers(Signals.MUSIC_OFF);
	}
}
