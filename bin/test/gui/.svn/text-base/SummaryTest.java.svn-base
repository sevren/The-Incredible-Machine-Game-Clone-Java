package test.gui;

import junit.framework.TestCase;
import org.junit.*;
import static org.junit.Assert.*;

import tim.model.Puzzle;
import tim.gui.LevelSummaryDialog;
import tim.model.LevelParser;

import javax.swing.JFrame;

public class SummaryTest {

	//Inital Setup
	private static final String XML_FILEPATH = "./resources/DefaultLevelSet.xml";
	private static final int PUZZLE_NUM = 2;

	private static Puzzle puzzle = null;
	
	/**Setup and load the level parser with a puzzle from the DefaultLevelSet.xml*/
	@BeforeClass public static void getPuzzle()
	{
		//Setup
		LevelParser parser = new LevelParser(XML_FILEPATH);
		puzzle = parser.getPuzzle(PUZZLE_NUM);
	}
	
	/**Display the correct level summary dialog with the correct values from the  level parser*/
	@Test public void displayDialogTest()
	{
		//Setup
		JFrame frame = new JFrame();
		//Action Tested
		LevelSummaryDialog dialog = new LevelSummaryDialog(frame, puzzle.getTitle(), puzzle.getPass(), puzzle.getSummary());
		//Post
		dialog.startDialog();
	}
}
