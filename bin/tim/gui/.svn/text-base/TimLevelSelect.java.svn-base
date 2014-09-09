//
//  TimLevelSelect.java
//  
//
//  Created by Group Four on 14/10/08.
//  Copyright 2008 Group Four. All rights reserved.
//

package tim.gui;

import java.util.*;
import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Dialog for choosing what level to proceed to for
 * skip level functionality.  The View for Puzzle Manager.
 */
public class TimLevelSelect extends Dialog implements ActionListener {
	
	private int selected;
	private JTextArea levelTxt;
	private int totalLevels;
	private int unlockedLevels;
	private ArrayList<JButton> lvlButtonList;
	
    /**
     * Constructor takes reference to the calling frame,
     * the number of levels in total managed by Puzzle Manager
     * and the number of levels unlocked from the current players
     * profile.
     *
     * @param top parent frame
     * @param numOfLevels number of levels in puzzle manager
     * @param lvlsUnlocked number of levels unlocked in player profile
     */
	public TimLevelSelect(JFrame top, int numOfLevels, int lvlsUnlocked) {
		super(top, "Select a Level to Load or Unlock", true);
		totalLevels = numOfLevels;
		unlockedLevels = lvlsUnlocked;
		initializeOptions();
		
		this.setLocationRelativeTo(top);
	}
	
    /**
     * Method that dynamically creates a list of numbered locked or 
     * unlocked levels for the given number of unlocked levels and total levels
     * specified in the constructor
     */
	private void initializeOptions() {
		this.setSize(new Dimension(400, 400));
		this.setLayout(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		
		lvlButtonList = new ArrayList<JButton>();
		for(int i = 0; i < totalLevels; i++) {
			if(i+1 <= unlockedLevels)
				lvlButtonList.add(new JButton("Level" + (i+1)));
			else
				lvlButtonList.add(new JButton("Level " + (i+1) + " Locked"));
			lvlButtonList.get(i).addActionListener(this);
			buttonPanel.add(lvlButtonList.get(i));			
		}
	
		JScrollPane jpane = new JScrollPane(buttonPanel);
		this.add(jpane, BorderLayout.CENTER);
		
		JButton button = new JButton("OK");
		button.setActionCommand("OK");
		button.addActionListener(this);
		this.add(button, BorderLayout.SOUTH);
	}
	
    /**
     * Retrieves the button selected from the menu
     *
     * @param e the button event
     */
	public void actionPerformed(ActionEvent e) {
		if(e != null) {
			//add 1 to compensate for array list 0 entry being Level 1
			selected = lvlButtonList.indexOf(e.getSource()) + 1;
			this.setVisible(false);
		}
	}
    
    /**
     * Returns the level selected from the menu
     *
     * @return the level selected from the menu
     */
	public int getLevelSelected() {
		return selected;
    }
    
    /**
     * Sets the number of unlocked levels to be display
     * in the menu
     * @param levels number of levels locked up to and including
     */
    public void setUnlockedLevels(int levels) {
        unlockedLevels = levels;
    }
    
    /**
     * Gets the number of unlocked levels currently display in 
     * the menu
     * @return number of unlocked levels in menu
     */
    public int getUnlockedLevels() {
        return unlockedLevels;
    }
}
