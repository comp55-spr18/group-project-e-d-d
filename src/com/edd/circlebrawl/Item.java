package com.edd.circlebrawl;

public abstract class Item extends BaseActor {
	
	public void consume(BaseActor consumer) {
		// Access the item list
		if(CircleBrawl.ITEM_LIST.contains(this)) {
			CircleBrawl.ITEM_LIST.remove(this); // remove this item from the global list
		}
		
		remove(); // remove this item from visual display of screen
		activate(consumer); // activate effect of this item
	}
	
	public abstract void activate(BaseActor consumer); // triggers an effect such as increase in size or strength
	
	@Override
	public boolean collidesWith(BaseActor anotherActor) {
		if(super.collidesWith(anotherActor)) {
			consume(anotherActor);
			return true;
		}
		return false;
	}
	
}
