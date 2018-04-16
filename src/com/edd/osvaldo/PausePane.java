package com.edd.osvaldo;

import java.awt.Color;
import java.awt.event.MouseEvent;

import com.edd.circlebrawl.MainApplication;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class PausePane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GLabel pauseScreenHeader;
	private GButton resume;
	private GButton quit;

	public PausePane(MainApplication app) {
		super();
		program = app;
		// pauseScreenHeader = new GLabel("Game Paused", (program.WINDOW_WIDTH -
		// pauseScreenHeader.getWidth()) / 2,
		// program.WINDOW_HEIGHT / 2 - 200);
		resume = new GButton("Resume", (program.WINDOW_WIDTH - 200) / 2, (program.WINDOW_HEIGHT - 200) / 2, 200, 100);
		resume.setFillColor(Color.GREEN);
		quit = new GButton("Quit", (program.WINDOW_WIDTH - 200) / 2, ((program.WINDOW_HEIGHT - 200) / 2) + 130, 200,
				100);
		quit.setFillColor(Color.GREEN);

	}

	@Override
	public void showContents() {
		// program.add(pauseScreenHeader);
		program.add(resume);
		program.add(quit);
	}

	@Override
	public void hideContents() {
		// program.remove(pauseScreenHeader);
		program.remove(resume);
		program.remove(quit);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());

		if (obj == resume) {
			// program.getSomePane().setGamePaused(false);
			// program.getSomePane().setSoundPaused(false);
			program.switchToSome();
		} else if (obj == quit) {
			// program.getSomePane().setSoundPaused(false);
			// program.getSomePane().hideContents();
			program.switchToMenu();
		}

	}
}