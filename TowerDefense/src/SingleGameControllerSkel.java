import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import model.Delivery;
import model.Drawable;
import model.GameControllerInterface;
import model.PurchaseOrder;
import GUI.GameCanvas;
import GUI.Map.Tile;
import GUI.SinglePlayerShopPanel;

public class SingleGameControllerSkel implements GameControllerInterface {

	// main game class
	static final int UPDATE_RATE = 60; // number of game updates per second
	static final long UPDATE_PERIOD = 1000000000L / UPDATE_RATE; // nanoseconds

	// State of the game
	boolean gameOver = false;
	boolean gamePaused = false;
	public int frameCounter = 0;
	public int secondCounter = 0;
	// public GameCanvas gameCanvas;
	private GameCanvas canvas;
	private SinglePlayerShopPanel shop;
	private JFrame gui = new JFrame();

	public static void main(String[] args) {
		SingleGameControllerSkel game = new SingleGameControllerSkel();
	}

	public static void wait(int n) {
		long t0, t1;
		t0 = System.currentTimeMillis();
		do {
			t1 = System.currentTimeMillis();
		} while ((t1 - t0) < 1000);
	}

	public SingleGameControllerSkel() {
		gui.setLayout(new FlowLayout());
		shop = new SinglePlayerShopPanel();
		canvas = new GameCanvas(this);
		shop.connectToMap(canvas);
		gui.setTitle("Game");
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(shop.PANEL_WIDTH + 50, canvas.PANEL_HEIGHT + shop.PANEL_HEIGHT + 100);
		gui.add(canvas);
		gui.add(shop);

		JMenuBar menubar = new JMenuBar();
		gui.setJMenuBar(menubar);

		JMenu fileMenu = new JMenu("File");
		menubar.add(fileMenu);

		JMenuItem newGame = new JMenuItem("New Game");
		JMenuItem exit = new JMenuItem("Exit");
		fileMenu.add(newGame);
		fileMenu.addSeparator();
		fileMenu.add(exit);
		newGame.addActionListener(new allMenuAction());
		exit.addActionListener(new allMenuAction());

		gui.setVisible(true);
		gui.repaint();

		gameStart();
	}

	private class allMenuAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JMenuItem menuItem = (JMenuItem) arg0.getSource();
			if (menuItem.getText() == "New Game") {
				SingleGameControllerSkel newGame = new SingleGameControllerSkel(); // Is this even acceptable...?
			}
			gui.dispose();
		}
	}

	public void gameStart() {
		// gui = new GameGUI();
		// Create a new thread
		Thread gameThread = new Thread() {
			// Override run() to provide the running behavior of this thread.
			@Override
			public void run() {
				gameLoop();
			}
		};
		// Start the thread. start() calls run(), which in turn calls
		// gameLoop().
		gameThread.start();
	}

	public void gameUpdate() {
		// get some gameLogic in here!
	}

	@Override
	// Run the game loop here.
	public void gameLoop() {
		// Regenerate the game objects for a new game
		// ......

		// Game loop
		long beginTime, timeTaken, timeLeft;
		while (true) {
			beginTime = System.nanoTime();
			if (gameOver)
				break; // break the loop to finish the current play
			if (!gamePaused) {
				// Update the state and position of all the game objects,
				// detect collisions and provide responses.
				gameUpdate();
			}
			// Refresh the display

			// Delay timer to provide the necessary delay to meet the target
			// rate
			timeTaken = System.nanoTime() - beginTime;
			timeLeft = (UPDATE_PERIOD - timeTaken) / 1000000; // in milliseconds
			if (timeLeft < 10)
				timeLeft = 10; // set a minimum
			try {
				// Provides the necessary delay and also yields control so that
				// other thread can do work.
				Thread.sleep(timeLeft);
			} catch (InterruptedException ex) {
			}
		}
	}

	@Override
	public void sendDelivery(Delivery d) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addOrder(PurchaseOrder po) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(ArrayList<Drawable> arr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processOrders() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawMapSelection() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawHealthBars() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyShopOfSelection(int tileX, int tileY, Tile tile) {
		// TODO Auto-generated method stub
		
	}
}
