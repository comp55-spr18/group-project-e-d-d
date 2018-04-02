package com.edd.circlebrawl;

import acm.graphics.GImage;
import acm.graphics.GObject;

public abstract class BaseActor implements Actor {
	protected GObject sprite;
	protected CircleBrawl driver;
	protected double x;
	protected double y;
	//protected Random seed = new Random();

	// Setters for sprite
	public void setX(double x) {
		this.x = x;
		// why?
		//this.sprite.setLocation(x, this.sprite.getY());
	}

	public void setY(double y) {
		this.y =y;
		// why?
		//this.sprite.setLocation(this.sprite.getX(), y);
	}

	public void setSprite(GImage sprite) {
		this.sprite = sprite;
	}

	// Getters for sprite
	public double getX() {
		return x;// why? -- this.sprite.getX();
	}

	public double getY() {
		return y;// why? -- this.sprite.getY();
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
		return Math.sqrt(Math.pow((anotherActor.getX() - this.getX()), 2)
				+ Math.pow((anotherActor.getY() - this.getY()), 2)) == sprite.getWidth()
						+ anotherActor.getSprite().getWidth();
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

		// NOTE: Bottom replaced the top after coord generator implementation
		sprite.setLocation(x + sprite.getWidth() / 2, y + sprite.getHeight() / 2);
		//sprite.setLocation(genRandCoord(), genRandCoord());

		driver.add(sprite);
	}
	
	public void setupSprite(GObject sprite) {
		this.sprite = sprite;

		// NOTE: Bottom replaced the top after coord generator implementation
		sprite.setLocation(x + sprite.getWidth() / 2, y + sprite.getHeight() / 2);
		//sprite.setLocation(genRandCoord(), genRandCoord());

		driver.add(sprite);
	}

	/**
	 * Generates a coordinate that abides by the given map dimensions
	 * 
	 * @return a single x or y coordinate
	 */
	// TODO: Determine if this is where we want to place the coordinate generator,
	// and java.util.* import
	// TODO: Implement the coordinate generator, should consider the bottom-right
	// most corner of sprite in calculation
	// TODO: Should consider if there is a pre-existing object in that space prior
	// to regenerating
	
	// There is no point in having this here. - Zach
	
	/*public double genRandCoord() {
		return 0.0;
	}*/

}
