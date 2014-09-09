//
//  TimWorldTest.java
//
//  Created by Group Four on 15/11/08.
//  Copyright 2008 Group Four.  All rights reserved.

package test.model;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.Assert.*;

import Widgets.Widget;
import Widgets.*;

import tim.model.TimWorld;

import net.phys2d.math.Vector2f;
import net.phys2d.raw.World;

/**
  JUnit tests for TimWorld.
  */
public class TimWorldTest extends TestCase
{
    /**
      Test that a widget is correctly added to the world.
      */
    @Test public void testAddWidget()
    {
        // Setup
        TimWorld world = new TimWorld();
        MysteryBox mbox = new MysteryBox();

        // Pre
        assertEquals(world.getWidgets().size(), 0);

        // Action Tested
        world.addWidget(mbox);

        // Post
        assertEquals(world.getWidgets().size(), 1);
        assertTrue(world.getWidgets().contains(mbox));
    }

    /**
      Test that a widget is correctly removed from the world.
      */
    @Test public void testRemoveWidget()
    {
        // Setup
        TimWorld world = new TimWorld();
        MysteryBox mbox = new MysteryBox();
        world.addWidget(mbox);

        // Pre
        assertTrue(world.getWidgets().contains(mbox));

        // Action Tested
        world.removeWidget(mbox);

        // Post
        assertFalse(world.getWidgets().contains(mbox));
    }

    /**
      Test that world state is properly reset.

      TODO: could be improved by testing with a widget whose setDirection/getDirection methods are meaningful
      */
    @Test public void testPrepareReset()
    {
        // Setup
        TimWorld world = new TimWorld();
        MysteryBox mbox = new MysteryBox();
        
        /* set initial pos */
        Vector2f pos = new Vector2f(100.0f,100.0f);
        mbox.setPosition(pos);

        /* add to world */
        world.addWidget(mbox);

        /* prepare world */
        world.prepare();

        /* step world 10 times, widget should have fallen from initial position now */
        for(int i = 0; i < 10; i++)
        {
            world.step();
        }

        // Pre
        /* make sure widget has moved */
        assertFalse(mbox.getPositionX() == pos.getX() && mbox.getPositionY() == pos.getY());

        // Action Tested
        world.reset();

        // Post
        /* make sure widget is back to initial pos */
        assertTrue(mbox.getPositionX() == pos.getX() && mbox.getPositionY() == pos.getY());
    }


    /**
      Test clearing world.
      */
    @Test public void testClear()
    {
        // Setup
        TimWorld world = new TimWorld();
        MysteryBox mbox = new MysteryBox();

        /* add to world */
        world.addWidget(mbox);

        // Pre
        /* make sure widget is in the world */
        assertTrue(world.getWidgets().contains(mbox));

        // Action Tested
        world.clear();

        //Post
        /* make sure widgets set is empty now */
        assertEquals(world.getWidgets().size(),0);

    }

	/**
	  Check that testPlacement returns safe for a widget in a valid position
	*/
    @Test public void testCleanPlacement()
    {
    	//Setup
		TimWorld world = new TimWorld();
		
		/* create a box and stick it in the world */
		SimpleBox placedBox = new SimpleBox();
		placedBox.setPosition(new Vector2f(200.0f, 200.0f));
		world.addWidget(placedBox);
		
		/* create second box */
		SimpleBox newBox = new SimpleBox();
		
		/* make sure this box would be safe @ 300,300 */
		assertTrue(world.testPlacement(newBox, 300.0f, 300.0f));
    }
	/**
	  Check that testPlacement returns not safe for a widget with corner overlaps
	*/
    @Test public void testCornerOverlapPlacement()
    {
    	//Setup
		TimWorld world = new TimWorld();
		
		/* create a box and stick it in the world */
		SimpleBox placedBox = new SimpleBox();
		placedBox.setPosition(new Vector2f(200.0f, 200.0f));
		world.addWidget(placedBox);
		
		/* create second box */
		SimpleBox newBox = new SimpleBox();
		
		/* make sure this box would be safe @ 300,300 */
		assertFalse(world.testPlacement(newBox, 225.0f, 225.0f));
    }
	
	/**
	  Check that testPlacement returns not safe for a widget inside another widget
	*/
    @Test public void testInteriorOverlapPlacement()
    {
    	//Setup
		TimWorld world = new TimWorld();
		
		/* create a box and stick it in the world */
		SimpleBox placedBox = new SimpleBox();
		placedBox.setPosition(new Vector2f(200.0f, 200.0f));
		world.addWidget(placedBox);
		
		/* create second box */
		MysteryBox newBox = new MysteryBox();
		
		/* make sure this box would be safe @ 300,300 */
		assertFalse(world.testPlacement(newBox, 205.0f, 205.0f));
    }
		
	/**
	  Check that testPlacement returns not safe for widgets with only edges overlapping
	*/
    @Test public void testEdgeOverlapPlacement()
    {
    	//Setup
		TimWorld world = new TimWorld();
		
		/* create a box and stick it in the world */
		SimpleBox placedBox = new SimpleBox();
		placedBox.setPosition(new Vector2f(200.0f, 200.0f));
		world.addWidget(placedBox);
		
		/* create second box */
		WoodPiece wood = new WoodPiece();
		
		/* make sure this box would be safe @ 300,300 */
		assertFalse(world.testPlacement(wood, 195.0f, 205.0f));
    }
}
