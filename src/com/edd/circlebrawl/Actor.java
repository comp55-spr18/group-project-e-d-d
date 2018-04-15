package com.edd.circlebrawl;

import com.edd.collision.CollisionBox;

/*
 * This is going to be the interface for the base Actor.
 * Interfaces can't have non-constant or non static member
 * variables so I'm going to just use this interface to 
 * guarantee some methods that every that implements 
 * Actor is going to be required to have.*/
import acm.graphics.GImage;
import acm.graphics.GObject;

public interface Actor {
	
	public void basicPreConstructor(int x, int y, MainApplication driver); // ALWAYS CALL IN BEGINNING OF CONSTRUCTORS
	public void basicPreConstructor(GameType gameType, MainApplication driver); // ALWAYS CALL IN BEGINNING OF CONSTRUCTORS
	public void basicPostConstructor(); // ALWAYS CALL IN END OF CONSTRUCTORS
	public void basicPostConstructor(String spriteFile); // ALWAYS CALL IN END OF CONSTRUCTORS
	
	// Setters
	public void setX(double x);
	public void setY(double y);
	public void setSprite(GImage sprite);

	// Getters
	public double getX();
	public double getY();
	public GObject getSprite();
	public CollisionBox getCollisionBox();

	// Other
	public boolean collidesWith(BaseActor anotherActor);

	public void remove();
	
	public void setupSprite(GObject sprite);
	
	public double getWidth();
	public double getHeight();
	
	public void constructCollisionBox();	
	
	public int getTranslationX();
	public int getTranslationY();
	
	public void setRandomLocation();
}
