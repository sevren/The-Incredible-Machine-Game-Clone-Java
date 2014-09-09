//
//  TimGame.java
//  
//
//  Created by Group Four on 23/09/08.
//  Copyright 2008 Group Four. All rights reserved.
//
/**
 * controller part of tim package 
 */
package tim.game;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Toolkit;
import java.awt.Dimension;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.*;

import net.phys2d.math.Vector2f;

import tim.gui.*;
import tim.model.*; 
import Widgets.*;

/**
 Controller class.  Handles all listener events from tim gui and fires events to the gui and world.
 Handles main game loop and required calculations
 */
public class TimGame implements TimGUIEventListener, MouseListener, MouseMotionListener, KeyListener, ActionListener, Drawable {


    /** 
      GameState enumeration.
      NOT_AVAIL - set before any level has been loaded
      EDITING - a level has been loaded, game is not running and level is not completed
      RUNNING - a loaded level is currently running
      COMPLETE - level has been run and victory condition met, but haven't yet loaded new level
    */
    private enum GameState { NOT_AVAIL, EDITING, RUNNING, COMPLETE };
    private boolean GAME_RUNNING = false;

    /**
      SelectMode enumeration.
      NONE - no widget selected / dragged / adding
      SELECTED - a widget is selected in the game area, but is not being dragged
      DRAGGING - a widget already placed in the game area is being dragged
      ADDING - a new widget from the widget scroller is being dragged on the game canvas
      */
    private enum SelectMode { NONE, SELECTED, DRAGGING, ADDING };
    private SelectMode SELECT_MODE = SelectMode.NONE;

    /**
      DragType enumeration.
      CLICK - drag started with a click and must end with a click
      PRESS - drag started with a press and must end with a release
      */
    private enum DragType { CLICK, PRESS };
    private DragType DRAG_TYPE = null;
    
    /* selected widget tracking */
    private Widget selectedWidget = null;
    private String addedWidget = null;

    /* x and y of last mousePress on canvas */
    private int pressX = 0;
	private int pressY = 0;
    
    /* x and y of last mouse position on canvas */
    private int mouseX = 0;
    private int mouseY = 0;
    
    /* offset of mouseclick from topleft corner of a widget */
    private float clickOffsetX = 0;
    private float clickOffsetY = 0;

    /* Timer that controls stepping the world */
    private Timer timTimer;

    /* various game and gui modules */
	private TimGUI timGUI;
    private TimWorld timWorld;
	private PuzzleManager timLevels;

    /* the widget scroller */
    private WidgetScroller ws;

    /* the player profile */
    private PlayerProfile pf;

    /* the audio player */
    private AudioPlayer ap;

    /* the main world canvas */
    private GameCanvas canvas;

    /* the help system */
	private TimHelp timHelp;
    
    /* Win checking collision listeners */
    private WinTester winTester = null;
    
    /* splash screen frame + label */
	private JFrame timSplash;
    
    /* List of TimEventListeners */
    private ArrayList<TimEventListener> timListeners = new ArrayList<TimEventListener>();

    /* TODO: remove me */
    private String statusMsg = new String();
    
	/*setup for the Splashscreen*/
	public TimGame() 
    {
        /* set up timer for displaying splash screen */
        timTimer = new Timer(10, this);

        /* set up splash screen */
		timSplash = new JFrame();
		JLabel splashLabel = new JLabel();
		splashLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/images/TIMG4logo.jpg")));
		timSplash.add(splashLabel);
		timSplash.setSize(1000,550);
		timSplash.setUndecorated(true);
		timSplash.setLocationRelativeTo(null);
		timSplash.setVisible(true);	
        
        /* instantiate large objects */
        pf = new PlayerProfile();
	    ap = new AudioPlayer();
        timWorld = new TimWorld();
        timHelp = new TimHelp();

        /* instantiate widget scroller and add to all listeners */
        ws = new WidgetScroller();
        ws.addMouseListener(this);
        ws.addMouseMotionListener(this);

        /* instantiate game canvas and add to all listeners */
        canvas = new GameCanvas(timWorld);
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
        canvas.addKeyListener(this);
        canvas.addDrawable(this);
	}

    /**
      Main method. 
     */
    public static void main(String[] args) 
    {
        /* instantiate game */
        TimGame game = new TimGame();

        /* set timer to kill the splash screen in a second */
		Timer timer = new Timer(1000,game);
		timer.setRepeats(false);
		timer.start();
	}
    
