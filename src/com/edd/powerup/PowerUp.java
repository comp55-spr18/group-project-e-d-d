package com.edd.powerup;

import com.edd.character.Character;
import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.CircleBrawl;
import com.edd.circlebrawl.Item;
import com.edd.generator.BaseGenerator;
import com.edd.osvaldo.MainApplication;

import acm.graphics.GImage;

public class PowerUp extends Item {

	private int time;
	private int ticks;
	private PowerUpType type; // the type of the PowerUp
	private Character consumer; // the one who consumes the powerup
	private boolean activated = false;
	
	public PowerUp(int x, int y, MainApplication driver, int efficacy, int multiple, PowerUpType type, BaseGenerator generator) {
		this.x = x;
		this.y = y;
		this.driver = driver;
		this.efficacy = efficacy;
		this.multiple = multiple;
		this.type = type;
		this.time = type.getTime();
		this.generator = generator;
		setupSprite("com/edd/powerup/"+type.getSpriteFile());
		
		if (type == type.SPEED)
			((GImage) this.sprite).setBounds(x,y,80,80); 
		if (type == type.STRENGTH)
			((GImage) this.sprite).setBounds(x,y,90,100);
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
				System.out.println("Modify speed by "+finalEfficacy+"!");
				break;
			case STRENGTH:
				consumer.modifyStrength(finalEfficacy);
				System.out.println("Modify strength by "+finalEfficacy+"!");
				break;
			case ENDURANCE:
				consumer.modifyDefense(finalEfficacy);
				System.out.println("Modify defense by "+finalEfficacy+"!");
				break;
		}
		activated = !activated;
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
				toggleEffect();
				generator.addToRemoveList(this); // removing this powerup from ticklist
			} else {
				time--;
			}
	}

}
