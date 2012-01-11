package util;

public final class MapBox {
	public static final byte boxSize 	= 5;	// in byte
	
	// in bit
	public static final byte picID1 	= 10;
	public static final byte picID2 	= 10;
	public static final byte boxID 		= 13;
	public static final byte header		= 2;
	
	private byte[] data;
	public MapBox(byte[] data) {
		if (data.length != MapBox.boxSize)
			throw new IllegalArgumentException("data size should be " + boxSize);
		this.data = data;
	}
	
	public boolean isWalkable() {
		return false;
	}
	
	public int getLayer1_ID() {
		return 0;
	}
	
	public int getLayer2_ID() {
		return 0;
	}
	
	public boolean hasEvent() {
		return false;
	}
	
	public int getBoxID() {
		return 0;
	}
	
	public void setWalkable(boolean walkable) {
		
	}
	
	public void setEvent(boolean isEvent) {
		
	}
	
	public void setLayer1_ID(int picID) {
		if (!this.isValidValue(picID, MapBox.picID1))
			throw new IllegalArgumentException("Invalid picture ID");
		data[0] = (byte) (-1 & (picID >> (Integer.SIZE - MapBox.picID1)));
		byte lowerbits = -1 ^ (-1 << (MapBox.picID1 - Byte.SIZE));
		
	}
	
	public void setLayer2_ID(int picID) {
		if (!this.isValidValue(picID, MapBox.picID2))
			throw new IllegalArgumentException("Invalid picture ID");
	}
	
	public void setBoxID(int picID) {
		if (!this.isValidValue(picID, MapBox.boxID))
			throw new IllegalArgumentException("Invalid box ID");
		
	}
	
	private boolean isValidValue(int value, byte size) {
		if ((value & (-1 << size)) != 0)
			return false;
		return true;
	}
}
