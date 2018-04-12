package com.edd.circlebrawl;

import com.edd.character.Player;
import com.edd.osvaldo.MainApplication;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import acm.graphics.*;

public class Camera implements Tick{
	private double instanceX, instanceY;
	private Player player;
	private MainApplication app;
	
	
	public Camera(double instanceX, double instanceY, Player player, MainApplication app){
		this.instanceX = instanceX;
		this.instanceY = instanceY;
		this.player = player;
		this.app = app;
	}
	
	public void setX(double x) {
		instanceX = x;
	}
	
	//I don't really know how useful these two functions will be but I'm adding them just in case we need them.
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void setY(double y) {
		instanceY = y;
	}
	
	public double getX() {
		return instanceX;
	}
	
	public double getY() {
		return instanceY;
	}
	
	
	
	//this is the function that's going to move everything on the screen according to the direction
	//that the player is controlling with WASD.
	//This is going to use a iterator to iterate over every GObject in the GCanvas that makes
	//up the elements of the screen. The only things that won't be moving are the player's 
	//attack ring, the player, and the buttons the screen may or may not have.
	public void translate(double velX, double velY) {
		for (Iterator<GObject> it = app.getGCanvas().iterator(); it.hasNext();)
			if (it.next() == player.sprite || it.next() == player.getSawSprite()){
				continue;
			}
		
			// TEMPORARILY COMMENTED OUT THE LINES BELOW; THEY NEED EDITING TO WORK WITH COLLISION - Zach
		
			//else
				//it.next().move(velX, velY);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}
	
}
