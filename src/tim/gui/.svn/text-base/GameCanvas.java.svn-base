//
//  GameCanvas.java
//  
//
//  Created by Group Four on 07/11/08.
//  Copyright 2008 Group Four. All rights reserved.
//

package tim.gui;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;

import tim.gui.Drawable;
import tim.model.TimWorld;

import Widgets.Widget;

/**
  GameCanvas is an implementation of a swing canvas that draws the game world.

  The canvas will always draw all widgets that are fixed parts of the level, or that have been placed by the user.  An additional set of drawable objects can be added via the drawable interface.

    Doublebuffering strategy adapted from tutorial here:
    http://www.codeproject.com/KB/graphics/javadoublebuffer.aspx
    Author: Zizilamoroso
  */
public class GameCanvas extends Canvas
{
    private ArrayList<Drawable> drawables;

    private TimWorld world;

    private int bufferWidth;
    private int bufferHeight;
    private Image bufferImage;
    private Graphics bufferGraphics;

	/**
	 * Constructor, makes a drawable area that reflects
	 * the changes made to TimWorld.
	 * @param world The object that has things needed to be drawn
	 * to the display.
	 */
    public GameCanvas(TimWorld world)
    {
        drawables = new ArrayList<Drawable>();
        this.world = world;
        
        bufferWidth = 1;
        bufferHeight = 1;
        bufferImage = null;
        bufferGraphics = null;

        /* make the background cyan */
        this.setBackground(Color.cyan);
    }

	/**
	 * Updates the playfield to reflect any changes made
	 * to the referenced TimWorld object.
	 */
    public void update(Graphics g)
    {
        paint(g);
    }

	/** The draw method, displays the objects from TimWorld. */
    public void paint(Graphics g)
    {
        /* check for any change in canvas size */
        if(bufferWidth != this.getSize().width
                || bufferHeight != this.getSize().height
                || bufferImage == null
                || bufferGraphics == null)
        {
            /* set new sizes */
            bufferWidth = this.getSize().width;
            bufferHeight = this.getSize().height;

            /* wipe buffer */
            if(bufferGraphics != null)
            {
                bufferGraphics.dispose();
                bufferGraphics = null;
            }
            if(bufferImage != null)
            {
                bufferImage.flush();
                bufferImage = null;
            }
            
            /* garbage collection */
            System.gc();
    
            /* recreate buffer */
            bufferImage = createImage(bufferWidth, bufferHeight);
            bufferGraphics = bufferImage.getGraphics();
        }

        if(bufferGraphics != null)
        {
            /* clear buffer */
            bufferGraphics.clearRect(0,0,bufferWidth,bufferHeight);

            /* draw all widgets */
            for(Widget w : world.getWidgets())
            {
                //if (w.getType() != Widget.ActionType.STATIC)
                    w.draw((Graphics2D)bufferGraphics);
            }
    
            /* draw all other drawables */
            for(Drawable d : drawables)
            {
                d.draw((Graphics2D)bufferGraphics);
            }

            g.drawImage(bufferImage,0,0,this);
        }
    }

    /**
      Register a drawable object to be painted when the canvas repaints.
      @param d The drawable to add.
      */
    public void addDrawable(Drawable d)
    {
        /* only add to the arraylist if its not already in there */
        if(!drawables.contains(d))
            drawables.add(d);
    }

    /**
      Unregister a previously registered drawable object.
      @param d The drawable to remove.
      */
    public void removeDrawable(Drawable d)
    {
        drawables.remove(d);
    }

}
