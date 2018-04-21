package com.edd.circlebrawl;

import java.util.Iterator;

import com.edd.character.Player;

import acm.graphics.GObject;

public class Camera implements Tick {
	private double instanceX, instanceY;
	private Player player;
	private MainApplication app;
	private int totalTranslationX = 0;
	private int totalTranslationY = 0;

	public Camera(int totalTranslationX, int totalTranslationY, Player player, MainApplication app) {
		this.totalTranslationX = totalTranslationX;
		this.totalTranslationY = totalTranslationY;
		this.player = player;
		this.app = app;
		if(app instanceof MultiplayerDriver) {
			MultiplayerDriver multiDriver = (MultiplayerDriver)app;
			if(multiDriver.getClientPlayer() == player)
				applyInitialTranslation();
		} else {
			applyInitialTranslation();
		}
	}
	
	private void applyInitialTranslation(){
		for (Iterator<GObject> it = app.getGCanvas().iterator(); it.hasNext();) {
			GObject next = it.next();
			if (next == player.sprite || next == player.getSaw().getSprite() || next == player.getNameLabel()
					|| next.equals(app.muteButton) || next.equals(app.pauseButton)) {
				continue;
			} else
				next.move(totalTranslationX, totalTranslationY);
		}
	}
	
	public void undoTranslation(){
		for (Iterator<GObject> it = app.getGCanvas().iterator(); it.hasNext();) {
			GObject next = it.next();
			if (next == player.sprite || next == player.getSaw().getSprite() || next == player.getNameLabel()
					|| next.equals(app.muteButton) || next.equals(app.pauseButton)) {
				continue;
			} else
				next.move(-totalTranslationX, -totalTranslationY);
		}
	}

	public void setX(double x) {
		instanceX = x;
	}

	// I don't really know how useful these two functions will be but I'm adding
	// them just in case we need them.
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

	public int getTotalTranslationX() {
		return totalTranslationX;
	}

	public int getTotalTranslationY() {
		return totalTranslationY;
	}

	// this is the function that's going to move everything on the screen according
	// to the direction
	// that the player is controlling with WASD.
	// This is going to use a iterator to iterate over every GObject in the GCanvas
	// that makes
	// up the elements of the screen. The only things that won't be moving are the
	// player's
	// attack ring, the player, and the buttons the screen may or may not have.
	public void translate(double velX, double velY) {
		totalTranslationX += velX;
		totalTranslationY += velY;
		for (Iterator<GObject> it = app.getGCanvas().iterator(); it.hasNext();) {
			GObject next = it.next();
			if (next == player.sprite || next == player.getSaw().getSprite() || next == player.getNameLabel()
					|| next.equals(app.muteButton) || next.equals(app.pauseButton)) {
				continue;
			} else
				next.move(velX, velY);
		}
	}

	@Override
	public void tick() {
		for (Iterator<GObject> it = app.getGCanvas().iterator(); it.hasNext();) {

			GObject next = it.next();
			if (next == player.sprite || next == player.getSaw().getSprite()) {
				continue;
			} else {
				// next.move(velX, velY);
			}
		}

	}

}
