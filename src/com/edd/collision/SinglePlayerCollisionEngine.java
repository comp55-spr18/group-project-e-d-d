package com.edd.collision;

import com.edd.character.Character;
import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.Item;
import com.edd.circlebrawl.MainApplication;
import com.edd.circlebrawl.Resource;
import com.edd.powerup.PowerUp;

public class SinglePlayerCollisionEngine extends BaseCollisionEngine {

	public SinglePlayerCollisionEngine(BaseActor actor, MainApplication driver){
		this.actor = actor;
		this.driver = driver;
		this.accesser = driver.actorAccesser;
	}
	
	@Override
	public void moveActor(int x, int y){
		((Character)actor).move(x,y);
	}
	
	@Override
	protected void cleanUpItem(Item item){
		if(item instanceof PowerUp)
			driver.POWERUP_GEN.use((PowerUp)item);
		if(item instanceof Resource)
			driver.RESOURCE_GEN.addToRemoveList(item);
	}
}
