//
//  TimLevel.java
//  
//
//  Created by Group Four on 23/09/08.
//  Copyright 2008 Group Four. All rights reserved.
//
package tim.model;

import Widgets.Widget;

import java.util.HashSet;

import net.phys2d.math.Vector2f;
import net.phys2d.raw.World;

/**
    Classes that store individual game levels must implement the TimLevel class.
  */
public interface TimLevel
{

    /**
        Check for success conditions on the level.
        @return True if level objectives are all complete, false otherwise.
      */
    boolean levelCompleted();

    /**
      Get the objectives list for the level.
      @return The objectives in string form.
      */
    String getObjectives();

    /**
      Get the short objective tag for top of game screen.
      @return The objective tag in string form.
      */
    String getShortObjectives();

	/**
      Returns password for this level
      @return The password.
      */
    String getPassword();

    /**
      Returns list of widgets associated with this level.

      Important notes:

      The first time this list is returned any locked widgets (that are thus pre-placed parts of the 
      level) must have their states, positions and directions set completely such that they can be
      directly added to the world ready-to-go.

      Additionally during the first call positions for all of the locked widgets <b>must</b> be 
      arranged by the level such that none of their boundaries overlap.

      On subsequent calls this function will continue to return the same complete list of widgets 
      associated with this level but the states/positions/directions/etc of any of those widgets are
      no longer any concern of the implementer - the entire list is returned as-is.

      @return Arraylist of all widgets for the level.
      */
    HashSet<Widget> getWidgets();

    /**
      Returns physics world for this level.

      This world should be instantiated once and then the same one returned each time getWorld is 
      called.  Objects should not be added to the world by the TimLevel implementers.  TimWorld will 
      handle adding of all locked and user-placed widgets to the world.

      @return The level's phys2d world.
      */
    World getWorld();
    
    /**
      Returns the display string shown on the header 
      jpanel displaying level information
     
      @return level's display label
     */
    String getDisplayLabel();
    
    /**
      Returns whether the current level is locked or not
     
      @return true - locked, false - unlocked
     */
    boolean isLocked();

}

