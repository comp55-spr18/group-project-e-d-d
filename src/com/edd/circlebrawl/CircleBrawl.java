package com.edd.circlebrawl;

import java.util.ArrayList; 
import com.edd.powerup.PowerUpGenerator;
import acm.graphics.*;

// Driver Class
public class CircleBrawl extends MainApplication implements Tick {

	public final ArrayList<Item> ITEM_LIST = new ArrayList<Item>();
	public final PowerUpGenerator POWERUP_GEN = new PowerUpGenerator(this);
	public final int MAP_WIDTH = 1024;
	public final int MAP_HEIGHT = 768;
	public final int WINDOW_HEIGHT = 1024;
	public final int WINDOW_WIDTH = 768;
	private double velX = 0;
	private double velY = 0;
	
	
	public void init() {
		setSize(WINDOW_HEIGHT, WINDOW_HEIGHT);
	}
	
	@Override
	public void run() {
		//GOval spritePlaceHolder = new GOval()
		long lastTime = System.nanoTime();
		final double ticks = 60.0;
		double ns = 1000000000 / ticks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		
		POWERUP_GEN.generatePowerUp();
		POWERUP_GEN.generatePowerUp();
		POWERUP_GEN.generatePowerUp();
		POWERUP_GEN.generatePowerUp();
		POWERUP_GEN.generatePowerUp();
		
		
		GLabel g = new GLabel("Ticks: " + ticks + "\nFrames: " + frames);
		g.setLocation(WINDOW_WIDTH - g.getX(), WINDOW_HEIGHT - g.getY());
		add(g);
		
		while(true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				g.setLabel("Ticks: " + ticks + "\nFrames: " + frames);
				updates = 0;
				frames = 0;
			}
		}
	}
	
	public void tick() {
		
	}
}
