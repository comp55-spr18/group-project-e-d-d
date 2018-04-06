package com.edd.circlebrawl;

import java.awt.Color;
import java.util.Random;

import acm.graphics.GOval;

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
	public Resource(int x, int y, CircleBrawl driver, int efficacy, int multiple) {
		Random rand = new Random();
		sprite = new GOval(x, y, efficacy * multiple, efficacy * multiple);
		((GOval) sprite).setFilled(true);
		((GOval) sprite).setFillColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
		setupSprite(sprite);
		this.driver = driver;
		this.efficacy = efficacy;
		this.multiple = multiple;
	}

	public Resource generateResource() {
		Random rand = new Random();
		return new Resource(rand.nextInt(driver.MAP_WIDTH - 50) + 1, rand.nextInt(driver.MAP_HEIGHT - 50) + 1, driver,
				rand.nextInt(10), rand.nextInt(10));
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

	@Override
	public void tick() {

	}

}
