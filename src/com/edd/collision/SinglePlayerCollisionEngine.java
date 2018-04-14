package com.edd.collision;

import java.util.ArrayList;

import com.edd.character.Character;
import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.Item;
import com.edd.circlebrawl.Resource;
import com.edd.osvaldo.MainApplication;
import com.edd.powerup.PowerUp;

public class SinglePlayerCollisionEngine extends BaseCollisionEngine {

	public SinglePlayerCollisionEngine(BaseActor actor, MainApplication driver){
		this.actor = actor;
		this.driver = driver;
	}
	
	@Override
	public void moveActor(int x, int y){
		((Character)actor).move(x,y);
	}
	
	@Override
	protected CollisionResult collidesWithObstacles(int x, int y) {
		return collidesWithActors(driver.OBSTACLE_GEN.getActors(),x,y);
	}

	@Override
	protected CollisionResult collidesWithOtherCharacters(int x, int y) {
		return collidesWithActors(driver.AI_GEN.getActors(),x,y).merge(CollisionUtil.overlaps(actor,driver.player,x,y));
	}

	@Override
	protected ArrayList<Item> collidesWithItems(int x, int y) {
		return super.collidesWithItems(driver.POWERUP_GEN.getActors(),driver.RESOURCE_GEN.getActors(),x,y);
	}
	
	@Override
	public boolean collidesWithAnything(){
		ArrayList<BaseActor> players = new ArrayList<BaseActor>();
		players.add(driver.player);
		return collidesWithAnything(driver.POWERUP_GEN.getActors(),driver.RESOURCE_GEN.getActors(),driver.AI_GEN.getActors(),driver.OBSTACLE_GEN.getActors(),players);
	}
	
	@Override
	protected void cleanUpItem(Item item){
		if(item instanceof PowerUp)
			driver.POWERUP_GEN.use((PowerUp)item);
		if(item instanceof Resource)
			driver.RESOURCE_GEN.addToRemoveList(item);
	}
}
