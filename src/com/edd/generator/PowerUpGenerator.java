package com.edd.generator;

import java.util.ArrayList;
import java.util.Random;

import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.CircleBrawl;
import com.edd.circlebrawl.Item;
import com.edd.osvaldo.MainApplication;
import com.edd.powerup.PowerUp;
import com.edd.powerup.PowerUpType;

import javafx.util.Pair;

public class PowerUpGenerator extends BaseGenerator {
	
	private ArrayList<PowerUp> usedPowerUps; //a list of all powerups that have been consumed
	private ArrayList<PowerUp> usedPowerUpsToRemove; //a list of all powerups that have been consumed and need to be removed
	
	public PowerUpGenerator(MainApplication driver) {
		usedPowerUps = new ArrayList<PowerUp>();
		usedPowerUpsToRemove = new ArrayList<PowerUp>();
		
		this.driver = driver;
		
		maxSpawns = 5;
		spawnDelay = 5;
		
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
	
	public void use(PowerUp powerUp){
		if(actors.contains(powerUp)){
			actors.remove(powerUp);
			usedPowerUps.add(powerUp);
		}
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
	public void addToRemoveList(BaseActor actor){
		if(actors.contains(actor)){
			usedPowerUps.add((PowerUp)actor);
			actorsToRemove.add(actor);
		} else if(usedPowerUps.contains(actor)){
			usedPowerUpsToRemove.add((PowerUp)actor);
		}
	}
	
	@Override
	public void spawn() {
		PowerUpType generatedType = generatePowerUpType(rand);
		Pair<Integer,Integer> loc = generateLocation(50,150);
		actors.add(new PowerUp(loc.getKey(),loc.getValue(),driver,getRandomEfficacy(10,20),1,generatedType,this));
	}
	
	@Override
	public void tick() {
		for(BaseActor actor : actorsToRemove){
			remove(actor);
		}
		
		super.tick();
		
		if(activated) {

			for(PowerUp powerUp : usedPowerUpsToRemove){
				remove(powerUp);
			}
			
			usedPowerUpsToRemove.clear();
			
			for(PowerUp powerUp : usedPowerUps) {
				powerUp.tick();
			}
			
		}
	}
}
