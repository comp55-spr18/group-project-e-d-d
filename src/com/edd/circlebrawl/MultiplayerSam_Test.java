package com.edd.circlebrawl;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.edd.character.Player;
import com.edd.generator.BoundaryGenerator;
import com.edd.powerup.PowerUp;
import com.edd.powerup.PowerUpType;

// Driver Class
public class MultiplayerSam_Test extends MainApplication implements Tick {

	// CHANGE NAME HERE
	// String myName = this.getSaltString();
	String myName = "Mike";
	boolean myNameSet = false;
	// CHANGE NAME HERE

	public final HashMap<String, Player> characters = new HashMap<String, Player>();
	public final HashMap<String, PowerUp> powerups = new HashMap<String, PowerUp>();
	public final HashMap<String, Resource> resources = new HashMap<String, Resource>();

	public final HashMap<String, Player> charactersToAdd = new HashMap<String, Player>();
	public final HashMap<String, PowerUp> powerupsToAdd = new HashMap<String, PowerUp>();
	public final HashMap<String, Resource> resourcesToAdd = new HashMap<String, Resource>();

	public final ArrayList<String> powerupsToRemove = new ArrayList<String>();
	public final ArrayList<String> resourcesToRemove = new ArrayList<String>();

	private final BoundaryGenerator BOUNDARY_GEN = new BoundaryGenerator(this);
	private Player player;

	private NetworkClient NC;
	
	private boolean done = false;

	int ticks = 0;
	int frames = 0;

	private JTextField name;
	private JPanel[] login = { new JPanel() };
	private JLabel nameLabel;
	private JButton button;

