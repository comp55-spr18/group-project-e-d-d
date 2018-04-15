package com.edd.collision;

import com.edd.character.Character;
import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.Item;
import com.edd.circlebrawl.MainApplication;

public class MultiPlayerCollisionEngine extends BaseCollisionEngine {

	public MultiPlayerCollisionEngine(BaseActor actor, MainApplication driver){
		this.actor = actor;
		this.driver = driver;
		this.accesser = driver.actorAccesser;
	}
	
	@Override
	public void moveActor(int x, int y){
		((Character)actor).moveMulti(x,y);
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
