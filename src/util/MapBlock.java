package util;

public final class MapBlock {
	private static final boolean DEBUG = false;
	
	public static final byte BLOCK_SIZE = 
				(byte) (Math.ceil((MapBlock.PIC_ID_SIZE * MapBlock.NUM_LAYER 
				+ MapBlock.BLOCK_ID_SIZE) * 1.0 / Byte.SIZE) + 1); // in byte
	
	// in bit
	public static final byte PIC_ID_SIZE	= 10;
	public static final byte BLOCK_ID_SIZE 	= 12;
	public static final byte HEADER_SIZE	= 2;
	public static final byte NUM_LAYER		= 2;
	
	private final int walkableBit = Byte.MIN_VALUE;
	private final int eventBit = 1 << (Byte.SIZE - 2);
	
	private byte[] data;
	private Parameter par;
	
	public MapBlock() {
		this(new byte[MapBlock.BLOCK_SIZE]);		
	}
	
	public MapBlock(byte[] data) {
		if (data.length != MapBlock.BLOCK_SIZE)
			throw new IllegalArgumentException("data size should be " + BLOCK_SIZE);
		assert(MapBlock.PIC_ID_SIZE >= Byte.SIZE);
		assert(MapBlock.BLOCK_ID_SIZE >= Byte.SIZE);
		this.data = data;
		par = new Parameter();
	}
	
	public boolean isWalkable() {
		return ((data[MapBlock.BLOCK_SIZE - 1] & this.walkableBit) != 0);
	}
	
	public boolean hasEvent() {
		return ((data[MapBlock.BLOCK_SIZE - 1] & this.eventBit) != 0);
	}
	
	public void setWalkable(boolean walkable) {
		byte d = data[MapBlock.BLOCK_SIZE - 1];
		data[MapBlock.BLOCK_SIZE - 1] = walkable ? (byte) (d | this.walkableBit) : 
												(byte) (d & (-1 ^ this.walkableBit));;
	}
	
	public void setEvent(boolean isEvent) {
		byte d = data[MapBlock.BLOCK_SIZE - 1];
		data[MapBlock.BLOCK_SIZE - 1] = isEvent ? (byte) (d | this.eventBit) : 
												(byte) (d & (-1 ^ this.eventBit));;
	}
	
	public int getLayer1_ID() {
		par.setLayerIDParameter(0);
		return this.getID();
	}
	
	public int getLayer2_ID() {
		par.setLayerIDParameter(1);
		return this.getID();
	}
	
	public int getBlock_ID() {
		par.setBlockIDParameter();
		return this.getID();
	}
	
	private int getID() {
		int id = 0;
		if (par.upper != 0) {
			id = (byte) (data[par.pos] & ~(-1 << par.upper));
			Debug.debugOutput("\tid upper: " + id, DEBUG);
		}
		for (int i = 1; i <= par.len ;i++) {
			id <<= Byte.SIZE;
			id |= data[par.pos + i] & ~(-1 << Byte.SIZE);
		}
		if (par.lower != 0) {
			id <<= par.lower;			
			int id_lower = data[par.pos + par.len + 1] >> (par.lower_rest);
			id_lower = id_lower >= 0 ? id_lower : id_lower ^ (-1 << par.lower);
			Debug.debugOutput("\tid lower: " + id_lower, DEBUG);
			id |= id_lower & ~(-1 << par.lower);
		}
		return id;
	}
	
	public void setLayer1_ID(int pic_id) {
		if (!this.isValidValue(pic_id, MapBlock.PIC_ID_SIZE))
			throw new IllegalArgumentException("Invalid picture ID");		
		par.setLayerIDParameter(0);		
		setID(pic_id);
	}
	
	public void setLayer2_ID(int pic_id) {
		if (!this.isValidValue(pic_id, MapBlock.PIC_ID_SIZE))
			throw new IllegalArgumentException("Invalid picture ID");
		par.setLayerIDParameter(1);		
		setID(pic_id);
	}
	
	public void setBlock_ID(int blockID) {
		if (!this.isValidValue(blockID, MapBlock.BLOCK_ID_SIZE))
			throw new IllegalArgumentException("Invalid block ID");		
		par.setBlockIDParameter();		
		setID(blockID);
	}
	
	private void setID(int id) {
		if (par.lower != 0) {
			byte lower = (byte) (id << par.lower_rest);
			Debug.debugOutput("lower: " + lower, DEBUG);
			data[par.pos + par.len + 1] &= (byte) ~(-1 << par.lower_rest);
			data[par.pos + par.len + 1] |= lower;
			id >>= par.lower;
		}
		for (int i = par.len; i > 0; i--) {
			data[par.pos + i] = 0;
			data[par.pos + i] |= (byte) id;
			id >>= Byte.SIZE;
		}
		if (par.upper != 0) {
			data[par.pos] &= -1 << (par.upper);
			data[par.pos] |= id;
		}
	}
	
	// Check if the size of given value cannot be contained
	// within the given size
	private boolean isValidValue(int value, int size) {
		if ((value & (-1 << size)) != 0)
			return false;
		return true;
	}
	
	byte[] getData() {
		byte[] ret = new byte[this.data.length];
		for(int i = 0; i < ret.length; i++) {
			ret[i] = this.data[i];
		}
		return ret;
	}
	
	private class Parameter {
		private int pos;
		private int offset;
		private int upper;
		private int lower;
		private int lower_rest;
		private int len;
		
		private void setLayerIDParameter(int layer) {
			pos = MapBlock.PIC_ID_SIZE * layer / Byte.SIZE;
			offset = MapBlock.PIC_ID_SIZE * layer % Byte.SIZE;
			upper = Byte.SIZE - offset;
			lower = (MapBlock.PIC_ID_SIZE + offset) % Byte.SIZE;
			lower_rest = Byte.SIZE - lower;
			len = (MapBlock.PIC_ID_SIZE - upper - lower) / Byte.SIZE;
		}
		
		private void setBlockIDParameter() {
			pos = MapBlock.PIC_ID_SIZE * MapBlock.NUM_LAYER / Byte.SIZE;
			offset = MapBlock.PIC_ID_SIZE * MapBlock.NUM_LAYER % Byte.SIZE;
			upper = Byte.SIZE - offset;
			lower = (offset + MapBlock.BLOCK_ID_SIZE) % Byte.SIZE;
			lower_rest = Byte.SIZE - lower;
			len = (MapBlock.BLOCK_ID_SIZE - upper - lower) / Byte.SIZE;
		}
	}
}
