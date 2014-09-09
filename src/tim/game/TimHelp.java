/** TimHelp.java
* Contains various methods for working with the help screens
* @author Kiril Goguev
**/
package tim.game;
import java.io.*;
import java.util.*;

public class TimHelp 
{

	private ArrayList<String> helpFiles = new ArrayList<String>();
	private File HelpDirectory = new File ("resources/help");
	

	/**
	 * Constructor, creates the class and prepares the help files
	 * for display in the program.
	 */
	public TimHelp()
	{
		this.Readin();
	}


	/** Checks if help documentation exists in the reasources/help folder
	*   Really doesnt need to be called outside of this class
	*	@return True if files are available for reading in from the specified
	*   folder, false otherwise
	*/
	private boolean HelpFilesExist()
	{
		
		if (HelpDirectory.listFiles().length==0)
		{
			//ERROR CHECKING REMOVE ME WHEN FINISHED
			System.out.println("Error: Help Files are not available\n");
			return false;
		}
		else
		{
			//ERROR CHECKING REMOVE ME WHEN FINISHED
			System.out.println("The number of help files are: "+HelpDirectory.listFiles().length+" \n");
			return true;
		}
	}
	
	
	/** Reads in from all files into an arraylist
	* really should only be called once at the start of the game!
	* @author Kiril Goguev
	* @return boolean based on successful read in of files
	**/
	private boolean  Readin()
	{
		int filesReadCounter=0;
		FileReader textIn;
		BufferedReader helpText;
		String helpTextString;
		String helpFileContents ="";
		ArrayList<String> files = new ArrayList<String>();
		files.add("resources/help/Main.Help");
		files.add("resources/help/FreePlay.Help");
		files.add("resources/help/Level1.Help");
		files.add("resources/help/Level2.Help");
		files.add("resources/help/Level3.Help");
		files.add("resources/help/Level4.Help");
		files.add("resources/help/Level5.Help");
		files.add("resources/help/Level6.Help");
		files.add("resources/help/Level7.Help");
		files.add("resources/help/Level8.Help");
		files.add("resources/help/Level9.Help");
		files.add("resources/help/Level10.Help");
		if(true)
		{
			try
			{

					for(int i=0;i<files.size();i++)
					{
						//open each file in the file[] array
						//textIn = new FileReader(files.get(i));
						InputStream is =  TimHelp.class.getClassLoader().getResourceAsStream(files.get(i));
						InputStreamReader isr = new InputStreamReader(is);

						helpText = new BufferedReader(isr);
						//read in each file line by line
						helpTextString = helpText.readLine();
						//Build the contents of the file into a continuous string
						helpFileContents = helpFileContents+helpTextString+"\n";
						//Read until the end of the file
						while (helpTextString !=null)
						{
							helpTextString = helpText.readLine();
							helpFileContents = helpFileContents+helpTextString+"\n";
						}
						helpFileContents = helpFileContents+helpTextString+"\n";
						//store the contents of each string in the arraylist
						helpFiles.add(helpFileContents);
						//Clear the contents of string for the next file
						helpFileContents="";
					}

				
				return true;
			}
			catch(Exception E)
			{
				//ERROR CHECKING REMOVE ME WHEN FINISHED
				System.out.println("Error: reading in from a file "+E.getMessage());
				return false;
			}
		}
		else
		{
			//ERROR CHECKING REMOVE ME WHEN FINISHED
			System.out.println("Error: No help Files exist, can not read in all help files");
			return false;
		}
		
	}	
	/** Returns the proper string for the Help menu to be displayed based on the parameters from the current game state
	*	@param State trigger for returning which content to return to timGui, may be changed later to use a GameState Enum...
	*
	*/
	public String getHelpContent(String State)
	{
		if (State.matches("Main"))
		{
			return helpFiles.get(0);
		}
		else if (State.matches("Freeplay"))
		{
			return helpFiles.get(1);
		}
		else if (State.matches("First Steps - 01"))
		{
			return helpFiles.get(2);
		}
		else if (State.matches("Gorilla vs. Bomb - 02"))
		{
			return helpFiles.get(3);
		}
		else if (State.matches ("Monkey's Playground - 03"))
		{
			return helpFiles.get(4);
		}
		else if (State.matches("Level FOUR (aka. The fourth level) - 04"))
		{
			return helpFiles.get(5);
		}
		else if (State.matches ("Sports ball reunion - 05"))
		{
			return helpFiles.get(6);
		}
		else if (State.matches("Level6"))
		{
			return helpFiles.get(7);
		}
		else if (State.matches("Level7"))
		{
			return helpFiles.get(8);
		}
		else if (State.matches("Level8"))
		{
			return helpFiles.get(9);
		}
		else if (State.matches ("Level9"))
		{
			return helpFiles.get(10);
		}
		else if (State.matches("Level10"))
		{
			return helpFiles.get(11);
		}
		else
		{
			return null;
		}
	}
	
	
	
}
