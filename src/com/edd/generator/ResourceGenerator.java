package com.edd.generator;

import com.edd.circlebrawl.GameType;
import com.edd.circlebrawl.Resource;
import com.edd.osvaldo.MainApplication;

public class ResourceGenerator extends BaseGenerator {

	public ResourceGenerator(GameType gameType, MainApplication mainApplication) {
		this.gameType = gameType;
		this.driver = mainApplication;
		
		maxSpawns = 250;
		spawnDelay = 1;
		
		activated = true;
	}
	
	@Override
	public void spawn() {
		for(int i=0;i<10;i++)
			actors.add(new Resource(gameType, driver,getRandomEfficacy(2,3),1,this));
	}

}
