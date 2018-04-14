package com.edd.map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import acm.graphics.GImage;

public abstract class MapBuilder {
	
	public static final int MIN_ROWS = 30;
	public static final int MAX_ROWS = 60;
	public static final int MIN_COLS = 30;
	public static final int MAX_COLS = 60;
	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 32;
	public static final GImage TILE_SET = new GImage("com/edd/map/spritesheetscomplete.png");
	
	public static Map buildMap(){
		Random rand = new Random();
		int rows = rand.nextInt(MAX_ROWS-MIN_ROWS+1)+MIN_ROWS;
		int cols = rand.nextInt(MAX_COLS-MIN_COLS+1)+MIN_COLS;
		GImage[][] map = new GImage[rows][cols];
		
		for(int row=0;row<rows;row++){
			for(int col=0;col<cols;col++){
				map[row][col] = getTile(0,2);
			}
		}
		
		return new Map(map,TILE_WIDTH,TILE_HEIGHT);
	}
	
	private static GImage getTile(int row, int col){
		BufferedImage tileSet = new BufferedImage((int)TILE_SET.getWidth(),(int)TILE_SET.getHeight(),BufferedImage.TYPE_INT_RGB);
		Graphics g = tileSet.createGraphics();
		g.drawImage(TILE_SET.getImage(), 0, 0, null);
		g.dispose();
		return new GImage(tileSet.getSubimage(row*TILE_WIDTH, col*TILE_WIDTH, TILE_WIDTH, TILE_HEIGHT));
	}
	
}
