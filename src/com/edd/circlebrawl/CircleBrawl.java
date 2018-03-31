package com.edd.circlebrawl;

import java.util.ArrayList;

import com.edd.powerup.PowerUpGenerator;

// Driver Class
public class CircleBrawl extends MainApplication {

	public final ArrayList<Item> ITEM_LIST = new ArrayList<Item>();
	public final PowerUpGenerator POWERUP_GEN = new PowerUpGenerator(this);
	public final int MAP_WIDTH = 1024;
	public final int MAP_HEIGHT = 768;
	
	@Override
	public void run() {
		this.setSize(MAP_WIDTH, MAP_HEIGHT);
		POWERUP_GEN.generatePowerUp();
		POWERUP_GEN.generatePowerUp();
		POWERUP_GEN.generatePowerUp();
		POWERUP_GEN.generatePowerUp();
		POWERUP_GEN.generatePowerUp();
	}
}
