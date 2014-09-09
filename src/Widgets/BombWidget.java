//Author: Cesar Chu
//Course: CIS3750
//Date: October 17 2008
//This BombWidget class is an item in the puzzle game "TIM"
//This will generally explode on impact and send other objects 
//near the bomb away from the bomb that exploded.

package Widgets;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

import net.phys2d.raw.shapes.Box;
import net.phys2d.math.*;
import net.phys2d.raw.*;

/**
 * This BombWidget is currently not completed
 * it's bare minimum has been put in,
 * it's forces after the collision has been set has not been defined
 * however the image after the detonation has been implemented
 *
 */

public class BombWidget implements Widget
{
    private final float width = 40;
    private final float height = 50;
	Vector2f[] position;
	boolean locked;
	boolean activated;
	boolean detonated;
	boolean resetted;
	Direction dir;
	Body[] b;
	ImageIcon imgicon;
	BufferedImage img;
	
	/**
	 * The no constructor BombWidget, this no constructor bombwidget 
	 * creates a bombwidget with initial position of 0,0 (top left)
	 * which is not locked, not resetted, not activated, and also
	 * has not been detonated yet
	 */
	public BombWidget()
	{
		b = new Body[8];
		Body top_left = new StaticBody(new Box(12, 15));
		b[0] = top_left;
		Body top_middle = new StaticBody(new Box(16, 15));
		b[1] = top_middle;
		Body top_right = new StaticBody(new Box(12, 15));
		b[2] = top_right;
		Body middle_right = new StaticBody(new Box(12, 20));
		b[3] = middle_right;
		Body bottom_right = new StaticBody(new Box(12, 15));
		b[4] = bottom_right;
		Body bottom_middle = new StaticBody(new Box(16, 15));
		b[5] = bottom_middle;
		Body bottom_left = new StaticBody(new Box(12, 15));
		b[6] = bottom_left;
		Body middle_left = new StaticBody(new Box(12, 20));
		b[7] = middle_left;

		position = new Vector2f[4];
		for (int i = 0; i < 4; i++)
		{
			position[i] = new Vector2f();
		}
		setPosition(new Vector2f((float)0.0, (float)0.0));
		locked = false;
		resetted = false;
		dir = Direction.NORTH;
		initializeBomb();
	}

	/*
	* Private method for our use, it initializes the bomb so that the image
	* is the actual image that it's supposed to be displaying
	*/
	private void initializeBomb()
	{
		imgicon = new ImageIcon(getClass().getClassLoader().getResource("resources/img/bomb.gif"));
		try
		{
			img = ImageIO.read(getClass().getClassLoader().getResource("resources/img/bomb.gif"));
		}
		catch(IOException e)
		{
			System.err.println("Error loading img");
			img = null;
		}
		for (int i = 0; i < 8; i++)
		{
			b[i].setEnabled(true);
		}
		activated = false;
		detonated = false;
	}

	/**
	 * Set the position of the BombWidget.
	 *
	 * @param f  The x,y coordinates of the top left 
	 *           x-y coordinates of the Widget within the container.
	 */
	public void setPosition(Vector2f f)
	{
		float x, y;
		x = f.getX();
		y = f.getY();
		b[0].setPosition(x + 6, (float)(y + 7.5));
		b[1].setPosition(x + 12 + 8, (float)(y + 7.5));
		b[2].setPosition(x + 12 + 16 + 6, (float)(y + 7.5));
		b[3].setPosition(x + 12 + 16 + 6, (float)(y + 15 + 10));
		b[4].setPosition(x + 12 + 16 + 6, (float)(y + 15 + 20 + 7.5));
		b[5].setPosition(x + 12 + 8, (float)(y + 15 + 20 + 7.5));
		b[6].setPosition(x + 6, (float)(y + 15 + 20 + 7.5));
		b[7].setPosition(x + 6, (float)(y + 15 + 10));
		position[0].set(x, y);
		position[1].set(x + 40, y);
		position[2].set(x + 40, y + 50);
		position[3].set(x, y + 50);
	}

	/**
	 * Set the top-left x-coordinate of the BombWidget.
	 * it is equivalent to setPosition(new Vector2f(x, getPositionY()));
	 *
	 * @param x  The x coordinate within the container.
	 */
	public void setPositionX(float x)
	{
		b[0].setPosition(x + 6, b[0].getPosition().getY());
		b[1].setPosition(x + 12 + 8, b[1].getPosition().getY());
		b[2].setPosition(x + 12 + 8 + 6, b[2].getPosition().getY());
		b[3].setPosition(x + 12 + 16 + 6, b[3].getPosition().getY());
		b[4].setPosition(x + 12 + 16 + 6,  b[4].getPosition().getY());
		b[5].setPosition(x + 12 + 8, b[5].getPosition().getY());
		b[6].setPosition(x + 6, b[6].getPosition().getY());
		b[7].setPosition(x + 6, b[7].getPosition().getY());
		position[0].set(x, position[0].getY());
		position[1].set(x + 40, position[1].getY());
		position[2].set(x + 40, position[2].getY());
		position[3].set(x, position[3].getY());

	}

