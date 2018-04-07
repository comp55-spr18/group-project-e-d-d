package com.edd.powerup;

import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.Character;
import com.edd.circlebrawl.CircleBrawl;
import com.edd.circlebrawl.Item;
import com.edd.generator.PowerUpGenerator;

public class PowerUp extends Item {

	private int time;
	private int ticks;
	private PowerUpType type; // the type of the PowerUp
	private Character consumer; // the one who consumes the powerup
	private PowerUpGenerator generator;
	private boolean activated = false;
	
	public PowerUp(int x, int y, CircleBrawl driver, int efficacy, int multiple, PowerUpType type, PowerUpGenerator generator) {
		this.x = x;
		this.y = y;
		this.driver = driver;
		this.efficacy = efficacy;
		this.multiple = multiple;
		this.type = type;
		this.time = type.getTime();
		this.generator = generator;
		setupSprite("com/edd/powerup/"+type.getSpriteFile());
	}
	
	@Override
	public void activate(BaseActor consumer) {
		if(consumer instanceof Character) {
			this.consumer = (Character)consumer;;
			toggleEffect();
		}
	}
	
	public void toggleEffect() {
		int finalEfficacy = activated ? getFinalEfficacy()*-1 : getFinalEfficacy();
		switch(type) {
			case SPEED:
				consumer.modifySpeed(finalEfficacy);
			case STRENGTH:
				consumer.modifyStrength(finalEfficacy);
			case ENDURANCE:
				consumer.modifyDefense(finalEfficacy);
		}
		activated = !activated;
		System.out.println("toggle - "+type+" - "+activated);
	}
	
	@Override
	public void tick() {
		ticks++;
		if(ticks >= driver.TICKS_PER_SECOND) {
			ticks = 0;
			secondPassed();
		}
	}
	
	public void secondPassed() {
		if(activated)
			if(time <= 0) {
				System.out.println("toggle");
				toggleEffect();
				generator.addToRemoveList(this); // removing this powerup from ticklist
			} else {
				System.out.println(time);
				time--;
			}
	}

}
