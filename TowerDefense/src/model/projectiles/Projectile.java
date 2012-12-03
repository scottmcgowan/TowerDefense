package model.projectiles;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import model.Drawable;

public abstract class Projectile extends Drawable {
	
	// Current location in super class
	//	Point pos;
	
	// End point
	private Point destination;
	
	// Pixels to move per frame
	private double speed;
	
	// Does this projectile burn out
	private boolean disposable;
	
	// If disposable, how long until burnout
	private int duration;

	// How much hp to subtract
	private int damage;
	
	// Collision box
	private Shape cBox;
	
	private boolean isAlive;
	
	public Projectile(int xPos, int yPos, int xDes, int yDes) {
		
		super(new Point(xPos, yPos));
		
		speed = 10;
		damage = 10;
		disposable = false;
		isAlive = true;
		destination = new Point(xDes, yDes);
		
		int width = 10;
		int height = 10;
		
		// TODO: will need to adjust for GUI
		cBox = new Ellipse2D.Double(xPos, yPos, width, height);
	}
	
	public void updatePosition() {
		if (pos.x != destination.x && pos.y != destination.y) {
			
			int x = destination.x - pos.x;
			int y = destination.y - pos.y;
			Point2D vect = new Point(x, y);
			
			double distance = pos.distance(destination);

			double xDis = vect.getX() / distance;
			double yDis = vect.getY() / distance;

			vect.setLocation(xDis * speed, yDis * speed);

			pos.setLocation(pos.x + vect.getX(), pos.y + vect.getY());
		} else if (pos.x == destination.x && pos.y == destination.y) {
			isAlive = false;
		}
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public Shape getBounds() {
		return cBox;
	}
	
	public int getDamage() {
		return damage;
	}

}
