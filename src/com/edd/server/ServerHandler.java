package com.edd.server;

import java.net.*;
import java.util.Arrays;
import java.util.Map.Entry;
import java.io.*;

public class ServerHandler extends Thread {
	private Socket socket = null;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private ServerListener SL = null;
	
	public ServerHandler(Socket socket, ServerListener SL) {
		super("ServerHandler");
		this.socket = socket;
		this.SL = SL;
	}
	
	public String string_between(String input, String left, String right){
		int pos_left = input.indexOf(left) + left.length();
		int pos_right = input.indexOf(right);
		return input.substring(pos_left, pos_right);
	}

	public void run() {
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String inputLine;
			
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
				if(inputLine.contains("<newclient>")) {
					String clientName = this.string_between(inputLine, "<newclient>", "</newclient>");
					SL.clients.put(new ServerPlayer(clientName), out);
					System.out.println("new client: " + SL.clients.keySet());
					handleNewClient(clientName);
				}
				if(inputLine.contains("<remove>")) {
					String clientName = this.string_between(inputLine, "<remove>", "</remove>");
					
					ServerPlayer obj = null;
					for (Entry<ServerPlayer, PrintWriter> entry : SL.clients.entrySet()) {
						String name = entry.getKey().getPlayerName();
					    if(name.equals(clientName))
					    		obj = entry.getKey();
					}
					if(obj != null)
						SL.clients.remove(obj);
					handlePlayerRemove(clientName);
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
	
	public void sendPlayerPacket(String packet, String playerName) {
		PrintWriter w = getPlayerSocket(playerName);
		w.println(packet);
	}
	
	public String buildPlayerPacket(String playerName, int x, int y) {
		return "(" + playerName + "," + x + "," + y + ")";
	}
	
	public void sendPlayerList(String playerName) {
		String allPlayers = "";
		for (Entry<ServerPlayer, PrintWriter> entry : SL.clients.entrySet()) {
			String name = entry.getKey().getPlayerName();
			int x = entry.getKey().getPlayerX();
			int y = entry.getKey().getPlayerY();
		    if(name.equals(playerName))
		    		continue;
		    allPlayers += buildPlayerPacket(name, x, y) + ",";
		}
		sendPlayerPacket("<playerlist>" + allPlayers + "</playerlist>", playerName);
	}
	
	public void handleNewClient(String playerName) {
		sendPlayerList(playerName);
		sendGlobalPacket("<newclient>"+playerName+"</newclient>", playerName);
		sendPlayerPacket("<newclient>JOIN_OK</newclient>", playerName);
	}
	
	public void handlePlayerRemove(String playerName) {
		sendGlobalPacket("<remove>"+playerName+"</remove>", playerName);
	}
	
	public void handlePlayerMove(String clientName, String xVelocity, String yVelocity) {
		sendGlobalPacket("<move>" + clientName + "," + xVelocity + "," + yVelocity + "</move>", clientName);
	}
}