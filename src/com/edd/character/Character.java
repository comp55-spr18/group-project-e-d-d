package com.edd.character;

import java.awt.Color;
import java.util.ArrayList;

import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.GameType;
import com.edd.circlebrawl.MainApplication;
import com.edd.circlebrawl.MultiplayerSam_Test;
import com.edd.collision.BaseCollisionEngine;
import com.edd.collision.CollisionBox;
import com.edd.collision.CollisionResult;

import acm.graphics.GOval;

public abstract class Character extends BaseActor {

	// ALL NUMBERS BELOW CAN BE CHANGED AT WILL
	
	protected final int MAX_SIZE = 300;
	protected final int MAX_DEFENSE = 50;
	protected final int MAX_SPEED = 15;
	protected final int MAX_STRENGTH = 100;
	protected final double MAX_ATTACK_SPEED = 3;
	
	protected final int MIN_SIZE = 30; // death below this point
	protected final int MIN_DEFENSE = -50;
	protected final int MIN_SPEED = 1;
	protected final int MIN_STRENGTH = 10; 
	protected final double MIN_ATTACK_SPEED = .5;
	
	protected final int ATTACK_DURATION = MainApplication.TICKS_PER_SECOND/2;
	private final int HIT_VELOCITY_REDUCTION_PER_TICK = 10;
	
	// ALL NUMBERS ABOVE CAN BE CHANGED AT WILL
	
	protected BaseCollisionEngine collisionEngine;
	
	protected int size; // how large this Character is; also indicative of health
	protected int defense; // how much damage this Character can take
	protected int speed; // how fast this Character can move
	protected int strength; // how much damage this Character can deal
	protected int range; // how far the character can hit
	protected double attackSpeed; // how fast the character can attack (higher = slower)
	
	protected Saw saw;
	protected ArrayList<AttackOrb> attackOrbs;
	protected ArrayList<AttackOrb> attackOrbRemovalList;
	
	protected boolean dead = false;
	
	private boolean isAttacking, attackedRecently;
	private int attackTicks;
	private int hitVelocityX, hitVelocityY;
	
	protected void basicCharacterConstructor(BaseCollisionEngine engine, GameType gameType, int size, int defense, int speed, int strength, double attackSpeed, Color color){
		this.collisionEngine = engine;
		this.size = size;
		this.defense = defense;
		this.speed = speed;
		this.strength = strength;
		this.attackSpeed = attackSpeed;
		this.sprite = new GOval(x, y, size, size);
		this.attackOrbs = new ArrayList<AttackOrb>();
		this.attackOrbRemovalList = new ArrayList<AttackOrb>();
		((GOval)sprite).setColor(color);
		((GOval)sprite).setFilled(true);
		saw = new Saw(this,driver);
		
		basicPostConstructor();
		adjustSaw();
	}
	
	//modifiers
	public int modifySize(int modifyValue) {
		if(size+modifyValue > MAX_SIZE)
			modifyValue = MAX_SIZE-size;
		if(size+modifyValue < MIN_SIZE){
			onDeath();
			return 0;
		}
		
		size += modifyValue;
		adjustSaw();
		resize(modifyValue);

		// making AttackOrbs' size scale
		for(AttackOrb attackOrb : attackOrbs){
			int attackOrbModifyValue = (int)(modifyValue*AttackOrb.PERCENT_OF_CHARACTER);
			attackOrb.modifySize(attackOrbModifyValue);
			attackOrb.move(modifyValue/2, -attackOrbModifyValue);
		}
		
		return modifyValue;
	}
	
	protected void adjustSaw(){
		range = (int)(size*1.5);
		saw.adjust();
	}
	
	public int modifyDefense(int modifyValue) {
		if(defense+modifyValue > MAX_DEFENSE)
			modifyValue = MAX_DEFENSE-defense;
		if(defense+modifyValue < MIN_DEFENSE)
			modifyValue = -(defense-MIN_DEFENSE);
		
		defense += modifyValue;
		
		return modifyValue;
	}
	
