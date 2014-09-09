package tim.model;

//
//  LevelParser.java
//  
//
//  Created by Nathaniel Ridder on 11/2/08.
//  Copyright 2008 Group Four. All rights reserved.
//

//import org.jdom.*;
import org.w3c.dom.*;
import org.w3c.dom.Node.*;
import org.xml.sax.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.*;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import tim.model.Puzzle;
import tim.model.WidgetInfo;
import tim.model.LockedWidgetInfo;
import tim.model.ToolboxWidgetInfo;
import tim.model.WinConditionInfo;

import Widgets.Widget;

/**
 * A collection of static methods for the manipulation of XML Documents
 * designed for the GroupFour project. More specifically, for pulling out
 * or sticking in levels stored on the hard disk.
 * @author Nathaniel Ridder
 */
public class LevelParser {

	private static final int LEVELNODES              = 8;
	private static final int LOCKEDWIDGETNODES       = 4;
	private static final int TOOLBOXWIDGETNODES      = 2;
	private static final int NUMPUZZLES_PER_DOCUMENT = 10;

	private static final String LEVEL_TAG          = "Level";
	
	private static final String TITLE              = "Title";
	private static final String PASS               = "Password";
	private static final String SUMMARY            = "Summary";
	private static final String TIME               = "BonusTime";
	private static final String SCORE              = "CompletionScore";
	private static final String LOCKED_WIDGET_SET  = "LockedWidgets";
	private static final String TOOLBOX_WIDGET_SET = "PlaceableWidgets";
	private static final String WINCOND            = "WinningConditions";
	
	private static final String WIDGET_TYPE        = "Type";
	private static final String WIDGET_POSX        = "PosX";
	private static final String WIDGET_POSY        = "PosY";
	private static final String WIDGET_DIRECTION   = "dir";
	private static final String WIDGET_NUMBER      = "Number";
	
	private static final String WINCOND_ATTR       = "type";
	private static final String WINCOND_ARG        = "argument";
	
	private static final String DEFAULT_FILE       = "resources/DefaultLevelSet.xml";
	
	private Document puzzle_set;
	private String original_filename;
	
	public LevelParser()
	{
		//create a fresh Document, with root node being <DefaultLevelSet>
		original_filename = DEFAULT_FILE;
		puzzle_set = getLevelXMLFromFile(original_filename);
	}
	
