package com.edd.circlebrawl;

import java.awt.event.KeyEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GRect;


public class TestCamera extends MainApplication{
	
	private boolean keyW, keyS, keyA, keyD;
	private int xVelocity = 0;
	private int yVelocity = 0;
	private int keyI;
	private GImage background = new GImage("com/edd/character/giant_nebula.jpg");
//	GOval oval = new GOval(x + size / 2, y + size / 2, size, size);
	
	public void init(){
		setSize(1920, 1080);
	}
	
	public void run() {
		GRect test = new GRect(10,10,100, 100);
		add(background);
		add(test);
		
		long lastTime = System.nanoTime();
		final double ticks = 60.0;
		double ns = 1000000000 / ticks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();

		GLabel g = new GLabel("Ticks: " + ticks + "\nFrames: " + frames);
		g.setLocation(WINDOW_WIDTH - g.getWidth() - 60, WINDOW_HEIGHT - g.getHeight());
		add(g);

		while (true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				g.setLabel("Ticks: " + ticks + "\nFrames: " + frames);
				updates = 0;
				frames = 0;
			}
		}
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
	
	public void tick() {
		int buffer = 10;

		if (keyW) {
			yVelocity = -10;
		}

		if (keyS) {
			yVelocity = 10;
		} 
		if (keyA) {
			xVelocity = -10;
		}

		else if (keyD) {
			xVelocity = 10;
		}

		this.getGCanvas().setBounds(xVelocity, yVelocity, 1920, 1080);
	}

}
