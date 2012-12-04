
package model.enemies;

import java.awt.Point;
import java.util.ArrayList;

/**
 * A fast Enemy
 */
public class Speedy extends Enemy {
	
	private static int speedySpeed = 1;
	private static int speedyHP = 25;
	private static int speedyDamage = 5;

	public Speedy(ArrayList<Point> initPath) {
		super(initPath, speedySpeed, speedyHP, speedyDamage);
	}
	
	public int getSpriteCount() {
		int result = 0;
		if (counter < 4)
			result = 0;
		else if (counter < 4)
			result = 1;
		else if (counter < 8)
			result = 2;
		else
			counter = 0;
		return result;
	}
	
	@Override
	public void attack() {
		// TODO Auto-generated method stub
	}

}