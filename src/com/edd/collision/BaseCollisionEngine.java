package com.edd.collision;

import java.util.ArrayList;

import com.edd.character.Character;
import com.edd.circlebrawl.BaseActor;
import com.edd.osvaldo.MainApplication;
import com.edd.powerup.PowerUp;

public abstract class BaseCollisionEngine {

	protected Character character;
	protected MainApplication driver;
	
	public CollisionResult move(int x, int y) {
		
		ArrayList<PowerUp> powerUps = collidesWithPowerUps(x,y);
		
		CollisionResult obstacleOverlap = collidesWithObstacles(x,y);
		CollisionResult characterOverlap = collidesWithOtherCharacters(x,y);
		
		boolean xOverlaps = obstacleOverlap.xCollides || characterOverlap.xCollides;
		boolean yOverlaps = obstacleOverlap.yCollides || characterOverlap.yCollides;

		if(powerUps != null && !powerUps.isEmpty())
			for(PowerUp powerUp : powerUps)
				powerUp.consume(character);
		
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
	
	protected abstract CollisionResult collidesWithObstacles(int x, int y);
	
	protected abstract CollisionResult collidesWithOtherCharacters(int x, int y);
	
	protected abstract ArrayList<PowerUp> collidesWithPowerUps(int x, int y);
	
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
}
