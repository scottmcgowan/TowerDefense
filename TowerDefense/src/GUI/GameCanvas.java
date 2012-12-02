package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

import model.GameControllerInterface;
import GUI.Map.Tile;

// Main component to display the map
public class GameCanvas extends JPanel implements KeyListener {
	// Parameters for the game screen
	public static final int PANEL_WIDTH = 480;
	public static final int PANEL_HEIGHT = 360;

	// Parameters of the game grid
	private int rows = 12;
	private int cols = 16;

	// Size of each square on grid
	private int gridWidth = PANEL_WIDTH / cols; // 30x30
	private int gridHeight = PANEL_HEIGHT / rows; // 30x30

	// Location of each square
	private int xTop = 0;
	private int yTop = 0;

	// GUI representation of each square
	private PaintSquare[][] gameMap = new PaintSquare[rows][cols];
	private GameCanvas canvas;

	private String selected = "";
	private Component clicked;
	private GameControllerInterface game;
	Map map = new Map();

	// Constructor
	public GameCanvas(GameControllerInterface gc) {
		game = gc;
		setFocusable(true); // so that can receive key-events
		requestFocus();
		addKeyListener(this);
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		// setLocation(0,0);
		setLayout(new GridLayout(12,16));
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
		for (int y = 0; y < map.getRow(); y++) {
			for (int x = 0; x < map.getCol(); x++) {
				JPanel temp = gameMap[y][x];
				temp.addMouseListener(new MouseClickListener());
				add(temp);
				temp.repaint();
			}
		}
	}

	private class PaintSquare extends JPanel {
		// Locations used to identify the component
		private int locationX;
		private int locationY;

		public int getTileX(){
			return locationX;
		}

		public int getTileY(){
			return locationY;
		}
		
		PaintSquare(int x, int y) {
			locationX = x;
			locationY = y;
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D gr = (Graphics2D) g;
			Tile current = map.tileMap[locationX][locationY];
			
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
			case ICE_TOWER:
				try {
					gr.drawImage(ImageIO.read(new File("images/IceTower.png")), 0,
							0, this);
				} catch (IOException e) {
					System.out.println("Could not find IceTower.png");
				}
				break;
			case FIRE_TOWER:
				try {
					gr.drawImage(ImageIO.read(new File("images/FireTower.png")), 0,
							0, this);
				} catch (IOException e) {
					System.out.println("Could not find FireTower.png");
				}
				break;
			case LIGHTNING_TOWER:
				try {
					gr.drawImage(ImageIO.read(new File("images/LightningTower.png")), 0,
							0, this);
				} catch (IOException e) {
					System.out.println("Could not find LightningTower.png");
				}
				break;
			}
		}
	}

	// Finds the path coordinates for enemy
	private Stack<Integer> pathTrail() {
		Stack<Integer> trail = new Stack<Integer>();

		// Push start points if start tile is on the horizontal walls
		for (int i = 0; i < map.tileMap[0].length; i++) {
			if (map.tileMap[0][i] == Tile.START) {
				trail.push(i * 30 + 15);
				trail.push(0);
			}
			if (map.tileMap[map.tileMap.length][i] == Tile.START) {
				trail.push(i * 30 + 15);
				trail.push(0);
			}
		}

		// Push start points if start tile is on the vertical walls
		for (int j = 0; j < map.tileMap.length; j++) {
			if (map.tileMap[j][0] == Tile.START) {
				trail.push(0);
				trail.push(j * 30 + 15);
			}
			if (map.tileMap[map.tileMap.length][j] == Tile.START) {
				trail.push(0);
				trail.push(j * 30 + 15);
			}
		}

		// Finds elements by traversing through the 8 squares around the current
		// square.
		// If the path has not been traversed, the middle coordinates will be
		// pushed into the stack
		Tile current = Tile.START;
		int currentX = trail.elementAt(0);
		int currentY = trail.elementAt(1);
		while (current != Tile.GOAL) {
			for (int i = currentX - 16; i <= currentX - 13; i++)
				for (int j = currentY - 16; j <= currentY - 13; j++) {
					if (i > -1 && i < map.tileMap.length && j > -1
							&& j < map.tileMap[0].length) {
						if (map.tileMap[j][i] != Tile.ENVIRONMENT
								&& (i * 30 + 15) != currentX
								&& (j * 30 + 15) != currentY) {
							current = map.tileMap[j][i];
							currentX = i * 30 + 15;
							currentY = j * 30 + 15;
							trail.push(currentX);
							trail.push(currentY);
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
	public void purchase() {
		for (int i = 0; i < gameMap.length; i++) {
			for (int j = 0; j < gameMap[i].length; j++) {
				if (clicked.equals(gameMap[i][j])) {
					if (map.tileMap[i][j] == Tile.ENVIRONMENT) {
						if (selected.equals("ICE_TOWER"))
							map.tileMap[i][j] = Tile.ICE_TOWER;
						if (selected.equals("FIRE_TOWER"))
							map.tileMap[i][j] = Tile.FIRE_TOWER;
						if (selected.equals("LIGHTNING_TOWER"))
							map.tileMap[i][j] = Tile.LIGHTNING_TOWER;
						repaint();
					}
				}
			}
		}
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
			game.notifyShopOfSelection(click.getTileX(),click.getTileY(),map.tileMap[click.getTileX()][click.getTileY()]);
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

	// Set the type of tower to be purchased
	public void setSelected(String n) {
		this.selected = n;
	}

	// Process a key-pressed event.
	public void gameKeyPressed(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_UP:
			// ......
			break;
		case KeyEvent.VK_DOWN:
			// ......
			break;
		case KeyEvent.VK_LEFT:
			// ......
			break;
		case KeyEvent.VK_RIGHT:
			// ......
			break;
		}
	}

	// Process a key-released event.
	public void gameKeyReleased(int keyCode) {
	}

	// Process a key-typed event.
	public void gameKeyTyped(char keyChar) {
	}

	// KeyEvent handlers
	@Override
	public void keyPressed(KeyEvent e) {
		gameKeyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		gameKeyReleased(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {
		gameKeyTyped(e.getKeyChar());
	}
}