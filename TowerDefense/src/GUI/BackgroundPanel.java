package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

import resources.Res;

import model.Drawable;
import model.GameControllerInterface;
import GUI.Map.Tile;


// Main component to display the map
public class BackgroundPanel extends JPanel implements KeyListener {
	// Parameters for the game screen
	public static final int PANEL_WIDTH = 480;
	public static final int PANEL_HEIGHT = 360;
	public ArrayList<Drawable> drawList = new ArrayList<Drawable>();

	// Parameters of the game grid
	private int rows = 12;
	private int cols = 16;

	// Size of each square on grid
	private int gridWidth = PANEL_WIDTH / cols; // 30x30
	private int gridHeight = PANEL_HEIGHT / rows; // 30x30

	// Location of each square
	private int xTop = 0;
	private int yTop = 0;
	private int timer = 0;

	// GUI representation of each square
	private PaintSquare[][] gameMap = new PaintSquare[rows][cols];
	private Component clicked;
	private GameControllerInterface game;
	private BufferedImage background;
	Map map = new Map();
	private boolean bufferSaved = false;
	
	// Constructor
	public BackgroundPanel(GameControllerInterface gc) {
		game = gc;
		setFocusable(true); // so that can receive key-events
		addKeyListener(this);
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setLayout(new GridLayout(12,16));
		requestFocus();
		setBackground(Color.WHITE);
		// Initialize a new path
		int[][] path = new int[map.getRow()][map.getCol()];

		// Example path; 2 = start, 1 = path, 3 = end
		path[5][0] = 2;
		path[5][1] = 1;
		path[5][2] = 1;
		path[5][3] = 1;
		path[2][3] = 1;
		path[3][3] = 1;
		path[4][3] = 1;
		path[2][4] = 1;
		path[2][5] = 1;
		path[2][6] = 1;
		path[2][7] = 1;
		path[3][7] = 1;
		path[4][7] = 1;
		path[5][7] = 1;
		path[6][7] = 1;
		path[6][8] = 1;
		path[6][9] = 1;
		path[6][10] = 1;
		path[3][10] = 1;
		path[4][10] = 1;
		path[5][10] = 1;
		path[3][11] = 1;
		path[3][12] = 1;
		path[3][13] = 1;
		path[4][13] = 1;
		path[5][13] = 1;
		path[6][13] = 1;
		path[7][13] = 1;
		path[8][13] = 1;
		path[8][14] = 1;
		path[8][15] = 3;

		map.setPath(path);

		// Creates each square component according to the map and puts it into a
		// component array that represents the map
		for (int i = 0; i < map.getRow(); i++) {
			for (int j = 0; j < map.getCol(); j++) {
				PaintSquare e = new PaintSquare(i, j);
				gameMap[i][j] = e;
			}
		}

		// Sets each component's dimensions and adds the component to the
		// container
		rerenderBackground();
	}

