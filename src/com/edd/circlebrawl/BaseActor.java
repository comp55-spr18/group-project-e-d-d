package com.edd.circlebrawl;

import acm.graphics.GImage;

public abstract class BaseActor implements Actor {
	protected GImage sprite;
	protected CircleBrawl driver;

	// Setters for sprite
	public void setX(double x) {
		this.sprite.setLocation(x, this.sprite.getY());
	}

	public void setY(double y) {
		this.sprite.setLocation(this.sprite.getX(), y);
	}

	public void setSprite(GImage sprite) {
		this.sprite = sprite;
	}

	// Getters for sprite
	public double getX() {
		return this.sprite.getX();
	}

	public double getY() {
		return this.sprite.getY();
	}

	public GImage getSprite() {
		return this.sprite;
	}

	public boolean collidesWith(BaseActor anotherActor) {
		return Math.sqrt(Math.pow((anotherActor.getX() - this.getX()), 2)
				+ Math.pow((anotherActor.getY() - this.getY()), 2)) == sprite.getWidth()
						+ anotherActor.getSprite().getWidth();
	} // TODO: Make function

	public void remove() {
		driver.remove(sprite);
	};

	// public void setupSprite(String spriteFile) {
	// sprite = new GImage(spriteFile);
	// sprite.setLocation(x + sprite.getWidth() / 2, y + sprite.getHeight() / 2); //
	// TODO: This is created assuming
	// // that the x and y of actors
	// // represent topleft corners.
	// driver.add(sprite);
	// }

}
