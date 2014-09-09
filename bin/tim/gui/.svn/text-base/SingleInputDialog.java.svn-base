/*
 *  SingleInputDialog.java
 *
 *  Created by Nathaniel Ridder on 15/10/08
 *  Copyright 2008 Group Four. All rights reserved.
 *  Description:  A generic class to make a Dialog screen that allows
 *                for a single input to be drawn from the user. 
 */
package tim.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.util.*;

/**
 * Generic class for a creation of a Dialog screen for the sole
 * purpose of getting a single line of input from the user.
 */
public class SingleInputDialog extends Dialog implements ActionListener
{
	private String input;
	private JTextField text_field;
	private JButton okButton;
    private JButton cancButton;
	private JButton curButton;
    
	private String action_command;
	private int currency_needed;
	
	/**
	 * Constructor - Creates a custom dialog screen and displays it
	 * for the user to use.
	 * @param parent The container associated with this instance.
	 * @param title The title of the Dialog screen. Will be displayed on the title bar.
	 * @param input_question The question to ask the user.
	 */
	public SingleInputDialog (JFrame parent, String title, String input_question)
	{
		super (parent, title, true);
		
		initializeGUI(input_question);

		setupButtons(0);
        input = new String();
		currency_needed = 0;
		
		this.setLocationRelativeTo(parent);
	}
	
	/**
	 * Constructor - Use this constructor when getting a password from the user
	 * and want to have an option to use currency instead of having the right input.
	 * @param parent The container associated with this instance.
	 * @param title The title of the Dialog screen. Will be displayed on the title bar.
	 * @param input_question The question to ask the user.
	 * @param amount The number to put on the second button, signifying the currency
	 * needed to bypass the input_question.
	 */
	public SingleInputDialog (JFrame parent, String title, String input_question, int amount)
	{
		super (parent, title, true);
		
		initializeGUI(input_question);

		setupButtons(amount);
        input = new String();
		currency_needed = amount;
		
		this.setLocationRelativeTo(parent);
	}
	
	//Creates the GUI components of the dialog, first part
	private void initializeGUI(String input_question)
	{
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setSize(new Dimension(320, 100));
		this.setResizable(false);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		this.add(panel);
		JLabel label = new JLabel(input_question);
		label.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
		panel.add(label, BorderLayout.WEST);
		
		text_field = new JTextField();
		this.add(text_field);
	}
	
	//Creates the GUI components of the dialog, buttons part.
	private void setupButtons (int amount)
	{
		JPanel panel = new JPanel();
		this.add(panel);
		
		okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		okButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		okButton.addActionListener(this);
		panel.add(okButton);
		
		cancButton = new JButton ("Cancel");
		cancButton.setActionCommand("Cancel");
		cancButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		cancButton.addActionListener(this);
		panel.add(cancButton);
		
		if (amount > 0)
		{
			curButton = new JButton("Unlock (" +amount+ "coins)");
			curButton.setActionCommand("Unlock");
			curButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			curButton.addActionListener(this);
			panel.add(curButton);
		}
	}
	
	/** Handles button press events */
	public void actionPerformed(ActionEvent e)
	{
        if(e != null) {
            if(e.getActionCommand().equals("OK"))
                input = text_field.getText();
			if(e.getActionCommand().equals("Unlock"))
				input = "!UNLOCK!";
        }
        this.setVisible(false);
	}
	
	/**
	 * Sets this dialog to be visible, which keeps
	 * focus on it until the user is finished.
	 */
	public void startDialog()
	{
		this.setVisible(true);
	}
	
	/**
	 * Used for JUnit testing purposes.
	 * @param input Any sort of string you wish.
	 * It doesn't matter, but the output will be the
	 * same as what you pass in.
	 */
	public void simulateInput(String input)
	{
		text_field.setText(input);
		okButton.doClick();
	}
	
	/**
	 * Accessor - Returns the user's input.
	 * @return The input retrieved from the textfield.
	 */
	public String getInput()
	{
		return input;
	}
	
	/**
	 * Retrieves which button was pressed.
	 * @return The string that represents the button the user has pressed.
	 */
	public String getAction()
	{
		return action_command;
	}
	
	/**
	 * Retrieves the amount required to bypass the input.
	 * @return The value required from the user to bypass a password.
	 */
	public int getCurrencyNeeded ()
	{
		return currency_needed;
	}
}
