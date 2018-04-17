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
	private final String[] IMAGE_FILES = {"bruntCar1_small.png","bruntCar2_small.png","sandbag2V.png"};
	
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
		Random rand = new Random();
		this.obstacleFile = IMAGE_FILES[rand.nextInt(IMAGE_FILES.length)];
		basicPostConstructor("com/edd/obstacle/" + this.obstacleFile);
	}

	private void basicObstacleConstructor(GImage image){
		this.obstacleFile = "";
		this.sprite = image;
		basicPostConstructor();
	}

	public String getObstacleFile() {
		return obstacleFile;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}
}