	/**
      Handle action events fired by the two timers. */
	public void actionPerformed(ActionEvent e) 
    {
	
        if(e.getSource().equals(timTimer)) 
        {
            /* this event was fired by timTimer */
            
            timWorld.step(); //step the game world
            canvas.repaint(); //repaint the canvas
            
            /* test for win condition */
            if(winTester != null && winTester.checkForWin())
            {
                stopGame(true);
                switchGameState(GameState.COMPLETE);
            }
        } 
        else //if(e.getActionCommand().equals(TIMER_SPLASH_REMOVE))
        {
            /* this event fired by the splash screen delay timer from the main() method */

            /* hide splash screen */
            timSplash.setVisible(false);

            /* instantiate TimGUI */
            timGUI = new TimGUI(this, canvas, ws, pf, "Fantastic Fourth Machine - 4.44",timHelp);
            
            /* start music based on player profile's setting */
	        if (pf.getPlayerSetting().getMusic() == PlayerSetting.Setting.ON)
	        {
		        ap.playMusic();
	        }
        }
	}

    /** Creates a new puzzle from the level number entered. 
        Creates widgets from WidgetInfo classes and
        add adds to game world and widget scroller where necessary.
     
        @param levelNum to change to
     */
    private void changeLevel(int levelNum) {
        /* TODO: clean up in here */
        try 
        {
            if(levelNum > 0) 
            {
                /* positive leve number means get a puzzle from manager */

                /* retrieve the puzzle from the puzzlemanager by number */
                Puzzle level = timLevels.getLevel(levelNum);

                /* if manager returned null, print error message and don't do anything else */
                if(level == null) 
                {
                    System.err.println("Error changing to level " + levelNum);
                    fireTimEvent(new TimEvent(this,TimEvent.UPDATE),TimEvent.TIMGUI); //not quite sure why this event gets fired in the event of a level loading failure since nothing has changed --Andrew
                    return;
                }

                /* clear existing world */
                winTester = null; //remove old wintester
                timWorld.clear(); //clear world of old widgets
                ws.clearWidgets(); //clear widget scroller of old widgets
                SELECT_MODE = SelectMode.NONE;
                selectedWidget = null;

                /* create the widgets and add them to the world */
                ArrayList<LockedWidgetInfo> lockedWidgets = level.getLockedWidgetSet();

                /* ordered list of created widgets */
                ArrayList<Widget> widgetList = new ArrayList<Widget>();

                for(int i=0; i < lockedWidgets.size(); i++) 
                {
                    /* create the widget */
                    Widget w = WidgetFactory.createWidget(lockedWidgets.get(i).getWidgetClassName());
                    /* set its position */
                    w.setPosition(new Vector2f(lockedWidgets.get(i).getX(),lockedWidgets.get(i).getY()));
                    /* set its direction */
                    w.setDirection(lockedWidgets.get(i).getDirection());
                    /* set it to locked */
                    w.setLock(true);
                    /* add it to the world */
                    timWorld.addWidget(w);

                    /* dirty hack to be able to retrieve widgets from ordered list for win condition tester */
                    widgetList.add(w);
                }

                /* Add placeable widgets to scroller */
                ArrayList<ToolboxWidgetInfo> levelWidgets = level.getToolboxWidgetSet();
                for(int i=0; i < levelWidgets.size(); i++) 
                {
                    String className =levelWidgets.get(i).getWidgetClassName();
                    int qty = levelWidgets.get(i).getNumber();
                    ws.addWidget(className.substring(className.lastIndexOf('.') + 1,className.length()), qty);
                }

                /* set up win condition tester */
                /* win condition info */
                String winConditionType = new String(level.getWinCondition().getType());
                ArrayList<Object> winArgs = level.getWinCondition().getArguments();
                
                /* set up the win condition for the level*/
                if(winConditionType.equals(WinConditionInfo.SINGLECOLLISION)) 
                {
                    if(winArgs != null) 
                    {
                        SingleCollision singCol = new SingleCollision();
                        timWorld.addCollisionListener(singCol);
                        /* grab appropriate widget from the list */
                        singCol.setTargetWidget(widgetList.get(Integer.parseInt(winArgs.get(0).toString())));
                        winTester = singCol;
                    }
                }
                else if(winConditionType.equals(WinConditionInfo.PAIRCOLLISION)) 
                {
                    if(winArgs != null) 
                    {
                        PairCollision pairCol = new PairCollision();
                        timWorld.addCollisionListener(pairCol);
                        pairCol.setTargetWidget1(widgetList.get(Integer.parseInt(winArgs.get(0).toString())));
                        pairCol.setTargetWidget2(widgetList.get(Integer.parseInt(winArgs.get(1).toString())));
                        winTester = pairCol;
                    }
                }
                else if(winConditionType.equals(WinConditionInfo.TOUCHLOCATION)) 
                {
                    if(winArgs != null) 
                    {
                        TouchingLocationCollision touchLocCol = new TouchingLocationCollision(Integer.parseInt(winArgs.get(1).toString()), Integer.parseInt(winArgs.get(2).toString()), Integer.parseInt(winArgs.get(3).toString()), Integer.parseInt(winArgs.get(4).toString()));
                        touchLocCol.setTargetWidget1(widgetList.get(Integer.parseInt(winArgs.get(0).toString())));
                        winTester = touchLocCol;
                    }
                }
                else if(winConditionType.equals(WinConditionInfo.INSIDELOCATION)) 
                {
                    if(winArgs != null) 
                    {
                        InsideLocationCollision inLocCol = new InsideLocationCollision(Integer.parseInt(winArgs.get(1).toString()), Integer.parseInt(winArgs.get(2).toString()), Integer.parseInt(winArgs.get(3).toString()), Integer.parseInt(winArgs.get(4).toString()));
                        timWorld.addCollisionListener(inLocCol);
                        inLocCol.setTargetWidget1(widgetList.get(Integer.parseInt(winArgs.get(0).toString())));
                        winTester = inLocCol;
                    }
                }
                else
                {
                    /* TODO: expand this error handling considerably */
                    System.out.println("Invalid Win Condition set");
                }
                
                /* fire event to tell timgui to update */
                fireTimEvent(new TimEvent(this,TimEvent.UPDATE,levelNum,level),TimEvent.TIMGUI);
            }
            else 
            {
                /* freeplay mode */
                timWorld.clear();
                winTester = null; //remove old win tester
                SELECT_MODE = SelectMode.NONE;
                selectedWidget = null;

                /* add all widgets from the factory to the scroller in unlimited quantities */
                ws.clearWidgets();
                for(String widget : WidgetFactory.availableWidgets()) {
                    ws.addWidget(widget,-1);
                }
            }
        } 
        catch (Exception ex) 
        {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            System.out.println("Error changing to level " + levelNum);
        }
    }

