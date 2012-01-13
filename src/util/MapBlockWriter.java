package util;

import java.io.IOException;
import java.io.OutputStream;

public final class MapBlockWriter {
	private OutputStream output;
	
	public MapBlockWriter(OutputStream output) {
		this.output = output;
	}
	
	public void write(MapBlock mb) {
		try {
			output.write(mb.getData());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void finalize() {
		try {
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
