package com.edd.circlebrawl;

import java.awt.Color;
import java.util.Random;

import com.edd.generator.BaseGenerator;

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
	public Resource(int x, int y, CircleBrawl driver, int efficacy, int multiple, BaseGenerator generator) {
		this.x = x;
		this.y = y;
		Random rand = new Random();
		sprite = new GOval(x, y, efficacy * multiple * 2, efficacy * multiple * 2);
		((GOval) sprite).setFilled(true);
		((GOval) sprite).setFillColor(new Color(rand.nextInt(200)+25, rand.nextInt(200)+25, rand.nextInt(200)+25));
		this.driver = driver;
		this.efficacy = efficacy;
		this.multiple = multiple;
		this.generator = generator;
		setupSprite(sprite);
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
			generator.addToRemoveList(this);
		}
	}

	@Override
	public void tick() {
		//animation?
	}

}
