package com.edd.circlebrawl;
/*
 * This is going to be the interface for the base Actor.
 * Interfaces can't have non-constant or non static member
 * variables so I'm going to just use this interface to 
 * guarantee some methods that every that implements 
 * Actor is going to be required to have.*/
import acm.graphics.*;

public interface Actor 
{
	//getters
	public int getX();
	public int getY();
	public GImage getSprite();
	
	//setters
	public void setX(int x);
	public void setY(int y);
	public void setSprite(GImage sprite);
	
	//other
	public boolean collidesWith(BaseActor anotherActor);
	public void remove();
	public void setupSprite(String spriteFile);
}
