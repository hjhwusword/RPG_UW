package Map.IDs;

import java.util.HashSet;
import java.util.LinkedList;

public class ImageID {
	private final String filename;
	private final int id;
	
	protected ImageID(String filename, int id) {
		this.filename = filename;
		this.id = id;
	}
	
	public int getID() {
		return this.id;
	}
	
	public String getFilename() {
		return this.filename;
	}
	
	/**
	 *  Check if you have a duplicate id when you declare the image
	 * @return true if there is duplicate, false otherwise
	 */
	public boolean hasDuplicateIDs(LinkedList<ImageID> imgs) {
		HashSet<Integer> set = new HashSet<Integer>();
		for (ImageID img : imgs) {
			if (set.contains(img.getID()))
				return false;
			set.add(img.getID());
		}
		return true;
	}
}
