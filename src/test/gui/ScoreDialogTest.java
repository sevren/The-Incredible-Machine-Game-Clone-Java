package test.gui;

import junit.framework.TestCase;
import org.junit.*;
import static org.junit.Assert.*;

import javax.swing.JFrame;

import tim.gui.ScoreDialog;


public class ScoreDialogTest
{
	//Inital Setup
	private static final int TIME_LEFT     = 60;
	private static final int LEVEL_SCORE   = 25500;
	private static final int PREV_SCORE    = 20000;
	private static final int USER_SCORE    = 10000;

	private static final int CURRENCY_GAIN_NOTIME = LEVEL_SCORE / 1000;
	private static final int CURRENCY_GAIN_TIME   = (LEVEL_SCORE + (TIME_LEFT * 20)) / 1000;
	
	private static final int TOTAL_SCORE_NOTIME   = LEVEL_SCORE;
	private static final int TOTAL_SCORE_TIME     = LEVEL_SCORE + (TIME_LEFT * 20);
	
	private static final int TOTALUSER_SCORE_NOTIME = TOTAL_SCORE_NOTIME + USER_SCORE;
	private static final int TOTALUSER_SCORE_TIME   = TOTAL_SCORE_TIME + USER_SCORE;

	static JFrame parent = null;
	ScoreDialog dialog = null;
	
	/**
	 * Making the parent Container, so there are no errors in initializing
	 * the dialog.
	 */
	@BeforeClass public static void initParent()
	{
		//Setup
		parent = new JFrame();
	}
	
	/**
	 * Pointless, but eh, killing the parent Container so it will be garbage
	 * collected quicker.
	 */
	@AfterClass public static void nullParent()
	{
		//Teardown
		parent = null;
	}
	
	/**
	 * Nullifying the ScoreDialog so that information has less chance of 
	 * passing onward from test to test.
	 */
	@After public void nullDialog()
	{
		//Teardown
		dialog = null;
	}
	
	/**
	 * Testing the basics. More seeing if it stores things correctly than 
	 * actual calculations.
	 */
	@Test public void basicScoreCalculation()
	{
		//Setup
		dialog = new ScoreDialog(parent, 0, LEVEL_SCORE, 0, USER_SCORE);
		//Post
		assertEquals("No calculations, straight storage error.",LEVEL_SCORE, dialog.getLevelScore());
		assertEquals(TOTALUSER_SCORE_NOTIME, dialog.getNewUserScore());
		assertEquals(CURRENCY_GAIN_NOTIME, dialog.getCurrency());
	}
	
	/**
	 * Adding the bonus time aspect to the dialog, to see if it calculates
	 * the resulting user score and currency correctly.
	 */
	@Test public void TimeScoreCalculation()
	{
		//Setup
		dialog = new ScoreDialog(parent, TIME_LEFT, LEVEL_SCORE, 0, USER_SCORE);
		//Post
		assertEquals(TOTAL_SCORE_TIME, dialog.getLevelScore());
		assertEquals(TOTALUSER_SCORE_TIME, dialog.getNewUserScore());
		assertEquals(CURRENCY_GAIN_TIME, dialog.getCurrency());
	}
	
	/**
	 * Adding the previous score of the level that a possible user has 
	 * managed to get. Prolly will not be used in the final build due to
	 * the profile not supporting previous scores of the various levels.
	 * This addition changes only the user score at the end.
	 */
	@Test public void PrevScoreCalculation()
	{
		//Setup
		dialog = new ScoreDialog(parent, TIME_LEFT, LEVEL_SCORE, PREV_SCORE, USER_SCORE);
		dialog.startDialog();
		//Pre
		assertEquals(TOTAL_SCORE_TIME, dialog.getLevelScore());
		//Action Tested
		if (TOTAL_SCORE_TIME < PREV_SCORE)
			//Post
			assertEquals(USER_SCORE, dialog.getNewUserScore());
		else
			//Post
			assertEquals(USER_SCORE + " + " + TOTAL_SCORE_TIME + " - " + PREV_SCORE, USER_SCORE + (TOTAL_SCORE_TIME - PREV_SCORE), dialog.getNewUserScore());
		assertEquals(CURRENCY_GAIN_TIME, dialog.getCurrency());
	}
}
