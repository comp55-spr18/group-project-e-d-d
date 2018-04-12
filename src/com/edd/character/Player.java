package com.edd.character;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.Timer;

import com.edd.circlebrawl.Camera;
import com.edd.collision.SinglePlayerCollisionEngine;
import com.edd.osvaldo.MainApplication;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GOval;

public class Player extends Character implements ActionListener {

	private final int BASE_SIZE = 100;
	private final int BASE_DEFENSE = 10;
	private final int BASE_SPEED = 5;
	private final int BASE_STRENGTH = 50;
	
	private boolean keyW, keyS, keyA, keyD;
	private double xVelocity = 0;
	private double yVelocity = 0;
	private int keyI;
	public int ATTACK_RING = 190;
	private GOval ring;
	private GLabel namePlate;
	private Timer testTimer = new Timer(4000, this);
	public boolean startTick = false;
	private Color c[] = { Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.CYAN, Color.PINK, Color.YELLOW, Color.MAGENTA };
	private Color pColor;
	Camera cam;

	public Player(int x, int y, MainApplication mainApplication) {

		basicPreConstructor(x,y,driver);
		basicCharacterConstructor(new SinglePlayerCollisionEngine(this,driver),BASE_SIZE,BASE_DEFENSE,BASE_SPEED,BASE_STRENGTH,"");
		
		Random rand = new Random();
		int n = rand.nextInt(8);
		this.pColor = c[n];

		saw.setBounds((driver.WINDOW_WIDTH/2) - ATTACK_RING/2, (driver.WINDOW_HEIGHT/2) - ATTACK_RING/2 , ATTACK_RING, ATTACK_RING);
		cam = new Camera(0, 0, this, mainApplication);
		

		// BELOW IS TEMP FOR DEMO
		this.sprite = new GOval(x + size / 2, y + size / 2, size, size);
		((GOval)sprite).setColor(pColor);
		((GOval)sprite).setFilled(true);
		ring = new GOval(this.getX() / 2, this.getY() / 2 + 30, 140, 140);
		
		basicPostConstructor();
	}

	public Player(String name, int x, int y, MainApplication driver) {

		basicPreConstructor(x,y,driver);
		basicCharacterConstructor(new SinglePlayerCollisionEngine(this,driver),BASE_SIZE,BASE_DEFENSE,BASE_SPEED,BASE_STRENGTH,name);
		
		//Nameplate
		namePlate = new GLabel(name, x + size / 2, y + size / 2);
		driver.add(namePlate);

		// BELOW IS TEMP FOR DEMO
		sprite = new GOval(x + size / 2, y + size / 2, size, size);
		((GOval)sprite).setColor(pColor);
		((GOval)sprite).setFilled(true);
		namePlate = new GLabel(name, x + size / 2, y + size / 2);
		
		basicPostConstructor();
	}
	
	public Player(String name, int x, int y, int color, MainApplication driver) {
		
		basicPreConstructor(x,y,driver);
		basicCharacterConstructor(new SinglePlayerCollisionEngine(this,driver),BASE_SIZE,BASE_DEFENSE,BASE_SPEED,BASE_STRENGTH,name);
		
		//TODO Sam: Fix this class to allow for a versatile method
		Random rand = new Random();
		int n = rand.nextInt(8);

		// BELOW IS TEMP FOR DEMO
		sprite = new GOval(x + size / 2, y + size / 2, size, size);
		((GOval)sprite).setColor(pColor);
		((GOval)sprite).setFilled(true);
		namePlate = new GLabel(name, x + size / 2, y + size / 2);
		
		driver.add(namePlate);
		
		basicPostConstructor();
	}
	
	public Color generateColor (){
		Random rand = new Random();
		int n = rand.nextInt(8);
		return c[n];
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public GLabel getNameLabel() {
		return namePlate;
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
		driver.add(saw);
		testTimer.start();

	}

	public void actionPerformed(ActionEvent e) {
		driver.remove(saw);
	}

	public void tick() {
		double height = sprite.getHeight();
		double width = sprite.getWidth();
		int buffer = 10;

		if (keyW && y + height / 2 - buffer >= 0) {
			yVelocity = -speed;
			cam.translate(xVelocity, yVelocity);
		}

		else if (keyS && y + height * 1.5 + buffer <= driver.WINDOW_HEIGHT) {
			yVelocity = speed;
			cam.translate(xVelocity, yVelocity);
		} else {
			yVelocity = 0;
			cam.translate(xVelocity, yVelocity);
		}
			
		if (keyA && x + width / 2 - buffer >= 0) {
			xVelocity = -speed;
			cam.translate(xVelocity, yVelocity);
		}
		else if (keyD && x + width * 1.5 + buffer <= driver.WINDOW_WIDTH) {
			xVelocity = speed;
			cam.translate(xVelocity, yVelocity);
		} else {
			xVelocity = 0;
			cam.translate(xVelocity, yVelocity);
		}
			
		this.attemptMove(xVelocity, yVelocity);
		if(namePlate != null)
			this.namePlate.move(xVelocity, yVelocity);
		this.bringToFront();
		saw.move(xVelocity, yVelocity);
	}

	public GImage getSawSprite() {
		return this.saw;
	}
	public void bringToFront() {
		driver.remove(sprite);
		driver.add(sprite);
	}

	public void removePlayer() {
		this.remove();
	}

}
