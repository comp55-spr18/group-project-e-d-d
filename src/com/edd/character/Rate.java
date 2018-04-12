package com.edd.character;

public enum Rate {
	INCREASING,
	NEUTRAL,
	DECREASING;
	
	public static Rate getRateFromVelocity(int vel){
		if(vel < 0)
			return DECREASING;
		if(vel > 0)
			return INCREASING;
		return NEUTRAL;
	}
}
