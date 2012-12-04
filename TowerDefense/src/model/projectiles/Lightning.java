package model.projectiles;

public class Lightning extends Projectile {
	
	private static int speed = 6;
	private static boolean splash = false;
	
	public Lightning(int xPos, int yPos, int xDes, int yDes, int damage) {
		super(xPos, yPos, xDes, yDes, speed, damage, splash);
		// TODO stuff
	}

}
