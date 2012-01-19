package Map.MapBlock;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

import util.Arguments;

/**
 * reads the data of a map
 * @author chiehwu
 *
 */
public final class MapReader {
	// fields
	private InputStream input;
	private byte[] next;
	private boolean hasNext;
	private int map_size;
	private int current_read_size;
	
	/**
	 * constructs a reader with given input stream
	 * @param input the input stream needed to be read
	 * @throws IllegalArgumentException if input is null
	 */
	public MapReader(InputStream input) {
		if (!Arguments.isNotNull(input))
			throw new IllegalArgumentException();
		
		this.input = input;
		this.next = new byte[MapBlock.BLOCK_SIZE];
		
		this.readMapSize();
		this.readBlock();
	}
	
	// gets the map size from data
	private void readMapSize() {
		int bytes = Integer.SIZE / Byte.SIZE;
		byte[] map_size = new byte[bytes];
		try {
			this.input.read(map_size);
			for (int i = bytes - 1; i >= 0; i--) {
				this.map_size |= map_size[i] << (i * Byte.SIZE);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * checks if there is still block after
	 * @return true if there is block, false otherwise
	 */
	public boolean hasNextBlock() {
		return this.hasNext;
	}
	
	/**
	 * gets the next MapBlock
	 * @return the next MapBlock 
	 * @throw NoSuchElementException if there is no more block
	 */
	public MapBlock getNextBlock() {
		if (this.hasNextBlock()) {
			MapBlock block = new MapBlock(this.next);
			readBlock();
			return block;
		}else 
			throw new NoSuchElementException();
	}
	
	/**
	 * gets the total size the the map
	 * @return the size of the map
	 */
	public int getTotalMapSize() {
		return this.map_size;
	}
	
	// reads a block from the input stream
	private void readBlock() {
		try {
			this.input.read(next);
			this.current_read_size++;
			this.hasNext = this.current_read_size <= this.map_size;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