	public int modifySpeed(int modifyValue) {
		if(speed+modifyValue > MAX_SPEED)
			modifyValue = MAX_SPEED-speed;
		if(speed+modifyValue < MIN_SPEED)
			modifyValue = -(speed-MIN_SPEED);
		
		speed += modifyValue;
		
		// making AttackOrbs' s scale
		for(AttackOrb attackOrb : attackOrbs)
			attackOrb.modifySpeed((int)(modifyValue*AttackOrb.PERCENT_OF_CHARACTER));
		
		return modifyValue;
	}
	
	public int modifyStrength(int modifyValue) {
		if(strength+modifyValue > MAX_STRENGTH)
			modifyValue = MAX_STRENGTH-strength;
		if(strength+modifyValue < MIN_STRENGTH)
			modifyValue = -(strength-MIN_STRENGTH);
		
		strength += modifyValue;
		
		// making AttackOrbs' strength scale
		for(AttackOrb attackOrb : attackOrbs)
			attackOrb.modifyStrength((int)(modifyValue*AttackOrb.PERCENT_OF_CHARACTER));
		
		return modifyValue;
	}
	
	public double modifyAttackSpeed(double modifyValue) {
		if(attackSpeed+modifyValue > MAX_SPEED)
			modifyValue = MAX_ATTACK_SPEED-attackSpeed;
		if(speed+modifyValue < MIN_SPEED)
			modifyValue = -(attackSpeed-MIN_ATTACK_SPEED);
		
		attackSpeed += modifyValue;
		
		return modifyValue;
	}
	
	private void resize(int modifyValue){
		driver.remove(sprite);
		
		GOval oldSprite = (GOval)sprite;

		sprite = new GOval(sprite.getX(),sprite.getY(),getWidth()+modifyValue,getHeight()+modifyValue);

		((GOval)sprite).setFilled(true);
		((GOval)sprite).setFillColor(oldSprite.getFillColor());
		
		driver.add(sprite);
		
		//driver.remove(saw);
		//saw.setBounds(x - sprite.getWidth() * 2, y, (saw.getWidth()+modifyValue*1.5), saw.getHeight()+modifyValue*1.5);
		//saw.move(modifyValue/1.5, modifyValue/1.5);
		
		constructCollisionBox();
	}
	
	//getters
	public int getSize() { return size; }
	public int getDefense() { return defense; }
	public int getSpeed() { return speed; }
	public int getStrength() { return strength; }
	public double getAttackSpeed(){ return attackSpeed; }
	public int getRange(){ return range; }
	public Saw getSaw() { return saw; }
	public BaseCollisionEngine getCollisionEngine(){ return collisionEngine; }
	
	public ArrayList<AttackOrb> getAttackOrbs(){ return attackOrbs; }
	public AttackOrb spawnAttackOrb(GameType gameType){ return new AttackOrb(gameType,this,driver); }
	public void despawnAttackOrb(AttackOrb attackOrbToRemove){ attackOrbRemovalList.add(attackOrbToRemove); }
	
	protected void attemptAttack(){
		if(!isAttacking && !attackedRecently){
			saw.start();
			isAttacking = true;
		}
	}
	
	private void stopAttacking(){
		saw.stop();
	}
	
	public void onHit(Character actor){
		if(actor.x > x){
			hitVelocityX = -actor.getStrength()*2;
		} else {
			hitVelocityX = actor.getStrength()*2;
		}
		
		if(actor.y > y){
			hitVelocityY = -actor.getStrength()*2;
		} else {
			hitVelocityY = actor.getStrength()*2;
		}

		modifySize(-actor.getStrength()/2);
	}
	
	public void onDeath(){
		dead = true;
		
		for(AttackOrb attackOrb : attackOrbs){
			attackOrbs.remove(attackOrb);
			attackOrb.remove();
		}
		
		if(this instanceof AI){
			remove();
			saw.remove();
			driver.AI_GEN.addToRemoveList(this);
		}
		if(this instanceof Player){
			//Player p = (Player)this;
			//p.respawn();
		}
	}
	
