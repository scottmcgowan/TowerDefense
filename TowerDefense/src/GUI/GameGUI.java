package GUI;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GameGUI extends JFrame {
	
	//Parameters for the JFrame
	static final int CANVAS_WIDTH = 800;
	static final int CANVAS_HEIGHT = 600;

	//Parameters for the game screen
	private static final int GAME_WIDTH = 360;
	private static final int GAME_HEIGHT = 480;

	//Parameters of the game grid
	private int rows = 12;
	private int cols = 16;

	//Size of each square on grid
	private int gridWidth = GAME_WIDTH / rows;
	private int gridHeight = GAME_HEIGHT / cols;

	//Location of each square
	private int xTop = 0;
	private int yTop = 0;

	//GUI representation of each square
	private JPanel[][] game = new JPanel[rows][cols];
	private GameCanvas canvas;

	// Constructor to initialize the UI components and game objects
	public GameGUI() {
		// Initialize the game objects
		gameInit();

		// UI components
		canvas = new GameCanvas();

		canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		this.setContentPane(canvas);

		// Other UI components such as button, score board, if any.
		// ......

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setTitle("MY GAME");
		this.setVisible(true);
	}

	// ------ All the game related codes here ------

	// Initialize all the game objects, run only once.
	public void gameInit() {
	}

	// Start and re-start the game.
	public void gameStart() {
	}

	// Shutdown the game, clean up code that runs only once.
	public void gameShutdown() {
	}

	// One step of the game.
	public void gameUpdate() {
	}

	// Refresh the display after each step.
	// Use (Graphics g) as argument if you are not using Java 2D.
	public void gameDraw(Graphics2D g2d) {
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

	// Custom drawing panel, written as an inner class.
	class GameCanvas extends JPanel implements KeyListener {
		Map map = new Map();

		// Constructor
		public GameCanvas() {
			setFocusable(true); // so that can receive key-events
			requestFocus();
			addKeyListener(this);
			setLayout(null);

			setBackground(Color.WHITE);
			
			//Initialize a new path
			int[][] path = new int[map.getRow()][map.getCol()];
			for (int row = 0; row < path.length; row++) {
				for (int col = 0; col < path[row].length; col++) {
					path[row][col] = 0;
				}
			}

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

			for (int i = 0; i < map.getRow(); i++) {
				for (int j = 0; j < map.getCol(); j++) {
					JPanel e = new PaintSquare(i, j);
					game[i][j] = e;
				}
			}

			for (int y = 0; y < map.getRow(); y++) {
				yTop += gridHeight + 1;
				xTop = gridWidth + 1;
				for (int x = 0; x < map.getCol(); x++) {
					JPanel temp = game[y][x];
					temp.setSize(gridWidth + 1, gridHeight + 1);
					temp.setLocation(xTop, yTop);
					temp.addMouseListener(new MouseClickListener());
					xTop += gridWidth + 1;
					add(temp);
					temp.repaint();
				}
			}
		}

		private class PaintSquare extends JPanel {
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
				}
				else if (map.map[locationX][locationY] == Tile.TOWER) {
					gr.setColor(Color.BLACK);
					gr.drawRect(0, 0, gridWidth, gridHeight);
					gr.setColor(Color.BLUE);
					gr.fillRect(1, 1, gridWidth - 1, gridHeight - 1);
				}
			}
		}
		
		private class MouseClickListener implements MouseListener {

			/**
			 * Handles the event of a mouse clicking a specific listening component.
			 * Determines which component was clicked, and sends the appropriate calls to Game().
			 * 
			 * @param arg0 Mouse click event information.
			 */
			@Override
			public void mouseClicked(MouseEvent arg0) {
					Component clicked =  arg0.getComponent();
					for (int i = 0; i < game.length; i++) {
						for (int j = 0; j < game[i].length; j++) {
							if(clicked.equals(game[i][j])) {
								if(map.map[i][j] == Tile.ENVIRONMENT) {
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

	
	// main
	public static void main(String[] args) {
		// Use the event dispatch thread to build the UI for thread-safety.
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GameGUI();
			}
		});
	}
}