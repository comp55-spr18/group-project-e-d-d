package com.edd.character;

import java.util.ArrayList;

import com.edd.circlebrawl.ActorAccesser;
import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.GameType;
import com.edd.circlebrawl.MainApplication;
import com.edd.collision.BaseCollisionEngine;
import com.edd.collision.CollisionUtil;
import com.edd.collision.MultiPlayerCollisionEngine;
import com.edd.collision.SinglePlayerCollisionEngine;

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
		remove(); // do not show unless attacking
	}
	
	public Character getOwner(){ return owner; }
	
	public void start(){
		active = true;
		driver.add(sprite);
		owner.getSprite().sendToFront();
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
			for(BaseActor character : accesser.getCharacters()){
				if(CollisionUtil.overlaps(this, character)){
					((Character)character).onHit(owner);
					stop();
				}
			}
		}
	}
}