	/**
	 * Set the top-left y-coordinate of the BombWidget.
	 *
	 * @param y  The y coordinate within the container.
	 */
	public void setPositionY(float y)
	{
		b[0].setPosition(b[0].getPosition().getX(), (float)(y + 7.5));
		b[1].setPosition(b[1].getPosition().getX(), (float)(y + 7.5));
		b[2].setPosition(b[2].getPosition().getX(), (float)(y + 7.5));
		b[3].setPosition(b[3].getPosition().getX(), (float)(y + 15 + 10));
		b[4].setPosition(b[4].getPosition().getX(), (float)(y + 15 + 20 + 7.5));
		b[5].setPosition(b[5].getPosition().getX(), (float)(y + 15 + 20 + 7.5));
		b[6].setPosition(b[6].getPosition().getX(), (float)(y + 15 + 20 + 7.5));
		b[7].setPosition(b[7].getPosition().getX(), (float)(y + 15 + 10));
		position[0].set(position[0].getX(), y);
		position[1].set(position[1].getX(), y);
		position[2].set(position[2].getX(), y + 50);
		position[3].set(position[3].getX(), y + 50);
	}

	/**
	 * Get the top-left position of this BombWidget.
	 *
	 * @return   The x,y coordinates within the container.
	 */
	public Vector2f getPosition()
	{
		return position[0];
	}

	/**
	 * Get the top-left x-coordinate of this BombWidget.
	 *
	 * @return   The x coordinate within the container.
	 */
	public float getPositionX()
	{
		return position[0].getX();
	}

	/**
	 * Get the top-left y-coordinate of this BombWidget.
	 *
	 * @return   The y coordinate within the container.
	 */
	public float getPositionY()
	{
		return position[0].getY();
	}

	/**
	 * Draws the widget.
	 * @param g  The graphic to draw onto.
	 */
	public void draw (Graphics2D g)
	{
		//g.drawImage(img, (int)getPositionX(), (int)getPositionY(), imgicon.getImageObserver());

		if (img != null)
		{
			// we read in image properly in constructor, use it

			// rotation transform
			AffineTransform xform = new AffineTransform();
			xform.setToRotation(b[0].getRotation());

			g.drawImage(
				img,
				new AffineTransformOp(xform, AffineTransformOp.TYPE_BILINEAR),
				(int)this.getPosition().getX(),
				(int)this.getPosition().getY());
		}
	}
    
    
	/**
	 * Activates the widget.
	 * refuses to become activated if the widget has not been reset yet.
	 * Must be called by the engine for every widget when the gameplay start.
	 */
	public void activateWidget ()
	{
		if (resetted)
		{
			activated = true;
		}
	}

	/**
	 * Resets the widget to start state.
	 * Should reinitialize everything to with all bodies associated with this.
	 * Should reinitialize its own internal state information as well.
	 * Ensure that the caller invokes this once before allowing
	 * activateWidget() to do something.
	 * Should probably call each body's set(Shape s, float mass) in here.
	 */
	public void resetWidget()
	{
		resetted = true;
		initializeBomb();
	}

	/**
	 * does nothing since a BombWidget is a directionless widget
	 */
	public void rotateClockwise()
	{
	}

	/**
	 * does nothing since a BombWidget is a directionless widget
	 */
	public void rotateCounterClockwise ()
	{
	}

	/**
	 * does nothing, since the BombWidget is a directionless widget
	 *
	 * @param d Direction to rotate the Widget
	 */
	public void setDirection(Direction d)
	{
	}

	/**
	 * Returns the Direction where the BombWidget is facing
	 * this is always going to be NORTH, since the BombWidget is a directionless widget
	 *
	 * @return the direction the BombWidget is facing (always going to be Direction.NORTH)
	 */
	public Direction getDirection ()
	{
		return Direction.NORTH;
	}

	/**
	 * Determines if the BombWidget is active.
	 *
	 * @return whether or not the widget is active.
	 */
	public boolean isActive()
	{
		return activated;
	}

	/**
	 * Gets the name of the widget. Which is "Bomb Widget"
	 *
	 * @return The name of this widget.
	 */
	public String getName ()
	{
		return "Bomb Widget";
	}

	/**
	 * Gets the type of the BombWidget
	 * Type of BombWidget is ActionType.EXPLOSION.
	 *
	 * @return always returns to be of ActionType.EXPLOSION since this is the category
	 *         the BombWidget
	 */
	public ActionType getType()
	{
		return ActionType.EXPLOSION;
	}

