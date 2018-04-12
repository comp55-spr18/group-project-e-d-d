package com.edd.character;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.Timer;

import com.edd.circlebrawl.Camera;
import com.edd.collision.CollisionResult;
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
	private GLabel namePlate;
	private Timer testTimer = new Timer(4000, this);
	public boolean startTick = false;
	private Color c[] = { Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.CYAN, Color.PINK, Color.YELLOW, Color.MAGENTA };
	private Color pColor;
	private Camera cam;
	protected String name;

	public Player(int x, int y, MainApplication mainApplication) {

		Random rand = new Random();
		int n = rand.nextInt(8);
		this.pColor = c[n];
		
		basicPreConstructor(x,y,driver);
		basicCharacterConstructor(new SinglePlayerCollisionEngine(this,driver),BASE_SIZE,BASE_DEFENSE,BASE_SPEED,BASE_STRENGTH,pColor);

		this.name = "";
		
		cam = new Camera(0, 0, this, mainApplication);
		
		basicPostConstructor();
	}

	public Player(String name, int x, int y, MainApplication driver) {

		Random rand = new Random();
		int n = rand.nextInt(8);
		this.pColor = c[n];
		
		basicPreConstructor(x,y,driver);
		basicCharacterConstructor(new SinglePlayerCollisionEngine(this,driver),BASE_SIZE,BASE_DEFENSE,BASE_SPEED,BASE_STRENGTH,pColor);
		
		//Nameplate
		namePlate = new GLabel(name, x + size / 2, y + size / 2);
		driver.add(namePlate);
		cam = new Camera(0, 0, this, driver);
		this.name = name;

		// BELOW IS TEMP FOR DEMO
		namePlate = new GLabel(name, x + size / 2, y + size / 2);
		
		basicPostConstructor();
	}
	
	public Player(String name, int x, int y, int color, MainApplication driver) {

		Random rand = new Random();
		int n = rand.nextInt(8);
		this.pColor = c[n];
		
		basicPreConstructor(x,y,driver);
		basicCharacterConstructor(new SinglePlayerCollisionEngine(this,driver),BASE_SIZE,BASE_DEFENSE,BASE_SPEED,BASE_STRENGTH,pColor);
		
		//TODO Sam: Fix this class to allow for a versatile method
		cam = new Camera(0, 0, this, driver);
		this.name = name;

		// BELOW IS TEMP FOR DEMO
		namePlate = new GLabel(name, x + size / 2, y + size / 2);
		
		driver.add(namePlate);
		
		basicPostConstructor();
	}
	
	public Color generateColor (){
		Random rand = new Random();
		int n = rand.nextInt(8);
		return c[n];
	}

	public String getName() { return name; }
	
	public void setName(String name) { this.name = name; }
	
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
		}

		else if (keyS && y + height * 1.5 + buffer <= driver.WINDOW_HEIGHT) {
			yVelocity = speed;
		} else {
			yVelocity = 0;
		}
			
		if (keyA && x + width / 2 - buffer >= 0) {
			xVelocity = -speed;
		}
		else if (keyD && x + width * 1.5 + buffer <= driver.WINDOW_WIDTH) {
			xVelocity = speed;
		} else {
			xVelocity = 0;
		}

		CollisionResult moveSuccess = attemptMove(xVelocity, yVelocity);
		
		if(!moveSuccess.xCollides)
			xVelocity = 0;
		if(!moveSuccess.yCollides)
			yVelocity = 0;

		cam.translate(-xVelocity, -yVelocity);
		if(namePlate != null)
			namePlate.move(xVelocity, yVelocity);
		bringToFront();
	}

	public void bringToFront() {
		driver.remove(sprite);
		driver.add(sprite);
	}

	public void removePlayer() {
		this.remove();
	}

}
