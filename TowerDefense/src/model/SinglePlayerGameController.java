package model;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import GUI.MiscOptions;
import GUI.Map.Tile;
import GUI.SinglePlayerShopPanel;

public class SinglePlayerGameController implements GameControllerInterface {

	// main game class
	private static int UPDATE_RATE = 60; // number of game updates per
												// second
	private static long UPDATE_PERIOD = 1000000000L / UPDATE_RATE; // nanoseconds

	// State of the game
	private Player user = new Player();
	private ComputerPlayer comPlayer = new ComputerPlayer(this);
	private Game game;
	private boolean gameOver = false;
	private boolean gamePaused = false;
	private GameCanvas gameCanvas;
	private JFrame gui = new JFrame();
	private SinglePlayerShopPanel shop;
	private LinkedList<PurchaseOrder> orders = new LinkedList<PurchaseOrder>();
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
	private final int player = 1;
	private int tower_count = 0;
	private MiscOptions screens = new MiscOptions();

	public static void main(String[] args) {
		// JFrame menu = new MainMenu();
		SinglePlayerGameController game = new SinglePlayerGameController();
	}

	public static void wait(int n) {
		long t0, t1;
		t0 = System.currentTimeMillis();
		do {
			t1 = System.currentTimeMillis();
		} while (t1 - t0 < 1000);
	}

	public void lost() {
		if (!gameOver) {
			gameOver = true;
			System.out.println("Losing conditions met");
			screens.setLoseMessage();
			gui.dispose();
			new MainMenu();
		}
	}

	public boolean hasLost() {
		return game.gameOver();
	}
	

	public boolean hasWon() {
		return comPlayer.wave_remaining<0 && game.getEnemies().isEmpty();
	}

	public void won() {
		if (!gameOver) {
			System.out.println("Winning conditions met");
			screens.setWinMessage();
			gameOver = true;
			gui.dispose();
			new MainMenu();
		}
	}
	
	public SinglePlayerGameController() {
		game = new Game();
		gui.setLayout(new FlowLayout());
		shop = new SinglePlayerShopPanel(this, player);
		gameCanvas = new GameCanvas(this, 1);
		stats = new LogisticsPanel();
		// health.setSize(100,100);
		shop.connectToMap(gameCanvas);
		gui.setFocusable(true);
		gui.setResizable(false);
		gui.setTitle("Game");
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;
		gui.addKeyListener(new keyAction());
		gui.setSize(shop.PANEL_WIDTH + 75 + 50, gameCanvas.PANEL_HEIGHT
				+ shop.PANEL_HEIGHT + 100);
		gui.add(gameCanvas);
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
				SinglePlayerGameController newGame = new SinglePlayerGameController();
			}
			gui.dispose();
		}
	}

	public void gameStart() {
		
		shop.updateWithMoney(user.getMoney());
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
			comPlayer.update();
			if (game.getFunds() != 0) {
				user.setMoney(user.getMoney() + game.getFunds());
				shop.updateWithMoney(user.getMoney());
			}
			stats.updateHealth(game.getPlayerHealth());
			stats.updateMoney(user.getMoney());
			draw(game.getDrawable());
			gameCanvas.optimizeBakcground();
			processOrders();
			processSpawnQueue();
			if(hasLost())
				lost();
			if(hasWon())
				won();
			gui.setTitle("Tower Defense! "+"Wave remaining: " + comPlayer.wave_remaining);
		} else {
			if (!gameOver) {
				gameOver = true;
			}
		}
	}

	public void processSpawnQueue() {
		spawnTimer++;
		if (spawnTimer >= 60 && !spawnQueue.isEmpty()) {
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
		if (po.getPlayer() == player) {
			int userMoney = user.getMoney();
			int cost = po.getItem().value;
			user.setMoney(userMoney - cost);}

		if (po.getItem().type != MultiPlayerShop.TYPE_PURCHASE_ENEMY) {
			shop.updateButtons(po.getTile_x(), po.getTile_y(),
					po.getItem().towerType);}
		
			orders.add(po);
			System.out.println("Called the addOrder method");
			shop.updateWithMoney(user.getMoney());
	}

	@Override
	public void draw(ArrayList<Drawable> arr) {
		// TODO Auto-generated method stub
		if (arr != null && gameCanvas != null)
			gameCanvas.drawDrawables(arr);
	}

	@Override
	public void processOrders() {
		// TODO Auto-generated method stub
		for (PurchaseOrder po : orders) {
			System.out.print("iterating");
			if (po.getItem().type == MultiPlayerShop.TYPE_BUY_TOWER) {
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
				tower_count++;
				System.out.println("Player " + player + " tower added.");
			} else if (po.getItem().type == MultiPlayerShop.TYPE_UPGRADE_TOWER) {
				game.upgradeTower(po.getTile_x(), po.getTile_y());
			} else if (po.getItem().type == MultiPlayerShop.TYPE_PURCHASE_ENEMY) {

				for (int i = 0; i < 5; i++) {
					if (po.getItem().enemyType == Res.ENEMY_GRUNT_TYPE) {
						spawnQueue.add(new Grunt(gameCanvas.getPath()));
					} else if (po.getItem().enemyType == Res.ENEMY_SPEEDY_TYPE) {
						spawnQueue.add(new Speedy(gameCanvas.getPath()));
					} else if (po.getItem().enemyType == Res.ENEMY_BUFF_TYPE) {
						spawnQueue.add(new Buff(gameCanvas.getPath()));
					}
				}
				System.out.println("Player " + player
						+ " enemy order processed.");
			}
		}
		orders.clear();
	}

	@Override
	public void drawHealthBars() {
		// TODO Auto-generated method stub

	}

	public void setUpTower(int tileX, int tileY, int tower_type) {
		gameCanvas.addTower(tileX, tileY, tower_type);
		System.out.println("Tried adding tower");
	}

	@Override
	public void notifyShopOfSelection(int tileX, int tileY, Tile tile) {
		shop.updateButtons(tileX, tileY, tile.tileType);
		shop.updateWithMoney(user.getMoney());
	}

	@Override
	public void sendDelivery(Delivery d) {
		// Not necessary...

	}
	
	public void updateRate() {
			if (UPDATE_RATE == 60) {
				UPDATE_RATE = 120;
			} else {
				UPDATE_RATE = 60;
			}
			UPDATE_PERIOD = 1000000000L / UPDATE_RATE;
	}

	public void pause() {
		// TODO Auto-generated method stub
			gamePaused = !gamePaused;
	}

	private class keyAction implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			if (arg0.getKeyChar() == ' ')
				updateRate();
			if (arg0.getKeyChar() == 'p')
				pause();
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
