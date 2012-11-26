package network;
/**
 * Multi-Client Chat Program
 * Written by Zuoming Shi and Victor Nguyen, for CS335 Fall 2012.
 **/
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import model.Delivery;

class Liason extends Thread {
	private Socket socketFromServer;
	private ObjectInputStream readFromClient;
	private Server server;
	/**
	 * Construct an object that will run in it's own Thread so this object can
	 * communicate with that one connection
	 */
	public Liason(Socket socketFromServer,
			Vector<String> sharedCollectionReference, Server S) {
		this.socketFromServer = socketFromServer;
		server = S;
	}
	/**
	 * The void run() method in class Thread must be overridden in every class
	 * that extends Thread. The Server created a new instance of this class and
	 * sends it a start method, which is in Thread. The start method creates a
	 * new Thread which then calls the following run method where our domain
	 * specific logic goes.
	 */
	@Override
	public void run() {
		// Open the input and output streams so the
		// client can interact with the collection
		try {
			readFromClient = new ObjectInputStream(socketFromServer.getInputStream());
		} catch (IOException e) {
			System.out.println("Exception thrown while obtaining input & output streams");
			e.printStackTrace();
			return;
		}

		Delivery deliveryFromClient = null;
		boolean stayConnected = true;
		while (stayConnected) {
			try {
				deliveryFromClient = (Delivery) readFromClient.readObject();
				System.out.println("got stuff");
			} catch (IOException e1) {
				//Catch the closed connection exception to remove outputStream and Liason related to the client in the server.
				server.removeLiason(getId());
				return;
			} catch (ClassNotFoundException e1) {
				System.out.println("Error in Liason.run when trying to read from client");
				e1.printStackTrace();
			}
			server.updateMessage(deliveryFromClient);

		}
	}
}