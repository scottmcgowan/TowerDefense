package network;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import resources.Res;

import model.Delivery;
import model.Drawable;
import model.Game;
import model.GameControllerInterface;
import model.Player;
import model.PurchaseOrder;
import model.enemies.Enemy;
import model.enemies.Grunt;
import model.projectiles.Projectile;
import model.towers.FireTower;
import model.towers.IceTower;
import model.towers.LightningTower;
import model.towers.Tower;
import GUI.GameCanvas;
import GUI.Map;

public class MultiPlayerGameController implements GameControllerInterface {

	// main game class
	static final int UPDATE_RATE = 60; // number of game update per second
	static final long UPDATE_PERIOD = 1000000000L / UPDATE_RATE; // nanoseconds

	// State of the game
	boolean gameOver = false;
	boolean gamePaused = false;
	public int frameCounter = 0;
	public int secondCounter = 0;
	public NetworkPanel networkPanel;
	public Network network;
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
	
	public static void main(String[] args) {
		MultiPlayerGameController game = new MultiPlayerGameController(
				Server.SERVER_PLAYER);
		wait(1);
		MultiPlayerGameController game2 = new MultiPlayerGameController(
				Server.CLIENT_PLAYER);
	}

	public static void wait(int n) {
		long t0, t1;
		t0 = System.currentTimeMillis();
		do {
			t1 = System.currentTimeMillis();
		} while (t1 - t0 < 1000);
	}

	public void lost(){System.out.println("Losing conditions met");}
	
	public boolean hasLost(){
		return game.gameOver();}
	
	public void won(){System.out.println("Winning conditions met");}
	
	private boolean checkOnesidedTieConditions(){
		return thisPlayer.getMoney()<100 && game.getEnemies().isEmpty();}
	
	public void tie(){System.out.println("Tie conditions met");}

	public void updateLogisticSender(){
		spawn_timer++;
		if(spawn_timer>=300){
		System.out.println("Sending Logistics");
		String log = "Player" + player;
		//Tower count
		//Health
		//Money
			
		spawn_timer = 0;
		}
	}
	
	public MultiPlayerGameController(int player) {
		game = new Game();
		this.player = player;
		networkPanel = new NetworkPanel(player, this);
		if (player == Server.SERVER_PLAYER) {
			Server server = new Server(Server.PORT_NUMBER);
			server.start();
			network = new Network(networkPanel, Server.SERVER_PLAYER, this);
		} else {
			network = new Network(networkPanel, Server.CLIENT_PLAYER, this);
		}

		gui.setLayout(null);
		shop = new MultiPlayerShopPanel(player, this);
		gameCanvas = new GameCanvas(this);
		gameCanvas.setSize(gameCanvas.PANEL_WIDTH, gameCanvas.PANEL_HEIGHT);
		shop.connectToMap(gameCanvas);
		networkPanel.setSize(networkPanel.PANEL_WIDTH,
				networkPanel.PANEL_HEIGHT);
		shop.setSize(shop.PANEL_WIDTH, shop.PANEL_HEIGHT);
		gameCanvas.setLocation(20, 20);
		networkPanel.setLocation(gameCanvas.PANEL_WIDTH + 40, 20);
		shop.setLocation(80, gameCanvas.PANEL_HEIGHT + 40);
		gui.setTitle("Game");
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(gameCanvas.PANEL_WIDTH + networkPanel.PANEL_WIDTH + 80,
				gameCanvas.PANEL_HEIGHT + shop.PANEL_HEIGHT + 90);
		gui.add(networkPanel);
		gui.add(shop);
		gui.add(gameCanvas);

		JMenuBar menubar = new JMenuBar(); gui.setJMenuBar(menubar);
		
		JMenu fileMenu = new JMenu("File"); menubar.add(fileMenu);
		
		JMenuItem newGame = new JMenuItem("New Game"); JMenuItem exit = new
		JMenuItem("Exit"); fileMenu.add(newGame); fileMenu.addSeparator();
		fileMenu.add(exit); 
		newGame.addActionListener(new allMenuAction());
	 	exit.addActionListener(new allMenuAction());

		gui.setVisible(true);
		gui.repaint();
	}

	private class allMenuAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JMenuItem menuItem = (JMenuItem) arg0.getSource();
			if (menuItem.getText() == "New Game") {
			} else {
				gui.dispose();
			}
		}
	}

	public void addOrder(PurchaseOrder po) {
		if (po.getPlayer() == player) {
			thisPlayer.setMoney(thisPlayer.getMoney() - po.getItem().value);
			if (po.getItem().type != MultiPlayerShop.TYPE_PURCHASE_ENEMY) {
				orders.add(po);
				shop.updateButtons(po.getTile_x(), po.getTile_y(), po.getItem().towerType);
				System.out.println("Player "+ player + " tower order added");
			}
		} else {
			if (po.getItem().type == MultiPlayerShop.TYPE_PURCHASE_ENEMY) {
				orders.add(po);
				System.out.println("Player "+ player + " purchase enemy order added");
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
				setUpTower(po.getTile_x(), po.getTile_y(), po.getItem().towerType);
				game.addTower(tower);
				tower_count++;
				System.out.println("Player "+ player + " tower added.");
			} else if (po.getItem().type == MultiPlayerShop.TYPE_UPGRADE_TOWER) {

			} else if (po.getItem().type == MultiPlayerShop.TYPE_PURCHASE_ENEMY) {
				for(int i=0;i<5;i++){
				spawnQueue.add(new Grunt(gameCanvas.getPath()));}
				System.out.println("Player "+ player + " enemy order processed.");
			}
		}
		orders.clear();
	}
	
	public void sendDelivery(Delivery d) {
		network.sendDelivery(d);
	}

	public void gameStart() {
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
		// get some gameLogic in here!
		game.update();
		draw(game.getDrawable());
		processOrders();
		processSpawnQueue();
	}

	public void processSpawnQueue() {
		spawn_timer += 1;
		if(spawn_timer >=60 && !spawnQueue.isEmpty()){
			game.addEnemy(spawnQueue.poll());
			spawn_timer = 0;
			System.out.println("Player "+ player + " enemy added");
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
		gameCanvas.drawDrawables(arr);
	}

	@Override
	public void drawHealthBars() {
		// TODO Auto-generated method stub

	}

	public void setUpTower(int tileX, int tileY, int tower_type){
		gameCanvas.addTower(tileX, tileY, tower_type);
	}
	
	@Override
	public void notifyShopOfSelection(int tileX, int tileY, Map.Tile tile) {
		shop.updateButtons(tileX, tileY, tile.tileType);
		// update shop with the new money value
		shop.updateWithMoney(thisPlayer.getMoney());
	}

	@Override
	public void updateShopWithCurrentMoney() {
		shop.updateWithMoney(thisPlayer.getMoney());
	}

	public void checkForTie() {
		// TODO Auto-generated method stub
		
	}

}