	public void saveBufferedImage(){
		if(!bufferSaved){
			background = new BufferedImage(PANEL_WIDTH, PANEL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
			// Render the component and all its sub components
			paintAll(background.getGraphics());
		    paint(background.getGraphics());
			
			try {
				ImageIO.write(background, "png", new File("test.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			timer = 0;
			System.out.println("saved");
			bufferSaved = true;
		}
	}
	
	@Override
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		if(!bufferSaved){
		paintChildren(g);
		paintComponents(g);
		}
		else{
			g2.drawImage(background, 0, 0, null);
		}
	}
	
	public void rerenderBackground(){

		for (int y = 0; y < map.getRow(); y++) {
			for (int x = 0; x < map.getCol(); x++) {
				Component temp = gameMap[y][x];
				temp.addMouseListener(new MouseClickListener());
				add(temp);
				temp.repaint();
			}
		}
		repaint();
	}
	
	private class PaintSquare extends JComponent {
		// Locations used to identify the component
		private int locationX;
		private int locationY;

		//Fix these!!!!
		public int getTileX(){
			return locationX;
		}

		public int getTileY(){
			return locationY;
		}
		
		PaintSquare(int y, int x) {
			locationX = x;
			locationY = y;
			setIgnoreRepaint(true);
		}
		
		public void paint(Graphics g) {
			
			Graphics2D gr = (Graphics2D) g;
			//System.out.println("repainsdasdasting");
			
			Tile current = map.tileMap[locationY][locationX];
			
			switch (current) {
			case ENVIRONMENT:
				try {
					gr.drawImage(ImageIO.read(new File("images/grass.png")), 0,
							0, this);
				} catch (IOException e) {
					System.out.println("Could not find grass.png");
				}
				break;
			case PATH:
				try {
					gr.drawImage(ImageIO.read(new File("images/path.png")), 0,
							0, this);
				} catch (IOException e) {
					System.out.println("Could not find path.png");
				}
				break;
			case START:
				try {
					gr.drawImage(ImageIO.read(new File("images/start.png")), 0,
							0, this);
				} catch (IOException e) {
					System.out.println("Could not find start.png");
				}
				break;
			case GOAL:
				try {
					gr.drawImage(ImageIO.read(new File("images/goal.png")), 0,
							0, this);
				} catch (IOException e) {
					System.out.println("Could not find goal.png");
				}
				break;
//			case ICE_TOWER:
//				try {
//					gr.drawImage(ImageIO.read(new File("images/IceTower.png")), 0,
//							0, this);
//				} catch (IOException e) {
//					System.out.println("Could not find IceTower.png");
//				}
//				break;
//			case FIRE_TOWER:
//				try {
//					gr.drawImage(ImageIO.read(new File("images/FireTower.png")), 0,
//							0, this);
//				} catch (IOException e) {
//					System.out.println("Could not find FireTower.png");
//				}
//				break;
//			case LIGHTNING_TOWER:
//				try {
//					gr.drawImage(ImageIO.read(new File("images/LightningTower.png")), 0,
//							0, this);
//				} catch (IOException e) {
//					System.out.println("Could not find LightningTower.png");
//				}
//				break;
			}
		}
	}
	
	// Finds the path coordinates for enemy
	public ArrayList<Point> pathTrail() {
		ArrayList<Point> trail = new ArrayList<Point>();

		// Push start points if start tile is on the horizontal walls
		for (int i = 0; i < map.tileMap[0].length; i++) {
			if (map.tileMap[0][i] == Tile.START || map.tileMap[map.tileMap.length-1][i] == Tile.START) {
				trail.add(new Point((i*30), 0));
			}
		}

		// Push start points if start tile is on the vertical walls
		for (int j = 0; j < map.tileMap.length; j++) {
			if (map.tileMap[j][0] == Tile.START || map.tileMap[j][map.tileMap[0].length-1] == Tile.START) {
				trail.add(new Point(0, (j*30)));
			}
		}

		// Finds elements by traversing through the 8 squares around the current
		// square.
		// If the path has not been traversed, the middle coordinates will be
		// pushed into the stack
		Tile current = Tile.START;
		Point currentPoint = trail.get(0);
		int pointY = currentPoint.y, pointX = currentPoint.x;
		
		while (current != Tile.GOAL) {	
			if(currentPoint.x >= 30)
				pointX = currentPoint.x;
				pointX /= 30;
			
			if(currentPoint.y >= 30)
				pointY = currentPoint.y;
				pointY /=  30;
			
			for (int i = pointY - 1; i <= pointY + 1; i++)
				for (int j = pointX - 1; j <= pointX + 1; j++) {
					
					Point check = new Point((j*30), (i*30));
					if (i > -1 && i < map.tileMap.length && j > -1 && j < map.tileMap[0].length) {
						if((j == pointX && (i == pointY-1 || i == pointY+1)) || (i == pointY && (j == pointX-1 || j == pointX+1)))
						if (map.tileMap[i][j] != Tile.ENVIRONMENT && trail.contains(check) == false) {
							current = map.tileMap[i][j];
							currentPoint = new Point((j * 30),(i * 30));
							trail.add(currentPoint);
						}
					}
				}
		}
		return trail;
	}
	
	public Component getClicked() {
		return clicked;
	}

	// Makes sure an environment tile is clicked and then does a tower purchase
	public void purchase(int tileX, int tileY, int towerType) {
		switch(towerType){
		case Res.TOWER_FIRE_TYPE:
			map.tileMap[tileY][tileX] = Tile.FIRE_TOWER;
			break;
		case Res.TOWER_ICE_TYPE:
			map.tileMap[tileY][tileX] = Tile.ICE_TOWER;
			break;
		case Res.TOWER_LIGHTNING_TYPE:
			map.tileMap[tileY][tileX] = Tile.LIGHTNING_TOWER;
			break;
		}
//		bufferSaved = false;
		gameMap[tileY][tileX].repaint();
	}

	private class MouseClickListener implements MouseListener {
		/**
		 * Handles the event of a mouse clicking a specific listening component.
		 * Determines which component was clicked, and sends the appropriate
		 * calls to Game().
		 * 
		 * @param arg0
		 *            Mouse click event information.
		 */
		@Override
		public void mouseClicked(MouseEvent arg0) {
			clicked = arg0.getComponent();
			PaintSquare click = (PaintSquare)(arg0.getComponent());
			System.out.println(click.getTileX());
			System.out.println(click.getTileY());
			game.notifyShopOfSelection(click.getTileX(),click.getTileY(),map.tileMap[click.getTileY()][click.getTileX()]);
			repaint();
		}

		public void mouseEntered(MouseEvent arg0) {
			Component hover = arg0.getComponent();
				((JComponent) hover).setBorder(BorderFactory.createLineBorder(Color.YELLOW));
		}

		public void mouseExited(MouseEvent arg0) {
			Component hover = arg0.getComponent();
			((JComponent) hover).setBorder(BorderFactory.createEmptyBorder());
		}

		public void mousePressed(MouseEvent arg0) {
		}

		public void mouseReleased(MouseEvent arg0) {
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}