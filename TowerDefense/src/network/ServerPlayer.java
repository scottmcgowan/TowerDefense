package network;

import javax.swing.JFrame;

public class ServerPlayer {
	
	public static void main(String args[]) {
		Server server = new Server(Server.PORT_NUMBER);
		server.start();
		NetworkPanel np = new NetworkPanel(Server.SERVER_PLAYER);
		JFrame gui = new JFrame();
		gui.setTitle("Game");
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(350, 500);
		gui.setVisible(true);
		gui.add(np);
	}
	
}
