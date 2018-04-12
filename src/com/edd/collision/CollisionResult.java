package com.edd.collision;

public class CollisionResult {

	public boolean xCollides;
	public boolean yCollides;
	
	public CollisionResult(boolean xCollides, boolean yCollides){
		this.xCollides = xCollides;
		this.yCollides = yCollides;
	}
	
	public CollisionResult merge(CollisionResult other ){
		return new CollisionResult(xCollides || other.xCollides, yCollides || other.yCollides);
	}
	
}
