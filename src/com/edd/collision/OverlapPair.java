package com.edd.collision;

public class OverlapPair {

	public boolean xOverlaps;
	public boolean yOverlaps;
	
	public OverlapPair(boolean xOverlaps, boolean yOverlaps){
		this.xOverlaps = xOverlaps;
		this.yOverlaps = yOverlaps;
	}
	
	public OverlapPair merge(OverlapPair other ){
		return new OverlapPair(xOverlaps || other.xOverlaps, yOverlaps || other.yOverlaps);
	}
	
}
