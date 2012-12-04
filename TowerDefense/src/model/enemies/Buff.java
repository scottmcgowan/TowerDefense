package model.enemies;

import java.awt.Point;
import java.util.ArrayList;

/**
 * A strong Enemy
 */
public class Buff extends Enemy {

	private static int buffSpeed = 5;
	private static int buffHP = 200;
	private static int buffDamage = 50;
	
	public Buff(ArrayList<Point> initPath) {
		super(initPath, buffSpeed, buffHP, buffDamage);
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