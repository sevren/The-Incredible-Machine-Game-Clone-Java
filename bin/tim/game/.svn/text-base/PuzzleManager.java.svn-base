//
//  PuzzleManager.java
//  
//
//  Created by Group Four on 06/10/08.
//  Copyright 2008 Group Four. All rights reserved.
//
package tim.game;

import java.util.ArrayList;
import tim.model.*;

/**
 * Controller class for TimLevelSelect menu and, responsible for
 * encapsulation of levelParser and player profile
 */
public class PuzzleManager {
    /*Default xml level set*/
    private static final String DEFAULT_FILE = "./resources/DefaultLevelSet.xml";
	private int totalLevels;
    private LevelParser levelParser;
	private PlayerProfile currentProfile;
	
    /**
     * Basic Constructor creates a new puzzle manager
     * with a new basic player profile and default xml level set
     */
	public PuzzleManager() {
		currentProfile = new PlayerProfile();//new basic profile... starting on level 1
        levelParser = new LevelParser(DEFAULT_FILE);
	}
	
    /**
     * Constructor creates a new puzzle manager
     * with the provided player profile and the default xml level set
     * @param playerProfile player profile to use
     */
	public PuzzleManager(PlayerProfile playerProfile) {
		currentProfile = playerProfile;
        
        levelParser = new LevelParser(DEFAULT_FILE);
	}
    
    /**
     * Constructor creates a new puzzle manager
     * with the provided player profile and the specified xml level set
     *
     * @param playerProfile player profile to use
     * @param file xml level set to load
     */
	public PuzzleManager(PlayerProfile playerProfile, String file) {
		currentProfile = playerProfile;
        levelParser = new LevelParser(file);
	}
    
    /**
     * Sets the current profile for Puzzle Manager to the one specified
     * 
     * @param playerProfile player profile to use as current profile
     */
	public void setProfile(PlayerProfile playerProfile) {
		currentProfile = playerProfile;
	}

    /**
     * Add a puzzle level to the xml LevelParser
     * 
     * @param newLevel puzzle to add
     */
    public void addLevel(Puzzle newLevel) {
        //puzzleList.add(newLevel);
        //use the add Puzzle methods in level parser
    }
	
    /**
     * Get a specified level from the xml level parser
     * @param level puzzle level number to get
     * @return the puzzle to get by level number
     **/
    public Puzzle getLevel(int level) {
        if(level <= levelParser.getTitlesList().size())
            return levelParser.getPuzzle(level);
        else {
            System.out.println("Error: level entered not available");
            return null;
        }
    }
                       
    /**
     * Gets the current player profile in used by puzzle manager
     *
     * @return the current player profile
     */
	public PlayerProfile getProfile() {
		return currentProfile;
	}
	
    /**
     * Gets the number of unlocked levels from the current player profile
     *
     * @return the number of unlocked levels
     **/
	public int getUnlockedLevels() {
		return currentProfile.getLevels();
	}
	
    /**
     * Attempt to unlock a puzzle level held within puzzle manager
     * @param level the level to unlocked
     * @param pw the password entered
     * 
     * @return true if password is correct and level is unlocked, false otherwise
     **/
	public boolean unlockLevel(int level, String pw) {
		if((getLevel(level).getPass()).equals(pw)) {
			currentProfile=this.getProfile();
			currentProfile.setLevels(level);
			currentProfile.update();
			return true;
		} else
			return false;
     
	}
	
}
