package com.edd.map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import acm.graphics.GImage;

public class Map {

	private int tileWidth, tileHeight;
	private GImage[][] map;
	
	public Map(GImage[][] map, int tileWidth, int tileHeight){
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.map = map;
	}
	
	public GImage createImage(){
		BufferedImage image = new BufferedImage(map.length*tileWidth, map[0].length*tileHeight,BufferedImage.TYPE_INT_RGB);
		Graphics g = image.createGraphics();
		for(int rows=0;rows<map.length;rows++){
			for(int cols=0;cols<map[rows].length;cols++){
				g.drawImage(map[rows][cols].getImage(), rows*tileWidth, cols*tileHeight, null);
			}
		}
		g.dispose();
		return new GImage(image);
	}
	
}
