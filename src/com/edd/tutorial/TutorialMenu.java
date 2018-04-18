package com.edd.tutorial;

import java.awt.Color;
import java.awt.event.MouseEvent;

import com.edd.circlebrawl.MainApplication;
import com.edd.osvaldo.GButton;
import com.edd.osvaldo.GraphicsPane;

import acm.graphics.GImage;
import acm.graphics.GObject;

//dummy comment

public class TutorialMenu extends GraphicsPane {
	/* you will use program to get access to all of the GraphicsProgram calls */
	private MainApplication program;

	/* button instanitaiton list */
	private GButton movementTut;
	private GButton combatTut;
	private GButton obstaclesTut;
	private GButton resourcesTut;
	private GButton powerupsTut;
	private GButton enemiesTut;
	private GButton mainMenu;
	private GButton muteButton;

	/* instantiate and initalize graphical elements */
	private GImage background = new GImage("com/edd/osvaldo/brick4.jpg");
	private GImage title = new GImage("com/edd/osvaldo/CircleBrawl.gif", program.WINDOW_WIDTH / 2,
			program.WINDOW_HEIGHT / 2);
	private boolean soundPaused = false;

	public TutorialMenu(MainApplication app) { // not sure if I'll be using MainApplication
		super();
		program = app;

		title.setLocation((program.WINDOW_WIDTH - title.getWidth()) / 2, program.WINDOW_HEIGHT / 2 - 200);

		// initialize the movement tutorial button
		movementTut = new GButton("Movement", (program.WINDOW_WIDTH - 500) / 2, (program.WINDOW_HEIGHT - 150) / 2, 100,
				100);
		movementTut.setFillColor(Color.GREEN);

		// initialize the combat tutorial button
		combatTut = new GButton("Combat", (program.WINDOW_WIDTH - 100) / 2, (program.WINDOW_HEIGHT - 150) / 2, 100,
				100);
		combatTut.setFillColor(Color.GREEN);

		// initialize the obstacles tutorial button
		obstaclesTut = new GButton("Obstacles", (program.WINDOW_WIDTH + 300) / 2, (program.WINDOW_HEIGHT - 150) / 2,
				100, 100);
		obstaclesTut.setFillColor(Color.GREEN);

		// initialize the resources tutorial button
		resourcesTut = new GButton("Resources", (program.WINDOW_WIDTH - 500) / 2, (program.WINDOW_HEIGHT + 150) / 2,
				100, 100);
		resourcesTut.setFillColor(Color.GREEN);

		// initialize the powerups tutorial button
		powerupsTut = new GButton("Powerups", (program.WINDOW_WIDTH - 100) / 2, (program.WINDOW_HEIGHT + 150) / 2, 100,
				100);
		powerupsTut.setFillColor(Color.GREEN);

		// initialize the enemies tutorial button
		enemiesTut = new GButton("Enemies", (program.WINDOW_WIDTH + 300) / 2, (program.WINDOW_HEIGHT + 150) / 2, 100,
				100);
		enemiesTut.setFillColor(Color.GREEN);

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
		program.add(movementTut);
		program.add(combatTut);
		program.add(obstaclesTut);
		program.add(resourcesTut);
		program.add(powerupsTut);
		program.add(enemiesTut);
		program.add(mainMenu);
	}

	@Override
	public void hideContents() {
		program.remove(background);
		program.remove(title);
		program.remove(muteButton);
		program.remove(movementTut);
		program.remove(combatTut);
		program.remove(obstaclesTut);
		program.remove(resourcesTut);
		program.remove(powerupsTut);
		program.remove(enemiesTut);
		program.remove(mainMenu);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());

		// mute button interaction
		// if (obj == muteButton && !soundPaused) {
		// program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
		// program.audio.pauseSound(program.MUSIC_FOLDER, program.SOUND_FILES[2]);
		// soundPaused = true;
		// muteButton.setFillColor(Color.GRAY);
		// } else if (obj == muteButton && soundPaused) {
		// program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
		// program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[2]);
		// soundPaused = false;
		// muteButton.setFillColor(Color.GREEN);
		// }
		//
		//
		// if (obj == rect) {
		// program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
		// program.test = true;
		// program.audio.stopSound(program.MUSIC_FOLDER, program.SOUND_FILES[2]);
		// program.switchToSome();
		// }
	}
}