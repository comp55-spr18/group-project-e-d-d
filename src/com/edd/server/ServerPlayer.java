package com.edd.server;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.edd.server.collision.CollisionBox;

public class ServerPlayer implements ServerActor{
	
	private String name;
	private int x;
	private int y;
	private int color;
	private CollisionBox cb;
	
	public ServerPlayer(String name) {
		Random rand = new Random();
		this.name = name;
		this.color = rand.nextInt(8);
		this.x = generateBound();
		this.y = generateBound();
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
