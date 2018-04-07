package com.edd.osvaldo;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 1280;
	public static final int WINDOW_HEIGHT = 720;
	public static final String MUSIC_FOLDER = "sounds";
	private static final String[] SOUND_FILES = { "r2d2.mp3", "somethinlikethis.mp3", "01. Scott Pilgrim Anthem.mp3" };

	private SomePane somePane;
	private MenuPane menu;
	private int count;

	AudioPlayer audio = AudioPlayer.getInstance();
	
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void run() {
		System.out.println("Hello, world!");
		somePane = new SomePane(this);
		menu = new MenuPane(this);
		switchToMenu();
	}

	public void switchToMenu() {
		audio.playSound(MUSIC_FOLDER, SOUND_FILES[2], true);
		count++;
		switchToScreen(menu);
	}

	public void switchToSome() {
		playRandomSound();
		switchToScreen(somePane);
	}

	private void playRandomSound() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.playSound(MUSIC_FOLDER, SOUND_FILES[count % SOUND_FILES.length]);
	}
}
