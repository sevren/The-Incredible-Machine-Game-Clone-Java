//
//	TimMenuScreen.java
//
//
//	Created by Group Four on 16/10/08
//	Copyright 2008 Group Four. All rights reserved.
//
package tim.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Top header panel displaying game level title and buttons to access the game menu and help
 */
public class TimMenuPanel extends JPanel implements ActionListener {
	private String action;
	private JButton menuButton, helpButton;
    private JLabel statusLabel;
    private TimGUI timGUIRef;
    private Image img;
	/**
	 * Creates a menu panel for the game level
	 * @param parent The container to 'display' this from.
	 */
	public TimMenuPanel (Object parent) {
		super();
        timGUIRef = (TimGUI)parent;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setPreferredSize(new Dimension(800, 28));
        menuButton = new JButton("Game Menu");
        helpButton = new JButton("Help");
        statusLabel = new JLabel("User in Menu");
        statusLabel.setForeground(Color.white);
        statusLabel.setMaximumSize(new Dimension(610, 28));
        statusLabel.setPreferredSize(new Dimension(610, 28));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setVerticalAlignment(SwingConstants.CENTER);
        //nice brown colour header
        this.img =  new ImageIcon(getClass().getClassLoader().getResource("resources/images/TimMenuPanel.jpg")).getImage();
        
        menuButton.addActionListener(this);
        helpButton.addActionListener(this);
       
        this.add(menuButton);
		this.add(statusLabel);
		this.add(helpButton);
    }
	
    /**
     * Handles menu and help button actions
     * @param e event from button
     */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menuButton)
			timGUIRef.startMenu();
        else if (e.getSource() == helpButton) {
            timGUIRef.DisplayHelp();
        }
        this.repaint();
	}
    
    /**
     * Draw this component
     * @param g graphics context
     */
    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
       
    }
	
    /**
     Sets the tim panel header label to the string specified
     */
    public void setTimPanelLabel(String label) {
        statusLabel.setText(label);
    }
	
	/**
	 Gets the tim panel header label, mainly used for the help file
	 @return status-label text
	*/
	public String getTimPanelLabel()
	{
		return statusLabel.getText().toString();
	}
    
	/**
	 * Accessor - Returns the user's selection.
	 * @return The button that was chosen by the user.
	 */
	public String getAction()
	{
		return action;
	}
    
}
