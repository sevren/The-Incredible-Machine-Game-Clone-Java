/*
 *  LevelSummaryDialog.java
 *
 *  Created by Nathaniel Ridder on 20/10/08
 *  Copyright 2008 Group Four. All rights reserved.
 *  Description: A custom dialog class that creates a window
 *               for information needed to be known before a 
 *               level is started.
 */
package tim.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Custom dialog class that creates a window holding information
 * needed to be known before a level is started.
 * @author Nathaniel Ridder - Group Four of CIS3750
 */
public class LevelSummaryDialog extends Dialog implements ActionListener
{
	/**
	 * Default Constructor. For testing purposes.
	 * @param parent Required for dialogs to run.
	 */
	public LevelSummaryDialog (JFrame parent)
	{
		 super(parent, "", true);
		 initializeGUI("Generic name - ##", "idonotexist", "This is a summary. It does nothing.");
		 
		 this.pack();
		 this.setLocationRelativeTo(parent);
	}
	
	/**
	 * The real constructor. Apparently, dialogs *need* a parent JComponent to run off of.
	 * @param parent Required for dialogs to run. 
	 * @param title String with this structure: <level name> - <level #>
	 * @param pass A sequence of alphanumerics.
	 * @param summary A short description of the level, about three sentences max.
	 * Hopefully already formatted so it fits a width of 40 characters. Can also
	 * include a hint for particularly difficult levels.
	 */
	public LevelSummaryDialog (JFrame parent, String title, String pass, String summary)
	{
		super(parent, "", true);
		initializeGUI(title, pass, summary);
		
		this.pack();
		this.setLocationRelativeTo(parent);
	}
	
	//Creating the GUI components, based on parameters
	private void initializeGUI(String title, String password, String summary)
	{
		this.setLayout(new BorderLayout());
		this.setSize(new Dimension(400, 400));
		this.setResizable(false);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		this.add(panel, BorderLayout.PAGE_START);
		
		JLabel label = new JLabel("LEVEL: " + title);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(label);
		
		panel.add(Box.createRigidArea(new Dimension(400, 5)));
		
		label = new JLabel("PASSWORD: " + password);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(label);
		
		panel.add(Box.createRigidArea(new Dimension(400, 15)));
		
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		this.add(panel, BorderLayout.CENTER);
		
		label = new JLabel("SUMMARY");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(label);
		
		panel.add(Box.createRigidArea(new Dimension(400, 5)));
		
		JTextArea text_area = new JTextArea(summary);
		text_area.setEditable(false);
		text_area.setLineWrap(true);
		text_area.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
		text_area.setPreferredSize(new Dimension(300, 100));
		panel.add(text_area);
		
		JButton button = new JButton ("OK");
		button.setActionCommand("OK");
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.addActionListener(this);
		this.add(button, BorderLayout.PAGE_END);
		
	}
	
	/** Handles button events, makes the dialog disappear. */
	public void actionPerformed (ActionEvent e)
	{
		this.setVisible(false);
	}
	
	/**
	 * Sets this dialog to be visible, which keeps
	 * focus on it until the user is finished with
	 * it.
	 */
	public void startDialog()
	{
		this.setVisible(true);
	}
}