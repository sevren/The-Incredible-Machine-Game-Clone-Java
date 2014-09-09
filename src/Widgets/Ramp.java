//
//  Ramp.java
//  
//
//  Created by Quincy Jermyn on 17/10/08.
//  Copyright 2008 __MyCompanyName__. All rights reserved.
//
package Widgets;

import java.awt.*;
import net.phys2d.raw.*;
import net.phys2d.raw.shapes.Box;
import net.phys2d.math.*;
import java.awt.Graphics2D;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

import javax.swing.ImageIcon;

/**
 * Ramp Widget used to bridge graps or support a rolling ball.
 * The Ramp can be pointing EAST or WEST. 
 * Custom sizes can be set in the custom constructor.
 */
public class Ramp implements Widget {
    private Body rampBody;
    private String NAME = "Ramp";

    private float width;
    private float height;
    private boolean locked;
    private boolean activated;
    private boolean reset;
    private float appForce;
    
    private Direction direc;
    private ActionType acType;
    private Body[] simBodies;
    private Joint[] simJoints;
    private String descr;
    private Vector2f[] bounds;
    private ImageIcon icon;
    private BufferedImage bimg;
    private Vector2f[] position;

    /**
     * Basic Contructor, gives a 50 by 25 ramp
     */
    public Ramp() {
        width  = 75;
        height = 20;
        rampBody = new Body(NAME, new Box(width,height), Body.INFINITE_MASS);
        rampBody.setPosition(0.0f,0.0f);
        position = new Vector2f[4];
		for (int i = 0; i < 4; i++) {
			position[i] = new Vector2f();
		}
        setPosition(new Vector2f((float)0.0, (float)0.0));
        rampBody.setRotation((float)Math.PI/6);
        simBodies = new Body[1];
        simBodies[0] = rampBody;
        simJoints = new Joint[0];
        activated = false;
        reset = false;
        appForce = 0;
        direc = Widget.Direction.EAST;
        acType = Widget.ActionType.STATIC;
        locked = false;
        descr = "Ramp Widget used to bridge graps or support a rolling ball";

        /* grab icon */
        icon = new ImageIcon(getClass().getClassLoader().getResource("resources/img/Ramp.gif"),"Ramp");
        
        /* read in image for draw method */
        try {
            bimg = ImageIO.read(getClass().getClassLoader().getResource("resources/img/Ramp.gif"));
        }
        catch(IOException e) {
            System.err.println("Error loading src/Widgets/img/Ramp.gif");
            bimg = null;
        }
        
    }
    
	/**
	 * Set the position of the widget.
	 * @param f  The x,y coordinates within the 
	 * container.
	 */
	public void setPosition(Vector2f f) {
        rampBody.setPosition(f.getX() + width/2, f.getY() + height/2.0f);
        position[0].set((ROVector2f) f);
		position[1].set(f.getX() + width, f.getY());
		position[2].set(f.getX() + width, f.getY() + height);
		position[3].set(f.getX(), f.getY() + height);
    }
	
	/**
	 * Set the x-coordinate of the widget.
	 * @param x The x coordinate within the container.
	 */
	public void setPositionX(float x) {
        rampBody.setPosition(x + width/2, getPosition().getY());
      	position[0].set(x, position[0].getY());
		position[1].set(x + width, position[1].getY());
		position[2].set(x + width, position[2].getY());
		position[3].set(x, position[3].getY());
    }
	
	/**
	 * Set the y-coordinate of the widget.
	 * @param y The y coordinate within the container.
	 */
	public void setPositionY(float y) {
        rampBody.setPosition(getPosition().getX(), y);
       	position[0].set(position[0].getX(), y);
		position[1].set(position[1].getX(), y);
		position[2].set(position[2].getX(), y + height);
		position[3].set(position[3].getX(), y + height);
    }
    
	/**
	 * Get the position of the widget.
	 * @return   The x,y coordinates within the container.
	 */
	public Vector2f getPosition()
    {
        ROVector2f pos = rampBody.getPosition();
        return position[0];
     //   return new Vector2f( pos.getX() - width/2.0f, pos.getY() - height/2.0f);
    }
    
	
	/**
	 * Get the x-coordinate of the widget.
	 * @return   The x coordinate within the container.
	 */
	public float getPositionX() {
        return position[0].getX();  
    }
	
	/**
	 * Get the x-coordinate of the widget.
	 * @return   The x coordinate within the container.
	 */
	public float getPositionY() {
        return position[0].getX();
    }
    
	/**
     * Draws the widget.
	 * @param g  The object that draws things.
     * 
	 */
	public void draw (Graphics2D g) {
        
        if (bimg != null) {
            // rotation transform
            AffineTransform xform = new AffineTransform();
            xform.setToRotation(rampBody.getRotation());
            
            if(direc == Widget.Direction.EAST) {
                g.drawImage(bimg,
                    new AffineTransformOp(xform, AffineTransformOp.TYPE_BILINEAR),
                    (int)this.getPosition().getX() + (int)height/2,
                    (int)this.getPosition().getY() - (int)height);
            }else if(direc == Widget.Direction.WEST) {
                g.drawImage(bimg,
                    new AffineTransformOp(xform, AffineTransformOp.TYPE_BILINEAR),
                    (int)this.getPosition().getX() + (int)height/2-(int)height/2,
                    (int)this.getPosition().getY() + (int)height);
            }
        }

    }
    
	/**
	 * Activates the widget.
	 */
	public void activateWidget () {
        reset = false;
        activated = true;
    }
	
