package com.edd.character;

import java.util.ArrayList;

import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.GameType;
import com.edd.collision.BaseCollisionEngine;
import com.edd.collision.CollisionUtil;
import com.edd.collision.MultiPlayerCollisionEngine;
import com.edd.collision.SinglePlayerCollisionEngine;
import com.edd.osvaldo.MainApplication;

import acm.graphics.GImage;

public class Saw extends Character {
	
	private Character owner;
	private boolean active;
	
	public Saw(GameType gameType, Character owner, MainApplication driver){
		this.owner = owner;
		BaseCollisionEngine collisionEngine = gameType == GameType.MULTIPLAYER ? new SinglePlayerCollisionEngine(this,driver) : new MultiPlayerCollisionEngine(this,driver);
		basicPreConstructor((int)owner.getX()+(int)owner.getWidth()/2-owner.getRange()/2,(int)owner.getY()+(int)owner.getHeight()-owner.getRange()/2,driver);
		basicCharacterConstructor(collisionEngine,gameType,owner.getRange(),0,0,0,0,null);
		basicPostConstructor("com/edd/character/saw.gif");
		this.gameType = gameType;
		adjust();
	}
	
	public Character getOwner(){ return owner; }
	
	public void start(){
		active = true;
		driver.add(sprite);
	}
	
	public void stop(){
		active = false;
		driver.remove(sprite);
	}
	
	public void adjust(){
		int range = owner.getRange();
		((GImage)sprite).setBounds(owner.getSprite().getX()+owner.getWidth()/2-range/2,owner.getSprite().getY()+owner.getHeight()/2-range/2,range,range);
	}
	
	@Override
	public void tick(){
		if(active){
			ArrayList<Character> characters = new ArrayList<Character>();
			
			switch(gameType){
				case SINGLEPLAYER:
					for(BaseActor character : driver.AI_GEN.getActors())
						characters.add((Character)character);
					characters.add(driver.player);
					break;
				case MULTIPLAYER:
					// TODO: Sam, implement collecting of characters in multiplayer here
					break;
			}
			
			for(Character character : characters){
				if(CollisionUtil.overlaps(this, character))
					character.onHit(owner);
			}
		}
	}
}
