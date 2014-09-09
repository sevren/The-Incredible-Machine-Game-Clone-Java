
//	PlayerProfileTest.java
//
//
//	Created by Group Four on 20/10/08
//	Copyright 2008 Group Four. All rights reserved.
//

/*

This PlayerProfileTest class will test out the functionality 
of the PlayerProfile class
each test is done one after another in a specific order

First test: tests wheter the profile folder is returning true when empty
Second test: tests wheter the create profile function works and returns the name as specified when creating the profile and wheter the update profile function works and returns all the contents being read in
Third test: tests wheter the load profile can read the proper score out of a profile file
Fourth test: tests wheter the getLevels profile works when you have set the levels function



*/
package test.game;

import junit.framework.TestCase;
import junit.framework.JUnit4TestAdapter;
import org.junit.*;

import tim.game.PlayerProfile;

public class PlayerProfileTest extends TestCase
{
	PlayerProfile pf = null;

	/**This test will return true on first run since the test case shall be with no files in the profile folder*/
	@Test public void testCheck()
	{
		//Setup
		pf = new PlayerProfile();
		//Post
		assertEquals(true, (boolean)pf.Check());
	}

	/**This test will return the name of the newly created profile file called "Kiril"*/
	@Test public void testGetName()
	{
		//Setup
		pf = new PlayerProfile();

		//Action Tested
		pf.Create();
		pf.update(10,200);
		pf.Load();

		//Post
		assertEquals("Kiril", pf.getName());
		assertEquals(10, pf.getLevels());
		assertEquals(200, pf.getScore());
	}



	/**This test will get the current score in "Kiril" profile*/
	@Test public void testGetScore()
	{
		//Setup
		pf = new PlayerProfile();
		//Action Tested
		pf.Load();
		
		//Post
		assertEquals(200, pf.getScore());
	}


	/**This test will get the number of levels unlocked*/
	@Test public void testGetLevels()
	{
		//Setup
		pf = new PlayerProfile();
		//Action Tested
		pf.setLevels(5);

		//Post
		assertEquals(5, pf.getLevels());
	}
	
	/**This test will teardown the inital setup*/
	@AfterClass public void cleanupPlayerProfile5()
	{
		//Cleanup
		pf = null;
		System.gc();
	}
/*
	public static void main(String args[])
	{
		org.junit.runner.JUnitCore.main("testing PlayerSetting");
	}
*/
}
