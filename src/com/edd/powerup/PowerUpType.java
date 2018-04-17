package com.edd.powerup;

import acm.graphics.GImage;

public enum PowerUpType {
	
	// POWERUPS PREFIXED WITH "CORRUPTED" HAVE EITHER WILDLY POSITIVE OR NEGATIVE EFFECTS
	
	// POWERUPS PREFIXED WITH "EVIL" HAVE ONLY NEGATIVE EFFECTS
	
	// POWERUPS PREFIXED WITH NEITHER HAVE ONLY POSITIVE EFFECTS
	
	SPEED(3,"powerup_speed.png",5,3,5),
	EVIL_SPEED(1,"evil_powerup_speed.png",5,-5,-3),
	CORRUPTED_SPEED(2,"corrupted_powerup_speed.png",5,-6,8),
	STRENGTH(3,"powerup_strength.png",7,5,10),
	EVIL_STRENGTH(1,"evil_powerup_strength.png",7,-10,-5),
	CORRUPTED_STRENGTH(2,"corrupted_powerup_strength.png",7,-15,15),
	ENDURANCE(3,"powerup_endurance.png",7,5,10),
	EVIL_ENDURANCE(1,"evil_powerup_endurance.png",7,-10,-5),
	CORRUPTED_ENDURANCE(2,"corrupted_powerup_endurance.png",7,-15,15),
	ATTACK_ORB(200,"powerup_attackorb.png",15);
	
	// Portions work this way:
	// Each PowerUpType gets a certain amount of "portions," and the higher number of portions one PowerUpType has, the higher likelihood it has of being generated.
	private int portions;
	private String spriteFile;
	private int time;
	private int minEfficacy;
	private int maxEfficacy;
	
	private PowerUpType(int portions, String spriteFile,int time) {
		this.portions = portions;
		this.spriteFile = spriteFile;
		this.time = time;
		this.minEfficacy = 0;
		this.maxEfficacy = 0;
	}
	
	private PowerUpType(int portions, String spriteFile,int time,int minEfficacy,int maxEfficacy) {
		this.portions = portions;
		this.spriteFile = spriteFile;
		this.time = time;
		this.minEfficacy = minEfficacy;
		this.maxEfficacy = maxEfficacy;
	}
	
	//getters
	public int getPortions() { return portions; }
	public String getSpriteFile() { return spriteFile; }
	public int getTime() { return time; }
	public int getMinEfficacy(){ return minEfficacy; }
	public int getMaxEfficacy(){ return maxEfficacy; }
	
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
