package com.edd.generator;

import com.edd.character.AI;
import com.edd.circlebrawl.GameType;
import com.edd.circlebrawl.MainApplication;

public class AIGenerator extends BaseGenerator {


	public AIGenerator(GameType gameType, MainApplication driver) {
		this.gameType = gameType;
		this.driver = driver;

		maxSpawns = 1;//driver.AI_MAX;
		spawnDelay = 2;
		
		activated = true;
	}

	
	@Override
	public void spawn() {
		actors.add(new AI(gameType, driver));
	}

}
