package com.edd.circlebrawl;

public abstract class Item extends BaseActor implements Tick {

	protected int multiple; // multiply any stat effects by this number
	protected int efficacy; // the strength of the item

	/**
	 * Removes all instance sof the item and triggers the activation
	 * 
	 * @param consumer
	 *            the actor to which the effects of item will be applied to
	 */
	public void consume(BaseActor consumer) {
		if (this != null && driver.ITEM_LIST.contains(this)) {
			driver.ITEM_LIST.remove(this); // remove this item from the global item list
		}

		remove(); // remove item from visual display of screen
		activate(consumer); // activate effect of this item
	}

	// Triggers an effect such as increase in size or strength
	public abstract void activate(BaseActor consumer);

	public boolean collisionCheck(BaseActor anotherActor) {
		if(anotherActor.collidesWith(this)) {
			consume(anotherActor);
			return true;
		}
		return false;
	}

	/**
	 * Overrides BaseActor. Detects if the argument Actor collides with the Actor of
	 * the current class. If so, then activate the effects.
	 * 
	 * @return the new efficacy after application of the modifier
	 */
	protected int getFinalEfficacy() {
		return efficacy * multiple;
	}

	// TODO: Implement tick
	public abstract void tick();

}
