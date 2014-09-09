//
//  TimEvent.java
//  
//
//  Created by Quincy Jermyn on 28/10/08.
//  Copyright 2008 __MyCompanyName__. All rights reserved.
//
package tim.game;

import java.util.EventObject;

/**
 * Event object used by the TimEventListener interface in communication
 * between the view (TimGUI) and game (TimGame) and model (TimWorld)
 */
public class TimGUIEvent extends EventObject {
    /*TimGUIEvent Commands*/
    public final static String TIMGAME = "TimGame";
    public final static String TIMWORLD = "TimWorld";
    public final static String TIMGUI = "TimGUI";
    
    public final static String NEWGAME = "new";
	public final static String SKIPLVL = "skip";
	public final static String EXIT = "exit";
    public final static String SETTINGS = "settings";
    public final static String UPDATE = "update";
    public final static String FREEPLAY = "Freeplay";
    public final static String START = "Start";
    public final static String STOP = "Stop";
    
    private String command;
    private String strData;
    private int data;
    
    /**
     * Constructor - Creates a new TimGUIEvent Object from the specified inputs
     * @param src class creating the event
     * @param com command to send
     */
    public TimGUIEvent(Object src, String com) {
        super(src);
        command = com;
    }
    
    /**
     * Constructor - Creates a new TimGUIEvent Object from the specified inputs
     * @param src class creating the event
     * @param com command to send
     * @param dat additional integer data for the command
     */
    public TimGUIEvent(Object src, String com, int dat) {
        super(src);
        command = com;
        data = dat;
    }
    
    /**
     * Constructor - Creates a new TimGUIEvent Object from the specified inputs
     * @param src the class creating the event
     * @param com the command to send
     * @param str additional string data for the command
     * @param dat additional integer data for the command
     */
    public TimGUIEvent(Object src, String com, String str, int dat) {
        super(src);
        command = com;
        data = dat;
        strData = str;
    }
    
    /**
     * Returns the TimGUIEvent's command
     *
     * @return the TimGUIEvent Command
     */
    public String getCommand() {
        return command;
    }
    
    /**
     * Returns additional string data from a TimEvent
     *
     * @return additional string data
     */
    public String getStrData() {
        return strData;
    }
    
    /**
     * Returns additional integer data from a TimEvent
     *
     * @return additional integer data
     */
    public int getData() {
        return data;
    }

}