    /** 
     * Receives the TimGUI event from the listener and handles changing
     * levels and the background work to start a levels
     *
     * @param the event to handle
     */
	private void startEventQueue(TimGUIEvent event) {
        if(event!=null) 
        {
            if(event.getCommand().equals(TimEvent.NEWGAME)) 
            {
                /* start a new game */
                timLevels = new PuzzleManager(pf);  // reinitialize puzzlemanager TODO: move this initialization to one spot
                changeLevel(1);                     // change to level one
                switchGameState(GameState.EDITING);   // change to editing mode
            }
            else if(event.getCommand().equals(TimEvent.SKIPLVL)) 
            {
                /* skip to a new level */
                timLevels = new PuzzleManager(pf); // reinitialize puzzlemanager TODO: move this initialization to one spot
                
                if(pf.getLevels() >= event.getData() || timLevels.unlockLevel(event.getData(), event.getStrData())) 
                {
                    /* user had this level unlocked, or entered proper password to unlock it */
                    changeLevel(event.getData());     // change to requested level
                    switchGameState(GameState.EDITING); // change to eiditng mode
                } 
                else
                {
                    /* user entered incorrect password */
                    /* TODO: output this message somewhere more useful than to the console.. */
                    JOptionPane.showMessageDialog(null, "Bad Password Entered, try again");
                    fireTimEvent(new TimEvent(this, TimEvent.BADPW), TimEvent.TIMGUI);
                }
            }
            else if(event.getCommand().equals(TimEvent.FREEPLAY)) 
            {
                /* enter free-play mode */
                changeLevel(-1); // change to freeplay level
                switchGameState(GameState.EDITING); // switch to editing mode
            }
            else if (event.getCommand().equals(TimEvent.EXIT)) 
            {

                System.exit(0); //exit the game
            }
        }
	}

    /**
      Switch between game modes.  
      Primarily used to switch between running & editing modes.  Action taken depends on what mode we're in, and what mode is requested.
      */
	private void switchGameState(GameState newMode) 
    {
        switch(newMode)
        {
            case NOT_AVAIL:
                /* probably won't ever switch TO this mode, but still: */
                GAME_RUNNING = false;
                timTimer.stop(); //stop timer
                break;
            case EDITING:
                GAME_RUNNING = false;
                timTimer.stop(); //stop timer
                timWorld.reset(); //reset world
                canvas.repaint(); //repaint canvas
                break;
            case RUNNING:
                GAME_RUNNING = true;
                timWorld.prepare(); //prepare world
                timTimer.start(); //start timer
                break;
            case COMPLETE:
                GAME_RUNNING = false;
                timTimer.stop(); //stop timer
                fireTimEvent(new TimEvent(this,TimEvent.WIN), TimEvent.TIMGUI); //fire win event
                break;

        }
    }

    /* TimEvent handling */
    public synchronized void addTimEventListener(TimEventListener tLis) {
        timListeners.add(tLis);
    }
    public synchronized void removeTimEventListener(TimEventListener tLis) {
        timListeners.remove(tLis);
    }
    private synchronized void fireTimEvent(TimEvent tEvent, String recvClass) {
        TimEventListener classListener;
        Iterator listeners = timListeners.iterator();
        while(listeners.hasNext()) {
            classListener = (TimEventListener)listeners.next();
            if(classListener.toString() == recvClass) {
                classListener.timEventReceived(tEvent);
            }
        }
    }
    
