package com.edd.generator;

import com.edd.character.AI;
import com.edd.circlebrawl.GameType;
import com.edd.osvaldo.MainApplication;

public class AIGenerator extends BaseGenerator {


	public AIGenerator(GameType gameType, MainApplication driver) {
		this.gameType = gameType;
		this.driver = driver;

		maxSpawns = 5;//driver.AI_MAX;
		spawnDelay = 0;
		
		activated = true;
	}

	
	@Override
	public void spawn() {
		actors.add(new AI(gameType, driver, this));
	}

}
