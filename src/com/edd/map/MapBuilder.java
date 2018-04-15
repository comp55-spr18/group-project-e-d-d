package com.edd.map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import acm.graphics.GImage;

public abstract class MapBuilder {
	
	public static final GImage TILE_SET = new GImage("com/edd/map/spritesheet.png");
	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 32;
	
	public static final int TILES_IN_MAP_X = 50;
	public static final int TILES_IN_MAP_Y = 50;
	public static final int TILE_BUFFER_X = 5;
	public static final int TILE_BUFFER_Y = 5;
	
	public static final int ROWS_IN_SET = (int)TILE_SET.getWidth()/TILE_WIDTH;
	public static final int COLS_IN_SET = (int)TILE_SET.getHeight()/TILE_HEIGHT;
	public static final int TOTAL_TILES = ROWS_IN_SET*COLS_IN_SET;
	
	public static HashMap<Integer,GImage> tileImages = new HashMap<Integer,GImage>();
	
	public static Map buildMap(String mapFile, int baselineID){
		if(tileImages.isEmpty())
			buildTileImages(baselineID);
		GImage[][] map = new GImage[TILES_IN_MAP_X][TILES_IN_MAP_Y];
		
		try {
			FileReader fileReader = new FileReader(mapFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line = null;
			int col = 0;
			
			while((line = bufferedReader.readLine()) != null){
				String[] elements = line.split(",");
				for(int row=0;row< TILES_IN_MAP_X;row++){
					if (row < elements.length)
					{
						int id = Integer.parseInt(elements[row]);
						GImage baseImage = tileImages.get(baselineID);
						GImage primaryImage = tileImages.get(id);
						if(primaryImage != null && baseImage != null){
							map[row][col] = layerImages(primaryImage, baseImage);
						} else {
							System.out.println(id+"_err");
							map[row][col] = tileImages.get(baselineID);
						}
					}
					
				}
				col++;
			}
			
			bufferedReader.close();
			
		} catch(FileNotFoundException ex){
			System.out.println("Unable to open map: "+mapFile);
		} catch(IOException ex){
			System.out.println("Error reading map: "+mapFile);
		} catch(NumberFormatException ex){
			System.out.println("Error reading map: "+mapFile+" -- ID value is not number?");
		}
		
		return new Map(map,TILE_WIDTH,TILE_HEIGHT,tileImages.get(baselineID));
	}
	
	private static void buildTileImages(int baselineID){
		try {
			FileReader fileReader = new FileReader("com/edd/map/spritesheet.csv");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line = null;
			int row = 0;
			
			while((line = bufferedReader.readLine()) != null){
				String[] elements = line.split(",");
				System.out.println(elements.length);
				for(int col=0;col<elements.length;col++){
					int id = Integer.parseInt(elements[col]);
					tileImages.put(id, getTile(row,col));
				}
				row++;
			}

			tileImages.put(-1, tileImages.get(baselineID)); // for non-existent tiles
			
			bufferedReader.close();
			
		} catch(FileNotFoundException ex){
			System.out.println("Unable to open spritesheet.csv!");
		} catch(IOException ex){
			System.out.println("Error reading map spritesheet.csv!");
		} catch(NumberFormatException ex){
			System.out.println("Error reading map spritesheet.csv! -- ID value is not number?");
		}
	}
	
	private static GImage layerImages(GImage primaryImage, GImage baseImage){
		BufferedImage result = new BufferedImage(TILE_WIDTH,TILE_HEIGHT,BufferedImage.TYPE_INT_RGB);
		Graphics g = result.createGraphics();
		g.drawImage(baseImage.getImage(), 0, 0, null);
		g.drawImage(primaryImage.getImage(), 0, 0, null);
		g.dispose();
		return new GImage(result);
	}

	private static GImage getTile(int row, int col){
		BufferedImage tileSet = new BufferedImage((int)TILE_SET.getWidth(),(int)TILE_SET.getHeight(),BufferedImage.TYPE_INT_ARGB);
		Graphics g = tileSet.createGraphics();
		g.drawImage(TILE_SET.getImage(), 0, 0, null);
		g.dispose();
		return new GImage(tileSet.getSubimage(col*TILE_WIDTH, row*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT));
	}
	
}