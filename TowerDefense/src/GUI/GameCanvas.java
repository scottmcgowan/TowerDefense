package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import GUI.Map.Tile;

// Main component to display the map
class GameCanvas extends JPanel implements KeyListener {
	// Parameters for the game screen
	private static final int GAME_WIDTH = 360;
	private static final int GAME_HEIGHT = 480;

	// Parameters of the game grid
	private int rows = 12;
	private int cols = 16;

	// Size of each square on grid
	private int gridWidth = GAME_WIDTH / rows;	//30x30
	private int gridHeight = GAME_HEIGHT / cols; //30x30

	// Location of each square
	private int xTop = 0;
	private int yTop = 0;

	// GUI representation of each square
	private JPanel[][] game = new JPanel[rows][cols];
	private GameCanvas canvas;

	private int selected = 0;

	Map map = new Map();

	// Constructor
	public GameCanvas() {
		setFocusable(true); // so that can receive key-events
		requestFocus();
		addKeyListener(this);
		setSize(GAME_HEIGHT, GAME_WIDTH);
		setLocation(0,0);
		setLayout(null);

		setBackground(Color.WHITE);

		// Initialize a new path
		int[][] path = new int[map.getRow()][map.getCol()];
		for (int row = 0; row < path.length; row++) {
			for (int col = 0; col < path[row].length; col++) {
				path[row][col] = 0;
			}
		}

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
				JPanel e = new PaintSquare(i, j);
				game[i][j] = e;
			}
		}

		// Sets each component's dimensions and adds the component to the
		// container
		for (int y = 0; y < map.getRow(); y++) {
			if (y == 0) {
			} else
				yTop += gridHeight;
			xTop = 0;
			for (int x = 0; x < map.getCol(); x++) {
				JPanel temp = game[y][x];
				temp.setSize(gridWidth, gridHeight);
				temp.setLocation(xTop, yTop);
				temp.addMouseListener(new MouseClickListener());
				xTop += gridWidth;
				add(temp);
				temp.repaint();
			}
		}
	}

	private class PaintSquare extends JPanel {
		// Locations used to identify the component
		int locationX;
		int locationY;

		PaintSquare(int x, int y) {
			locationX = x;
			locationY = y;
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D gr = (Graphics2D) g;

			if (map.map[locationX][locationY] == Tile.ENVIRONMENT) {
				gr.setColor(Color.BLACK);
				gr.drawRect(0, 0, gridWidth, gridHeight);
				gr.setColor(Color.GRAY);
				gr.fillRect(1, 1, gridWidth - 1, gridHeight - 1);
			} else if (map.map[locationX][locationY] == Tile.PATH) {
				gr.setColor(Color.BLACK);
				gr.drawRect(0, 0, gridWidth, gridHeight);
				gr.setColor(Color.YELLOW);
				gr.fillRect(1, 1, gridWidth - 1, gridHeight - 1);
			} else if (map.map[locationX][locationY] == Tile.START) {
				gr.setColor(Color.BLACK);
				gr.drawRect(0, 0, gridWidth, gridHeight);
				gr.setColor(Color.GREEN);
				gr.fillRect(1, 1, gridWidth - 1, gridHeight - 1);
			} else if (map.map[locationX][locationY] == Tile.GOAL) {
				gr.setColor(Color.BLACK);
				gr.drawRect(0, 0, gridWidth, gridHeight);
				gr.setColor(Color.RED);
				gr.fillRect(1, 1, gridWidth - 1, gridHeight - 1);
			} else if (map.map[locationX][locationY] == Tile.TOWER) {
				if(selected == 1) {
					gr.setColor(Color.BLACK);
					gr.drawRect(0, 0, gridWidth, gridHeight);
					gr.setColor(Color.BLUE);
					gr.fillRect(1, 1, gridWidth - 1, gridHeight - 1);
				}

				if (selected == 2) {
					gr.setColor(Color.BLACK);
					gr.drawRect(0, 0, gridWidth, gridHeight);
					gr.setColor(Color.CYAN);
					gr.fillRect(1, 1, gridWidth - 1, gridHeight - 1);
				}

				if (selected == 3) {
					gr.setColor(Color.BLACK);
					gr.drawRect(0, 0, gridWidth, gridHeight);
					gr.setColor(Color.MAGENTA);
					gr.fillRect(1, 1, gridWidth - 1, gridHeight - 1);
				}

				if (selected == 4) {
					gr.setColor(Color.BLACK);
					gr.drawRect(0, 0, gridWidth, gridHeight);
					gr.setColor(Color.ORANGE);
					gr.fillRect(1, 1, gridWidth - 1, gridHeight - 1);
				}

				selected = 0;
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
			Component clicked = arg0.getComponent();
			for (int i = 0; i < game.length; i++) {
				for (int j = 0; j < game[i].length; j++) {
					if (clicked.equals(game[i][j]) && selected != 0) {
						if (map.map[i][j] == Tile.ENVIRONMENT) {
							map.map[i][j] = Tile.TOWER;
							repaint();
						}
					}
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
	}

	protected void setSelected(int n) {
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