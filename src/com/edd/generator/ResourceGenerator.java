package com.edd.generator;

import com.edd.circlebrawl.Resource;
import com.edd.osvaldo.MainApplication;

import javafx.util.Pair;

public class ResourceGenerator extends BaseGenerator {

	public ResourceGenerator(MainApplication mainApplication) {
		this.driver = mainApplication;
		
		maxSpawns = 9;
		spawnDelay = 2;
		
		activated = true;
	}
	
	@Override
	public void spawn() {
		Pair<Integer,Integer> loc1 = generateLocation(50,150);
		actors.add(new Resource(loc1.getKey(),loc1.getValue(),driver,getRandomEfficacy(5,10),1,this));
		Pair<Integer,Integer> loc2 = generateLocation(50,150);
		actors.add(new Resource(loc2.getKey(),loc2.getValue(),driver,getRandomEfficacy(5,10),1,this));
	}

}
