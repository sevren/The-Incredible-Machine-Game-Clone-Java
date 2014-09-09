
/** CIS3750 Group Four
*   @author Kiril Goguev
inside-location
specifies single widget by ID
specifies a rectangular boundary (same as above)
any time all four corners of the widget's boundary are all found within the target boundary simultaneously the player wins
*/

package tim.model;
import net.phys2d.math.*;
import net.phys2d.raw.*;
import Widgets.*;

public class InsideLocationCollision implements WinTester,CollisionListener
{
	private Widget target1;
	private int x,y,width,height;
	private boolean ConditionMet = false;
	private Vector2f[] targetBoundary = new Vector2f[4];
	private Vector2f[] target1Boundary = new Vector2f[4];

	/** 
	 * Constructor for InsideCollision
	 *	@param target1 The widget that the win condition of the level depends upon.
	 * @param x The top left corner x-coordinate of the boundary the widget must be within.
	 * @param y The top left corner y-coordinate of the boundary the widget must be within.
	 * @param width Specifies how wide the boundary will stretch, in pixels.
	 * @param height Specifies how high the boundary will stretch, in pixels.
	 *
	 **/
	public InsideLocationCollision(Widget target1,int x, int y, int width, int height)
	{
		this.setTargetWidget1(target1);
		this.setTargetWidget1Boundary();
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		
		targetBoundary=this.setTargetBoundary(x,y,width,height);
	}	
   
	/** 
	 * Alternative constructor, allowing for the class to be initialized without
	 * a target Widget.
	 * @param x The top left corner x-coordinate of the boundary the widget must be within.
	 * @param y The top left corner y-coordinate of the boundary the widget must be within.
	 * @param width Specifies how wide the boundary will stretch, in pixels.
	 * @param height Specifies how high the boundary will stretch, in pixels.
	 */
   public InsideLocationCollision(int x, int y, int width, int height)
	{
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}
	
	/** Sets up the private Widget target1 variable
	* 	usually only called in the constructor
	*	@param target1 passed in from the constructor
	*
	**/
	public void setTargetWidget1(Widget target1)
	{
		this.target1=target1;
		this.setTargetWidget1Boundary();
        targetBoundary=this.setTargetBoundary(x,y,width,height);
	}

	
	/** Sets up the private Vector2f[4] target1Boundary variable
	* 	usually only called in the constructor
	*	
	*
	**/
	public void setTargetWidget1Boundary()
	{
		target1Boundary=target1.getBoundary();
	}


	/** Gets the private Widget target1 variable
	* 	usually only called in the checkForWin method
	*	@return target1 the target1 widget that was initalized in the constructor
	*
	**/	
	public Widget getTargetWidget1()
	{
		return this.target1;
	}
	
	/** returns the target widget's boundary
		@return target widget's boundary
	*/
	public Vector2f[] getTarget1WidgetBoundary()
	{
		return this.target1Boundary;
	}

	/** setup the boundary for the target location
	@param x top left x position
	@param y top left y position
	@param width width of the boundary
	@param height height of the boundary
	@return the boundaries of the target location in a 4-element array.
	**/
	public Vector2f[] setTargetBoundary(int x, int y,int width, int height)
	{
		Vector2f[] boundary = new Vector2f[4];
		boundary[0] = new Vector2f(x, y);
		boundary[1] = new Vector2f(x + width, y);
		boundary[2] = new Vector2f(x + width, y + height);
		boundary[3] = new Vector2f(x, y + height);
		
		return boundary;
	}
	
	
	public void collisionOccured(CollisionEvent ce)
    	{
	
		
	}


	/** checks if the specified widgets collided with each other in the timWorld
	*   @return True if win condition is met, false otherwise
	*
	**/
	public boolean checkForWin ()
	{
		//updates the moving widget target
		setTargetWidget1Boundary();
       
			//check for intersection -> borrowed code from TimWorld intersection 
	// 		for(int j = 0; j < 4; j++)
     //       {
                /*if((target1Boundary[j].getX()>=targetBoundary[j].getX())
				&&(target1Boundary[j].getY()>=targetBoundary[j].getY())
				&&(target1Boundary[(j+1)%4].getX()<=targetBoundary[(j+1)%4].getX())
				&&(target1Boundary[(j+1)%4].getY()>=targetBoundary[(j+1)%4].getY()))*/
                if
                ( target1Boundary[0].getX() >= targetBoundary[0].getX() &&
                  target1Boundary[0].getY() >= targetBoundary[0].getY() &&
                  
                  target1Boundary[1].getX() <= targetBoundary[1].getX() &&
                  target1Boundary[1].getY() >= targetBoundary[1].getY() &&
                  
                  target1Boundary[2].getX() <= targetBoundary[2].getX() &&
                  target1Boundary[2].getY() <= targetBoundary[2].getY() &&
                  
                  target1Boundary[3].getX() >= targetBoundary[3].getX() &&
                  target1Boundary[3].getY() <= targetBoundary[3].getY() )
                {
			
                    return true;
                }     
    //    }
	
        return false;

		
		
           
	}
       

    /** Used to reset the win flag after a level is completed
     */
	public void resetWinFlag () {
        ConditionMet = false;
    }
		
		
	

}
