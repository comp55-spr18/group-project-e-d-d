package com.edd.circlebrawl;

import java.awt.Color;
import java.awt.event.KeyEvent;
import acm.graphics.GOval;

public class Player extends Character {
	
	private boolean keyW, keyS, keyA, keyD;
	private double xVelocity = 0;
	private double yVelocity = 0;
	private int keyI;
	public int ATTACK_RING = 150;
	private GOval ring;

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
		ring = new GOval(this.getX() / 2, this.getY() / 2 + 30, 140, 140);
	}
	
	public Player(String name, int x, int y, CircleBrawl driver) {
		this(x, y, driver);
		this.name = name;
	}
	
	
	public void move(double x, double y) {
		sprite.move(x, y);
		this.x += x;
		this.y += y;
	}
	
	public void keyPressed(KeyEvent e) {
		keyI = e.getKeyChar();
		if (keyI == 'w') {
			keyW = true;
		}
		if (keyI == 's') {
			keyS = true;
		}
		if (keyI == 'a') {
			keyA = true;
		}
		if (keyI == 'd') {
			keyD = true;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		keyI = e.getKeyChar();
		if (keyI == 'w') {
			keyW = false;
			yVelocity = 0;
		}
		if (keyI == 's') {
			keyS = false;
			yVelocity = 0;
		}
		if (keyI == 'a') {
			keyA = false;
			xVelocity = 0;
		}
		if (keyI == 'd') {
			keyD = false;
			xVelocity = 0;
		}
	}
	
	public void tick() {
		double x = this.getX();
		double y = this.getY();

		if (keyW && y >= 0) {
			yVelocity = -10;
			System.out.println("Y: " + y);
		}

		else if (keyS && y + this.getSprite().getHeight() * 2 <= 768) {
			yVelocity = 10;
			System.out.println("Y: " + y);
		} else
			yVelocity = 0;

		if (keyA && x >= 0) {
			xVelocity = -10;
			System.out.println("X: " + x);
		}

		else if (keyD && x + this.getSprite().getWidth()* 2 <= 1024) {
			xVelocity = 10;
			System.out.println("X: " + x);
		} else
			xVelocity = 0;

		this.move(xVelocity, yVelocity);
		this.bringToFront();
		this.collision();
		//ring.move(xVelocity, yVelocity);
	}

	
	public void bringToFront(){
		driver.remove(sprite);
		driver.add(sprite);
	}
	
}
