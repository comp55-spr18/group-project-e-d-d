package com.edd.collision;

import java.util.ArrayList;

import com.edd.character.Character;
import com.edd.osvaldo.MainApplication;
import com.edd.powerup.PowerUp;

public class SinglePlayerCollisionEngine extends BaseCollisionEngine {

	public SinglePlayerCollisionEngine(Character character, MainApplication driver){
		this.character = character;
		this.driver = driver;
	}
	
	@Override
	protected CollisionResult collidesWithObstacles(int x, int y) {
		return collidesWithActors(driver.OBSTACLE_GEN.getActors(),x,y);
	}

	@Override
	protected CollisionResult collidesWithOtherCharacters(int x, int y) {
		return collidesWithActors(driver.AI_GEN.getActors(),x,y).merge(CollisionUtil.overlaps(character,driver.player,x,y));
	}

	@Override
	protected ArrayList<PowerUp> collidesWithPowerUps(int x, int y) {
		return null;
	}

}
