package model.towers;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import model.Drawable;

//TODO Make abstract after testing
public abstract class Tower extends Drawable {

	public static final int NO_TYPE = 0;
	public static final int FIRE_TYPE = 1;
	public static final int ICE_TYPE = 2;
	public static final int LIGHTNING_TYPE = 3;
	
	// Tower's location
	private Point pos;
	
	// Fire rate
	private int rate;
	
	// Attack radius
	private int rangeRadius;
	
	// Range collision shape
	private Shape range;
	
	// Tower's collision shape
	private Shape cBox;
	
	/**
	 * 
	 * @param xPos Leftmost point of the tower
	 * @param yPos Topmost point of the tower
	 */
	public Tower(int xPos, int yPos) {
		pos = new Point(xPos, yPos);
		
		rate = 60;
		
		// TODO: Eliminate magic numbers.
		int width = 30;
		int height = 30;
		
		rangeRadius = 45;
		
		cBox = new Rectangle2D.Double(xPos, yPos, width, height);
		
		// Init position is top-left corner, 
		// so first add half the size of the tower to find the center point
		// Then subtract the radius to get upper-left bounds for range
		range = new Ellipse2D.Double(xPos + (width / 2) - rangeRadius, 
									yPos + (height / 2) - rangeRadius, 
									rangeRadius * 2, 
									rangeRadius * 2);
		
		pos = new Point(xPos, yPos);
	}
	
	public Point getPostion() {
		return pos;
	}
	
	// To determine if an enemy is range of the tower
	public Shape getRange() {
		return range;
	}
	
	// For enemy attacks
	public Shape getBounds() {
		return cBox;
	}
	
	public int getFireRate() {
		return rate;
	}
	
	// TODO: Let Game do this
//	public void attack() {
//		
//	}
	
}
