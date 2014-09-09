package test.model;

import junit.framework.TestCase;
import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import tim.model.LevelParser;
import tim.model.Puzzle;
import tim.model.LockedWidgetInfo;
import tim.model.ToolboxWidgetInfo;
import tim.model.WidgetInfo;

public class LevelParserTest
{
	//Inital Setup
	LevelParser parser = null;
	
	public static final String XML_ALTPATH   = "./resources/levelstorage.xml";
	public static final String XML_SAVEPATH  = "./resources/test.xml";
	public static final String XML_SAVEPATH2 = "./resources/test2.xml";
	
	private static final String PUZ_TITLE    = "Gorilla vs. Bomb - 02";
	private static final String PUZ_PASS     = "twolevel";
	private static final String PUZ_SUMMARY  = "This poor gorilla just doesn't know what's good for him.";
	private static final int PUZ_TIME        = 100;
	private static final int PUZ_SCORE       = 75000;
 
	/**Setup creates the instance*/
	@Before public void createParser()
	{
		parser = new LevelParser();
	}
	/**Teardown*/
	@After public void nullParser()
	{
		parser = null;
	}
	/**This tests the Constructor*/
	@Test public void constructor()
	{
		assertNotNull(parser.getDocument());
	}
	
	/**This tests the paramaters in the constructor*/
	@Test public void paramConstructor()
	{
		LevelParser alt_parser = new LevelParser(XML_ALTPATH);
		assertNotNull(alt_parser.getDocument());
	}
	
	/**This tests the second constructor*/
	@Test public void paramConstructor2()
	{
		LevelParser alt_parser = new LevelParser(XML_ALTPATH);
		assertEquals(alt_parser.getFilename(), XML_ALTPATH);
	}
	
	/**This tests that the puzzle is fully parsed*/
	@Test public void getPuzzle()
	{
		//Setup
		Puzzle puzzle = parser.getPuzzle(2);
		ArrayList<LockedWidgetInfo> lwi = null;
		ArrayList<ToolboxWidgetInfo> twi = null;
		
		//Pre
		assertNotNull(puzzle);
		assertEquals(PUZ_TITLE, puzzle.getTitle());
		assertEquals(PUZ_PASS, puzzle.getPass());
		assertEquals(PUZ_SUMMARY, puzzle.getSummary());
		assertEquals(PUZ_TIME, puzzle.getTime());
		assertEquals(PUZ_SCORE, puzzle.getScore());
		
		lwi = puzzle.getLockedWidgetSet();
		twi = puzzle.getToolboxWidgetSet();
		//Post
		assertTrue(lwi.size() > 0);
		assertTrue(twi.size() > 0);
		
		for (LockedWidgetInfo widget : lwi)
		{
			System.out.println("l_widget = " + widget.getWidgetClassName());
		}
		
		for (ToolboxWidgetInfo widget : twi)
		{
			System.out.println("t_widget = " + widget.getWidgetClassName());
		}
	}
	/**This tests if the file was successfully written to disk*/
	@Test public void writeToFile()
	{
		assertTrue(parser.writeToFile(XML_SAVEPATH));
	}
	/**This tests if the file is written*/
	@Test public void write()
	{
		assertTrue(parser.writeToFile());
	}

	@Test public void staticWrite()
	{
		assertTrue(LevelParser.writeToFile(XML_SAVEPATH2, parser.getDocument()));
	}

	/**This tests if the level can be deleted by puzzle reference*/
	@Test public void deleteLevelByPuzzle()
	{
		Puzzle puzzle = parser.getPuzzle(2);
		assertTrue(parser.deleteLevel(puzzle));
	}
	/**This tests if the level can be deleted by level number reference */
	@Test public void deleteLevelByLevelnum()
	{
		assertTrue(parser.deleteLevel(1));
	}
	/**This tests if the puzzle can be appended to the list of levels*/
	@Test public void appendLevel()
	{
		Puzzle puzzle = parser.getPuzzle(1);
		assertTrue(parser.appendLevel(puzzle));
	}
	/**This tests if the puzzle can be inserted into the list of levels*/
	@Test public void insertLevel()
	{
		Puzzle puzzle = parser.getPuzzle(2);
		assertTrue(parser.insertLevel(puzzle, 1));
	}
	/**This tests if the puzzle can be replaced by a specific level number*/
	@Test public void replaceLevelByLevelnum()
	{
		Puzzle puzzle = parser.getPuzzle(2);
		assertTrue(parser.replaceLevel(puzzle, 1));
	}
	/**This tests if the puzzle can be replaced with a new puzzle*/
	@Test public void replaceLevelByPuzzle()
	{
		Puzzle puzzle = parser.getPuzzle(2);
		Puzzle old_puzzle = parser.getPuzzle(1);
		assertTrue(parser.replaceLevel(puzzle, old_puzzle));
	}
	/**This tests if the titles parsed from the puzzle is not null*/
	@Test public void getTitlesList()
	{
		assertNotNull(parser.getTitlesList());
	}
	
	/**This tests if the password from the parser is not null*/
	@Test public void getPassword ()
	{
		assertNotNull(parser.getPassword(1));
	}
}
