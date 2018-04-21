package com.edd.character;

import java.awt.Color;
import java.util.Random;

import com.edd.circlebrawl.GameType;
import com.edd.circlebrawl.MainApplication;
import com.edd.circlebrawl.MultiplayerDriver;

public class AttackOrb extends Character {

	public static final double PERCENT_OF_CHARACTER = .20;
	
	private Random rand;
	private Character owner;
	private final int BASE_ATTACK_SPEED = 1; // delay, in seconds, between attacks
	private double currentAngle;
	
	/***
	 * Attack Orb is a unique character created by the AttackOrb PowerUp. An AttackOrb orbits its "owner" and automatically attacks once every ATTACK_DELAY seconds. AttackOrbs do not collide,
	 * can not be damaged, are 1/5 the size of the owner, and deal 1/5 the damage of the owner.
	 * @param owner the character controlling this object
	 * @param driver the main driver class of the program (MainApplication)
	 */
	public AttackOrb(GameType gameType, Character owner, MainApplication driver) {
		rand = new Random();
		this.owner = owner;
		this.owner.attackOrbs.add(this);

		basicPreConstructor((int)(owner.getX()+owner.getWidth()/2),(int)(owner.getY()-owner.getHeight()/2),driver);

		basicCharacterConstructor(null,gameType,(int)(owner.getSize()*PERCENT_OF_CHARACTER),0,5,
									(int)(owner.getStrength()*PERCENT_OF_CHARACTER),BASE_ATTACK_SPEED,new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));
		basicPostConstructor();

		move(-size/2,-size/2);
		
		if(owner instanceof Player){
			boolean shouldTranslate = false;
			switch(gameType){
				case SINGLEPLAYER:
					shouldTranslate = true;
					break;
				case MULTIPLAYER:
					MultiplayerDriver multiDriver = (MultiplayerDriver)driver;
					if(owner == multiDriver.getClientPlayer())
						shouldTranslate = true;
					break;
			}
			if(shouldTranslate){
				sprite.setLocation((int)(owner.getSprite().getX()+owner.getWidth()/2),(int)(owner.getSprite().getY()-owner.getHeight()/2));
			}
		}

	}
	
	private void setAttackOrbPosition(){
		x = owner.getX()+owner.getWidth()/2-size/2+1;
		y = owner.getY()-owner.getHeight()-size/2;
		sprite.setLocation(owner.getSprite().getX()+owner.getWidth()/2-size/2+1,owner.getSprite().getY()-owner.getHeight()/2-size/2);
		currentAngle = 0;
	}
	
	@Override
	public void movePolar(int distance, double angle){
		super.movePolar(distance, angle);
		int distanceFromOwnerX = (int)((owner.getSprite().getX()+owner.getWidth()/2)-(sprite.getX()+size/2));
		if((distanceFromOwnerX < -size/10 && distanceFromOwnerX > -size/4 && sprite.getY() < owner.getSprite().getY()) || Math.abs(distanceFromOwnerX) > owner.getWidth()*1.5)
			setAttackOrbPosition();
	}
	
	@Override
	public void tick(){
		super.tick();
		attemptAttack();
		
		attackOrbMove();
	}
	
	private void attackOrbMove(){
		// move logic
		currentAngle -= (360/MainApplication.TICKS_PER_SECOND);
		movePolar((int)((2*Math.PI*owner.getSize())/MainApplication.TICKS_PER_SECOND),currentAngle);  // I HATE YOU MATH --Zach
	}
	
	public Character getOwner(){ return owner; }
	
}
