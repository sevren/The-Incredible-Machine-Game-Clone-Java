//
//	TimMenuScreen.java
//
//
//	Created by Nathaniel Ridder on 29/09/08
//	Copyright 2008 Group Four. All rights reserved.
//
package tim.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import tim.game.TimEvent;

public class TimMenuScreen extends Dialog implements ActionListener
{
	private String action = null;
	private String name = "Guest";
	private JButton new_game, skip_level, settings, exit, freeplay, resume;
	
	/**
	 * Constructor - Creates a custom menu dialog, for the main
	 * menu. Displays on screen upon initialization.
	 * @param parent The container associated with this instance.
	 */
	public TimMenuScreen (JFrame parent)
	{
		super (parent, "Welcome, Guest!", true);

		initializeGUI();
		
		this.pack();
		this.setLocationRelativeTo(parent);
	}
	
	/**
	 * Constructor - Creates a custom menu dialog, for the main menu.
	 * Displays on screen upon initialization.
	 * @param parent The container associated with this instance.
	 * @param n The name of the user currently loaded.
	 */
	public TimMenuScreen (JFrame parent, String n)
	{
		super (parent, "Welcome, " + n + "!", true);
		
		initializeGUI();
		
		this.pack();
		this.setLocationRelativeTo(parent);
	}
	
	//create and set the GUI components
	private void initializeGUI()
	{
		this.setSize(new Dimension(350, 200));
		this.setPreferredSize(new Dimension(350, 200));
		this.setResizable(false);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));		
		
		new_game = new JButton("New Game");
		new_game.setActionCommand(TimEvent.NEWGAME);
		new_game.setAlignmentX(Component.CENTER_ALIGNMENT);
		new_game.addActionListener(this);
		this.add(new_game);
        
        resume = new JButton("Resume Game");
        resume.setActionCommand(TimEvent.RESUME);
		resume.setAlignmentX(Component.CENTER_ALIGNMENT);
		resume.addActionListener(this);
		this.add(resume);
		
		skip_level = new JButton("Skip To Level");
		skip_level.setActionCommand(TimEvent.SKIPLVL);
		skip_level.setAlignmentX(Component.CENTER_ALIGNMENT);
		skip_level.addActionListener(this);
		this.add(skip_level);
        
        freeplay = new JButton("Free Play");
		freeplay.setActionCommand(TimEvent.FREEPLAY);
		freeplay.setAlignmentX(Component.CENTER_ALIGNMENT);
		freeplay.addActionListener(this);
		this.add(freeplay);
        
        settings = new JButton("Settings");
		settings.setActionCommand(TimEvent.SETTINGS);
		settings.setAlignmentX(Component.CENTER_ALIGNMENT);
		settings.addActionListener(this);
		this.add(settings);
		
		exit = new JButton("Exit to Desktop");
		exit.setActionCommand(TimEvent.EXIT);
		exit.setAlignmentX(Component.CENTER_ALIGNMENT);
		exit.addActionListener(this);
		this.add(exit);
        
	}
	
	/** Event handled here is making the main menu disappear. */
	public void actionPerformed(ActionEvent e)
	{
		if (e != null)
		{
			action = e.getActionCommand();
			this.setVisible(false);
		}
	}
	
	/**
	 * Testing purposes. Pretends to press the button
	 * that is named in the parameter.
	 * @param button_name The button to virtually press.
	 */
	public void simulateButtonPress (String button_name)
	{
		if (button_name == "New Game")
			new_game.doClick();
		else if (button_name == "Skip To Level")
			skip_level.doClick();
        else if (button_name == "Free Play")
			freeplay.doClick();
		else if (button_name == "Settings")
			settings.doClick();
		else if (button_name == "Exit to Desktop")
			exit.doClick();
	}
	
	/**
	 * Sets this dialog to be visible, which keeps
	 * focus on it until the user is finished with it.
	 */
	public void startDialog()
	{
		this.setVisible(true);
	}
	
	/**
	 * Accessor - Returns the user's selection.
	 * @return The button that was chosen by the user.
	 */
	public String getAction()
	{
		return action;
	}
	
	/**
	 * Mutator - Sets the name that is displayed on top
	 * of the main menu to the passed in parameter.
	 * @param n The name to change the title to.
	 */
	public void setName(String n)
    {
        name = n;
        this.setTitle("Welcome, " + name + "!");
    }
    
	/**
	 * Adds a button to the Main Menu.
	 * @param name The name of the button to allow.
	 */
    public void addButton(String name) {
        JButton button = new JButton(name);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.addActionListener(this);
		this.add(button);
    }
}
