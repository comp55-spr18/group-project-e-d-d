package com.edd.collision;

import java.util.ArrayList;

import com.edd.character.Character;
import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.Item;
import com.edd.circlebrawl.Resource;
import com.edd.osvaldo.MainApplication;
import com.edd.powerup.PowerUp;

public class MultiPlayerCollisionEngine extends BaseCollisionEngine {

	public MultiPlayerCollisionEngine(BaseActor actor, MainApplication driver){
		this.actor = actor;
		this.driver = driver;
	}
	
	@Override
	public void moveActor(int x, int y){
		((Character)actor).moveMulti(x,y);
	}
	
	@Override
	protected CollisionResult collidesWithObstacles(int x, int y) {
		ArrayList<BaseActor> multiplayerObstacles = new ArrayList<BaseActor>(); // TODO: Sam, make this the actual list of obstacles 
		return collidesWithActors(multiplayerObstacles,x,y);
	}

	@Override
	protected CollisionResult collidesWithCharacters(int x, int y) {
		ArrayList<BaseActor> multiplayerCharacters = new ArrayList<BaseActor>(); // TODO: Sam, make this the actual list of characters (Players & AI)
		return collidesWithActors(multiplayerCharacters,x,y);
	}

	@Override
	protected ArrayList<Item> collidesWithItems(int x, int y) {
		ArrayList<BaseActor> multiplayerPowerUps = new ArrayList<BaseActor>(); // TODO: Sam, make this the actual list of PowerUps
		ArrayList<BaseActor> multiplayerResources = new ArrayList<BaseActor>(); // TODO: Sam, make this the actual list of Resources
		return super.collidesWithItems(multiplayerPowerUps,multiplayerResources,x,y);
	}
	
	@Override
	public boolean collidesWithAnything(){
		ArrayList<BaseActor> multiplayerPowerUps = new ArrayList<BaseActor>(); // TODO: Sam, make this the actual list of PowerUps
		ArrayList<BaseActor> multiplayerResources = new ArrayList<BaseActor>(); // TODO: Sam, make this the actual list of Resources
		ArrayList<BaseActor> multiplayerAIs = new ArrayList<BaseActor>(); // TODO: Sam, make this the actual list of AIs
		ArrayList<BaseActor> multiplayerObstacles = new ArrayList<BaseActor>(); // TODO: Sam, make this the actual list of obstacles 
		ArrayList<BaseActor> multiplayerPlayers = new ArrayList<BaseActor>(); // TODO: Sam, make this actual list of players
		return collidesWithAnything(multiplayerPowerUps,multiplayerResources,multiplayerAIs,multiplayerObstacles,multiplayerPlayers);
	}
	
	@Override
	protected void cleanUpItem(Item item){
		
		// TODO: Sam, make this remove the item from whatever ticklist it belongs to. Below is the code for the SinglePlayer version of this function. (for reference)
		
		/*if(item instanceof PowerUp)
			driver.POWERUP_GEN.addToRemoveList(item);
		if(item instanceof Resource)
			driver.RESOURCE_GEN.addToRemoveList(item);*/
	}
}
