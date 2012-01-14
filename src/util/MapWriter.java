package util;

import java.io.IOException;
import java.io.OutputStream;

public final class MapWriter {
	private OutputStream output;
	private int map_size;
	private int current_map_size;
	
	public MapWriter(OutputStream output, int map_size) {
		if (!Arguments.isNotNull(output))
			throw new IllegalArgumentException("output is null");
		if (!Arguments.isInRange(this.map_size, 1, Short.MAX_VALUE))
			throw new IllegalArgumentException("Size should be less than " + Short.MAX_VALUE);
		this.output = output;
		this.map_size = map_size;
		this.current_map_size = 0;
		this.writeSize();
	}
	
	private void writeSize() {
		byte[] size = new byte[2];
		size[0] = (byte) (this.map_size >> Byte.SIZE);
		size[1] = (byte) this.map_size;
		try {
			this.output.write(size);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void write(MapBlock mb) {
		if (!Arguments.isNotNull(mb))
			throw new IllegalArgumentException("given MapBlock is null");
		if (this.current_map_size >= this.map_size) {
			System.err.println("The written size already reached the limit.");
			return;
		}
		try {
			this.output.write(mb.getData());
			this.current_map_size++;
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
