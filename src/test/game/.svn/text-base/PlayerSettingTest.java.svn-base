
//	PlayerSettingTest.java
//
//
//	Created by Group Four on 20/10/08
//	Copyright 2008 Group Four. All rights reserved.
//

/*

This PlayerSettingTest class will test out the functionality 
of the PlayerSetting class

*/
package test.game;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.Assert;

import tim.game.PlayerSetting;

public class PlayerSettingTest extends TestCase
{
	
	/**This test returns the Difficulty setting within the player settings module*/
	@Test public void testGetDifficulty()
	{
		//Setup
		PlayerSetting ps = new PlayerSetting();
		//Action Tested
		ps.setDifficulty(PlayerSetting.Difficulty.HARD);
		//Post
		assertEquals(PlayerSetting.Difficulty.HARD, ps.getDifficulty());
	}
	
	/**This test returns the Music setting within the player settings module*/
	@Test public void testGetMusic()
	{
		//Setup
		PlayerSetting ps = new PlayerSetting();
		//Action Tested
		ps.setMusic(PlayerSetting.Setting.OFF);
		//Post
		assertEquals(PlayerSetting.Setting.OFF, ps.getMusic());
	}
	
	/**This test returns the Sound Setting within the player settings module*/
	@Test public void testGetSound()
	{
		//Setup
		PlayerSetting ps = new PlayerSetting();
		//Action Tested
		ps.setSound(PlayerSetting.Setting.OFF);
		//Post
		assertEquals(PlayerSetting.Setting.OFF, ps.getSound());
	}

	/**This test returns the Gravity setting within the player settings module*/
	@Test public void testGetGravity()
	{
		//Setup
		PlayerSetting ps = new PlayerSetting();
		//Action Tested
		ps.setGravity(65);
		//Post
		assertEquals(65, ps.getGravity());
	}
/*
	public static void main(String args[])
	{
		org.junit.runner.JUnitCore.main("testing PlayerSetting");
	}
*/
}
