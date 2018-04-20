package com.edd.tutorial;

import java.awt.Color;
import java.awt.event.MouseEvent;

import com.edd.circlebrawl.MainApplication;
import com.edd.osvaldo.GButton;
import com.edd.osvaldo.GraphicsPane;

import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class PowerUpTutorialMenu extends GraphicsPane {
	/* you will use program to get access to all of the GraphicsProgram calls */
	private MainApplication program;

	/* button instantiation list */
	private GImage powerUpStrengthTut;
	private GImage corruptedStrengthTut;
	private GImage evilStrengthTut;
	private GImage powerUpEndurance;
	private GImage corruptedEndurance;
	private GImage evilEndurance;
	private GImage powerUpSpeed;
	private GImage corruptedSpeed;
	private GImage evilSpeed;
	private GImage attackOrb;
	private GImage closeButton;
	private GRect backdrop;
	private GButton mainMenu;
	private GButton muteButton;
	private GImage popup = null;

	/* instantiate and initalize graphical elements */
	private GImage background = new GImage("com/edd/osvaldo/brick4.jpg");
	private GImage title = new GImage("com/edd/osvaldo/CircleBrawl.gif", program.WINDOW_WIDTH / 2,
			program.WINDOW_HEIGHT / 2);
	private boolean soundPaused = false;

	public PowerUpTutorialMenu(MainApplication app) { // not sure if I'll be using MainApplication
		super();
		program = app;

		title.setLocation((program.WINDOW_WIDTH - title.getWidth()) / 2, program.WINDOW_HEIGHT / 2 - 200);
		backdrop = new GRect(title.getX(), title.getY() + title.getHeight() + 20, title.getWidth(), 200);
		backdrop.setFillColor(Color.GRAY);
		backdrop.setFilled(true);

		// initialize the power up strength tutorial button
		powerUpStrengthTut = new GImage("com/edd/powerup/powerup_strength.png");
		powerUpStrengthTut.setLocation(title.getX() + 20,
				(program.WINDOW_HEIGHT - powerUpStrengthTut.getHeight()) / 2 - 50);

		// initialize the corrupted strength button
		corruptedStrengthTut = new GImage("com/edd/powerup/corrupted_powerup_strength.png");
		corruptedStrengthTut.setLocation(title.getX() + 125,
				(program.WINDOW_HEIGHT - powerUpStrengthTut.getHeight()) / 2 - 50);

		// initialize the evil strength button
		evilStrengthTut = new GImage("com/edd/powerup/evil_powerup_strength.png");
		evilStrengthTut.setLocation((program.WINDOW_WIDTH - evilStrengthTut.getWidth()) / 2,
				(program.WINDOW_HEIGHT - powerUpStrengthTut.getHeight()) / 2 - 50);

		// initialize the power up endurance button
		powerUpEndurance = new GImage("com/edd/powerup/powerup_endurance.png");
		powerUpEndurance.setLocation((program.WINDOW_WIDTH - evilStrengthTut.getWidth()) / 2 + 125,
				(program.WINDOW_HEIGHT - powerUpStrengthTut.getHeight()) / 2 - 50);

		// initialize the corrupted endurance button
		corruptedEndurance = new GImage("com/edd/powerup/corrupted_powerup_endurance.png");
		corruptedEndurance.setLocation(title.getX() + title.getWidth() - 20 - corruptedEndurance.getWidth(),
				(program.WINDOW_HEIGHT - powerUpStrengthTut.getHeight()) / 2 - 50);

		// initialize the evil endurance tutorial button
		evilEndurance = new GImage("com/edd/powerup/evil_powerup_endurance.png");
		evilEndurance.setLocation(title.getX() + 20, (program.WINDOW_HEIGHT - powerUpStrengthTut.getHeight()) / 2 + 50);

		// initialize the power up speed button
		powerUpSpeed = new GImage("com/edd/powerup/powerup_speed.png");
		powerUpSpeed.setLocation(title.getX() + 125, (program.WINDOW_HEIGHT - powerUpStrengthTut.getHeight()) / 2 + 50);

		// initialize the corrupted speed button
		corruptedSpeed = new GImage("com/edd/powerup/corrupted_powerup_speed.png");
		corruptedSpeed.setLocation((program.WINDOW_WIDTH - corruptedSpeed.getWidth()) / 2,
				(program.WINDOW_HEIGHT - powerUpStrengthTut.getHeight()) / 2 + 50);

		// initialize the evil speed button
		evilSpeed = new GImage("com/edd/powerup/evil_powerup_speed.png");
		evilSpeed.setLocation((program.WINDOW_WIDTH - evilStrengthTut.getWidth()) / 2 + 125,
				(program.WINDOW_HEIGHT - powerUpStrengthTut.getHeight()) / 2 + 50);

		// initialize the attackOrb button
		attackOrb = new GImage("com/edd/powerup/powerup_attackorb.png");
		attackOrb.setLocation(title.getX() + title.getWidth() - 20 - attackOrb.getWidth(),
				(program.WINDOW_HEIGHT - powerUpStrengthTut.getHeight()) / 2 + 50);

		// initialize the mute button
		muteButton = new GButton("Mute", (program.WINDOW_WIDTH + 700) / 2, (program.WINDOW_HEIGHT + 600) / 2, 50, 50);
		muteButton.setFillColor(Color.GREEN);

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
		program.add(muteButton);
		program.add(mainMenu);
		program.add(backdrop);
		program.add(powerUpStrengthTut);
		program.add(corruptedStrengthTut);
		program.add(evilStrengthTut);
		program.add(powerUpEndurance);
		program.add(corruptedEndurance);
		program.add(evilEndurance);
		program.add(powerUpSpeed);
		program.add(corruptedSpeed);
		program.add(evilSpeed);
		program.add(attackOrb);
	}

	@Override
	public void hideContents() {
		program.remove(background);
		program.remove(title);
		program.remove(muteButton);
		program.remove(mainMenu);
		program.remove(backdrop);
		program.remove(powerUpStrengthTut);
		program.remove(corruptedStrengthTut);
		program.remove(evilStrengthTut);
		program.remove(powerUpEndurance);
		program.remove(corruptedEndurance);
		program.remove(evilEndurance);
		program.remove(powerUpSpeed);
		program.remove(corruptedSpeed);
		program.remove(evilSpeed);
		program.remove(attackOrb);
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
		if (popup != null && obj == closeButton) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			program.remove(closeButton);
			program.remove(popup);
			popup = null;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());

		if (obj == mainMenu) {
			program.switchToMenu();
		}
		if (obj == powerUpStrengthTut) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			popup = new GImage("com/edd/tutorial/strengthTutPopup.jpg");
			popup.setSize(500, 500);
			popup.setLocation((program.WINDOW_WIDTH - popup.getWidth()) / 2,
					(program.WINDOW_HEIGHT - popup.getHeight()) / 2);
			program.add(popup);
			program.add(closeButton);
		}
		if (obj == corruptedStrengthTut) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			popup = new GImage("com/edd/tutorial/corruptedStrengthTutPopup.jpg");
			popup.setSize(500, 500);
			popup.setLocation((program.WINDOW_WIDTH - popup.getWidth()) / 2,
					(program.WINDOW_HEIGHT - popup.getHeight()) / 2);
			program.add(popup);
			program.add(closeButton);
		}
		if (obj == evilStrengthTut) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			popup = new GImage("com/edd/tutorial/evilStrengthTutPopup.jpg");
			popup.setSize(500, 500);
			popup.setLocation((program.WINDOW_WIDTH - popup.getWidth()) / 2,
					(program.WINDOW_HEIGHT - popup.getHeight()) / 2);
			program.add(popup);
			program.add(closeButton);
		}
		if (obj == powerUpEndurance) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			popup = new GImage("com/edd/tutorial/enduranceTutPopup.jpg");
			popup.setSize(500, 500);
			popup.setLocation((program.WINDOW_WIDTH - popup.getWidth()) / 2,
					(program.WINDOW_HEIGHT - popup.getHeight()) / 2);
			program.add(popup);
			program.add(closeButton);
		}
		if (obj == corruptedEndurance) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			popup = new GImage("com/edd/tutorial/corruptedEnduranceTutPopup.jpg");
			popup.setSize(500, 500);
			popup.setLocation((program.WINDOW_WIDTH - popup.getWidth()) / 2,
					(program.WINDOW_HEIGHT - popup.getHeight()) / 2);
			program.add(popup);
			program.add(closeButton);
		}
		if (obj == evilEndurance) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			popup = new GImage("com/edd/tutorial/evilEnduranceTutPopup.jpg");
			popup.setSize(500, 500);
			popup.setLocation((program.WINDOW_WIDTH - popup.getWidth()) / 2,
					(program.WINDOW_HEIGHT - popup.getHeight()) / 2);
			program.add(popup);
			program.add(closeButton);
		}
		if (obj == powerUpSpeed) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			popup = new GImage("com/edd/tutorial/speedTutPopup.jpg");
			popup.setSize(500, 500);
			popup.setLocation((program.WINDOW_WIDTH - popup.getWidth()) / 2,
					(program.WINDOW_HEIGHT - popup.getHeight()) / 2);
			program.add(popup);
			program.add(closeButton);
		}
		if (obj == corruptedSpeed) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			popup = new GImage("com/edd/tutorial/corruptedSpeedTutPopup.jpg");
			popup.setSize(500, 500);
			popup.setLocation((program.WINDOW_WIDTH - popup.getWidth()) / 2,
					(program.WINDOW_HEIGHT - popup.getHeight()) / 2);
			program.add(popup);
			program.add(closeButton);
		}
		if (obj == evilSpeed) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			popup = new GImage("com/edd/tutorial/evilSpeedTutPopup.jpg");
			popup.setSize(500, 500);
			popup.setLocation((program.WINDOW_WIDTH - popup.getWidth()) / 2,
					(program.WINDOW_HEIGHT - popup.getHeight()) / 2);
			program.add(popup);
			program.add(closeButton);
		}
		if (obj == attackOrb) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			popup = new GImage("com/edd/tutorial/attackOrbTutPopup.jpg");
			popup.setSize(500, 500);
			popup.setLocation((program.WINDOW_WIDTH - popup.getWidth()) / 2,
					(program.WINDOW_HEIGHT - popup.getHeight()) / 2);
			program.add(popup);
			program.add(closeButton);
		}

	}
}