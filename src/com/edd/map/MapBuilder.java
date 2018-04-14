package com.edd.map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import acm.graphics.GImage;

public abstract class MapBuilder {
	
	public static final GImage TILE_SET = new GImage("com/edd/map/spritesheetscomplete.png");
	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 32;
	public static final int ROWS_IN_SET = (int)TILE_SET.getWidth()/TILE_WIDTH;
	public static final int COLS_IN_SET = (int)TILE_SET.getHeight()/TILE_HEIGHT;
	public static final int TOTAL_TILES = ROWS_IN_SET*COLS_IN_SET;
	
	public static Map buildMap(String mapFile, int rows, int cols, int baselineID){
		GImage[][] map = new GImage[rows][cols];
		GImage baselineImage = getTileFromID(baselineID);
		
		try {
			FileReader fileReader = new FileReader(mapFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line = null;
			int row = 0;
			
			while((line = bufferedReader.readLine()) != null){
				String[] elements = line.split(",");
				for(int col=0;col<elements.length;col++){
					int id = Integer.parseInt(elements[col]);
					GImage baseImage = new GImage(baselineImage.getImage());
					GImage primaryImage = getTileFromID(id);
					map[col][row] = layerImages(primaryImage, baseImage);
				}
				row++;
			}
			
			bufferedReader.close();
			
		} catch(FileNotFoundException ex){
			System.out.println("Unable to open map: "+mapFile);
		} catch(IOException ex){
			System.out.println("Error reading map: "+mapFile);
		} catch(NumberFormatException ex){
			System.out.println("Error reading map: "+mapFile+" -- ID value is not number?");
		}
		
		return new Map(map,TILE_WIDTH,TILE_HEIGHT);
	}
	
	private static GImage layerImages(GImage primaryImage, GImage baseImage){
		BufferedImage result = new BufferedImage(TILE_WIDTH,TILE_HEIGHT,BufferedImage.TYPE_INT_RGB);
		Graphics g = result.createGraphics();
		g.drawImage(baseImage.getImage(), 0, 0, null);
		g.drawImage(primaryImage.getImage(), 0, 0, null);
		g.dispose();
		return new GImage(result);
	}
	
	private static GImage getTileFromID(int id){
		int tId = 0;
		for(int col=0;col<COLS_IN_SET;col++){
			for(int row=0;row<ROWS_IN_SET;row++){
				if(tId == id)
					return getTile(row,col);
				tId++;
			}
		}
		System.out.println("ERROR: ID GREATER THAN TILE COUNT");
		return null;
	}

	private static GImage getTile(int row, int col){
		BufferedImage tileSet = new BufferedImage((int)TILE_SET.getWidth(),(int)TILE_SET.getHeight(),BufferedImage.TYPE_INT_RGB);
		Graphics g = tileSet.createGraphics();
		g.drawImage(TILE_SET.getImage(), 0, 0, null);
		g.dispose();
		return new GImage(tileSet.getSubimage(row*TILE_WIDTH, col*TILE_WIDTH, TILE_WIDTH, TILE_HEIGHT));
	}
	
}