	/**
	 * What happens with the widget when two bodies touch.
	 *
	 * Ensure that the widget carefully handles
	 * collision events between its own bodies.
	 *
	 * @param e The collision event that was triggered by a collision.
	 */
	public void reactToTouchingBody(CollisionEvent e)
	{
		imgicon = new ImageIcon(getClass().getClassLoader().getResource("resources/img/boom.gif"));
		try
		{
			img = ImageIO.read(getClass().getClassLoader().getResource("resources/img/boom.gif"));
		}
		catch(IOException ex)
		{
			System.err.println("Error loading img");
			img = null;
		}
		if (!detonated)
		{
			if (e.getBodyA().equals(b[0]))
			{
				e.getBodyB().addForce(new Vector2f(-150000f, -150000f));
			}
			else if (e.getBodyA().equals(b[1])) 
			{
				e.getBodyB().addForce(new Vector2f(0f, -150000f));
			}
			else if (e.getBodyA().equals(b[2])) 
			{
				e.getBodyB().addForce(new Vector2f(15000f, -15000f));
			}
			else if (e.getBodyA().equals(b[3])) 
			{
				e.getBodyB().addForce(new Vector2f(15000f, 0f));
			}
			else if (e.getBodyA().equals(b[4])) 
			{
				e.getBodyB().addForce(new Vector2f(15000f, 15000f));
			}
			else if (e.getBodyA().equals(b[5])) 
			{
				e.getBodyB().addForce(new Vector2f(0f, 15000f));
			}
			else if (e.getBodyA().equals(b[6])) 
			{
				e.getBodyB().addForce(new Vector2f(-15000f, 15000f));
			}
			else if (e.getBodyA().equals(b[7])) 
			{
				e.getBodyB().addForce(new Vector2f(-15000f, 0f));
			}
			detonated = true;
			for (int i = 0; i < 8; i++)
			{
				b[i].setEnabled(false);
			}

		} // if it has already detonated else do nothing
	}

	/**
	 * Tell if the BombWidget can connect to joints or not.
	 *
	 * @return always returns false since this BombWidget is not connectable
	 */
	public boolean isConnectable()
	{
		return false;
	}

	/**
	 * Sets the points where joints will connect to this widget.
	 * If the widget is not connectable, do nothing.
	 *
	 * @param points The points within the widget that designate
	 * where joints connect to.
	 */
	public void setConnectionPoints(Vector2f[] points)
	{
	}

	/**
	 * Whether or not the BombWidget is locked and cannot be moved.
	 *
	 * @return If the widget is locked (true) or not (false).
	 */
	public boolean isLocked()
	{
		return locked;
	}

	/**
	 * Sets the lock state of the BombWidget.
	 * Lock state returned by isLocked should reflect the change.
	 *
	 * @param locked If the widget is to be locked (true) or not (false).
	 */
	public void setLock(boolean locked)
	{
		this.locked = locked;
	}

	/**
	 * This widget is not connectable so it will do nothing and return null
	 *
	 * @param point The x,y coordinates where the joint is attempting to connected to.
	 *
	 * @return returns null since this BombWidget does not have joints
	 */
	public Vector2f attachJoint (Vector2f point)
	{
		return null;
	}

	/**
	 * This will return the body of what the BombWidget uses
	 *
	 * @return An array of bodies.
	 */
	public Body[] getBodiesForSimulation ()
	{
		return b;
	}

	/**
	 * returns nothing since this BombWidget does not have any joints
	 *
	 * @return this is going to return null since this widget doesn't have joints
	 */
	public Joint[] getJointsForSimulation ()
	{
		return null;
	}

	/**
	 * does nothing since theres no joint connecting to this widget
	 *
	 * @param anchor_point The x,y coordinates of the connected Joint.
	 */
	public void receiveImpulse(Vector2f anchor_point)
	{
	}

	/**
	 * Gets the four boundary vertices of the BombWidget,
	 *
	 * @return An array of Vector2f which will be the four corners x,y coordinates.
	 */
	public Vector2f[] getBoundary()
	{	
	        return position;
	}

	/**
	 * Retrieves the description of the widget.
	 *
	 * @return The small blurb describing what the widget does.
	 */
	public String getDescription ()
	{
		return "This widget explodes on impact and will affect other objects around it when it explodes";
	}

	/**
	 * This returns the image that i made, which is 40x50
	 * depending if the bomb activated or not it will have an undetonated bomb
	 * or a detonated bomb
	 *
	 * @return The image which is linked with the widget.
	 */
	public ImageIcon getIcon ()
	{
		return imgicon;
	}
    
}
