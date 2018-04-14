package com.edd.map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import acm.graphics.GImage;

public class Map {

	private int tileWidth, tileHeight;
	private GImage[][] map;
	private GImage baseImage;
	
	public Map(GImage[][] map, int tileWidth, int tileHeight, GImage baseImage){
		this.map = map;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.baseImage = baseImage;
	}
	
	public GImage createImage(){
		BufferedImage image = new BufferedImage(map.length*tileWidth, map[0].length*tileHeight,BufferedImage.TYPE_INT_RGB);
		Graphics g = image.createGraphics();
		for(int rows=0;rows<map.length;rows++){
			for(int cols=0;cols<map[rows].length;cols++){
				GImage tile = map[rows][cols];
				if(tile == null)
					tile = baseImage;
				g.drawImage(tile.getImage(), rows*tileWidth, cols*tileHeight, null);
			}
		}
		g.dispose();
		return new GImage(image);
	}
	
}
