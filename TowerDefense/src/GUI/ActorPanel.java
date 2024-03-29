package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

import resources.Res;

import model.Drawable;
import model.enemies.Buff;
import model.enemies.Enemy;
import model.enemies.Grunt;
import model.enemies.Speedy;
import model.projectiles.Flame;
import model.projectiles.IceBeam;
import model.projectiles.Lightning;
import model.projectiles.Projectile;
import model.towers.FireTower;
import model.towers.IceTower;
import model.towers.LightningTower;
import model.towers.Tower;

public class ActorPanel extends JPanel {

	// Parameters for the game screen
	public static final int PANEL_WIDTH = 480;
	public static final int PANEL_HEIGHT = 360;
	public ArrayList<Drawable> drawList = new ArrayList<Drawable>();

	private BufferedImage fireTower;
	private BufferedImage fireTower2;
	private BufferedImage fireTower3;
	private BufferedImage iceTower;
	private BufferedImage iceTower2;
	private BufferedImage iceTower3;
	private BufferedImage lightningTower;
	private BufferedImage lightningTower2;
	private BufferedImage lightningTower3;
	
	private BufferedImage lightning;
	private BufferedImage fire;
	private BufferedImage ice;
	private static int projectileSize = 10;

	private static final int CHAR_WIDTH = 30;
	private static final int CHAR_HEIGHT = 30;
	private BufferedImage enemySprites;
	private static final int spriteCount = 3;
	private int counter;

	private BufferedImage cirEnemy;

	public ActorPanel() {
		setOpaque(false);
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		loadImages();
		counter = 0;
	}

	public void loadImages() {
		try {
			fireTower = ImageIO.read(new File("images/FireTower.png"));
			fireTower2 = ImageIO.read(new File("images/FireTower2.png"));
			fireTower3 = ImageIO.read(new File("images/FireTower3.png"));
			iceTower = ImageIO.read(new File("images/IceTower.png"));
			iceTower2 = ImageIO.read(new File("images/IceTower2.png"));
			iceTower3 = ImageIO.read(new File("images/IceTower3.png"));
			lightningTower = ImageIO.read(new File("images/LightningTower.png"));
			lightningTower2 = ImageIO.read(new File("images/LightningTower2.png"));
			lightningTower3 = ImageIO.read(new File("images/LightningTower3.png"));
			cirEnemy = ImageIO.read(new File("images/circenemy.png"));
			
			fire = ImageIO.read(new File("images/FireProjectile.png"));
			lightning = ImageIO.read(new File("images/LightningProjectile.png"));
			ice = ImageIO.read(new File("images/IceProjectile.png"));
			
			enemySprites = ImageIO.read(new File("images/EnemySprites.png"));
		} catch (IOException e) {
			System.out.println("Error loading images!");
		}
	}

	public void drawDrawables(ArrayList<Drawable> arr) {
		drawList = arr;
		repaint();
	}

