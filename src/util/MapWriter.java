package util;

import java.io.IOException;
import java.io.OutputStream;

/**
 * writes blocks into a output stream
 * @author Hans
 *
 */
public final class MapWriter {
	private OutputStream output;
	private int map_size;
	private int current_written_size;
	
	/**
	 * constructs a writer with given output stream and a map size
	 * @param output an output stream to write
	 * @param map_size the size of the map
	 */
	public MapWriter(OutputStream output, int map_size) {
		if (!Arguments.isNotNull(output))
			throw new IllegalArgumentException("output is null");
		if (!Arguments.isInRange(map_size, 1, Integer.MAX_VALUE))
			throw new IllegalArgumentException("Size should be less than " + Integer.MAX_VALUE);
		this.output = output;
		this.map_size = map_size;
		this.current_written_size = 0;
		this.writeSize();
	}
	
	// write the size into the output
	private void writeSize() {
		int map_size = this.map_size;
		int bytes = Integer.SIZE / Byte.SIZE;
		byte[] size = new byte[bytes];
		for (int i = 0; i < bytes; i++) {
			size[i] = (byte) map_size;
			map_size >>= Byte.SIZE;
		}
		try {
			this.output.write(size);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * writes the given MapBlock to output
	 * @param mb the MapBlock to write
	 */
	public void write(MapBlock mb) {
		if (!Arguments.isNotNull(mb))
			throw new IllegalArgumentException("given MapBlock is null");
		if (this.current_written_size >= this.map_size) {
			System.err.println("The written size already reached the limit.");
			return;
		}
		try {
			this.output.write(mb.getData());
			this.current_written_size++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * gets the max size of the map
	 * @return the max size of the map
	 */
	public int getMaxMapSize() {
		return this.map_size;
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
