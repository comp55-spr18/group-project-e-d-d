package com.edd.powerup;

import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.CircleBrawl;
import com.edd.circlebrawl.Item;

import acm.graphics.GImage;

public class PowerUp extends Item {

	private int time; // how many seconds the PowerUp stays in effect for
	private PowerUpType type; // the type of the PowerUp
	
	public PowerUp(int x, int y, CircleBrawl driver, int efficacy, int multiple, PowerUpType type) {
		this.x = x;
		this.y = y;
		this.driver = driver;
		this.efficacy = efficacy;
		this.multiple = multiple;
		this.type = type;
		this.time = type.getTime();
		setupSprite("com/edd/powerup/"+type.getSpriteFile());
	}
	
	@Override
	public void activate(BaseActor consumer) {
		// TODO Auto-generated method stub
		
	}

}
