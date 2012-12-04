package model.towers;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import resources.Res;

import model.Drawable;

//TODO Make abstract after testing
public abstract class Tower extends Drawable {
	
	// Tower's location in super class
	// Point pos;
	
	// Fire rate
	private int fireRate;
	private int reloadCount;
	private boolean canFire;
	
	// Attack radius
	private double rangeRadius;
	
	// Range collision shape
	private Shape range;
	
	// Tower's collision shape
	private Shape cBox;
	
	/**
	 * 
	 * @param xPos
	 *            Leftmost point of the tower
	 * @param yPos
	 *            Topmost point of the tower
	 * @param fireRate
	 *            This tower's fire rate
	 * @param range
	 *            Range multiplier for this tower
	 */
	public Tower(int xPos, int yPos, int fireRate, double range) {
		super(new Point(xPos, yPos));
		
		canFire = true;
		this.fireRate = fireRate; // 60 = one shot per second
		reloadCount = 1;
		
		int width = Res.GRID_WIDTH;
		int height = Res.GRID_HEIGHT;
		
		rangeRadius = width * range;
		
		cBox = new Rectangle2D.Double(xPos, yPos, width, height);
		
		// Init position is top-left corner, 
		// so first add half the size of the tower to find the center point
		// Then subtract the radius to get upper-left bounds for range
		this.range = new Ellipse2D.Double(xPos + (width / 2) - rangeRadius, 
									yPos + (height / 2) - rangeRadius, 
									rangeRadius * 2, 
									rangeRadius * 2);
		
		pos = new Point(xPos, yPos);
	}
	
	
	// To determine if an enemy is range of the tower
	public Shape getRange() {
		return range;
	}
		
	public int getFireRate() {
		return fireRate;
	}

	// For enemy attacks
	public Shape getBounds() {
		return cBox;
	}
	
	public boolean canFire() {
		return canFire;
	}
	
	public void fire() {
		canFire = false;
		reloadCount = 1;
	}
	
	public void reload() {
		reloadCount += 1;
		if (reloadCount % fireRate == 0)
			canFire = true;
	}

	public abstract int getID();
	
}
