package com.edd.character;

import java.awt.Color;
import java.util.ArrayList;

import com.edd.circlebrawl.BaseActor;
import com.edd.collision.BaseCollisionEngine;
import com.edd.collision.CollisionBox;
import com.edd.collision.CollisionResult;

import acm.graphics.GImage;
import acm.graphics.GOval;

public abstract class Character extends BaseActor {

	protected BaseCollisionEngine collisionEngine;
	
	protected int size; // how large this Character is; also indicative of health
	protected int defense; // how much damage this Character can take
	protected int speed; // how fast this Character can move
	protected int strength; // how much damage this Character can deal
	protected GImage saw = new GImage("com/edd/character/Buzzsaw2.gif");
	protected ArrayList<AttackOrb> attackOrbs;
	protected ArrayList<AttackOrb> attackOrbRemovalList;
	
	public int ATTACK_RING = 190;
	
	protected void basicCharacterConstructor(BaseCollisionEngine engine, int size, int defense, int speed, int strength, Color color){
		this.collisionEngine = engine;
		this.size = size;
		this.defense = defense;
		this.speed = speed;
		this.strength = strength;
		this.sprite = new GOval(x, y, size, size);
		this.attackOrbs = new ArrayList<AttackOrb>();
		this.attackOrbRemovalList = new ArrayList<AttackOrb>();
		
		((GOval)sprite).setColor(color);
		((GOval)sprite).setFilled(true);
		
		// temp
		saw.setBounds(((driver.WINDOW_WIDTH - sprite.getWidth()) /2) - ATTACK_RING/2, ((driver.WINDOW_HEIGHT - sprite.getHeight())/2) - ATTACK_RING/2 , ATTACK_RING, ATTACK_RING);
	}
	
	//modifiers
	public void modifySize(int modifyValue) {
		size += modifyValue; // TODO: Include death detection!
		resize(modifyValue);

		// making AttackOrbs' size scale
		for(AttackOrb attackOrb : attackOrbs){
			int attackOrbModifyValue = (int)(modifyValue*AttackOrb.PERCENT_OF_CHARACTER);
			attackOrb.modifySize(attackOrbModifyValue);
			attackOrb.move(modifyValue/2, -attackOrbModifyValue);
		}
	}
	
	public void modifyDefense(int modifyValue) {
		defense += modifyValue; // TODO: Maybe insert defense upper/lower limits?
	}
	
	public void modifySpeed(int modifyValue) {
		speed += modifyValue; //TODO: Maybe insert speed upper/lower limits?
		
		// making AttackOrbs' s scale
		for(AttackOrb attackOrb : attackOrbs)
			attackOrb.modifySpeed((int)(modifyValue*AttackOrb.PERCENT_OF_CHARACTER));
	}
	
	public void modifyStrength(int modifyValue) {
		strength += modifyValue; //TODO: Maybe insert strength upper/lower limits?
		
		// making AttackOrbs' strength scale
		for(AttackOrb attackOrb : attackOrbs)
			attackOrb.modifyStrength((int)(modifyValue*AttackOrb.PERCENT_OF_CHARACTER));
	}
	
	public void scaleSprite() {
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
	public GImage getSawSprite() { return saw; }
	
	public ArrayList<AttackOrb> getAttackOrbs(){ return attackOrbs; }
	public AttackOrb spawnAttackOrb(){ return new AttackOrb(this,driver); }
	public void despawnAttackOrb(AttackOrb attackOrbToRemove){ attackOrbRemovalList.add(attackOrbToRemove); }
	
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
		if(!(this instanceof Player))
			sprite.move(x,y);
		this.x += x;
		this.y += y;
		
		//saw.move(x, y);
		
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
		
		if(!(this instanceof Player))
			sprite.movePolar(distance,angle);
		this.x += xChange;
		this.y += yChange;
		
		saw.move(distance, angle);
		
		for(AttackOrb attackOrb : attackOrbs)
			attackOrb.movePolar(distance, angle);
		
		constructCollisionBox();
	}
	
	@Override
	public void tick(){
		for(AttackOrb attackOrb : attackOrbs)
			attackOrb.tick();
		for(AttackOrb attackOrb : attackOrbRemovalList){
			attackOrbs.remove(attackOrb);
			attackOrb.remove();
		}
		attackOrbRemovalList.clear();
	}
	
	@Override
	public void constructCollisionBox(){
		collisionBox = new CollisionBox((int)(x+getWidth()/10),(int)(y+getHeight()/10),(int)(x+getWidth()/10*9),(int)(y+getHeight()/10*9));
	}
	
}
