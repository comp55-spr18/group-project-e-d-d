package com.edd.osvaldo;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import com.edd.character.Player;
import com.edd.circlebrawl.Tick;
import com.edd.generator.AIGenerator;
import com.edd.generator.ObstacleGenerator;
import com.edd.generator.PowerUpGenerator;
import com.edd.generator.ResourceGenerator;

import acm.graphics.GLabel;

public class MainApplication extends GraphicsApplication implements Tick {
	public static final int WINDOW_WIDTH = 1280;
	public static final int WINDOW_HEIGHT = 720;
	public static final String MUSIC_FOLDER = "sounds";
	static final String[] SOUND_FILES = { "r2d2.mp3", "somethinlikethis.mp3", "01. Scott Pilgrim Anthem.mp3", "11. Bollywood.mp3", "saw.mp3" };
	public final int TICKS_PER_SECOND = 120;
	public final PowerUpGenerator POWERUP_GEN = new PowerUpGenerator(this);
	public final ResourceGenerator RESOURCE_GEN = new ResourceGenerator(this);
	public final ObstacleGenerator OBSTACLE_GEN = new ObstacleGenerator(this);
	public final AIGenerator AI_GEN = new AIGenerator(this);
	public final int AI_MAX = 3;
	public Player player;
	
	public boolean startTick = false;

	private SomePane somePane;
	private MenuPane menu;
	private int count;

	AudioPlayer audio = AudioPlayer.getInstance();
	public boolean test = false;
	
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void run() {
		somePane = new SomePane(this);
		menu = new MenuPane(this);
		switchToMenu();
		
		long lastTime = System.nanoTime();
		final double ticks = 120.0;
		double ns = 1000000000 / ticks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();

		GLabel g = new GLabel("Ticks: " + ticks + "\nFrames: " + frames);
		g.setLocation(WINDOW_WIDTH - g.getWidth() - 60, WINDOW_HEIGHT - g.getHeight());

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

	public void switchToMenu() {
		audio.playSound(MUSIC_FOLDER, SOUND_FILES[2], true);
		count++;
		switchToScreen(menu);
	}

	public void switchToSome() {
		audio.stopSound(MUSIC_FOLDER, SOUND_FILES[2]);
		audio.playSound(MUSIC_FOLDER, SOUND_FILES[3]);
		switchToScreen(somePane);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		if (test) {
			OBSTACLE_GEN.tick();
			//RESOURCE_GEN.tick();
			//POWERUP_GEN.tick();
			//AI_GEN.tick();
			player.tick();
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

	public void bringPlayersToFront() {
		// TODO Auto-generated method stub
		
	}
}