	public LevelParser (String filename)
	{
		puzzle_set = getLevelXMLFromFile(filename);
		if (puzzle_set == null)
		{
			//create fresh Document, with root node being <DefaultLevelSet>
			original_filename = DEFAULT_FILE;
			puzzle_set = getLevelXMLFromFile(original_filename);
		}
		else
			original_filename = filename;
	}
	/**
	 * Retrieves the XML Document from the hard disk, based on the parameter.
	 * @param filename Where the XML file is stored.
	 * @return An in-memory representation of the XML file, for easy traversal.
	 * Most common use will be within this suite of methods.
	 */
	public static Document getLevelXMLFromFile(String filename)
	{
		//SAXBuilder builder = new SAXBuilder();
		DocumentBuilderFactory dom_factory = DocumentBuilderFactory.newInstance();
		dom_factory.setValidating(true);
		dom_factory.setIgnoringComments(true);
		
		try
		{
			DocumentBuilder builder = dom_factory.newDocumentBuilder();
			builder.setErrorHandler(null);
			InputStream is = LevelParser.class.getClassLoader().getResourceAsStream("resources/DefaultLevelSet.xml");//ClassLoader.getSystemClassLoader().getResourceAsStream("resources/DefaultLevelSet.xml");
			
			Document loaded_levelset = builder.parse(is);
			return loaded_levelset;
		}
		catch (ParserConfigurationException e)
		{
			System.out.println("Problem with Parser: " + e.getMessage());
		}
		catch (SAXException e)
		{
			System.out.println(filename + " not a well-formed XML document. Here's why...");
			System.out.println(e.getMessage());
		}
		catch (IOException e)
		{
			System.out.println(filename + " has IO problems due to:");
			System.out.println(e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * Attempts to read a Document and pull out the correct level from it.
	 *
	 * Document must be in this format, otherwise it will not be able to read it:
	 * <DocRoot <can be anything, might be useful to be the filename> >
	 * 	<Title>
	 * 	<Password>
	 * 	<Summary>
	 * 	<BonusTime>
	 * 	<CompletionScore>
	 * 	<LockedWidgets>
	 *			<Widget>
					<Type>
					<PosX>
					<PosY>
					<dir>
	 * 	<PlaceableWidgets>
	 *			<Widget>
	 *				<Type>
	 *				<Number>
	 * 	<WinningConditions type="collision/dual_collision/intersect/time_intersect">
	 *			< <varies based on type> >
	 * @param level_num The level number to pull out. Since level 0 is free-play,
	 * please use any whole number beyond that. For XML representations that are split
	 * across multiple files, this parameter will be modulus by the number that they are
	 * split into. It is the user's responsibility to use the correct Document that represents
	 * the correct level spread, otherwise you will get the wrong Puzzle from this method.
	 * @return The class holding information for the instantiation of the puzzle. Null if any error occurs.
	 */
	public Puzzle getPuzzle(int level_num)
	{
		if (puzzle_set == null)
			return null;
		if (level_num <= 0)
			return null;
			
		ArrayList<Element> children_nodes = new ArrayList<Element>();
		Element root, level_node, pointer;
		int modified_levelnum;
		
		root = puzzle_set.getDocumentElement();
		children_nodes = collectElementNodes(root.getChildNodes());
		
		//Need to translate the level number to use computer counting, starting at 0. 
		//Since our levels will be numbered 1 through to infinity, we have to shift back by one.
		modified_levelnum = (level_num - 1) % NUMPUZZLES_PER_DOCUMENT;
		/* NOTE: level_num as a parameter will probably be the raw level number.
		 * Since I plan on having multiple XML levelsets, to keep memory required
		 * for the Document to a decent size, there will have to be a transformation
		 * on level_num. For example, if all XML documents have only 10 levels for each
		 * then get() would have the parameter level_num%10 .
		 */
		try
		{
			level_node = (Element)children_nodes.get(modified_levelnum);
		}
		catch (Exception e)
		{
			System.out.println("Problem: " + e.getMessage());
			return null;
		}
		
		String title, pass, summary;
		int time, score;
		ArrayList<LockedWidgetInfo> locked_widgets;
		ArrayList<ToolboxWidgetInfo> toolbox_widgets;
		String wincond_type;
		WinConditionInfo new_wincond;
		
		children_nodes = collectElementNodes(level_node.getChildNodes());
		if (children_nodes.size() != LEVELNODES)
			return null;
			
		Iterator level_walker = ((List)children_nodes).iterator();
		
		//Require the title element
		pointer = (Element)level_walker.next();
		if (pointer.getTagName().compareToIgnoreCase(TITLE) != 0)
			return null;
		title = pointer.getTextContent();
		
		//Require the password element
		pointer = (Element)level_walker.next();
		if (pointer.getTagName().compareToIgnoreCase(PASS) != 0)
			return null;	
		pass = pointer.getTextContent();
		
		//Require the summary element
		pointer = (Element)level_walker.next();
		if (pointer.getTagName().compareToIgnoreCase(SUMMARY) != 0)
			return null;
		summary = pointer.getTextContent();
		
		//Require the bonustime element
		pointer = (Element)level_walker.next();
		try
		{
			if (pointer.getTagName().compareToIgnoreCase(TIME) != 0)
				throw new Exception ("Element does not exist in XML document.");
			time = Integer.parseInt(pointer.getTextContent());
			
			if (time < 0)
				throw new Exception ("Holds a negative value. Please use 0 or higher.");
		}
		catch (Exception e)
		{
			System.out.println("Problem with element <" + TIME + ">:");
			System.out.println(e.getMessage());
			return null;
		}
		
		//Require the completionScore element
		pointer = (Element)level_walker.next();
		try
		{
			if (pointer.getTagName().compareToIgnoreCase(SCORE) != 0)
				throw new Exception("Element does not exist in XML document.");
			score = Integer.parseInt(pointer.getTextContent());
			
			if (score < 0)
				throw new Exception("Holds a negative value. Please use 0 or higher.");
		}
		catch (Exception e)
		{
			System.out.println("Problem with element <" + SCORE + ">:");
			System.out.println(e.getMessage());
			return null;
		}
		
		//Require the lockedWidget Element
		pointer = (Element)level_walker.next();
		if (pointer.getTagName().compareToIgnoreCase(LOCKED_WIDGET_SET) != 0)
			return null;
		locked_widgets = parseLockedWidgets(pointer);

		//Require the toolboxWidget Element
		pointer = (Element)level_walker.next();
		if (pointer.getTagName().compareToIgnoreCase(TOOLBOX_WIDGET_SET) != 0)
			return null;
		toolbox_widgets = parseToolboxWidgets(pointer);
		
		//Require the WinCondition Element
		pointer = (Element)level_walker.next();
		if (pointer.getTagName().compareToIgnoreCase(WINCOND) != 0)
			return null;	
			
		wincond_type = pointer.getAttribute(WINCOND_ATTR);
		if (wincond_type == null)
			return null;

		//Create method, taking in the type attribute value.
		new_wincond = parseWinCondition(pointer, wincond_type);

		//Method parses and then configures the correct class to be returned
		
		//All nodes parsed, now to add them together in creating the Puzzle class.
		return new Puzzle(title, pass, summary, time, score, locked_widgets, toolbox_widgets, new_wincond);
	}
	
	private ArrayList<Element> collectElementNodes(NodeList nlist)
	{
		ArrayList<Element> elements = new ArrayList<Element>();
		for (int i = 0; i < nlist.getLength(); i++)
		{
			Node n = nlist.item(i);
			if (n.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE)
				elements.add((Element)n);
		}
		
		return elements;
	}	
	
	private ArrayList<LockedWidgetInfo> parseLockedWidgets(Element parent)
	{
		ArrayList<LockedWidgetInfo> locked_widgets = new ArrayList<LockedWidgetInfo>();
		ArrayList<Element> widget_nodes = collectElementNodes(parent.getChildNodes());
		Iterator widget_walker = widget_nodes.iterator();
		Element node;
		
		while (widget_walker.hasNext())
		{
			node = (Element)widget_walker.next();
			try
			{
				locked_widgets.add((LockedWidgetInfo)createWidgetFromNode(node.getChildNodes(), true));
			}
			catch (Exception e)
			{
				System.out.println("Problem with widget creation:");
				System.out.println(e.getMessage());
				return null;
			}
		}
		
		return locked_widgets;
	}	

	private ArrayList <ToolboxWidgetInfo> parseToolboxWidgets(Element parent)
	{
		ArrayList<ToolboxWidgetInfo> toolbox_widgets = new ArrayList<ToolboxWidgetInfo>();
		ArrayList<Element> widget_nodes = collectElementNodes(parent.getChildNodes());
		Iterator widget_walker = widget_nodes.iterator();
		Element node;
		
		while (widget_walker.hasNext())
		{
			node = (Element)widget_walker.next();
			try
			{
				toolbox_widgets.add((ToolboxWidgetInfo)createWidgetFromNode(node.getChildNodes(), false));
			}
			catch (Exception e)
			{
				System.out.println("Problem with widget creation:");
				System.out.println(e.getMessage());
				return null;
			}
		}
		return toolbox_widgets;
	}
	
	//For now, it is also enforcing positions of these elements.
	//If you want flexibility, you might want to use getElementsByTagName, but you need to use the same case. Shame, that.
	private WidgetInfo createWidgetFromNode(NodeList widget_properties, boolean is_locked) throws Exception
	{
		ArrayList<Element> widget_prop_nodes = collectElementNodes(widget_properties);
		Iterator walker = widget_prop_nodes.iterator();
		Element node;
		
		String type;
		
		if (is_locked)
		{
			String direction;
			int x, y;
			x = y = 0;
			
			if (widget_prop_nodes.size() != LOCKEDWIDGETNODES)
				throw new Exception ("LockedWidget element does not have " + LOCKEDWIDGETNODES + " children.");
			
			node = (Element)walker.next();
			if (node.getTagName().compareToIgnoreCase(WIDGET_TYPE) != 0)
				throw new Exception ("LockedWidget element does not have a child " + WIDGET_TYPE + " element.");
			type = node.getTextContent();
			
			node = (Element)walker.next();
			if (node.getTagName().compareToIgnoreCase(WIDGET_POSX) != 0)
				throw new Exception ("LockedWidget element does not have a child " + WIDGET_POSX + " element.");
			x = Integer.parseInt(node.getTextContent());
			
			node = (Element)walker.next();
			if (node.getTagName().compareToIgnoreCase(WIDGET_POSY) != 0)
				throw new Exception ("LockedWidget element does not have a child " + WIDGET_POSY + " element.");
			y = Integer.parseInt(node.getTextContent());
			
			node = (Element)walker.next();
			if (node.getTagName().compareToIgnoreCase(WIDGET_DIRECTION) != 0)
				throw new Exception ("LockedWidget element does not have a child " + WIDGET_DIRECTION + " element.");
			direction = node.getTextContent();
			
			return new LockedWidgetInfo(type, direction, x, y);
		}
		else
		{
			int number;
			if (widget_prop_nodes.size() != TOOLBOXWIDGETNODES)
				throw new Exception ("ToolboxWidget element does not have " + TOOLBOXWIDGETNODES + " children.");
			
			//First node is the widget type, which we store.
			node = (Element)walker.next();
			if (node.getTagName().compareToIgnoreCase(WIDGET_TYPE) != 0)
				throw new Exception ("ToolboxWidget element does not have a child " + WIDGET_TYPE + " element.");
			type = node.getTextContent();
		
			//Second node is the number of that widgets available, which we also store.
			node = (Element)walker.next();
			if (node.getTagName().compareToIgnoreCase(WIDGET_NUMBER) != 0)
				throw new Exception ("ToolboxWidget element does not have a child " + WIDGET_NUMBER + " element.");
			number = Integer.parseInt(node.getTextContent());
		
			return new ToolboxWidgetInfo(type, number);
		}
	}
	//Retrieves the Win Condition from the Document and stores it.
	private WinConditionInfo parseWinCondition(Element parent, String type)
	{
		ArrayList<Element> wincond_tags = collectElementNodes(parent.getChildNodes());
		ArrayList<Object> wincond_args = new ArrayList<Object>();
		for (Element tag : wincond_tags)
		{
			wincond_args.add(tag.getTextContent());
		}

		return new WinConditionInfo(type, wincond_args);
	}

	/**
	 * Writes the in-memory representation of the XML Document to hard disk.
	 * @param filename The file to save to.
	 * @param xml_doc The in-memory representation of the XML Document.
	 * @return Whether it was successful (true) or not (false).
	 */
	public static boolean writeToFile(String filename, Document xml_doc)
	{
		if (xml_doc == null)
			return false;
		
		try
		{
			Source source = new DOMSource(xml_doc);
			
			File file = new File(filename);
			Result result = new StreamResult(file);
			
			Transformer xformer = TransformerFactory.newInstance().newTransformer();
			xformer.transform(source, result);
			return true;
			/*
			XMLOutputter doc_out = new XMLOutputter();
			FileWriter writer = new FileWriter(filename);
			doc_out.setIndent("  "); // use two space indentation
			doc_out.setNewlines(true); 
			doc_out.output(xml_doc, writer);
			writer.close();*/
		}
		catch (TransformerException e)
		{
			System.out.println(filename + " had a problem with writing:");
			System.out.println(e.getMessage());
		}
		catch (Exception e)
		{
			System.out.println("There was a problem:");
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	/**
	 * Writes the in-memory representation of the XML Document referenced
	 * by this class to hard disk. Still references the original file that
	 * it pulled the Document from afterward.
	 * @param filename The file to save to.
	 * @return Whether it was succesful (true) or not (false).
	 */
	public boolean writeToFile(String filename)
	{
		if (puzzle_set == null)
			return false;
		//Might be a good idea to just dump puzzle_set and filename to the static
		// method above.
		
		//return writeToFile(filename, puzzle_set);
		try
		{
			Source source = new DOMSource(puzzle_set);
			
			File file = new File(filename);
			Result result = new StreamResult(file);
			
			Transformer xformer = TransformerFactory.newInstance().newTransformer();
			xformer.transform(source, result);
			
			return true;
		}
		catch (TransformerException e)
		{
			System.out.println(filename + " had a problem with writing:");
			System.out.println(e.getMessage());
		}
		catch (Exception e)
		{
			System.out.println("There was a problem:");
			System.out.println(e.getMessage());
		}
		
		return false;
	}
	
	/**
	 * Writes the in-memory representation of the XML Document referenced
	 * by this class to hard disk under the original filename.
	 * @return Whether it was successful (true) or not (false).
	 */
	public boolean writeToFile()
	{
		if (puzzle_set == null)
			return false;
		
		//	return writeToFile(original_filename, puzzle_set);
		try
		{
			Source source = new DOMSource(puzzle_set);
			
			File file = new File(original_filename);
			Result result = new StreamResult(file);
			
			Transformer xformer = TransformerFactory.newInstance().newTransformer();
			xformer.transform(source, result);
			
			return true;
		}
		catch (TransformerException e)
		{
			System.out.println(original_filename + " had a problem with writing:");
			System.out.println(e.getMessage());
		}
		catch (Exception e)
		{
			System.out.println("There was a problem:");
			System.out.println(e.getMessage());
		}
		
		return false;
	}
	
	/*
	 * Attempts to remove a level from the XML document that is on hard disk,
	 * based on a Puzzle class.
	 * If a change has occured, it will be saved back to hard disk.
	 * @param filename The file to load the XML Document and save back to
	 * afterward.
	 * @param old_puzzle The Puzzle to be removed.
	 * @return Whether it was successful (true) or not (false).
	 */
	/*
	public static boolean deleteLevel(String filename, Puzzle old_puzzle)
	{
		if (filename == null)
			return false;
		if (old_puzzle == null)
			return false;
		
		Document ondisk_puzzle_set = getLevelXMLFromFile(filename);
		
		if (!deleteLevel(puzzle_set, old_puzzle))
				return false;
			
		return writeToFile(filename, puzzle_set);
	}*/
	
	/*
	 * Attempts to remove a level from the XML document that is on hard disk,
	 * based on a level number.
	 * If a change has occured, it will be saved back to hard disk.
	 * @param filename The file to load the XML Document and save back to 
	 * afterward.
	 * @param level_num The number of the level to remove. This must run from
	 * 1 to MAXINT, and will be modified by the method to suit file splits.
	 * @return Whether it was successful (true) or not (false).
	 */
	/*
	public static boolean deleteLevel(String filename, int level_num)
	{
		if (filename == null)
			return false;
		if (level_num <= 0)
			return false;
			
		Document puzzle_set = getLevelXMLFromFile(filename);
		
		if (!deleteLevel(puzzle_set, level_num))
			return false;
		
		return writeToFile(filename, puzzle_set);
	}*/

	/**
	 * Attempts to remove a level from a loaded XML document, based on a 
	 * Puzzle class.
	 * If a change has occured, it will (hopefully) be reflected in the 
	 * passed-in Document.
	 * @param old_puzzle The Puzzle to be removed.
	 * @return Whether it was successful (true) or not (false).
	 */
	public boolean deleteLevel(Puzzle old_puzzle)
	{
		if (old_puzzle == null)
			return false;
			
		Element doc_root;
		Node level_root;
		doc_root = puzzle_set.getDocumentElement();

		ArrayList<Element> existing_puzzles = collectElementNodes(doc_root.getChildNodes());
		for (int i = 0; i < existing_puzzles.size(); i++)
		{
			level_root = (Element)existing_puzzles.get(i);
			level_root = (Element)(collectElementNodes(level_root.getChildNodes()).get(0));
			if ((level_root != null) && (level_root.getTextContent().compareToIgnoreCase(old_puzzle.getTitle()) == 0))
				return deleteLevel(i+1);
		}
		
		System.out.println("Could not find puzzle in Document.");
		return false;
		
	}
	
	/**
	 * Attempts to remove a level from a loaded XML document, based on
	 * a level number.
	 * If a change has occured, it will (hopefully) be reflected in the
	 * passed-in Document.
	 * @param level_num The number of the level to remove. This must run from
	 * 1 to MAXINT, and will be modified by the method to suit file splits.
	 * @return Whether it was successful (true) or not (false).
	 */
	public boolean deleteLevel(int level_num)
	{
		if (puzzle_set == null)
			return false;
		if (level_num <= 0)
			return false;
		
		int modified_levelnum;
				
		Element doc_root, level_root;
		doc_root = puzzle_set.getDocumentElement();
		ArrayList<Element> existing_puzzles = collectElementNodes(doc_root.getChildNodes());
		
		modified_levelnum = (level_num - 1) % NUMPUZZLES_PER_DOCUMENT;
		if (modified_levelnum > existing_puzzles.size())
		{
			System.out.println("Attempted to delete a level from beyond the Document's range.");
			return false;
		}
		
		try
		{
			doc_root.removeChild((Element)existing_puzzles.get(modified_levelnum));
		}
		catch (DOMException e)
		{
			System.out.println("Problem removing old puzzle from Document: " + e.getMessage());
			return false;
		}
		
		return true;
	}
	
	/*
	 * Adds a new level to the end of the XML document on hard disk, then saves
    * the changes.
	 * If a maximum number of levels are enforced by this class, then it will
    * fail if it attempts to add a new level to an already full document.
	 * @param filename The file to load the XML document from. Also where the
    * changes will be saved to.
	 * @param new_puzzle The new Puzzle to add.
	 * @return Whether it was successful (true) or not (false). If false, then
	 * no changes were made to the file.
	 */
	/*
	public static boolean appendLevel(String filename, Puzzle new_puzzle)
	{
		if (filename == null)
			return false;
		if (new_puzzle == null)
			return false;
			
		Document puzzle_set = getLevelXMLFromFile(filename);
		if (puzzle_set == null)
		{
			return false;
		}
		
		if (!appendLevel(puzzle_set, new_puzzle))
			return false;
			
		return writeToFile(filename, puzzle_set);
	}*/
	
	/*
	 * Inserts a new level into the XML document on hard disk, then saves 
	 * the resulting changes.
	 * If a maximum number of levels are enforced by this class, then it will
	 * fail if it attempts to insert into an already full document.
	 * @param filename The file to load the XML document from. Also where the
	 * change will be saved to.
	 * @param new_puzzle The new Puzzle to add.
	 * @return Whether it was successful (true) or not (false). If false, then
	 * no changes were made to the file.
	 */
	/*
	public static boolean insertLevel(String filename, Puzzle new_puzzle, int level_num)
	{
		if (filename == null)
			return false;
		if (new_puzzle == null)
			return false;
		if (level_num <= 0)
			return false;
		
		Document puzzle_set = getLevelXMLFromFile(filename);
		if (puzzle_set == null)
		{
			return false;
		}
		
		if (!insertLevel(puzzle_set, new_puzzle, level_num))
			return false;
		
		return writeToFile(filename, puzzle_set);
	}*/
	
	/**
	 * Adds a new level to the end of the in-memory XML document.
	 * If a maximum number of levels are enforced by this class, then it will
     * fail if it attempts to add a new level to an already full document.
	 * @param new_puzzle The new Puzzle to add.
	 * @return Whether it was successful (true) or not (false). If false, then
	 * no changes were made to puzzle_set.
	 */
	public boolean appendLevel(Puzzle new_puzzle)
	{
		if (new_puzzle == null)
			return false;
			
		Element root;
		ArrayList<Element> existing_puzzles;
		int num_puzzles;
		
		root = puzzle_set.getDocumentElement();
		existing_puzzles = collectElementNodes(root.getChildNodes());
		num_puzzles = existing_puzzles.size();
		
		return insertLevel(new_puzzle, num_puzzles+1);
	}
	

	/**
	 * Inserts a new level into the in-memory XML document.
	 * If a maximum number of levels are enforced by this class, then it will
	 * fail if it attempts to insert into an already full document.
	 * @param new_puzzle The new Puzzle to add.
	 * @return Whether it was successful (true) or not (false). If false, then
	 * no changes were made to puzzle_set.
	 */
	public boolean insertLevel(Puzzle new_puzzle, int level_num)
	{
		if (puzzle_set == null)
			return false;
		if (new_puzzle == null)
			return false;
		if (level_num <= 0)
			return false;
		
		Element doc_root, level_root;
		int modified_levelnum;
		
		doc_root = puzzle_set.getDocumentElement();
		ArrayList<Element> existing_puzzles = collectElementNodes(doc_root.getChildNodes());
		if (existing_puzzles.size() > NUMPUZZLES_PER_DOCUMENT)
		{
			System.out.println("Document already has " + NUMPUZZLES_PER_DOCUMENT + " stored. Please use another level set.");
			return false;
		}
			
		level_root = createLevelBranch(puzzle_set, new_puzzle);
		if (level_root == null)
		{
			System.out.println("Error in appendToLevelXML(Document puzzle_set, Puzzle new_puzzle, int level_num).");
			return false;
		}
		
		modified_levelnum = (level_num - 1) % NUMPUZZLES_PER_DOCUMENT;
		
		//If we are past the range, then we perform the insertion at the end of the list,
		// no matter what the actual number asked for.
		if (modified_levelnum >= existing_puzzles.size())
		{
			try
			{
				doc_root.insertBefore(level_root, null);
			}
			catch (DOMException e)
			{
				System.out.println("Document failed to add new node to the end of itself: " + e.getMessage());
				return false;
			}
		}
		else
		{
			try
			{
				doc_root.insertBefore(level_root, existing_puzzles.get(modified_levelnum));
			}
			catch (DOMException e)
			{
				System.out.println("Document failed to insert new level before the existing " + level_num + " level: " + e.getMessage());
				return false;
			}
		}
		
		return true;
	}
	
	//Creates a root element that holds all the level information to be passed into the Document
	private static Element createLevelBranch(Document puzzle_set, Puzzle new_puzzle)
	{
		Element level_root, level_node, widget_node, wincond_node;
		Attr wincond_attr;
		String win_type;
		WinConditionInfo wincond;
		
		level_root = puzzle_set.createElement(LEVEL_TAG);
		
		level_node = puzzle_set.createElement(TITLE);
		try
		{
			level_node.setTextContent(new_puzzle.getTitle());
			level_root.appendChild(level_node);
		}
		catch (DOMException e)
		{
			System.out.println("Document failed to add in the <" + TITLE + "> tag: " + e.getMessage());
			return null;
		}
		
		level_node = puzzle_set.createElement(PASS);
		try
		{
			level_node.setTextContent(new_puzzle.getPass());
			level_root.appendChild(level_node);
		}
		catch (DOMException e)
		{
			System.out.println("Document failed to add in the <" + PASS + "> tag: " + e.getMessage());
			return null;
		}
		
		level_node = puzzle_set.createElement(SUMMARY);
		try
		{
			level_node.setTextContent(new_puzzle.getSummary());
			level_root.appendChild(level_node);
		}
		catch (DOMException e)
		{
			System.out.println("Document failed to add in the <" + SUMMARY + "> tag: " + e.getMessage());
			return null;
		}
		
		level_node = puzzle_set.createElement(TIME);
		try
		{
			level_node.setTextContent(Integer.toString(new_puzzle.getTime()));
			level_root.appendChild(level_node);
		}
		catch (DOMException e)
		{
			System.out.println("Document failed to add in the <" + TIME + "> tag: " + e.getMessage());
			return null;
		}
		
		level_node = puzzle_set.createElement(SCORE);
		try
		{
			level_node.setTextContent(Integer.toString(new_puzzle.getScore()));
			level_root.appendChild(level_node);
		}
		catch (DOMException e)
		{
			System.out.println("Document failed to add in the <" + SCORE + "> tag: " + e.getMessage());
			return null;
		}
		
		level_node = puzzle_set.createElement(LOCKED_WIDGET_SET);
		try
		{
			Widget.Direction dir;
			String dir_string;
			for(LockedWidgetInfo lwi : new_puzzle.getLockedWidgetSet())
			{
				widget_node = puzzle_set.createElement(WIDGET_TYPE);
				widget_node.setTextContent(lwi.getWidgetClassName());
				level_node.appendChild(widget_node);
				
				widget_node = puzzle_set.createElement(WIDGET_POSX);
				widget_node.setTextContent(Float.toString(lwi.getX()));
				level_node.appendChild(widget_node);
				
				widget_node = puzzle_set.createElement(WIDGET_POSY);
				widget_node.setTextContent(Float.toString(lwi.getY()));
				level_node.appendChild(widget_node);
				
				widget_node = puzzle_set.createElement(WIDGET_DIRECTION);
				
				dir = lwi.getDirection();
				if (dir == Widget.Direction.NORTH)
					dir_string = "NORTH";
				else if (dir == Widget.Direction.SOUTH)
					dir_string = "SOUTH";
				else if (dir == Widget.Direction.EAST)
					dir_string = "EAST";
				else
					dir_string = "WEST";
				widget_node.setTextContent(dir_string);
				level_node.appendChild(widget_node);
			}
			level_root.appendChild(level_node);
		}
		catch (DOMException e)
		{
			System.out.println("Document failed to add in the <" + LOCKED_WIDGET_SET + "> tag: " + e.getMessage());
			return null;
		}
		
		level_node = puzzle_set.createElement(TOOLBOX_WIDGET_SET);
		try
		{
			for (ToolboxWidgetInfo twi : new_puzzle.getToolboxWidgetSet())
			{
				widget_node = puzzle_set.createElement(WIDGET_TYPE);
				widget_node.setTextContent(twi.getWidgetClassName());
				level_node.appendChild(widget_node);
				
				widget_node = puzzle_set.createElement(WIDGET_NUMBER);
				widget_node.setTextContent(Integer.toString(twi.getNumber()));
				level_node.appendChild(widget_node);
			}
			
			level_root.appendChild(level_node);
		}
		catch (DOMException e)
		{
			System.out.println("Document failed to add in the <" + TOOLBOX_WIDGET_SET + "> tag: " + e.getMessage());
			return null;
		}
		
		level_node = puzzle_set.createElement(WINCOND);
		try
		{
			wincond = new_puzzle.getWinCondition();
			win_type = wincond.getType();
			level_node.setAttribute(WINCOND_ATTR, win_type);
			for (Object arg : wincond.getArguments())
			{
				wincond_node = puzzle_set.createElement(WINCOND_ARG);
				wincond_node.setTextContent(arg.toString());
				level_node.appendChild(wincond_node);
			}
			level_root.appendChild(level_node);
		}
		catch (DOMException e)
		{
			System.out.println("Document failed to add in the <" + WINCOND + "> tag: " + e.getMessage());
			return null;
		}
		
		return level_root;
	}
	
	/**
	 * Attempts to replace a level with a new one inside the in-memory 
	 * XML Document. Use when you want to make a replacement for a level
	 * at such-n-such a number.
	 * @param new_puzzle The Puzzle that will replace the one already existing
	 * in the document.
	 * @param level_num The number of the Puzzle to be replaced. This must be in
	 * the range 1 to MAXINT and will be further transformed by modulus to suit
	 * the max number of levels stored per Document.
	 * @return Whether it was successful (true) or not (false). If false, then
	 * changes were not saved to puzzle_set.
	 */
	public boolean replaceLevel(Puzzle new_puzzle, int level_num)
	{
		if (new_puzzle == null)
			return false;
		if (level_num <= 0)
			return false;
		
		Element doc_root, level_root;
		ArrayList<Element> existing_puzzles;
		int modified_levelnum;
		
		doc_root = puzzle_set.getDocumentElement();
		existing_puzzles = collectElementNodes(doc_root.getChildNodes());
		
		modified_levelnum = (level_num - 1) % NUMPUZZLES_PER_DOCUMENT;
		
		if (modified_levelnum > existing_puzzles.size())
		{
			System.out.println("No puzzle exists in that slot. There are only " + existing_puzzles.size() + " stored in this Document.");
			return false;
		}
		
		level_root = createLevelBranch(puzzle_set, new_puzzle);
		if (level_root == null)
		{
			System.out.println("Error in replaceLevel().");
			return false;
		}
		
		try
		{
			doc_root.replaceChild(level_root, existing_puzzles.get(modified_levelnum));
		}
		catch (DOMException e)
		{
			System.out.println("Error in replacing the puzzle: " + e.getMessage());
			return false;
		}
		
		return true;
	}
	
	/**
	 * Attempts to replace a level with a new one inside the in-memory 
	 * XML Document. Use when you have the old Puzzle class, which will
	 * be identified by its title element in the document.
	 * @param new_puzzle The Puzzle that will replace the one already existing
	 * in the document.
	 * @param old_puzzle The Puzzle to be replaced. It will be searched for 
	 * within the document.
	 * @return Whether it was successful (true) or not (false). If false, then
	 * changes were not saved to puzzle_set.
	 */
	public boolean replaceLevel(Puzzle new_puzzle, Puzzle old_puzzle)
	{
		if (new_puzzle == null)
			return false;
		if (old_puzzle == null)
			return false;
			
		Element doc_node, level_node;
		ArrayList<Element> existing_puzzles;
		
		doc_node = puzzle_set.getDocumentElement();
		existing_puzzles = collectElementNodes(doc_node.getChildNodes());
		
		//This finds the old_puzzle in the Document. We need to shift the level_num parameter by one
		// so that it keeps with the numbering system we have for levels.
		for (int i = 0; i < existing_puzzles.size(); i++)
		{
			level_node = (Element)existing_puzzles.get(i);
			level_node = (Element)collectElementNodes(level_node.getChildNodes()).get(0);
			if (level_node.getTextContent().compareToIgnoreCase(old_puzzle.getTitle()) == 0)
				return replaceLevel(new_puzzle, i+1);
		}
		
		return false;
	}
	
	/**
	 * Retrieves every single title of the levels that reside within the
	 * XML document. For use with the Select Level class.
	 * @return A collection of strings, in the same order as they were 
	 * read from the document.
	 */
	public ArrayList<String> getTitlesList ()
	{
		if (puzzle_set == null)
			return null;
			
		ArrayList<String> titles = new ArrayList<String>();
		Element doc_root = puzzle_set.getDocumentElement();
		NodeList title_elements = doc_root.getElementsByTagName(TITLE);
		
		Node n;
		for (int i = 0; i < title_elements.getLength(); i++)
		{
			n = title_elements.item(i);
			titles.add(n.getTextContent());
		}
		
		return titles;
	}
	
	/**
	 * Retrieves a single password associated with a level number.
	 * @param level_num The number of the level that the password resides in.
	 * This must be part of the range 1 to MAXINT and will be further modified
	 * by this method to fit within the maximum number of levels stored per
	 * document.
	 * @return The password found. Otherwise null.
	 */
	public String getPassword (int level_num)
	{
		if (puzzle_set == null)
			return null;
		if (level_num <= 0)
			return null;
			
		Element doc_root = puzzle_set.getDocumentElement();
		NodeList pass_element = doc_root.getElementsByTagName(PASS);
		int modified_levelnum = (level_num-1) % NUMPUZZLES_PER_DOCUMENT;
		if (modified_levelnum > pass_element.getLength())
			return null;
			
		return pass_element.item(modified_levelnum).getTextContent();
	}
	
	/** 
	 * Retrieves the raw XML Document used by this class.
	 * @return The in-memory representation of an XML file.
	 */
	public Document getDocument ()
	{
		return puzzle_set;
	}
	
	/**
	 * Retrieves the filename that the XML file was loaded from
	 * upon initialization.
	 * @return The path to the XML file used by this class.
	 */
	public String getFilename()
	{
		return original_filename;
	}
	
}
