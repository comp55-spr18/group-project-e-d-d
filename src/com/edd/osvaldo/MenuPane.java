package com.edd.osvaldo;

import java.awt.Color;
import java.awt.event.MouseEvent;

import com.edd.circlebrawl.MainApplication;
import com.edd.circlebrawl.MultiplayerSam_Test;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

//dummy comment

public class MenuPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GButton rect; // singleplayer option
	private GButton rect2; // multiplayer option
	private GButton rect3; // tutorial option
	private GButton muteButton;
	private GButton quitButton;
	private GImage background = new GImage("com/edd/osvaldo/brick4.jpg");
	private GImage title = new GImage("com/edd/osvaldo/CircleBrawl.gif", program.WINDOW_WIDTH / 2,
			program.WINDOW_HEIGHT / 2);
	private GLabel sign = new GLabel("Under Construction?", ((program.WINDOW_WIDTH - 200) / 2) + 40,
			((program.WINDOW_HEIGHT - 200) / 2) + 210);
	private boolean soundPaused = false;

	public MenuPane(MainApplication app) {
		super();
		program = app;

		// try {
		// GraphicsEnvironment ge =
		// GraphicsEnvironment.getLocalGraphicsEnvironment();
		// ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("/Fonts/Sugar
		// Death2.tff"))); //not sure if this path format is right, so fix as you need
		// } catch (IOException|FontFormatException e) {
		// // Optionally you can have a handle for the exception, or no try block at all
		// }

		// title = new GLabel("Circle Brawl" , program.WINDOW_WIDTH/2 ,
		// program.WINDOW_HEIGHT/2);
		// title.setFont("Sugar Death2-68");
		// title.setLocation((program.WINDOW_WIDTH- title.getWidth())/2,
		// (program.WINDOW_HEIGHT/2) - 200);

		// initialize title graphic
		title.setLocation((program.WINDOW_WIDTH - title.getWidth()) / 2, program.WINDOW_HEIGHT / 2 - 200);

		// nitialize singleplayer button
		rect = new GButton("Singleplayer", (program.WINDOW_WIDTH - 200) / 2, (program.WINDOW_HEIGHT - 200) / 2, 200,
				100);
		rect.setFillColor(Color.GREEN);

		// initialize mutliplayer button
		rect2 = new GButton("Multiplayer", (program.WINDOW_WIDTH - 200) / 2, ((program.WINDOW_HEIGHT - 200) / 2) + 130,
				200, 100);
		rect2.setFillColor(Color.GRAY);

		// initilize tutorial button
		rect3 = new GButton("Tutorial", (program.WINDOW_WIDTH - 200) / 2, ((program.WINDOW_HEIGHT - 200) / 2) + 260,
				200, 100);
		rect3.setFillColor(Color.GREEN);

		// initialize the mute button
		muteButton = new GButton("Mute", (program.WINDOW_WIDTH + 700) / 2, (program.WINDOW_HEIGHT + 600) / 2, 50, 50);
		muteButton.setFillColor(Color.GREEN);

		// initialize the quit button
		quitButton = new GButton("Quit", (program.WINDOW_WIDTH + 900) / 2, (program.WINDOW_HEIGHT + 600) / 2, 50, 50);
		quitButton.setFillColor(Color.GREEN);
	}

	@Override
	public void showContents() {
		program.add(background);
		program.add(rect);
		program.add(rect2);
		program.add(rect3);
		program.add(sign);
		program.add(title);
		program.add(muteButton);
		program.add(quitButton);
	}

	@Override
	public void hideContents() {
		program.remove(background);
		program.remove(rect);
		program.remove(rect2);
		program.remove(rect3);
		program.remove(sign);
		program.remove(title);
		program.remove(muteButton);
		program.remove(quitButton);
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
		if (obj == quitButton) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			System.exit(0);
		}
		if (obj == rect3) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			program.switchToTutorialMenu();
		}
		if (obj == rect2) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			program.removeAll();
			MultiplayerSam_Test m = new MultiplayerSam_Test();
			m.init();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException ie) {
			}
			m.run();

		}
		if (obj == rect) {
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[5]);
			program.test = true;
			program.audio.stopSound(program.MUSIC_FOLDER, program.SOUND_FILES[2]);
			program.switchToSome();
		}
	}
}