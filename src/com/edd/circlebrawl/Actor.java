package com.edd.circlebrawl;

/*
 * This is going to be the interface for the base Actor.
 * Interfaces can't have non-constant or non static member
 * variables so I'm going to just use this interface to 
 * guarantee some methods that every that implements 
 * Actor is going to be required to have.*/
import acm.graphics.GImage;

public interface Actor {
	// Setters
	public void setX(double x);

	public void setY(double y);

	public void setSprite(GImage sprite);

	// Getters
	public double getX();

	public double getY();

	public GImage getSprite();

	// Other
	public boolean collidesWith(BaseActor anotherActor);

	public void remove();

	public void setupSprite(String spriteFile);
}
