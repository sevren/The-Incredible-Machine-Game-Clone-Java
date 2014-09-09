/*
 *  ScoreDialog.java
 *  Created by Nathaniel Ridder on 20/10/08
 *  Copyright 2008 Group Four. All rights reserved.
 *  Description: A custom dialog class that creates a window
 *               for information needed to be known after a
 *               level is completed.
 */

package tim.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Custom dialog class that creates a window holding information
 * needed to be known after a level is completed.
 */
public class ScoreDialog extends Dialog implements ActionListener
{
	private int total_user_score;
	private int total_level_score;
	private int currency_gain;
	
	/**
	 * Default Constructor. For testing purposes.
	 * @param parent Required for dialogs to run.
	 */
	public ScoreDialog (JFrame parent)
	{
		super (parent, "Level Complete!", true);
		
		total_level_score = 15000 + calculateScoreFromTime(90);
		currency_gain = calculateCurrencyGain((double)total_level_score);
		
		initializeGUI(90, 15000, 0);
		updateScore(total_level_score, 0);
	}
	
	/**
	 * The real constructor. The parameters, aside from the first, are harvested
	 * from a completed puzzle.
	 * @param parent Required for dialogs to run.
	 * @param seconds_left The number of seconds left in the puzzle bonus time.
	 * @param score The score associated with finishing the level.
	 * @param prev_score The score that the user has previously acquired from this level.
	 * @param user_score The total score that the user has acquired so far.
	 */
	public ScoreDialog(JFrame parent, int seconds_left, int score, int prev_score, int user_score)
	{
		super (parent, "Level Complete!", true);
		
		total_level_score = score + calculateScoreFromTime(seconds_left);
		currency_gain = calculateCurrencyGain((double)total_level_score);
		
		initializeGUI(seconds_left, score, user_score);
		//If the score is above player's previous, then we want to give the difference to the player's score
		if (prev_score < total_level_score)
			updateScore(total_level_score - prev_score, user_score);
		else
			updateScore(0, user_score);
			
		this.setLocationRelativeTo(parent);
	}
	
	/* Set up of the first part of the GUI
	 *   Time left, Bonus gained from time.
	 *   Level Score and user's previous score.
	 */
	private void initializeGUI(int seconds, int level_score, int user_score)
	{
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setSize(new Dimension(400, 400));
		this.setResizable(false);
		
		JPanel panel = new JPanel();
		this.add(panel);
		
		JLabel label = new JLabel("Time Left: ");
		label.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(label);
		
		label = new JLabel(parseTimeFromSeconds(seconds));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(label);
		
		panel = new JPanel();
		this.add(panel);
		
		label = new JLabel("Bonus Score: ");
		label.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(label);
		
		label = new JLabel(parseScoreFromInt(calculateScoreFromTime(seconds)));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(label);
		
		panel = new JPanel();
		this.add(panel);
		
		label = new JLabel("Level Score: ");
		label.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(label);
		
		label = new JLabel(parseScoreFromInt(level_score));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(label);
		
		panel = new JPanel();
		this.add(panel);
		
		label = new JLabel("Your score: ");
		label.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(label);
		
		label = new JLabel(parseScoreFromInt(user_score));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(label);
		
		label = new JLabel("-------------------------------");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(label);
		
	}
	
	/* Second part of the GUI setup
	 *   Displays the increase to the score, the user's score
	 *   before playing the level and after.
	 */ 
	private void updateScore(int increase_to_score, int user_score)
	{
		JPanel panel = new JPanel();
		this.add(panel);
		
		JLabel label = new JLabel("Gained: ");
		label.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(label);
		
		label = new JLabel("+" + parseScoreFromInt(increase_to_score));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(label);
		
		panel = new JPanel();
		this.add(panel);
		
		label = new JLabel("Your New Score: ");
		label.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(label);
		
		label = new JLabel(parseScoreFromInt(increase_to_score + user_score));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(label);
		
		panel = new JPanel();
		this.add(panel);
		
		label = new JLabel("You have gained " + currency_gain + " coins.");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(label);
		
		JButton button = new JButton("OK");
		button.setActionCommand("OK");
		button.addActionListener(this);
		this.add(button);
		
		total_user_score = increase_to_score + user_score;
	}
	
	//Parses the String, so it has commas in between sets of 3 numbers.
	private String parseScoreFromInt(int score)
	{
		String parsed_score = new String();
		int pertinent_part = 0;
		if (score >= 1000000000)
		{
			pertinent_part = score / 1000000000;
			parsed_score += Integer.toString(pertinent_part) + ",";
			score = score % 1000000000;
		}
		if (score >= 1000000)
		{
			pertinent_part = score / 1000000;
			parsed_score += Integer.toString(pertinent_part) + ",";
			score = score % 1000000;
		}
		if (score >= 1000)
		{
			pertinent_part = score / 1000;
			parsed_score += Integer.toString(pertinent_part) + ",";
			score = score % 1000;
		}
		if (score >= 0)
		{
			parsed_score += String.format("%1$03d  points", score);
		}
		else
		{
			parsed_score = "000  points";
		}
		
		return parsed_score;
	}
	
	//Converts the String, so it has colons and whatnot in the right spaces.
	private String parseTimeFromSeconds (int seconds)
	{
		String parsed_time = new String();
		int pertinent_part;
		if (seconds >= 0)
		{
			if (seconds >= 3600)
			{
				pertinent_part = seconds / 3600;
				parsed_time = Integer.toString(pertinent_part) + ":";
				seconds = seconds % 3600;
			}
			if (seconds >= 60)
			{
				pertinent_part = seconds / 60;
				parsed_time += Integer.toString(pertinent_part) + ":";
				seconds = seconds % 60;
			}
			
			parsed_time += String.format("%1$02d", seconds);
		}
		else
		{
			parsed_time = "-";
			if (seconds <= 3600)
			{
				pertinent_part = (seconds / 3600) * -1;
				parsed_time = Integer.toString(pertinent_part) + ":";
				seconds = seconds % -3600;
			}
			if (seconds <= 60)
			{
				pertinent_part = (seconds / 60) * -1;
				parsed_time = Integer.toString(pertinent_part) + ":";
				seconds = seconds % -60;
			}
			parsed_time += Integer.toString((-1)*seconds);
		}
		
		return parsed_time;
	}
	
	private int calculateScoreFromTime (int seconds)
	{
		//basic formula, change if need be.
		return seconds * 20;
	}
	
	private int calculateCurrencyGain (double score)
	{
		//ditto, change things if you need to.
		return (int)(Math.floor(score / 1000));
	}
	
	/** Handles button event */
	public void actionPerformed (ActionEvent e)
	{
		this.setVisible(false);
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
	 * Retrieves the score calculated by the dialog.
	 * @return The score achieved by the user for this level.
	 */
	public int getLevelScore ()
	{
		return total_level_score;
	}
	
	/**
	 * Retrieves the new total score that the user has gained
	 * from completing this level.
	 * @return The user score to update the profile with.
	 */
	public int getNewUserScore ()
	{
		return total_user_score;
	}
	
	/**
	 * Retrieves the currency gained from completing this level.
	 * @return The currency to add to the profile.
	 */
	public int getCurrency()
	{
		return currency_gain;
	}
}
