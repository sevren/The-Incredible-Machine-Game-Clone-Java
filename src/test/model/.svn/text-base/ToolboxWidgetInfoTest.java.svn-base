package test.model;

import junit.framework.TestCase;
import org.junit.*;
import static org.junit.Assert.*;

import tim.model.ToolboxWidgetInfo;

public class ToolboxWidgetInfoTest
{
	//Inital Setup
	ToolboxWidgetInfo twi = null;
	
	private static final String TYPE = "Gadget"; 
	private static final int NUMBER  = 5;
	
	/**Setup*/
	@Before public void setParams()
	{
		twi = new ToolboxWidgetInfo(TYPE, 5);
	}
	/**Teardown*/
	@After public void nullObject()
	{
		twi = null;
	}
	/**This tests to see if the class name from the ToolBoxWidgetInfo is equal to the specified type*/
	@Test public void getWidgetClassName()
	{
		assertEquals(TYPE, twi.getWidgetClassName());
	}
	/**This tests to see if the number from the ToolBoxWidgetInfo is equal to the specified number*/
	@Test public void getNumber()
	{
		assertEquals(NUMBER, twi.getNumber());
	}
}
