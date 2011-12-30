package Music;

import java.util.Observer;

import util.Arguments;

public abstract class MusicPlayer implements Runnable, Observer {
	private String fileName;
	private boolean loop;
	
	public MusicPlayer(String fileName, boolean loop) {
		assert(!Arguments.isNotEmptyOrWhitespace(fileName));
		this.fileName = fileName;
		this.loop = loop;
	}
	
	public String getFileName() {
		return this.fileName;
	}
	
	public boolean isLoop() {
		return this.loop;
	}
	
	public abstract void stop();
	
	public abstract boolean isPaused();
}
