//
//  WidgetFactoryTest.java
//
//  Created by Group Four on 06/11/08.
//  Copyright 2008 Group Four.  All rights reserved.

package test.Widgets;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.Assert.*;

import Widgets.Widget;
import Widgets.*;

import javax.swing.ImageIcon;

public class WidgetFactoryTest extends TestCase
{
    /**
      Test pulling a widget out of the factory
      */
    @Test public void testWidgetCreation()
    {
        // Setup
        Widget mbox = null;
        Widget tv = null;
        Widget convey = null;
        Widget simple = null;
        Widget bomb = null;

        // Pre
        assertNull(mbox);
        assertNull(tv);
        assertNull(convey);
        assertNull(simple);
        assertNull(bomb);

        // Action Tested
        mbox = WidgetFactory.createWidget("MysteryBox");
        tv = WidgetFactory.createWidget("TVwidget");
        convey = WidgetFactory.createWidget("ConveyorBelt");
        simple = WidgetFactory.createWidget("SimpleBox");
        bomb = WidgetFactory.createWidget("BombWidget");

        // Post
        assertTrue(mbox instanceof Widgets.MysteryBox);
        assertTrue(tv instanceof Widgets.TVwidget);
        assertTrue(convey instanceof Widgets.ConveyorBelt);
        assertTrue(simple instanceof Widgets.SimpleBox);
        assertTrue(bomb instanceof Widgets.BombWidget);
    }

    @Test public void testIconRetrieval()
    {
        // Setup
        ImageIcon i = null;

        // Pre

        // Action Tested
        i = WidgetFactory.getIcon("MysteryBox");

        // Post
        assertNotNull(i);
        assertTrue(i instanceof ImageIcon);
    }
}
