package com.edd.osvaldo;
import java.awt.Color;
import java.awt.event.MouseEvent;

import com.edd.character.AI;
import com.edd.character.Player;

import acm.graphics.GImage;


public class SomePane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	
	private GImage background;
	private GParagraph para;
	

	public SomePane(MainApplication app) {
		this.program = app;
		program.player = new Player("Mike", program.WINDOW_WIDTH / 2 - 100, program.WINDOW_HEIGHT / 2 - 100, program);
		
		background = new GImage("com/edd/osvaldo/game_background.png");
		program.player.getNameLabel().setColor(Color.WHITE);
	}

	@Override
	public void showContents() {
		program.add(background);
		program.add(program.player.getNameLabel());
		//program.POWERUP_GEN.spawn();
	}

	@Override
	public void hideContents() {
		//program.remove(program.player.getSprite());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//para.setText("you need\nto click\non the eyes\nto go back");
		program.player.mousePressed(e);
		program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[4]);
//		GObject obj = program.getElementAt(e.getX(), e.getY());
//		if (obj == background) {
//			program.switchToMenu();
//		}
	}

}
