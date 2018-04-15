package com.edd.collision;

import java.util.ArrayList;

import com.edd.character.Character;
import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.Item;
import com.edd.circlebrawl.Resource;
import com.edd.osvaldo.MainApplication;
import com.edd.powerup.PowerUp;

public abstract class BaseCollisionEngine {

	protected BaseActor actor;
	protected MainApplication driver;
	
	public CollisionResult move(int x, int y) {
		if(actor instanceof Character){
			Character character = (Character)actor;
			ArrayList<Item> items = collidesWithItems(x,y);
			CollisionResult boundaryOverlap = collidesWithBoundaries(x,y);
			CollisionResult obstacleOverlap = collidesWithObstacles(x,y);
			CollisionResult characterOverlap = collidesWithCharacters(x,y);

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
	
	// when implementing, set return to call collidesWithActors() and pass in the proper list
	protected abstract CollisionResult collidesWithBoundaries(int x, int y);
	
	// when implementing, set return to call collidesWithActors() and pass in the proper list
	protected abstract CollisionResult collidesWithObstacles(int x, int y);

	// when implementing, set return to call collidesWithActors() and pass in the proper list
	protected abstract CollisionResult collidesWithCharacters(int x, int y);
	
	// when implementing, set return to call collidesWithItems() and pass in the proper lists
	protected abstract ArrayList<Item> collidesWithItems(int x, int y);

	// returns true if the character is colliding with any existing actor
	public abstract boolean collidesWithAnything();
	
	// removes the item from active item lists
	protected abstract void cleanUpItem(Item item);
	
	protected boolean collidesWithAnything(ArrayList<BaseActor> powerUps, ArrayList<BaseActor> resources, ArrayList<BaseActor> AIs, ArrayList<BaseActor> obstacles, ArrayList<BaseActor> players){
		for(BaseActor powerUp : powerUps)
			if(CollisionUtil.overlaps(actor, powerUp))
				return true;
		for(BaseActor resource : resources)
			if(CollisionUtil.overlaps(actor, resource))
				return true;
		for(BaseActor AI : AIs)
			if(CollisionUtil.overlaps(actor, AI))
				return true;
		for(BaseActor obstacle : obstacles)
			if(CollisionUtil.overlaps(actor, obstacle))
				return true;
		for(BaseActor player : players)
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
	
	protected ArrayList<Item> collidesWithItems(ArrayList<BaseActor> powerUps, ArrayList<BaseActor> items, int x, int y){
		ArrayList<Item> retVal = new ArrayList<Item>();
		for(BaseActor powerUp : powerUps){
			if(powerUp.collidesWith(actor))
				retVal.add((PowerUp)powerUp);
		}
		for(BaseActor resource : items){
			if(resource.collidesWith(actor))
				retVal.add((Resource)resource);
		}
		return retVal;
	}
}
