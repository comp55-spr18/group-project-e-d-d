package com.edd.circlebrawl;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import com.edd.character.Player;
import com.edd.generator.AIGenerator;
import com.edd.generator.BoundaryGenerator;
import com.edd.generator.ObstacleGenerator;
import com.edd.generator.PowerUpGenerator;
import com.edd.generator.ResourceGenerator;
import com.edd.map.Map;
import com.edd.map.MapBuilder;
import com.edd.osvaldo.AudioPlayer;
import com.edd.osvaldo.GButton;
import com.edd.osvaldo.GraphicsApplication;
import com.edd.osvaldo.MenuPane;
import com.edd.osvaldo.SomePane;

import acm.graphics.GImage;
import acm.graphics.GLabel;

public class MainApplication extends GraphicsApplication implements Tick {
	public static final int WINDOW_WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int WINDOW_HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public static final String MUSIC_FOLDER = "sounds";
	public static final String[] SOUND_FILES = { "r2d2.mp3", "somethinlikethis.mp3", "01. Scott Pilgrim Anthem.mp3",
			"11. Bollywood.mp3", "saw.mp3", "theClickSound.mp3", "cheapShop.mp3" };
	public static final int TICKS_PER_SECOND = 120;

	public final PowerUpGenerator POWERUP_GEN = new PowerUpGenerator(GameType.SINGLEPLAYER, this);
	public final ResourceGenerator RESOURCE_GEN = new ResourceGenerator(GameType.SINGLEPLAYER, this);
	public final ObstacleGenerator OBSTACLE_GEN = new ObstacleGenerator(GameType.SINGLEPLAYER, this);
	public final BoundaryGenerator BOUNDARY_GEN = new BoundaryGenerator(this);
	public final AIGenerator AI_GEN = new AIGenerator(GameType.SINGLEPLAYER, this);

	public final int AI_MAX = 3;
	public Player player;

	public boolean startTick = false;

	private SomePane somePane;
	private MenuPane menu;

	protected Map currentMap;
	protected GImage backgroundImage;
	protected ActorAccesser actorAccesser = new ActorAccesser(GameType.SINGLEPLAYER, this);

	public AudioPlayer audio = AudioPlayer.getInstance();
	public boolean test = false;
	public GButton muteButton;
	public GButton pauseButton;

	public void setupMap(){
		currentMap = MapBuilder.buildMap("com/edd/map/test-background.csv", 200, 200, 55, 55, 2);
		backgroundImage = currentMap.createImage();
	}
	
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		add(new GImage("com/edd/osvaldo/brick4.jpg"));
		GImage loading = new GImage("com/edd/osvaldo/CircleBrawlLoading.gif");
		loading = new GImage("com/edd/osvaldo/CircleBrawlLoading.gif", (WINDOW_WIDTH - loading.getWidth()) / 2,
				(WINDOW_HEIGHT - loading.getHeight()) / 2);
		add(loading);
		setupMap();
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

		addKeyListeners();
		addMouseListeners();

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
		if (test && !somePane.getPauseStatus()) {
			OBSTACLE_GEN.tick();
			RESOURCE_GEN.tick();
			POWERUP_GEN.tick();
			BOUNDARY_GEN.spawn();
			AI_GEN.tick();
			player.tick();
		}

	}

	public void bringPlayersToFront() {
		// TODO Auto-generated method stub

	}

	public void keyPressed(KeyEvent e) {
		player.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		player.keyReleased(e);
	}

	public void resetGame() {

	}
	
	public ActorAccesser getAccesser(){
		return actorAccesser;
	}
	
	public GImage getBackgroundImage(){
		if(backgroundImage == null)
			setupMap();
		return backgroundImage;
	}
	
	public int getMapWidth(){
		return currentMap.getMapWidth();
	}
	
	public int getMapHeight(){
		return currentMap.getMapHeight();
	}
	
	public int getTilesInMapX(){
		return currentMap.getTilesInMapX();
	}
	
	public int getTilesInMapY(){
		return currentMap.getTilesInMapY();
	}
	
	public int getTilesBufferX(){
		return currentMap.getTilesBufferX();
	}
	
	public int getTilesBufferY(){
		return currentMap.getTilesBufferY();
	}
}
