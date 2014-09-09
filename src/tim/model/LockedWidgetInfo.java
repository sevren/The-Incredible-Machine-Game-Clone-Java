package tim.model;

import Widgets.*;

/**
 * Class storing the least amount of information needed about Widgets
 * that will be part of the Level once it is loaded into the playfield.
 * @author Nathaniel Ridder
 */
public class LockedWidgetInfo extends WidgetInfo
{
	private Widget.Direction dir;
	private float x, y;
	

	/**
	 * The real constructor. All the information needed to create a Widget
	 * will be passed in as parameters here. This will likely be used by
	 * the freeplay class, for ease of use.
	 * @param type Widget Class name. Found by reading filenames.
	 * @param direction Enumeration of NORTH, SOUTH, EAST or WEST. Pick one.
	 * @param x The coordinate on the x-axis as a float. Parser will lop off
	 * the subpixel positioning (the fraction).
	 * @param y The coordinate on the y-axis as a float. Parser will lop off 
	 * the subpixel positioning (the fraction).
	 */
	public LockedWidgetInfo (String type, Widget.Direction direction, float x, float y)
	{
		widget_name = type;
		
		dir = direction;
			
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Constructor most likely to be used by the LevelParser. Or 99% true, as 
	 * the same person who wrote this Class wrote the LevelParser! 
	 * @param type Widget Class name. Found by reading Widget filenames.
	 * @param direction The enumeration of NORTH, SOUTH, EAST or WEST. Used
	 * due to the fact that XML does not care about java enums and dumps all
	 * stored information back in as strings. 
	 * @param x The x-axis value, based on position.
	 * @param y The y-axis value, based on position.
	 */
	public LockedWidgetInfo (String type, String direction, int x, int y)
	{
		widget_name = type;
		
		if (direction.compareToIgnoreCase("NORTH") == 0)
			dir = Widget.Direction.NORTH;
		else if (direction.compareToIgnoreCase("SOUTH") == 0)
			dir = Widget.Direction.SOUTH;
		else if (direction.compareToIgnoreCase("EAST") == 0)
			dir = Widget.Direction.EAST;
		else
			dir = Widget.Direction.WEST;
		
		this.x = (float)x;
		this.y = (float)y;
	}
	
	/**
	 * Retrieves the direction that the Widget needs to have set.
	 * @return Enumerated Direction.
	 */
	public Widget.Direction getDirection()
	{
		return dir;
	}
	
	/**
	 * Retrieves the position on the x-axis the Widget needs to have set.
	 * @return X-axis position.
	 */
	public float getX()
	{
		return x;
	}
	
	/**
	 * Retrieves the position on the y-axis the Widget needs to have set.
	 * @return Y-axis position.
	 */
	public float getY()
	{
		return y;
	}
}
