package com.edd.osvaldo;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class MenuPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GButton rect;
	private GImage background = new GImage("com/edd/osvaldo/brick4.jpg");
	private GImage title = new GImage("com/edd/osvaldo/CircleBrawl.gif", program.WINDOW_WIDTH/2, program.WINDOW_HEIGHT/2);
	
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
		rect.setFillColor(Color.RED);
	}

	@Override
	public void showContents() {
		program.add(background);
		program.add(rect);
		program.add(title);
	}

	@Override
	public void hideContents() {
		program.remove(rect);
		program.remove(title);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == rect) {
			program.switchToSome();
		}
	}
}
