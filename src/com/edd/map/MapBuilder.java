package com.edd.map;

import java.util.Random;

import acm.graphics.GImage;

public abstract class MapBuilder {
	
	public static final int MIN_ROWS = 30;
	public static final int MAX_ROWS = 60;
	public static final int MIN_COLS = 30;
	public static final int MAX_COLS = 60;
	
	public static Map buildMap(){
		Random rand = new Random();
		int rows = rand.nextInt(MAX_ROWS-MIN_ROWS+1)+MIN_ROWS;
		int cols = rand.nextInt(MAX_COLS-MIN_COLS+1)+MIN_COLS;
		GImage[][] map = new GImage[rows][cols];
		
		// CONSTRUCT MAP HERE
		
		return new Map(map,0,0);
	}
	
}
