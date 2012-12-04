package model;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import resources.Res;

import model.enemies.Enemy;
import model.projectiles.*;
import model.towers.Tower;


/**
 * Contains primary game logic
 */
public class Game {

	private ArrayList<Tower> towerList = new ArrayList<Tower>();
	
	private ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	
	private ArrayList<Projectile> projectileList = new ArrayList<Projectile>();
	
	// A list of all objects to be drawn
	private ArrayList<Drawable> drawable = new ArrayList<Drawable>();
	
	// false if game is in play
	private boolean gameOver;
	
	// frame counter for update()
	private int counter;
	
	// Player Health
	private int playerHealth = 100;
	
	private int playerMoney;
	
	
	public Game() {
		towerList = new ArrayList<Tower>();
		enemyList = new ArrayList<Enemy>();
		projectileList = new ArrayList<Projectile>();
		drawable = new ArrayList<Drawable>();
		
		gameOver = false;
		
		counter = 0;
		
		playerMoney = 0;
	}
	
	
	public ArrayList<Tower> getTowers() {
		return towerList;
	}
	
	
	public ArrayList<Enemy> getEnemies() {
		return enemyList;
	}
	
	
	public ArrayList<Projectile> getProjectiles() {
		return projectileList;
	}
	
	
	/**
	 * @return A list of all drawable game objects in the order to be drawn
	 */
	public ArrayList<Drawable> getDrawable() {
		drawable = new ArrayList<Drawable>();
		drawable.addAll(towerList);
		drawable.addAll(enemyList);
		drawable.addAll(projectileList);
		return drawable;
	}
	
	
	/**
	 * @return true is the game is over
	 */
	public boolean gameOver() {
		return gameOver;
	}
	
	public void setGameOver(boolean over){
		gameOver = over;
	}
	
	
	/**
	 * @return int value of the player's current health. Health <= 0 is gameOver
	 */
	public int getPlayerHealth() {
		return playerHealth;
	}
	
	/**
	 * @return How much money the player gains in a single frame
	 */
	public int getFunds() {
		return playerMoney;
	}
	
	/**
	 * Allows the Game Controller to set the playerHealth.
	 * 
	 * @param health
	 *            int value for the player's health
	 * @return false if health parameter is zero or negative
	 */
	public boolean setPlayerHealth(int health) {
		if (health <= 0)
			return false;
		playerHealth = health;
		return true;
	}
	
	
	/**
	 * Updates all game logic, to be called every frame
	 */
	public void update() {
		
		if (gameOver)
			return;
		
		playerMoney = 0;
		
		// Temp lists to keep track of enemys/projectiles to remove from the game
		ArrayList<Projectile> tempProj = new ArrayList<Projectile>();
		ArrayList<Enemy> tempEnemy = new ArrayList<Enemy>();
		
		// Move all enemies first
		for (Enemy enemy : enemyList) {
			if (enemy.canMove()) {
				enemy.updatePosition();
			} else {
				enemy.rest();
			}
			
			// An enemy has died, but no shots were fired,
			// this enemy has reached the goal
			
			if (!enemy.isAlive()) {
				tempEnemy.add(enemy);
				// Enemy scored, decrement player health
				playerHealth -= enemy.getDamage();
				if (playerHealth <= 0) {
					gameOver = true;
					return;
				}
			}
		}
		
		// Cleanup enemy list
		enemyList.removeAll(tempEnemy);
		
		// Fire any available projectiles second
		for (Tower tower : towerList) {
			
			// First check that the tower can fire, to avoid unnecessary collision detection
			if (tower.canFire()) {
				
				for (Enemy enemy: enemyList) {
					if (tower.getRange().intersects((Rectangle2D) enemy.getBounds())) {
						
						Point pos = new Point(tower.getPosition());
						// point projectile to middle of grid space
						pos.x += Res.GRID_WIDTH / 2;
						pos.y += Res.GRID_HEIGHT / 2;
						
						Point des = new Point(enemy.getPosition());
						// point projectile to middle of grid space
						des.x += Res.GRID_WIDTH / 2;
						des.y += Res.GRID_HEIGHT / 2;
						
						int id = tower.getID();
						if (id == Res.TOWER_NO_TYPE)
							addProjectile(new Pellet(pos.x, pos.y, des.x, des.y, tower.getDamage()));							
						else if (id == Res.TOWER_FIRE_TYPE)
							addProjectile(new Flame(pos.x, pos.y, des.x, des.y, tower.getDamage()));
						else if (id == Res.TOWER_ICE_TYPE)
							addProjectile(new IceBeam(pos.x, pos.y, des.x, des.y, tower.getDamage()));
						else if (id == Res.TOWER_LIGHTNING_TYPE)
							addProjectile(new Lightning(pos.x, pos.y, des.x, des.y, tower.getDamage()));
						tower.fire();
						
						// this tower has fired, move on to the next one
						break;
					}
				} // end inner
			} else
				tower.reload(); // The tower is waiting to fire
		} // end outer
		
		// Check for projectile collision last
		for (Projectile projectile : projectileList) {
			projectile.updatePosition();
			
			for (Enemy enemy : enemyList) {
				if (projectile.getBounds().intersects((Rectangle2D) enemy.getBounds())) {
					
					// Deal damage, if enemy is killed remove enemy and add funds
					if (enemy.wound(projectile.getDamage())) {
						tempEnemy.add(enemy);
						enemy.kill();
						playerMoney += 25;
					}
					
					// Destroy projectile
					if (!tempProj.contains(projectile))
						tempProj.add(projectile);
					
					// Break the loop if this projectile should only affect one enemy
					if (!projectile.isSplash())
						break;
				} 
			}
			
			if (!projectile.isAlive()) {
				if (!tempProj.contains(projectile))
					tempProj.add(projectile);
			}
		}
		
		// Cleanup lists
		projectileList.removeAll(tempProj);
		enemyList.removeAll(tempEnemy);
		
		// Increment frame counter for tower fireRate calculations
		counter++;
	}
	
	/**
	 * Upgrades the tower at a tile position
	 * @param x
	 * @param y
	 */
	public void upgradeTower(int x, int y) {
		System.out.println("upgrade called");
		for (Tower t : towerList) {
			if (t.getTileX()==x && t.getTileY()==y) {
				t.upgrade();
				System.out.println("upgrade");
				break;
			}
		}
	}
	
	
	/**
	 * Add an enemy to the list of active enemies
	 * @param enemy The Enemy object to add to the game
	 */
	public void addEnemy(Enemy enemy) {
		enemyList.add(0, enemy);
	}
	
	
	/**
	 * Add a tower to the list of active towers
	 * @param tower The Tower object to add to the game
	 */
	public void addTower(Tower tower) {
		towerList.add(tower);
	}
	
	
	/**
	 * Add a Projectile to the list of active projectiles
	 * @param proj The Projectile object to add to the game
	 */
	public void addProjectile(Projectile proj) {
		projectileList.add(proj);
	}
	
}
