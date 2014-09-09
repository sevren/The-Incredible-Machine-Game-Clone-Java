//
// SimpleBox.java
//
// Created on 17/10/08
// Copyright (c) 2008 GroupFour
//
package Widgets;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import net.phys2d.math.*;
import net.phys2d.raw.*;
import net.phys2d.raw.shapes.*;

/**
  Creates a simple

  @author Group Four
  */
public class SimpleBox implements Widget
{
    /* flags */
    private boolean locked = false;
    private boolean active = false;
    private boolean reset = false;

    /* walls */
    private Body box;
    
    /* basic dimension stuff */
    private final float width = 50.0f;
    private final float height = 50.0f;

    private Body[] bodies;
    private Joint[] joints;

    private ImageIcon icon;

    /**
     * Basic constructor
     */
    public SimpleBox()
    {
		box = new StaticBody(new Box(width, height));

        bodies = new Body[1];
        bodies[0] = box;

        joints = new Joint[0];

        /* set up imageicon */
        BufferedImage img = new BufferedImage((int)width,(int)height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D)img.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0,0,(int)width,(int)height);
        g.setColor(Color.BLACK);
        g.drawRect(0,0,(int)width-1,(int)height-1);
        for(int i = 5; i <= width; i += 5)
        {
            g.drawLine(0,i,i,0);
            g.drawLine(i,(int)height,(int)width,i);
        }

        icon = new ImageIcon(img);
    }

	/**
     * @see Widgets.Widget#setPosition
	 */
	public void setPosition(Vector2f f)
    {
        box.setPosition(f.getX() + width/2.0f, f.getY() + height/2.0f);
    }

	/**
     * @see Widgets.Widget#setPositionX
	 */
	public void setPositionX(float x)
    {
        this.setPosition(new Vector2f(x, getPositionY()));
    }

	/**
     * @see Widgets.Widget#setPositionY
	 */
	public void setPositionY(float y)
    {
        this.setPosition(new Vector2f(getPositionX(), y));
    }

	/**
     * @see Widgets.Widget#getPosition
	 */
	public Vector2f getPosition()
    {
        ROVector2f currentPos = box.getPosition();

        return new Vector2f( currentPos.getX() - width/2.0f, currentPos.getY() - height/2.0f);
    }

	/**
     * @see Widgets.Widget#getPositionX
	 */
	public float getPositionX()
    {
        return this.getPosition().getX();
    }

	/**
     * @see Widgets.Widget#getPositionY
	 */
	public float getPositionY()
    {
        return this.getPosition().getX();
    }

	/**
     * @see Widgets.Widget#draw
	 */
	public void draw (Graphics2D g)
    {
        /* 
           Method for getting translated points of box adapted from drawLineBody in:
           http://www.cokeandcode.com/phys2d/source/builds/src060408.zip
           test/net/phys2d/raw/test/AbstractDemo.java
           Line 328
           Author: Kevin Glass
         */
        Vector2f[] points = ((Box)box.getShape()).getPoints(box.getPosition(),box.getRotation());
   
        g.setColor(Color.BLACK);

        /*draw edges*/
        for(int i = 0; i <= 3; i++)
        {
        	g.drawLine(
        			(int)points[i].getX(),
    			    (int)points[i].getY(),
        			(int)points[(i+1)%4].getX(),
        			(int)points[(i+1)%4].getY());
        }

        /* draw hashes */
        int x1 = (int)points[0].getX();
        int y1 = (int)points[0].getY();
        int x2 = (int)points[2].getX();
        int y2 = (int)points[2].getY();
        for(int i = 5; i <= width; i += 5)
        {
            g.drawLine(x1,y1+i,x1+i,y1);
            g.drawLine(x2-i,y2,x2,y2-i);
        }
    }

	/**
     * @see Widgets.Widget#activateWidget
	 */
	public void activateWidget ()
    {
        if (reset == false)
        {
            /* refuse activation */
            return;
        }
        else
        {
            active = true;
        }
    }

	/**
     * @see Widgets.Widget#resetWidget
	 */
	public void resetWidget()
    {
        active = false;
        reset = true;
        /* TODO reset position + direction to remembered state if that's the idea from API? */
    }

	/**
     * @see Widgets.Widget#rotateClockwise
	 */
	public void rotateClockwise()
    {
        /* no concept of initial direction for a square box */
        return;
    }

	/**
     * @see Widgets.Widget#rotateCounterClockwise
	 */
    public void rotateCounterClockwise ()
    {
        /* no concept of initial direction for a square box */
        return;
    }

	/**
     * @see Widgets.Widget#setDirection
	 */
	public void setDirection(Direction d)
    {
        /* no concept of direction for a square box */
        return;
    }

	/**
     * @see Widgets.Widget#getDirection
	 */
	public Direction getDirection ()
    {
        return Widget.Direction.NORTH;
    }

	/**
     * @see Widgets.Widget#isActive
	 */
	public boolean isActive()
    {
        return active;
    }

	/**
     * @see Widgets.Widget#getName
	 */
	public String getName()
    {
        return new String("SimpleBox");
    }

	/**
     * @see Widgets.Widget#getType
	 */
	public ActionType getType()
    {
        return Widget.ActionType.STATIC;
    }

	/**
     * @see Widgets.Widget#reactToTouchingBody
	 */
	public void reactToTouchingBody(CollisionEvent e)
    {
        /* no special action needs to be taken by this widget in collisions */
        return;
    }

	/**
     * @see Widgets.Widget#isConnectable
	 */
	public boolean isConnectable()
    {
        /* not connectable */
        return false;
    }

	/**
     * @see Widgets.Widget#setConnectionPoints
	 */
	public void setConnectionPoints(Vector2f[] points)
    {
        /* not connectable */
        return;
    }

	/**
     * @see Widgets.Widget#isLocked
	 */
	public boolean isLocked()
    {
        return locked;
    }

	/**
     * @see Widgets.Widget#setLock
	 */
	public void setLock(boolean locked)
    {
        this.locked = locked;
    }

	/**
     * @see Widgets.Widget#attachJoint
	 */
	public Vector2f attachJoint (Vector2f point)
    {
        /* no connection points available */
        return null;
    }

	/**
     * @see Widgets.Widget#getBodiesForSimulation
	 */
	public Body[] getBodiesForSimulation ()
    {   
        return bodies;
    }

	/**
     * @see Widgets.Widget#getJointsForSimulation
	 */
	public Joint[] getJointsForSimulation ()
    {
        return joints;
    }

	/**
     * @see Widgets.Widget#receiveImpulse
	 */
	public void receiveImpulse(Vector2f anchor_point)
    {
        /* have no joints, nothing to activate */
        return;
    }

	/**
     * @see Widgets.Widget#getBoundary
	 */
	public Vector2f[] getBoundary()
    {
        /* assumption is boundary doesn't rotate with the widget and doesn't change size */
        
        Vector2f[] corners = new Vector2f[4];

        float x = this.getPosition().getX();
        float y = this.getPosition().getY();

        corners[0] = new Vector2f(x, y);
        corners[1] = new Vector2f(x + width, y);
        corners[2] = new Vector2f(x + width, y + height);
        corners[3] = new Vector2f(x, y + height);
        
        return corners;
        
    }

	/**
     * @see Widgets.Widget#getDescription
	 */
	public String getDescription ()
    {
        return new String("A simple static box.");
    }

	/**
     * @see Widgets.Widget#getIcon
	 */
	public ImageIcon getIcon()
    {
        return icon;
    }
}

