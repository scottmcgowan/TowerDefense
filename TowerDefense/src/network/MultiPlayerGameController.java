package network;

import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JFrame;

import model.Delivery;
import model.Drawable;
import model.Game;
import model.GameControllerInterface;
import model.Player;
import model.PurchaseOrder;
import model.enemies.Buff;
import model.enemies.Enemy;
import model.enemies.Grunt;
import model.enemies.Speedy;
import model.towers.FireTower;
import model.towers.IceTower;
import model.towers.LightningTower;
import model.towers.Tower;
import resources.Res;
import run.MainMenu;
import GUI.GameCanvas;
import GUI.LogisticsPanel;
import GUI.Map;
import GUI.MiscOptions;

public class MultiPlayerGameController implements GameControllerInterface {

	// main game class
	int UPDATE_RATE = 60; // number of game update per second
	long UPDATE_PERIOD = 1000000000L / UPDATE_RATE; // nanoseconds

	// State of the game
	private boolean gameOver = false;
	private boolean gamePaused = false;
	private int frameCounter = 0;
	private int secondCounter = 0;
	private NetworkPanel networkPanel;
	private Network network;
	private GameCanvas gameCanvas;
	private MultiPlayerShopPanel shop;
	private Player thisPlayer = new Player();
	private JFrame gui = new JFrame();
	private LinkedList<PurchaseOrder> orders = new LinkedList<PurchaseOrder>();
	private Game game;
	private LinkedList<Enemy> spawnQueue = new LinkedList<Enemy>();
	private int spawn_timer;
	private int logistic_timer;
	private int player;
	private int currentTileX;
	private int currentTileY;
	private int tower_count;
	private Server server;
	private LogisticsPanel stats;
	private MiscOptions screens = new MiscOptions();

	public static void main(String[] args) {
		MultiPlayerGameController game = new MultiPlayerGameController(
				Server.SERVER_PLAYER, 1);
		wait(1);
		MultiPlayerGameController game2 = new MultiPlayerGameController(
				Server.CLIENT_PLAYER, 1);
	}

	public static void wait(int n) {
		long t0, t1;
		t0 = System.currentTimeMillis();
		do {
			t1 = System.currentTimeMillis();
		} while (t1 - t0 < 1000);
	}

	public void lost() {
		System.out.println(gameOver);
		if (!gameOver) {
			System.out.println("lost really called");
			gameOver = true;
			System.out.println("Losing conditions met");
			screens.setLoseMessage(gui);
			gui.dispose();
			new MainMenu();
			tryShuttingServer();
		}
	}

