package com.edd.map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import acm.graphics.GImage;

public class MapBuilder {
	
	public static final GImage TILE_SET = new GImage("com/edd/map/V3spritesheet.png");
	public static final int TILE_WIDTH = 256;
	public static final int TILE_HEIGHT = 256;
	
	public static final int ROWS_IN_SET = (int)TILE_SET.getWidth()/TILE_WIDTH;
	public static final int COLS_IN_SET = (int)TILE_SET.getHeight()/TILE_HEIGHT;
	public static final int TOTAL_TILES = ROWS_IN_SET*COLS_IN_SET;
	
	private HashMap<Integer,GImage> tileImages = new HashMap<Integer,GImage>();
	private BufferedImage tileSetImage = new BufferedImage(ROWS_IN_SET*TILE_WIDTH,COLS_IN_SET*TILE_HEIGHT,BufferedImage.TYPE_INT_RGB);
	
	public int mapWidth;
	public int mapHeight;
	public int tilesInMapX;
	public int tilesInMapY;
	public int tilesBufferX;
	public int tilesBufferY;
	
	public GImage buildMap(String mapFile, int tilesInMapX, int tilesInMapY, int tilesBufferX, int tilesBufferY, int baselineID){
		buildTileSet();
		buildTileImages(baselineID);		
		this.tilesInMapX = tilesInMapX;
		this.tilesInMapY = tilesInMapY;
		this.tilesBufferX = tilesBufferX;
		this.tilesBufferY = tilesBufferY;
		this.mapWidth = tilesInMapX*TILE_WIDTH;
		this.mapHeight = tilesInMapY*TILE_HEIGHT;
		BufferedImage mapImage = new BufferedImage(mapWidth,mapHeight,BufferedImage.TYPE_INT_RGB);
		Graphics g = mapImage.createGraphics();
		
		try {
			FileReader fileReader = new FileReader(mapFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line = null;
			int col = 0;
			
			while((line = bufferedReader.readLine()) != null){
				String[] elements = line.split(",");
				for(int row=0;row< tilesInMapX;row++){
					if (row < elements.length)
					{
						int id = Integer.parseInt(elements[row]);
						if(tileImages.containsKey(id)) {
							g.drawImage(tileImages.get(id).getImage(),col*TILE_HEIGHT,row*TILE_WIDTH, null);
						} else {
							System.out.println("ERROR: No ID equivalent: "+id);
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
		
		g.dispose();
		return new GImage(mapImage);
		//return new Map(map,tilesInMapX,tilesInMapY,tilesBufferX,tilesBufferY,TILE_WIDTH,TILE_HEIGHT,tileImages.get(baselineID));
	}
	
	private void buildTileSet() {
		Graphics g = tileSetImage.createGraphics();
		g.drawImage(TILE_SET.getImage(), 0, 0, null);
		g.dispose();
	}
	
	private void buildTileImages(int baselineID){
		try {
			FileReader fileReader = new FileReader("com/edd/map/V3Tileset.csv");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line = null;
			int row = 0;
			
			while((line = bufferedReader.readLine()) != null){
				String[] elements = line.split(",");
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

	private GImage getTile(int row, int col){
		return new GImage(tileSetImage.getSubimage(col*TILE_WIDTH, row*TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT));
	}
	
}