//
//  TimWorld.java
//  
//
//  Created by Group Four on 23/09/08.
//  Copyright 2008 Group Four. All rights reserved.
//
package tim.model;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.ArrayList;

import net.phys2d.raw.Body;
import net.phys2d.raw.Joint;

import net.phys2d.raw.World;
import net.phys2d.raw.strategies.QuadSpaceStrategy;

import net.phys2d.raw.CollisionEvent;
import net.phys2d.raw.CollisionListener;

import net.phys2d.math.Vector2f;
import net.phys2d.math.ROVector2f;

import Widgets.Widget;
import tim.game.*;

/**
    TODO: complete comments
    TimWorld handles the physics world and all of the widgets it currently
    contains.  Methods are provided for changing properties of the world,
    and for adding/removing/manipulating widgets.
 */
public class TimWorld implements CollisionListener 
{
    /* game world */
    private final static float GRAVITY = 100;
    private World world;
    
    /* list of widgets in the world */
    private HashSet<Widget> widgets;

    /* body -> widget lookup for faster collision event handling */
    private Hashtable<Body,Widget> bodyToWidget;
  
    /* widget state storage table */
    private Hashtable<Widget,WidgetState> widgetStates;

    /* flag to indicate world is prepared for stepping */
    private boolean prepared;

    /** 
       Simple inner class for storing widget position/direction.  Used in widget state table.
     */
    private class WidgetState
    {
        private Widget.Direction direction;
        private Vector2f position;
        
        /**
          Basic constructor.

          @param direction Direction of widget.
          @param position Position of widget.
          */
        public WidgetState(Widget.Direction direction, Vector2f position)
        {
            this.direction = direction;
            this.position = position;
        }
        
        /**
          Get stored direction.

          @return Returns stored direction.
          */
        public Widget.Direction getDirection()
        {
            return direction;
        }
        
        /**
          Get stored position.

          @return Returns stored position.
          */
        public Vector2f getPosition()
        {
            return position;
        }
    }

    /**
      Basic constructor.
      */
	public TimWorld() 
    {
        /* set up phys2d world and add this as a listener */
        world = new World(new Vector2f(0.0f, GRAVITY), 100, new QuadSpaceStrategy(20, 5));
        world.addListener(this);

        /* create sets */
        widgets = new HashSet<Widget>();
        bodyToWidget = new Hashtable<Body,Widget>();
        widgetStates = new Hashtable<Widget,WidgetState>();

        /* prepared flag to false */
        prepared = false;
	}
  
    /**
      Process collision events.
      (part of CollisionListener interface)

      Method will notify any widgets and wincondition testers that might be concerned with the CollisionEvent.
      @param ce A collision event that has taken place.
      */
    public void collisionOccured(CollisionEvent ce)
    {
        /* handle widget collision */
        Widget a = bodyToWidget.get(ce.getBodyA());
        Widget b = bodyToWidget.get(ce.getBodyB());

        /* if both widgets were found, and collision wasn't between two bodies of the same widget */
        if(a != null && b != null && a != b)
        {
            a.reactToTouchingBody(ce);
            b.reactToTouchingBody(ce);
        }
        
        
        /*  
            TODO: pass collision event to win condition tester 
                    -> where is the testWin method available from?
                    -> fire this collision event to the tester as an event? 
           
            (unless wintester interface changes and registers itself as its own collision listener)
         */
        
        
        
        
    }

    /**
      Returns list of widgets in the game world.
      @return HashSet containing the widgets.
      */
    public HashSet<Widget> getWidgets()
    {
        return widgets;
    }

    /**
      Set gravity in the world.
      This method takes only a y component as most gravity will be downward-only.  The x component of the gravity vector will be set to 0.
      @param y 
      */
    public void setGravity(float y)
    {
        this.setGravity(new Vector2f(0.0f, y));
    }

    /**
      Set gravity in the world.
      @param v Vector describing vector to be used for the gravity.
      */
    public void setGravity(ROVector2f v)
    {
        this.setGravity(v.getX(),v.getY());
    }

    /**
      Set gravity in the world.
      @param x X component of the gravity.
      @param y Y component of the gravity.
      */
    public void setGravity(float x, float y)
    {
        world.setGravity(x,y);
    }

    /**
      Add a collision listener for the game world.
      Wraps the world's addListener method.
      @param cl The CollisionListener.
      */
    public void addCollisionListener(CollisionListener cl)
    {
        world.addListener(cl);
    }

    /**
      Remove a collision listener from the game world.
      Wraps the world's removeListener method.
      @param cl The CollisionListener.
      */
    public void removeCollisionListener(CollisionListener cl)
    {
        world.removeListener(cl);
    }

