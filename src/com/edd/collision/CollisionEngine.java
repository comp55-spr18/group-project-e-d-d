package com.edd.collision;

import java.util.ArrayList;

import com.edd.character.Character;
import com.edd.circlebrawl.ActorAccesser;
import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.Item;
import com.edd.circlebrawl.MainApplication;
import com.edd.circlebrawl.Resource;
import com.edd.powerup.PowerUp;

public class CollisionEngine {

	protected BaseActor actor;
	protected MainApplication driver;
	
	public CollisionEngine(BaseActor actor, MainApplication driver){
		this.actor = actor;
		this.driver = driver;
	}
	
	public CollisionResult move(int x, int y) {
		if(actor instanceof Character){
			Character character = (Character)actor;
			ArrayList<Item> items = collidesWithItems(driver.actorAccesser.getItems(),x,y);
			CollisionResult boundaryOverlap = collidesWithActors(driver.actorAccesser.getBoundaries(),x,y);
			CollisionResult obstacleOverlap = collidesWithActors(driver.actorAccesser.getObstacles(),x,y);
			CollisionResult characterOverlap = collidesWithActors(driver.actorAccesser.getCharacters(),x,y);

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
				character.move(x,y);
				return new CollisionResult(x==0,y==0);
			}
		}
		
		return new CollisionResult(true,true); // do not move!
	}
	
	private void cleanUpItem(Item item){
		if(item instanceof PowerUp)
			driver.actorAccesser.removePowerUp((PowerUp)item);
		if(item instanceof Resource)
			driver.actorAccesser.removeResource((Resource)item);
	}
	
	public boolean collidesWithAnything(){
		for(BaseActor powerUp : driver.actorAccesser.getPowerUps())
			if(CollisionUtil.overlaps(actor, powerUp))
				return true;
		for(BaseActor resource : driver.actorAccesser.getResources())
			if(CollisionUtil.overlaps(actor, resource))
				return true;
		for(BaseActor AI : driver.actorAccesser.getAIs())
			if(CollisionUtil.overlaps(actor, AI))
				return true;
		for(BaseActor obstacle : driver.actorAccesser.getObstacles())
			if(CollisionUtil.overlaps(actor, obstacle))
				return true;
		for(BaseActor player : driver.actorAccesser.getPlayers())
			if(CollisionUtil.overlaps(actor, player))
				return true;
		return false;
	}
	
	private CollisionResult collidesWithActors(ArrayList<BaseActor> otherActors, int x, int y) {
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
	
	private ArrayList<Item> collidesWithItems(ArrayList<BaseActor> items, int x, int y){
		ArrayList<Item> retVal = new ArrayList<Item>();
		for(BaseActor item : items){
			if(item.collidesWith(actor))
				retVal.add((Item)item);
		}
		return retVal;
	}
}
