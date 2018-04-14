package com.edd.powerup;

import acm.graphics.GImage;

public enum PowerUpType {
	SPEED(1,"powerup_speed.png",4),
	STRENGTH(1,"powerup_strength.png",4),
	ENDURANCE(1,"powerup_endurance.png",4),
	ATTACK_ORB(100,"powerup_attackorb.png",15);
	
	// Portions work this way:
	// Each PowerUpType gets a certain amount of "portions," and the higher number of portions one PowerUpType has, the higher likelihood it has of being generated.
	private int portions;
	private String spriteFile;
	private int time;
	
	private PowerUpType(int portions, String spriteFile,int time) {
		this.portions = portions;
		this.spriteFile = spriteFile;
		this.time = time;
	}
	
	//getters
	public int getPortions() { return portions; }
	public String getSpriteFile() { return spriteFile; }
	public int getTime() {
		return time;
	}
	
	public static int getTotalPortions() {
		int totalPortions = 0;
		for(PowerUpType type : values()) {
			totalPortions += type.portions;
		}
		return totalPortions;
	}
	
	
	public static PowerUpType stringToEnum(String name) {
		if(name.equalsIgnoreCase("speed"))
			return PowerUpType.SPEED;
		if(name.equalsIgnoreCase("strength"))
			return PowerUpType.STRENGTH;
		if(name.equalsIgnoreCase("endurance"))
			return PowerUpType.ENDURANCE;
		return null;
	}
}
