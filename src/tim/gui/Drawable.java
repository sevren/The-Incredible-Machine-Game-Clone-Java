//
//  TIMDrawable.java
//  
//
//  Created by Group Four on 07/11/08.
//  Copyright 2008 Group Four. All rights reserved.
//

package tim.gui;

import java.awt.Graphics2D;

/**
  Classes implementing the drawable interface contain a method
  to draw themselves onto a canvas using a Graphics2D object
  */
public interface Drawable
{
    /**
      Draw to a canvas using the provided Graphics2D instance
      @param g The graphics2D object.
      */
    public void draw(Graphics2D g);
}