	public void tryShuttingServer(){
		if(server!=null){
			try {
				server.myServerSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			server.interrupt();}
	}
	
	public boolean hasLost() {
		return game.gameOver();
	}

	public void won() {
		if (!gameOver) {
			System.out.println("Winning conditions met");
			screens.setWinMessage(gui);
			gameOver = true;
			gui.dispose();
			new MainMenu();
			tryShuttingServer();
		}
	}

	public boolean checkOnesidedTieConditions() {
		return thisPlayer.getMoney() < 100 && game.getEnemies().isEmpty();
	}

	public void tie() {
		if (!gameOver) {
			System.out.println("Tie conditions met");
			screens.setLocationRelativeTo(gui);
			screens.setTieMessage(gui);
			gameOver = true;
			gui.dispose();
			new MainMenu();
			tryShuttingServer();
		}
	}

	public void updateLogisticSender() {
		spawn_timer++;
		if (spawn_timer >= 1200) {

			System.out.println("Sending Logistics");
			String log = "System: player " + player + " has " + tower_count
					+ " towers, " + thisPlayer.getMoney() + " funds, and "
					+ game.getPlayerHealth() + " health remaining.\n";
			sendDelivery(new Delivery(log, player, false, false,
					checkOnesidedTieConditions(), false, false));
			spawn_timer = 0;
		}
		if (spawn_timer % 120 == 0) {
			sendDelivery(new Delivery("", player, false, false,
					checkOnesidedTieConditions(), false, false));
		}
	}

	public MultiPlayerGameController(int player, int map) {
		game = new Game();
		this.player = player;
		networkPanel = new NetworkPanel(player, this);
		if (player == Server.SERVER_PLAYER) {
			server = new Server(Server.PORT_NUMBER);
			server.start();
			network = new Network(networkPanel, Server.SERVER_PLAYER, this);
		} else {
			network = new Network(networkPanel, Server.CLIENT_PLAYER, this);
		}

		gui.setLayout(new FlowLayout());
		gui.setFocusable(true);
		gui.setResizable(false);
		shop = new MultiPlayerShopPanel(player, this);
		stats = new LogisticsPanel();
		gameCanvas = new GameCanvas(this, map);
		gameCanvas.setSize(gameCanvas.PANEL_WIDTH, gameCanvas.PANEL_HEIGHT);
		shop.connectToMap(gameCanvas);
		networkPanel.setSize(networkPanel.PANEL_WIDTH,
				networkPanel.PANEL_HEIGHT);
		shop.setSize(shop.PANEL_WIDTH, shop.PANEL_HEIGHT);
		gameCanvas.setLocation(20, 20);
		networkPanel.setLocation(gameCanvas.PANEL_WIDTH + 40, 20);
		shop.setLocation(80, gameCanvas.PANEL_HEIGHT + 40);
		gui.setTitle("Game");


        gui.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        gui.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                tryShuttingServer();
                gui.dispose();
                System.exit(0);
            }
        } );
        
		gui.setSize(gameCanvas.PANEL_WIDTH + networkPanel.PANEL_WIDTH + 80,
				gameCanvas.PANEL_HEIGHT + shop.PANEL_HEIGHT + 90);
		gui.add(gameCanvas);
		gui.add(networkPanel);
		gui.add(shop);
		gui.add(stats);
		//JMenuBar menubar = new JMenuBar();
		//gui.setJMenuBar(menubar);

		//JMenu fileMenu = new JMenu("File");
		//menubar.add(fileMenu);

		//JMenuItem newGame = new JMenuItem("New Game");
		//JMenuItem exit = new JMenuItem("Exit");
		//fileMenu.add(newGame);
		//fileMenu.addSeparator();
		//fileMenu.add(exit);
		//newGame.addActionListener(new allMenuAction());
		//exit.addActionListener(new allMenuAction());
		gui.addKeyListener(new keyAction());
		gui.setVisible(true);
		gui.repaint();
		if(player == Server.SERVER_PLAYER){
			wait(1);
			screens.setServerMessage(gui);			
		}
	}

	private class keyAction implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			if (arg0.getKeyChar() == ' ')
				updateRate(false);
			if (arg0.getKeyChar() == 'p')
				pause(false);
			
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

	public void addOrder(PurchaseOrder po) {
		if (po.getPlayer() == player) {
			thisPlayer.setMoney(thisPlayer.getMoney() - po.getItem().value);
			if (po.getItem().type != MultiPlayerShop.TYPE_PURCHASE_ENEMY) {
				orders.add(po);
				shop.updateButtons(po.getTile_x(), po.getTile_y(),
						po.getItem().towerType);
				System.out.println("Player " + player + " tower order added");
			}
		} else {
			if (po.getItem().type == MultiPlayerShop.TYPE_PURCHASE_ENEMY) {
				orders.add(po);
				System.out.println("Player " + player
						+ " purchase enemy order added");
			}
		}
		shop.updateWithMoney(thisPlayer.getMoney());
		System.out.println("Called add order");
	}

	@Override
	public void processOrders() {
		// TODO Auto-generated method stub
		for (PurchaseOrder po : orders) {
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

	public void sendDelivery(Delivery d) {
		network.sendDelivery(d);
	}

	public void gameStart() {
		wait(1);
		shop.updateWithMoney(thisPlayer.getMoney());
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
				thisPlayer.setMoney(thisPlayer.getMoney() + game.getFunds());
				shop.updateWithMoney(thisPlayer.getMoney());
			}
			stats.updateHealth(game.getPlayerHealth());
			stats.updateMoney(thisPlayer.getMoney());
			updateLogisticSender();
			draw(game.getDrawable());
			gameCanvas.optimizeBakcground();
			processOrders();
			processSpawnQueue();
		} else {
			if (!gameOver) {
				sendDelivery(new Delivery("", player, false, true, false,
						false, false));
			}
		}
	}

	public void processSpawnQueue() {
		spawn_timer += 1;
		if (spawn_timer >= 150 && !spawnQueue.isEmpty()) {
			game.addEnemy(spawnQueue.poll());
			spawn_timer = 0;
			System.out.println("Player " + player + " enemy added");
		}
	}

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
	public void draw(ArrayList<Drawable> arr) {
		// TODO Auto-generated method stub
		if (arr != null && gameCanvas != null) {
			gameCanvas.drawDrawables(arr);
		}
	}

	@Override
	public void drawHealthBars() {
		// TODO Auto-generated method stub

	}

	public void setUpTower(int tileX, int tileY, int tower_type) {
		gameCanvas.addTower(tileX, tileY, tower_type);
	}

	@Override
	public void notifyShopOfSelection(int tileX, int tileY, Map.Tile tile) {
		shop.updateButtons(tileX, tileY, tile.tileType);
		// update shop with the new money value
		shop.updateWithMoney(thisPlayer.getMoney());
	}

	public void updateRate(boolean fromNetwork) {
		String rateString = new String();
		if(UPDATE_RATE==60){rateString = "90";}
		if(UPDATE_RATE==90){rateString = "60";}
		if (fromNetwork) {
			if (UPDATE_RATE == 60) {
				UPDATE_RATE = 90;
				System.out.println("Rate changed to 90");
			} else {
				UPDATE_RATE = 60;
				System.out.println("Rate changed to 60");
			}
			UPDATE_PERIOD = 1000000000L / UPDATE_RATE;
		} else {
			sendDelivery(new Delivery("Player " + player + " changed game rate to "
					+ rateString, player, false, true));
		}
	}

	public void pause(boolean fromNetwork) {
		// TODO Auto-generated method stub
		if (fromNetwork) {
			gamePaused = !gamePaused;
		} else {
			//sendDelivery(new Delivery("", player, true, false));
			sendDelivery(new Delivery("Player " + player + " paused the game", player, true, false));
		}
	}
}
