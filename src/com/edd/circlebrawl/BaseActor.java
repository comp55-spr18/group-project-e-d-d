package com.edd.circlebrawl;

import acm.graphics.GImage;
import acm.graphics.GObject;

public abstract class BaseActor implements Actor {
	protected GObject sprite;
	protected CircleBrawl driver;
	protected double x;
	protected double y;

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

	/**
	 * Determines if Actor intersects argument Actor
	 * 
	 * @param anotherActor
	 *            the Actor that we want to check if intersects current Actor
	 * @return true/false indicating if the two Actors are intersecting
	 */
	public boolean collidesWith(BaseActor anotherActor) {
		return this.getSprite().contains(anotherActor.getX() + anotherActor.getSprite().getWidth() / 2,
				anotherActor.getY() + anotherActor.getSprite().getHeight() / 2);
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

		sprite.setLocation(x + sprite.getWidth() / 2, y + sprite.getHeight() / 2);

		driver.add(sprite);
	}

	public void setupSprite(GObject sprite) {
		this.sprite = sprite;

		sprite.setLocation(x + sprite.getWidth() / 2, y + sprite.getHeight() / 2);

		driver.add(sprite);
	}

}
