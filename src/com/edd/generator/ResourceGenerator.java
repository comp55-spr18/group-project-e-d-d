package com.edd.generator;

import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.CircleBrawl;
import com.edd.circlebrawl.Item;
import com.edd.circlebrawl.Resource;

import javafx.util.Pair;

public class ResourceGenerator extends BaseGenerator {

	public ResourceGenerator(CircleBrawl driver) {
		this.driver = driver;
		
		maxSpawns = 7;
		spawnDelay = 3;
		
		activated = true;
	}
	
	@Override
	public void spawn() {
		Pair<Integer,Integer> loc1 = generateLocation();
		actors.add(new Resource(loc1.getKey(),loc1.getValue(),driver,getRandomEfficacy(5,10),1,this));
		Pair<Integer,Integer> loc2 = generateLocation();
		actors.add(new Resource(loc2.getKey(),loc2.getValue(),driver,getRandomEfficacy(5,10),1,this));
	}

}
