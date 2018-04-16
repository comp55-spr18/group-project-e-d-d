package com.edd.server;
import java.util.Random;

public class ServerPlayer {
	
	private String name;
	private int x;
	private int y;
	private int color;
	
	public ServerPlayer(String name) {
		Random rand = new Random();
		this.name = name;
		int min = 32*80;
		int max = 32*170-min;
		this.x = rand.nextInt(max) + min;
		this.y = rand.nextInt(max) + min;
		this.color = rand.nextInt(8);
	}
	
	public int getPlayerX() {
		return x;
	}
	
	public int getPlayerY() {
		return y;
	}
	
	public int getPlayerColor() {
		return color;
	}
	
	public void setPlayerX(int x) {
		this.x = x;
	}
	
	public void setPlayerY(int y) {
		this.y = y;
	}
	
	public String getPlayerName() {
		return name;
	}

	@Override
	public String toString() {
		return "name=" + name;
	}
	
}
