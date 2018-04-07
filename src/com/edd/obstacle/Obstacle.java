package com.edd.obstacle;

import java.util.Random;

import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.CircleBrawl;
import com.edd.circlebrawl.Tick;
import com.edd.generator.BaseGenerator;
import com.edd.generator.ObstacleGenerator;

public class Obstacle extends BaseActor implements Tick {
	private String obstacleFile; // the filename for the obstacle image
	protected BaseGenerator generator; // the generator generating this obstacle

	public Obstacle(int x, int y, CircleBrawl driver, BaseGenerator generator) {
		this.x = x;
		this.y = y;
		this.driver = driver;
		this.generator = generator;
		this.obstacleFile = ((ObstacleGenerator) generator).randomObstacle(new Random());
		setupSprite("com/edd/obstacle/" + this.obstacleFile);
	}

	public String getObstacleFile() {
		return obstacleFile;
	}

	// public boolean collisionCheck(BaseActor anotherActor) {
	// if (anotherActor.collidesWith(this)) {
	// // Return true flag of stop Character from moving here
	// return true;
	// }
	// return false;
	// }

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}
}
