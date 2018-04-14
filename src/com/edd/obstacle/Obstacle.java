package com.edd.obstacle;

import java.util.Random;

import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.Tick;
import com.edd.collision.CollisionBox;
import com.edd.generator.BaseGenerator;
import com.edd.osvaldo.MainApplication;

public class Obstacle extends BaseActor implements Tick {
	private final int MAX_IMAGES = 6; // number of images
	
	private String obstacleFile; // the filename for the obstacle image
	protected BaseGenerator generator; // the generator generating this obstacle

	public Obstacle(int x, int y, MainApplication driver, BaseGenerator generator) {
		basicPreConstructor(x,y,driver);

		this.driver = driver;
		this.generator = generator;
		this.obstacleFile = randomObstacle();
		
		basicPostConstructor("com/edd/obstacle/" + this.obstacleFile);
	}
	
	public Obstacle(int x, int y, MainApplication driver, BaseGenerator generator, String obstacleFile) {
		basicPreConstructor(x,y,driver);

		this.driver = driver;
		this.generator = generator;
		this.obstacleFile = obstacleFile;
		
		basicPostConstructor("com/edd/obstacle/" + this.obstacleFile);
	}

	public String randomObstacle() {
		Random rand = new Random();
		int selectedImageFile = rand.nextInt(MAX_IMAGES) + 1;
		switch (selectedImageFile) {
		case 1:
			return "redCrate.png";
		case 2:
			return "blueCrate.png";
		case 3:
			return "greenCrate.png";
		case 4:
			return "redCrateHorizontal.png";
		case 5:
			return "blueCrateHorizontal.png";
		default:
			return "greenCrateHorizontal.png";
		}
	}

	public String getObstacleFile() {
		return obstacleFile;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}
}
