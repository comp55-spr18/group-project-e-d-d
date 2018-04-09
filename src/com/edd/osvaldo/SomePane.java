package com.edd.osvaldo;
import java.awt.event.MouseEvent;

import com.edd.circlebrawl.Player;
import com.edd.circlebrawl.Tick;

import acm.graphics.GImage;
import acm.graphics.GObject;


public class SomePane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	
	private GImage img;
	private GParagraph para;
	

	public SomePane(MainApplication app) {
		this.program = app;
		program.player = new Player(program.WINDOW_WIDTH / 2 - 100, program.WINDOW_HEIGHT / 2 - 100, program);
		img = new GImage("robot head.jpg", 100, 100);
		para = new GParagraph("welcome\nto my\nsecret room!", 150, 300);
		para.setFont("Arial-24");
	}

	@Override
	public void showContents() {
		//program.POWERUP_GEN.spawn();
	}

	@Override
	public void hideContents() {
		//program.remove(program.player.getSprite());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		para.setText("you need\nto click\non the eyes\nto go back");
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == img) {
			program.switchToMenu();
		}
	}

}
