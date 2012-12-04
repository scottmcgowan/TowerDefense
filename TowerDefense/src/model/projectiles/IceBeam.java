package model.projectiles;

public class IceBeam extends Projectile {
	
	private static int speed = 3;
	private static boolean splash = false;
	private static int damage = 30;

	public IceBeam(int xPos, int yPos, int xDes, int yDes) {
		super(xPos, yPos, xDes, yDes, speed, damage, splash);
		// TODO stuff
	}

}
