package com.edd.osvaldo;
import java.awt.Color;
import java.awt.event.MouseEvent;

import com.edd.circlebrawl.MainApplication;
import com.edd.circlebrawl.MultiplayerSam_Test;
import com.edd.circlebrawl.Tick;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

//dummy comment

public class MenuPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GButton rect;
	private GButton rect2;
	private GButton muteButton;
	private GImage background = new GImage("com/edd/osvaldo/brick4.jpg");
	private GImage title = new GImage("com/edd/osvaldo/CircleBrawl.gif", program.WINDOW_WIDTH/2, program.WINDOW_HEIGHT/2);
	private GLabel sign = new GLabel("Under Construction?", ((program.WINDOW_WIDTH - 200) / 2) + 40, ((program.WINDOW_HEIGHT - 200) / 2) + 210);
	private boolean soundPaused = false;
	
	public MenuPane(MainApplication app) {
		super();
		program = app;
		
//		try {
//		     GraphicsEnvironment ge = 
//		         GraphicsEnvironment.getLocalGraphicsEnvironment();
//		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("/Fonts/Sugar Death2.tff"))); //not sure if this path format is right, so fix as you need
//		} catch (IOException|FontFormatException e) {
//		     // Optionally you can have a handle  for the exception, or no try block at all
//		}
		
//		title = new GLabel("Circle Brawl" , program.WINDOW_WIDTH/2 , program.WINDOW_HEIGHT/2);
//		title.setFont("Sugar Death2-68");
//		title.setLocation((program.WINDOW_WIDTH- title.getWidth())/2, (program.WINDOW_HEIGHT/2) - 200);
		title.setLocation((program.WINDOW_WIDTH - title.getWidth())/2, program.WINDOW_HEIGHT/2 - 200);
		rect = new GButton("Singleplayer", (program.WINDOW_WIDTH - 200) / 2, (program.WINDOW_HEIGHT - 200) / 2, 200, 100);
		rect.setFillColor(Color.GREEN);
		
		//the mute button GButton
		muteButton = new GButton("Mute", (program.WINDOW_WIDTH + 700) / 2, (program.WINDOW_HEIGHT + 600) / 2, 50, 50);
		muteButton.setFillColor(Color.GREEN);
		
		rect2 = new GButton("Multiplayer", (program.WINDOW_WIDTH - 200) / 2, ((program.WINDOW_HEIGHT - 200) / 2) + 130, 200, 100);
		rect2.setFillColor(Color.GRAY);
	}

	@Override
	public void showContents() {
		program.add(background);
		program.add(rect);
		program.add(rect2);
		program.add(sign);
		program.add(title);
		program.add(muteButton);
	}

	@Override
	public void hideContents() {
		program.remove(background);
		program.remove(rect);
		program.remove(title);
		program.remove(rect2);
		program.remove(muteButton);
		program.remove(sign);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());

		//mute button interaction
		if (obj == muteButton && !soundPaused) {
			program.audio.pauseSound(program.MUSIC_FOLDER, program.SOUND_FILES[2]);
			soundPaused = true;
			muteButton.setFillColor(Color.GRAY);
		}
		else if (obj == muteButton && soundPaused){
			program.audio.playSound(program.MUSIC_FOLDER, program.SOUND_FILES[2]);
			soundPaused = false;
			muteButton.setFillColor(Color.GREEN);
		}	
		
		if (obj == rect2) {
			program.removeAll();
			MultiplayerSam_Test m = new MultiplayerSam_Test();
			m.init();
			//m.run();
			
	  	}
		
		if (obj == rect) {
			program.test = true;
			program.audio.stopSound(program.MUSIC_FOLDER, program.SOUND_FILES[2]);
			program.switchToSome();
		}
	}
}