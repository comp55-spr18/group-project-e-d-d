package com.edd.powerup;

import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.Character;
import com.edd.circlebrawl.CircleBrawl;
import com.edd.circlebrawl.Item;

public class PowerUp extends Item {

	private int time; // how many seconds the PowerUp stays in effect for
	private PowerUpType type; // the type of the PowerUp
	
	private int x;
	private int y;
	
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
		if(consumer instanceof Character) {
			Character consumerChar = (Character)consumer;
			int finalEfficacy = getFinalEfficacy();
			switch(type) {
				case SPEED:
					consumerChar.modifySpeed(finalEfficacy);
					//TODO: Insert logic for reset in speed after time.
					//consumerChar.modifySpeed(-finalEfficacy);
				case STRENGTH:
					consumerChar.modifyStrength(finalEfficacy);
					//TODO: Insert logic for reset in strength after time
					//consumerChar.modifyStrength(-finalEfficacy);
				case ENDURANCE:
					consumerChar.modifyDefense(finalEfficacy);
					//TODO: Insert logic for reset in defense after time
					//consumerChar.modifyDefense(-finalEfficacy);
			}
		}
	}
	
	public void tick() {
		
	}

	
	
	// The following methods needed to be implemented or else Java complained. 
	public void setX(int x) {
		// TODO Auto-generated method stub
		
	}
	
	public void setY(int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setupSprite(String spriteFile) {
		// TODO Auto-generated method stub
		
	};

}
