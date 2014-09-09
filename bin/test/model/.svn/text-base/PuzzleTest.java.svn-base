package test.model;

import junit.framework.TestCase;
import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import tim.model.LockedWidgetInfo;
import tim.model.ToolboxWidgetInfo;
import tim.model.WinConditionInfo;
import tim.model.Puzzle;

import Widgets.Widget;

public class PuzzleTest
{
	//Inital Setup
	private static final String TITLE      = "Grimm Testing Level - 01";
	private static final String PASS       = "Pr0-F4n\'ny";
	private static final String SUMMARY    = "I do the testing around here, boy.";
	private static final int TIME          = 90;
	private static final int SCORE         = 25500;

	private static final String LWI_TYPE           = "Gadget";
	private static final Widget.Direction LWI_WDIR = Widget.Direction.EAST;
	private static final float LWI_X               = 67f;
	private static final float LWI_Y               = 22f;
	
	private static final String TWI_TYPE           = "Crane";
	private static final int TWI_NUMBER            = 2;
	private static final String TWI_TYPE2          = "Grasper";
	private static final int TWI_NUMBER2           = 15;
	
	private static final String WCI_TYPE           = "SingleCollision";
	static ArrayList<Object> args = new ArrayList<Object>();
	static WinConditionInfo wci    = new WinConditionInfo (WCI_TYPE, args);
	
	Puzzle puzzle = null;
	static ArrayList<LockedWidgetInfo> lwi_list = null;
	static ArrayList<ToolboxWidgetInfo> twi_list = null;
	
	/**Setup*/
	@BeforeClass public static void createWidgetLists()
	{
		lwi_list = new ArrayList<LockedWidgetInfo>();
		lwi_list.add(new LockedWidgetInfo(LWI_TYPE, LWI_WDIR, LWI_X, LWI_Y));
		
		twi_list = new ArrayList<ToolboxWidgetInfo>();
		twi_list.add(new ToolboxWidgetInfo(TWI_TYPE, TWI_NUMBER));
		twi_list.add(new ToolboxWidgetInfo(TWI_TYPE2, TWI_NUMBER2));
		
		args.add(0);
	}
	/**Setup*/
	@Before public void createPuzzle()
	{
		puzzle = new Puzzle (TITLE, PASS, SUMMARY, TIME, SCORE, lwi_list, twi_list, wci);
	}
	/**Teardown*/
	@After public void nullPuzzle()
	{
		puzzle = null;
	}
	/**This tests to see if the puzzle title is equal to the specified title*/
	@Test public void getTitle()
	{
		assertEquals(TITLE, puzzle.getTitle());
	}
	/**This tests to see if the puzzle password is equal to the specified password*/
	@Test public void getPass()
	{
		assertEquals(PASS, puzzle.getPass());
	}
	/**This tests to see if the puzzle summary is equal to the specified summary*/
	@Test public void getSummary()
	{
		assertEquals(SUMMARY, puzzle.getSummary());
	}
	/**This tests to see if the puzzle time is equal to the specified time*/
	@Test public void getTime()
	{
		assertEquals(TIME, puzzle.getTime());
	}
	/**This tests to see if the puzzle score is equal to the specified score*/
	@Test public void getScore()
	{
		assertEquals(SCORE, puzzle.getScore());
	}

	/**This tests to see if LockedWidgetInfo has the same objects as the puzzles LockedWidgetInfo*/	
	@Test public void getLocked()
	{
		ArrayList<LockedWidgetInfo> check_against = puzzle.getLockedWidgetSet();
		assertEquals(check_against.size(), lwi_list.size());
		for (int i = 0; i < check_against.size() || i < lwi_list.size(); i++)
		{
			assertEquals(check_against.get(i), lwi_list.get(i));
		}
	}
	/**This tests to see if the ToolBoxWidgetInfo has the same objects as the puzzles toolBoxWidgetSet*/
	@Test public void getToolbox()
	{
		ArrayList<ToolboxWidgetInfo> check_against = puzzle.getToolboxWidgetSet();
		assertEquals(check_against.size(), twi_list.size());
		for (int i = 0; i < check_against.size() || i < twi_list.size(); i++)
		{
			assertEquals(check_against.get(i), twi_list.get(i));
		}
	}
	/**This tests to see if the puzzles WinCondition is equal to the specified WinCondition*/
	@Test public void getWinCondition()
	{
		WinConditionInfo check = puzzle.getWinCondition();
		assertEquals(WCI_TYPE, check.getType());
		assertEquals(args.size(), check.getArguments().size());
		
	}
}
