package model.enemies;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import model.Drawable;

public abstract class Enemy extends Drawable {
	
	// Data fields every enemy must maintain
	
	// Enemy's initial health
	private int maxHP;
	
	// This enemy's current health
	private int hp;
	
	// Is the enemy active in game
	private boolean isAlive;
	
	// Movement speed
	private int speed;
	
	// Attack damage
	private int damage;
	
	// Attack radius
	private int radius;
	
	// Position on the map
	private Point pos;
	
	// The shape of this enemy for collision detection
	private Shape cBox;
	
	private Point[] path;
	private int currentPath;
	
	/**
	 * Create an enemy with an explicit starting position, stritcly for testing
	 * purposes
	 * 
	 * @param xPos
	 *            Leftmost point of this enemy
	 * @param yPos
	 *            Topmost point of this enemy
	 */
	public Enemy(int xPos, int yPos) {
		
		hp = 100;
		maxHP = hp;
		damage = 10;
		
		isAlive = true;
		
		path = new Point[0];
		
		// TODO: Magic numbers
		int width = 30;
		int height = 30;
		
		// TODO: For now enemies take up 1 grid space, may change
		cBox = new Rectangle2D.Double(xPos, yPos, xPos + width, yPos + height);
		
		pos = new Point(xPos, yPos);
	}

	/**
	 * Enemy constructor
	 * 
	 * @param initPath
	 *            Point[] map path for this enemy to follow, where initPath[0]
	 *            is the starting location, and initPath[length-1] is the goal.
	 *            Points in this array are midpoints for each step in the path.
	 */
	public Enemy(Point[] initPath) {
		
		hp = 100;
		maxHP = hp;
		damage = 10;
		
		speed = 1;
		
		isAlive = true;
		
		// TODO: Magic numbers
		int width = 30;
		int height = 30;
		
		path = initPath;
		
		// path contains midpoint coordinates, get the top-left point
		int top = path[0].y - (height / 2);
		int left = path[0].x - (width / 2);
		cBox = new Rectangle2D.Double(left, top, left + width, top + height);
		
		pos = path[0];
		currentPath = 0;
	}
	
	public Shape getBounds() {
		return cBox;
	}
	
	public Point getPosition() {
		return pos;
	}
	
	public int getHP() {
		return hp;
	}
	
	public int getMaxHP() {
		return maxHP;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public int getDamage() {
		return damage;
	}
	
	/**
	 * Deal damage to this enemy
	 * 
	 * @param damage Amount of HP to subtract
	 * @return true if the damage dealt kills the enemy
	 */
	public boolean wound(int damage) {
		hp -= damage;
		if (hp <= 0)
			return true;
		return false;
	}
	
	// TODO: This might have bugs
	public void updatePosition() {
		if (currentPath < path.length - 1 && isAlive) {
			
			// Set the temp destination
			Point next = path[currentPath + 1];
			
			// The enemy is moving horizontally
			if (pos.y == next.y) {
				if (pos.x < next.x) {
					pos.x += speed;
					if (pos.x >= next.x)
						currentPath++;
				}
				else {
					pos.x -= speed;
					if (pos.x <= next.x)
						currentPath++;
				}
			}
			
			// The enemy is moving vertically
			else if (pos.x == next.x) {
				if (pos.y < next.y) {
					pos.y += speed;
					if (pos.y >= next.y)
						currentPath++;
				}
				else {
					pos.y -= speed;
					if (pos.y <= next.y)
						currentPath++;
				}
			}
			
			if (currentPath >= path.length - 1)
				isAlive = false;
		}
	}
	
	public void attack() {
		// TODO
	}

}
