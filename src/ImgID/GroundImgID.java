package ImgID;

import java.util.LinkedList;

public final class GroundImgID extends ImageID {
	private static final LinkedList<ImageID> imgs 
						= new LinkedList<ImageID>();
		
	public static final GroundImgID GRASS = new GroundImgID("grass.jpg", 0x01);
	public static final GroundImgID WATER = new GroundImgID("water.jpg", 0x02);
	
	private GroundImgID(String filename, int id) {
		super(filename, id);
		imgs.add(this);
		assert(super.hasDuplicateIDs(imgs));
	}
}