	@Override
	public void init() {
		myName = JOptionPane.showInputDialog("Enter name: ");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		actorAccesser = new ActorAccesser(GameType.MULTIPLAYER, this);

		setupMap();
		add(this.currentMap);

		Random r = new Random();
		NC = new NetworkClient("138.68.18.227", 9993, r.nextInt(100), this);
		NC.start();

		// This needs to be temporary.
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				NC.sendExit();
			}
		}));

	}

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

	class NetworkClient extends Thread {

		private String hostname;
		private int port;
		private Socket socketClient;
		private int clientID;
		private String clientName;
		boolean client_initiated = false;
		int myStartX = 0;
		int myStartY = 0;
		int myStartColor = 0;
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

		public String string_between(String input, String left, String right) {
			int pos_left = input.indexOf(left) + left.length();
			int pos_right = input.indexOf(right);
			return input.substring(pos_left, pos_right);
		}

		public void connect() throws UnknownHostException, IOException {
			System.out.println("Attempting to connect to " + this.hostname + ":" + this.port);
			this.socketClient = new Socket(this.hostname, this.port);
			System.out.println("Connection Established");
		}

		public void sendPacket(PrintWriter out, String p) {
			out.println(p);
			System.out.println("Sent: " + p);
		}

		public String parsePacket(String p) {
			if (p.contains("JOIN_OK")) {
				return "JOIN_OK";
			}
			if (p.contains("newclient")) {
				return "newclient";
			}
			if (p.contains("playerlist")) {
				return "playerlist";
			}
			if (p.contains("removePU")) {
				return "removePU";
			}
			if (p.contains("remove")) {
				return "remove";
			}
			if (p.contains("move")) {
				return "move";
			}
			if (p.contains("powerup")) {
				return "powerup";
			}
			if (p.contains("resource")) {
				return "resource";
			}
			return "";
		}

		public void readResponse() throws IOException, InterruptedException {
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(this.socketClient.getInputStream()));
			out = new PrintWriter(this.socketClient.getOutputStream(), true);
			String userInput;
			while ((userInput = stdIn.readLine()) != null) { // remember to implement ENUMS here
				System.out.println("recv: " + userInput);
				if (parsePacket(userInput).equals("JOIN_OK")) {
					String[] packet = string_between(userInput, "<newclient>", "</newclient>").split(",");
					myStartX = Integer.parseInt(packet[1]);
					myStartY = Integer.parseInt(packet[2]);
					myStartColor = Integer.parseInt(packet[3]);
					System.out.println("Now true");
					this.client_initiated = true;
				}
				if (parsePacket(userInput).equals("newclient")) {
					String[] packet = string_between(userInput, "<newclient>(", ")</newclient>").split(",");
					String name = packet[0];
					int x = Integer.parseInt(packet[1]);
					int y = Integer.parseInt(packet[2]);
					int color = Integer.parseInt(packet[3]);
					Player p = new Player(name, false, x, y, color, world);
					world.addPlayer(p);
				}
				if (parsePacket(userInput).equals("playerlist")) {
					String packet = string_between(userInput, "<playerlist>", "</playerlist>");
					if (!packet.isEmpty()) {
						String[] pArray = packet.split("%");
						for (String p : pArray) {
							System.out.println(p);
							String[] playerInfo = string_between(p, "(", ")").split(",");
							String clientName = playerInfo[0];
							int clientX = Integer.parseInt(playerInfo[1]);
							int clientY = Integer.parseInt(playerInfo[2]);
							int clientColor = Integer.parseInt(playerInfo[3]);
							Player NP = new Player(clientName, false, clientX, clientY, clientColor, world);
							System.out.println("adding player" + clientName);
							charactersToAdd.put(clientName, NP);
							world.addPlayer(NP);
						}
					}
				}
				if (parsePacket(userInput).equals("powerup")) {
					String packet = string_between(userInput, "<powerup>", "</powerup>");
					if (!packet.isEmpty()) {
						String[] pArray = packet.split("%");
						for (String p : pArray) {
							System.out.println(p);
							String[] powerUpInfo = string_between(p, "(", ")").split(",");
							String powerUpID = powerUpInfo[0];
							int efficacy = Integer.parseInt(powerUpInfo[1]);
							int multiple = Integer.parseInt(powerUpInfo[2]);
							int x = Integer.parseInt(powerUpInfo[3]);
							int y = Integer.parseInt(powerUpInfo[4]);
							String type = powerUpInfo[5];
							PowerUpType PUT = PowerUpType.stringToEnum(type);
							PowerUp PU = new PowerUp(GameType.MULTIPLAYER, x, y, world, efficacy, multiple,
									PowerUpType.stringToEnum(type));
							System.out.println("adding powerup" + powerUpID);
							powerupsToAdd.put(powerUpID, PU);
						}
					}
				}
				if (parsePacket(userInput).equals("resource")) {
					String packet = string_between(userInput, "<resource>", "</resource>");
					if (!packet.isEmpty()) {
						String[] pArray = packet.split("%");
						for (String p : pArray) {
							System.out.println(p);
							String[] resourceInfo = string_between(p, "(", ")").split(",");
							String resourceID = resourceInfo[0];
							int efficacy = Integer.parseInt(resourceInfo[1]);
							int multiple = Integer.parseInt(resourceInfo[2]);
							int x = Integer.parseInt(resourceInfo[3]);
							int y = Integer.parseInt(resourceInfo[4]);
							Resource r = new Resource(x, y, world, efficacy, multiple);
							System.out.println("adding resource" + resourceID);
							resourcesToAdd.put(resourceID, r);
						}
					}
					System.out.println("Resources: " + resources);
				}
				if (parsePacket(userInput).equals("removePU")) {
					String toRemove = string_between(userInput, "<removePU>", "</removePU>");
					powerupsToRemove.add(toRemove);
				}
				if (parsePacket(userInput).equals("remove")) {
					String toRemove = string_between(userInput, "<remove>", "</remove>");
					Player tR = characters.get(toRemove);
					tR.removePlayer();
					tR.getNameLabel().setVisible(false); // TODO
					System.out.println(tR.getX());
				}
				if (parsePacket(userInput).equals("move")) {
					String[] packet = string_between(userInput, "<move>", "</move>").split(",");
					String clientName = packet[0];
					double xVelocity = Double.parseDouble(packet[1]);
					double yVelocity = Double.parseDouble(packet[2]);
					Player p = characters.get(clientName);
					movePlayer(p, xVelocity, yVelocity);
					p.getNameLabel().move(xVelocity, yVelocity);
				}
			}
		}

		public void sendJoin() {
			this.clientName = world.myName;
			sendPacket(out, "<newclient>" + this.clientName + "</newclient>");
		}

		public void sendMove(double x, double y) {
			sendPacket(out, "<move>" + x + "," + y + "</move>");
		}

		public void sendExit() {
			sendPacket(out, "<remove>" + this.clientName + "</remove>");
		}

		public String getPowerUpID(PowerUp PU) {
			for (Entry<String, PowerUp> entry : world.powerups.entrySet()) {
				String ID = entry.getKey();
				PowerUp PUA = entry.getValue();
				if (PU.equals(PUA)) {
					return ID;
				}
			}
			return null;
		}

		public String getResourceID(Resource PU) {
			for (Entry<String, Resource> entry : world.resources.entrySet()) {
				String ID = entry.getKey();
				Resource PUA = entry.getValue();
				if (PU.equals(PUA)) {
					return ID;
				}
			}
			return null;
		}

		public void removePowerUp(PowerUp r) {
			String ID = getPowerUpID(r);
			sendPacket(out, "<removePU>" + ID + "</removePU>");
		}

		public void removeResource(Resource r) {
			String ID = getResourceID(r);
			sendPacket(out, "<removeR>" + ID + "</removeR>");
		}

		public int getStartX() {
			return this.myStartX;
		}

		public int getStartY() {
			return this.myStartY;
		}

		public boolean getClientInitiated() {
			return this.client_initiated;
		}

	}

	// For testing
	
	public void startWait() {
		if(done)
			return;
		try {
		Thread.sleep(2000);
		} catch (InterruptedException e) {}
		done = true;
	}

	@Override
	public void run() {
		NC.sendJoin();
		while (!NC.getClientInitiated()) {
			System.out.print("");
		} // wait until complete
		System.out.println(NC.getStartX() + " + " + NC.getStartY());
		startWait();
		player = new Player(NC.clientName, true, NC.myStartX, NC.myStartY, NC.myStartColor, this);

		long lastTime = System.nanoTime();
		final double ticks = 60.0;
		double ns = 1000000000 / ticks;
		double delta = 0;
		long timer = System.currentTimeMillis();

		while (true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				delta--;
			}
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frames = 0;
			}
		}
	}

	public void addPlayer(Player p) {
		charactersToAdd.put(p.getName(), p);
	}

	public void movePlayer(Player p, double xVelocity, double yVelocity) {
		if (p != null) {
			characters.get(p.getName()).attemptMove((int) xVelocity, (int) yVelocity);
			Player p2 = characters.get(p.getName());
			characters.get(p.getName()).setX(p2.getX());
			characters.get(p.getName()).setY(p2.getY());
		}
	}

	public void moveSuccess(Player player, int xVel, int yVel) {
		if (xVel != 0 || yVel != 0) {
			NC.sendMove(xVel, yVel);
		}
	}

	public boolean isClient(String name) {
		return name.equals(this.myName);
	}

	public void tick() {
		player.tick();
		BOUNDARY_GEN.spawn();
		checkMapLoc();
	}

	public void keyPressed(KeyEvent e) {
		player.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		player.keyReleased(e);
	}

	public ArrayList<BaseActor> getPlayerList() {
		ArrayList<BaseActor> players = new ArrayList<BaseActor>();

		for (String playerID : charactersToAdd.keySet()) {
			characters.put(playerID, charactersToAdd.get(playerID));
		}
		charactersToAdd.clear();

		for (Player player : characters.values()) {
			players.add(player);
		}
		return players;
	}

	public ArrayList<BaseActor> getPowerUps() {
		ArrayList<BaseActor> PU = new ArrayList<BaseActor>();

		for (String powerUpID : powerupsToAdd.keySet()) {
			powerups.put(powerUpID, powerupsToAdd.get(powerUpID));
		}
		powerupsToAdd.clear();
		for (String powerUpID : powerupsToRemove) {
			powerups.remove(powerUpID);
		}
		powerupsToRemove.clear();

		for (PowerUp powerup : powerups.values()) {
			PU.add(powerup);
		}
		return PU;
	}

	public ArrayList<BaseActor> getResources() {
		ArrayList<BaseActor> GR = new ArrayList<BaseActor>();

		for (String resourceID : resourcesToAdd.keySet()) {
			resources.put(resourceID, resourcesToAdd.get(resourceID));
		}
		resourcesToAdd.clear();
		for (String resourceID : resourcesToRemove) {
			resources.remove(resourceID);
		}
		resourcesToRemove.clear();

		for (Resource resource : resources.values()) {
			GR.add(resource);
		}
		return GR;
	}
	
	public ArrayList<BaseActor> getBoundaries() {
		return BOUNDARY_GEN.getActors();
	}

	public Player getClientPlayer() {
		return this.player;
	}

	public void removePowerUP(PowerUp PU) {
		NC.removePowerUp(PU);
		powerups.remove(NC.getPowerUpID(PU));
	}

	public void removeResource(Resource r) {
		NC.removeResource(r);
		resources.remove(NC.getResourceID(r));
	}
}