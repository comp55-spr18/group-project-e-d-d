package com.edd.circlebrawl;

public abstract class Item extends BaseActor {
	
	protected int multiple; // multiply any stat effects by this number
	protected int efficacy; // the strength of the item
	
	public void consume(BaseActor consumer) {
		// Access the item list
		if(driver.ITEM_LIST.contains(this)) {
			driver.ITEM_LIST.remove(this); // remove this item from the global list
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
	
	protected int getFinalEfficacy() { // returns the efficacy and any modifiers one may want to make to it
		return efficacy*multiple;
	}
	
}
