package tim.model;

import java.util.ArrayList;

/**
 * Class holding information to construct a new WinCondition.
 */
public class WinConditionInfo {

    public final static String SINGLECOLLISION = "SingleCollision";
    public final static String PAIRCOLLISION = "PairCollision";
    public final static String TOUCHLOCATION = "TouchLocation";
    public final static String INSIDELOCATION = "InsideLocation";
    
	private String type;
	private ArrayList<Object> args;
	
	/**
	 * Constructor, stores information passed in.
	 * Immutable.
	 * @param type The type of WinCondition required. Valid ones are
	 * 'SingleCollision', 'PairCollision', 'TouchLocation', 'InsideLocation' .
	 * @param arguments The various arguments needed to process that Win Condition.
	 */
	public WinConditionInfo (String type, ArrayList<Object> arguments)
	{
		this.type = new String(type);
		args = arguments;
	}
	
	/**
	 * Retrieves the type that needs to be instantiated from this information.
	 * @return The type, same as one of the WinCondition Classes.
	 */
	public String getType()
	{
		return type;
	}
	
	/**
	 * Retrieves the arguments needed to instantiated a Win Condition class.
	 * @return The arguments, can be anything so it can be extended for more
	 * types of Win Conditions.
	 */
	public ArrayList<Object> getArguments()
	{
		return args;
	}
	
}
