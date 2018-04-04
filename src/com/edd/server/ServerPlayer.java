package com.edd.server;
import java.util.Random;

public class ServerPlayer {
	
	private String name;
	private int x;
	private int y;
	
	public ServerPlayer(String name) {
		Random rand = new Random();
		this.name = name;
		this.x = rand.nextInt(1024 + 1 - 0) + 0;
		this.y = rand.nextInt(768 + 1 - 0) + 0;
	}
	
	public int getPlayerX() {
		return x;
	}
	
	public int getPlayerY() {
		return y;
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
