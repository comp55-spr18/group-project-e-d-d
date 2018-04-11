package com.edd.character;

import com.edd.circlebrawl.BaseActor;

import acm.graphics.GImage;
import acm.graphics.GOval;

public abstract class Character extends BaseActor {

	protected int size; // how large this Character is; also indicative of health
	protected int defense; // how much damage this Character can take
	protected int speed; // how fast this Character can move
	protected int strength; // how much damage this Character can deal
	protected String name;
	protected GImage saw = new GImage("com/edd/character/Buzzsaw2.gif");
	public int ATTACK_RING = 190;
	
	//modifiers
	public void modifySize(int modifyValue) {
		size += modifyValue; // TODO: Implement proper size changes, include death detection!
		
		// stuff below is temporary to demonstrate resource effects
		driver.remove(sprite);
		GOval oldSprite = (GOval)sprite;
		sprite = new GOval(sprite.getX()-modifyValue/2,sprite.getY()-modifyValue/2,sprite.getSize().getWidth()+modifyValue,sprite.getSize().getHeight()+modifyValue);
		sprite.move(-oldSprite.getWidth() - sprite.getWidth(), oldSprite.getHeight());
		((GOval)sprite).setFilled(true);
		((GOval)sprite).setFillColor(oldSprite.getFillColor());
		setupSprite(sprite);
		driver.remove(saw);
		saw.setBounds((saw.getX()-modifyValue/2), (saw.getY()-modifyValue/2), (saw.getWidth()+modifyValue*1.5), saw.getHeight()+modifyValue*1.5);
		saw.move(modifyValue/1.5, modifyValue/1.5);
	}
	public void modifyDefense(int modifyValue) {
		defense += modifyValue; // TODO: Maybe insert defense limit?
	}
	public void modifySpeed(int modifyValue) {
		speed += modifyValue; //TODO: Maybe insert speed upper/lower limits?
	}
	public void modifyStrength(int modifyValue) {
		strength += modifyValue; //TODO: Maybe insert strength upper/lower limits?
	}
	
	public void scaleSprite() {
	}
	
	//getters
	public int getSize() { return size; }
	public int getDefense() { return defense; }
	public int getSpeed() { return speed; }
	public int getStrength() { return strength; }
	public String getName() { return name; }
	
	/***
	 * Moves the character. Also handles collision detection (or it will, at least).
	 * @param x the x val to move the character
	 * @param y the y val to move the character
	 * @return if the movement was successful
	 */
	public boolean move(double x, double y) {
		sprite.move(x,y);
		this.x += x;
		this.y += y;
		return true;
	}
	
	// Should not exist! Collision should be handled in move()!
	public void collision(){
		driver.POWERUP_GEN.checkCollision(this);
		driver.RESOURCE_GEN.checkCollision(this);
	}
	
}
