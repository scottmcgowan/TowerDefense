package model.projectiles;

public class Flame extends Projectile {
	
	private static int speed = 3;
	private static boolean splash = true;
	private static int damage = 30;

	public Flame(int xPos, int yPos, int xDes, int yDes) {
		super(xPos, yPos, xDes, yDes, speed, damage, splash);
		// TODO stuff
	}

}
