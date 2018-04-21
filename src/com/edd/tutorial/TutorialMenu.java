package com.edd.tutorial;

import java.awt.Color;
import java.awt.event.MouseEvent;

import com.edd.circlebrawl.MainApplication;
import com.edd.osvaldo.GButton;
import com.edd.osvaldo.GraphicsPane;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class TutorialMenu extends GraphicsPane {
	/* you will use program to get access to all of the GraphicsProgram calls */
	private MainApplication program;

	/* button instantiation list */
	private GImage movementTut;
	private GImage combatTut;
	private GImage obstaclesTut;
	private GImage resourcesTut;
	private GImage powerupsTut;
	private GImage enemiesTut;
	private GImage closeButton;
	private GButton mainMenu;
	private GButton muteButton;
	private GImage popup = null;

	/* instantiate and initalize graphical elements */
	private GImage background = new GImage("com/edd/osvaldo/brick4.jpg");
	private GImage title = new GImage("com/edd/osvaldo/CircleBrawl.gif", program.WINDOW_WIDTH / 2,
			program.WINDOW_HEIGHT / 2);

	public TutorialMenu(MainApplication app) { // not sure if I'll be using MainApplication
		super();
		program = app;

		title.setLocation((program.WINDOW_WIDTH - title.getWidth()) / 2, program.WINDOW_HEIGHT / 2 - 200);

		// initialize the movement tutorial button
		movementTut = new GImage("com/edd/tutorial/Movement.gif");
		movementTut.setSize(movementTut.getWidth() / 2, movementTut.getHeight() / 2);
		movementTut.setLocation((program.WINDOW_WIDTH - movementTut.getWidth()) / 2,
				(program.WINDOW_HEIGHT - movementTut.getHeight()) / 2 - 50);

		// initialize the combat tutorial button
		combatTut = new GImage("com/edd/tutorial/Combat.gif");
		combatTut.setSize(combatTut.getWidth() / 2, combatTut.getHeight() / 2);
		combatTut.setLocation((program.WINDOW_WIDTH - combatTut.getWidth()) / 2,
				(program.WINDOW_HEIGHT - combatTut.getHeight()) / 2);

		// initialize the obstacles tutorial button
		obstaclesTut = new GImage("com/edd/tutorial/Obstacles.gif");
		obstaclesTut.setSize(obstaclesTut.getWidth() / 2, obstaclesTut.getHeight() / 2);
		obstaclesTut.setLocation((program.WINDOW_WIDTH - obstaclesTut.getWidth()) / 2,
				(program.WINDOW_HEIGHT - obstaclesTut.getHeight()) / 2 + 50);

		// initialize the resources tutorial button
		resourcesTut = new GImage("com/edd/tutorial/Resources.gif");
		resourcesTut.setSize(resourcesTut.getWidth() / 2, resourcesTut.getHeight() / 2);
		resourcesTut.setLocation((program.WINDOW_WIDTH - resourcesTut.getWidth()) / 2,
				(program.WINDOW_HEIGHT - resourcesTut.getHeight()) / 2 + 100);

		// initialize the powerups tutorial button
		powerupsTut = new GImage("com/edd/tutorial/Power.gif");
		powerupsTut.setSize(powerupsTut.getWidth() / 2, powerupsTut.getHeight() / 2);
		powerupsTut.setLocation((program.WINDOW_WIDTH - powerupsTut.getWidth()) / 2,
				(program.WINDOW_HEIGHT - powerupsTut.getHeight()) / 2 + 165);

		// initialize the enemies tutorial button
		enemiesTut = new GImage("com/edd/tutorial/AI.gif");
		enemiesTut.setSize(enemiesTut.getWidth() / 2, enemiesTut.getHeight() / 2);
		enemiesTut.setLocation((program.WINDOW_WIDTH - enemiesTut.getWidth()) / 2,
				(program.WINDOW_HEIGHT - enemiesTut.getHeight()) / 2 + 230);

		// initialize the mute button
		muteButton = new GButton("Mute", (program.WINDOW_WIDTH + 700) / 2, (program.WINDOW_HEIGHT + 600) / 2, 50, 50);

		// initialize the return to menu button
		mainMenu = new GButton("Main Menu", (program.WINDOW_WIDTH + 900) / 2, (program.WINDOW_HEIGHT + 600) / 2, 50,
				50);
		mainMenu.setFillColor(Color.GREEN);

		// initialize the close window button
		closeButton = new GImage("com/edd/tutorial/cancelButton.png");
		closeButton.setSize(50, 50);
		closeButton.setLocation((program.WINDOW_WIDTH - closeButton.getWidth()) / 2 + 210,
				(program.WINDOW_HEIGHT - closeButton.getHeight()) / 2 - 210);
	}

	@Override
	public void showContents() {
		program.add(background);
		program.add(title);
		if (program.soundPaused)
			muteButton.setFillColor(Color.GRAY);
		else
			muteButton.setFillColor(Color.GREEN);
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
		if (obj == muteButton && !program.soundPaused) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			program.audio.pauseSound(program.MUSIC_FOLDER, program.SOUND_FILES[2]);
			program.soundPaused = true;
			muteButton.setFillColor(Color.GRAY);
		} else if (obj == muteButton && program.soundPaused) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[2]);
			program.soundPaused = false;
			muteButton.setFillColor(Color.GREEN);
		}
		if (obj == powerupsTut) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			program.switchToPowerUpTutorialMenu();
		}
		if (popup != null && obj == closeButton) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			program.remove(closeButton);
			program.remove(popup);
			popup = null;
		}
		if (obj == movementTut) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			popup = new GImage("com/edd/tutorial/movementTutPopup.jpg");
			popup.setSize(500, 500);
			popup.setLocation((program.WINDOW_WIDTH - popup.getWidth()) / 2,
					(program.WINDOW_HEIGHT - popup.getHeight()) / 2);
			program.add(popup);
			program.add(closeButton);
		}
		if (obj == combatTut) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			// TODO CHANGE THIS
			popup = new GImage("com/edd/tutorial/combatTut.jpg");
			popup.setSize(500, 500);
			popup.setLocation((program.WINDOW_WIDTH - popup.getWidth()) / 2,
					(program.WINDOW_HEIGHT - popup.getHeight()) / 2);
			program.add(popup);
			program.add(closeButton);
		}
		if (obj == obstaclesTut) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			// TODO CHANGE THIS
			popup = new GImage("com/edd/tutorial/obstaclesTut.jpg");
			popup.setSize(500, 500);
			popup.setLocation((program.WINDOW_WIDTH - popup.getWidth()) / 2,
					(program.WINDOW_HEIGHT - popup.getHeight()) / 2);
			program.add(popup);
			program.add(closeButton);
		}
		if (obj == resourcesTut) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			// TODO CHANGE THIS
			popup = new GImage("com/edd/tutorial/resourcesTut.jpg");
			popup.setSize(500, 500);
			popup.setLocation((program.WINDOW_WIDTH - popup.getWidth()) / 2,
					(program.WINDOW_HEIGHT - popup.getHeight()) / 2);
			program.add(popup);
			program.add(closeButton);
		}
		if (obj == enemiesTut) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			// TODO CHANGE THIS
			popup = new GImage("com/edd/tutorial/aiTut.jpg");
			popup.setSize(500, 500);
			popup.setLocation((program.WINDOW_WIDTH - popup.getWidth()) / 2,
					(program.WINDOW_HEIGHT - popup.getHeight()) / 2);
			program.add(popup);
			program.add(closeButton);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());

		if (obj == mainMenu) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			program.switchToMenu();
		}
	}
}