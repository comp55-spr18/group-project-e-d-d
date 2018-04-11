package com.edd.circlebrawl;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

import com.edd.character.Player;
import com.edd.generator.ObstacleGenerator;
import com.edd.generator.PowerUpGenerator;
import com.edd.generator.ResourceGenerator;
import com.edd.osvaldo.MainApplication;

import acm.graphics.GLabel;
import acm.graphics.GOval;

// Driver Class
public class CircleBrawl extends MainApplication implements Tick {

	public final PowerUpGenerator POWERUP_GEN = new PowerUpGenerator(this);
	public final ResourceGenerator RESOURCE_GEN = new ResourceGenerator(this);
	public final ObstacleGenerator OBSTACLE_GEN = new ObstacleGenerator(this);
	public final int MAP_WIDTH = 1024;
	public final int MAP_HEIGHT = 768;
	public final int WINDOW_WIDTH = 1024;
	public final int WINDOW_HEIGHT = 768;
	public final int TICKS_PER_SECOND = 60;
	public Player player;
	public boolean init = false;

	public static final int ATTACK_RING = 150;

	private GOval ring;
	private Timer testTimer = new Timer(2000, this);
	private int i = 0;

	int ticks = 0;
	int frames = 0;

	@Override
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	@Override
	public void run() {
		// POWERUP_GEN.generatePowerUp();

		player = new Player(MAP_WIDTH / 2 - 100, MAP_HEIGHT / 2 - 100, this);

		ring = new GOval(player.x + 30, player.y + 30, 140, 140);
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
		player.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		player.keyReleased(e);
	}

	public void actionPerformed(ActionEvent e) {
		player.actionPerformed(e);
	}

	public void tick() {
		OBSTACLE_GEN.tick();
		RESOURCE_GEN.tick();
		POWERUP_GEN.tick();
		player.tick();
	}

	// temporary function. Removes Player sprite and reads.
	public void bringPlayersToFront() {
		player.bringToFront();
	}
}
