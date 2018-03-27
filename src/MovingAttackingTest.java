import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

import acm.graphics.GOval;
import acm.program.GraphicsProgram;

public class MovingAttackingTest extends GraphicsProgram {
	public static final int SCREEN_HEIGHT = 1024;
	public static final int SCREEN_WIDTH = 768;
	public static final int BALL_CIRC = 100;
	public static final int ATTACK_RING = 150;

	char keyI;
	boolean wKeyPressed; // Up
	boolean sKeyPressed; // Down
	boolean aKeyPressed; // Left
	boolean dKeyPressed; // Right

	private GOval ball;
	private GOval ring;
	private Timer testTimer = new Timer(2000, this);

	public void run() {
		ball = new GOval(SCREEN_HEIGHT / 2, SCREEN_WIDTH / 2, BALL_CIRC, BALL_CIRC);
		ring = new GOval((SCREEN_HEIGHT - (ATTACK_RING - BALL_CIRC)) / 2,
				(SCREEN_WIDTH - (ATTACK_RING - BALL_CIRC)) / 2, ATTACK_RING, ATTACK_RING);
		ball.setFilled(true);
		ball.setFillColor(Color.RED);
		addKeyListeners();
		addMouseListeners();

		add(ball);
	}

	public void keyPressed(KeyEvent e) {
		// if the a flag it set to true and e.getKeyChar == 'w'
		// then move directionally up and left
		// standard 4 directions
		//north east
		if (dKeyPressed == true && wKeyPressed == true) {
			ball.move(50, -50);
			ring.move(50, -50);
		}
		
		//northwest
		else if (aKeyPressed == true && wKeyPressed == true) {
			ball.move(-50, -50);
			ring.move(-50, -50);
		}
		
		//southeast
		else if (dKeyPressed == true && sKeyPressed == true) {
			ball.move(50, 50);
			ring.move(50, 50);
		}
		
		//southwest
		else if (aKeyPressed == true && sKeyPressed == true) {
			ball.move(-50, 50);
			ring.move(-50, 50);
		}
		else if (e.getKeyChar() == 'w') {
			wKeyPressed = true;
			ball.move(0, -50);
			ring.move(0, -50);
		} else if (e.getKeyChar() == 's') {
			sKeyPressed = true;
			ball.move(0, 50);
			ring.move(0, 50);
		}

		else if (e.getKeyChar() == 'a') {
			aKeyPressed = true;
			ball.move(-50, 0);
			ring.move(-50, 0);
		} else if (e.getKeyChar() == 'd') {
			dKeyPressed = true;
			ball.move(50, 0);
			ring.move(50, 0);
		}

		// //Bidirectional
		// else if (e.getKeyChar() == 'w' && e.getKeyChar() == 'd')
		// {
		// ball.move(50, -50);
		// ring.move(50, -50);
		// }

	}

	public void keyReleased(KeyEvent e) {
		keyI = e.getKeyChar();
		if (keyI == 'w') {
			wKeyPressed = false;
		}
		if (keyI == 'a') {
			sKeyPressed = false;
		}
		if (keyI == 's') {
			aKeyPressed = false;
		}
		if (keyI == 'd') {
			dKeyPressed = false;
		}
	}

	public void mousePressed(MouseEvent e) {
		add(ring);
		testTimer.start();

	}

	// public void mouseReleased(MouseEvent e)
	// {
	// remove(ring);
	// testTimer.stop();
	//
	// }

	public void actionPerformed(ActionEvent e) {
		remove(ring);
	}

	public void init() {
		setSize(SCREEN_HEIGHT, SCREEN_WIDTH);
	}
}
