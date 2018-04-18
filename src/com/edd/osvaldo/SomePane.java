package com.edd.osvaldo;

import java.awt.Color;
import java.awt.event.MouseEvent;

import com.edd.character.Player;
import com.edd.circlebrawl.MainApplication;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class SomePane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GImage background;
	private GParagraph para;
	private GButton muteButton = new GButton("Mute", (program.WINDOW_WIDTH + 700) / 2,
			(program.WINDOW_HEIGHT + 600) / 2, 50, 50);
	private GButton pauseButton = new GButton("Pause", (program.WINDOW_WIDTH + 900) / 2,
			(program.WINDOW_HEIGHT + 600) / 2, 50, 50);
	private GButton resume = new GButton("Resume", ((program.WINDOW_WIDTH - 200) / 2) - 150,
			(program.WINDOW_HEIGHT - 200) / 2, 200, 100);
	private GButton quit = new GButton("Quit", ((program.WINDOW_WIDTH - 200) / 2) + 150,
			(program.WINDOW_HEIGHT - 200) / 2, 200, 100);

	private boolean soundPaused = false;
	private boolean gamePaused = false;

	public SomePane(MainApplication app) {
		this.program = app;
		program.player = new Player("Mike", app.getMapWidth() / 2 - 100, app.getMapHeight() / 2 - 100, app);
		muteButton.setFillColor(Color.GREEN);
		program.muteButton = this.muteButton;
		pauseButton.setFillColor(Color.GREEN);
		program.pauseButton = this.pauseButton;
		background = app.getBackgroundImage();
		background.move(program.player.getCam().getTotalTranslationX(), program.player.getCam().getTotalTranslationY());
		program.player.getNameLabel().setColor(Color.WHITE);
		resume.setFillColor(Color.GREEN);
		quit.setFillColor(Color.GREEN);
	}

	@Override
	public void showContents() {
		program.add(background);
		program.add(program.player.getNameLabel());
		program.add(muteButton);
		program.add(pauseButton);
		// program.POWERUP_GEN.spawn();
	}

	@Override
	public void hideContents() {
		program.remove(muteButton);
		program.remove(pauseButton);
		// program.remove(program.player.getSprite());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (gamePaused) {
			checkResume(obj);
			checkQuit(obj);
		}
		// mute button interaction
		toggleMuteButton(obj);
		// pause button interaction
		togglePauseButton(obj);
		// prevents the saw from engaging if you selected a valid button
		if (obj == muteButton || obj == pauseButton || obj == resume || obj == quit || gamePaused)
			return;
		program.player.mousePressed(e);
		program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[4]);
		// GObject obj = program.getElementAt(e.getX(), e.getY());
		// if (obj == background) {
		// program.switchToMenu();
		// }
	}

	/**
	 * Checks if the mute button was selected. If so, executed correlating
	 * instructions. Returns a boolean value reflecting whether it was selected or
	 * not.
	 * 
	 * @param obj
	 *            The object selected
	 * @return A Boolean value reflecting whether it was selected or not
	 */
	public boolean toggleMuteButton(GObject obj) {
		if (obj == muteButton && !soundPaused && !gamePaused) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);// Button Click
			program.audio.pauseSound(program.MUSIC_FOLDER, program.SOUND_FILES[3]);
			soundPaused = true;
			muteButton.setFillColor(Color.GRAY);
			return true;
		} else if (obj == muteButton && soundPaused && !gamePaused) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);// Button Click
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[3]);
			soundPaused = false;
			muteButton.setFillColor(Color.GREEN);
			return true;
		} else if (obj == muteButton && !soundPaused && gamePaused) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]); // Button Click
			program.audio.pauseSound(program.MUSIC_FOLDER, program.SOUND_FILES[6]);
			soundPaused = true;
			muteButton.setFillColor(Color.GRAY);
			return true;
		} else if (obj == muteButton && soundPaused && gamePaused) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);// Button Click
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[6]);
			soundPaused = false;
			muteButton.setFillColor(Color.GREEN);
			return true;
		}

		return false;
	}

	/**
	 * Checks if the pause button was selected. If so, executed correlating
	 * instructions. Returns a boolean value reflecting whether it was selected or
	 * not.
	 * 
	 * @param obj
	 *            The object selected
	 * @return A Boolean value reflecting whether it was selected or not
	 */
	public boolean togglePauseButton(GObject obj) {
		if (obj == pauseButton && !soundPaused) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			program.audio.pauseSound(program.MUSIC_FOLDER, program.SOUND_FILES[3]);
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[6]);
			gamePaused = true;
			pause();
			return true;
		} else if (obj == pauseButton) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			gamePaused = true;
			pause();
			return true;
		} else
			return false;
	}

	public boolean getPauseStatus() {
		return this.gamePaused;
	}

	public void checkResume(GObject obj) {
		if (obj == resume) {
			unpause();
		}
	}

	public void checkQuit(GObject obj) { // Not working need to revisit
		if (obj == quit) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			System.exit(0);
			// program.remove(resume);
			// program.remove(quit);
			// program.removeAll();
			// program.switchToMenu();
		}
	}

	public void pause() {
		program.add(resume);
		program.add(quit);
	}

	public void unpause() {
		gamePaused = false;
		program.remove(resume);
		program.remove(quit);
		if (!soundPaused) {
			program.audio.pauseSound(program.MUSIC_FOLDER, program.SOUND_FILES[6]); // Stop pause menu music
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[3]); // Play start menu music
		}
	}

	public void bringAllToFront() {
		pauseButton.sendToFront();
		muteButton.sendToFront();
		if (gamePaused) {
			resume.sendToFront();
			quit.sendToFront();
		}
	}
}
