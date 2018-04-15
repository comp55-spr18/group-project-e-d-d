package com.edd.generator;

import java.util.ArrayList;
import java.util.Random;

import com.edd.circlebrawl.ActorAccesser;
import com.edd.circlebrawl.BaseActor;
import com.edd.circlebrawl.GameType;
import com.edd.circlebrawl.MainApplication;
import com.edd.circlebrawl.Tick;

public abstract class BaseGenerator implements Tick{

	protected int maxSpawns; //max number of the actor that can be spawned at once
	protected int spawnDelay; //seconds between spawns
	
	protected int currentTicks; //current ticks, used to count spawn timer
	protected boolean activated; //if true, continue functions; if false, stop all functions
	
	protected GameType gameType;
	protected Random rand = new Random();
	
	protected MainApplication driver;
	protected ArrayList<BaseActor> actors = new ArrayList<BaseActor>();
	protected ArrayList<BaseActor> actorsToRemove = new ArrayList<BaseActor>();
	
	@Override
	public void tick() {
		if(activated) {
			for(BaseActor actor : actors) {
				actor.tick();
			}
			for(BaseActor actor : actorsToRemove){
				remove(actor);
			}
			
			actorsToRemove.clear();
			
			currentTicks++;
			if(currentTicks >= spawnDelay*driver.TICKS_PER_SECOND && actors.size() < maxSpawns) {
				spawn();
				currentTicks = 0;
			}
		}
	}
	
	/***
	 * Spawns the actor. Dependent on subclasses.
	 */
	public abstract void spawn();
	
	/***
	 * Adds an actor to the list of actors pending removal.
	 * @param actor the actor to be removed.
	 */
	public void addToRemoveList(BaseActor actor){
		actorsToRemove.add(actor);
	}
	
	/***
	 * Removes the actor from the list of actors.
	 * @param actor the actor to remove
	 * @return if the removal worked
	 */
	protected boolean remove(BaseActor actor) {
		if(actors.contains(actor)) {
			actors.remove(actor);
			return true;
		}
		return false;
	}
	
	/***
	 * Activates the generator if not active.
	 */
	public void activate() {
		if(!activated) {
			activated = true;
			
			//adding any hidden actors to the screen
			for(BaseActor actor : actors) {
				driver.add(actor.getSprite());
			}
		}
	}
	
	/***
	 * Deactivates the generator if active.
	 */
	public void deactivate() {
		if(activated) {
			activated = false;
			
			//removing any visible actors from the screen
			for(BaseActor actor : actors) {
				driver.remove(actor.getSprite());
			}
		}
	}
	
	/***
	 * Determines if the generator is active.
	 * @return the activated state of the generator.
	 */
	public boolean isActivated() {
		return activated;
	}
	
	/***
	 * Determines a randomized efficacy.
	 * @return the randomized efficacy.
	 */
	protected int getRandomEfficacy(int min, int max) {
		int total = Math.abs(max-min);
		if(total < 1)
			return 0; // wtf?n
		for(int i=0;i<5;i++){
			for(int j=min;j<=max;j++){
				if(rand.nextInt(total) == 0){
					return j;
				}
			}
		}
		return max; // failsafe
	}
	
	public ArrayList<BaseActor> getActors(){ return actors; }
	
}
