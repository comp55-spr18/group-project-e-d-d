package com.edd.circlebrawl;

public abstract class Character extends BaseActor {

	protected int size;
	
	protected void changeSize(int changeValue) {
		size += changeValue; // TODO: Implement proper size changes, include death detection!
	}
	
}