	/**
     * @see Widgets.Widget#resetWidget
	 */
	public void resetWidget() {
        if(direc == Widget.Direction.WEST)
            rampBody.setRotation(-(float)Math.PI/6);
        else if(direc == Widget.Direction.EAST)
            rampBody.setRotation((float)Math.PI/6);
        reset = true;
        activated = false;
    }
    
	/**
	 * Retrieves the power of the force that it
	 * applies to other widgets, if applicable.
	 * @return Power of the force.
	 */
	public float getAppliedForce() {
        return appForce; 
    }
    
	/**
	 * Sets the power of the force that it
	 * applies to other widgets, if applicable.
	 * @param power Power of the force.
	 */
	public void setAppliedForce(float power) {
        appForce = power;
    }
    
	/**
     * @see Widgets.Widget#rotateClockwise
	 */
	public void rotateClockwise() {
        return;
    }
	
	/**
     * @see Widgets.Widget#rotateCounterClockwise
	 */
	public void rotateCounterClockwise () {
        return;
    }
    
	/**
     * @see Widgets.Widget#setDirection
	 */
    public void setDirection(Direction d)  {
        if(d == Widget.Direction.WEST) {
            direc = d;
            rampBody.setRotation(-(float)Math.PI/6);
        }
        else if(d == Widget.Direction.EAST) {
            direc = d;
            rampBody.setRotation((float)Math.PI/6);
        }
        return;
    }
	
	/**
     * @see Widgets.Widget#getDirection
	 */
	public Direction getDirection ()  {
        return direc;
    }
    
	/**
	 * Determines if the widget is active.
	 * @return whether or not the widget is active.
	 */
	public boolean isActive()  {
        return activated;
    }
    
	/**
	 * Sets the name of the widget.
	 * @param name Widget name
	 */
	public void setName(String name)  {
        NAME = name;
    }
    
	/**
	 * Gets the name of the widget.
	 * @return The name of the widget.
	 */
	public String getName ()  {
        return NAME;
    }
    
	/**
	 * Gets the type of the widget. This should hopefully
	 * be part of ActionType.
	 * @return The ActionType that the widget is categorized as.
	 */
	public ActionType getType()  {
        return acType;
    }
	
	/**
	 * Set the type of the widget. This should hopefully be
	 * part of ActionType.
	 * @param type The ActionType the widget is categorized as.
	 */
	public void setType(ActionType type)  {
        acType = type;
    }
	
	/**
	 * What happens with the widget when another widget touches it.
	 * @param other_body The body that touched it.
	 * @param f The x,y coordinate that is the contact point.
	 */
	public void anotherBodyTouching(Body other_body, Vector2f f)  {
    }
    
    /**
     * @see Widgets.Widget#reactToTouchingBody
	 */
	public void reactToTouchingBody(CollisionEvent e)
    {
        /*No action in collisions*/
        return;
    }
    
    /**
     * @see Widgets.Widget#isConnectable
	 */
	public boolean isConnectable() {
        return false;
    }
    
    
	/**
     * @see Widgets.Widget#setConnectionPoints
     * Note: Ramp Widget can't be connected
	 */
	public void setConnectionPoints(Vector2f [] points)  {
        return;
    }
	
	/**
     * @see Widgets.Widget#isLocked
	 */
	public boolean isLocked() {
        return locked;
    }
	
	/**
     * @see Widgets.Widget#setLock
	 */
	public void setLock(boolean locked) {
        this.locked = locked;
    }
    
	/**
     * @see Widgets.Widget#attachJoint
	 */
	public Vector2f attachJoint (Vector2f point) {
        return new Vector2f(0.0f,0.0f);
    }
    
	/**
	 * Applies a force to the body.
	 * @param other_body The body that is to be affected.
	 */
	public void applyForce(Body other_body) {
        return;
    }
    
	/**
     * @see Widgets.Widget#getBodiesForSimulation
	 */
	public Body [] getBodiesForSimulation ()  {
        return simBodies;
    }
	
	/**
     * @see Widgets.Widget#getJointsForSimulation
	 */
	public Joint [] getJointsForSimulation () {
        return simJoints;
        
    }
	
	/**
     * @see Widgets.Widget#receiveImpulse
	 */
    
	public void receiveImpulse(Vector2f anchor_point) {
        return;
    }
	
	
	/**
	 * Sets a rectangular area around the widget, should be used
	 * as a way to detect overlapping widgets.
	 * @param bounds The four corners of the boundary.
	 */
	public void setBoundary(Vector2f [] bounds) {
        this.bounds = bounds;
        return;
    }
	
    
	/**
     * @see Widgets.Widget#getBoundary
	 */
	public Vector2f [] getBoundary() {
        Vector2f[] corners = new Vector2f[4];
       
        corners[0] = new Vector2f(position[0].getX(), position[0].getY()-height);
        corners[1] = new Vector2f(position[0].getX() +width, position[0].getY()-height);
        corners[2] = new Vector2f(position[0].getX() +width, position[0].getY() +width - (height*2));
        corners[3] = new Vector2f(position[0].getX(), position[0].getY() + width - (height*2));
        
	   return corners;
    }
	
	/**
	 * Sets the description of the widget.
	 * @param desc Small blurb describing what the widget does.
	 */
    public void setDescription (String desc) {
        descr = desc;
    }
    
	/**
     * @see Widgets.Widget#getDescription
	 */
    public String getDescription () {
        return descr;
    }
    
    
    /**
     * @see Widgets.Widget#getIcon
	 */
	public ImageIcon getIcon()
    {
        return icon;
    }
}
