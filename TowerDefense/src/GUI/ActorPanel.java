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

	public ActorPanel() {
		setOpaque(false);
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
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
				try {
					BufferedImage fireTower = ImageIO.read(new File("images/FireTower.png"));
					gr.drawImage(fireTower, (int) ((Tower) d).getPosition().getX(),
							(int) ((Tower) d).getPosition().getY(), this);
				} catch (IOException e) {
				}
				gr.setColor(Color.BLACK);
				gr.draw(getVisibleRect());
				gr.setColor(Color.blue);
				gr.draw(((Tower) d).getRange());
			} else if (d instanceof IceTower) {
				try {
					BufferedImage IceTower = ImageIO.read(new File("images/IceTower.png"));
					gr.drawImage(IceTower, (int) ((Tower) d).getPosition().getX(),
							(int) ((Tower) d).getPosition().getY(), this);
				} catch (IOException e) {
				}
				gr.setColor(Color.BLACK);
				gr.draw(getVisibleRect());
				gr.setColor(Color.blue);
				gr.draw(((Tower) d).getRange());
			} else if (d instanceof LightningTower) {
				try {
					BufferedImage LightningTower = ImageIO.read(new File("images/LightningTower.png"));
					gr.drawImage(LightningTower, (int) ((Tower) d).getPosition().getX(),
							(int) ((Tower) d).getPosition().getY(), this);
				} catch (IOException e) {
				}
				gr.setColor(Color.BLACK);
				gr.draw(getVisibleRect());
				gr.setColor(Color.blue);
				gr.draw(((Tower) d).getRange());
			}else if (d instanceof Enemy) {
				try {
					BufferedImage cirEnemy = ImageIO.read(new File("images/circenemy.png"));
					gr.drawImage(cirEnemy, (int) ((Enemy) d).getPosition().getX(),
							(int) ((Enemy) d).getPosition().getY(), this);
				} catch (IOException e) {
				}
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
