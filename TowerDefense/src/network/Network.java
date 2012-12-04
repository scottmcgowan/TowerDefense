package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;

import model.Delivery;
import model.GameControllerInterface;

public class Network extends Observable {

	public ObjectOutputStream outputToLiasonLoop; // stream to server
	private ObjectInputStream inputFromServerLoop; // stream from server
	public int player;
	private GameControllerInterface game;

	public Network(NetworkPanel np, int player, MultiPlayerGameController gc) {
		game = gc;
		addObserver(np);
		openForConnection(gc);
		this.player = player;
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
			if (d == null) {
				System.out.print("failed!");
			}
			outputToLiasonLoop.writeObject(d);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void openForConnection(final MultiPlayerGameController gc) {
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
						if (d.newGameReady) {
							gc.gameStart();
							System.out.println("New Game Started");
						} else if (d.lose) {
							if (d.player == player) {
								gc.lost();
							} else {
								gc.won();
							}
						} else if (d.tieMet) {
							if(player!=d.player && gc.checkOnesidedTieConditions()){
								sendDelivery(new Delivery("", player, false, false, false, true, true));
							}
						} else if (d.tied){
							gc.tie();
						} else if (d.pause){
							//System.out.println("sdasdasda");
							gc.pause(true);
							setChanged();
							notifyObservers(outputMessage);
						} else if (d.rate){
							//System.out.println("sdasdasda");
							if(player != d.player){
							gc.updateRate(true);
							setChanged();
							notifyObservers(outputMessage);}
						}
						
						// Does not update if message is empty, otherwise alerts
						// gui to update with the message.
						else if (!outputMessage.trim().equals("")) {
							// gui.update(outputMessage);
							if (d.player == player) {
								if (d.messageForSelf) {
									setChanged();
									notifyObservers(outputMessage);
								}
							} else {
								if (d.messageForOther) {
									setChanged();
									notifyObservers(outputMessage);
								}
							}

							// System.out.println(outputMessage);
							if(d.getOrder()!=null){
							game.addOrder(d.getOrder());}
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
}
