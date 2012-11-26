package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;

import javax.swing.JFrame;

import model.Delivery;

//Runs the updating logic of the game, like a game controller
//Receives info sent from the server
public class GameLogic extends Observable {

	// main game class
	static final int UPDATE_RATE = 60; // number of game update per second
	static final long UPDATE_PERIOD = 1000000000L / UPDATE_RATE; // nanoseconds
	public ObjectOutputStream outputToLiasonLoop; // stream to server
	private ObjectInputStream inputFromServerLoop; // stream from server

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

	public GameLogic() {
		gameStart();
	}

	public void sendMessage(String text) {
		try {
			outputToLiasonLoop.writeObject(text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendDelivery(Delivery d) {
		try {
			outputToLiasonLoop.writeObject(d);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		Thread clientThread = new Thread() {
			// Override run() to provide the running behavior of this thread.
			@Override
			public void run() {
				connectToServer();
				try {
					while (true) {
						Delivery d = (Delivery) inputFromServerLoop
								.readObject();
						String outputMessage = d.getMessage();
						// Does not update if message is empty, otherwise alerts
						// gui to update with the message.
						if (!outputMessage.trim().equals("")) {
							// gui.update(outputMessage);
							setChanged();
							notifyObservers(outputMessage);
							System.out.println(outputMessage);
						}
					}
				} catch (IOException e) {
					System.out.println("In Client.connectToServer");
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					System.out
							.println("Trying to read an object of a different written type from Liason");
					e.printStackTrace();
				}
			}
		};

		// Start the thread. start() calls run(), which in turn calls
		// gameLoop().
		gameThread.start();
		clientThread.start();
	}

	public void connectToServer() {
		Socket sock = null;
		try {
			// connect to the server
			sock = new Socket(Server.HOST_NAME, Server.PORT_NUMBER);
		} catch (Exception e) {
			System.out.println("Client was unable to connect to server.");
			e.printStackTrace();
		}
		try {
			outputToLiasonLoop = new ObjectOutputStream(sock.getOutputStream());
			inputFromServerLoop = new ObjectInputStream(sock.getInputStream());
		} catch (Exception e) {
			System.out
					.println("Unable to obtain Input & Output streams from Socket");
			e.printStackTrace();
		}
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
