package com.edd.circlebrawl;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

import com.edd.powerup.PowerUpGenerator;

import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.program.GraphicsProgram;

public class MovingAttackingTest extends GraphicsProgram {
	public final int MAP_WIDTH = 1024;
	public final int MAP_HEIGHT = 768;
	public final int WINDOW_HEIGHT = 1024;
	public final int WINDOW_WIDTH = 768;
	private final int BALL_CIRC = 100;
	public static final int ATTACK_RING = 150;
	private int keyI;
	private int lastKeyPressed;

	private GOval ball;
	private GOval ring;
	private Timer testTimer = new Timer(2000, this);
	private boolean keyW, keyS, keyLEFT, keyRIGHT;
	private double xVelocity = 0;
	private double yVelocity = 0;
//	Thread animationThread;


	public void run() {
		ball = new GOval(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2, BALL_CIRC, BALL_CIRC);
		ring = new GOval((WINDOW_WIDTH - (ATTACK_RING - BALL_CIRC)) / 2,
				(WINDOW_HEIGHT - (ATTACK_RING - BALL_CIRC)) / 2, ATTACK_RING, ATTACK_RING);
		ball.setFilled(true);
		ball.setFillColor(Color.RED);
		addKeyListeners();
		addMouseListeners();
		
		long lastTime = System.nanoTime();
		final double ticks = 60.0;
		double ns = 1000000000 / ticks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		
		GLabel g = new GLabel("Ticks: " + ticks + "\nFrames: " + frames);
		g.setLocation(WINDOW_WIDTH - g.getX(), WINDOW_HEIGHT - g.getY());
		add(g);
		add(ball);
		
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
		double x = ball.getX();
		double y = ball.getY();
		
		int count = 0;
        int LKP = lastKeyPressed;
        if(keyW && y >= 0) {
	    	yVelocity = -10;
	    	System.out.println("Y: " + y);
	    }
        
        else if(keyS && y + BALL_CIRC <= 768) {
	    	yVelocity = 10;
	    	System.out.println("Y: " + y);
	    }
        else 
        	yVelocity = 0;
        
	    if(keyLEFT && x >= 0) {
	    	xVelocity = -10;
	    	System.out.println("X: " + x);
	    }
	    
	    else if(keyRIGHT && x + BALL_CIRC <= 1024) {
	    	xVelocity = 10;
	    	System.out.println("X: " + x);
	    }
	    else 
	    	xVelocity = 0;
	    
	    ball.move(xVelocity, yVelocity);
	    ring.move(xVelocity, yVelocity);
	}


	public void keyPressed(KeyEvent e) {
		
		keyI = e.getKeyChar();
	    if (keyI == 'w'){ keyW = true; }
	    if (keyI == 's'){ keyS = true; }
	    if (keyI == 'a'){ keyLEFT = true; }
	    if (keyI == 'd'){ keyRIGHT = true; }
	    
	    
//	    animationThread = new Thread(new Runnable() {
//            public void run() {
                int count = 0;
                int LKP = lastKeyPressed;
                
                

//                while (true) {
            	   
                    
            	    //moving the ball
//            	    if(keyW) {
//            	    	yVelocity = -5;
//            	    }
//            	    if(keyS) {
//            	    	yVelocity = 5;
//            	    }
//            	    if(keyLEFT) {
//            	    	xVelocity = -5;
//            	    }
//            	    if(keyRIGHT) {
//            	    	xVelocity = 5;
//            	    }
//            	    try {
//            	    	Thread.sleep(10);
//            	    } catch (Exception ex) {}
//            	  
//            	    lastKeyPressed = keyI;
//                }
//            }
//        });
//        animationThread.start();
	        
	   
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

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}
}
