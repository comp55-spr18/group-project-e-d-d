package com.edd.character;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.GameType;
import com.edd.circlebrawl.MainApplication;
import com.edd.circlebrawl.Resource;
import com.edd.collision.BaseCollisionEngine;
import com.edd.collision.CollisionResult;
import com.edd.collision.MultiPlayerCollisionEngine;
import com.edd.collision.SinglePlayerCollisionEngine;
import com.edd.generator.AIGenerator;

public class AI extends Character {

	private static final int DETECTION_RANGE_X = 400;
	private static final int DETECTION_RANGE_Y = 200;
	private static final double DIRECTION_RESET_DELAY = .5; // time in seconds before AI chooses new direction

	private final int BASE_DEFENSE = 8;
	private final int BASE_SPEED = 4;
	private final int BASE_STRENGTH = 30;
	private final double BASE_ATTACK_SPEED = 2;
	
	private AIGenerator generator;
	private Random rand;
	
	private int directionResetTicks;
	private Direction direction;
	private int xVelocity;
	private int yVelocity;
	
	/***
	 * AI is an artificially intelligent character. Its goals include growing stronger and killing the player.
	 * @param x the x for the AI to start at
	 * @param y the y for the AI to start at
	 * @param driver the main driver of the game
	 * @param generator the thing generating the AI
	 */
	public AI(int x, int y, GameType gameType, MainApplication driver, AIGenerator generator) {
		basicPreConstructor(x,y,driver);
		basicAIConstructor(gameType,driver,generator);
	}
	/***
	 * AI is an artificially intelligent character. Its goals include growing stronger and killing the player.
	 * @param gameType the type of game (SinglePlayer/MultiPlayer)
	 * @param driver the main driver of the game
	 * @param generator the thing generating the AI
	 */
	public AI(GameType gameType, MainApplication driver, AIGenerator generator) {
		basicPreConstructor(gameType,driver);
		basicAIConstructor(gameType,driver,generator);
	}
	
	private void basicAIConstructor(GameType gameType, MainApplication driver, AIGenerator generator){
		rand = new Random();
		BaseCollisionEngine collisionEngine = gameType == GameType.SINGLEPLAYER ? new SinglePlayerCollisionEngine(this,driver) : new MultiPlayerCollisionEngine(this,driver);
		basicCharacterConstructor(collisionEngine,gameType,80+rand.nextInt(31),BASE_DEFENSE,BASE_SPEED,BASE_STRENGTH,BASE_ATTACK_SPEED,new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));
		basicPostConstructor();
		chooseNewRandomDirection(); // establishing initial direction
	}
	
	@Override
	public void tick(){
		if(!dead){
			super.tick();
			
			Player nearestPlayerInRange = (Player)getNearestActorInRange(accesser.getPlayers());
			AI nearestAIInRange = (AI)getNearestActorInRange(accesser.getAIs());
			
			if(nearestPlayerInRange != null){
				chooseNewDirectionTowardActor(nearestPlayerInRange);
				if(characterIsInAttackRange(nearestPlayerInRange))
					attemptAttack();
			} else if(nearestAIInRange != null){
				chooseNewDirectionTowardActor(nearestAIInRange);
				if(characterIsInAttackRange(nearestAIInRange))
					attemptAttack();
			} else {
				directionResetTicks++;
				if(directionResetTicks >= DIRECTION_RESET_DELAY*MainApplication.TICKS_PER_SECOND){
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
			if(result.xCollides && result.yCollides){
				chooseNewRandomDirection();
			}
	}
	}
	
	private boolean characterIsInAttackRange(Character character){
		int distance = (int)Math.abs(x-character.getX());
		return distance <= range;
	}
	
	private BaseActor getNearestActorInRange(ArrayList<BaseActor> actors){
		
		ArrayList<BaseActor> parsedActors = new ArrayList<BaseActor>();
		for(BaseActor actor : actors)
			if(actor != this)
				parsedActors.add(actor);
		
		BaseActor nearestActor = null;
		int minDist = 0;
		
		for(BaseActor actor : parsedActors){
			int distance = (int)Math.abs(x-actor.getX());
			if(distance < minDist || minDist == 0){
				minDist = distance;
				nearestActor = (BaseActor)actor;
			}
		}
		
		return nearestActor;
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
