package com.edd.powerup;

import java.util.Random;

import com.edd.circlebrawl.CircleBrawl;

public class PowerUpGenerator {

	private CircleBrawl driver;
	private boolean activated;
	
	public PowerUpGenerator(CircleBrawl driver) {
		this.driver = driver;
	}
	
	public PowerUp generatePowerUp() {
		Random rand = new Random();
		PowerUpType generatedType = generatePowerUpType(rand);
		return new PowerUp(rand.nextInt(driver.MAP_WIDTH-50)+1,rand.nextInt(driver.MAP_HEIGHT-50)+1,driver,getRandomEfficacy(rand),1,generatedType);
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
	
	public int getRandomEfficacy(Random rand) {
		return rand.nextInt(11)+10; // Efficacy is between 10 and 20. This can be changed.
	}
	
	public void activate() {
		activated = true;
	}
	
	public void deactivate() {
		activated = false;
	}
	
}
