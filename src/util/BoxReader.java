package util;

import java.io.IOException;
import java.io.InputStream;

public final class BoxReader {	
	// fields
	private InputStream input;
	
	/**
	 * 
	 * @param input
	 */
	public BoxReader(InputStream input) {
		this.input = input;
	}
	
	public void hasNextBox() {
		
	}
	
	public MapBlock getNextBox() {
		return null;
	}
	
	@Override
	public void finalize() {
			try {
				this.input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}