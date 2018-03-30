package com.edd.server;

import java.net.*;
import java.util.ArrayList;
import java.io.*;
 
public class ServerListener {
	ArrayList<PrintWriter> clients = new ArrayList<PrintWriter>();
    public void listen() throws IOException {
 
        int portNumber = 9991; // temp port. will fix this later
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