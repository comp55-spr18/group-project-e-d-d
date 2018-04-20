package com.edd.server.collision;


public abstract class CollisionUtil {
	
	public static boolean overlaps(CollisionBox first, CollisionBox second){
		int x1b = first.xb; // far left end of moving actor
		int y1b = first.yb; // far up end of moving actor
		int x1e = first.xe; // far right end of moving actor
		int y1e = first.ye; // far down end of moving actor

		int x2b = second.xb; // far left end of resting actor
		int y2b = second.yb; // far up end of resting actor
		int x2e = second.xe; // far right end of resting actor
		int y2e = second.ye; // far down end of resting actor
		
		boolean overlaps = inRange(x1b,x1e,x2b,x2e) && inRange(y1b,y1e,y2b,y2e);
		return overlaps;
	}
	
	private static boolean inRange(int t1b, int t1e, int t2b, int t2e){
		return (t1b >= t2b && t1b <= t2e) || (t1e >= t2b && t1e <= t2e) || (t2b >= t1b && t2b <= t1e) || (t2e >= t1b && t2e <= t1e);
	}
	
}
