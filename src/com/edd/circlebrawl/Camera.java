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
	private int totalTranslationX = 0;
	private int totalTranslationY = 0;
	
	public Camera(int totalTranslationX, int totalTranslationY, Player player, MainApplication app){
		this.totalTranslationX = totalTranslationX;
		this.totalTranslationY = totalTranslationY;
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
	
	public int getTotalTranslationX(){ return totalTranslationX; }
	public int getTotalTranslationY(){ return totalTranslationY; }
	
	//this is the function that's going to move everything on the screen according to the direction
	//that the player is controlling with WASD.
	//This is going to use a iterator to iterate over every GObject in the GCanvas that makes
	//up the elements of the screen. The only things that won't be moving are the player's 
	//attack ring, the player, and the buttons the screen may or may not have.
	public void translate(double velX, double velY) {
		totalTranslationX += velX;
		totalTranslationY += velY;
		for (Iterator<GObject> it = app.getGCanvas().iterator(); it.hasNext();){
			GObject next = it.next();
			if (next == player.sprite || next == player.getSawSprite()){
				continue;
			}
			else
				next.move(velX, velY);
		}
	}

	@Override
	public void tick() {
		for (Iterator<GObject> it = app.getGCanvas().iterator(); it.hasNext();){

			GObject next = it.next();
			if (next == player.sprite || next == player.getSawSprite()){
				continue;
			} else {
				//next.move(velX, velY);
			}
		}
		
	}
	
}
