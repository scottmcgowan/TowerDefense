package model.enemies;

import java.awt.Point;
import java.util.ArrayList;

/**
 * A standard Enemy
 */
public class Grunt extends Enemy {
	
	private static int gruntSpeed = 10;
	private static int gruntHP = 100;
	private static int gruntDamage = 10;
	
	public Grunt(ArrayList<Point> initPath) {
		super(initPath, gruntSpeed, gruntHP, gruntDamage);
	}

	public Grunt(int xPos, int yPos) {
		super(xPos, yPos);
	}
	
	@Override
	public void attack() {
		// TODO Auto-generated method stub
		
	}

}