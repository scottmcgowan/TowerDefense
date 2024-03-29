package model.enemies;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import model.Drawable;
import resources.Res;

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
	
	// Attack damage
	private int damage;
	
	// Attack radius
	private int radius;

	// Movement speed
	private int framesPerMove;
	private int maxFramesPerMove;
	private int slowCounter;
	protected int counter;
	
	// The shape of this enemy for collision detection
	private Shape cBox;
	private int width;
	private int height;
	
	private ArrayList<Point> path;
	private int currentPath;
	
	private boolean isSlowed;
	private boolean canMove;
	private int moveCount;
	
	private Res.Dir moving;
	
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
		
		moving = Res.Dir.EAST;
		
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
		isSlowed = false;
		slowCounter = 0;
		this.framesPerMove = speed;
		maxFramesPerMove = speed;
		moveCount = 1;
		counter = 0;
		
		isAlive = true;
		
		this.width = Res.GRID_WIDTH;
		this.height = Res.GRID_HEIGHT;
		
		path = initPath;
		currentPath = 0;
		
		// Magic numbers to pad enemy cBox from the sides of the path
		cBox = new Rectangle2D.Double(pos.x + 5, pos.y + 5, width - 10, height - 10);
		
	}
	
	public Shape getBounds() {
		return cBox;
	}
	
	public Res.Dir getDirection() {
		return moving;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHP() {
		return hp;
	}
	
	public double getMaxHP() {
		return maxHP;
	}
	
	public int getDamage() {
		return damage;
	}
	
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public boolean isSlowed() {
		return isSlowed;
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
	 * @return The frames that must pass for this enemy to move one pixel.
	 */
	public int getSpeed() {
		return maxFramesPerMove;
	}
	
	public void setSpeed(int frames) {
		framesPerMove = frames;
		
	}
	
	public void slow() {
		isSlowed = true;
		setSpeed(maxFramesPerMove + 1);
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
		if (isSlowed) {
			if (slowCounter > 200) {
				setSpeed(maxFramesPerMove);
				isSlowed = false;
				slowCounter = 0;
			} else
				slowCounter++;
		}
			
	}
	
	// TODO: This might have bugs
	public void updatePosition() {
		if (currentPath < path.size() - 1 && isAlive && canMove) {
			
			// Set the temp destination
			Point next = path.get(currentPath + 1);
			
			// The enemy is moving horizontally
			if (pos.y == next.y) {
				if (pos.x < next.x) {
					moving = Res.Dir.EAST;
					pos.x += 1;
					if (pos.x >= next.x)
						currentPath++;
				}
				else {
					moving = Res.Dir.WEST;
					pos.x -= 1;
					if (pos.x <= next.x)
						currentPath++;
				}
			}
			
			// The enemy is moving vertically
			else if (pos.x == next.x) {
				if (pos.y < next.y) {
					moving = Res.Dir.SOUTH;
					pos.y += 1;
					if (pos.y >= next.y)
						currentPath++;
				}
				else {
					moving = Res.Dir.NORTH;
					pos.y -= 1;
					if (pos.y <= next.y)
						currentPath++;
				}
			}
			
			if (currentPath >= path.size() - 1)
				isAlive = false;
			
			// Magic numbers to pad enemy cBox from the sides of the path
			cBox = new Rectangle2D.Double(pos.x + 5, pos.y + 5, width - 10, height - 10);
			
			canMove = false;
			counter++;
			rest();
		}
	}

	public abstract int getSpriteCount();
	
	public abstract void attack();

}
