package com.edd.generator;

import com.edd.circlebrawl.GameType;
import com.edd.circlebrawl.Resource;
import com.edd.osvaldo.MainApplication;

public class ResourceGenerator extends BaseGenerator {

	public ResourceGenerator(GameType gameType, MainApplication mainApplication) {
		this.gameType = gameType;
		this.driver = mainApplication;
		
		maxSpawns = 18;
		spawnDelay = 1;
		
		activated = true;
	}
	
	@Override
	public void spawn() {
		actors.add(new Resource(gameType, driver,getRandomEfficacy(2,3),1,this));
		actors.add(new Resource(gameType, driver,getRandomEfficacy(2,3),1,this));
	}

}
