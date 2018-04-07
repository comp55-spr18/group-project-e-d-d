package com.edd.obstacle;

import java.util.Random;

import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.CircleBrawl;
import com.edd.circlebrawl.Tick;
import com.edd.generator.BaseGenerator;
import com.edd.generator.ObstacleGenerator;

public class Obstacle extends BaseActor implements Tick {
	private final int MAX_IMAGES = 6; // number of images
	
	private String obstacleFile; // the filename for the obstacle image
	protected BaseGenerator generator; // the generator generating this obstacle

	public Obstacle(int x, int y, CircleBrawl driver, BaseGenerator generator) {
		this.x = x;
		this.y = y;
		this.driver = driver;
		this.generator = generator;
		this.obstacleFile = randomObstacle();
		setupSprite("com/edd/obstacle/" + this.obstacleFile);
	}

	public String randomObstacle() {
		Random rand = new Random();
		int selectedImageFile = rand.nextInt(MAX_IMAGES) + 1;
		switch (selectedImageFile) {
		case 1:
			return "cloud1.png";
		case 2:
			return "cloud2.png";
		case 3:
			return "cloud3.png";
		case 4:
			return "cloud4.png";
		case 5:
			return "cloud5.png";
		case 6:
			return "airplane.png";
		default:
			return "cloud1.png";
		}
	}

	public String getObstacleFile() {
		return obstacleFile;
	}

	// TODO: THIS SHOULD BE collidesWith() NOT COLLISIONCHECK.. I think
	
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
