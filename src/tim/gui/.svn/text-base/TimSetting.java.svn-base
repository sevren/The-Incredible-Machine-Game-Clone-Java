
//	TimSetting.java
//
//
//	Created by Group Four on 15/10/08
//	Copyright 2008 Group Four. All rights reserved.
//

/*

This GUI interface is for the user to select their own settings for the game.

*/
package tim.gui;

import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import tim.game.PlayerSetting;

/**
 * This class is to make a Dialog menu so that the user can conveniently choose
 * what kind of settings they want for their game.  if the game is easy/normal/hard
 * if the music is on/off, if the sound is on/off, and also the gravity settings for each
 * level.
 */
public class TimSetting extends JDialog implements ActionListener, AdjustmentListener
{

	private JRadioButton monbutton, moffbutton;
	private JRadioButton sonbutton, soffbutton;
	private JRadioButton easybutton, normalbutton, hardbutton;
	private JScrollBar gravbar;
	private JLabel gravlbl;
	private PlayerSetting playerSet;

	// Will probably need to read in the current user that is logged on
	// so that the settings will be saved under the current user.
	
	/**
	 * simple constructor, doesn't read in a user yet, must read in a PlayerSetting in order to
	 * remember their states, so that they can save to their profile.
	 */
	public TimSetting()
	{
		super ((JFrame)null, "Settings", true);
		playerSet = new PlayerSetting();

		monbutton = new JRadioButton("On ");
		moffbutton = new JRadioButton("Off ");
		
		sonbutton = new JRadioButton("On");
		soffbutton = new JRadioButton("Off");
		
		easybutton = new JRadioButton("Easy");
		normalbutton = new JRadioButton("Normal");
		hardbutton = new JRadioButton("Hard");
		
		monbutton.setSelected(true);
		sonbutton.setSelected(true);
		normalbutton.setSelected(true);		
		
		gravbar = new JScrollBar(Adjustable.HORIZONTAL, 10, 5, 50, 105);
		initializeOptions();		
	}
	
	/**
	 * This Constructor will allow the player settings to be saved, it must be passed
	 * a PlayerSetting object so that it sets the PlayerSettings accordingly and save
	 * the profile accordingly.
	 *
	 * @param ps PlayerSetting that needs to be changed accordingly
	 */
	public TimSetting(PlayerSetting ps)
	{
		super ((JFrame)null, "Settings", true);

		playerSet = ps;
		monbutton = new JRadioButton("On ");
		moffbutton = new JRadioButton("Off ");
		
		sonbutton = new JRadioButton("On");
		soffbutton = new JRadioButton("Off");
		
		easybutton = new JRadioButton("Easy");
		normalbutton = new JRadioButton("Normal");
		hardbutton = new JRadioButton("Hard");
		
		if (ps.getDifficulty() == PlayerSetting.Difficulty.EASY)
		{
			easybutton.setSelected(true);
		}
		else if (ps.getDifficulty() == PlayerSetting.Difficulty.NORMAL)
		{
			normalbutton.setSelected(true);
		}
		else
		{
			hardbutton.setSelected(true);
		}
		
		if (ps.getMusic() == PlayerSetting.Setting.ON)
		{
			monbutton.setSelected(true);
		}
		else
		{
			moffbutton.setSelected(true);
		}
		
		if (ps.getSound() == PlayerSetting.Setting.ON)
		{
			sonbutton.setSelected(true);
		}
		else
		{
			soffbutton.setSelected(true);
		}
		
		gravbar = new JScrollBar(Adjustable.HORIZONTAL, ps.getGravity(), 5, 50, 105);
		
		initializeOptions();
	}
	
	/*
	* initializes all the options inside the TimSetting.
	* will eventually need to read in a user in order to set the user settings
	* or if its a new user then it will set a default settings.
	*/
	private void initializeOptions()
	{
		this.setSize(new Dimension(400, 400));
		this.setLayout(new GridLayout(4, 1));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JLabel difflbl = new JLabel("Difficulty : ");
		JLabel muslbl = new JLabel("Music : ");
		JLabel soundlbl = new JLabel("Sound : ");
		gravlbl = new JLabel("Gravity : " + gravbar.getValue());

		gravbar.addAdjustmentListener(this);

		ButtonGroup musgroup = new ButtonGroup();
		musgroup.add(monbutton);
		musgroup.add(moffbutton);

		ButtonGroup soundgroup = new ButtonGroup();
		soundgroup.add(sonbutton);
		soundgroup.add(soffbutton);
		
		ButtonGroup diffgroup = new ButtonGroup();
		diffgroup.add(easybutton);
		diffgroup.add(normalbutton);
		diffgroup.add(hardbutton);

		JPanel diffpanel = new JPanel(new GridLayout(4, 1));
		diffpanel.add(difflbl);
		diffpanel.add(easybutton);
		diffpanel.add(normalbutton);
		diffpanel.add(hardbutton);
	
		JPanel muspanel = new JPanel(new GridLayout(3, 1));
		muspanel.add(muslbl);
		muspanel.add(monbutton);
		muspanel.add(moffbutton);
		
		JPanel soundpanel = new JPanel(new GridLayout(3, 1));
		soundpanel.add(soundlbl);
		soundpanel.add(sonbutton);
		soundpanel.add(soffbutton);
		
		JPanel gravpanel = new JPanel(new GridLayout(2, 1));
		gravpanel.add(gravlbl);
		gravpanel.add(gravbar);

		JPanel butpanel = new JPanel(new FlowLayout());
		
		JButton savebutton = new JButton("Save");
		savebutton.setSize(10, 20);
		savebutton.addActionListener(this);
		
		JButton cancelbutton = new JButton("Cancel");
		cancelbutton.setSize(10, 20);
		cancelbutton.addActionListener(this);

		butpanel.add(savebutton);
		butpanel.add(cancelbutton);

		JPanel settingpanel = new JPanel(new GridLayout(1, 2));

		settingpanel.add(muspanel);
		settingpanel.add(soundpanel);
		
		this.add(diffpanel);
		this.add(settingpanel);
		this.add(gravpanel);
		this.add(butpanel);
		
	}
	
	/**
	 * When user clicks save then it will write to a txt file (or xml) depending on how the user profile is set up
	 * to save all the settings for that particular user, however if the user clicks on cancel then nothing changes.
	 * @param e the actionevent that happen to trigger this method
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals("Save")) // if its save then save all the current settings
		{
			if (easybutton.isSelected())
			{
				playerSet.setDifficulty(PlayerSetting.Difficulty.EASY);
			}
			else if (normalbutton.isSelected())
			{
				playerSet.setDifficulty(PlayerSetting.Difficulty.NORMAL);
			}
			else
			{
				playerSet.setDifficulty(PlayerSetting.Difficulty.HARD);
			}
			if (monbutton.isSelected())
			{
				playerSet.setMusic(PlayerSetting.Setting.ON);
			}
			else
			{
				playerSet.setMusic(PlayerSetting.Setting.OFF);
			}
			if (sonbutton.isSelected())
			{
				playerSet.setSound(PlayerSetting.Setting.ON);
			}
			else
			{
				playerSet.setSound(PlayerSetting.Setting.OFF);
			}
			playerSet.setGravity(gravbar.getValue());
		} // end of if its save, if its not save then just dispose and don't change the playerSet
		this.dispose();
	}

	/**
	 * This is for adjusting the gravity bar, when it changes the label changes it's value
	 * accordingly.
	 */
	public void adjustmentValueChanged(AdjustmentEvent e)
	{
		if (e != null)
		{
			gravlbl.setText("Gravity : " + gravbar.getValue());
		}
	}
}
