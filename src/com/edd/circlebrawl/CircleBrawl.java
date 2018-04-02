package com.edd.circlebrawl;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Timer;

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
	private Player player = new Player(MAP_WIDTH/2-100,MAP_HEIGHT/2-100,this);
	
	private final int BALL_CIRC = 100;
	public static final int ATTACK_RING = 150;
	private int keyI;
	private int lastKeyPressed;

	private GOval ring;
	private Timer testTimer = new Timer(2000, this);
	private boolean keyW, keyS, keyLEFT, keyRIGHT;
	private double xVelocity = 0;
	private double yVelocity = 0;
	
	int ticks = 0;
	int frames = 0;
	
	@Override
	public void init() {
		setSize(WINDOW_HEIGHT, WINDOW_HEIGHT);
	}
	
	@Override
	public void run() {
		ring = new GOval(player.x+30,player.y+30,140,140);
		addKeyListeners();
		addMouseListeners();
		
		//GOval spritePlaceHolder = new GOval()
		long lastTime = System.nanoTime();
		final double ticks = 60.0;
		double ns = 1000000000 / ticks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		
		for(int i=0;i<5;i++)
			ITEM_LIST.add(POWERUP_GEN.generatePowerUp());
		
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

	public void keyPressed(KeyEvent e) {
		keyI = e.getKeyChar();
	    if (keyI == 'w'){ keyW = true; }
	    if (keyI == 's'){ keyS = true; }
	    if (keyI == 'a'){ keyLEFT = true; }
	    if (keyI == 'd'){ keyRIGHT = true; }
	}
	
	public void keyReleased(KeyEvent e) {
		keyI = e.getKeyChar();
        if (keyI == 'w'){ keyW = false; yVelocity = 0;}
        if (keyI == 's'){ keyS = false; yVelocity = 0;}
        if (keyI == 'a'){ keyLEFT = false; xVelocity = 0;}
        if (keyI == 'd'){ keyRIGHT = false; xVelocity = 0;}
	}

	public void mousePressed(MouseEvent e) {
		add(ring);
		testTimer.start();

	}

	// public void mouseReleased(MouseEvent e)
	// {
	// remove(ring);
	// testTimer.stop();
	//
	// }

	public void actionPerformed(ActionEvent e) {
		remove(ring);
	}
	
	public void tick() {
		double x = player.getX();
		double y = player.getY();
		
		int count = 0;
        int LKP = lastKeyPressed;
        if(keyW && y >= 0) {
	    	yVelocity = -10;
	    }
        
        else if(keyS && y + BALL_CIRC <= 768) {
	    	yVelocity = 10;
	    }
        else 
        	yVelocity = 0;
        
	    if(keyLEFT && x >= 0) {
	    	xVelocity = -10;
	    }
	    
	    else if(keyRIGHT && x + BALL_CIRC <= 1024) {
	    	xVelocity = 10;
	    }
	    else 
	    	xVelocity = 0;
	    
	    player.move(xVelocity, yVelocity);
	    ring.move(xVelocity, yVelocity);
	    
	    for(Item item : ITEM_LIST) {
	    	if(item != null && player.collidesWith(item)) {
	    		item.consume(player);
	    	}
	    }
	}
}
