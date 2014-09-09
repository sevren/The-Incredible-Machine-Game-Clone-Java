// Place this in a folder called Widgets
// Do not change this file at all when implementing the API,
// to ensure that your copy is the same as the one used in marking!

package Widgets;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import net.phys2d.math.*;
import net.phys2d.raw.*;

/**
 * The Widget API, which defines the functionality
 * of a "puzzle piece" in a game.
 *
 * <h1>Important!</h1>
 * <b>ALL IMPLEMENTATIONS MUST PROVIDE A DEFAULT CONSTRUCTOR
 * TO MAKE THEIR WIDGET RUN ACROSS GROUPS.</b>
 * <i>ie.</i> if your class is MyWidget, you need a public MyWidget() constructor.
 *
 * Don't call resetWidget() in your constructor, instead expect calling code to
 * perform a widget.resetWidget() before activating.
 *
 * Additionally, create all Bodies and Joints in your constructor,
 * and then don't change your variables, or bad things can happen in calling code.
 */
public interface Widget
{
	/**
		The category a Widget belongs to, so we can categorize and such in toolbars.
		Impact: does something on impact
		Explosion: detonates (possibly on impact), and can "blow up" static widgets
		Bounce: bounces around the world
		Wind: produces a wind force which moves widgets around.
		Static: acts like a wall.
	*/
	enum ActionType { IMPACT, EXPLOSION, BOUNCE, WIND, STATIC };
	
	/** An enumeration of on-screen directions this widget may face. */
	enum Direction { NORTH, SOUTH, EAST, WEST };

	/**
	 * Set the position of the widget.
	 * @param f  The x,y coordinates within the
	 * container.
	 */
	void setPosition(Vector2f f);

	/**
	 * Set the top-left x-coordinate of the widget.
	 * Should be equivalent to setPosition(x, getPositionY());
	 * @param x  The x coordinate within the container.
	 */
	void setPositionX(float x);

	/**
	 * Set the top-left y-coordinate of the widget.
	 * Should be equivalent to setPosition(getPositionX(), y);
	 * @param y  The y coordinate within the container.
	 */
	void setPositionY(float y);

	/**
	 * Get the top-left position of the widget.
	 * @return   The x,y coordinates within the container.
	 */
	Vector2f getPosition();

	/**
	 * Get the top-left x-coordinate of the widget.
	 * Should be equivalent to getPosition().getX();
	 * @return   The x coordinate within the container.
	 */
	float getPositionX();

	/**
	 * Get the top-left y-coordinate of the widget.
	 * Should be equivalent to getPosition().getY();
	 * @return   The y coordinate within the container.
	 */
	float getPositionY();

	/**
	 * Draws the widget.
	 * @param g  The graphic to draw onto.
	 */
	void draw (Graphics2D g);

	/**
	 * Activates the widget.
	 * Must refuse activation before resetWidget is called at least once.
	 * Must be called by the engine for every widget when the gameplay start.
	 */
	void activateWidget ();

	/**
	 * Resets the widget to start state.
	 * Should reinitialize everything to with all bodies associated with this.
	 * Should reinitialize its own internal state information as well.
	 * Ensure that the caller invokes this once before allowing
	 * activateWidget() to do something.
	 * Should probably call each body's set(Shape s, float mass) in here.
	 */
	void resetWidget();

	/**
	 * Rotates the Widget 90 Degrees in a
	 * clockwise direction.
	 *
	 * Here is a compass, with numberings showing the order of rotation:
	 *
	 *        NORTH 2
	 *          |
	 * WEST 1 - o - EAST 3
	 *          |
	 *        SOUTH 4
	 *
	 *
	 * In other words, this rotation should under "conventional" circumstances go:
	 * WEST -> NORTH -> EAST -> SOUTH -> WEST -> ...
	 *
	 * Note: For widgets that may only be flipped horizontally or vertically,
	 * you could have it do the following:
	 * NORTH -> SOUTH -> NORTH -> ...
	 * or WEST -> EAST -> WEST -> ...
	 * and for widgets with no notion of "direction", do nothing.
	 */
	void rotateClockwise();

	/**
	 * Rotates the Widget 90 Degrees in a
	 * counter-clockwise direction.
	 *
	 * Here is a compass, with numberings showing the order of rotation:
	 *
	 *        NORTH 4
	 *          |
	 * WEST 1 - o - EAST 3
	 *          |
	 *        SOUTH 2
	 *
	 * In other words, this rotation should under "conventional" circumstances go:
	 * WEST -> SOUTH -> EAST -> NORTH -> WEST -> ...
	 *
	 * Note: For widgets that may only be flipped horizontally or vertically,
	 * you could have it do the following:
	 * NORTH -> SOUTH -> NORTH -> ...
	 * or WEST -> EAST -> WEST -> ...
	 * and for widgets with no notion of "direction", do nothing.
	 *
	 */
	void rotateCounterClockwise ();

