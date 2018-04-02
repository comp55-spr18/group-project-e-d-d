package com.edd.circlebrawl;

import java.awt.Color;

import acm.graphics.GOval;

public class Player extends Character {

	public Player(int x, int y, CircleBrawl driver) {
		this.x = x;
		this.y = y;
		this.driver = driver;
		this.size = 100; // TODO: Make global final variable for defaults
		this.defense = 10; // TODO: Make global final variable for defaults
		this.speed = 50; // TODO: Make global final variable for defaults
		this.strength = 50; // TODO: Make global final variable for defaults
		
		//BELOW IS TEMP FOR DEMO
		GOval localSprite = new GOval(x+size/2,y+size/2,size,size);
		setupSprite(localSprite);
		localSprite.setColor(Color.PINK);
		localSprite.setFilled(true);
	}
	
	public void move(double x, double y) {
		sprite.move(x, y);
		this.x += x;
		this.y += y;
	}
	
}
