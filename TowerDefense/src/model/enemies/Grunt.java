package model.enemies;

import java.awt.Point;
import java.util.ArrayList;

/**
 * A standard Enemy
 */
public class Grunt extends Enemy {
	
	private static int gruntSpeed = 2;
	private static int gruntHP = 100;
	private static int gruntDamage = 10;
	
	public Grunt(ArrayList<Point> initPath) {
		super(initPath, gruntSpeed, gruntHP, gruntDamage);
	}

	public Grunt(int xPos, int yPos) {
		super(xPos, yPos);
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