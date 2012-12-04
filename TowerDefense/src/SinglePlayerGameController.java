import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import network.MultiPlayerShop;

import model.Delivery;
import model.Drawable;
import model.Game;
import model.GameControllerInterface;
import model.Player;
import model.PurchaseOrder;
import model.SinglePlayerShop;
import model.enemies.Buff;
import model.enemies.Enemy;
import model.enemies.Grunt;
import model.enemies.Speedy;
import model.towers.FireTower;
import model.towers.IceTower;
import model.towers.LightningTower;
import model.towers.Tower;
import resources.Res;
import GUI.GameCanvas;
import GUI.LogisticsPanel;
import GUI.MainMenu;
import GUI.Map.Tile;
import GUI.SinglePlayerShopPanel;

public class SinglePlayerGameController implements GameControllerInterface {

	// main game class
	private static final int UPDATE_RATE = 60; // number of game updates per second
	private static final long UPDATE_PERIOD = 1000000000L / UPDATE_RATE; // nanoseconds
	private static final int PLAYER_INT = 1;
	
	// State of the game
	private Player user = new Player();
	private Game game;
	private boolean gameOver = false;
	private boolean gamePaused = false;
	private GameCanvas canvas;
	private JFrame gui = new JFrame();
	private SinglePlayerShopPanel shop;
	private LinkedList<PurchaseOrder> listOfOrders = new LinkedList<PurchaseOrder>();
	private LinkedList<Enemy> spawnQueue = new LinkedList<Enemy>();
	private int timer;
	private int frameCounter = 0;
	private int secondCounter = 0;
	private int spawnTimer;
	private int logisticTimer;
	private int currentTileX;
	private int currentTileY;
	private int towerCount;
	private LogisticsPanel stats;

	public static void main(String[] args) {
		JFrame menu = new MainMenu();
		SinglePlayerGameController game = new SinglePlayerGameController();
	}
	
	public static void wait(int n) {
		long t0, t1;
		t0 = System.currentTimeMillis();
		do {
			t1 = System.currentTimeMillis();
		} while (t1 - t0 < 1000);
	}
	
	public void winGame() {
		gameOver = true;
		System.out.println("YOU ARE WINNER");
	}
	
	public void tieGame() {
		gameOver = true;
		System.out.println("Game tied.");
	}
	
	public void loseGame() {
		gameOver = true;
		System.out.println("You lost the game.");
	}
	
	public boolean hasLost() {
		return game.gameOver();
	}
	
	public boolean checkForTie() {
		int userMoney = user.getMoney();
		ArrayList<Enemy> enemyList = game.getEnemies();
		return userMoney < 100 && enemyList.isEmpty();
	}
	
	// ?!?!?!
	public void updateLogisticSender() {
		spawnTimer++;
		if (spawnTimer >= 1200) {
			System.out.println("Sending logistics...");
			spawnTimer = 0;
		}
	}

	public SinglePlayerGameController() {
		game = new Game();
		gui.setLayout(new FlowLayout());
		shop = new SinglePlayerShopPanel();
		canvas = new GameCanvas(this);
		stats = new LogisticsPanel();
//		health.setSize(100,100);
		shop.connectToMap(canvas);
		gui.setTitle("Game");
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(shop.PANEL_WIDTH + 75 + 50, canvas.PANEL_HEIGHT + shop.PANEL_HEIGHT + 100);
		gui.add(canvas);
		gui.add(shop);
		gui.add(stats);

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
				SinglePlayerGameController newGame = new SinglePlayerGameController(); // Is this even acceptable...?
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
		if (!hasLost()) {
			game.update();
			if (game.getFunds() != 0) {
				user.setMoney(user.getMoney() + game.getFunds());
				shop.updateWithMoney(user.getMoney());
			}
			stats.updateHealth(game.getPlayerHealth());
			stats.updateMoney(user.getMoney());
			draw(game.getDrawable());
			canvas.optimizeBakcground();
			processOrders();
			processSpawnQueue();
		} else {
			if (!gameOver) {
				gameOver = true;
			}
		}
	}
	
	public void processSpawnQueue() {
		spawnTimer++;
		if (spawnTimer >= 150 && !spawnQueue.isEmpty()) {
			game.addEnemy(spawnQueue.poll());
			spawnTimer = 0;
		}
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
	public void addOrder(PurchaseOrder po) {
		int userMoney = user.getMoney();
		int cost = po.getItem().value;
		user.setMoney(userMoney - cost);
		System.out.println("Called the addOrder method");
	}

	@Override
	public void draw(ArrayList<Drawable> arr) {
		// TODO Auto-generated method stub
		if (arr != null && canvas != null)
			canvas.drawDrawables(arr);
	}

	@Override
	public void processOrders() {
		// TODO Auto-generated method stub
		for (PurchaseOrder po : listOfOrders) {
			if (po.getItem().type == SinglePlayerShop.TYPE_BUY_TOWER) {
				Tower tower = null;
				System.out.println(po.getTile_x());
				System.out.println(po.getTile_y());
				switch (po.getItem().towerType) {
				case Res.TOWER_FIRE_TYPE:
					tower = new FireTower(po.getTile_x() * Res.GRID_WIDTH,
							po.getTile_y() * Res.GRID_HEIGHT);
					break;
				case Res.TOWER_ICE_TYPE:
					tower = new IceTower(po.getTile_x() * Res.GRID_WIDTH,
							po.getTile_y() * Res.GRID_HEIGHT);
					break;
				case Res.TOWER_LIGHTNING_TYPE:
					tower = new LightningTower(po.getTile_x() * Res.GRID_WIDTH,
							po.getTile_y() * Res.GRID_HEIGHT);
					break;
				}
				setUpTower(po.getTile_x(), po.getTile_y(),
						po.getItem().towerType);
				game.addTower(tower);
				towerCount++;
				System.out.println("Player " + PLAYER_INT + " tower added.");
			} else if (po.getItem().type == SinglePlayerShop.TYPE_UPGRADE_TOWER) {

			} 
		}
		listOfOrders.clear();
	}

	@Override
	public void drawHealthBars() {
		// TODO Auto-generated method stub

	}
	
	public void setUpTower(int tileX, int tileY, int tower_type) {
		canvas.addTower(tileX, tileY, tower_type);
	}
	
	@Override
	public void notifyShopOfSelection(int tileX, int tileY, Tile tile) {
		shop.updateButtons(tileX, tileY, tile.tileType);
		shop.updateWithMoney(user.getMoney());
	}

	@Override
	public void updateShopWithCurrentMoney() {
		shop.updateWithMoney(user.getMoney());
	}

	@Override
	public void sendDelivery(Delivery d) {
		// Not necessary...

	}

}
