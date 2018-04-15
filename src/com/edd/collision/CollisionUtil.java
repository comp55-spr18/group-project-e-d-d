package com.edd.collision;

import com.edd.character.Direction;
import com.edd.character.Rate;
import com.edd.circlebrawl.BaseActor;

public abstract class CollisionUtil {
	
	private static final int MDRPI = 5; // Max detection range per instance
	
	public static CollisionResult overlaps(BaseActor moving, BaseActor resting, int x, int y){

		CollisionResult result = new CollisionResult(false,false);

		int tX = 0;
		int tY = 0;
		
		if(x < -MDRPI)
			tX = x+MDRPI;
		else if(x > MDRPI)
			tX = x-MDRPI;

		if(y < -MDRPI)
			tY = y+MDRPI;
		else if(y > MDRPI)
			tY = y-MDRPI;
		
		if(x > MDRPI || y > MDRPI){
			result.merge(overlaps(moving,resting,tX,tY));
		}
		
		if(moving == resting) // if the two actors are the same
			return new CollisionResult(false,false);
		
		Direction direction = Direction.getDirectionFromVelocity(x,y);
		Rate xRate = Rate.getRateFromVelocity(x);
		Rate yRate = Rate.getRateFromVelocity(y);
		
		boolean xOverlap = false;
		boolean yOverlap = false;
		
		CollisionBox cb1 = moving.getCollisionBox();
		CollisionBox cb2 = resting.getCollisionBox();
		
		int x1b = cb1.xb+x; // far left end of moving actor
		int y1b = cb1.yb+y; // far up end of moving actor
		int x1e = cb1.xe+x; // far right end of moving actor
		int y1e = cb1.ye+y; // far down end of moving actor

		int x2b = cb2.xb; // far left end of resting actor
		int y2b = cb2.yb; // far up end of resting actor
		int x2e = cb2.xe; // far right end of resting actor
		int y2e = cb2.ye; // far down end of resting actor

		if(direction == Direction.NEUTRAL){
			boolean overlaps = inRange(x1b,x1e,x2b,x2e) && inRange(y1b,y1e,y2b,y2e);
			return new CollisionResult(overlaps,overlaps);
		}
		
		if(inRange(y1b-y,y1e-y,y2b,y2e))
			switch(xRate){
				case INCREASING:
						xOverlap = eastCheck(x1b,x1e,x2b,x2e);
					break;
				case DECREASING:
						xOverlap = westCheck(x1b,x1e,x2b,x2e);
					break;
				case NEUTRAL:
					// do nothing
					break;
		}

		if(inRange(x1b-x,x1e-x,x2b,x2e))
			switch(yRate){
				case INCREASING:
						yOverlap = southCheck(y1b,y1e,y2b,y2e);
					break;
				case DECREASING:
						yOverlap = northCheck(y1b,y1e,y2b,y2e);
					break;
				case NEUTRAL:
					// do nothing
					break;
		}
		
		return result.merge(new CollisionResult(xOverlap,yOverlap));
	}
	
	public static boolean overlaps(BaseActor first, BaseActor second){
		return overlaps(first, second, 0, 0).xCollides; // both xOverlaps & yOverlaps are true if direction is neutral (0,0 velocity) and it overlaps
	}
	
	private static boolean inRange(int t1b, int t1e, int t2b, int t2e){
		return (t1b >= t2b && t1b <= t2e) || (t1e >= t2b && t1e <= t2e) || (t2b >= t1b && t2b <= t1e) || (t2e >= t1b && t2e <= t1e);
	}
	
	private static boolean northCheck(int y1b, int y1e, int y2b, int y2e){
		return y1b <= y2e && y1b >= y2b;
	}
	
	private static boolean southCheck(int y1b, int y1e, int y2b, int y2e){
		return y1e >= y2b && y1b <= y2b;
	}
	
	private static boolean eastCheck(int x1b, int x1e, int x2b, int x2e){
		return x1e >= x2b && x1b <= x2b;
	}
	
	private static boolean westCheck(int x1b, int x1e, int x2b, int x2e){
		return x1b <= x2e && x1b >= x2b;
	}
	
}
