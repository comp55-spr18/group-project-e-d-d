package com.edd.server;

import java.util.Random;

public class ServerPowerUp {
	
	private int efficacy;
	private int multiple;
	private int x;
	private int y;
	private String type;
	private String myID;
	Random r = new Random();
	
	public ServerPowerUp() {
		this.efficacy = getEfficacy(10, 20);
		this.multiple = 1;
		int min = 32*80;
		int max = 32*170-min;
		this.x = r.nextInt(max) + min;
		this.y = r.nextInt(max) + min;
		this.type = getType();
		this.myID = getSaltString();
	}
	
	private int getEfficacy(int min, int max) {
		return r.nextInt(max-min+1)+min; // Efficacy is between min and max.
	}
	
	private String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
	
	private String getType() {
		int rand = r.nextInt(2) + 1;
		switch(rand) {
		case 1:
			return "Speed";
		case 2:
			return "Strength";
		case 3:
			return "Endurance";
		default:
			return "";
		}		
	}
	
	public String generatePacket() {
		return "("+ myID + "," + efficacy + "," + multiple + "," + x + "," + y + "," + type + ")";
	}
	
	public String getID() {
		return this.myID;
	}
}