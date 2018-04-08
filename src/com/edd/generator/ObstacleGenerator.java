package com.edd.generator;

import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.CircleBrawl;
import com.edd.obstacle.Obstacle;

import javafx.util.Pair;

public class ObstacleGenerator extends BaseGenerator {
	public ObstacleGenerator(CircleBrawl driver) {
		this.driver = driver;

		maxSpawns = 2;
		spawnDelay = 5;

		activated = true;
	}

	@Override
	public void spawn() {
		Pair<Integer, Integer> loc = generateLocation(0, 200);
		actors.add(new Obstacle(loc.getKey(), loc.getValue(), driver, this));
	}

	public boolean collidesWith(BaseActor anotherActor) {
		for (BaseActor actor : actors) {
			if (actor instanceof Obstacle && actor.collidesWith(anotherActor))
				return true;
		}
		return false;
	}
}
