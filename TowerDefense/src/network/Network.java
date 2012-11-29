package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;

import model.Delivery;
import model.GameControllerInterface;

public class Network extends Observable{

	public ObjectOutputStream outputToLiasonLoop; // stream to server
	private ObjectInputStream inputFromServerLoop; // stream from server
	public int player;
	private GameControllerInterface game;
	
	public Network(NetworkPanel np, int player, GameControllerInterface gc){
		game = gc;
		addObserver(np);
		openForConnection();
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
			if(d==null){
			System.out.print("failed!");}
			outputToLiasonLoop.writeObject(d);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void openForConnection() {
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
							//game.addOrder(d.getOrder());
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
