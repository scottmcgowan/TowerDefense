package model;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

// TODO Make abstract after testing
public class Enemy extends Drawable {
	
	// Data fields every enemy must maintain
	
	// This enemy's health
	private int hp;
	
	// Movement speed
	private int speed;
	
	// Attack damage
	private int attack;
	
	// Attack radius
	private int radius;
	
	// Position on the map
	private Point pos;
	
	// The shape of this enemy for collision detection
	private Shape cBox;
	
	
	public Enemy(int xPos, int yPos) {
		
		hp = 100;
		
		// TODO: Magic numbers
		int width = 30;
		int height = 30;
		
		// TODO: For now enemies take up 1 grid space, may change
		cBox = new Rectangle2D.Double(xPos, yPos, xPos + width, yPos + height);
		
		pos = new Point(xPos, yPos);
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
	
	public void updatePosition() {
		// TODO: Path finding
	}
	
	public void attack() {
		// TODO
	}

}
