package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import model.enemies.Enemy;
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
	
	private BufferedImage cirEnemy;

	public ActorPanel() {
		setOpaque(false);
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		loadImages();
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
			}else if (d instanceof Enemy) {
				gr.drawImage(cirEnemy, (int) ((Enemy) d).getPosition().getX(),
						(int) ((Enemy) d).getPosition().getY(), this);
				// Health bars
				gr.setColor(Color.RED);
				gr.drawLine(d.getX(), d.getY(), d.getX() + ((Enemy) d).getWidth(), d.getY());
				double health = (((Enemy) d).getHP() / ((Enemy) d).getMaxHP()) * ((Enemy) d).getWidth();
				int size = (int) Math.floor(health);
				gr.setColor(Color.GREEN);
				gr.drawLine(d.getX(), d.getY(), d.getX() + size, d.getY());
				gr.setColor(Color.BLACK);
				gr.draw(getVisibleRect());
			} else if (d instanceof Projectile) {
				gr.setColor(Color.black);
				gr.drawString("P", (int) ((Projectile) d).getPosition().getX(),
						(int) ((Projectile) d).getPosition().getY());
				gr.draw(((Projectile) d).getBounds());
			}
		}
	}
}
