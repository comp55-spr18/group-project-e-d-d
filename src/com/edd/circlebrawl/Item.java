package com.edd.circlebrawl;

public abstract class Item extends BaseActor {
	
	public void consume(BaseActor consumer) {
		// TODO: Access global list
		// TODO: Remove item from list
		// TODO: Remove item from screen
		activate(consumer);
	}
	
	public void activate(BaseActor consumer) {
		// Leave empty! Implement in PowerUp and Resource
	}
	
	@Override
	public boolean collidesWith(BaseActor anotherActor) {
		if(super.collidesWith(anotherActor)) {
			consume(anotherActor);
			return true;
		}
		return false;
	}
	
}
