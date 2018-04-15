package com.edd.character;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.edd.circlebrawl.Camera;
import com.edd.circlebrawl.GameType;
import com.edd.circlebrawl.MainApplication;
import com.edd.circlebrawl.MultiplayerSam_Test;
import com.edd.collision.BaseCollisionEngine;
import com.edd.collision.CollisionResult;
import com.edd.collision.MultiPlayerCollisionEngine;
import com.edd.collision.SinglePlayerCollisionEngine;

import acm.graphics.GLabel;

public class Player extends Character {

	private final int BASE_SIZE = 100;
	private final int BASE_DEFENSE = 10;
	private final int BASE_SPEED = 5;
	private final int BASE_STRENGTH = 50;
	private final double BASE_ATTACK_SPEED = 2.3;
	
	private boolean keyW, keyS, keyA, keyD;
	private double xVelocity = 0;
	private double yVelocity = 0;
	private int keyI;
	private GLabel namePlate;
	private Color c[] = { Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.CYAN, Color.PINK, Color.YELLOW, Color.MAGENTA };
	private Color pColor;
	private Camera cam;
	protected String name;

	public Player(int x, int y, MainApplication mainApplication) {
		basicPreConstructor(x,y,driver);
		basicPlayerConstructor(GameType.SINGLEPLAYER,"", new SinglePlayerCollisionEngine(this,driver));
	}

	public Player(String name, int x, int y, MainApplication driver) {
		basicPreConstructor(x,y,driver);
		basicPlayerConstructor(GameType.SINGLEPLAYER,name, new SinglePlayerCollisionEngine(this,driver));
	}
	
	public Player(String name, int x, int y, int color, MainApplication driver) {
		basicPreConstructor(x,y,driver);
		basicPlayerConstructor(GameType.MULTIPLAYER,name, c[color],new MultiPlayerCollisionEngine(this,driver));
	}
	
	public Player(GameType gameType, MainApplication driver) {
		basicPreConstructor(gameType,driver);
		basicPlayerConstructor(gameType,"", new SinglePlayerCollisionEngine(this,driver));
	}
	
	public Player(String name, GameType gameType, MainApplication driver) {
		basicPreConstructor(gameType,driver);
		basicPlayerConstructor(gameType,name, new SinglePlayerCollisionEngine(this,driver));
	}
	
	public Player(String name, GameType gameType, int color, MainApplication driver) {
		basicPreConstructor(gameType,driver);
		basicPlayerConstructor(gameType,name, c[color],new MultiPlayerCollisionEngine(this,driver));
	}
	
	protected void basicPlayerConstructor(GameType gameType, String name, Color color, BaseCollisionEngine collisionEngine){
		basicCharacterConstructor(collisionEngine,gameType,BASE_SIZE,BASE_DEFENSE,BASE_SPEED,BASE_STRENGTH,BASE_ATTACK_SPEED,color);
		this.name = name;
		cam = new Camera(-(int)x+MainApplication.WINDOW_WIDTH/2-(int)getWidth()/2, -(int)y+MainApplication.WINDOW_HEIGHT/2-(int)getHeight()/2, this, driver);
		this.pColor = color;
		sprite.setLocation(MainApplication.WINDOW_WIDTH/2-getWidth()/2,MainApplication.WINDOW_HEIGHT/2-getHeight()/2);
		namePlate = new GLabel(name, sprite.getX() + size/2, sprite.getY() + size);
		namePlate.move(-namePlate.getWidth()/2, namePlate.getHeight());
		driver.add(namePlate);

		driver.addKeyListeners();
		driver.addMouseListeners();
		adjustSaw();
	}

	protected void basicPlayerConstructor(GameType gameType, String name, BaseCollisionEngine collisionEngine){
		Random rand = new Random();
		basicPlayerConstructor(gameType,name,c[rand.nextInt(c.length)],collisionEngine);
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
	
	public Camera getCam(){ return cam; }

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
		attemptAttack();
	}

	public void tick() {
		
		super.tick();

		if(keyW)
			yVelocity = -speed;
		if(keyS)
			yVelocity = speed;
		if(keyA)
			xVelocity = -speed;
		if(keyD)
			xVelocity = speed;

		CollisionResult moveSuccess = attemptMove((int)xVelocity, (int)yVelocity);
		
		if(moveSuccess.xCollides)
			xVelocity = 0;
		if(moveSuccess.yCollides)
			yVelocity = 0;
		
		if(driver instanceof MultiplayerSam_Test)
			((MultiplayerSam_Test)driver).moveSuccess(this, (int)xVelocity, (int)yVelocity);

		cam.translate(-xVelocity, -yVelocity);
		bringToFront();
	}

	public void bringToFront() {
		sprite.sendToFront();
	}

	public void removePlayer() {
		this.remove();
	}

}
