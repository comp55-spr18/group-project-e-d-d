package com.edd.circlebrawl;

import java.util.ArrayList;

public class ActorAccesser {

	private GameType gameType;
	private MainApplication driver;
	
	public ActorAccesser(GameType gameType, MainApplication driver){
		this.gameType = gameType;
		this.driver = driver;
	}
	
	public ArrayList<BaseActor> getCharacters(){
		ArrayList<BaseActor> players = getPlayers();
		ArrayList<BaseActor> AIs = getAIs();
		ArrayList<BaseActor> retVal = new ArrayList<BaseActor>();
		for(BaseActor player : players)
			retVal.add(player);
		for(BaseActor ai : AIs)
			retVal.add(ai);
		return retVal;
	}
	
	public ArrayList<BaseActor> getItems(){
		ArrayList<BaseActor> powerUps = getPowerUps();
		ArrayList<BaseActor> resources = getResources();
		ArrayList<BaseActor> retVal = new ArrayList<BaseActor>();
		for(BaseActor powerUp : powerUps)
			retVal.add(powerUp);
		for(BaseActor resource : resources)
			retVal.add(resource);
		return retVal;
	}
	
	public ArrayList<BaseActor> getPowerUps(){
		switch(gameType){
			case SINGLEPLAYER:
				return driver.POWERUP_GEN.getActors();
			case MULTIPLAYER:
				MultiplayerSam_Test d = (MultiplayerSam_Test)driver;
				return d.getPowerUps();
		}
		return new ArrayList<BaseActor>(); // only here so Java doesn't complain
	}
	
	public ArrayList<BaseActor> getResources(){
		switch(gameType){
			case SINGLEPLAYER:
				return driver.RESOURCE_GEN.getActors();
			case MULTIPLAYER:
				MultiplayerSam_Test d = (MultiplayerSam_Test)driver;
				return d.getResources();
		}
		return new ArrayList<BaseActor>(); // only here so Java doesn't complain
	}
	
	public ArrayList<BaseActor> getBoundaries(){
		switch(gameType){
			case SINGLEPLAYER:
				return driver.BOUNDARY_GEN.getActors();
			case MULTIPLAYER:
				return new ArrayList<BaseActor>(); // TODO: Sam, make this retrieve the proper list
		}
		return new ArrayList<BaseActor>(); // only here so Java doesn't complain
	}
	
	public ArrayList<BaseActor> getObstacles(){
		switch(gameType){
			case SINGLEPLAYER:
				return driver.OBSTACLE_GEN.getActors();
			case MULTIPLAYER:
				return new ArrayList<BaseActor>(); // TODO: Sam, make this retrieve the proper list
		}
		return new ArrayList<BaseActor>(); // only here so Java doesn't complain
	}
	
	public ArrayList<BaseActor> getPlayers(){
		switch(gameType){
			case SINGLEPLAYER:
				ArrayList<BaseActor> ret = new ArrayList<BaseActor>();
				ret.add(driver.player);
				return ret;
			case MULTIPLAYER:
				MultiplayerSam_Test d = (MultiplayerSam_Test)driver;
				return d.getPlayerList();
		}
		return new ArrayList<BaseActor>(); // only here so Java doesn't complain
	}
	
	public ArrayList<BaseActor> getAIs(){
		switch(gameType){
			case SINGLEPLAYER:
				return driver.AI_GEN.getActors();
			case MULTIPLAYER:
				return new ArrayList<BaseActor>(); // TODO: Sam, make this retrieve the proper list
		}
		return new ArrayList<BaseActor>(); // only here so Java doesn't complain
	}
	
}
