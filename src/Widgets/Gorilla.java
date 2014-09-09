//
// Gorilla.java
//
package Widgets;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
import java.lang.Object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

import javax.swing.ImageIcon;

import net.phys2d.math.*;
import net.phys2d.raw.*;
import net.phys2d.raw.shapes.*;

public class Gorilla implements Widget
{
    /* flags */
    private boolean locked;
    private boolean active;
    private boolean reset;

    /* Widget scroller icon */
    private ImageIcon icon;
    
    /*Smash animation*/
    private ImageIcon smashAnim;
    
    /* image */
    private BufferedImage img;

    /* walls */
    private Body gorillaBody;
    private Body[] bodies;

    /* corner joints */
    private FixedJoint topLeftJoint;
    private FixedJoint topRightJoint;
    private FixedJoint bottomLeftJoint;
    private FixedJoint bottomRightJoint;
    private Vector2f[] position;
    
    /* basic dimension stuff */
    private final float width = 45.0f;
    private final float height = 50.0f;
    
    private final float gorillaMass = 250.0f;
    private boolean anim = false;
    /**
     * Basic constructor
     */
    public Gorilla() {
        bodies = new Body[1];
        gorillaBody = new Body("Gorilla Body",new Box(width, height),gorillaMass);
        bodies[0] = gorillaBody;
        position = new Vector2f[4];
		for (int i = 0; i < 4; i++)
		{
			position[i] = new Vector2f();
		}
		setPosition(new Vector2f((float)0.0, (float)0.0));
        
        /* set flags */
        locked = false;
        active = false;
        reset = false;

        /* grab icon */
        icon = new ImageIcon(getClass().getClassLoader().getResource("resources/img/Gorilla.gif"),"Gorilla");
        smashAnim = new ImageIcon(getClass().getClassLoader().getResource("resources/img/GorillaSmash.gif"),"GorillaSmash");
        /* read in image for draw method */
        try {
            img = ImageIO.read(getClass().getClassLoader().getResource("resources/img/Gorilla.gif"));
        }
        catch(IOException e) {
            System.err.println("Error loading Gorilla img");
            img = null;
        }

    }


	/**
     * @see Widgets.Widget#setPosition
	 */
	public void setPosition(Vector2f f)
    {
        gorillaBody.setPosition(f.getX() + width/2.0f,f.getY() + height/2.0f);

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
        ROVector2f pos = gorillaBody.getPosition();
        return new Vector2f( pos.getX() - width/2.0f, pos.getY() - height/2.0f);
    }

	/**
     * @see Widgets.Widget#getPositionX
	 */
	public float getPositionX()
    {
        return (getPosition().getX());
    }

	/**
     * @see Widgets.Widget#getPositionY
	 */
	public float getPositionY()
    {
        return (getPosition().getY());
    }


    private void drawBoxBody(Body b, Graphics2D g)
    {
        /* Method for getting translated points of box adapted from drawLineBody in:
           http://www.cokeandcode.com/phys2d/source/builds/src060408.zip
           test/net/phys2d/raw/test/AbstractDemo.java
           Line 328
           Author: Kevin Glass
           */
        Vector2f[] points = ((Box)b.getShape()).getPoints(b.getPosition(),b.getRotation());
        
        for(int i = 0; i <= 3; i++)
        {
        	g.drawLine(
        			(int)points[i].getX(),
        			(int)points[i].getY(),
        			(int)points[(i+1)%4].getX(),
        			(int)points[(i+1)%4].getY());
        }

    }


	/**
     * @see Widgets.Widget#draw
	 */
	public void draw (Graphics2D g)
    {
        if (img != null) {
            AffineTransform xform = new AffineTransform();
           // xform.setToRotation(gorillaBody.getRotation());
            if(anim == false) {
                g.drawImage(
                        img,
                        new AffineTransformOp(xform, AffineTransformOp.TYPE_BILINEAR),
                        (int)this.getPosition().getX(),
                        (int)this.getPosition().getY());
            } else if (anim == true) {
                AffineTransform tfm = new AffineTransform();
            //    tfm.rotate(gorillaBody.getRotation());
                tfm.translate((int)this.getPosition().getX(), (int)this.getPosition().getY());
                g.drawImage(smashAnim.getImage(), tfm, null);
                
            }
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
        gorillaBody.setRotation( 0.0f );
        ROVector2f vel = gorillaBody.getVelocity(); /* seems to fix gorilla still
                                                       rotating after stop.  - Jason */
        gorillaBody.adjustVelocity( new Vector2f(0.0f/*vel.getX()*-1 */, /*vel.getY()*-1)*/0.0f) );
        gorillaBody.setForce(0.0f,0.0f);
        anim = false;
        active = false;
        reset = true;
        /* TODO reset position + direction to remembered state */
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
        return new String("Gorilla");
    }

	/**
     * @see Widgets.Widget#getType
	 */
	public ActionType getType()
    {
        return Widget.ActionType.BOUNCE;
    }

	/**
     * @see Widgets.Widget#reactToTouchingBody
	 */
	public void reactToTouchingBody(CollisionEvent ce)
    {
        anim = true;
        for(int i=0; i < bodies.length; i++) {
            if(ce.getBodyA() == bodies[0]) {
                Body b = ce.getBodyB();
                b.addForce(new Vector2f(100000,0));
            } else if(ce.getBodyB() == bodies[0]) {
                Body a = ce.getBodyA();
                a.addForce(new Vector2f(100000,0));
            }
        }
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
      //  Joint[] joints = new Joint[4];


        return null;
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
        corners[0] = new Vector2f(this.getPositionX(), this.getPositionY());
        corners[1] = new Vector2f(this.getPositionX() + width, this.getPositionY());
        corners[2] = new Vector2f(this.getPositionX() + width, this.getPositionY() + height);
        corners[3] = new Vector2f(this.getPositionX(), this.getPositionY() + height);
            
        return corners;
    }

	/**
     * @see Widgets.Widget#getDescription
	 */
	public String getDescription ()
    {
        return new String("A Gorilla! Get me Angry and me Smashy!");
    }

	/**
     * @see Widgets.Widget#getIcon
	 */
	public ImageIcon getIcon()
    {
        return icon;
    }
}