    /**
      Handle incoming messages from TimGUI.
      */
    public void timGUIEventReceived(TimGUIEvent event) 
    {
        /* TODO: remove debug msg */
        //System.out.println("TimGame got " + event.getCommand() + " from TimGUI");
        
        if(event.getCommand().equals(TimGUIEvent.START)) 
        {
            /* Start the game */
            switchGameState(GameState.RUNNING);
        }
        else if(event.getCommand().equals(TimGUIEvent.STOP)) 
        {
            /* Stop the game */
            switchGameState(GameState.EDITING);
        }
        else if(event.getCommand().equals(TimGUIEvent.SETTINGS))
        {
            /* settings change */
	        PlayerSetting ps = pf.getPlayerSetting();
    	    if (pf.getPlayerSetting().getMusic() == PlayerSetting.Setting.OFF)
    	    {
    	    	ap.stopMusic();
            }
    	    else
    	    {
    	    	ap.playMusic();
    	    }
        	timWorld.setGravity(ps.getGravity());
	    }
        else 
        {
            startEventQueue(event);
      }
    }
    
    /** 
       Mouse listener event corresponding to mouse being moved when no buttons are depressed.
     */
    public void mouseMoved(MouseEvent mEvent) 
    {
        /* don't do anything while game is running */
        if(GAME_RUNNING)
            return;

        /* TODO: remove debug msg */
        statusMsg = new String("mouseMoved @ " + mEvent.getX() + ", " + mEvent.getY() + " on " + mEvent.getComponent().getClass().getName());

        if(mEvent.getComponent() instanceof GameCanvas)
        {
            /* handle mouse movement on gamecanvas while game is not running */
            switch(SELECT_MODE)
            {
                case NONE:
                    //nothing
                    break;
                case SELECTED:
                    //nothing
                    break;
                case DRAGGING:
                case ADDING:
                    /* update mouseX and mouseY for bounding box drawing of the widget being dragged or added */
                    mouseX = mEvent.getX();
                    mouseY = mEvent.getY();
                    canvas.repaint();
                    break;
            }
        }
	}

