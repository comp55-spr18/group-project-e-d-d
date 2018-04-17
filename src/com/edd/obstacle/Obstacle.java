package com.edd.obstacle;

import java.util.Random;

import com.edd.circlebrawl.ActorAccesser;
import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.GameType;
import com.edd.circlebrawl.MainApplication;
import com.edd.circlebrawl.Tick;
import com.edd.generator.BaseGenerator;

import acm.graphics.GImage;

public class Obstacle extends BaseActor implements Tick {
	private final int MAX_IMAGES = 6; // number of images
	
	private String obstacleFile; // the filename for the obstacle image

	public Obstacle(GameType gameType, MainApplication driver) {
		basicPreConstructor(gameType,driver);
		basicObstacleConstructor();
	}
	
	public Obstacle(GameType gameType, MainApplication driver, GImage image) {
		basicPreConstructor(gameType,driver);
		basicObstacleConstructor(image);
	}
	
	public Obstacle(int x, int y, MainApplication driver) {
		basicPreConstructor(x,y,driver);
		basicObstacleConstructor();
	}
	
	public Obstacle(int x, int y, MainApplication driver, GImage image) {
		basicPreConstructor(x,y,driver);
		basicObstacleConstructor(image);
	}
	
	private void basicObstacleConstructor(){
		this.obstacleFile = randomObstacle();
		basicPostConstructor("com/edd/obstacle/" + this.obstacleFile);
	}

	private void basicObstacleConstructor(GImage image){
		this.obstacleFile = "";
		this.sprite = image;
		basicPostConstructor();
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
