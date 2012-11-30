package network;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;

import model.Delivery;
import model.Drawable;
import model.Game;
import model.GameControllerInterface;
import model.Player;
import model.PurchaseOrder;
import model.enemies.Enemy;
import model.towers.Tower;
import GUI.GameCanvas;
import GUI.Map;

public class MultiPlayerGameControllerSkel implements GameControllerInterface{

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
	private GameCanvas canvas;
	private MultiPlayerShopPanel shop;
	private Player thisPlayer = new Player();
	private Player otherPlayer = new Player();
	private JFrame gui = new JFrame();
	private ArrayList<PurchaseOrder> orders = new ArrayList<PurchaseOrder>();
	private Game game;
	private ArrayList<Enemy> spawnQueue;
	private Timer timer;

	public static void main(String[] args) {
		MultiPlayerGameControllerSkel game = new MultiPlayerGameControllerSkel(Server.SERVER_PLAYER);
		wait(1);
		MultiPlayerGameControllerSkel game2 = new MultiPlayerGameControllerSkel(Server.CLIENT_PLAYER);
	}

	public static void wait (int n){
        long t0,t1;
        t0=System.currentTimeMillis();
        do{
            t1=System.currentTimeMillis();
        }
        while (t1-t0<1000);
	}
	
	public MultiPlayerGameControllerSkel(int player) {
		networkPanel = new NetworkPanel(player, this);
		if(player==Server.SERVER_PLAYER){
			Server server = new Server(Server.PORT_NUMBER);
			server.start();
			network = new Network(networkPanel, Server.SERVER_PLAYER, this);
		}else{
			network = new Network(networkPanel, Server.CLIENT_PLAYER, this);
		}
		
		gui.setLayout(null);
		shop = new MultiPlayerShopPanel(player, this);
		canvas = new GameCanvas(this);
		shop.connectToMap(canvas);
		canvas.setSize(canvas.PANEL_WIDTH, canvas.PANEL_HEIGHT);
		networkPanel.setSize(networkPanel.PANEL_WIDTH, networkPanel.PANEL_HEIGHT);
		shop.setSize(shop.PANEL_WIDTH, shop.PANEL_HEIGHT);
		canvas.setLocation(20, 20);
		networkPanel.setLocation(canvas.PANEL_WIDTH+40, 20);
		shop.setLocation(80, canvas.PANEL_HEIGHT+40);
		gui.setTitle("Game");
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(canvas.PANEL_WIDTH+networkPanel.PANEL_WIDTH+80, canvas.PANEL_HEIGHT+shop.PANEL_HEIGHT+90);
		gui.setVisible(true);
		gui.add(networkPanel);
		gui.add(shop);
		gui.add(canvas);
		
		/*
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
		exit.addActionListener(new allMenuAction());*/
		
		gui.repaint();
		gameStart();
	}
	
	private class allMenuAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JMenuItem menuItem = (JMenuItem) arg0.getSource();
			if (menuItem.getText() == "New Game") {
			}
			else {
				gui.dispose();
			}
		}
	}

	public void addOrder(PurchaseOrder po){
		orders.add(po);
		thisPlayer.setMoney(thisPlayer.getMoney()-po.getItem().value);
		return;
	}

	public void sendDelivery(Delivery d) {
		network.sendDelivery(d);
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
		//get some gameLogic in here!
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
		
	}

	@Override
	public void processOrders() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawHealthBars() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyShopOfSelection(int tileX, int tileY, Map.Tile tile) {
		shop.updateButtons(tileX, tileY, tile.tileType);
		shop.updateWithMoney(thisPlayer.getMoney());
	}
	
	@Override
	public void updateShopWithCurrentMoney(){
		shop.updateWithMoney(thisPlayer.getMoney());
	}
	
}
