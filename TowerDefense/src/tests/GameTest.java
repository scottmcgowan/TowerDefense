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
		
//		System.out.println(game.getDrawable().toString());
	}
	
	@Test
	public void testTowerRange() {
		
		// Note: tower init position is top-left corner,
		// center tower at (0, 0)
		Tower tower = new Tower(-15, -15);
		System.out.println(tower.getRange().getBounds2D().getWidth());
		System.out.println(tower.getRange().getBounds2D().getHeight());
		
		double centerX = tower.getRange().getBounds2D().getCenterX();
		double centerY = tower.getRange().getBounds2D().getCenterY();
		
		System.out.println(centerX);
		System.out.println(centerY);
		
		System.out.println(tower.getRange().getBounds2D().getMaxX());
		System.out.println(tower.getRange().getBounds2D().getMaxY());
		
		assertFalse(tower.getRange().getBounds().contains(new Point(-46, -46))); // top-left bounds
		assertTrue(tower.getRange().getBounds().contains(new Point(-45, -45))); // top-left
		assertFalse(tower.getRange().getBounds().contains(new Point(45, -45))); // top-right bounds
		assertTrue(tower.getRange().getBounds().contains(new Point(44, -45))); // top-right
		assertFalse(tower.getRange().getBounds().contains(new Point(-46, 45))); // bottom-left bounds
		assertTrue(tower.getRange().getBounds().contains(new Point(-45, 44))); // bottom-left
		assertFalse(tower.getRange().getBounds().contains(new Point(45, 45))); // bottom-right bounds
		assertTrue(tower.getRange().getBounds().contains(new Point(44, 44))); // bottom-right
	}
	
	@Test
	public void testUpdateEnemyHP() {
		Game game = new Game();
		
		Tower tower = new Tower(0, 0);
		game.addTower(tower);
		Enemy enemy = new Enemy(5, 5);
		game.addEnemy(enemy);
		
		assertEquals(100, game.getEnemies().get(0).getHP());
		game.update();
		assertEquals(90, game.getEnemies().get(0).getHP());
		
		// Tower can't fire again yet, HP still 90
		game.update();
		assertEquals(90, game.getEnemies().get(0).getHP());
		
		// Let one second pass
		for (int i = 0; i < tower.getFireRate() - 2; i++) {
			game.update();
		}
		
		// Tower fires again
		assertEquals(90, game.getEnemies().get(0).getHP());
		game.update();
		assertEquals(80, game.getEnemies().get(0).getHP());
		
		// Keep firing
		for (int i = 0; i < tower.getFireRate() * 8 - 1; i ++) {
			game.update();
		}
		
		// Kill the enemy
		assertEquals(10, game.getEnemies().get(0).getHP());
		game.update();
		assertEquals(0, game.getEnemies().size());
	}
	
	@Test
	public void testUpdateProjectile() {
		Projectile proj = new Projectile(0, 0, 50, 50);
		assertEquals(new Point(0, 0), proj.getPosition());
		proj.updatePosition();
		assertEquals(new Point(10, 10), proj.getPosition());
		proj.updatePosition();
		assertEquals(new Point(20, 20), proj.getPosition());
		proj.updatePosition();
		proj.updatePosition();
		proj.updatePosition();
		assertEquals(new Point(50, 50), proj.getPosition());
		proj.updatePosition();
		assertEquals(new Point(50, 50), proj.getPosition());
	}

}
