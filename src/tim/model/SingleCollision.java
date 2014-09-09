/** CIS3750 Group Four
*   @author Kiril Goguev
*   single-collision class implments WinTester interface, provides a class specifically designed to handle single collision WinCondtions
*   specifically, any time a specified widget collides with anything in the TimWorld the player wins.
*/
package tim.model;
import net.phys2d.math.*;
import net.phys2d.raw.*;
import Widgets.*;

public class SingleCollision implements WinTester,CollisionListener
{
	/*The widget that will be checked for the win condition*/
	private Widget targetWidget;
	private String winConditionType;
	private boolean ConditionMet = false;
    
	/**
	 * Constructor, initializes an empty class. Needs
	 * to be loaded with a Widget later on if it is to be used.
	 */
    public SingleCollision()
	{

	}
    
	/** sets the widget that will be used to win the level
	*	@param winConditionWidget Widget that will be used in checking for
   * collisions and winning conditions
	**/
	public SingleCollision(Widget winConditionWidget)
	{
		this.setTargetWidget(winConditionWidget);
	}
	
	

	/** Sets up the private Widget TargetWidget variable.
	*	@param winConditionWidget The Widget this class depends on
	* to check that a level has been won.
	*
	**/	
	public void setTargetWidget(Widget winConditionWidget)
	{
		this.targetWidget=winConditionWidget;
	}

	/** Retrieves the widget stored by this class.
	*	@return The widget that is to be checked for a collision.
	*
	**/	
	public Widget getTargetWidget()
	{
		return this.targetWidget;
	}

	/**
	 * Upon a collision event, checks to see if the Widget
	 * is involved. If so, then the condition for winning
	 * is met.
	 */
	public void collisionOccured(CollisionEvent ce)
    	{
		//get targetWidget Bodies 
		Body [] w = targetWidget.getBodiesForSimulation();
		int count=0;
		//iterate through each body in the collision event and makes sure that inner bodies dont set themselves off
		for(int i= 0 ; i< w.length;i++)
		{
			if ((w[i]==ce.getBodyA())||(w[i]==ce.getBodyB()))
			{
				//increment the counter if and only if one of the bodies in the targetWidget has collided with any of the bodies in the world
				count++;
			}
			
		}
		// if the targetWidget body has collided with any other widgets in the world then the condition was met
		if (count==1)
		{
			ConditionMet=true;
		}
	}
	
	/** checks if the specified widget collided with anything in the timWorld
	*   @return True if collided, false otherwise
	*
	**/
	public boolean checkForWin()
	{
		return ConditionMet;
	}
    
    /** Used to reset the win flag after a level is completed
     */
	public void resetWinFlag () {
        ConditionMet = false;
    }

}
