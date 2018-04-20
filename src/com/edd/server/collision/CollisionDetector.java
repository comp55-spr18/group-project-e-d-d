package com.edd.server.collision;

import com.edd.server.ServerActor;

public class CollisionDetector {

	private ServerActor actor;
	private AccessServerElements ase;
	
	public CollisionDetector(ServerActor actor, AccessServerElements ase){
		this.actor = actor;
		this.ase = ase;
	}
	
	/***
	 * Determines if the actor with CollisionBox cb collides with any registered collision objects
	 * @return if the actor is colliding with something
	 */
	public boolean collides(){
		for(ServerActor player : ase.getPlayers()){
			if(actor != player && CollisionUtil.overlaps(actor.getCollisionBox(),player.getCollisionBox())){
				System.out.println(actor + " collides with player " + player);
				return true;
			}
		}
		for(ServerActor powerUp : ase.getPowerUps()){
			if(actor != powerUp && CollisionUtil.overlaps(actor.getCollisionBox(),powerUp.getCollisionBox())){
				System.out.println(actor + " collides with powerUp " + powerUp);
				return true;
			}
		}
		for(ServerActor resource : ase.getResource()){
			if(actor != resource && CollisionUtil.overlaps(actor.getCollisionBox(),resource.getCollisionBox())){
				System.out.println(actor + " collides with resource " + resource);
				return true;
			}
		}
		return false;
	}
	
}
