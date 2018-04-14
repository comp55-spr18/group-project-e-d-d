package com.edd.character;

import java.awt.Color;
import java.util.Random;

import com.edd.osvaldo.MainApplication;

public class AttackOrb extends Character {

	public static final double PERCENT_OF_CHARACTER = .20;
	
	private Random rand;
	private Character owner;
	private final int ATTACK_DELAY = 2; // delay, in seconds, between attacks
	private int ticks;
	private int seconds;
	private double currentAngle;
	
	/***
	 * Attack Orb is a unique character created by the AttackOrb PowerUp. An AttackOrb orbits its "owner" and automatically attacks once every ATTACK_DELAY seconds. AttackOrbs do not collide,
	 * can not be damaged, are 1/5 the size of the owner, and deal 1/5 the damage of the owner.
	 * @param owner the character controlling this object
	 * @param driver the main driver class of the program (MainApplication)
	 */
	public AttackOrb(Character owner, MainApplication driver) {
		rand = new Random();
		this.owner = owner;
		this.owner.attackOrbs.add(this);
		
		basicPreConstructor((int)(owner.getSprite().getX()+owner.getWidth()/2),(int)(owner.getSprite().getY()-owner.getHeight()),driver);
		basicCharacterConstructor(null,(int)(owner.getSize()*PERCENT_OF_CHARACTER),(int)(owner.getSpeed()*PERCENT_OF_CHARACTER),5,
									(int)(owner.getStrength()*PERCENT_OF_CHARACTER),new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));
		basicPostConstructor();
	}
	
	@Override
	public void tick(){
		ticks++;

		// attack logic
		if(ticks >= driver.TICKS_PER_SECOND){
			seconds++;
			
			if(seconds >= ATTACK_DELAY){
				//attack(); TODO: Uncomment once Mike makes attacks work.
				seconds = 0;
			}
			
			ticks = 0;
		}
		
		// move logic
		currentAngle -= owner.size/100;
		movePolar(speed,currentAngle);
	}
	
}
