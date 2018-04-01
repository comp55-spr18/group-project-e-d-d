package com.edd.circlebrawl;

import acm.graphics.GImage;

public class Resource extends Item {
	
	public Resource(int x, int y, GImage sprite, CircleBrawl driver, int efficacy, int multiple) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.driver = driver;
		this.efficacy = efficacy;
		this.multiple = multiple;
	}
	
	@Override
	public void activate(BaseActor consumer) {
		if(consumer instanceof Character) {
			((Character)consumer).modifySize(efficacy*multiple);
		}
	}
	
	public void tick() {}

}
