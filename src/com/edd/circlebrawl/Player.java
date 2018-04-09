package com.edd.circlebrawl;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.Timer;

import com.edd.osvaldo.MainApplication;

import acm.graphics.GLabel;
import acm.graphics.GOval;

public class Player extends Character implements ActionListener {

	private boolean keyW, keyS, keyA, keyD;
	private double xVelocity = 0;
	private double yVelocity = 0;
	private int keyI;
	public int ATTACK_RING = 150;
	private GOval ring;
	private GLabel namePlate;
	private Timer testTimer = new Timer(2000, this);
	public boolean startTick = false;
	private Color c[] = { Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.CYAN, Color.PINK, Color.YELLOW, Color.MAGENTA };
	private Color pColor;

	public Player(int x, int y, MainApplication mainApplication) {
		
		Random rand = new Random();
		int n = rand.nextInt(8);
		this.pColor = c[n];
		this.x = x;
		this.y = y;
		this.driver = mainApplication;
		this.size = 100; // TODO: Make global final variable for defaults
		this.defense = 10; // TODO: Make global final variable for defaults
		this.speed = 50; // TODO: Make global final variable for defaults
		this.strength = 50; // TODO: Make global final variable for defaults
		

		// BELOW IS TEMP FOR DEMO
		GOval localSprite = new GOval(x + size / 2, y + size / 2, size, size);
		setupSprite(localSprite);
		localSprite.setColor(pColor);
		localSprite.setFilled(true);
		ring = new GOval(this.getX() / 2, this.getY() / 2 + 30, 140, 140);
	}

	public Player(String name, int x, int y, MainApplication driver) {
		
		this(x, y, driver);
		this.name = name;
		
		//Nameplate
		namePlate = new GLabel(name, x + size / 2, y + size / 2);
		driver.add(namePlate);
	}
	
	public Color generateColor (){
		Random rand = new Random();
		int n = rand.nextInt(8);
		return c[n];
	}
	
	public void setName(String name) {
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

	public void mousePressed(MouseEvent e) {
		driver.add(ring);
		testTimer.start();

	}

	public void actionPerformed(ActionEvent e) {
		driver.remove(ring);
	}

	public void tick() {
		double height = sprite.getHeight();
		double width = sprite.getWidth();
		int buffer = 10;

		if (keyW && y + height / 2 - buffer >= 0 || this.driver.OBSTACLE_GEN.collidesWith(this)) {
			yVelocity = -10;
		}

		else if (keyS && y + height * 1.5 + buffer <= driver.WINDOW_HEIGHT
				|| this.driver.OBSTACLE_GEN.collidesWith(this)) {
			yVelocity = 10;
		} else
			yVelocity = 0;

		if (keyA && x + width / 2 - buffer >= 0 || this.driver.OBSTACLE_GEN.collidesWith(this)) {
			xVelocity = -10;
		}

		else if (keyD && x + width * 1.5 + buffer <= driver.WINDOW_WIDTH || this.driver.OBSTACLE_GEN.collidesWith(this)) {
			xVelocity = 10;
		} else
			xVelocity = 0;

		this.move(xVelocity, yVelocity);
		this.namePlate.move(xVelocity, yVelocity);
		this.bringToFront();
		this.collision();
		ring.move(xVelocity, yVelocity);
	}

	public void bringToFront() {
		driver.remove(sprite);
		driver.add(sprite);
	}

	public void removePlayer() {
		this.remove();
	}

}