	/***
	 * Attempts to move the character. Also handles collision detection.
	 * @param x the x val to move the character
	 * @param y the y val to move the character
	 * @return if the movement was successful
	 */
	public CollisionResult attemptMove(int x, int y){
		if(collisionEngine != null){
			return collisionEngine.move(x, y);
		} else {
			move(x,y);
			return new CollisionResult(false,false);
		}
	}
	
	/***
	 * Moves the character.
	 * @param x the x val to move the character (including saw and attack orbs)
	 * @param y the y val to move the character (including saw and attack orbs)
	 */
	public void move(int x, int y) {
		
		if(shouldMoveSprite())
			sprite.move(x,y);
		this.x += x;
		this.y += y;
	
		if(attackOrbs != null)
			for(AttackOrb attackOrb : attackOrbs)
				attackOrb.move(x, y);
		
		constructCollisionBox();
	}
	
	/***
	 * Moves the character in a polar direction.
	 * @param distance the distance to move the character
	 * @param angle the angle to move the character
	 */
	public void movePolar(int distance, double angle) {
		
		int xChange = 0, yChange = 0;
		// TODO: Implement xChange and yChange
		
		if(shouldMoveSprite())
			sprite.movePolar(distance,angle);
		this.x += xChange;
		this.y += yChange;
		
		if(attackOrbs != null)
			for(AttackOrb attackOrb : attackOrbs)
				attackOrb.movePolar(distance, angle);
		
		constructCollisionBox();
	}
	
	private boolean shouldMoveSprite(){
		if(this instanceof Player){
			Player p = (Player)this;
			if(driver instanceof MultiplayerSam_Test){
				MultiplayerSam_Test multiDriver = (MultiplayerSam_Test)driver;
				return !multiDriver.isClient(p.name);
			}
			return false;
		}
		return true;
	}
	
	@Override
	public void tick(){
		if(!dead){
			
			if(saw != null)
				saw.tick();
			
			if(attackOrbs != null && attackOrbRemovalList != null){
				for(AttackOrb attackOrb : attackOrbs)
					attackOrb.tick();
				for(AttackOrb attackOrb : attackOrbRemovalList){
					attackOrbs.remove(attackOrb);
					attackOrb.remove();
				}
				attackOrbRemovalList.clear();
			}
			
			if(isAttacking || attackedRecently){
				attackTicks++;
				if(attackedRecently && attackTicks >= (MAX_ATTACK_SPEED-attackSpeed+.1)*MainApplication.TICKS_PER_SECOND){
					attackedRecently = false;
					attackTicks = 0;
				}
					
				if(isAttacking && attackTicks >= ATTACK_DURATION){
					isAttacking = false;
					attackedRecently = true;
					stopAttacking();
					attackTicks = 0;
				}
			}
			
			if(hitVelocityX != 0 || hitVelocityY != 0){
				CollisionResult cr = attemptMove(hitVelocityX,hitVelocityY);
				
				if(cr.xCollides)
					hitVelocityX = 0;
				if(cr.yCollides)
					hitVelocityY = 0;
				
				if(hitVelocityX != 0){
					if(hitVelocityX > 0){
						hitVelocityX -= HIT_VELOCITY_REDUCTION_PER_TICK;
						if(hitVelocityX < 0)
							hitVelocityX = 0;
					}
					if(hitVelocityX < 0)
						hitVelocityX += HIT_VELOCITY_REDUCTION_PER_TICK;
						if(hitVelocityX > 0)
							hitVelocityX = 0;
				}
				
				if(hitVelocityY != 0){
					if(hitVelocityY > 0)
						hitVelocityY -= HIT_VELOCITY_REDUCTION_PER_TICK;
						if(hitVelocityY < 0)
							hitVelocityY = 0;
					if(hitVelocityY < 0)
						hitVelocityY += HIT_VELOCITY_REDUCTION_PER_TICK;
						if(hitVelocityY > 0)
							hitVelocityY = 0;
				}
			}
		}
	}
	
	@Override
	public void constructCollisionBox(){
		collisionBox = new CollisionBox((int)(x+getWidth()/10),(int)(y+getHeight()/10),(int)(x+getWidth()/10*9),(int)(y+getHeight()/10*9));
	}
	
}
