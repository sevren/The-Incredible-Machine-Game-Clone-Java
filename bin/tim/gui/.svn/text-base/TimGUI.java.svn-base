    //
//  
//  
//
//  Created by Group Four on 23/09/08.
//  Copyright 2008 Group Four. All rights reserved.
//
package tim.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.lang.*;
import java.util.EventListener;
import tim.model.TimWorld;
import java.util.*;
import tim.model.Puzzle;
import tim.game.*;

import Widgets.Widget;

import net.phys2d.math.Vector2f;

/**
 * View class. Uses TimEventListener to receive TimEvents from TimGame.  
 * Fire's events to TimWorld and TimGame using fireTimGUIEvent.
 */
public class TimGUI extends JFrame implements TimEventListener {
    
    private TimGUIEvent guiEvent;

    private TimGame gameRef;
    private TimWorld timWorld;
    private TimMenuPanel menuPanel;
    private TimLevelSelect levelMenu;
    
    private Puzzle level;
    private WidgetScroller wsRef;
    private PlayerProfile pfProfile;
    private TimMenuScreen menu;
    private GameCanvas canvas;
    private TimHelp timHelp;
    private ScorePanel scorePanel;
    //The list of classes that will receive TimGUIEvents
    private ArrayList<TimGUIEventListener> timGUIListeners = new ArrayList<TimGUIEventListener>();
    
    /**
     Constructor for TimGUI needs a reference to the game that created it, the world, and 
     the widget scroller to display widgets in the bottom panel
	 * @param tGame A reference of TimGame, so it can be hooked into a GUIEventListener.
	 * @param canvas Needed to display as part of the GUI.
	 * @param ws Needed to display as part of the GUI.
	 * @param pf Needed to display as a dialog, when creating/setting the player profile.
	 * @param guiName The name to be displayed in the title bar.
	 * @param timHelp Information needed to display a dialog when the Help button is pressed.
     */
	public TimGUI(TimGame tGame, GameCanvas canvas, WidgetScroller ws, PlayerProfile pf, String guiName,TimHelp tHelp) {
        gameRef = tGame;
        wsRef = ws;
        pfProfile = pf;
		timHelp=tHelp;
        this.canvas = canvas;
        
        addTimGUIEventListener(tGame);
        gameRef.addTimEventListener(this);
        
		this.addContentToPane(this.getContentPane());
		this.setTitle(guiName);
		this.setSize(new Dimension(800, 600));
        this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch(Exception e) { } 

        menu = new TimMenuScreen(this);
                
        if (pfProfile.Check()== false) {
            pfProfile.Create();
        }
        else
            pfProfile.Load();
        
        menu.setName(pfProfile.getName());
		startMenu();
	}
    
