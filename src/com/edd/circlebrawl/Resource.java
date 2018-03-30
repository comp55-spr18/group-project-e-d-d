package com.edd.circlebrawl;

import acm.graphics.GImage;

public class Resource extends Item {

	private int efficacy;
	
	public Resource(int x, int y, GImage sprite, CircleBrawl driver, int efficacy) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.driver = driver;
		this.efficacy = efficacy*multiple; // multiply the efficacy by the multiple in its parent "Item."
	}
	
	@Override
	public void activate(BaseActor consumer) {
		if(consumer instanceof Character) {
			((Character)consumer).changeSize(efficacy);
		}
	}

}
