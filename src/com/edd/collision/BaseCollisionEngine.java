package com.edd.collision;

import java.util.ArrayList;

import com.edd.character.Character;
import com.edd.circlebrawl.ActorAccesser;
import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.Item;
import com.edd.circlebrawl.MainApplication;
import com.edd.circlebrawl.Resource;
import com.edd.powerup.PowerUp;

public abstract class BaseCollisionEngine {

	protected BaseActor actor;
	protected MainApplication driver;
	protected ActorAccesser accesser;
	
	public CollisionResult move(int x, int y) {
		if(actor instanceof Character){
			Character character = (Character)actor;
			ArrayList<Item> items = collidesWithItems(accesser.getItems(),x,y);
			CollisionResult boundaryOverlap = collidesWithActors(accesser.getBoundaries(),x,y);
			CollisionResult obstacleOverlap = collidesWithActors(accesser.getObstacles(),x,y);
			CollisionResult characterOverlap = collidesWithActors(accesser.getCharacters(),x,y);

			boolean xOverlaps = obstacleOverlap.xCollides || characterOverlap.xCollides || boundaryOverlap.xCollides;
			boolean yOverlaps = obstacleOverlap.yCollides || characterOverlap.yCollides || boundaryOverlap.yCollides;
	
			if(items != null && !items.isEmpty())
				for(Item item : items){
					item.consume(character);
					cleanUpItem(item);
				}
			
			if(xOverlaps)
				x = 0;
			if(yOverlaps)
				y = 0;

			if(x != 0 || y != 0){
				moveActor(x,y);
				return new CollisionResult(x==0,y==0);
			}
		}
		
		return new CollisionResult(true,true); // do not move!
	}

	// handles actual movement
	protected abstract void moveActor(int x, int y);
	
	// removes the item from active item lists
	protected abstract void cleanUpItem(Item item);
	
	public boolean collidesWithAnything(){
		for(BaseActor powerUp : accesser.getPowerUps())
			if(CollisionUtil.overlaps(actor, powerUp))
				return true;
		for(BaseActor resource : accesser.getResources())
			if(CollisionUtil.overlaps(actor, resource))
				return true;
		for(BaseActor AI : accesser.getAIs())
			if(CollisionUtil.overlaps(actor, AI))
				return true;
		for(BaseActor obstacle : accesser.getObstacles())
			if(CollisionUtil.overlaps(actor, obstacle))
				return true;
		for(BaseActor player : accesser.getPlayers())
			if(CollisionUtil.overlaps(actor, player))
				return true;
		return false;
	}
	
	protected CollisionResult collidesWithActors(ArrayList<BaseActor> otherActors, int x, int y) {
		boolean xOverlaps = false;
		boolean yOverlaps = false;
		
		for(BaseActor otherActor : otherActors){
			CollisionResult overlaps = CollisionUtil.overlaps(actor, otherActor, x, y);
			if(overlaps.xCollides)
				xOverlaps = true;
			if(overlaps.yCollides)
				yOverlaps = true;
		}

		return new CollisionResult(xOverlaps,yOverlaps);
	}
	
	protected ArrayList<Item> collidesWithItems(ArrayList<BaseActor> items, int x, int y){
		ArrayList<Item> retVal = new ArrayList<Item>();
		for(BaseActor item : items){
			if(item.collidesWith(actor))
				retVal.add((Item)item);
		}
		return retVal;
	}
}
