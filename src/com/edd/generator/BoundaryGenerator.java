package com.edd.generator;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import com.edd.map.MapBuilder;
import com.edd.obstacle.Obstacle;
import com.edd.osvaldo.MainApplication;

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
			
			int totalBufferX = MapBuilder.TILE_BUFFER_X*2;
			int totalBufferY = MapBuilder.TILE_BUFFER_Y*2;
			
			GImage horizontalBoundary = new GImage("com/edd/obstacle/barbedWire.png");
			GImage verticalBoundary = new GImage("com/edd/obstacle/barbedWireV.png");
			
			BufferedImage northBoundaryImage = new BufferedImage(MapBuilder.TILE_WIDTH*MapBuilder.TILES_IN_MAP_X-totalBufferX,MapBuilder.TILE_HEIGHT,BufferedImage.TYPE_INT_ARGB);
			BufferedImage southBoundaryImage = new BufferedImage(MapBuilder.TILE_WIDTH*MapBuilder.TILES_IN_MAP_X-totalBufferX,MapBuilder.TILE_HEIGHT,BufferedImage.TYPE_INT_ARGB);
			BufferedImage westBoundaryImage = new BufferedImage(MapBuilder.TILE_WIDTH,MapBuilder.TILE_HEIGHT*MapBuilder.TILES_IN_MAP_Y-totalBufferY,BufferedImage.TYPE_INT_ARGB);
			BufferedImage eastBoundaryImage = new BufferedImage(MapBuilder.TILE_WIDTH,MapBuilder.TILE_HEIGHT*MapBuilder.TILES_IN_MAP_Y-totalBufferY,BufferedImage.TYPE_INT_ARGB);
			
			Graphics ng = northBoundaryImage.createGraphics();
			Graphics sg = southBoundaryImage.createGraphics();
			Graphics wg = westBoundaryImage.createGraphics();
			Graphics eg = eastBoundaryImage.createGraphics();
			
			for(int i=0;i<100;i++){
				ng.drawImage(horizontalBoundary.getImage(),i*MapBuilder.TILE_WIDTH*3,0,null);
				sg.drawImage(horizontalBoundary.getImage(),i*MapBuilder.TILE_WIDTH*3,0,null);
				wg.drawImage(verticalBoundary.getImage(),0,i*MapBuilder.TILE_HEIGHT*3,null);
				eg.drawImage(verticalBoundary.getImage(),0,i*MapBuilder.TILE_HEIGHT*3,null);
			}
			
			ng.dispose();
			sg.dispose();
			wg.dispose();
			eg.dispose();
			
			GImage northBoundary = new GImage(northBoundaryImage);
			GImage southBoundary = new GImage(southBoundaryImage);
			GImage westBoundary = new GImage(westBoundaryImage);
			GImage eastBoundary = new GImage(eastBoundaryImage);
			
			actors.add(new Obstacle(MapBuilder.TILE_WIDTH*MapBuilder.TILE_BUFFER_X,MapBuilder.TILE_HEIGHT*MapBuilder.TILE_BUFFER_Y,driver,this,northBoundary));
			actors.add(new Obstacle(MapBuilder.TILE_WIDTH*MapBuilder.TILE_BUFFER_X,MainApplication.MAP_HEIGHT-MapBuilder.TILE_HEIGHT*MapBuilder.TILE_BUFFER_Y,driver,this,southBoundary));
			actors.add(new Obstacle(MapBuilder.TILE_WIDTH*MapBuilder.TILE_BUFFER_X,MapBuilder.TILE_HEIGHT*MapBuilder.TILE_BUFFER_Y,driver,this,westBoundary));
			actors.add(new Obstacle(MainApplication.MAP_WIDTH-MapBuilder.TILE_WIDTH*MapBuilder.TILE_BUFFER_X,MapBuilder.TILE_HEIGHT*MapBuilder.TILE_BUFFER_Y,driver,this,eastBoundary));
			
			hasSpawned = true;
		}
	}

}
