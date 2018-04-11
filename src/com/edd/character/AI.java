package com.edd.character;

import java.awt.Color;
import java.util.Random;

import com.edd.generator.AIGenerator;
import com.edd.osvaldo.MainApplication;

import acm.graphics.GOval;

public class AI extends Character {

	private static final int DETECTION_RANGE_X = 400;
	private static final int DETECTION_RANGE_Y = 200;
	private static final double DIRECTION_RESET_DELAY = .5; // time in seconds before AI chooses new direction
	
	private AIGenerator generator;
	private Random rand;
	
	private int directionResetTicks;
	private Direction direction;
	private int xVelocity;
	private int yVelocity;
	
	public AI(int x, int y, MainApplication driver, AIGenerator generator) {
		
		rand = new Random();
		
		this.x = x;
		this.y = y;
		this.size = 80+rand.nextInt(31);
		this.defense = 10;
		this.speed = 5;
		this.strength = 50;
		this.driver = driver;
		
		GOval localSprite = new GOval(x + size / 2, y + size / 2, size, size);
		setupSprite(localSprite);
		localSprite.setColor(new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));
		localSprite.setFilled(true);
	}
	
	@Override
	public void tick(){
		if(playerIsInDetectionRange()){
			int xVel = driver.player.getX() > x ? speed : -speed;
			int yVel = driver.player.getY() > y ? speed : -speed;
			if(driver.player.getX() == x)
				xVel = 0;
			if(driver.player.getY() == y)
				yVel = 0;
			move(xVel, yVel);
		} else {
			directionResetTicks++;
			if(directionResetTicks >= DIRECTION_RESET_DELAY*driver.TICKS_PER_SECOND){
				chooseNewDirection();
				directionResetTicks = 0;
			}
			move(xVelocity,yVelocity);
		}
	}
	
	private boolean playerIsInDetectionRange(){
		return(Math.abs(driver.player.getX()-x) <= DETECTION_RANGE_X && Math.abs(driver.player.getY()-y) <= DETECTION_RANGE_Y);
	}
	
	private void chooseNewDirection(){
		Direction[] directions = Direction.values();
		direction = directions[rand.nextInt(directions.length)];

		// getting x velocity from x rate of change in direction
		switch(direction.getXRate()){
			case NEUTRAL:
				xVelocity = 0;
				break;
			case INCREASING:
				xVelocity = speed;
				break;
			case DECREASING:
				xVelocity = -speed;
				break;
		}
		
		// getting y velocity from y rate of change in direction
		switch(direction.getYRate()){
			case NEUTRAL:
				yVelocity = 0;
				break;
			case INCREASING:
				yVelocity = speed;
				break;
			case DECREASING:
				yVelocity = -speed;
				break;
		}
	}
	
	public Direction getDirection(){ return direction; }
}