	public void paint(Graphics g) {

		super.paintComponent(g);
		Graphics2D gr = (Graphics2D) g;

		for (Drawable d : drawList) {
			if (d instanceof FireTower) {
				if (((FireTower) d).getLevel() == 1) {
					gr.drawImage(fireTower, (int) ((Tower) d).getPosition()
							.getX(), (int) ((Tower) d).getPosition().getY(), this);
				}
				else if (((FireTower) d).getLevel() == 2) {
					gr.drawImage(fireTower2, (int) ((Tower) d).getPosition()
							.getX(), (int) ((Tower) d).getPosition().getY(), this);
				}
				else if (((FireTower) d).getLevel() == 3) {
					gr.drawImage(fireTower3, (int) ((Tower) d).getPosition()
							.getX(), (int) ((Tower) d).getPosition().getY(), this);
				}
				gr.setColor(Color.BLACK);
				gr.draw(getVisibleRect());
				gr.setColor(Color.blue);
				gr.draw(((Tower) d).getRange());
			} else if (d instanceof IceTower) {
				if (((IceTower) d).getLevel() == 1) {
					gr.drawImage(iceTower, (int) ((Tower) d).getPosition()
							.getX(), (int) ((Tower) d).getPosition().getY(), this);
				}
				else if (((IceTower) d).getLevel() == 2) {
					gr.drawImage(iceTower2, (int) ((Tower) d).getPosition()
							.getX(), (int) ((Tower) d).getPosition().getY(), this);
				}
				else if (((IceTower) d).getLevel() == 3) {
					gr.drawImage(iceTower3, (int) ((Tower) d).getPosition()
							.getX(), (int) ((Tower) d).getPosition().getY(), this);
				}
				gr.setColor(Color.BLACK);
				gr.draw(getVisibleRect());
				gr.setColor(Color.blue);
				gr.draw(((Tower) d).getRange());
			} else if (d instanceof LightningTower) {
				if (((LightningTower) d).getLevel() == 1) {
					gr.drawImage(lightningTower, (int) ((Tower) d)
							.getPosition().getX(), (int) ((Tower) d)
							.getPosition().getY(), this);
				}
				else if (((LightningTower) d).getLevel() == 2) {
					gr.drawImage(lightningTower2, (int) ((Tower) d)
							.getPosition().getX(), (int) ((Tower) d)
							.getPosition().getY(), this);
				}
				else if (((LightningTower) d).getLevel() == 3) {
					gr.drawImage(lightningTower3, (int) ((Tower) d)
							.getPosition().getX(), (int) ((Tower) d)
							.getPosition().getY(), this);
				}
				gr.setColor(Color.BLACK);
				gr.draw(getVisibleRect());
				gr.setColor(Color.blue);
				gr.draw(((Tower) d).getRange());
			} else if (d instanceof Enemy) {
				int num = ((Enemy) d).getSpriteCount();
				int spriteX = 0;
				int spriteY = 0;
				
				// Set the Y coordinate on the sprite sheet
				if (((Enemy) d).getDirection() == Res.Dir.SOUTH)
					spriteY = 0;
				else if (((Enemy) d).getDirection() == Res.Dir.WEST)
					spriteY = CHAR_WIDTH;
				else if (((Enemy) d).getDirection() == Res.Dir.EAST)
					spriteY = CHAR_WIDTH * 2;
				else if (((Enemy) d).getDirection() == Res.Dir.NORTH)
					spriteY = CHAR_WIDTH * 3;
				
				// Get the correct block of sprite characters
				if (d instanceof Buff)
					spriteX = 0;
				else if (d instanceof Speedy)
					spriteX = CHAR_WIDTH * 3;
				else if (d instanceof Grunt)
					spriteX = CHAR_WIDTH * 6;
				
				// Display the right sequence of the animation
				if (num == 0)
					spriteX += 0;
				else if (num == 1)
					spriteX += CHAR_WIDTH;
				else if (num == 2)
					spriteX += CHAR_WIDTH * 2;
				
				gr.drawImage(enemySprites.getSubimage(spriteX, spriteY,
						CHAR_WIDTH, CHAR_HEIGHT), (int) ((Enemy) d)
						.getPosition().getX(), (int) ((Enemy) d)
						.getPosition().getY(), this);
				
				gr.setColor(Color.BLACK);
				gr.draw(getVisibleRect());
			} else if (d instanceof Lightning) {
				gr.drawImage(lightning, d.getX(), d.getY(), projectileSize, projectileSize, this);
			} else if (d instanceof Flame) {
				gr.drawImage(fire, d.getX(), d.getY(), projectileSize, projectileSize, this);
			} else if (d instanceof IceBeam) {
				gr.drawImage(ice, d.getX(), d.getY(), projectileSize, projectileSize, this);
			}
		} // end for
			
		// Draw health bars on top of everything else
		for (Drawable d : drawList) {
			if (d instanceof Enemy) {
				gr.setColor(Color.RED);
				gr.setStroke(new BasicStroke(3));
				gr.drawLine(d.getX(), d.getY(),
						d.getX() + ((Enemy) d).getWidth(), d.getY());

				double health = (((Enemy) d).getHP() / ((Enemy) d).getMaxHP())
						* ((Enemy) d).getWidth();
				int size = (int) Math.floor(health);
				gr.setColor(Color.GREEN);
				gr.drawLine(d.getX(), d.getY(), d.getX() + size, d.getY());

				gr.setStroke(new BasicStroke(1));
			}
		} // end for
	}
}
