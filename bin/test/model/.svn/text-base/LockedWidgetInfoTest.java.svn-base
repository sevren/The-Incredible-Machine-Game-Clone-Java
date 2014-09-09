package test.model;

import junit.framework.TestCase;
import org.junit.*;
import static org.junit.Assert.*;

import tim.model.LockedWidgetInfo;

import Widgets.Widget;

public class LockedWidgetInfoTest
{
	//Inital Setup
	LockedWidgetInfo lwi;
	
	private static final String TYPE           = "Gadget";
	private static final String DIR            = "SOUTH";
	private static final Widget.Direction WDIR = Widget.Direction.SOUTH;
	private static final int INT_POSX          = 20;
	private static final int INT_POSY          = 55;
	private static final float FLOAT_POSX      = 35;
	private static final float FLOAT_POSY      = 19;
	
	/**Setup*/
	@Before public void setParams()
	{
		lwi = new LockedWidgetInfo(TYPE, DIR, INT_POSX, INT_POSY);
	}
	/**Teardown*/
	@After public void nullObject()
	{
		lwi = null;
	}
	/**This tests if the instanciated widget is the of the specified type*/
	@Test public void getWidgetClassName()
	{
		assertEquals(TYPE, lwi.getWidgetClassName());
	}
	/**This tests if the instanciated widget is facing the specified direction  */
	@Test public void getDirection()
	{
		assertEquals(WDIR.toString(), lwi.getDirection().toString());
	}
	/**This tests if the instanciated widget X location is at the specified number*/
	@Test public void getX()
	{
		assertEquals((float)INT_POSX, lwi.getX(), 0f);
	}
	/**This tests if the instanciated widget Y location is at the specified number*/
	@Test public void getY()
	{
		assertEquals((float)INT_POSY, lwi.getY(), 0f);
	}
	/**This tests if the instanciated widget alternate constructor works*/
	@Test public void altConstructor()
	{
		LockedWidgetInfo lwi2 = new LockedWidgetInfo(TYPE, WDIR, FLOAT_POSX, FLOAT_POSY);
		assertEquals(TYPE, lwi2.getWidgetClassName());
		assertEquals(WDIR.toString(), lwi2.getDirection().toString());
		assertEquals(FLOAT_POSX, lwi2.getX(), 0f);
		assertEquals(FLOAT_POSY, lwi2.getY(), 0f);
	}
	
}
