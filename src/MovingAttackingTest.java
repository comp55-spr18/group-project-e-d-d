import acm.program.GraphicsProgram;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import acm.graphics.*;
import acm.program.*;
import javax.swing.Timer;



public class MovingAttackingTest extends GraphicsProgram
{
	public static final int SCREEN_HEIGHT = 1024;
	public static final int SCREEN_WIDTH = 768;
	public static final int BALL_CIRC = 100;
	public static final int ATTACK_RING = 150;
	
	private GOval ball;
	private GOval ring;
	private Timer testTimer = new Timer(2000, this);

	
	public void run()
	{
		 ball = new GOval(SCREEN_HEIGHT/2, SCREEN_WIDTH/2, BALL_CIRC, BALL_CIRC);
		 ring = new GOval((SCREEN_HEIGHT - (ATTACK_RING - BALL_CIRC))/2, (SCREEN_WIDTH - (ATTACK_RING - BALL_CIRC))/2, ATTACK_RING, ATTACK_RING);
		 ball.setFilled(true);
		 ball.setFillColor(Color.RED);
		 addKeyListeners();
		 addMouseListeners();
		 
		 add(ball);
	}
	
	public void keyPressed(KeyEvent e)
	{
		
		//standard 4 directions
		if (e.getKeyChar() == 'w')
		{
			ball.move(0, -50);
			ring.move(0, -50);
		}
		else if (e.getKeyChar() == 's')
		{
			ball.move(0, 50);
			ring.move(0, 50);
		}
		
		else if (e.getKeyChar() == 'a')
		{
			ball.move(-50, 0);
			ring.move(-50, 0);
		}
		else if (e.getKeyChar() == 'd')
		{
			ball.move(50, 0);
			ring.move(50, 0);
		}
		
		//Bidirectional
		else if (e.getKeyChar() == 'w' && e.getKeyChar() == 'd')
		{
			ball.move(50, -50);
			ring.move(50, -50);
		}
	}
	
	public void mousePressed(MouseEvent e) {
		add(ring);
		testTimer.start();
		
	}
	
//	public void mouseReleased(MouseEvent e)
//	{
//		remove(ring);
//		testTimer.stop();
//		
//	}
	
    public void actionPerformed(ActionEvent e)
	{
    	remove(ring);    	
	}
	
	public void init() 
	{
		setSize(SCREEN_HEIGHT, SCREEN_WIDTH);
	}
}
