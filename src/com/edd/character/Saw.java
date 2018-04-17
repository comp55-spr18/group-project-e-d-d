package com.edd.character;

import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.MainApplication;
import com.edd.collision.CollisionUtil;

import acm.graphics.GImage;

public class Saw extends BaseActor {
	
	private Character owner;
	private boolean active;
	
	public Saw(Character owner, MainApplication driver){
		this.owner = owner;
		basicPreConstructor((int)owner.getX()+(int)owner.getWidth()/2-owner.getRange()/2,(int)owner.getY()+(int)owner.getHeight()-owner.getRange()/2,driver);
		basicPostConstructor("com/edd/character/saw.gif");
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
		x = (int)owner.getX()+(int)owner.getWidth()/2-owner.getRange()/2;
		y = (int)owner.getY()+(int)owner.getHeight()-owner.getRange()/2;
		constructCollisionBox();
	}
	
	@Override
	public void tick(){
		adjust();
		if(active){
			for(BaseActor character : accesser.getCharacters()){
				if(character != owner){
					if(CollisionUtil.overlaps(this, character)){
						((Character)character).onHit(owner);
						active = false;
					}
				}
			}
		}
	}
}
