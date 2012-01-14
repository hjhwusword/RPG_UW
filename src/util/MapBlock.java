package util;

/**
 * stores, set and retreieve the information
 * of a map block.
 * @author chiehwu
 *
 */
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
	
	/**
	 * Constructs a MapBlock with no data assigned
	 */
	public MapBlock() {
		this(new byte[MapBlock.BLOCK_SIZE]);		
	}
	
	/**
	 * Constructs a MapBlock with the given data
	 * @param data an array of bytes representing the information
	 * @throw IllegalArgumentException when the given data length 
	 * is not equal to the Block size
	 * @throw IllegalArgumentException when the data is null
	 * @pre size of picture id should be greater than a byte
	 * @pre size of block id should be greater than a byte
	 */
	public MapBlock(byte[] data) {
		if (Arguments.isNotNull(data))
			throw new IllegalArgumentException("data is null");			
		if (data.length != MapBlock.BLOCK_SIZE)
			throw new IllegalArgumentException("data size should be " + BLOCK_SIZE);
		assert(MapBlock.PIC_ID_SIZE >= Byte.SIZE);
		assert(MapBlock.BLOCK_ID_SIZE >= Byte.SIZE);
		this.data = data;
		par = new Parameter();
	}
	
	/**
	 * checks if the block is walkable
	 * @return true if the block is walkable, false otherwise
	 */
	public boolean isWalkable() {
		return ((data[MapBlock.BLOCK_SIZE - 1] & this.walkableBit) != 0);
	}
	
	/**
	 * checks if the block has an event
	 * @return true if the block has an event, false otherwise
	 */
	public boolean hasEvent() {
		return ((data[MapBlock.BLOCK_SIZE - 1] & this.eventBit) != 0);
	}
	
	/**
	 * sets the MapBlock to walkable/non-walkable
	 * @param walkable true, set to walkable, false otherwise
	 */
	public void setWalkable(boolean walkable) {
		byte d = data[MapBlock.BLOCK_SIZE - 1];
		data[MapBlock.BLOCK_SIZE - 1] = walkable ? (byte) (d | this.walkableBit) : 
												(byte) (d & (-1 ^ this.walkableBit));;
	}
	
	/**
	 * sets the MapBlock to an event block or turn it off
	 * @param isEvent true set to an event, false otherwise
	 */
	public void setEvent(boolean isEvent) {
		byte d = data[MapBlock.BLOCK_SIZE - 1];
		data[MapBlock.BLOCK_SIZE - 1] = isEvent ? (byte) (d | this.eventBit) : 
												(byte) (d & (-1 ^ this.eventBit));;
	}
	
	/**
	 * gets the picture id of the given layer
	 * @param layer the layer whose id is needed
	 * @return the picture id of layer
	 * @throw IllegalArgumentException if layer is larger than NUM_LAYER or less than 1
	 */
	public int getLayer_ID(int layer) {
		if (!Arguments.isInRange(layer, 1, MapBlock.NUM_LAYER))
			throw new IllegalArgumentException("Layer should be less or equal to " + MapBlock.NUM_LAYER);
		par.setLayerIDParameter(layer - 1);
		return this.getID();
	}
	
	/**
	 * gets the block id
	 * @return the block id
	 */
	public int getBlock_ID() {
		par.setBlockIDParameter();
		return this.getID();
	}
	
	// gets the id of the assigned parameters
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
	
	/**
	 * sets the given layer id to the given id
	 * @param layer the layer needed to be set
	 * @param pic_id the id needed to be set
	 * @throws IllegalArgumentException when layer is larger than
	 * 			the default number of layers or less than 1
	 * @throws IllegalArugumentException when the size of pic_id
	 * 			is greater than PIC_ID_SIZE
	 */
	public void setLayer_ID(int layer, int pic_id) {
		if (!Arguments.isInRange(layer, 1, MapBlock.NUM_LAYER + 1))
			throw new IllegalArgumentException("Layer should be less or equal to " + MapBlock.NUM_LAYER);
		if (!this.isValidValue(pic_id, MapBlock.PIC_ID_SIZE))
			throw new IllegalArgumentException("Invalid picture ID");		
		par.setLayerIDParameter(layer - 1);		
		setID(pic_id);
	}
	
	/**
	 * sets the block id to the given id
	 * @param blockID the id needed to be set
	 * @throws IllegalArugumentException when the size of blockID
	 * 			is greater than BLOCK_ID_SIZE
	 */
	public void setBlock_ID(int blockID) {
		if (!this.isValidValue(blockID, MapBlock.BLOCK_ID_SIZE))
			throw new IllegalArgumentException("Invalid block ID");		
		par.setBlockIDParameter();		
		setID(blockID);
	}
	
	// sets the data to the given id 
	// according to the parameters
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
	
	// Checks if the size of given value cannot be contained
	// within the given size
	private boolean isValidValue(int value, int size) {
		if ((value & (-1 << size)) != 0)
			return false;
		return true;
	}
	
	/**
	 * produce a copy of the data of the map block
	 * @return an array of bytes representing the information
	 */
	byte[] getData() {
		byte[] ret = new byte[this.data.length];
		for(int i = 0; i < ret.length; i++) {
			ret[i] = this.data[i];
		}
		return ret;
	}
	
	// stores the parameters needed for storing and 
	// retrieving id
	private class Parameter {
		private int pos;
		private int offset;
		private int upper;
		private int lower;
		private int lower_rest;
		private int len;
		
		// sets the parameters respecting to layer
		private void setLayerIDParameter(int layer) {
			pos = MapBlock.PIC_ID_SIZE * layer / Byte.SIZE;
			offset = MapBlock.PIC_ID_SIZE * layer % Byte.SIZE;
			upper = Byte.SIZE - offset;
			lower = (MapBlock.PIC_ID_SIZE + offset) % Byte.SIZE;
			lower_rest = Byte.SIZE - lower;
			len = (MapBlock.PIC_ID_SIZE - upper - lower) / Byte.SIZE;
		}
		
		// sets the parameters respecting to block
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
