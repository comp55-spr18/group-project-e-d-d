package com.edd.circlebrawl;
import acm.graphics.*;
import acm.program.GraphicsProgram;

public abstract class BaseActor implements Actor {
	protected int x;
	protected int y;
	protected GImage sprite;
	protected CircleBrawl driver;
	
	//basic getters for the coordinates and for the sprite
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	public GImage getSprite() { return this.sprite; }
	
	//The consequent setters
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public void setSprite(GImage sprite) { this.sprite = sprite; }
	
	//other
	public boolean collidesWith(BaseActor anotherActor) { return false; } // TODO: Make function
	public void remove() { driver.remove(sprite); };
	
}
