package com.edd.circlebrawl;

import acm.graphics.GImage;

public class Resource extends Item {

	/**
	 * Constructor
	 * 
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 * @param sprite
	 *            image object of the sprite
	 * @param driver
	 *            the associated CircleBrawl driver
	 * @param efficacy
	 *            the strength of item
	 * @param multiple
	 *            the multiplier for stat effects
	 */
	public Resource(int x, int y, GImage sprite, CircleBrawl driver, int efficacy, int multiple) {
		this.sprite = sprite;
		this.sprite.setLocation(x, y);
		this.driver = driver;
		this.efficacy = efficacy;
		this.multiple = multiple;
	}

	/**
	 * Activates the side effects of the item. Specifically, consumer's size.
	 * 
	 * @param consumer
	 *            the character consuming the item
	 */
	@Override
	public void activate(BaseActor consumer) {
		if (consumer instanceof Character) {
			((Character) consumer).modifySize(efficacy * multiple);
		}
	}

	public void tick() {
	}

}
