//
//  MainMenuTest.java
//  
//
//  Created by nridder on 10/21/08.
//  Copyright 2008 Group Four. All rights reserved.
//
package test.gui;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.Assert.*;

import tim.gui.TimMenuScreen;

import javax.swing.JFrame;


public class MainMenuTest extends TestCase {

	/**This test checks to see if the proper Action is returned when a specfic button is pressed on the main menu*/
	@Test public void testButtonPressReturnValue()
	{
		//Setup
		JFrame frame = new JFrame();
		TimMenuScreen tms = new TimMenuScreen(frame);
		//Action Tested
		tms.simulateButtonPress("New Game");
		//Post
		assertEquals("Simulated Button Press - New Game: ", tms.getAction(), "new");
	}
}
