package tim.model;

//
//  Puzzle.java
//  
//
//  Created by Nathaniel Ridder on 11/2/08.
//  Copyright 2008 Group Four. All rights reserved.
//

import Widgets.Widget;

import java.util.ArrayList;

import tim.model.WidgetInfo;
import tim.model.LockedWidgetInfo;
import tim.model.ToolboxWidgetInfo;

import tim.model.WinConditionInfo;
//import tim.model.WinConditionFactory;

public class Puzzle {

	String title, pass, summary;
	int time, score;
	
	ArrayList<LockedWidgetInfo> lockedWidget_set;
	ArrayList<ToolboxWidgetInfo> toolboxWidget_set;
	
	WinConditionInfo wincond;

	/**
	 * Default. Lookitthat...it does nothing.
	 * I mean, a bird, a plane, super bird-plane man!
	 */
	public Puzzle ()
	{
	
	}
	
	/**
	 * The real constructor. All the things needed to make a puzzle/level
	 * (I use them interchangeably) are passed into here at this time.
	 * @param title The title that the puzzle has.
	 * @param password The password associated with this level.
	 * @param summary A short description that gives a bit of aid to
	 * the player for solving this puzzle.
	 * @param bonus_time The amount of time available to the player
	 * for being eligable to bonus points on the level score after completing
	 * a puzzle.
	 * @param level_score The set amount of points to get from completing
	 * this level.
	 * @param lwi Short for LockedWidgetInfos(sss), just a list of all Widgets
	 * that will be already set up as part of the puzzle once loaded.
	 * @param twi Short for ToolboxWidgetInfos(sss), just a list of all Widgets
	 * that will be first available in the toolbox in some quantity once the 
	 * puzzle is loaded.
	 * @param wincond The associated winning condition that the level must
	 * reach before it can be completed. You people paying attention will notice
	 * the puzzle/level trend here. I'll settle on Puzzle for the rest of
	 * the commentary.
	 */
	public Puzzle (String title, String password, String summary, int bonus_time, int level_score, ArrayList<LockedWidgetInfo> lwi, ArrayList<ToolboxWidgetInfo> twi, WinConditionInfo wincond)
	{
		this.title = title;
		pass = password;
		this.summary = summary;
		time = bonus_time;
		score = level_score;
		lockedWidget_set = lwi;
		toolboxWidget_set = twi;
		this.wincond = wincond;
	}

	/**
	 * Retrieves the title of the puzzle.
	 * @return The title.
	 */
	public String getTitle ()
	{
		return title;
	}
	
	/**
	 * Retrieves the password of the puzzle.
	 * @return The password, in string form.
	 */
	public String getPass ()
	{
		return pass;
	}
	
	/**
	 * Retrieves the summary of the puzzle.
	 * @return The brief description of the puzzle.
	 */
	public String getSummary ()
	{
		return summary;
	}
	
	/**
	 * Retrieves the time until bonus is depleted for a puzzle.
	 * @return The bonus time.
	 */
	public int getTime ()
	{
		return time;
	}
	
	/**
	 * Retrieves the set score for completing the puzzle.
	 * @return An amount of points that will always be given
	 * to the player for completing the puzzle.
	 */
	public int getScore ()
	{
		return score;
	}
	
	/**
	 * Retrieves the list of locked widgets for the puzzle.
	 * @return All the widgets needed to make the puzzle.
	 */
	public ArrayList<LockedWidgetInfo>  getLockedWidgetSet ()
	{
		return lockedWidget_set;
	}
	
	/** 
    * Retrieves the list of toolbox widgets for the puzzle.
	 * @return All the widgets needed inside the toolbox of the puzzle.
	 */
	public ArrayList<ToolboxWidgetInfo> getToolboxWidgetSet ()
	{
		return toolboxWidget_set;
	}
	
	/**
	 * Retrieves the winning condition Class that is associated with
	 * this puzzle.
	 * @return Class that settles the debate, has the player won the puzzle?
	 */
	public WinConditionInfo getWinCondition ()
	{
		return wincond;
	}
}