package model.projectiles;

public class Pellet extends Projectile {

	private static int speed = 4;
	private static boolean splash = false;
	
	public Pellet(int xPos, int yPos, int xDes, int yDes, int damage) {
		super(xPos, yPos, xDes, yDes, speed, damage, splash);
		// TODO stuff
	}

}
