/** CIS3750 Group Four
*   @author Kiril Goguev
*   pair-collision class implments WinTester interface, provides a class specifically designed to handle pair collision WinCondtions
*   specifically, any time these 2 widgets collide with each other the player wins
*/
package tim.model;
import net.phys2d.math.*;
import net.phys2d.raw.*;
import Widgets.*;
public class PairCollision implements WinTester,CollisionListener
{
	private Widget target1;
	private Widget target2;
	private boolean ConditionMet = false;
   
	/**
	 * Empty constructor, allowing this class to be initalized
	 * without the two Widgets it depends upon.
	 */
    public PairCollision()
	{
      //  this.setTargetWidget1(target1);
        //this.setTargetWidget2(target2);
	}	
    
	/** Constructor for pair-Collision
	*	@param target1 A widget that must collide...
	*  @param target2 with this second one, in order for a 
	*  win to register for the level.
	*
	**/
	public PairCollision(Widget target1, Widget target2)
	{
		this.setTargetWidget1(target1);
		this.setTargetWidget2(target2);
	}	
	
	/** Sets the first widget of the pair to be collided.
	*	@param target1 The Widget the class depends on for
	*  declaring a puzzle won.
	*
	**/
	public void setTargetWidget1(Widget target1)
	{
		this.target1=target1;
	}

	/** Sets up second widget of the pair to be collided.
	*	@param target2 The Widget the class depends on for
	*  declaring a puzzle won.
	*
	**/
	public void setTargetWidget2(Widget target2)
	{
		this.target2=target2;
	}

	/** Retrieves the first widget of the pair to be collided.
	*	@return target1 The first widget referenced by this class.
	*
	**/	
	public Widget getTargetWidget1()
	{
		return this.target1;
	}

	/** Retrieves the second widget of the pair to be collided.
	*	@return target2 The second widget referenced by this class.
	*
	**/	
	public Widget getTargetWidget2()
	{
		return this.target2;
	}
	
	/**
	 * Upon collision, check to see if the two widgets were involved.
	 * If so, declare a win. If not, be quiet.
	 */
	public void collisionOccured(CollisionEvent ce)
    	{
		//get targetWidgets Bodies 
		Body [] t1 = target1.getBodiesForSimulation();
		Body [] t2 = target2.getBodiesForSimulation();
		int count=0;
		//iterate through each body in the collision event and makes sure that inner bodies dont set themselves off
		for(int i= 0 ; i< t1.length;i++)
		{
			for (int j =0;j<t2.length;j++)
			{
				if ((t1[i]==ce.getBodyA())&&(t2[j]==ce.getBodyB())||(t1[i]==ce.getBodyB())&&(t2[j]==ce.getBodyA()))
				{
					ConditionMet=true;
					break;
				}
			}
			
		}
		
	}

	/** checks if the specified widgets collided with eachother in the timWorld
	*   @return True if win condition is met, false otherwise
	*
	**/
	public boolean checkForWin ()
	{
		return ConditionMet;	
	}
    
    /** Used to reset the win flag after a level is completed
     */
	public void resetWinFlag () {
        ConditionMet = false;
    }
}
