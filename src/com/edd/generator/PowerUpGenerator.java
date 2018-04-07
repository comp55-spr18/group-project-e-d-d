package com.edd.generator;

import java.util.ArrayList;
import java.util.Random;

import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.CircleBrawl;
import com.edd.powerup.PowerUp;
import com.edd.powerup.PowerUpType;

import javafx.util.Pair;

public class PowerUpGenerator extends BaseGenerator {
	
	public ArrayList<PowerUp> usedPowerUps; //a list of all powerups that have been consumed
	
	public PowerUpGenerator(CircleBrawl driver) {
		usedPowerUps = new ArrayList<PowerUp>();
		
		this.driver = driver;
		
		maxSpawns = 5;
		spawnDelay = 3;
		
		activated = true;
	}
	
	private PowerUpType generatePowerUpType(Random rand) {
		int randomNumber = rand.nextInt(PowerUpType.getTotalPortions()); 
		int typeTotal = 0;
		for(PowerUpType type : PowerUpType.values()) {
			int nextTypeTotal = typeTotal + type.getPortions();
			if(randomNumber >= typeTotal && randomNumber < nextTypeTotal) {
				return type;
			}
			typeTotal = nextTypeTotal;
		}
		return null; // This should never be reached
	}
	
	@Override
	protected boolean remove(BaseActor actor){
		if(super.remove(actor) == true)
			return true;
		if(usedPowerUps.contains(actor)){
			usedPowerUps.remove(actor);
			return true;
		}
		return false;
	}
	
	@Override
	public void spawn() {
		PowerUpType generatedType = generatePowerUpType(rand);
		Pair<Integer,Integer> loc = generateLocation();
		actors.add(new PowerUp(loc.getKey(),loc.getValue(),driver,getRandomEfficacy(10,20),1,generatedType,this));
	}
	
	@Override
	public void tick() {
		for(BaseActor actor : actorsToRemove){
			remove(actor);
		}
		
		super.tick();
		
		if(activated) {
			for(BaseActor powerUp : actors){
				if(((PowerUp)powerUp).collisionCheck(driver.player)) {
					usedPowerUps.add((PowerUp)powerUp);
					actors.remove(powerUp);
					break;
				}
			}
			for(PowerUp powerUp : usedPowerUps) {
				powerUp.tick();
			}
		}
	}
	
}
