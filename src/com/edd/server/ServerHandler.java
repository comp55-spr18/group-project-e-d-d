package com.edd.server;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class ServerHandler extends Thread {
	private Socket socket = null;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private ServerListener SL = null;
	
	private boolean clientInit = false;
	
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
				if(inputLine.contains("<newclient>")) {
					this.string_between(inputLine, "<newclient>", "</newclient>");
					SL.clients.add(out);
					System.out.println("new client: " + SL.clients);
				}
			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}