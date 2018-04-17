package com.edd.circlebrawl;

import com.edd.generator.BaseGenerator;

public abstract class Item extends BaseActor implements Tick {

	protected int multiple; // multiply any stat effects by this number
	protected int efficacy; // the strength of the item

	protected void basicItemConstructor(int efficacy, int multiple){
		this.efficacy = efficacy;
		this.multiple = multiple;
	}
	
	/**
	 * Removes all instance sof the item and triggers the activation
	 * 
	 * @param consumer
	 *            the actor to which the effects of item will be applied to
	 */
	public void consume(BaseActor consumer) {
		remove(); // remove item from visual display of screen
		activate(consumer); // activate effect of this item
	}

	// Triggers an effect such as increase in size or strength
	public abstract void activate(BaseActor consumer);

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
