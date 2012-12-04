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
	
	// Does this projectile cause splash damage
	private boolean splash;
	
	// If disposable, how long until burnout
	private int duration;

	// How much hp to subtract
	private int damage;
	
	// Collision box
	private Shape cBox;
	private int width;
	private int height;
	private Point2D vector;
	private boolean isAlive;
	
	public Projectile(int xPos, int yPos, int xDes, int yDes, int speed, int damage, boolean splash) {
		
		super(new Point(xPos, yPos));
		
		isAlive = true;
		disposable = false;
		this.speed = speed;
		this.damage = damage;
		this.splash = splash;
		destination = new Point(xDes, yDes);
		
		width = 10;
		height = 10;
		
		int x = destination.x - pos.x;
		int y = destination.y - pos.y;
		vector = new Point(x, y);
		
		double distance = pos.distance(destination);

		double xDis = vector.getX() / distance;
		double yDis = vector.getY() / distance;

		vector.setLocation(xDis * speed, yDis * speed);
		// TODO: will need to adjust for GUI
		cBox = new Ellipse2D.Double(xPos, yPos, width, height);
	}
	
	public void updatePosition() {
			
			pos.setLocation(pos.x + vector.getX(), pos.y + vector.getY());
			cBox = new Ellipse2D.Double(pos.x, pos.y, width, height);
			
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public boolean isSplash() {
		return splash;
	}
	
	public Shape getBounds() {
		return cBox;
	}
	
	public int getDamage() {
		return damage;
	}

}
