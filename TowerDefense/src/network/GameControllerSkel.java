package network;
import javax.swing.JFrame;

import model.Delivery;
import model.PurchaseOrder;

public class GameControllerSkel {

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
	//public GameCanvas gameCanvas;

	public static void main(String[] args) {
		GameControllerSkel game = new GameControllerSkel(true);
		wait(1);
		GameControllerSkel game2 = new GameControllerSkel(false);
	}

	public static void wait (int n){
        long t0,t1;
        t0=System.currentTimeMillis();
        do{
            t1=System.currentTimeMillis();
        }
        while (t1-t0<1000);
	}
	
	public GameControllerSkel(boolean isServer) {
		networkPanel = new NetworkPanel(this);
		if(isServer==true){
			Server server = new Server(Server.PORT_NUMBER);
			server.start();
			network = new Network(networkPanel, Server.SERVER_PLAYER);
		}else{
			network = new Network(networkPanel, Server.CLIENT_PLAYER);
		}
		
		JFrame gui = new JFrame();
		gui.setTitle("Game");
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(350, 300);
		gui.setVisible(true);
		gui.add(networkPanel);
		
		gameStart();
	}

	public void addOrder(PurchaseOrder po){
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
	private void gameLoop() {
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
}
