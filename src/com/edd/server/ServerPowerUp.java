package com.edd.server;

import java.util.Random;

import com.edd.server.collision.AccessServerElements;
import com.edd.server.collision.CollisionBox;
import com.edd.server.collision.CollisionDetector;

public class ServerPowerUp implements ServerActor{
	
	private int efficacy;
	private int multiple;
	private int x;
	private int y;
	private String type;
	private String myID;
	Random r = new Random();
	private CollisionBox cb;
	private AccessServerElements ASE;
	
	public ServerPowerUp(AccessServerElements ASE) {
		this.efficacy = getEfficacy(10, 20);
		this.multiple = 1;
		this.x = generateBound();
		this.y = generateBound();
		this.cb = new CollisionBox(x, y, x + 60, y + 60);
		this.type = getType();
		this.myID = getSaltString();
		this.ASE = ASE;
		//setValidSpawn();
	}
	
	public void setValidSpawn() {
		CollisionDetector CD = new CollisionDetector(this, ASE);
		while(CD.collides()) {
			this.x = generateBound();
			this.y = generateBound();
			cb = new CollisionBox(x, y, x + 60, y + 60);
		}
	}
	
	public int generateBound() {
//		int v;
//		Random random = new Random();
//		int min = 32*80;
//		int max = 32*170-min;
//		v = random.nextInt(max) + min;
//		return v;
		int min = 4077;
		int max = 8000;
		Random random = new Random();
		int randomNumber = random.nextInt(max + 1 - min) + min;
		return randomNumber;
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
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public CollisionBox getCollisionBox() {
		return this.cb;
	}
	
	public void setCollisionBox(CollisionBox cb) {
		this.cb = cb;
	}
	
}