    /**
     Displays the main tim start menu and options, after checking for a valid 
     player profile
     */
	public void startMenu() {
            gameRef.stopGame(false);
            scorePanel.setGoIcon();
            menu.setVisible(true);
            levelMenu = new TimLevelSelect(this, 5,pfProfile.getLevels());
            if (menu.getAction().equals(TimEvent.RESUME)) {
                if(level == null) {
                    guiEvent = new TimGUIEvent(this, TimEvent.SKIPLVL,pfProfile.getLevels());
                    fireTimGUIEvent(guiEvent, TimGUIEvent.TIMGAME);
                } else {
                    return;
                }
            }
            if (menu.getAction().equals(TimEvent.NEWGAME)) {
                scorePanel.setTimePenalty(0);
                guiEvent = new TimGUIEvent(this,TimEvent.NEWGAME,0);
                fireTimGUIEvent(guiEvent, TimGUIEvent.TIMGAME);
            }
            else if (menu.getAction().equals(TimEvent.SKIPLVL)) {
                //Skip to the level, using a password.
                scorePanel.setTimePenalty(0);
                levelMenu.setVisible(true);
                if(levelMenu.getLevelSelected() <= pfProfile.getLevels()) {
                    guiEvent = new TimGUIEvent(this, TimEvent.SKIPLVL,levelMenu.getLevelSelected());
                    fireTimGUIEvent(guiEvent, TimGUIEvent.TIMGAME);
                }
                else {
                    SingleInputDialog passDialog = new SingleInputDialog(this,"Level Password", "Enter Password: ");
                    passDialog.startDialog();
                    guiEvent = new TimGUIEvent(this,TimEvent.SKIPLVL,passDialog.getInput(), levelMenu.getLevelSelected());
                    fireTimGUIEvent(guiEvent, TimGUIEvent.TIMGAME);
                }
            }
            else if (menu.getAction().equals(TimEvent.FREEPLAY)) {
                scorePanel.setTimePenalty(0);
                guiEvent = new TimGUIEvent(this, TimEvent.FREEPLAY, 0);
                fireTimGUIEvent(guiEvent, TimGUIEvent.TIMGAME);
                setTimGUILabel(TimEvent.FREEPLAY);
            }
        else if (menu.getAction().equals(TimEvent.SETTINGS)) {
            TimSetting timsettings = new TimSetting(pfProfile.getPlayerSetting());
            timsettings.setVisible(true);
            pfProfile.update();
            guiEvent = new TimGUIEvent(this,TimEvent.SETTINGS);
            fireTimGUIEvent(guiEvent, TimGUIEvent.TIMGAME);
        }
		else if (menu.getAction().equals(TimEvent.EXIT)){                           
            guiEvent = new TimGUIEvent(this,TimEvent.EXIT);
            fireTimGUIEvent(guiEvent, TimGUIEvent.TIMGAME);
            
		}
	}
	
