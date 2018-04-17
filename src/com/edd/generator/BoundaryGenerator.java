package com.edd.generator;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.edd.circlebrawl.MainApplication;
import com.edd.map.MapBuilder;
import com.edd.obstacle.Obstacle;

import acm.graphics.GImage;

public class BoundaryGenerator extends BaseGenerator {

	boolean hasSpawned = false;
	
	public BoundaryGenerator(MainApplication driver) {
		this.driver = driver;
		maxSpawns = 0;
		spawnDelay = 0;		
		activated = true;
	}
	
	// SPAWN MUST BE CALLED MANUALLY
	
	@Override
	public void spawn() {
		if(!hasSpawned){
			activated = false;
			
			int tilesBufferX = driver.getTilesBufferX();
			int tilesBufferY = driver.getTilesBufferY();
			int tilesInMapX = driver.getTilesInMapX();
			int tilesInMapY = driver.getTilesInMapY();
			int mapWidth = driver.getMapWidth();
			int mapHeight = driver.getMapHeight();
			int tileWidth = MapBuilder.TILE_WIDTH;
			int tileHeight = MapBuilder.TILE_HEIGHT;
			int totalBufferX = tilesBufferX*2;
			int totalBufferY = tilesBufferY*2;
			
			GImage horizontalBoundary = new GImage("com/edd/obstacle/barbedWire.png");
			GImage verticalBoundary = new GImage("com/edd/obstacle/barbedWireV.png");
			
			BufferedImage northBoundaryImage = new BufferedImage(tileWidth*tilesInMapX-totalBufferX*tileWidth,tileHeight,BufferedImage.TYPE_INT_ARGB);
			BufferedImage southBoundaryImage = new BufferedImage(tileWidth*tilesInMapX-totalBufferX*tileWidth,tileHeight,BufferedImage.TYPE_INT_ARGB);
			BufferedImage westBoundaryImage = new BufferedImage(tileWidth,tileHeight*tilesInMapY-totalBufferY*tileHeight,BufferedImage.TYPE_INT_ARGB);
			BufferedImage eastBoundaryImage = new BufferedImage(tileWidth,tileHeight*tilesInMapY-totalBufferY*tileHeight,BufferedImage.TYPE_INT_ARGB);
			
			Graphics ng = northBoundaryImage.createGraphics();
			Graphics sg = southBoundaryImage.createGraphics();
			Graphics wg = westBoundaryImage.createGraphics();
			Graphics eg = eastBoundaryImage.createGraphics();
			
			for(int i=0;i<(tilesInMapX-totalBufferX)/3;i++){
				ng.drawImage(horizontalBoundary.getImage(),i*tileWidth*3,0,null);
				sg.drawImage(horizontalBoundary.getImage(),i*tileWidth*3,0,null);
			}
			for(int i=0;i<(tilesInMapY-totalBufferY)/3;i++){
				wg.drawImage(verticalBoundary.getImage(),0,i*tileHeight*3,null);
				eg.drawImage(verticalBoundary.getImage(),0,i*tileHeight*3,null);
			}
			
			ng.dispose();
			sg.dispose();
			wg.dispose();
			eg.dispose();
			
			GImage northBoundary = new GImage(northBoundaryImage);
			GImage southBoundary = new GImage(southBoundaryImage);
			GImage westBoundary = new GImage(westBoundaryImage);
			GImage eastBoundary = new GImage(eastBoundaryImage);
			
			actors.add(new Obstacle(tileWidth*tilesBufferX,tileHeight*tilesBufferY,driver,northBoundary));
			actors.add(new Obstacle(tileWidth*tilesBufferX,mapHeight-tileHeight*tilesBufferY,driver,southBoundary));
			actors.add(new Obstacle(tileWidth*tilesBufferX,tileHeight*tilesBufferY,driver,westBoundary));
			actors.add(new Obstacle(mapWidth-tileWidth*tilesBufferX,tileHeight*tilesBufferY,driver,eastBoundary));
			
			hasSpawned = true;
		}
	}

}
