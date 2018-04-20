package com.edd.server.collision;

import java.util.ArrayList;

import com.edd.server.*;

public class AccessServerElements {
	
	ArrayList<ServerPlayer> sp;
	ArrayList<ServerPowerUp> spu;
	ArrayList<ServerResource> sr;
	
	public AccessServerElements(ArrayList<ServerPlayer> sp, ArrayList<ServerPowerUp> spu, ArrayList<ServerResource> sr) {
		this.sp = sp;
		this.spu = spu;
		this.sr = sr;
	}
	
	public ArrayList<ServerPlayer> getPlayers(){
		return sp;
	}
	
	public ArrayList<ServerPowerUp> getPowerUps(){
		return spu;
	}
	
	public ArrayList<ServerResource> getResource(){
		return sr;
	}

}
