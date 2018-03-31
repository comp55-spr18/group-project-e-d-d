package com.edd.server;

import java.io.IOException;

public class MultiplayerDriver {
	
	public static void main(String[] args) {
		try {
			new ServerListener().listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}