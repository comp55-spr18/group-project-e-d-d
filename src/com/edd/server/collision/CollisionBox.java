package com.edd.server.collision;

public class CollisionBox {

	// xb = left side
	// yb = top side
	// xe = right side
	// ye = bottom side
	public int xb, yb, xe, ye;
	
	public CollisionBox(int xb, int yb, int xe, int ye){
		this.xb = xb;
		this.yb = yb;
		this.xe = xe;
		this.ye = ye;
	}
	
	@Override
	public String toString(){
		return "CollisionBox(XB: "+xb+", XE: "+xe+", YB: "+yb+", YE: "+ye+")";
	}
}
