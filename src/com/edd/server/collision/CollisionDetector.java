package com.edd.server.collision;

import com.edd.server.ServerPlayer;
import com.edd.server.ServerPowerUp;
import com.edd.server.ServerResource;

public class CollisionDetector {

	private CollisionBox cb;
	private AccessServerElements ase;
	
	public CollisionDetector(CollisionBox cb, AccessServerElements ase){
		this.cb = cb;
		this.ase = ase;
	}
	
	/***
	 * Determines if the actor with CollisionBox cb collides with any registered collision objects
	 * @return if the actor is colliding with something
	 */
	public boolean collides(){
		for(ServerPlayer player : ase.getPlayers()){
			if(CollisionUtil.overlaps(cb,player.getCollisionBox()))
				return true;
		}
		for(ServerPowerUp powerUp : ase.getPowerUps()){
			if(CollisionUtil.overlaps(cb,powerUp.getCollisionBox()))
				return true;
		}
		for(ServerResource resource : ase.getResource()){
			if(CollisionUtil.overlaps(cb,resource.getCollisionBox()))
				return true;
		}
		return false;
	}
	
}
