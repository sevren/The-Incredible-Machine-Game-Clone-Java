package tim.model;

/**
 * Class with the minimum required to initialize a Widget
 * within the WidgetScroller.
 * @author Nathaniel Ridder
 */
public class ToolboxWidgetInfo extends WidgetInfo
{
	private int number;
	
	/**
	 * The real constructor. Everything needed to make a Widget
	 * appear on the WidgetScroller is passed in at this time.
	 * @param type Widget Class name. Found from Widget filenames.
	 * @param number The amount of copies available.
	 */
	public ToolboxWidgetInfo(String type, int number)
	{
		widget_name = type;
		this.number = number;
	}
	
	/**
	 * Retrieves the amount of copies available of this Widget.
	 * @return Number of copies.
	 */
	public int getNumber()
	{
		return number;
	}

}