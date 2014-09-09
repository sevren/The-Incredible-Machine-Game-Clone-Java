//
//  TimEvent.java
//  
//
//  Created by Quincy Jermyn on 28/10/08.
//  Copyright 2008 __MyCompanyName__. All rights reserved.
//
package tim.game;

import java.util.EventObject;
import java.lang.Object;
import tim.model.Puzzle;

/**
 * Event object used by the TimEventListener interface in communication
 * between the controller (TimGame) and view (TimGUI) and model (TimWorld)
 */
public class TimEvent extends EventObject {
    
    /*TimEvent Commands*/
    public final static String TIMGAME = "TimGame";
    public final static String TIMWORLD = "TimWorld";
    public final static String TIMGUI = "TimGUI";
    
    public final static String RESUME = "resume";
    public final static String NEWGAME = "new";
	public final static String SKIPLVL = "skip";
	public final static String EXIT = "exit";
    public final static String SETTINGS = "settings";
    public final static String UPDATE = "update";
    public final static String STOP = "stop";
    public final static String WIN = "win";
    public final static String FREEPLAY = "Freeplay";
    public final static String BADPW = "Bad Password";
    
    private Puzzle puzzle;
    private String command;
    private String strData;
    private int data;

    /**
     * Constructor - Creates a new TimEvent Object from the specified inputs
     * @param src class creating the event
     * @param com command to send
     */
    public TimEvent(Object src, String com) {
        super(src);
        command = com;
    }
    
    /**
     * Constructor - Creates a new TimEvent Object from the specified inputs
     * @param src class creating the event
     * @param com command to send
     * @param dat additional integer data for the command
     */
    public TimEvent(Object src, String com, int dat) {
        super(src);
        command = com;
        data = dat;
    }
    
    /**
     * Constructor - Creates a new TimEvent Object from the specified inputs
     * @param src class creating the event
     * @param com command to send
     * @param str additional string data for the command
     */
    public TimEvent(Object src, String com, String str) {
        super(src);
        command = com;
        strData = str;
    }
    
    /**
     * Constructor - Creates a new TimEvent Object from the specified inputs
     * @param src class creating the event
     * @param com command to send
     * @param str additional string data for the command
     * @param dat additional integer data for the command
     */
    public TimEvent(Object src, String com, String str, int dat) {
        super(src);
        command = com;
        data = dat;
        strData = str;
    }
    
    /**
     * Constructor - Creates a new TimEvent Object from the specified inputs
     * @param src class creating the event
     * @param com command to send
     * @param p puzzle for retrieval of puzzle info in the view
     */
    public TimEvent(Object src, String com, int dat, Puzzle p) {
        super(src);
        command = com;
        data = dat;
        puzzle = p;
    }
        
    /**
     * Returns the TimEvent's command
     *
     * @return the TimEvent Command
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
    
    /**
     * Returns a puzzle from a TimEvent
     *
     * @return a puzzle
     */
    public Puzzle getPuzzle() {
       return puzzle;
    }

}
