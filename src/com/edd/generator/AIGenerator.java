package com.edd.generator;

import com.edd.character.AI;
import com.edd.circlebrawl.GameType;
import com.edd.circlebrawl.MainApplication;

public class AIGenerator extends BaseGenerator {


	public AIGenerator(GameType gameType, MainApplication driver) {
		this.gameType = gameType;
		this.driver = driver;

		maxSpawns = driver.AI_MAX;
		spawnDelay = 10;
		
		activated = true;
	}

	
	@Override
	public void spawn() {
		actors.add(new AI(gameType, driver, this));
	}

}
