package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import model.*;

import org.junit.Test;

public class GameTest {

	@Test
	public void testDrawableList() {
		Game game = new Game();
		
		// add 3 projectiles, 2 enemies, 2 towers
		game.addProjectile(new Projectile(0, 0, 75, 75));
		game.addProjectile(new Projectile(0, 0, 100, 100));
		game.addEnemy(new Enemy(0, 0));
		game.addTower(new Tower(0, 0));
		game.addEnemy(new Enemy(50, 50));
		game.addTower(new Tower(50, 50));
		game.addProjectile(new Projectile(0, 0, 50, 50));
		
		// check order of the list
		assertTrue(game.getDrawable().get(0) instanceof Tower);
		assertTrue(game.getDrawable().get(1) instanceof Tower);
		assertTrue(game.getDrawable().get(2) instanceof Enemy);
		assertTrue(game.getDrawable().get(3) instanceof Enemy);
		assertTrue(game.getDrawable().get(4) instanceof Projectile);
		assertTrue(game.getDrawable().get(5) instanceof Projectile);
		assertTrue(game.getDrawable().get(6) instanceof Projectile);
		
		System.out.println(game.getDrawable().toString());
	}

}
