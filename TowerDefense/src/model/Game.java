package model;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Queue;


/**
 * Contains primary game logic
 */
public class Game {

	private ArrayList<Tower> towerList;
	
	private ArrayList<Enemy> enemyList;
	
	private ArrayList<Projectile> projectileList;
	
	// A list of all objects to be drawn
	private ArrayList<Drawable> drawable;
	
	// false if game is in play
	private boolean gameOver;
	
	// frame counter for update()
	private int counter;
	
	
	public Game() {
		towerList = new ArrayList<Tower>();
		enemyList = new ArrayList<Enemy>();
		projectileList = new ArrayList<Projectile>();
		drawable = new ArrayList<Drawable>();
		
		gameOver = false;
		
		counter = 0;
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
	
	public boolean gameOver() {
		return gameOver;
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
	
	public void update() {
		
		if (gameOver)
			return;
		
		// Move all enemies first
		for (Enemy enemy : enemyList) {
			enemy.updatePosition();
		}
		
		// Fire any available projectiles second
		for (Tower tower : towerList) {
			
			// First check that the tower can fire, to avoid unnecessary collision detection
			if (counter % tower.getFireRate() == 0) {
				
				for (Enemy enemy: enemyList) {
					if (tower.getRange().intersects((Rectangle2D) enemy.getBounds())) {
						
						Point pos = tower.getPostion();
						Point des = enemy.getPosition();
						addProjectile(new Projectile(pos.x, pos.y, des.x, des.y));
						
						// this tower has fired, move on to the next one
						break;
					}
				} // end inner
			}
		} // end outer
		
		// Check for projectile collision last
		for (Projectile projectile : projectileList) {
			projectile.updatePosition();
			
			for (Enemy enemy : enemyList) {
				if (projectile.getBounds().intersects((Rectangle2D) enemy.getBounds())) {
					
					// Deal damage, remove enemy if damage kills it
					if (enemy.wound(projectile.getDamage()))
						enemyList.remove(enemy);
					
					// Destroy projectile
					projectileList.remove(projectile);
				}
			}
		}
		
		// Increment frame counter for tower fireRate calculations
		counter++;
	}
	
	/**
	 * Add an enemy to the list of active enemies
	 * @param enemy The Enemy object to add to the game
	 */
	public void addEnemy(Enemy enemy) {
		enemyList.add(enemy);
	}
	
	/**
	 * Add a tower to the list of active towers
	 * @param tower The Tower object to add to the game
	 */
	public void addTower(Tower tower) {
		towerList.add(tower);
	}
	
	public void addProjectile(Projectile proj) {
		projectileList.add(proj);
	}
	
}
