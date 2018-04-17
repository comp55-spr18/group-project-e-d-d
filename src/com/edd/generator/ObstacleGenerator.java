package com.edd.generator;

import com.edd.circlebrawl.ActorAccesser;
import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.GameType;
import com.edd.circlebrawl.MainApplication;
import com.edd.obstacle.Obstacle;

public class ObstacleGenerator extends BaseGenerator {
	
	public ObstacleGenerator(GameType gameType, MainApplication driver) {
		this.gameType = gameType;
		this.driver = driver;

		maxSpawns = 20;
		spawnDelay = 0; // all spawn immediately

		activated = true;
	}

	@Override
	public void spawn() {
		actors.add(new Obstacle(gameType, driver));
	}

	public boolean collidesWith(BaseActor anotherActor) {
		for (BaseActor actor : actors) {
			if (actor instanceof Obstacle && actor.collidesWith(anotherActor))
				return true;
		}
		return false;
	}
}