	/**
	 * Manually set the direction of which
	 * the Widget is facing.
	 *
	 * Note: For widgets that may only be flipped horizontally or vertically
	 * (or don't have a notion of "direction") the widget can ignore the request.
	 * This means that no change to direction should occur in the case of
	 * an invalid direction for the widget.
	 *
	 * Basically, depending on the widget, setDirection may not do anything when
	 * supplied a direction.
	 *
	 * @param d Direction to rotate the Widget
	 */
	void setDirection(Direction d);

	/**
	 * Gets the direction in which the Widget is currently facing
	 * @return The direction that the widget is facing. Should not be null!
	 *		So, pick an arbitrary direction for directionless widgets on construction.
	 */
	Direction getDirection ();

	/**
	 * Determines if the widget is active.
	 * @return whether or not the widget is active.
	 */
	boolean isActive();

	/**
	 * Gets the name of the widget.
	 * @return The name of the widget.
	 */
	String getName ();

	/**
	 * Gets the type of the widget. This should be part of ActionType.
	 * @return The ActionType that the widget is categorized as.
	 */
	ActionType getType();

	/**
	 * What happens with the widget when two bodies touch.
	 *
	 * Ensure that the widget carefully handles
	 * collision events between its own bodies.
	 *
	 * @param e The collision event that was triggered by a collision.
	 */
	void reactToTouchingBody(CollisionEvent e);

	/**
	 * If this widget can connect to joints or not.
	 * If it doesn't make sense to have your widget swinging around, make this false.
	 */
	boolean isConnectable();

	/**
	 * Sets the points where joints will connect to this widget.
	 * If the widget is not connectable, do nothing.
	 * @param points The points within the widget that designate
	 * where joints connect to.
	 */
	void setConnectionPoints(Vector2f[] points);

	/**
	 * Whether or not the widget is locked and cannot be moved.
	 * Lock state should not be a fixed value, so setLock does something
	 * @return If the widget is locked (true) or not (false).
	 */
	boolean isLocked();

	/**
	 * Sets the lock state of the widget.
	 * Lock state returned by isLocked should reflect the change.
	 * @param locked If the widget is to be locked (true) or
	 * not (false).
	 */
	void setLock(boolean locked);

	/**
	 * The point that a joint is attempting to connect to the
	 * widget. What is returned is the closest connection point.
	 * If the widget is not connectable, return null
	 * @param point The x,y coordinates where the joint is attempting to
	 * connected to.
	 * @return The connection point that the joint needs to be connected to,
	 * 		or null if Widget is not connectable.
	 */
	Vector2f attachJoint (Vector2f point);

	/**
	 * Returns an array of the Bodies this widget has, for use in Phys2D engine.
	 * The Body instances returned should stay the same across calls.
	 * This means create all Body objects you need when calling the constructor.
	 * @return An array of bodies.
	 */
	Body[] getBodiesForSimulation ();

	/**
	 * Returns an array of the Joints this widget has, for use in Phys2D engine.
	 * The Joint instances returned should stay the same across calls.
	 * This means create all Joint objects you need when calling the constructor.
	 * @return An array of joints.
	 *     If you don't use any, you can return either null or an empty array.
	 */
	Joint[] getJointsForSimulation ();

	/**
	 * Remote widget activation based from a connected Joint.
	 * @param anchor_point The x,y coordinates of the connected Joint.
	 */
	void receiveImpulse(Vector2f anchor_point);

	/**
	 * Gets the four boundary vertices of the widget,
	 * which chould be used as a way to detect overlapping widgets.
	 * The corner array must be in the following (clockwise) order:
	 *      corner[0]: top-left corner
	 *      corner[1]: top-right corner
	 *      corner[2]: bottom-right corner
	 *	    corner[3]: bottom-left corner
	 *	
	 *	To get width of the rectangular bound you go: corner[2].getX() - corner[0].getX()
	 *	To get height of the rectangular bound you go: corner[2].getY() - corner[0].getY()
	 * @return The four corners x,y coordinates.
	 */
	Vector2f[] getBoundary();

	/**
	 * Retrieves the description of the widget.
	 * @return The small blurb describing what the widget does.
	 */
	String getDescription ();

	/**
	 * Retrieves the images associated with the widget.
	 * The image should not exceed 100x100.
	 * @return The image which is linked with the widget.
	 */
	ImageIcon getIcon ();
}
