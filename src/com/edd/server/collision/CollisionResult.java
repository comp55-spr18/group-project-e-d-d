package com.edd.server.collision;

public class CollisionResult {

	public boolean xCollides; // if the x axis collides with something
	public boolean yCollides; // if the y axis collides with something
	
	public CollisionResult(boolean xCollides, boolean yCollides){
		this.xCollides = xCollides;
		this.yCollides = yCollides;
	}
	
	public CollisionResult merge(CollisionResult other ){
		return new CollisionResult(xCollides || other.xCollides, yCollides || other.yCollides);
	}
	
	@Override
	public String toString(){
		return "CollisionResult: xCollides="+xCollides+", yCollides="+yCollides;
	}
	
}
