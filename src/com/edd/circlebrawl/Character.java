package com.edd.circlebrawl;

public abstract class Character extends BaseActor {

	protected int size; // how large this Character is; also indicative of health
	protected int defense; // how much damage this Character can take
	protected int speed; // how fast this Character can move
	protected int strength; // how much damage this Character can deal
	
	//modifiers
	public void modifySize(int modifyValue) {
		size += modifyValue; // TODO: Implement proper size changes, include death detection!
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
	
	//getters
	public int getSize() { return size; }
	public int getDefense() { return defense; }
	public int getSpeed() { return speed; }
	public int getStrength() { return strength; }
	
}
