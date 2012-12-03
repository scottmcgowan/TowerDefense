package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import model.Drawable;
import model.GameControllerInterface;
import model.enemies.Enemy;
import model.projectiles.Projectile;
import model.towers.Tower;
import GUI.Map.Tile;


// Main component to display the map
public class GameCanvas extends JLayeredPane{
	// Parameters for the game screen
	public static final int PANEL_WIDTH = 480;
	public static final int PANEL_HEIGHT = 360;

	private GameControllerInterface game;
	private BackgroundPanel backgroundPanel;
	private ActorPanel actorPane;
	Map map = new Map();
	private ArrayList<Point> path = new ArrayList<Point>();
	
	// Constructor
	public GameCanvas(GameControllerInterface gc) {
		setFocusable(true); // so that can receive key-events
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		requestFocus();
		setBackground(Color.WHITE);
		
		game = gc;
		actorPane = new ActorPanel();
		actorPane.setSize(PANEL_WIDTH, PANEL_HEIGHT);
		add(actorPane, JLayeredPane.MODAL_LAYER);
		backgroundPanel = new BackgroundPanel(gc);
		backgroundPanel.setSize(PANEL_WIDTH, PANEL_HEIGHT);
		add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);
		backgroundPanel.repaint();
		setPath(backgroundPanel.pathTrail());
	}
	
	public void drawDrawables(ArrayList<Drawable> arr){
		actorPane.drawDrawables(arr);
		actorPane.repaint();
	}
	
	public BackgroundPanel getBackgroundPanel(){
		return backgroundPanel;
	}

	public void addTower(int tileX, int tileY, int tower_type) {
		backgroundPanel.purchase(tileX, tileY, tower_type);
	}

	public ArrayList<Point> getPath() {
		return path;
	}

	public void setPath(ArrayList<Point> path) {
		this.path = path;
	}
}