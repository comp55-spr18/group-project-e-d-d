package com.edd.server.collision;

public enum Direction {
	NORTH_WEST(Rate.DECREASING,Rate.INCREASING),
	NORTH(Rate.NEUTRAL,Rate.INCREASING),
	NORTH_EAST(Rate.INCREASING,Rate.INCREASING),
	WEST(Rate.DECREASING,Rate.NEUTRAL),
	EAST(Rate.INCREASING,Rate.NEUTRAL),
	SOUTH_WEST(Rate.DECREASING,Rate.DECREASING),
	SOUTH(Rate.NEUTRAL,Rate.DECREASING),
	SOUTH_EAST(Rate.INCREASING,Rate.DECREASING),
	NEUTRAL(Rate.NEUTRAL,Rate.NEUTRAL);

	private Rate xRate;
	private Rate yRate;
	
	private Direction(Rate xRate, Rate yRate){
		this.xRate = xRate;
		this.yRate = yRate;
	}
	
	public Rate getXRate(){ return xRate; }
	public Rate getYRate(){ return yRate; }
	
	public static Direction getDirectionFromRates(Rate xRate, Rate yRate){
		for(Direction direction : values()){
			if(direction.xRate == xRate && direction.yRate == yRate)
				return direction;
		}
		return null;
	}
	
	public static Direction getDirectionFromVelocity(int xVel, int yVel){
		Rate xRate = Rate.getRateFromVelocity(xVel);
		Rate yRate = Rate.getRateFromVelocity(yVel*-1); // multiply by -1 because decreasing is north in Java
		return getDirectionFromRates(xRate, yRate);
	}
}
