package model.projectiles;

public class Pellet extends Projectile {

	private static int speed = 4;
	private static boolean splash = false;
	private static int damage = 25;
	
	public Pellet(int xPos, int yPos, int xDes, int yDes) {
		super(xPos, yPos, xDes, yDes, speed, damage, splash);
		// TODO stuff
	}

}
