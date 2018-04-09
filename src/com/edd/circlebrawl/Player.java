package com.edd.circlebrawl;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

import com.edd.osvaldo.MainApplication;

import acm.graphics.GOval;

public class Player extends Character implements ActionListener {

	private boolean keyW, keyS, keyA, keyD;
	private double xVelocity = 0;
	private double yVelocity = 0;
	private int keyI;
	public int ATTACK_RING = 150;
	private GOval ring;
	private Timer testTimer = new Timer(2000, this);

	public Player(int x, int y, MainApplication mainApplication) {
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

//	public void mousePressed(MouseEvent e) {
//		driver.add(ring);
//		testTimer.start();
//
//	}
//
//	public void actionPerformed(ActionEvent e) {
//		driver.remove(ring);
//	}

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
		this.bringToFront();
		this.collision();
		//ring.move(xVelocity, yVelocity);
	}

	public void bringToFront() {
	}

	public void removePlayer() {
		this.remove();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
