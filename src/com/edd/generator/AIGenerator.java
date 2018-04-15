package com.edd.generator;

import com.edd.character.AI;
import com.edd.circlebrawl.GameType;
import com.edd.circlebrawl.MainApplication;

public class AIGenerator extends BaseGenerator {


	public AIGenerator(GameType gameType, MainApplication driver) {
		this.gameType = gameType;
		this.driver = driver;

		maxSpawns = 1;//driver.AI_MAX;
		spawnDelay = 0;
		
		activated = true;
	}

	
	@Override
	public void spawn() {
		actors.add(new AI((int)driver.player.getX()-100,(int)driver.player.getY(),gameType, driver, this));
	}

}
