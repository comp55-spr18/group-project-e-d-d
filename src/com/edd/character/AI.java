package com.edd.character;

import java.awt.Color;
import java.util.Random;

import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.Resource;
import com.edd.collision.CollisionResult;
import com.edd.collision.SinglePlayerCollisionEngine;
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
	
	/***
	 * AI is an artificially intelligent character. Its goals include growing stronger and killing the player.
	 * @param x
	 * @param y
	 * @param driver
	 * @param generator
	 */
	public AI(int x, int y, MainApplication driver, AIGenerator generator) {
		rand = new Random();
		
		basicPreConstructor(x,y,driver);
		basicCharacterConstructor(new SinglePlayerCollisionEngine(this,driver),80+rand.nextInt(31),10,5,50,"");
		
		
		sprite = new GOval(x + size / 2, y + size / 2, size, size);
		((GOval)sprite).setColor(new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));
		((GOval)sprite).setFilled(true);
		
		chooseNewRandomDirection(); // establishing initial direction
		
		basicPostConstructor();
	}
	
	@Override
	public void tick(){
		if(playerIsInDetectionRange()){
			chooseNewDirectionTowardActor(driver.player);
		} else {
			directionResetTicks++;
			if(directionResetTicks >= DIRECTION_RESET_DELAY*driver.TICKS_PER_SECOND){
				Resource nearestResource = getNearestResourceInRange();
				if(nearestResource != null){
					chooseNewDirectionTowardActor(nearestResource);
				} else {
					chooseNewRandomDirection();
				}
				directionResetTicks = 0;
			}
		}
		
		if(direction == null){
			System.out.println("WARNING: Having to use fail-safe random direction! Should never be called! Something's wrong?");
			chooseNewRandomDirection();
		}
		
		setVelocityFromDirection();
		CollisionResult result = attemptMove(xVelocity,yVelocity);
		if(!result.xCollides && !result.yCollides){
			chooseNewRandomDirection();
		}
	}
	
	private boolean playerIsInDetectionRange(){
		return(Math.abs(driver.player.getX()-x) <= DETECTION_RANGE_X && Math.abs(driver.player.getY()-y) <= DETECTION_RANGE_Y);
	}
	
	private Resource getNearestResourceInRange(){
		return null;
	}
	
	private void chooseNewDirectionTowardActor(BaseActor actor){
		Rate xRate = Rate.INCREASING;
		Rate yRate = Rate.INCREASING;
		
		if(actor.getX() > x)
			xRate = Rate.INCREASING;
		if(actor.getX() < x)
			xRate = Rate.DECREASING;
		if(actor.getX() == x)
			xRate = Rate.NEUTRAL;
		
		if(actor.getY() > y)
			yRate = Rate.INCREASING;
		if(actor.getY() < y)
			yRate = Rate.DECREASING;
		if(actor.getY() == y)
			yRate = Rate.NEUTRAL;
		
		direction = Direction.getDirectionFromRates(xRate,yRate);
	}
	
	private void chooseNewRandomDirection(){
		Direction[] directions = Direction.values();
		direction = directions[rand.nextInt(directions.length)];
	}
	
	private void setVelocityFromDirection(){
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
