package Map.EventBlock;

import util.Arguments;
import ImgID.CharacterImgID;

public abstract class EventBlock{
	public CharacterImgID img;
	
	public EventBlock(CharacterImgID img) {
		this.setImg(img);
	}
	
	public abstract EventType getType();
	
	public void setImg(CharacterImgID img) {
		assert(Arguments.isNotNull(img));
		this.img = img;		
	}
	
	public CharacterImgID getImg() {
		return this.img;
	}
}