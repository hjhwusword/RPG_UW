package Map.EventBlock;

import java.util.LinkedList;

import util.Arguments;

import battle.Item;

import ImgID.CharacterImgID;

public final class ShopBlock extends EventBlock {
	LinkedList<Item> inventories;

	public ShopBlock(CharacterImgID img) {
		super(img);
		inventories = new LinkedList<Item>();
	}
	
	public void addItem(Item item) {
		Arguments.isNotNull(item);
		this.inventories.add(item);
	}
	
	public Item removeItem() {
		//TODO finish ID thing
		return null;
	}

	@Override
	public EventType getType() {
		return EventType.SHOP;
	}

}
