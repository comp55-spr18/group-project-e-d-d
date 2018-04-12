package com.edd.generator;

import com.edd.character.AI;
import com.edd.osvaldo.MainApplication;

import javafx.util.Pair;

public class AIGenerator extends BaseGenerator {


	public AIGenerator(MainApplication driver) {
		this.driver = driver;

		maxSpawns = 1;//driver.AI_MAX;
		spawnDelay = 0;
		
		activated = true;
	}

	
	@Override
	public void spawn() {
		Pair<Integer, Integer> loc = generateLocation(0, 200);
		actors.add(new AI(loc.getKey(), loc.getValue(), driver, this));
	}

}
