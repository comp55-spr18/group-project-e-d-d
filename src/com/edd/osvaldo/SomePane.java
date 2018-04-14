package com.edd.osvaldo;
import java.awt.Color;
import java.awt.event.MouseEvent;

import com.edd.character.AI;
import com.edd.character.Player;
import com.edd.map.Map;
import com.edd.map.MapBuilder;

import acm.graphics.GImage;
import acm.graphics.GObject;



public class SomePane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private Map map = MapBuilder.buildMap();
	
	private GImage background = map.createImage();
	private GParagraph para;
	GButton muteButton = new GButton("Mute", (program.WINDOW_WIDTH + 700) / 2, (program.WINDOW_HEIGHT + 600) / 2, 50, 50);
	private boolean soundPaused = false;
	

	public SomePane(MainApplication app) {
		this.program = app;
		program.player = new Player("Mike", program.WINDOW_WIDTH / 2 - 100, program.WINDOW_HEIGHT / 2 - 100, program);
		muteButton.setFillColor(Color.GREEN);
		
		program.player.getNameLabel().setColor(Color.WHITE);
	}

	@Override
	public void showContents() {
		program.add(background);
		program.add(program.player.getNameLabel());
		program.add(muteButton);
		//program.POWERUP_GEN.spawn();
	}

	@Override
	public void hideContents() {
		program.remove(muteButton);
		//program.remove(program.player.getSprite());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//para.setText("you need\nto click\non the eyes\nto go back");
		GObject obj = program.getElementAt(e.getX(), e.getY());
		
		//mute button interaction
		if (obj == muteButton && !soundPaused) {
			program.audio.pauseSound(program.MUSIC_FOLDER, program.SOUND_FILES[3]);
			soundPaused = true;
			muteButton.setFillColor(Color.GRAY);
		}
		else if (obj == muteButton && soundPaused){
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[3]);
			soundPaused = false;
			muteButton.setFillColor(Color.GREEN);
		}	
		program.player.mousePressed(e);
		program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[4]);
//		GObject obj = program.getElementAt(e.getX(), e.getY());
//		if (obj == background) {
//			program.switchToMenu();
//		}
	}

}
