package com.edd.circlebrawl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

// Driver Class
public class MultiplayerDriver extends MainApplication implements Tick {
	
	public final int MAP_WIDTH = 1024;
	public final int MAP_HEIGHT = 768;
	public final int WINDOW_WIDTH = 1024;
	public final int WINDOW_HEIGHT = 768;

	private JTextField name;
	private JPanel[] login = {new JPanel()};
	private JLabel nameLabel;
	private JButton button;
	private boolean myNameSet = false;
	@Override
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	    setBackground(new Color(85,153,187));
	    name = new JTextField();
	    nameLabel= new JLabel("Enter name: ");
	    button = new JButton();
	    button.setText("Join!");
	    login[0].add(nameLabel);
	    login[0].add(name);
	    login[0].add(button);
	    for(JPanel p : login){
	        this.add(p);
	    }
	    name.setBounds(5, 5, 100, 100);
	    name.setPreferredSize(new Dimension(120,20));
	    button.addActionListener(new ActionListener() {
	    	  public void actionPerformed(ActionEvent e)
	    	  {
	    		  System.out.println(name.getText());
	    		  myNameSet = true;
	    	  }
	    	
	    });
	}
	
	@Override
	public void run() {
		while(!myNameSet) {
			System.out.println("");
		}
		System.out.println("name set");
		//this.
		//new MultiplayerSam_Test();
	}
}