    /**
      Tests if a widget can be placed at given coords without overlapping other
      already placed widgets.

      @param widget the widget to be placed
      @param x X coordinate to place at
      @param y Y coordinate to place at
      @return true for valid position (no overlaps), false for invalid
      */
    public boolean testPlacement(Widget widget, float x, float y)
    {
        /* 
           Three tests to check for conditions that could invalidate the placement position:
            1. check bounding box against other widgets for edge intersections
            2. check if any of the widget's corners fall within other widgets
            3. check if any other widgets' corners fall within this widget
          */

        /* Need to set up array containing bounding box of widget but translated to target coords */

        /* get the boundary of the widget */
		Vector2f[] corners = widget.getBoundary();
	
        /* get width and height (compare x/y of bottom right corner vs top left) */
		float width = corners[2].getX() - corners[0].getX();
		float height = corners[2].getY() - corners[0].getY();
		
        /* testCorners will be the same boundary corners, only translated to the new location being tested */
		Vector2f[] testCorners = new Vector2f[4];
		
        /* use x,y,width,height to fill in testCorners - kept in same clockwise-from-top-left order as boundaries normally are */
		testCorners[0] = new Vector2f(x,y);
		testCorners[1] = new Vector2f(x+width,y);
		testCorners[2] = new Vector2f(x+width,y+height);
		testCorners[3] = new Vector2f(x,y+height);

        /* now compare against all other widgets */
        for(Widget w : widgets)
        {
            /* make sure we don't compare widget against itself */
            if(w != widget)
            {
                /* check edge intersections */
                if(boundingBoxEdgesIntersect(testCorners, w.getBoundary()))
                {
                    /* overlap detected, return false for invalid */
                    return false;
                }

                /* check for this widget's corners inside other widgets */
                for(int i = 0; i < 4; i++)
                {
                    if(boundsContainsPoint(w.getBoundary(),testCorners[i]))
                    {
                        /* point is inside, return false for invalid */
                        return false;
                    }
                }
                
                /* check for other widgets' corners inside this widget */
                for(int i = 0; i < 4; i++)
                {
                    if(boundsContainsPoint(testCorners,w.getBoundary()[i]))
                    {
                        /* point is inside, return false for invalid */
                        return false;
                    }
                }
            }
        }

        /* no overlaps, return true for valid position */
        return true;
    }

    /**
      Test if a bounding box contains a point.

      @param bounds The bounding box.
      @param point The point.
      @return True if point is contained, false otherwise.
      */
    private boolean boundsContainsPoint(Vector2f[] bounds, Vector2f point)
    {
        if ( (point.getX() > bounds[0].getX() && point.getX() < bounds[2].getX()) //point is in same column
              &&
             (point.getY() > bounds[0].getY() && point.getY() < bounds[2].getY())) //point is in same row
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    /**
      Test for intersection between two bounding box corner sets.
      The corners in the array arguments should be entered clockwise from top-left.
      @param a The first set of corners.
      @param b The second set of corners.
      @param return True if intersecting, otherwise false.
      */
    private boolean boundingBoxEdgesIntersect(Vector2f[] a, Vector2f[] b)
    {
        /* 
           Line segments in a are:
             a[0] -> a[1]
             a[1] -> a[2]
             a[2] -> a[3]
             a[3] -> a[0]

           Each must be tested against all segments in b.
           */
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                if(testLineIntersection( a[i],
                                         a[(i+1)%4],
                                         b[j],
                                         b[(j+1)%4]))
                {
                    return true;
                }
            }
        }

        return false;
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

    /**
      Returns any widget whose boundary contains the point described by the given vector.

      @param v The point to test.
      @return The first widget found whose bounding box contains the point, or null if none found.
      */
    private Widget getIntersectingWidget(ROVector2f v)
    {
        return getIntersectingWidget(v.getX(), v.getY());
    }

    /**
      Returns any widget whose boundary contains the point described by the given x,y coords.

      @param x The x coord.
      @param y The y coord.
      @return The first widget found whose bounding box contains the point, or null if none found.
      */
    private Widget getIntersectingWidget(float x, float y)
    {
        HashSet<Widget> list = new HashSet<Widget>();

        for(Widget w : widgets)
        {
            if(boundsContainsPoint(w.getBoundary(),new Vector2f(x,y)))
            {
                return w;
            }
        }

        return null;
    }

    /**
        Return the widget whose bounding box contains the given x,y coords.

        To be used to aid mouse interaction.

        @param x The x coord of a mouse click.
        @param y The y coord of a mouse click.
        @return The widget at specified coords, or null if none is found.
      */
    public Widget grabWidget(float x, float y)
    {
        return getIntersectingWidget(x,y);
    }

