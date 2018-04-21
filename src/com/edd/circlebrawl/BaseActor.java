package com.edd.circlebrawl;

import java.util.Random;

import com.edd.character.AttackOrb;
import com.edd.character.Character;
import com.edd.character.Player;
import com.edd.character.Saw;
import com.edd.collision.CollisionBox;
import com.edd.collision.CollisionEngine;
import com.edd.collision.CollisionUtil;
import com.edd.map.MapBuilder;

import acm.graphics.GImage;
import acm.graphics.GObject;

public abstract class BaseActor implements Actor, Tick {
	protected GObject sprite;
	protected MainApplication driver;
	protected double x;
	protected double y;
	protected GameType gameType;
	protected CollisionBox collisionBox;
	protected ActorAccesser accesser;

	public void basicPreConstructor(int x, int y, MainApplication driver) {
		this.x = x;
		this.y = y;
		this.driver = driver;
		this.accesser = driver.actorAccesser;
	}

	// use this for auto generation of location
	public void basicPreConstructor(GameType gameType, MainApplication driver) {
		this.gameType = gameType;
		this.driver = driver;
		this.accesser = driver.actorAccesser;
	}

	public void basicPostConstructor() {
		if (sprite != null) {
			if (gameType != null)
				setRandomLocation();
			setupSprite(sprite);
			applyTranslation();
			constructCollisionBox();
		}
	}

	public void basicPostConstructor(String spriteFile) {
		sprite = new GImage(spriteFile);
		if (gameType != null)
			setRandomLocation();
		setupSprite(sprite);
		applyTranslation();
		constructCollisionBox();
	}

	private void applyTranslation() {
		if(driver instanceof MultiplayerDriver){
			MultiplayerDriver multiDriver = (MultiplayerDriver)driver;
			Player p = multiDriver.getClientPlayer();
			if(isValidTranslation(p))
				sprite.move(p.getCam().getTotalTranslationX(),p.getCam().getTotalTranslationY());
			
		} else if(!(this instanceof Player)){
			Player p = driver.player;
			if(isValidTranslation(p))
				sprite.move(p.getCam().getTotalTranslationX(),p.getCam().getTotalTranslationY());
		}
	}
	
	private boolean isValidTranslation(Player p){
		if(p == null)
			return false;
		
		if(this instanceof Saw){
			Saw s = (Saw)this;
			if(s.getOwner() == p)
				return false;
		}
		
		if(this instanceof AttackOrb){
			AttackOrb a = (AttackOrb)this;
			if(a.getOwner() == p)
				return false;
		}
		
		return true;
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
		return CollisionUtil.overlaps(this, anotherActor);
	}

	/**
	 * Removes the sprite from the game
	 */
	public void remove() {
		driver.remove(sprite);
	};

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

	public double getWidth() {
		return sprite.getWidth();
	}

	public double getHeight() {
		return sprite.getHeight();
	}

	public void constructCollisionBox() {
		collisionBox = new CollisionBox((int) x, (int) y, (int) (x + getWidth()), (int) (y + getHeight()));
	}

	public int getTranslationX() {
		return (int) (x - sprite.getX());
	}

	public int getTranslationY() {
		return (int) (y - sprite.getY());
	}

	public void setRandomLocation() {
		Random rand = new Random();
		int tilesBufferX = driver.getTilesBufferX();
		int tilesBufferY = driver.getTilesBufferX();
		int mapWidth = driver.getMapWidth();
		int mapHeight = driver.getMapHeight();
		int tileWidth = MapBuilder.TILE_WIDTH;
		int tileHeight = MapBuilder.TILE_HEIGHT;
		int minX = tilesBufferX * tileWidth + tileWidth*2;
		int minY = tilesBufferY * tileHeight + tileHeight*2;
		int maxX = mapWidth - (int) getWidth() - tilesBufferX * tileWidth - tileWidth*2;
		int maxY = mapHeight - (int) getHeight() - tilesBufferY * tileHeight - tileHeight*2;

		CollisionEngine tempEngine = null;

		if (this instanceof Character) {
			tempEngine = ((Character) this).getCollisionEngine();
		} else {
			tempEngine = new CollisionEngine(this,driver);
		}

		constructCollisionBox();
		while (tempEngine.collidesWithAnything()) {
			setX(rand.nextInt(maxX - minX + 1) + minX);
			setY(rand.nextInt(maxY - minY + 1) + minY);
			constructCollisionBox();
		}
	}

	public MainApplication getDriver() {
		return this.driver;
	}
}
