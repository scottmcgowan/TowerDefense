package GUI;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import network.MultiPlayerGameController;
import network.Server;

public class MainMenu extends JFrame {

	public static void main(String[] args) {
		new MainMenu();
	}

	public MainMenu() {
		setSize(600, 400);
		setLayout(new FlowLayout());
		JPanel buttons = new JPanel();

		buttons.setLayout(new GridLayout(4, 1, 0, 5));

		JButton singlePlayer = new JButton("Single Player");
		JButton multiPlayerS = new JButton("MultiPlayer (Server)");
		JButton multiPlayerC = new JButton("MultiPlayer (Client)");
		JButton help = new JButton("Help");

		singlePlayer.addActionListener(new buttonAction());
		multiPlayerS.addActionListener(new buttonAction());
		multiPlayerC.addActionListener(new buttonAction());
		help.addActionListener(new buttonAction());

		buttons.add(singlePlayer);
		buttons.add(multiPlayerS);
		buttons.add(multiPlayerC);
		buttons.add(help);

		this.add(buttons);

		this.setLocationRelativeTo(null);
		setVisible(true);
	}

	private class buttonAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			MiscOptions options = new MiscOptions();
			JButton clickButton = (JButton) arg0.getSource();
			if (clickButton.getText().equals("Single Player")) {
			}
			if (clickButton.getText().equals("MultiPlayer (Server)")) {
				dispose();
				new MapSelection(new MultiPlayerGameController(Server.SERVER_PLAYER));
			}
			if (clickButton.getText().equals("MultiPlayer (Client)")) {
				dispose();
				new MultiPlayerGameController(Server.CLIENT_PLAYER);
			}
			if (clickButton.getText().equals("Help"))
				options.setHelpMessage();
		}
	}
}
