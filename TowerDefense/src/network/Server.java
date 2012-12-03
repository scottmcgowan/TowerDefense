package network;

/**
 * Multi-Client Chat Program
 * Written by Zuoming Shi and Victor Nguyen, for CS335 Fall 2012.
 **/
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

import model.Delivery;

/**
 * This server is a thread that accepts requests to connect from clients. Each
 * request cause a new Thread to handle the client, or in other words there is
 * one thread per client on the server side.
 * 
 * Each client running in it's own thread shares the same database which is why
 * the collection class has synchronized methods to allow one request to
 * complete before another thread can jump into the middle. Use Vector<E>
 */
public class Server extends Thread {

	// change these constants if needed
	public static final int PORT_NUMBER = 4009;
	public static final String HOST_NAME = "localhost";
	public static final int SERVER_PLAYER = 1;
	public static final int CLIENT_PLAYER = 2;
	private ServerSocket myServerSocket; // client request source
	private Vector<String> connectedClients;
	private ArrayList<ObjectOutputStream> listOutputStreams = new ArrayList<ObjectOutputStream>();
	private ArrayList<Liason> listLiasons = new ArrayList<Liason>();

	public static void main(String args[]) {
		Server server = new Server(PORT_NUMBER);
		// Always call the start() method on a Thread. Don't call the run
		// method.
		server.start();
	}

	public Server(int port) {
		connectedClients = new Vector<String>();
		try {
			myServerSocket = new ServerSocket(PORT_NUMBER);
		} catch (IOException e) {
			System.out.println("In Server.Server(),the constructor");
		}
	}

	// This run() method is now in a separate Thread and will listen
	// for connections at PORT_NUMBER at the machine in which it is running
	@Override
	public void run() {
		try {
			while (listLiasons.size()<2) {
				// accept blocks until request comes over socket
				Socket intoServer = myServerSocket.accept();

				// For every new connection, start a new Thread that will
				// communicate with that client. Each one will have access
				// to the common collection of all users who are connected.
				Thread t = new Liason(intoServer, connectedClients, this);// ,
																			// idCount);
				// idCount++;

				if (intoServer == null) {
					continue;
				} else {
					if (intoServer.getOutputStream() != null)
						listOutputStreams.add(new ObjectOutputStream(intoServer
								.getOutputStream()));
					listLiasons.add((Liason) t);
					// always call the start() method on a Thread. Don't call
					// the run method.
					t.start();
					if(listLiasons.size()==2){
						listOutputStreams.get(0).writeObject(new Delivery("New Game Started", true, false, false, false, false));
						listOutputStreams.get(1).writeObject(new Delivery("New Game Started", true, false, false, false, false));
					}
				}
			}
		} catch (IOException e) {
			System.out.println("In Server.run");
			e.printStackTrace();
		}
	}

	// Sends message to via all avalible outputStreams to Clients.
	public void updateMessage(Delivery d) {
		// ObjectOutputStream current;
		try {
			listOutputStreams.get(0).writeObject(d);
			listOutputStreams.get(1).writeObject(d);
			/*
			if (d.player == SERVER_PLAYER) {
				
				if (d.messageForSelf) {
					listOutputStreams.get(0).writeObject(d);
				}
				if (d.messageForOther) {
					if(listOutputStreams.size()>=2){
					listOutputStreams.get(1).writeObject(d);}
				}
			} else {
				if (d.messageForSelf) {
					if(listOutputStreams.size()>=2){
					listOutputStreams.get(1).writeObject(d);}
				}
				if (d.messageForOther) {
					listOutputStreams.get(0).writeObject(d);
				}
			}*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * for (int i = 0; i < listOutputStreams.size(); i++) { try {
		 * listOutputStreams.get(i).writeObject(d); } catch (IOException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } }
		 */
	}

	// Removes the Liason and the OutputStream connected to a Client upon the
	// Client exiting.
	public void removeLiason(long id) {
		System.out.println(id + ":PassedID");
		int i = 0;
		while (listLiasons.get(i).getId() != id) {
			System.out.println(id + ":LiasonID");
			i++;
			if (i == listLiasons.size()) {
				return;
			}
		}
		listLiasons.remove(i);
		listOutputStreams.remove(i);
	}
}