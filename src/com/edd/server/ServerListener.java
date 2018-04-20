package com.edd.server;

import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

import java.io.*;
 
public class ServerListener {
	HashMap<ServerPlayer, PrintWriter> clients = new HashMap<ServerPlayer, PrintWriter>();
	HashMap<String, ServerPowerUp> powerups = new HashMap<String, ServerPowerUp>();
	HashMap<String, ServerResource> resources = new HashMap<String, ServerResource>();
    public void listen() throws IOException {
 
        int portNumber = 9993; // temp port. will fix this later
        boolean listening = true;
         
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) { 
            while (listening) {
                new ServerHandler(serverSocket.accept(), this).start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}