    /**
      Add a widget to the world.

      This method is to be used when adding widgets to the world whose positions have been set ahead of time.  No checking for overlaps against existing widgets will be done.

      @param widget The widget to add.
      @return True for success, false for failure or if widget is already in the world.
      */
    public boolean addWidget(Widget widget)
    {
        if(widgets.contains(widget))
        {
            /* widget has already been added */
            return false;
        }

        widgets.add(widget);
        
        /* make sure the widget has some bodies */
        if(widget.getBodiesForSimulation() != null) {
            /* add widget's bodies to world */
            for( Body b : widget.getBodiesForSimulation())
            {
                world.add(b);
            }
        }

        /* make sure the widget has some joints */ 
        if(widget.getJointsForSimulation() != null) {
            /* add widget's joints to world */
            for( Joint j : widget.getJointsForSimulation())
            {
                world.add(j);
            }
        }

        return true;
    }

    /**
      Place a widget at a given location in the world.

      If placement position is valid, following work will be done:
      <br><pre>
      - widget's bodies + joints will be added to the game world
      - widget will be added to widgets list (if not already there)
      - widget's position will be set to the given coords
      - widget will be added to widgets list if it wasn't there already
      - bodies/joints will be added to world if they weren't already there
      </pre>

      If placement position is not valid, nothing will be done.
      
      @param widget the widget to be placed
      @param x X coordinate to place at
      @param y Y coordinate to place at
      @return true for success, false for failure
      */
    public boolean moveWidget(Widget widget, float x, float y)
    {
        if (!testPlacement(widget,x,y))
        {
            /* bad position, return false */
            return false;
        }

        /* set the position */
        widget.setPosition(new Vector2f(x,y));

        /* add the widget in case it wasn't in the list, this handles bodies */
        addWidget(widget);

        /* success! */
        return true;
    }

    /** 
      Remove a widget from the world.

      It will be removed from widgets list and all of its bodies and
      joints will be removed from world.

      @param widget The widget to remove.
      @return true for success, false for failure
      */
    public boolean removeWidget(Widget widget)
    {
        /* make sure widget is in the world */
        if (!widgets.contains(widget))
        {
            /* widget wasn't in widgets list, return false for failure */
            return false;
        }

        /* remove widget's bodies from world */
		if(widget.getBodiesForSimulation() != null)
		{
			for( Body b : widget.getBodiesForSimulation())
			{
				world.remove(b);
			}
		}

        /* remove widget's joints from world */
		if(widget.getJointsForSimulation() != null)
		{
			for( Joint j : widget.getJointsForSimulation())
			{
				world.remove(j);
			}
		}
        
        /* remove from widgets list */
        widgets.remove(widget);

        return true;
    }

    /**
      Remove all widgets from the world.

      All widgets removed from the widget list and references discarded.
      All bodies/joints/etc removed from world.
      */
    public void clear()
    {
        widgets.clear();
        world.clear();
    }

    /**
      Prepare the game world to be run.

      ** NOTE ** This method should always be called once each time the game world is about to be run.

      <pre>
      - all widgets will have their reset() and activate() methods called per the widget API.
      - positional + direction data for each widget will be stored
      - lookup table of body->widget associations will be built to improve collisionListener performance
      </pre>
      */
    public void prepare()
    {
        /* clear tables */
        bodyToWidget.clear();
        widgetStates.clear();
        
        /* loop through wigets */
        for(Widget w : widgets)
        {
            /* reset and activate */
            w.resetWidget();
            w.activateWidget();

            /* store bodies for collision detection */
            for(Body b : w.getBodiesForSimulation())
            {
                bodyToWidget.put(b,w);
            }

            widgetStates.put(w, new WidgetState(w.getDirection(),w.getPosition()));
        }

        prepared = true;
    }

    /**
      Resets all widgets in the world to their starting states, locations, and directions.
      
      Position state and direction are both reset to state stored during previous prepare.
      All widgets have their reset methods called.
      Widget velocities will be reset to zero.
     */
    public void reset()
    {
        prepared = false;

        for(Widget w : widgets)
        {
            /* reset velocities of all widgets to 0 
               do this by adjusting with the negative of their current values, no explicit setVelocity available */
            for(Body b : w.getBodiesForSimulation())
            {
                b.adjustVelocity( new Vector2f(-1 * b.getVelocity().getX(), -1 * b.getVelocity().getY()));
                b.adjustAngularVelocity(-1 * b.getAngularVelocity());
            }
            
            /* reload position/direction from state */
            WidgetState state = widgetStates.get(w);
            if(state == null)
            {
                /* TODO: error... */
            }
            else
            {
                w.setPosition(state.getPosition());
                w.setDirection(state.getDirection());
            }

            /* reset widget */
            w.resetWidget();
        }
    }

    /**
      Steps the physics world forward one increment.

      Wrapped to be used in place of getWorld().step() in case extra work needs to be added per-world step to play nicely with Widget API.
      */
    public void step()
    {
        /* TODO: possibly prevent stepping if prepared == false */
        world.step();
    }

    /**
      String identifier for event system.
      @return String identifier for this class.
      */
    public String toString() {
        return TimEvent.TIMWORLD;
    }
}
