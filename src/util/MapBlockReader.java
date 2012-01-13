package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

public final class MapBlockReader {	
	// fields
	private InputStream input;
	private byte[] next;
	private boolean hasNext;
	
	public MapBlockReader(InputStream input) {
		if (!Arguments.isNotNull(input))
			throw new IllegalArgumentException();
		
		this.input = input;
		this.next = new byte[MapBlock.BLOCK_SIZE];
		this.readBlock();
	}
	
	public boolean hasNextBlock() {
		return this.hasNext;
	}
	
	public MapBlock getNextBlock() {
		if (this.hasNextBlock()) {
			MapBlock block = new MapBlock(this.next);
			readBlock();
			return block;
		}else 
			throw new NoSuchElementException();
	}
	
	private void readBlock() {
		try {
			hasNext = input.read(next) > 0;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
