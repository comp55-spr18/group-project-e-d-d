package com.edd.circlebrawl;

public abstract class Character extends BaseActor {

	protected int size;
	protected int defense;
	
	protected void changeSize(int changeValue) {
		size += changeValue; // TODO: Implement proper size changes, include death detection!
	}
	
	//getters
	public int getSize() { return size; }
	public int getDefense() { return defense; }
	
}
