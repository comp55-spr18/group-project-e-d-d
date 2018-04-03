package com.edd.circlebrawl;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.Timer;

import com.edd.osvaldo.MainApplication;
import com.edd.powerup.PowerUpGenerator;

import acm.graphics.GLabel;
import acm.graphics.GOval;

// Driver Class
public class MultiplayerSam_Test extends CircleBrawl implements Tick {

	public final HashMap<String, Player> characters = new HashMap<String, Player>();
	public final ArrayList<Item> ITEM_LIST = new ArrayList<Item>();
	public final PowerUpGenerator POWERUP_GEN = new PowerUpGenerator(this);
	public final int MAP_WIDTH = 1024;
	public final int MAP_HEIGHT = 768;
	public final int WINDOW_WIDTH = 1024;
	public final int WINDOW_HEIGHT = 768;
	private double velX = 0;
	private double velY = 0;
	private Player player;

	private final int BALL_CIRC = 100;
	public static final int ATTACK_RING = 150;
	private int keyI;
	private int lastKeyPressed;

	private GOval ring;
	private Timer testTimer = new Timer(2000, this);
	private boolean keyW, keyS, keyLEFT, keyRIGHT;
	private double xVelocity = 0;
	private double yVelocity = 0;
	private int i = 0;
	
	NetworkClient NC;

	int ticks = 0;
	int frames = 0;

	@Override
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		Random r = new Random();
		NC = new NetworkClient("127.0.0.1", 9991, r.nextInt(100), this);
		NC.start();
	}
	
	class NetworkClient extends Thread{
		
		private String hostname;
		private int port;
		private Socket socketClient;
		private int clientID;
		private boolean client_initiated = false;
		PrintWriter out;
		MultiplayerSam_Test world;
		
		public NetworkClient(String hostname, int port, int clientID, MultiplayerSam_Test world) {
			this.hostname = hostname;
			this.port = port;
			this.clientID = clientID;
			this.world = world;
		}
		
		public void run() {
			System.out.println("Thread running");
			try {
				this.connect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				this.readResponse();
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public String string_between(String input, String left, String right){
			int pos_left = input.indexOf(left) + left.length();
			int pos_right = input.indexOf(right);
			return input.substring(pos_left, pos_right);
		}
		
		public void connect() throws UnknownHostException, IOException{
			System.out.println("Attempting to connect to " + this.hostname + ":" + this.port);
		    this.socketClient = new Socket(this.hostname, this.port);
		    System.out.println("Connection Established");
		}
		
		public void sendPacket(PrintWriter out, String p) {
			out.println(p);
			System.out.println("Sent: " + p);
		}
		
		public String parsePacket(String p) {
			if(p.contains("JOIN_OK")) {
				return "JOIN_OK";
			}
			if(p.contains("newclient")) {
				return "newclient";
			}
			return "";
		}
		
		public void readResponse() throws IOException, InterruptedException{
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(this.socketClient.getInputStream()));
			out = new PrintWriter(this.socketClient.getOutputStream(), true);
		    String userInput;
		    while ((userInput = stdIn.readLine()) != null) { //remember to implement ENUMS here
		        System.out.println("recv: " + userInput);
		    		if(parsePacket(userInput).equals("JOIN_OK")) {
		    			this.client_initiated = true;
		    		}
		    		if(parsePacket(userInput).equals("newclient")) {
		    			String name = string_between(userInput, "<newclient>", "</newclient>");
		    			world.addPlayer(name);
		    		}
		    }
		}
	
	}

	// For testing
	
	public String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
	
	@Override
	public void run() {
		for (int i = 0; i < 5; i++)
			ITEM_LIST.add(POWERUP_GEN.generatePowerUp());

		player = new Player(MAP_WIDTH / 2 - 100, MAP_HEIGHT / 2 - 100, this);

		ring = new GOval(player.x + 30, player.y + 30, 140, 140);
		addKeyListeners();
		addMouseListeners();
		
		NC.sendPacket(NC.out, "<newclient>" + getSaltString() + "</newclient>");
		while(!NC.client_initiated) {} //wait until complete

		long lastTime = System.nanoTime();
		final double ticks = 60.0;
		double ns = 1000000000 / ticks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();

		GLabel g = new GLabel("Ticks: " + ticks + "\nFrames: " + frames);
		g.setLocation(WINDOW_WIDTH - g.getWidth() - 60, WINDOW_HEIGHT - g.getHeight());
		add(g);

		while (true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				g.setLabel("Ticks: " + ticks + "\nFrames: " + frames);
				updates = 0;
				frames = 0;
			}
		}
	}
	
	public void addPlayer(String uniqName) {
		Player p = new Player(MAP_WIDTH / 2 - 100, MAP_HEIGHT / 2 - 100, this);
		characters.put(uniqName, p);
	}

	public void keyPressed(KeyEvent e) {
		keyI = e.getKeyChar();
		if (keyI == 'w') {
			keyW = true;
		}
		if (keyI == 's') {
			keyS = true;
		}
		if (keyI == 'a') {
			keyLEFT = true;
		}
		if (keyI == 'd') {
			keyRIGHT = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		keyI = e.getKeyChar();
		if (keyI == 'w') {
			keyW = false;
			yVelocity = 0;
		}
		if (keyI == 's') {
			keyS = false;
			yVelocity = 0;
		}
		if (keyI == 'a') {
			keyLEFT = false;
			xVelocity = 0;
		}
		if (keyI == 'd') {
			keyRIGHT = false;
			xVelocity = 0;
		}
	}

	public void mousePressed(MouseEvent e) {
		add(ring);
		testTimer.start();

	}

	public void actionPerformed(ActionEvent e) {
		remove(ring);
	}

	public void tick() {
		double x = player.getX();
		double y = player.getY();

		int count = 0;
		int LKP = lastKeyPressed;
		if (keyW && y >= 0) {
			yVelocity = -10;
			System.out.println("Y: " + y);
		}

		else if (keyS && y + BALL_CIRC * 2 <= 768) {
			yVelocity = 10;
			System.out.println("Y: " + y);
		} else
			yVelocity = 0;

		if (keyLEFT && x >= 0) {
			xVelocity = -10;
			System.out.println("X: " + x);
		}

		else if (keyRIGHT && x + BALL_CIRC * 2 <= 1024) {
			xVelocity = 10;
			System.out.println("X: " + x);
		} else
			xVelocity = 0;

		player.move(xVelocity, yVelocity);
		ring.move(xVelocity, yVelocity);

		for (Item item : ITEM_LIST) {
			if (item != null && player.collidesWith(item)) {
				System.out.println("Intersection detected");
				item.consume(player);
				break;
			}
		}
	}
}
