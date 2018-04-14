package com.edd.map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import acm.graphics.GImage;

public class Map {

	private int tileWidth, tileHeight;
	private GImage[][] map;
	
	public Map(GImage[][] map, int tileWidth, int tileHeight){
		this.map = map;
	}
	
	public GImage createImage(){
		BufferedImage image = new BufferedImage(map.length*tileWidth, map[0].length*tileHeight,BufferedImage.TYPE_INT_RGB);
		Graphics g = image.createGraphics();
		for(int i=0;i<map.length;i++){
			for(int j=0;j<map[i].length;j++){
				g.drawImage(map[i][j].getImage(), i*tileWidth, j*tileHeight, null);
			}
		}
		g.dispose();
		return new GImage(image);
	}
	
}
