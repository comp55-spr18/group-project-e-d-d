package com.edd.generator;

import com.edd.obstacle.Obstacle;
import com.edd.osvaldo.MainApplication;

public class BoundaryGenerator extends BaseGenerator {

	boolean hasSpawned = false;
	
	public BoundaryGenerator(MainApplication driver) {
		this.driver = driver;	
		maxSpawns = 0;
		spawnDelay = 0;		
		activated = true;
	}
	
	// SPAWN MUST BE CALLED MANUALLY
	
	@Override
	public void spawn() {
		if(!hasSpawned){
			activated = false;
			actors.add(new Obstacle(0,-320,driver,this,"boundary_horizontal.png"));
			actors.add(new Obstacle(0,MainApplication.MAP_HEIGHT,driver,this,"boundary_horizontal.png"));
			actors.add(new Obstacle(-320,0,driver,this,"boundary_vertical.png"));
			actors.add(new Obstacle(MainApplication.MAP_WIDTH,0,driver,this,"boundary_vertical.png"));
		}
	}

}
