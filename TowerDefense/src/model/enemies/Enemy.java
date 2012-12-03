package model.enemies;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import resources.Res;

import model.Drawable;

public abstract class Enemy extends Drawable {
	
	// Data fields every enemy must maintain

	// Position in super class
	// Point pos;
	
	// Enemy's initial health
	private int maxHP;
	
	// This enemy's current health
	private int hp;
	
	// Is the enemy active in game
	private boolean isAlive;
	
	// Movement speed
	private int framesPerMove;
	
	// Attack damage
	private int damage;
	
	// Attack radius
	private int radius;
	
	// The shape of this enemy for collision detection
	private Shape cBox;
	private int width;
	private int height;
	
	private ArrayList<Point> path;
	private int currentPath;
	
	private boolean canMove;
	private int moveCount;
	
	/**
	 * Create an enemy with an explicit starting position, strictly for testing
	 * purposes
	 * 
	 * @param xPos
	 *            Leftmost point of this enemy
	 * @param yPos
	 *            Topmost point of this enemy
	 */
	public Enemy(int xPos, int yPos) {
		super(new Point(xPos, yPos));
		this.framesPerMove = 1;
		
		hp = 100;
		maxHP = hp;
		damage = 10;
		
		isAlive = true;
		
		path = new ArrayList<Point>();
		
		width = Res.GRID_WIDTH;
		height = Res.GRID_HEIGHT;
		
		// TODO: For now enemies take up 1 grid space, may change
		cBox = new Rectangle2D.Double(xPos, yPos, xPos + width, yPos + height);
	}

	/**
	 * Enemy constructor
	 * 
	 * @param initPath
	 *            Point[] map path for this enemy to follow, where initPath[0]
	 *            is the starting location, and initPath[length-1] is the goal.
	 *            Points in this array are midpoints for each step in the path.
	 */
	public Enemy(ArrayList<Point> initPath, int speed, int hp, int damage) {
		super(initPath.get(0));
		
		this.hp = hp;
		this.maxHP = hp;
		this.damage = damage;
		
		canMove = true;
		this.framesPerMove = speed;
		moveCount = 1;
		
		isAlive = true;
		
		int width = Res.GRID_WIDTH;
		int height = Res.GRID_HEIGHT;
		
		path = initPath;
		currentPath = 0;
		
		// path contains midpoint coordinates, get the top-left point
//		int top = pos.y - (height / 2);
//		int left = pos.x - (width / 2);
		cBox = new Rectangle2D.Double(pos.x, pos.y, pos.x + width, pos.y + height);
		
	}
	
	public Shape getBounds() {
		return cBox;
	}
	
	public int getHP() {
		return hp;
	}
	
	public int getMaxHP() {
		return maxHP;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void kill() {
		isAlive = false;
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
	
	/**
	 * This getter is for testing
	 * 
	 * @return The frames that must pass for this enemy to move one pixel.
	 */
	public int getSpeed() {
		return framesPerMove;
	}
	
	public void setSpeed(int frames) {
		framesPerMove = frames;
	}
	
	/**
	 * @return true if this enemy can move this frame
	 */
	public boolean canMove() {
		return canMove;
	}
	
	/**
	 * The enemy is resting, wait to move
	 */
	public void rest() {
		moveCount += 1;
		if (moveCount % framesPerMove == 0)
			canMove = true;
	}
	
	// TODO: This might have bugs
	public void updatePosition() {
		if (currentPath < path.size() - 1 && isAlive && canMove) {
			
			// Set the temp destination
			Point next = path.get(currentPath + 1);
			
			// The enemy is moving horizontally
			if (pos.y == next.y) {
				if (pos.x < next.x) {
					pos.x += 1;
					if (pos.x >= next.x)
						currentPath++;
				}
				else {
					pos.x -= 1;
					if (pos.x <= next.x)
						currentPath++;
				}
			}
			
			// The enemy is moving vertically
			else if (pos.x == next.x) {
				if (pos.y < next.y) {
					pos.y += 1;
					if (pos.y >= next.y)
						currentPath++;
				}
				else {
					pos.y -= 1;
					if (pos.y <= next.y)
						currentPath++;
				}
			}
			
			if (currentPath >= path.size() - 1)
				isAlive = false;

			cBox = new Rectangle2D.Double(pos.x, pos.y, pos.x + width, pos.y + height);
			
			canMove = false;
			moveCount = 1;
		}
	}
	
	public abstract void attack();

}
