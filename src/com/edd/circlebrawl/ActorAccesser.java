package com.edd.circlebrawl;

import java.util.ArrayList;

import com.edd.character.AI;
import com.edd.character.Player;
import com.edd.obstacle.Obstacle;
import com.edd.powerup.PowerUp;
import com.edd.character.Character;

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
				MultiplayerDriver d = (MultiplayerDriver)driver;
				return d.getPowerUps();
		}
		return new ArrayList<BaseActor>(); // only here so Java doesn't complain
	}
	
	public ArrayList<BaseActor> getResources(){
		switch(gameType){
			case SINGLEPLAYER:
				return driver.RESOURCE_GEN.getActors();
			case MULTIPLAYER:
				MultiplayerDriver d = (MultiplayerDriver)driver;
				return d.getResources();
		}
		return new ArrayList<BaseActor>(); // only here so Java doesn't complain
	}
	
	public ArrayList<BaseActor> getBoundaries(){
		switch(gameType){
			case SINGLEPLAYER:
				return driver.BOUNDARY_GEN.getActors();
			case MULTIPLAYER:
				MultiplayerDriver d = (MultiplayerDriver)driver;
				return d.getBoundaries();
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
				MultiplayerDriver d = (MultiplayerDriver)driver;
				return d.getPlayerList();
		}
		return new ArrayList<BaseActor>(); // only here so Java doesn't complain
	}
	
	public ArrayList<BaseActor> getAIs(){
		if(gameType == GameType.SINGLEPLAYER)
			return driver.AI_GEN.getActors();
		return new ArrayList<BaseActor>();
	}
	
	public void removeAI(AI ai){
		switch(gameType){
			case SINGLEPLAYER:
				driver.AI_GEN.addToRemoveList(ai);
				return;
			case MULTIPLAYER:
				// TODO: Sam, add to remove ticklist!
				return;
		}
	}
	
	public void removeObstacle(Obstacle obstacle){
		switch(gameType){
			case SINGLEPLAYER:
				driver.OBSTACLE_GEN.addToRemoveList(obstacle);
				return;
			case MULTIPLAYER:
				// TODO: Sam, add to remove ticklist!
				return;
		}
	}

	public void removeResource(Character c, Resource resource){
		switch(gameType){
			case SINGLEPLAYER:
				driver.RESOURCE_GEN.addToRemoveList(resource);
				return;
			case MULTIPLAYER:
				MultiplayerDriver d = (MultiplayerDriver)driver;
				d.RESOURCE_GEN.addToRemoveList(resource);
				d.removeResource((Player)c,resource);
				return;
		}
	}
	
	public void removePowerUp(Character c,PowerUp powerUp){
		switch(gameType){
			case SINGLEPLAYER:
				driver.POWERUP_GEN.addToRemoveList(powerUp);
				driver.POWERUP_GEN.use(powerUp);
				return;
			case MULTIPLAYER:
				MultiplayerDriver d = (MultiplayerDriver)driver;
				d.POWERUP_GEN.addToRemoveList(powerUp);
				d.POWERUP_GEN.use(powerUp);
				d.removePowerUP((Player)c,powerUp);
				return;
		}
	}
	
}
