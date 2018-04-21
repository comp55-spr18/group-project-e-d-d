package com.edd.server;

import java.io.IOException;

public class MultiplayerServerDriver {
	
	public static void main(String[] args) {
		try {
			new ServerListener().listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}