
//	WidgetScrollerTest.java
//
//
//	Created by Group Four on 20/10/08
//	Copyright 2008 Group Four. All rights reserved.
//

package test.gui;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.Assert;

import tim.gui.WidgetScroller;
import Widgets.*;

public class WidgetScrollerTest extends TestCase
{

/**  WidgetScroller.addWidget */
	@Test public void test_addWidget()
	{
		//Setup
		WidgetScroller ws = new WidgetScroller();
		//Pre
		assertEquals( 0, ws.getNumWidgets() );
		//Action Tested
		ws.addWidget("CCBox", 2);
		//Post
		assertEquals( 1, ws.getNumWidgets() );
		//Action Tested
		ws.addWidget("Gorilla", 3);
		//Post
		assertEquals( 2, ws.getNumWidgets() );
	}
	
	
/**  WidgetScroller.clearWidgets */
	@Test public void test_clearWidgets()
	{
		//Setup
		WidgetScroller ws = new WidgetScroller();
		ws.addWidget("CCBox", 1);
		ws.addWidget("Gorilla", -1);
		//Pre
		assertEquals( 2, ws.getNumWidgets() );
		//Action Tested
		ws.clearWidgets();
		//Post
		assertEquals( 0, ws.getNumWidgets() );
	}

	
/**  WidgetScroller.decreaseWidget */
	@Test public void test_decreaseWidget()
	{
		//Setup
		WidgetScroller ws = new WidgetScroller();
		ws.addWidget("CCBox", 5);
		
		//Pre
		assertEquals( 5, ws.getWidgetCount("CCBox") );
		//Action Tested
		ws.decreaseWidget("CCBox");
		//Post
		assertEquals( 4, ws.getWidgetCount("CCBox") );

		ws.addWidget("Gorilla", -1);
		assertEquals( -1, ws.getWidgetCount("Gorilla") );
		//Action Tested
		ws.decreaseWidget("Gorilla");
		//Post
		assertEquals( -1, ws.getWidgetCount("Gorilla") );
	}


/**  WidgetScroller.getWidgetCount */
	@Test public void test_getWidgetCount()
	{
		//Setup
		WidgetScroller ws = new WidgetScroller();
		
		//Post
		ws.addWidget("CCBox", 5);
		assertEquals( 5, ws.getWidgetCount("CCBox") );
		
		ws.addWidget("Gorilla", -1);
		assertEquals( -1, ws.getWidgetCount("Gorilla") );
		
		ws.addWidget("SimpleBox", 37);
		assertEquals( 37, ws.getWidgetCount("SimpleBox") );
	}
	

/**  WidgetScroller.getWidgetAt */
	@Test public void test_getWidgetAt()
	{
		//Setup
		WidgetScroller ws = new WidgetScroller();
		
		//Action Tested
		ws.addWidget("CCBox", 5);
		ws.addWidget("Gorilla", -1);
		ws.addWidget("SimpleBox", 37);
		
		//Post
		assertEquals( "CCBox", ws.getWidgetAt(50f, 20f) );
		assertEquals( "Gorilla", ws.getWidgetAt(150f, 20f) );
		assertEquals( "SimpleBox", ws.getWidgetAt(250f, 20f) );
	}


/**  WidgetScroller.getNumPages */
	@Test public void test_getNumPages()
	{
		//Setup
		WidgetScroller ws = new WidgetScroller();
		//Pre
		assertEquals( 1, ws.getNumPages() );
		//Action Tested
		ws.addWidget("CCBox", 1);
		//Post
		assertEquals( 1, ws.getNumPages() );
		//Action Tested
		ws.addWidget("Gorilla", 1);
		ws.addWidget("SimpleBox", 1);
		ws.addWidget("ConveyorBelt", 1);
		ws.addWidget("Ramp", 1);
		ws.addWidget("MysteryBox", 1);
		//Post
		assertEquals( 2, ws.getNumPages() );
	}
	
	
/**  WidgetScroller.getNumWidgets */
	@Test public void test_getNumWidgets()
	{
		//Setup
		WidgetScroller ws = new WidgetScroller();
		assertEquals( 0, ws.getNumWidgets() );
		
		ws.addWidget("CCBox", 1);
		assertEquals( 1, ws.getNumWidgets() );
		ws.addWidget("CCBox", 1);
		assertEquals( 1, ws.getNumWidgets() );
		
		ws.addWidget("Gorilla", 1);
		ws.addWidget("SimpleBox", 1);
		assertEquals( 3, ws.getNumWidgets() );
		//Action Tested
		ws.decreaseWidget("Gorilla");
		//Post
		assertEquals( 2, ws.getNumWidgets() );
	}
	

/**  WidgetScroller.getPage */
	@Test public void test_getPage()
	{
		//Setup
		WidgetScroller ws = new WidgetScroller();
		
		/* test getPage */
		assertEquals(ws.getPage(), 1);
	}
	
	
/**  WidgetScroller.nextPage */
	@Test public void test_nextPage()
	{
		//Setup
		WidgetScroller ws = new WidgetScroller();
		
		//Pre
		ws.addWidget("CCBox", 1);
		ws.addWidget("Gorilla", 1);
		ws.addWidget("SimpleBox", 1);
		ws.addWidget("ConveyorBelt", 1);
		ws.addWidget("Ramp", 1);
		ws.addWidget("MysteryBox", 1);
		
		assertEquals( 1, ws.getPage() );
		//Action Tested
		ws.nextPage();
		//Post
		assertEquals( 2, ws.getPage() );
	}
	
	
/**  WidgetScroller.prevPage */
	@Test public void test_prevPage()
	{
		//Setup
		WidgetScroller ws = new WidgetScroller();
		//Pre
		ws.addWidget("CCBox", 1);
		ws.addWidget("Gorilla", 1);
		ws.addWidget("SimpleBox", 1);
		ws.addWidget("ConveyorBelt", 1);
		ws.addWidget("Ramp", 1);
		ws.addWidget("MysteryBox", 1);
		
		assertEquals( 1, ws.getPage() );
		ws.nextPage();
		assertEquals( 2, ws.getPage() );
		//Action Tested
		ws.prevPage();
		//Post
		assertEquals( 1, ws.getPage() );
	}

}
