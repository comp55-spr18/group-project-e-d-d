package com.edd.server;

import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

//import com.edd.server.collision.AccessServerElements;

import java.io.*;

public class ServerHandler extends Thread {
	private Socket socket = null;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private ServerListener SL = null;
	private ArrayList<ServerPlayer> players = new ArrayList<ServerPlayer>();
	private ArrayList<ServerPowerUp> powerups = new ArrayList<ServerPowerUp>();
	private ArrayList<ServerResource> resources = new ArrayList<ServerResource>();
//	AccessServerElements ASE;
	
	public ServerHandler(Socket socket, ServerListener SL) {
		super("ServerHandler");
		this.socket = socket;
		this.SL = SL;
		Timer timer = new Timer();
		timer.schedule(new Generation(this), 0, 5000);
	}
	
	public void putToList() {
		players.removeAll(players);
		powerups.removeAll(powerups);
		resources.removeAll(resources);
		for(ServerPlayer sp : SL.clients.keySet()) {
			players.add(sp);
		}
		for(ServerPowerUp spu : SL.powerups.values()) {
			powerups.add(spu);
		}
		for(ServerResource sr : SL.resources.values()) {
			resources.add(sr);
		}
//		ASE = new AccessServerElements(players, powerups, resources);
	}
	
	public String string_between(String input, String left, String right){
		int pos_left = input.indexOf(left) + left.length();
		int pos_right = input.indexOf(right);
		return input.substring(pos_left, pos_right);
	}

