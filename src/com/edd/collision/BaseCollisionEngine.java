package com.edd.collision;

import java.util.ArrayList;

import com.edd.character.Character;
import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.Item;
import com.edd.circlebrawl.Resource;
import com.edd.osvaldo.MainApplication;
import com.edd.powerup.PowerUp;

public abstract class BaseCollisionEngine {

	protected Character character;
	protected MainApplication driver;
	
	public CollisionResult move(int x, int y) {
		
		ArrayList<Item> items = collidesWithItems(x,y);
		CollisionResult obstacleOverlap = collidesWithObstacles(x,y);
		CollisionResult characterOverlap = collidesWithOtherCharacters(x,y);
		
		boolean xOverlaps = obstacleOverlap.xCollides || characterOverlap.xCollides;
		boolean yOverlaps = obstacleOverlap.yCollides || characterOverlap.yCollides;

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
			character.move(x,y);
			return new CollisionResult(x!=0,y!=0);
		}
		
		return new CollisionResult(false,false);
	}

	// when implementing, set return to call collidesWithActors() and pass in the proper list
	protected abstract CollisionResult collidesWithObstacles(int x, int y);

	// when implementing, set return to call collidesWithActors() and pass in the proper list
	protected abstract CollisionResult collidesWithOtherCharacters(int x, int y);
	
	// when implementing, set return to call collidesWithItems() and pass in the proper lists
	protected abstract ArrayList<Item> collidesWithItems(int x, int y);
	
	// removes the item from active item lists
	protected abstract void cleanUpItem(Item item);
	
	protected CollisionResult collidesWithActors(ArrayList<BaseActor> actors, int x, int y) {
		boolean xOverlaps = false;
		boolean yOverlaps = false;
		
		for(BaseActor actor : actors){
			CollisionResult overlaps = CollisionUtil.overlaps(character, actor, x, y);
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
			if(powerUp.collidesWith(character))
				retVal.add((PowerUp)powerUp);
		}
		for(BaseActor resource : items){
			if(resource.collidesWith(character))
				retVal.add((Resource)resource);
		}
		return retVal;
	}
}
