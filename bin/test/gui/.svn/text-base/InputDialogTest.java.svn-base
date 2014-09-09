//
//  InputDialogTest.java
//  
//
//  Created by nridder on 10/21/08.
//  Copyright 2008 Group Four. All rights reserved.
//
package test.gui;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.Assert.*;

import tim.gui.SingleInputDialog;

import javax.swing.JFrame;


public class InputDialogTest extends TestCase {

	/**This test checks for null inpuy within the dialog*/
	@Test public void testRetrieveNullInput()
	{
		//Setup
		JFrame frame = new JFrame();
		SingleInputDialog sid = new SingleInputDialog(frame, "TITLE", "2+2?");
		//Post with Action Tested
		assertEquals("Initialized constructor, trying to pull null data: ", "", sid.getInput());
	}
	
	/**This test checks if the proper input can be pulled out from the input dialog box*/
	@Test public void testRetrieveInput()
	{
		//Setup
		JFrame frame = new JFrame();
		SingleInputDialog sid = new SingleInputDialog(frame, "TITLE", "2+2?");
		//Action Tested
		sid.simulateInput("4");
		//Post
		assertEquals("Retrieving 4 from input: ", "4", sid.getInput());
	}
	
	/**This test checks if the proper Title is being set/displayed int the input dialog box*/
	@Test public void testTitle()
	{
		//Setup
		JFrame frame = new JFrame();
		String title = "TITLE";
		SingleInputDialog sid = new SingleInputDialog(frame, title, "2+2?");
		//Post with ActionTested
		assertEquals("Title is TITLE: ", title, sid.getTitle());
	}
}

