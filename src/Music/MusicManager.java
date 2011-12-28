package Music;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import util.Arguments;

public final class MusicManager {
	private static MusicManager mm;
	
	// fields
	public static final String audioDir = "./audio";
	private Map<MusicType, String> audioList;
	private boolean musicOnOff;
	private Thread background;
	
	private MusicManager() {	
		audioList = createList();
		this.musicOnOff = true;
		background = null;
	}
	
	private static HashMap<MusicType, String> createList() {
		
		HashMap<MusicType, String> list = new HashMap<MusicType, String>();
		for (MusicType type : MusicType.values()) {
			
		}
		return list;
	}
	
	public static MusicManager getInstance() {
		if (mm == null) {
			mm = new MusicManager();
		}
		return mm;
	}
	
	public void playBackgroundMusic(MusicType mt) {
		Arguments.isNotNull(mt);
		player(null, mt, true);
	}
	
	public void playSound(LinkedList<MusicType> mts) {
		Arguments.isNotNull(mts);
		player(mts, null, false);
	}
	
	private void player(LinkedList<MusicType> mts, MusicType mt, boolean repeat) {
		if (mts == null) {
			
		}else {
			
		}
	}
	
	public void setMusicOnOff(boolean onOff) {
		this.musicOnOff = onOff; 
	}
}
