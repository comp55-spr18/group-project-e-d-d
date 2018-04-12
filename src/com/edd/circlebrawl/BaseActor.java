package com.edd.circlebrawl;

import com.edd.collision.CollisionBox;
import com.edd.collision.CollisionUtil;
import com.edd.osvaldo.MainApplication;

import acm.graphics.GImage;
import acm.graphics.GObject;

public abstract class BaseActor implements Actor, Tick {
	protected GObject sprite;
	protected MainApplication driver;
	protected double x;
	protected double y;
	protected CollisionBox collisionBox;

	public void basicPreConstructor(int x, int y, MainApplication driver){
		this.x = x;
		this.y = y;
		this.driver = driver;
	}
	
	public void basicPostConstructor(){
		if(sprite != null){
			setupSprite(sprite);
			constructCollisionBox();
		}
	}
	
	public void basicPostConstructor(String spriteFile){
		setupSprite(spriteFile);
		constructCollisionBox();
	}
	
	// Setters for sprite
	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setSprite(GImage sprite) {
		this.sprite = sprite;
	}

	// Getters for sprite
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public GObject getSprite() {
		return this.sprite;
	}
	
	public CollisionBox getCollisionBox() {
		return collisionBox;
	}

	/**
	 * Determines if Actor intersects argument Actor
	 * 
	 * @param anotherActor
	 *            the Actor that we want to check if intersects current Actor
	 * @return true/false indicating if the two Actors are intersecting
	 */
	public boolean collidesWith(BaseActor anotherActor) {
		// return (this.getSprite().contains(anotherActor.getX() +
		// anotherActor.getSprite().getWidth() / 2,
		// anotherActor.getY() + anotherActor.getSprite().getHeight() / 2));
		/*return (this.getSprite().contains(anotherActor.getSprite().getX(), anotherActor.getSprite().getY())
				|| this.getSprite().contains(anotherActor.getSprite().getX(),
						anotherActor.getSprite().getY() + anotherActor.getSprite().getHeight() / 2)
				|| this.getSprite().contains(anotherActor.getSprite().getX(),
						anotherActor.getSprite().getY() + anotherActor.getSprite().getHeight())
				|| this.getSprite().contains(anotherActor.getSprite().getX() + anotherActor.getSprite().getWidth() / 2,
						anotherActor.getSprite().getY())
				|| this.getSprite().contains(anotherActor.getSprite().getX() + anotherActor.getSprite().getWidth() / 2,
						anotherActor.getSprite().getY() + anotherActor.getSprite().getHeight())
				|| this.getSprite().contains(anotherActor.getSprite().getX() + anotherActor.getSprite().getWidth(),
						anotherActor.getSprite().getY())
				|| this.getSprite().contains(anotherActor.getSprite().getX() + anotherActor.getSprite().getWidth(),
						anotherActor.getSprite().getY() + anotherActor.getSprite().getHeight() / 2)
				|| this.getSprite().contains(anotherActor.getSprite().getX() + anotherActor.getSprite().getWidth(),
						anotherActor.getSprite().getY() + anotherActor.getSprite().getHeight()));*/
		return CollisionUtil.overlaps(this, anotherActor);
	}

	/**
	 * Removes the sprite from the game
	 */
	public void remove() {
		driver.remove(sprite);
	};

	/**
	 * Initializes a GImage objec, specifically the sprite
	 * 
	 * @param anotherActor
	 *            the Actor that we want to check if intersects current Actor
	 * @return true/false indicating if the two Actors are intersecting
	 */
	public void setupSprite(String spriteFile) {
		sprite = new GImage(spriteFile);

		sprite.setLocation(x, y);
		driver.bringPlayersToFront();
		driver.add(sprite);
	}

	public void setupSprite(GObject sprite) {
		this.sprite = sprite;

		sprite.setLocation(x, y);

		driver.add(sprite);
	}
	
	public void addSprite() {
		driver.add(sprite);
	}

	@Override
	public void tick() {
	}
	
	public double getWidth(){
		return sprite.getWidth();
	}
	
	public double getHeight(){
		return sprite.getHeight();
	}
	
	public void constructCollisionBox(){
		collisionBox = new CollisionBox((int)x,(int)y,(int)(x+getWidth()),(int)(y+getHeight()));
	}

}
