package com.edd.collision;

import java.util.ArrayList;

import com.edd.character.Character;
import com.edd.circlebrawl.BaseActor;
import com.edd.osvaldo.MainApplication;
import com.edd.powerup.PowerUp;

public abstract class BaseCollisionEngine {

	protected Character character;
	protected MainApplication driver;
	
	public boolean move(int x, int y) {
		
		ArrayList<PowerUp> powerUps = collidesWithPowerUps(x,y);
		
		OverlapPair obstacleOverlap = collidesWithObstacles(x,y);
		OverlapPair characterOverlap = collidesWithOtherCharacters(x,y);
		
		boolean xOverlaps = obstacleOverlap.xOverlaps || characterOverlap.xOverlaps;
		boolean yOverlaps = obstacleOverlap.yOverlaps || characterOverlap.yOverlaps;

		if(powerUps != null && !powerUps.isEmpty())
			for(PowerUp powerUp : powerUps)
				powerUp.consume(character);
		
		if(xOverlaps)
			x = 0;
		if(yOverlaps)
			y = 0;
		
		if(x != 0 || y != 0){
			character.move(x,y);
			return true;
		}
		
		return false;
	}
	
	protected abstract OverlapPair collidesWithObstacles(int x, int y);
	
	protected abstract OverlapPair collidesWithOtherCharacters(int x, int y);
	
	protected abstract ArrayList<PowerUp> collidesWithPowerUps(int x, int y);
	
	protected OverlapPair collidesWithActors(ArrayList<BaseActor> actors, int x, int y) {
		boolean xOverlaps = false;
		boolean yOverlaps = false;
		
		for(BaseActor actor : actors){
			OverlapPair overlaps = CollisionUtil.overlaps(character, actor, x, y);
			if(overlaps.xOverlaps)
				xOverlaps = true;
			if(overlaps.yOverlaps)
				yOverlaps = true;
		}
		
		return new OverlapPair(xOverlaps,yOverlaps);
	}
}