    /** 
      Mouse lsitener event corresponding to mouse being moved when button(s) are depressed. 
     */
	public void mouseDragged(MouseEvent mEvent) 
    {
        /* don't do anything while game is running */
        if(GAME_RUNNING)
            return;

        /* TODO: remove debug msg */
        statusMsg = new String("mouseDragged @ " + mEvent.getX() + ", " + mEvent.getY() + " on " + mEvent.getComponent().getClass().getName());

        if(mEvent.getComponent() instanceof GameCanvas)
        {
            /* handle mouse dragging on gamecanvas while game is not running */
            switch(SELECT_MODE)
            {
                //=================
                case NONE:
                case SELECTED:
                    /* mouse is being dragged but we aren't in drag mode, did this drag start on an unlocked widget? */
                    Widget startWidget = timWorld.grabWidget(pressX,pressY); //TODO: add a flag/check so we don't have to continually try to grab in case of failure
                    if(startWidget != null && startWidget.isLocked() == false)
                    {
                        /* mouse dragging started over an unlocked widget, pick it up and begin dragging */
                        selectedWidget = startWidget;
                        SELECT_MODE = SelectMode.DRAGGING;
                        DRAG_TYPE = DragType.PRESS;
                        
                        clickOffsetX = selectedWidget.getPosition().getX() - pressX;
                        clickOffsetY = selectedWidget.getPosition().getY() - pressY;
                        
                        canvas.repaint();
                    }
                    break;
                //=================
                case DRAGGING:
                case ADDING:
                    /* update mouseX and mouseY for bounding box drawing of the widget being dragged or added */
                    mouseX = mEvent.getX();
                    mouseY = mEvent.getY();
                    canvas.repaint();
                    break;
            }
        }
        else if(mEvent.getComponent() instanceof WidgetScroller)
        {
            /* dragging on widget scroller */

            /* make sure we aren't already in adding or dragging mode */
            if(SELECT_MODE != SelectMode.ADDING)
            {
                String pressedWidget = ws.getWidgetAt(pressX, pressY);
                if(pressedWidget != null)
                {
                    /* pressed on an actual widget, create an instance of it and commence dragging */
                    
                    // TODO: check for nulls
                    addedWidget = pressedWidget;
                    selectedWidget = WidgetFactory.createWidget(pressedWidget);
                    SELECT_MODE = SelectMode.ADDING;
                    DRAG_TYPE = DragType.PRESS;

                    /* set widget to 0,0 so its boundary will be drawn properly while dragging */
                    selectedWidget.setPositionX(0.0f);
                    selectedWidget.setPositionY(0.0f);

                    clickOffsetX = 0;
                    clickOffsetY = 0;

                    canvas.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));

                    /* TODO: remove debug msg */
                    //System.out.println("Picked up " + pressedWidget + " from widgetscroller");
                }
            }
            else
            {
                mouseX = mEvent.getX();
                mouseY = mEvent.getY() + 465;
                canvas.repaint();
            }
        }
	}
   
    /** 
       Mouse listener event corresponding to mouse button being depressed and then released with no movement in between. 
     */
	public void mouseClicked(MouseEvent mEvent) 
    {
        /* don't do anything while game running */
        if(GAME_RUNNING)
            return;

        /* only respond to button 1 */
        if(mEvent.getButton() != MouseEvent.BUTTON1)
            return;

        /* TODO: remove debug msg */
		//System.out.println("Mouse clicked on the "+ mEvent.getComponent().getClass().getName() +" @ "+ mEvent.getX() + ", " + mEvent.getY());

        if(mEvent.getComponent() instanceof GameCanvas)
        {
            Widget clickedWidget = timWorld.grabWidget(mEvent.getX(), mEvent.getY());
            switch(SELECT_MODE)
            {
                //=================
                case NONE:
                    //if over a non-locked widget, make it new selection
                    if(clickedWidget != null && clickedWidget.isLocked() == false)
                    {
                        selectedWidget = clickedWidget;
                        SELECT_MODE = SelectMode.SELECTED;
                        canvas.repaint();
                    }
                    break;
                //=================
                case SELECTED:
                    if(clickedWidget == null || clickedWidget.isLocked() == true)
                    {
                        /* clicked on no widget, or on a locked widget 
                                -> deselect current widget */
                        selectedWidget = null;
                        SELECT_MODE = SelectMode.NONE;
                        canvas.repaint();
                    }
                    else if(clickedWidget == selectedWidget)
                    {
                        /* clicked on currently selected widget
                            -> pick it up and start dragging */
                        
                        // store click offset
                        clickOffsetX = selectedWidget.getPosition().getX() - mEvent.getX();
                        clickOffsetY = selectedWidget.getPosition().getY() - mEvent.getY();
    
                        // start dragging mode, changing to move cursor
                        SELECT_MODE = SelectMode.DRAGGING;
                        DRAG_TYPE = DragType.CLICK;

                        canvas.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                        canvas.repaint();
                    }
                    else
                    {
                        /* clicked on an unlocked widget other than currently selected widget
                            -> make this widget the new selection */
                        selectedWidget = clickedWidget;
                        canvas.repaint();
                    }
                    break;
                //=================
                case DRAGGING:
                    //attempt to move widget, stop dragging if successful, keep widget selected
                    if(timWorld.moveWidget(selectedWidget, mEvent.getX() + clickOffsetX, mEvent.getY() + clickOffsetY))
                    {
                        /* successfully moved to new loc, stop dragging but keep selected */
                        SELECT_MODE = SelectMode.SELECTED;
                        
                        /* repaint canvas and play sound */
                        canvas.repaint();
    			        if (pf.getPlayerSetting().getSound() == PlayerSetting.Setting.ON)
        			    {
        					ap.playGoodSound();
        			    }

                        //TODO: remove debug msg
                       // System.out.println("mouseReleased successfully moved " + selectedWidget.getName() + " to " + (mEvent.getX() + clickOffsetX) + ", " + (mEvent.getY() + clickOffsetY) + ", widget.getPosition returns " + selectedWidget.getPosition());
                    }
                    else
                    {
                        /* new position no good, stop dragging */
                        SELECT_MODE = SelectMode.SELECTED;
                        
                        /* repaint canvas and play sound */
                        canvas.repaint();
    			        if (pf.getPlayerSetting().getSound() == PlayerSetting.Setting.ON)
        			    {
        					ap.playBadSound();
        			    }

                        //TODO: remove debug msg
       	                //System.out.println("New position " + (mEvent.getX() + clickOffsetX) + ", " + (mEvent.getY() + clickOffsetY) + " not valid for " + selectedWidget.getName() + ", leaving it where it was" + ".");
                    }
                    
                    //restore default cursor
                    canvas.setCursor(Cursor.getDefaultCursor());
                    break;
                //=================
                case ADDING:
    	            // try to move it
    	  	        if ( mEvent.getX()+clickOffsetX >= 0 && mEvent.getX()+clickOffsetX <= 780 && mEvent.getY() >= -434 &&
    	  	            timWorld.moveWidget(selectedWidget, mEvent.getX()+clickOffsetX, mEvent.getY()+clickOffsetY) )
    	      	    {
    	                // successfully moved to new position
    			        if (pf.getPlayerSetting().getSound() == PlayerSetting.Setting.ON)
    			        {
    				        ap.playGoodSound();
    			        }
    
                        /* TODO: remove debug msg */
    	    		    //System.out.println("mouseReleased successfully moved " + selectedWidget.getName() + " to " + (mEvent.getX() + clickOffsetX) + ", " + (mEvent.getY() + clickOffsetY) + ", widget.getPosition returns " + selectedWidget.getPosition());
    	                
                        // decrement this in widget scroller
    	                ws.decreaseWidget(addedWidget);

                        // change to selection mode
                        SELECT_MODE = SelectMode.SELECTED;
    	      	    }
    	      	    else
    	      	    {
    	                // move to new pos unsuccessful
                		if (pf.getPlayerSetting().getSound() == PlayerSetting.Setting.ON)
                		{
                			ap.playBadSound();
        			    }
                        /* TODO: remove debug msg */
               		    //System.out.println("New position " + (mEvent.getX() + clickOffsetX) + ", " + (mEvent.getY() + clickOffsetY) + " not valid for " + selectedWidget.getName() + ", returning it to widget scroller.");

                        SELECT_MODE = SelectMode.NONE;
                        selectedWidget = null;
    	      	    }
                    break;
            }
        }
        else if(mEvent.getComponent() instanceof WidgetScroller)
        {
            /* Click on widget scroller */
            switch(SELECT_MODE)
            {
                case NONE:
                case SELECTED:
                    String clickedWidget = ws.getWidgetAt(pressX, pressY);
                    if(clickedWidget != null)
                    {
                        /* clicked on an actual widget in scroller, create an instance of it and commence dragging */
                        
                        // TODO: check for nulls
                        addedWidget = clickedWidget;
                        selectedWidget = WidgetFactory.createWidget(clickedWidget);
                        SELECT_MODE = SelectMode.ADDING;
                        DRAG_TYPE = DragType.CLICK;
    
                        /* set widget to 0,0 so its boundary will be drawn properly while dragging */
                        selectedWidget.setPositionX(0.0f);
                        selectedWidget.setPositionY(0.0f);
    
                        clickOffsetX = 0;
                        clickOffsetY = 0;

                        canvas.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
    
                        /* TODO: remove debug msg */
                        //System.out.println("Picked up " + clickedWidget + " from widgetscroller");
                    }
                    break;
                case DRAGGING:
                    /* click on widget scroller while dragging */
                    //remove widget from world
                    /* TODO: remove debug msg */
                    //System.out.println("Returned " + selectedWidget.getName() + " to the widget scroller. (classname: " + selectedWidget.getClass().getName() + ")");

                    String widgetType = selectedWidget.getClass().getName();
                    ws.addWidget(widgetType.substring(widgetType.lastIndexOf(".")+1,widgetType.length()), 1);
                    
                    timWorld.removeWidget(selectedWidget);
                    SELECT_MODE = SelectMode.NONE;
                    selectedWidget = null;

                    break;
                
                case ADDING:
                    /* click on widget scroller while adding */
                    /* just deselect widget and go back to no selection */
                    SELECT_MODE = SelectMode.NONE;
                    selectedWidget = null;
                    break;
            }
        }
        canvas.setCursor(Cursor.getDefaultCursor());
        canvas.repaint();
    }


    /** 
      Mouse listener event for a mouse button being depressed 
     */
	public void mousePressed(MouseEvent mEvent) 
    {
        /* take no action while game running */
        if(GAME_RUNNING)
            return;

        /* only respond to button 1 */
        if(mEvent.getButton() != MouseEvent.BUTTON1)
            return;
    
        /* TODO: remove debug msg */
		//System.out.println("Mouse pressed on the "+ mEvent.getComponent().getClass().getName() +" @ "+ mEvent.getX() + ", " + mEvent.getY());
		
		if(SELECT_MODE != SelectMode.DRAGGING && SELECT_MODE != SelectMode.ADDING)
		{
			pressX = mEvent.getX();
			pressY = mEvent.getY();
		}
	}
    
    /** 
      Mouse listener event for mouse button being released.
     */
	public void mouseReleased(MouseEvent mEvent) 
    {
        /* take no action while game running */
        if(GAME_RUNNING)
            return;

        /* only respond to button 1 */
        if(mEvent.getButton() != MouseEvent.BUTTON1)
            return;

        /* TODO: remove debug msg */
		//System.out.println("Mouse released on the "+ mEvent.getComponent().getClass().getName() +" @ "+ mEvent.getX() + ", " + mEvent.getY());

        if(mEvent.getComponent() instanceof GameCanvas)
        {
            /* handle mouseReleased events on GameCanvas 
                    -> this implies click or dragging started on gamecanvas too */
            switch(SELECT_MODE)
            {
                case NONE:
                    //nothing
                    break;
                case SELECTED:
                    //nothing
                    break;
                //===================
                case DRAGGING:
                    //mouse released on gamecanvas during dragging = move widget to new loc, or if move was far enough south = re-add to scroller
                    /* don't continue if this is a click type drag, wait for the click handler to deal with it */
                    if(DRAG_TYPE == DragType.CLICK)
                        break;

                    if(mEvent.getY() >= 465)
                    {
                        /* put widget back in scroller */
                        /* TODO: remove debug msg */
                        //System.out.println("Returned " + selectedWidget.getName() + " to the widget scroller.");
                        
                        String widgetType = selectedWidget.getClass().getName();
                        ws.addWidget(widgetType.substring(widgetType.lastIndexOf(".")+1,widgetType.length()), 1);

                        timWorld.removeWidget(selectedWidget);
                        SELECT_MODE = SelectMode.NONE;
                        selectedWidget = null;
                        canvas.repaint();
                    }
                    else
                    {
                        /* test if position is within canvas boundary + try to move */
              	        if ( mEvent.getX()+clickOffsetX >= 0 && mEvent.getX()+clickOffsetX <= 780 && mEvent.getY()+clickOffsetY >= 0 &&
              	            timWorld.moveWidget(selectedWidget, mEvent.getX()+clickOffsetX, mEvent.getY()+clickOffsetY) )
                  	    {
                            // successfully moved to new position
        			        if (pf.getPlayerSetting().getSound() == PlayerSetting.Setting.ON)
        			        {
        				        ap.playGoodSound();
        			        }
                            
                            /* TODO: remove debug msg */
                		    //System.out.println("mouseReleased successfully moved " + selectedWidget.getName() + " to " + (mEvent.getX() + clickOffsetX) + ", " + (mEvent.getY() + clickOffsetY) + ", widget.getPosition returns " + selectedWidget.getPosition());
    
                            // change to selection mode
                            SELECT_MODE = SelectMode.SELECTED;
                            canvas.setCursor(Cursor.getDefaultCursor());
                            canvas.repaint();
                  	    }
                  	    else
                  	    {
                            // move to new pos unsuccessful
                    		if (pf.getPlayerSetting().getSound() == PlayerSetting.Setting.ON)
                    		{
                    			ap.playBadSound();
        				    }
                            /* TODO: remove debug msg */
        	       		    //System.out.println("New position " + (mEvent.getX() + clickOffsetX) + ", " + (mEvent.getY() + clickOffsetY) + " not valid for " + selectedWidget.getName() + ", returning it to widget scroller.");
    
                            SELECT_MODE = SelectMode.SELECTED;
                            canvas.setCursor(Cursor.getDefaultCursor());
                            canvas.repaint();
                  	    }
                    }
                    break;
                //===================
                case ADDING:
                    //shouldn't be able to get here, can't release mouse on gamecanvas while adding from scroller
                    break;
            }
        }
        else if(mEvent.getComponent() instanceof WidgetScroller)
        {
            /* handle mouseReleased events on widget scroller 
                    -> implies click or drag started on widgetscroller which should mean a widget is being PRESS DRAGGED out*/
            if(SELECT_MODE == SelectMode.ADDING && DRAG_TYPE == DragType.PRESS)
            {
                Point canvasPos = canvas.getMousePosition();

                if(canvasPos != null)
                {
                    /* mouse was released on gamecanvas, try to place the widget */
                    if(timWorld.moveWidget(selectedWidget,(float)canvasPos.getX(),(float)canvasPos.getY()))
                    {
                        /* placement successful */
                        SELECT_MODE = SelectMode.SELECTED;
                        canvas.setCursor(Cursor.getDefaultCursor());
                        ws.decreaseWidget(addedWidget);
                        canvas.repaint();
                    }
                    else
                    {
                        /* placement unsuccessful, cancel adding */
                        SELECT_MODE = SelectMode.NONE;
                        canvas.setCursor(Cursor.getDefaultCursor());
                        selectedWidget = null;
                        canvas.repaint();
                    }
                }
                else
                {
                    /* mouse was released outside of the gamecanvas, cancel adding */
                    SELECT_MODE = SelectMode.NONE;
                    canvas.setCursor(Cursor.getDefaultCursor());
                    selectedWidget = null;
                    canvas.repaint();
                }
            }
        }
    }
    
    /** unused event */
	public void mouseEntered(MouseEvent mEvent) {}		
	/** unused event */
	public void mouseExited(MouseEvent mEvent) {}

    /**
      Drawable interface implementation. Will be used to draw bounding boxes for selected / dragged widgets to the game canvas.
      */
    public void draw(Graphics2D g)
    {
        //g.setColor(Color.black);
        //g.drawString(statusMsg,20,20);

        /* only draw when game is not running */
        if(GAME_RUNNING == false && selectedWidget != null)
        {
            if(SELECT_MODE == SelectMode.SELECTED || SELECT_MODE == SelectMode.DRAGGING)
            {
                /* draw boundary of selected widget */
                g.setColor(Color.orange);

                Vector2f[] points = selectedWidget.getBoundary();
                for(int i = 0; i <= 3; i++)
        		{
                    
        			g.drawLine(
                               (int)points[i].getX(),
                               (int)points[i].getY(),
                               (int)points[(i+1)%4].getX(),
                               (int)points[(i+1)%4].getY());
        		}
            }

            if(SELECT_MODE == SelectMode.DRAGGING || SELECT_MODE == SelectMode.ADDING)
            {
                /* draw boundary of dragged widget */
                if (timWorld.testPlacement(selectedWidget, mouseX + clickOffsetX, mouseY + clickOffsetY))
                {
                    /* placement position is safe, draw green boundary */
                    g.setColor(Color.green);
                }
                else
                {
                    /* placement position unsafe, draw red boundary */
                    g.setColor(Color.red);
                }
                
                Vector2f[] points = selectedWidget.getBoundary();
                for(int i = 0; i <= 3; i++)
        		{
                    
                    if(SELECT_MODE == SelectMode.ADDING)
                    {
            			g.drawLine(
                               (int)(points[i].getX() + mouseX),
                               (int)(points[i].getY() + mouseY),
                               (int)(points[(i+1)%4].getX() + mouseX),
                               (int)(points[(i+1)%4].getY() + mouseY));
                    }
                    else
                    {
        			    g.drawLine(
                               (int)(points[i].getX() + (mouseX - pressX)),
                               (int)(points[i].getY() + (mouseY - pressY)),
                               (int)(points[(i+1)%4].getX() + (mouseX - pressX)),
                               (int)(points[(i+1)%4].getY() + (mouseY - pressY)));
                    }
        		}
            }
        }
    }

    /**
      keyPressed event for keyboard listener interface.
      */
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode())
        {
 			case KeyEvent.VK_ESCAPE:
                if(GAME_RUNNING == true)
                {
                    /* stop running */
                    switchGameState(GameState.EDITING);
                }
                else
                {
                    if(SELECT_MODE == SelectMode.SELECTED)
                    {
                        /* deselect current widget */
                        SELECT_MODE = SelectMode.NONE;
                        selectedWidget = null;
                        canvas.repaint();
                    }
                    else if(SELECT_MODE == SelectMode.DRAGGING)
                    {
                        /* cancel dragging */
                        SELECT_MODE = SelectMode.SELECTED;
                        canvas.repaint();
                    }
                    else if(SELECT_MODE == SelectMode.ADDING)
                    {
                        /* cancel adding */
                        SELECT_MODE = SelectMode.NONE;
                        selectedWidget = null;
                        canvas.repaint();
                    }
                    else
                    {
                        /* not running and no widget selected */
                        //TODO: show main menu
                    }
                }
				break;
            case KeyEvent.VK_DELETE:
                if(GAME_RUNNING == false)
                {
                    if(SELECT_MODE == SelectMode.SELECTED)
                    {
                        /* game not running and we're in selected mode */
                        /* delete currently selected widget */
                        timWorld.removeWidget(selectedWidget);

                        String widgetType = selectedWidget.getClass().getName();
                        ws.addWidget(widgetType.substring(widgetType.lastIndexOf(".")+1,widgetType.length()), 1);

                        SELECT_MODE = SelectMode.NONE;
                        selectedWidget = null;

                        canvas.repaint();
                    }
                }
                break;
            case KeyEvent.VK_LEFT:
                if(GAME_RUNNING == false && SELECT_MODE == SelectMode.SELECTED)
                {
                    /* game is not running and we have a widget selected */
                    if(selectedWidget != null)
                    {
                        /* rotate is counter clockwise */
                        selectedWidget.rotateCounterClockwise();
                        canvas.repaint();
                    }
                }
                break;
            case KeyEvent.VK_RIGHT:
                if(GAME_RUNNING == false && SELECT_MODE == SelectMode.SELECTED)
                {
                    /* game is not running and we have a widget selected */
                    if(selectedWidget != null)
                    {
                        /* rotate it clockwise */
                        selectedWidget.rotateClockwise();
                        canvas.repaint();
                    }
                }
                break;
		}
	}
    
    /** unused event */
	public void keyReleased(KeyEvent e){}
	/** unused event */
	public void keyTyped(KeyEvent e){}
    
    /** 
      Returns string representing this object for TimEvent handling.
      @return The string representing this object.
      */
    public String toString() {
        return TimEvent.TIMGAME;
    }

    /**
     * Method to stop gameplay if need be, if a win has occured
     * play the good sound!
     * @param win true if the game was stopped because of a win, false otherwise
     */
    public void stopGame(boolean win) {
        switchGameState(GameState.EDITING);
        if(win == true) {
            if (pf.getPlayerSetting().getSound() == PlayerSetting.Setting.ON)
            {
                ap.playGoodSound();
            }
        }
    }
    
}
