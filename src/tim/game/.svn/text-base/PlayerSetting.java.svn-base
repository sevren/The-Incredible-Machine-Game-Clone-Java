
//	PlayerSetting.java
//
//
//	Created by Group Four on 19/10/08
//	Copyright 2008 Group Four. All rights reserved.
//

/*

This PlayerSetting class will specify the individual players settings.
it will be used with UserProfile and TimSetting to determine what to save
into the player's settings.

*/
package tim.game;

/**
 * This Class is mainly to store the settings of the player it will have
 * difficulty, music, sound, and gravity variables for each of the player's
 * settings. Mainly for setting and getting these variables that are needed for
 * the Player Settings.
 *
 */
public class PlayerSetting
{
	// an enum to specify how hard the difficulty of the game will be
	public enum Difficulty { EASY, NORMAL, HARD };
	// an enum saying if it's on or off
	public enum Setting { ON, OFF };

	Difficulty diff;
	Setting music;
	Setting sound;
	int gravity;

	/**
	 * Default constructor, the difficulty will be set to normal, music set to ON
	 * Sound set to ON, and the gravity will be at 100.
	 */
	public PlayerSetting()
	{
		setDifficulty(Difficulty.NORMAL);
		setMusic(Setting.ON);
		setSound(Setting.ON);
		setGravity(100);
	}

	/**
	 * Given the parameters this PlayerSetting to be made
	 * this constructor will accept the parameters and set the settings
	 * to the corresponding parameter
	 *
	 * @param diff The difficulty of the game
	 * @param music the setting of the music if it's on or off
	 * @param sound the setting of the sound if it's on or off
	 * @param gravity the TIM world's gravity 0 minimum 50 max
	 */
	public PlayerSetting(Difficulty diff, Setting music, Setting sound, int gravity)
	{
		setDifficulty(diff);
		setMusic(music);
		setSound(sound);
		setGravity(gravity);
	}

	/**
	 * Given the parameters this PlayerSetting to be made (in Strings)
	 * this constructor will accept the parameters and set the settings
	 * to the corresponding parameter
	 * Difficulty = "easy", "normal", "hard"  -- if the strings aren't of these form it'll just set to default (normal)
	 * Setting = "on", "off" -- if the strings aren't of these form it'll just set to default (on)
	 * gravity = an int (if it's not between 50 - 100) it'll be set to default (100)
	 *
	 * @param diff The difficulty of the game
	 * @param music the setting of the music if it's on or off
	 * @param sound the setting of the sound if it's on or off
	 * @param gravity the TIM world's gravity 50 minimum 100 max
	 */
	public PlayerSetting(String diff, String music, String sound, int gravity)
	{
		if (diff.toLowerCase().equals("easy"))
		{
			setDifficulty(Difficulty.EASY);
		}
		else if (diff.toLowerCase().equals("normal"))
		{
			setDifficulty(Difficulty.NORMAL);
		}
		else
		{
			setDifficulty(Difficulty.HARD);
		}
		if (music.toLowerCase().equals("on"))
		{
			setMusic(Setting.ON);
		}
		else
		{
			setMusic(Setting.OFF);
		}
		if (sound.toLowerCase().equals("on"))
		{
			setSound(Setting.ON);
		}
		else
		{
			setSound(Setting.OFF);
		}
		setGravity(gravity);
	}
	
	/**
	 * Constructor, creates an instance using the parameters for the purpose
	 * of storing the settings of the user.
	 * @param diff The difficulty chosen by the user.
	 * Will be either 'easy', 'normal' or 'hard'. Any other string will
	 * be parsed as 'normal'.
	 * @param music Whether the music is be played (true) or not (false).
	 * @param sound Whether sound effects are to be played (true) or not (false).
	 * @param gravity Sets the gravity of the game from 0 to 50. 10 is default.
	 */
	public PlayerSetting(String diff, boolean music, boolean sound, int gravity)
	{
		if (diff.toLowerCase().equals("easy"))
			setDifficulty(Difficulty.EASY);
		else if (diff.toLowerCase().equals("hard"))
			setDifficulty(Difficulty.HARD);
		else
			setDifficulty(Difficulty.NORMAL);
			
		if (music)
			setMusic(Setting.ON);
		else
			setMusic(Setting.OFF);
			
		if (sound)
			setSound(Setting.ON);
		else
			setSound(Setting.OFF);
			
		setGravity(gravity);
	}


	/**
	 * Set's the difficulty of the TIM world manually
	 *
	 * @param diff the difficulty of the TIM world.
	 */
	public void setDifficulty(Difficulty diff)
	{
		this.diff = diff;
	}

	/**
	 * Set's the music of the TIM world manually
	 *
	 * @param music the music setting of the TIM world.
	 */
	public void setMusic(Setting music)
	{
		this.music = music;
	}

	/**
	 * Set's the sound of the TIM world manually
	 *
	 * @param sound the sound setting of the TIM world.
	 */
	public void setSound(Setting sound)
	{
		this.sound = sound;
	}

	/**
	 * Set's the gravity of the TIM world manually
	 * If the gravity is below 50 or greater than 100 then it sets the gravity
	 * to it's default setting which is 100
	 * 
	 * @param gravity the gravity of the TIM world
	 */
	public void setGravity(int gravity)
	{
		if (gravity >= 100 || gravity <= 50)
		{		
			this.gravity = 100;
		}
		else
		{
			this.gravity = gravity;
		}
	}

	/**
	 * get's the difficulty of the TIM world manually
	 *
	 * @return the difficulty of the TIM world
	 */
	public Difficulty getDifficulty()
	{
		return diff;
	}

	/**
	 * get's the difficulty of the TIM world manually
	 *
	 * @return the music setting of the TIM world
	 */
	public Setting getMusic()
	{
		return music;
	}

	/**
	 * get's the difficulty of the TIM world manually
	 *
	 * @return the sound setting of the TIM world
	 */
	public Setting getSound()
	{
		return sound;
	}

	/**
	 * get's the gravity of the TIM world
	 *
	 * @return the gravity of the TIM world
	 */
	public int getGravity()
	{
		return gravity;
	}

	/**
	 * This returns the PlayerSetting ps String wise (which will go down the list of
	 * difficulty -> music -> sound -> gravity and output them with a new line
	 * this is generally for outputting to the file where it saves the profile data.
	 *
	 * @return the string that could be outputted to file
	 */
	public String toString()
	{
		String result;
		if (diff == PlayerSetting.Difficulty.HARD)
		{
			result = "hard\n";
		}
		else if (diff == PlayerSetting.Difficulty.NORMAL)
		{
			result = "normal\n";
		}
		else
		{
			result = "easy\n";
		}
		if (music == PlayerSetting.Setting.ON)
		{
			result += "on\n";
		}
		else
		{
			result += "off\n";
		}
		if (sound == PlayerSetting.Setting.ON)
		{
			result += "on\n";
		}
		else
		{
			result += "off\n";
		}
		result += gravity + "\n";

		return result;
	}
	
}
