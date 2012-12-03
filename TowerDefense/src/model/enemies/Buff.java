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

	@Override
	public void attack() {
		// TODO Auto-generated method stub
	}

}