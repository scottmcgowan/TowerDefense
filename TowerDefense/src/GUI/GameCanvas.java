package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JLayeredPane;

import model.Drawable;
import model.GameControllerInterface;


// Main component to display the map
public class GameCanvas extends JLayeredPane{
	// Parameters for the game screen
	public static final int PANEL_WIDTH = 480;
	public static final int PANEL_HEIGHT = 360;

	private GameControllerInterface game;
	private BackgroundPanel backgroundPanel;
	private ActorPanel actorPane;
	Map map = new Map();
	private final ArrayList<Point> path;
	private boolean paintMap = true;
	
	// Constructor
	public GameCanvas(GameControllerInterface gc) {
		setFocusable(true); // so that can receive key-events
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		requestFocus();
		setBackground(Color.WHITE);
		setIgnoreRepaint(true);
		
		game = gc;
		actorPane = new ActorPanel();
		actorPane.setSize(PANEL_WIDTH, PANEL_HEIGHT);
		add(actorPane, JLayeredPane.MODAL_LAYER);
		backgroundPanel = new BackgroundPanel(gc);
		backgroundPanel.setSize(PANEL_WIDTH, PANEL_HEIGHT);
		add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);
		backgroundPanel.repaint();
		path = backgroundPanel.pathTrail();	
	}

	public void drawDrawables(ArrayList<Drawable> arr){
		actorPane.drawDrawables(arr);
	}
	
	public void optimizeBakcground(){
		backgroundPanel.saveBufferedImage();
	}

	public BackgroundPanel getBackgroundPanel(){
		return backgroundPanel;
	}

	
	@Override
	public void paintComponent(Graphics g){
		//super.paintComponent(g);
		/*
		if(paintMap){
		g.setClip(new Rectangle(0,0,1000,1000));
		paintChildren(g);
		paintMap = false;}
		else{
			g.setClip(new Rectangle(0,0,0,0));}*/
		//paintMap=false;}
		//paintComponent(g);
	}
	
	public void addTower(int tileX, int tileY, int tower_type) {
		backgroundPanel.purchase(tileX, tileY, tower_type);
	}

	public ArrayList<Point> getPath() {
		return path;
	}
}