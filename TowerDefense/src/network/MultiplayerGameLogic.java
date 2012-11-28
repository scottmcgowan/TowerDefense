package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;

import javax.swing.JFrame;

import model.Delivery;
import model.PurchaseOrder;

//Runs the updating logic of the game, like a game controller
//Receives info sent from the server
public class MultiplayerGameLogic {

	// main game class
	static final int UPDATE_RATE = 60; // number of game update per second
	static final long UPDATE_PERIOD = 1000000000L / UPDATE_RATE; // nanoseconds

	// State of the game
	boolean gameOver = false;
	boolean gamePaused = false;
	public int frameCounter = 0;
	public int secondCounter = 0;

	// To start and re-start the game.
	/*
	public static void main(String[] args) {
		GameLogic game = new GameLogic();
	}*/

	public MultiplayerGameLogic() {
		gameStart();
	}
	
	public void addOrder(PurchaseOrder po){
		return;
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
		frameCounter++;
		if (frameCounter % 5 == 0 && frameCounter < 30) {
			// gui.textArea.append(frameCounter+"\n");
			// gui.repaint();
		}
		if (frameCounter == 60) {
			secondCounter++;
			frameCounter = 0;
		}
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
