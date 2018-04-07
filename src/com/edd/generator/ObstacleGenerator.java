package com.edd.generator;

import java.util.Random;

import com.edd.circlebrawl.CircleBrawl;
import com.edd.obstacle.Obstacle;

import javafx.util.Pair;

public class ObstacleGenerator extends BaseGenerator {
	private final int MAX_IMAGES = 6; // number of images

	public ObstacleGenerator(CircleBrawl driver) {
		this.driver = driver;

		maxSpawns = 3;
		spawnDelay = 3;

		activated = true;
	}

	@Override
	public void spawn() {
		Pair<Integer, Integer> loc1 = generateLocation();
		actors.add(new Obstacle(loc1.getKey(), loc1.getValue(), driver, this));
		Pair<Integer, Integer> loc2 = generateLocation();
		actors.add(new Obstacle(loc2.getKey(), loc2.getValue(), driver, this));
	}

	public String randomObstacle(Random rand) {
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

}
