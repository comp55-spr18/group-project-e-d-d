package com.edd.server;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.edd.server.collision.CollisionBox;
import com.edd.server.collision.CollisionDetector;
import com.edd.server.collision.AccessServerElements;

public class ServerPlayer implements ServerActor{
	
	private String name;
	private int x;
	private int y;
	private int color;
	private CollisionBox cb;
	private AccessServerElements ASE;
	
	public ServerPlayer(String name, AccessServerElements ASE) {
		Random rand = new Random();
		this.name = name;
		this.color = rand.nextInt(8);
		this.x = generateBound();
		this.y = generateBound();
		this.cb = new CollisionBox(x, y, x + 300, y + 300);
		this.ASE = ASE;
		setValidSpawn();
	}
	
	public void setValidSpawn() {
		CollisionDetector CD = new CollisionDetector(this, ASE);
		while(CD.collides()) {
			this.x = generateBound();
			this.y = generateBound();
			cb = new CollisionBox(x, y, x + 300, y + 300);
		}
	}
	
	public int generateBound() {
		Random rand = new Random();
		int min = 32*80;
		int max = 32*170-min;
		this.x = rand.nextInt(max) + min;
		return x;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getPlayerColor() {
		return color;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public String getPlayerName() {
		return name;
	}

	@Override
	public String toString() {
		return "name=" + name;
	}
	
	public CollisionBox getCollisionBox() {
		return this.cb;
	}
	
	public void setCollisionBox(CollisionBox cb) {
		this.cb = cb;
	}
	
}
