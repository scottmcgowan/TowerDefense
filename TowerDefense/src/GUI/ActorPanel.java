package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Drawable;
import model.enemies.Enemy;
import model.projectiles.Projectile;
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
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D gr = (Graphics2D) g;

		for (Drawable d : drawList) {
			if (d instanceof Tower) {
				gr.setColor(Color.blue);
				gr.draw(((Tower) d).getRange());
				gr.setColor(Color.red);
				gr.drawString("T", (int) ((Tower) d).getPosition().getX(),
						(int) ((Tower) d).getPosition().getY());
			} else if (d instanceof Enemy) {
				try {
					gr.drawImage(ImageIO.read(new File("images/circenemy.png")), (int) ((Enemy) d).getPosition().getX(),
							(int) ((Enemy) d).getPosition().getY(), this);
				} catch (IOException e) {
					System.out.println("Could not find circenemy.png");
				}
				gr.drawString("E", (int) ((Enemy) d).getPosition().getX(),
						(int) ((Enemy) d).getPosition().getY());
			} else if (d instanceof Projectile) {
				gr.setColor(Color.black);
				gr.drawString("P", (int) ((Projectile) d).getPosition().getX(),
						(int) ((Projectile) d).getPosition().getY());
				gr.draw(((Projectile) d).getBounds());
			}
		}
	}

}
