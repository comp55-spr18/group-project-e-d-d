package com.edd.tutorial;

import java.awt.event.MouseEvent;

import com.edd.circlebrawl.MainApplication;
import com.edd.osvaldo.GButton;
import com.edd.osvaldo.GraphicsPane;

//dummy comment

public class PopUpWindow extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls

	private GButton popup; // singleplayer option

	public PopUpWindow(MainApplication app) {
		super();
		program = app;
		popup = new GButton("This is a test string", (program.WINDOW_WIDTH - 300) / 2,
				(program.WINDOW_HEIGHT - 150) / 2, 500, 500);
		// popup.setFillColor(Color.GREEN);
	}

	@Override
	public void showContents() {
		program.add(popup);
	}

	@Override
	public void hideContents() {
		program.remove(popup);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		hideContents();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		hideContents();
	}
}