	public void run() {
		System.out.println("listening");
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String inputLine;
			
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
				if(inputLine.contains("<newclient>")) {
					//putToList();
					String clientName = this.string_between(inputLine, "<newclient>", "</newclient>");
					
					// Check if this name exists
					if(this.clientExists(clientName)) {
						out.write("<error>Name in use</error>");
						out.close();
						continue;
					}
					
					SL.clients.put(new ServerPlayer(clientName), out);
					System.out.println("new client: " + SL.clients.keySet());
					handleNewClient(clientName);
					sendPlayerList(clientName);
					sendPowerUpList(clientName);
					sendResourceList(clientName);
				}
				if(inputLine.contains("<remove>")) {
					String clientName = this.string_between(inputLine, "<remove>", "</remove>");
					System.out.println(SL.clients);
					ServerPlayer obj = null;
					for (Entry<ServerPlayer, PrintWriter> entry : SL.clients.entrySet()) {
						String name = entry.getKey().getPlayerName();
					    if(name.equals(clientName))
					    		obj = entry.getKey();
					}
					if(obj != null)
						SL.clients.remove(obj);
					handlePlayerRemove(clientName);
					System.out.println(SL.clients);
				}
				if(inputLine.contains("<removePU>")) {
					String PUID = this.string_between(inputLine, "<removePU>", "</removePU>");
					handlePowerUpRemove(PUID);
				}
				if(inputLine.contains("<move>")) {
					String[] packetData = this.string_between(inputLine, "<move>", "</move>").split(",");
					String clientName = this.getPlayerName(out);
					String xVelocity = packetData[0];
					String yVelocity = packetData[1];
					handlePlayerMove(clientName, xVelocity, yVelocity);
				}
			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendGlobalPacket(String packet) {
		// From https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
		for (Entry<ServerPlayer, PrintWriter> entry : SL.clients.entrySet()) {
		    PrintWriter out = entry.getValue();
		    out.println(packet);
		}
	}
	
	public void sendGlobalPacket(String packet, String doNotSendTo) {
		for (Entry<ServerPlayer, PrintWriter> entry : SL.clients.entrySet()) {
			String name = entry.getKey().getPlayerName();
		    PrintWriter out = entry.getValue();
		    if(name.equals(doNotSendTo))
		    		continue;
		    out.println(packet);
		}
	}
	
	public PrintWriter getPlayerSocket(String playerName) {
		for (Entry<ServerPlayer, PrintWriter> entry : SL.clients.entrySet()) {
			String name = entry.getKey().getPlayerName();
		    PrintWriter out = entry.getValue();
		    if(name.equals(playerName)) {
		    		return out;
		    }
		}
		return null;
	}
	
	public ServerPlayer getPlayerKey(String playerName) {
		for (Entry<ServerPlayer, PrintWriter> entry : SL.clients.entrySet()) {
			ServerPlayer key = entry.getKey();
		    if(key.getPlayerName().equals(playerName)) {
		    		return key;
		    }
		}
		return null;
	}
	
	public String getPlayerName(PrintWriter sock) {
		for (Entry<ServerPlayer, PrintWriter> entry : SL.clients.entrySet()) {
			String name = entry.getKey().getPlayerName();
		    PrintWriter out = entry.getValue();
		    if(out.equals(sock)) {
		    		return name;
		    }
		}
		return null;
	}
	
	public void sendPlayerPacket(String packet) {
		out.println(packet);
	}
	
	public void closeSocket(String playerName) {
		PrintWriter o = getPlayerSocket(playerName);
		o.close();
	}
	
	public boolean clientExists(String clientName) {
		for (Entry<ServerPlayer, PrintWriter> entry : SL.clients.entrySet()) {
			String name = entry.getKey().getPlayerName();
			System.out.println("Looking for: "  + clientName);
			System.out.println("Currently: "  + name);
		    if(clientName.equals(name)) {
		    		System.out.println("True!");
		    		return true;
		    }
		}
		return false;
	}
	
	public void sendPlayerPacket(String packet, String playerName) {
		PrintWriter w = getPlayerSocket(playerName);
		w.println(packet);
	}
	
	public String buildPlayerPacket(String playerName, int x, int y, int color) {
		return "(" + playerName + "," + x + "," + y + "," + color + ")";
	}
	
	public void sendPlayerList(String playerName) {
		String allPlayers = "";
		for (Entry<ServerPlayer, PrintWriter> entry : SL.clients.entrySet()) {
			String name = entry.getKey().getPlayerName();
			int x = entry.getKey().getX();
			int y = entry.getKey().getY();
			int color = entry.getKey().getPlayerColor();
		    if(name.equals(playerName))
		    		continue;
		    allPlayers += buildPlayerPacket(name, x, y, color) + "%";
		}
		sendPlayerPacket("<playerlist>" + allPlayers + "</playerlist>", playerName);
	}
	
	public void sendPowerUpList(String playerName) {
		String allPowerups = "";
		for (Entry<String, ServerPowerUp> entry : SL.powerups.entrySet()) {
			ServerPowerUp SPU = entry.getValue();
			allPowerups += SPU.generatePacket() + "%";
		}
		sendPlayerPacket("<powerup>" + allPowerups + "</powerup>");
	}
	
	public void sendResourceList(String playerName) {
		String allResources = "";
		for (Entry<String, ServerResource> entry : SL.resources.entrySet()) {
			ServerResource SR = entry.getValue();
			allResources += SR.generatePacket() + "%";
		}
		sendPlayerPacket("<resource>" + allResources + "</resource>");
	}
	
	public void handleNewClient(String playerName) {
		ServerPlayer p = getPlayerKey(playerName);
		String playerLoc = buildPlayerPacket(playerName, p.getX(), p.getY(), p.getPlayerColor());
		sendGlobalPacket("<newclient>"+playerLoc+"</newclient>", playerName);
		sendPlayerPacket("<newclient>JOIN_OK" + "," + p.getX() + "," + p.getY() + "," + p.getPlayerColor() + "</newclient>", playerName);
	}
	
	public void handlePlayerRemove(String playerName) {
		sendGlobalPacket("<remove>"+playerName+"</remove>", playerName);
	}
	
	public void handlePlayerMove(String clientName, String xVelocity, String yVelocity) {
		sendGlobalPacket("<move>" + clientName + "," + xVelocity + "," + yVelocity + "</move>", clientName);
		ServerPlayer sp = getPlayerKey(clientName);
		int newX = (int)(sp.getX() + Double.parseDouble(xVelocity));
		int newY = (int)(sp.getY() + Double.parseDouble(yVelocity));
		sp.setX(newX);
		sp.setY(newY);
		System.out.println("Player:" + sp.getX() + ", " + sp.getY());
	}
	
	public void handlePowerUpRemove(String PUID) {
		sendGlobalPacket("<removePU>" + PUID + "</removePU>", getPlayerName(out));
		SL.powerups.remove(PUID);
	}
	
	public void handleResourceRemove(String RID) {
		sendGlobalPacket("<removeR>" + RID + "</removeR>", getPlayerName(out));
		SL.resources.remove(RID);
	}
	
	public void populatePowerups() {
		int maxPowerups = 50;
		if(this.SL.powerups.size() >= maxPowerups)
			return;
		while(this.SL.powerups.size() < maxPowerups) {
			ServerPowerUp SPU = new ServerPowerUp();
			SL.powerups.put(SPU.getID(), SPU);
			this.sendGlobalPacket("<powerup>"+SPU.generatePacket()+"%</powerup>");
		}
	}
	
	public void populateResources() {
		int maxResources = 200;
		if(this.SL.resources.size() >= maxResources)
			return;
		while(this.SL.resources.size() < maxResources) {
			ServerResource SR = new ServerResource();
			SL.resources.put(SR.getID(), SR);
			this.sendGlobalPacket("<resource>"+SR.generatePacket()+"%</resource>");
		}
	}
	
	class Generation extends TimerTask {
		ServerHandler SH;
		public Generation(ServerHandler SH) {
			this.SH = SH;
		}
	    public void run() {
	    	   //putToList();
	       SH.populatePowerups();
	       SH.populateResources();
	    }
	}
}