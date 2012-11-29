package model;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

//TODO Make abstract after testing
public class Projectile extends Drawable {
	
	// Current location
	private Point pos;
	
	// End point
	private Point destination;
	
	// Pixels to move per frame
	private double speed;
	
	// Degrees movement
	private double direction;
	
	// Does this projectile burn out
	private boolean disposable;
	
	// If disposable, how long until burnout
	private int duration;

	// How much hp to subtract
	private int damage;
	
	// Collision box
	private Shape cBox;
	
	public Projectile(int xPos, int yPos, int xDes, int yDes) {
		
		speed = 10;
		damage = 10;
		disposable = false;
		pos = new Point(xPos, yPos);
		destination = new Point(xDes, yDes);
		
		int width = 10;
		int height = 10;
		
		// TODO: will need to adjust for GUI
		cBox = new Ellipse2D.Double(xPos, yPos, width, height);
	}
	
	public void updatePosition() {
		if (pos.x != destination.x && pos.y != destination.y) {
			if (destination.x > pos.x) {
				if (destination.y > pos.y)
					pos.y += speed;
				else
					pos.y -= speed;
				pos.x += speed;
			} else {
				if (destination.y > pos.y)
					pos.y += speed;
				else
					pos.y -= speed;
				pos.x -= speed;
			}
		}
	}
	
	public Shape getBounds() {
		return cBox;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public Point getPosition() {
		return pos;
	}

}