	/** Displays the help menu if clicked on based on current game state
	*	~Kiril
	**/
	public void DisplayHelp()
	{
		final JFrame HelpFrame= new JFrame("Help");
		final JDialog HelpDialog = new JDialog(HelpFrame,"Help",true);
		JPanel panel = new JPanel();
		JScrollPane pane = new JScrollPane();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		HelpDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        	HelpDialog.setLocationRelativeTo(this);
        	HelpDialog.setSize(450, 400);
		panel.add(Box.createRigidArea(new Dimension(0, 10)));

		JTextArea HelpContent = new JTextArea(timHelp.getHelpContent(menuPanel.getTimPanelLabel()));
		HelpContent.setEditable(false);
		HelpContent.setLineWrap(true);
		HelpContent.setBackground(HelpDialog.getBackground());
		
		pane.getViewport().add(HelpContent);
		panel.add(pane);
		JButton close = new JButton("Close");
		close.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) { HelpFrame.dispose();}});
		
		panel.add(close);
		HelpDialog.add(panel);
		HelpDialog.setVisible(true);

	}

	
    
    /**
     * Adds gui components to the content pane for display
	 * @param contentPane The parent container to display this from.
     */
	public void addContentToPane(Container contentPane) {
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout( new BoxLayout(bottomPanel, BoxLayout.X_AXIS) );
		bottomPanel.add(wsRef);
        
        scorePanel = new ScorePanel(this);
		bottomPanel.add(scorePanel);

		menuPanel = new TimMenuPanel(this);
        
        /* hack the timWorld canvas into a JPanel so it can be fitted with a nice pixel border */
        JPanel worldBorder = new JPanel();
        worldBorder.setBorder(new LineBorder(Color.black, 1));
        worldBorder.setLayout(new BoxLayout(worldBorder, BoxLayout.X_AXIS));
        worldBorder.add(canvas);
        
		contentPane.add(menuPanel, BorderLayout.NORTH);
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
		contentPane.add(worldBorder, BorderLayout.CENTER);
	}
    
    /**
     * Adds a class implementing the TimGUIEventLister to the list of tim gui listeners
     * that receive TimGUIEvents
     * @param tLis TimGUIEventListener to add
     */
    public synchronized void addTimGUIEventListener(TimGUIEventListener tLis) {
        timGUIListeners.add(tLis);
    }
    
    /**
     * Removes a class implementing the TimGUIEventLister from the list of timguilisteners
     * that receive TimGUIEvents
     *
     * @param tLis TimGUIEventListener to remove
     */
    public synchronized void removeTimGUIEventListener(TimGUIEventListener tLis) {
        timGUIListeners.remove(tLis);
    }
    
    /**
     * Fires a TimGUIEvent to the desired listener in tim gui listener list.
     *
     * @param tEvent the TimGUIEvent to fire
     * @param recvClass the receiving class as specified for a TimGUIEvent command
     */
    public synchronized void fireTimGUIEvent(TimGUIEvent tEvent, String recvClass) {
        TimGUIEventListener classListener;
        Iterator listeners = timGUIListeners.iterator();
        while(listeners.hasNext()) {
            classListener = (TimGUIEventListener)listeners.next();
            if(classListener.toString() == recvClass)
                classListener.timGUIEventReceived(tEvent);
        }
	}
    
    /**
     * Receives Events required from the controller tim game and handles the
     * appropriate view functionality for the event received including game updates,
     * stopping, and winning as well as menu flow.
     *
     * @param tEvent the TimEvent received
     */
    public void timEventReceived(TimEvent tEvent) {
        //System.out.println("TimGUI got event " + tEvent.getCommand() + " from TimGame");
        if(tEvent.getCommand().equals(TimEvent.UPDATE))  {
            level = tEvent.getPuzzle();
            if(level == null) {
                startMenu();
                return;
            }
            LevelSummaryDialog levSummary = new LevelSummaryDialog(this,level.getTitle(), level.getPass(), level.getSummary());
            levSummary.setVisible(true);
            setUnlockedLevels(tEvent.getData());
            setTimGUILabel(level.getTitle());
            scorePanel.setPlayerScore(pfProfile.getScore());
        }
        else if(tEvent.getCommand().equals(TimEvent.STOP)) 
            scorePanel.setGoIcon();
        else if(tEvent.getCommand().equals(TimEvent.BADPW)) {
            JOptionPane.showMessageDialog(this, "Bad Password");
        }
        else if(tEvent.getCommand().equals(TimEvent.WIN)) {
            scorePanel.setGoIcon();
            if(scorePanel.getTimePenalty() > level.getTime())
                scorePanel.setTimePenalty(level.getTime());
            ScoreDialog scoreDialog = new ScoreDialog(this, level.getTime() - scorePanel.getTimePenalty(), level.getScore(), 0, pfProfile.getScore());
            scoreDialog.setVisible(true);
            int nextLevel;
            scorePanel.setTimePenalty(0);
            if(level != null) {
                nextLevel = levelMenu.getUnlockedLevels() + 1;
                setUnlockedLevels(nextLevel);
                pfProfile.setLevels(nextLevel);
                pfProfile.setScore(scoreDialog.getNewUserScore());
                pfProfile.update();
                guiEvent = new TimGUIEvent(this,TimEvent.SKIPLVL, nextLevel);
                fireTimGUIEvent(guiEvent, TimGUIEvent.TIMGAME);
            }
                
        }
    }

    
    /**
     * Sets the label from the current level to be display on the game area header
     * 
     * @param title the title to be set on the top panel
     */
    public void setTimGUILabel(String title) {
        menuPanel.setTimPanelLabel(title);
    }
    
    /**
     * Sets the number of unlocked levels for the 
     * current TimLevelSelect menu
     * @param levels unlocked up to and including
     */
    public void setUnlockedLevels(int levels) {
        levelMenu.setUnlockedLevels(levels);
    }
    
    /**
     * String representation for this class
     * @return string representation
     */
    public String toString() {
        return TimGUIEvent.TIMGUI;
    }
    
}
