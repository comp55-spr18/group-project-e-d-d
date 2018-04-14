package com.edd.powerup;

import com.edd.character.AttackOrb;
import com.edd.character.Character;
import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.Item;
import com.edd.generator.BaseGenerator;
import com.edd.osvaldo.MainApplication;

public class PowerUp extends Item {

	private int time;
	private int ticks;
	private PowerUpType type; // the type of the PowerUp
	private Character consumer; // the one who consumes the powerup
	private boolean activated = false;
	private AttackOrb attackOrb;
	
	public PowerUp(int x, int y, MainApplication driver, int efficacy, int multiple, PowerUpType type, BaseGenerator generator) {

		basicPreConstructor(x,y,driver);
		
		this.efficacy = efficacy;
		this.multiple = multiple;
		this.type = type;
		this.time = type.getTime();
		this.generator = generator;

		basicPostConstructor("com/edd/powerup/"+type.getSpriteFile());
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
			case ATTACK_ORB:
				if(!activated){
					attackOrb = consumer.spawnAttackOrb();
					System.out.println("Spawned attack orb!");
				} else {
					consumer.despawnAttackOrb(attackOrb);
					System.out.println("Despawned attack orb!");
				}
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
		if(activated){
			if(time <= 0) {
				toggleEffect();
				generator.addToRemoveList(this); // removing this powerup from ticklist
			} else {
				time--;
			}
		}
	}

}
