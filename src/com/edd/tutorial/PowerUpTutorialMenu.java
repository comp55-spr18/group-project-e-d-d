package com.edd.tutorial;

import java.awt.Color;
import java.awt.event.MouseEvent;

import com.edd.circlebrawl.MainApplication;
import com.edd.osvaldo.GButton;
import com.edd.osvaldo.GraphicsPane;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class PowerUpTutorialMenu extends GraphicsPane {
	/* you will use program to get access to all of the GraphicsProgram calls */
	private MainApplication program;

	/* button instantiation list */
	private GButton strengthTut;
	private GButton enduranceTut;
	private GButton speedTut;
	private GButton attackOrbTut;
	private GButton mainMenu;
	private GButton muteButton;
	private GButton popup = null;

	/* instantiate and initalize graphical elements */
	private GImage background = new GImage("com/edd/osvaldo/brick4.jpg");
	private GImage title = new GImage("com/edd/osvaldo/CircleBrawl.gif", program.WINDOW_WIDTH / 2,
			program.WINDOW_HEIGHT / 2);
	private boolean soundPaused = false;

	public PowerUpTutorialMenu(MainApplication app) { // not sure if I'll be using MainApplication
		super();
		program = app;

		title.setLocation((program.WINDOW_WIDTH - title.getWidth()) / 2, program.WINDOW_HEIGHT / 2 - 200);

		// initialize the strength tutorial button
		strengthTut = new GButton("Strength", (program.WINDOW_WIDTH - 300) / 2, (program.WINDOW_HEIGHT - 150) / 2, 100,
				100);
		strengthTut.setFillColor(Color.GREEN);

		// initialize the endurance tutorial button
		enduranceTut = new GButton("Endurance", (program.WINDOW_WIDTH + 100) / 2, (program.WINDOW_HEIGHT - 150) / 2,
				100, 100);
		enduranceTut.setFillColor(Color.GREEN);

		// initialize the speed tutorial button
		speedTut = new GButton("Speed", (program.WINDOW_WIDTH - 300) / 2, (program.WINDOW_HEIGHT + 150) / 2, 100, 100);
		speedTut.setFillColor(Color.GREEN);

		// initialize the attackOrb tutorial button
		attackOrbTut = new GButton("Enemies", (program.WINDOW_WIDTH + 100) / 2, (program.WINDOW_HEIGHT + 150) / 2, 100,
				100);
		attackOrbTut.setFillColor(Color.GREEN);

		// initialize the mute button
		muteButton = new GButton("Mute", (program.WINDOW_WIDTH + 700) / 2, (program.WINDOW_HEIGHT + 600) / 2, 50, 50);
		muteButton.setFillColor(Color.GREEN);

		// initialize the return to menu button
		mainMenu = new GButton("Main Menu", (program.WINDOW_WIDTH + 900) / 2, (program.WINDOW_HEIGHT + 600) / 2, 50,
				50);
		mainMenu.setFillColor(Color.GREEN);
	}

	@Override
	public void showContents() {
		program.add(background);
		program.add(title);
		program.add(muteButton);
		program.add(mainMenu);
		program.add(strengthTut);
		program.add(enduranceTut);
		program.add(speedTut);
		program.add(attackOrbTut);
	}

	@Override
	public void hideContents() {
		program.remove(background);
		program.remove(title);
		program.remove(muteButton);
		program.remove(mainMenu);
		program.remove(strengthTut);
		program.remove(enduranceTut);
		program.remove(speedTut);
		program.remove(attackOrbTut);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());

		// mute button interaction
		if (obj == muteButton && !soundPaused) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			program.audio.pauseSound(program.MUSIC_FOLDER, program.SOUND_FILES[2]);
			soundPaused = true;
			muteButton.setFillColor(Color.GRAY);
		} else if (obj == muteButton && soundPaused) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[2]);
			soundPaused = false;
			muteButton.setFillColor(Color.GREEN);
		}
		if (obj == strengthTut) {
			popup = new GButton("This is a test string", (program.WINDOW_WIDTH - 500) / 2,
					(program.WINDOW_HEIGHT - 500) / 2, 500, 500);
			program.add(popup);
		}
		if (popup != null && obj == popup) {
			program.remove(popup);
			popup = null;
		}
		// if (program.popup != null && obj == program.popup)

		// if (obj == rect) {
		// program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
		// program.test = true;
		// program.audio.stopSound(program.MUSIC_FOLDER, program.SOUND_FILES[2]);
		// program.switchToSome();
		// }
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());

		if (obj == mainMenu) {
			program.switchToMenu();
		}
	}
}