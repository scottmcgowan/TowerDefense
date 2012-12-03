package model;

import java.awt.Point;

public abstract class Drawable {
	
	protected Point pos;
	
	public Drawable(Point position) {
		pos = new Point(position.x, position.y);
	}
	
	/**
	 * @return a Point representation of the location
	 */
	public Point getPosition() {
		return pos;
	}
	
	/**
	 * @return The x coordinate
	 */
	public int getX() {
		return pos.x;
	}
	
	/**
	 * @return the y coordinate
	 */
	public int getY() {
		return pos.y;
	}
	
}
