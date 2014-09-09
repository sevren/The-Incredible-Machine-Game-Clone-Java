
/** CIS3750 Group Four
*   @author Kiril Goguev
touching-location
specifies a single widget by ID
specifies a rectangular boundary (by x/y loc of top left, width + height)
any time the widget's boundary overlaps the target boundary the player wins
*/

package tim.model;
import net.phys2d.math.*;
import net.phys2d.raw.*;
import Widgets.*;

public class TouchingLocationCollision implements WinTester
{
	private Widget target1;
	private int x, y, width, height;
	private boolean ConditionMet = false;
	private Vector2f[] targetBoundary = new Vector2f[4];
	private Vector2f[] target1Boundary = new Vector2f[4];
	
	/** Constructor for TouchingCollision
	 *	@param target1 The widget that the win condition of the level depends upon.
	 * @param x The top left corner x-coordinate of the boundary the widget must intersect.
	 * @param y The top left corner y-coordinate of the boundary the widget must intersect.
	 * @param width Specifies how wide the boundary will stretch, in pixels.
	 * @param height Specifies how high the boundary will stretch, in pixels.
	 *
	 **/
	public TouchingLocationCollision(Widget target1,int x, int y, int width, int height)
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
	  * Alternative constructor, allowing the class to be initialized without
	  * a target widget.
	  * @param x The top left corner x-coordinate of the boundary the widget must intersect.
	  * @param y The top left corner y-coordinate of the boundary the widget must intersect.
	  * @param width Specifies how wide the boundary will stretch, in pixels.
	  * @param height Specifies how high the boundary will stretch, in pixels.
	  */
    public TouchingLocationCollision(int x, int y, int width, int height)
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
	*	@return Widget referenced by this class.
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
	@return the boundaries of the target location
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
	
	
	 /**
      Test for intersection of two line segments.  The two line segments are defined by their endpoints and each segment must be either horizontal or vertical.

      @param a Start point of first line segment.
      @param b End point of first line segment.
      @param c Start point of second line segment.
      @param d End point of second line segment.
      @return True if lines intersect or are coincident, otherwise false.
      */
    private boolean testLineIntersection(Vector2f a, Vector2f b, Vector2f c, Vector2f d)
    {
        /* NOTE: this is not a complete solution to testing for line intersection.  Shortcuts
           are taken here since this method will only be used to test intersection of bounding
           box edges, and we know these are always either vertical or horizontal. */

        /* test to see which of the line segments are vertical */
        boolean firstIsVertical = (a.getX() == b.getX());
        boolean secondIsVertical = (c.getX() == d.getX());

        /* four cases: */
        if( firstIsVertical && secondIsVertical )
        {
            /* both are vertical */
            /* intersection if x values are equal AND either of first segment's y values fall between second segment's y values */
            return (a.getX() == c.getX()) && (valueBetween(a.getY(),c.getY(),d.getY()) || valueBetween(b.getY(),c.getY(),d.getY()));
        }
        else if( firstIsVertical )
        {
            /* only first is vertical */

            /* intersection if:
                    first segment's x value falls within column defined by second segment's x values
                      AND
                    second segment's y value falls within row defined by first segment's y values
            */
            return valueBetween(a.getX(),c.getX(),d.getX()) && valueBetween(c.getY(),a.getY(),b.getY());
        }
        else if( secondIsVertical )
        {
            /* only second is vertical */

            /* intersection if:
                    second segment's x value falls within column defined by first segment's x values
                      AND
                    first segment's y value falls within row defined by second segment's y values 
             */
            return valueBetween(c.getX(),a.getX(),b.getX()) && valueBetween(a.getY(),c.getY(),d.getY());
        }
        else
        {
            /* both are horizontal */
            /* intersection if y values are equal AND either of the first segment's x values fall between second segment's x values */
            return (a.getY() == c.getY()) && (valueBetween(a.getX(),c.getX(),d.getX()) || valueBetween(b.getX(),c.getX(),d.getX()));
        }
    }

    /**
       Test if a number is within a given range.  Test is inclusive so number is counted as in range if it is equal to one of the endpoints.  Endpoints may be specified in any order.
       @param x The number to test.
       @param first One end of the range.
       @param second The other end of the range.
       @return true if number is between, otherwise false.
       */
    private boolean valueBetween(float x, float first, float second)
    {
        return ( x >= first && x <= second ) || ( x <= first && x >= second);
    }



	/** checks if the specified widgets collided with eachother in the timWorld
	*   @return Boolean true if win condtion is met, false if it is not met yet
	*
	**/
	public boolean checkForWin ()
	{
		//updates the moving widget target
		setTargetWidget1Boundary();
        for(int i = 0; i < 4; i++)
        {
          	
			//check for intersection -> borrowed code from TimWorld intersection 
	 		for(int j = 0; j < 4; j++)
            {
                if(testLineIntersection( targetBoundary[i],
                                         targetBoundary[(i+1)%4],
                                         target1Boundary[j],
                                         target1Boundary[(j+1)%4]))
                {
                    return true;
                }

		
            
        }
	}
        return false;

		
		
	}
    
    /** Used to reset the win flag after a level is completed
     */
	public void resetWinFlag () {
        ConditionMet = false;
    }

}
