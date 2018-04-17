package com.edd.character;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.edd.circlebrawl.Camera;
import com.edd.circlebrawl.GameType;
import com.edd.circlebrawl.MainApplication;
import com.edd.circlebrawl.MultiplayerSam_Test;
import com.edd.collision.CollisionEngine;
import com.edd.collision.CollisionResult;

import acm.graphics.GLabel;

public class Player extends Character {

	private final int BASE_SIZE = 100;
	private final int BASE_DEFENSE = 10;
	private final int BASE_SPEED = 5;
	private final int BASE_STRENGTH = 20;
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
		basicPlayerConstructor(GameType.SINGLEPLAYER,"", new CollisionEngine(this,driver));
		setPlayerSpritePos();
	}

	public Player(String name, int x, int y, MainApplication driver) {
		basicPreConstructor(x,y,driver);
		basicPlayerConstructor(GameType.SINGLEPLAYER,name, new CollisionEngine(this,driver));
		setPlayerSpritePos();
	}
	
	public Player(String name, boolean isClient, int x, int y, int color, MainApplication driver) {
		basicPreConstructor(x,y,driver);
		basicPlayerConstructor(GameType.MULTIPLAYER,name, c[color],new CollisionEngine(this,driver));
		if(isClient)
			setPlayerSpritePos();
	}
	
	protected void basicPlayerConstructor(GameType gameType, String name, Color color, CollisionEngine collisionEngine){
		basicCharacterConstructor(collisionEngine,gameType,BASE_SIZE,BASE_DEFENSE,BASE_SPEED,BASE_STRENGTH,BASE_ATTACK_SPEED,color);
		basicPostConstructor();
		this.name = name;
		cam = new Camera(-(int)x+MainApplication.WINDOW_WIDTH/2-(int)getWidth()/2, -(int)y+MainApplication.WINDOW_HEIGHT/2-(int)getHeight()/2, this, driver);
		this.pColor = color;
		setNamePlateLabel();
		driver.addKeyListeners();
		driver.addMouseListeners();
		adjustSaw();
		this.gameType = gameType;
	}
	
	private void setPlayerSpritePos(){
		sprite.setLocation(MainApplication.WINDOW_WIDTH/2-getWidth()/2,MainApplication.WINDOW_HEIGHT/2-getHeight()/2);
		setNamePlateLabel();
	}
	
	private void setNamePlateLabel(){
		if(namePlate != null)
			driver.remove(namePlate);
		namePlate = new GLabel(name, sprite.getX() + size/2, sprite.getY() + size);
		namePlate.move(-namePlate.getWidth()/2, namePlate.getHeight());
		driver.add(namePlate);
	}

	protected void basicPlayerConstructor(GameType gameType, String name, CollisionEngine collisionEngine){
		Random rand = new Random();
		basicPlayerConstructor(gameType,name,c[rand.nextInt(c.length)],collisionEngine);
	}
	
	public Color generateColor (){
		Random rand = new Random();
		int n = rand.nextInt(8);
		return c[n];
	}

	public void respawn(){
		Random rand = new Random();
		
		remove();
		basicPreConstructor(gameType,driver);
		basicPlayerConstructor(gameType,name, c[rand.nextInt(c.length)],new CollisionEngine(this,driver));
		
		boolean setPlayerSpritePos = false;
		
		switch(gameType){
			case SINGLEPLAYER:
				setPlayerSpritePos = true;
				break;
			case MULTIPLAYER:
				MultiplayerSam_Test multiDriver = (MultiplayerSam_Test)driver;
				setPlayerSpritePos = multiDriver.getClientPlayer() == this;
				break;
		}
		
		if(setPlayerSpritePos)
			setPlayerSpritePos();
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

		bringToFront();
	}

	public void bringToFront() {
		sprite.sendToFront();
	}

	public void removePlayer() {
		this.remove();
	}

}
