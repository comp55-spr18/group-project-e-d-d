package com.edd.circlebrawl;

import java.util.Random;

import com.edd.character.AttackOrb;
import com.edd.character.Character;
import com.edd.character.Player;
import com.edd.collision.BaseCollisionEngine;
import com.edd.collision.CollisionBox;
import com.edd.collision.CollisionUtil;
import com.edd.collision.MultiPlayerCollisionEngine;
import com.edd.collision.SinglePlayerCollisionEngine;
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

	public void basicPreConstructor(int x, int y, MainApplication driver){
		this.x = x;
		this.y = y;
		this.driver = driver;
		this.accesser = driver.actorAccesser;
	}
	
	// use this for auto generation of location
	public void basicPreConstructor(GameType gameType, MainApplication driver){
		this.gameType = gameType;
		this.driver = driver;
		this.accesser = driver.actorAccesser;
	}
	
	public void basicPostConstructor(){
		if(sprite != null){
			if(gameType != null)
				setRandomLocation();
			setupSprite(sprite);
			applyTranslation();
			constructCollisionBox();
		}
	}
	
	public void basicPostConstructor(String spriteFile){
		sprite = new GImage(spriteFile);
		if(gameType != null)
			setRandomLocation();
		setupSprite(sprite);
		applyTranslation();
		constructCollisionBox();
	}
	
	private void applyTranslation(){
		if(this instanceof AttackOrb){
			AttackOrb attackOrb = (AttackOrb)this;
			if(attackOrb.getOwner() instanceof Player)
				return;
		}
		if(driver instanceof MultiplayerSam_Test){
			MultiplayerSam_Test multiDriver = (MultiplayerSam_Test)driver;
			Player p = multiDriver.getClientPlayer();
			if(p != null && this != p){
				sprite.move(p.getCam().getTotalTranslationX(),p.getCam().getTotalTranslationY());
			}
		} else if(!(this instanceof Player)){
			Player p = driver.player;
			sprite.move(p.getCam().getTotalTranslationX(),p.getCam().getTotalTranslationY());
		}
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
	
	public double getWidth(){
		return sprite.getWidth();
	}
	
	public double getHeight(){
		return sprite.getHeight();
	}
	
	public void constructCollisionBox(){
		collisionBox = new CollisionBox((int)x,(int)y,(int)(x+getWidth()),(int)(y+getHeight()));
	}

	public int getTranslationX(){
		return (int)(x - sprite.getX());
	}
	
	public int getTranslationY(){
		return (int)(y - sprite.getY());
	}
	
	public void setRandomLocation(){
		Random rand = new Random();
		int minX = MapBuilder.TILE_BUFFER_X*MapBuilder.TILE_WIDTH;;
		int minY = MapBuilder.TILE_BUFFER_Y*MapBuilder.TILE_HEIGHT;;
		int maxX = MainApplication.MAP_WIDTH-(int)getWidth()-MapBuilder.TILE_BUFFER_X*MapBuilder.TILE_WIDTH;
		int maxY = MainApplication.MAP_HEIGHT-(int)getHeight()-MapBuilder.TILE_BUFFER_Y*MapBuilder.TILE_HEIGHT;
		
		BaseCollisionEngine tempEngine = null;
		
		if(this instanceof Character){
			tempEngine = ((Character)this).getCollisionEngine();
		} else {
			switch(gameType){
				case SINGLEPLAYER:
					tempEngine = new SinglePlayerCollisionEngine(this,driver);
					break;
				case MULTIPLAYER:
					tempEngine = new MultiPlayerCollisionEngine(this,driver);
					break;
			}
		}

		constructCollisionBox();
		while(tempEngine.collidesWithAnything()){
			setX(rand.nextInt(maxX-minX+1)+minX);
			setY(rand.nextInt(maxY-minY+1)+minY);
			constructCollisionBox();
		}
	}
}
