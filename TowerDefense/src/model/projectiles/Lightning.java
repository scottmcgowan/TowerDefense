package model.projectiles;

public class Lightning extends Projectile {
	
	private static int speed = 6;
	private static boolean splash = false;
	private static int damage = 25;
	
	public Lightning(int xPos, int yPos, int xDes, int yDes) {
		super(xPos, yPos, xDes, yDes, speed, damage, splash);
		// TODO stuff
	}

}
