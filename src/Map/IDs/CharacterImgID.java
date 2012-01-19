package Map.IDs;

import java.util.LinkedList;

public final class CharacterImgID extends ImageID {
	private static final LinkedList<ImageID> imgs 
						= new LinkedList<ImageID>();
	
	public CharacterImgID(String filename, int id) {
		super(filename, id);
		imgs.add(this);
		assert(super.hasDuplicateIDs(imgs));
	}

}
