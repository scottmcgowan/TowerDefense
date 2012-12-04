package model.projectiles;

public class Flame extends Projectile {
	
	private static int speed = 3;
	private static boolean splash = true;

	public Flame(int xPos, int yPos, int xDes, int yDes, int damage) {
		super(xPos, yPos, xDes, yDes, speed, damage, splash);
		// TODO stuff
	}

}
