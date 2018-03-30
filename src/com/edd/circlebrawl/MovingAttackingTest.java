package com.edd.circlebrawl;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


import javax.swing.Timer;

import acm.graphics.GOval;
import acm.program.GraphicsProgram;

public class MovingAttackingTest extends GraphicsProgram {
	public static final int SCREEN_HEIGHT = 1024;
	public static final int SCREEN_WIDTH = 768;
	public static final int BALL_CIRC = 100;
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
		ball = new GOval(SCREEN_HEIGHT / 2, SCREEN_WIDTH / 2, BALL_CIRC, BALL_CIRC);
		ring = new GOval((SCREEN_HEIGHT - (ATTACK_RING - BALL_CIRC)) / 2,
				(SCREEN_WIDTH - (ATTACK_RING - BALL_CIRC)) / 2, ATTACK_RING, ATTACK_RING);
		ball.setFilled(true);
		ball.setFillColor(Color.RED);
		addKeyListeners();
		addMouseListeners();
		
		
		
	

		add(ball);
		
		while(true)
		{
			ball.move(xVelocity, yVelocity);
			ring.move(xVelocity, yVelocity);

			pause(10);
		}
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
            	    if(keyW) {
            	    	yVelocity = -5;
            	    }
            	    if(keyS) {
            	    	yVelocity = 5;
            	    }
            	    if(keyLEFT) {
            	    	xVelocity = -5;
            	    }
            	    if(keyRIGHT) {
            	    	xVelocity = 5;
            	    }
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
		setSize(SCREEN_HEIGHT, SCREEN_WIDTH);
	}
}
