package network;

import javax.swing.JFrame;

public class ClientPlayer {

	public static void main(String args[]) {
		NetworkPanel np = new NetworkPanel(Server.CLIENT_PLAYER);
		JFrame gui = new JFrame();
		gui.setTitle("Game");
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(350, 500);
		gui.setVisible(true);
		gui.add(np);
	}
}
