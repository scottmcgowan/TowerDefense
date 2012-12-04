package GUI;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import network.MultiPlayerGameController;
import network.Server;

public class MapSelection extends JFrame {
	int player;
	
	public MapSelection(int i) {
		player = i;
		setLayout(new FlowLayout());
		setSize(250, 100);
		setTitle("Map");
		JLabel text = new JLabel("Which map would you like to chose?");

		add(text);

		JPanel select = new JPanel();
		select.setSize(150, 50);
		select.setLayout(new GridLayout(1, 3, 5, 0));

		JButton map1 = new JButton("1");
		JButton map2 = new JButton("2");
		JButton map3 = new JButton("3");

		map1.addActionListener(new buttonAction());
		map2.addActionListener(new buttonAction());
		map3.addActionListener(new buttonAction());
		
		select.add(map1);
		select.add(map2);
		select.add(map3);

		add(select);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private class buttonAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			JButton clickButton = (JButton) arg0.getSource();
			if (clickButton.getText().equals("1")) {
				dispose();
				if(player == 1)
					new MultiPlayerGameController(Server.SERVER_PLAYER, 1);
				else
					new MultiPlayerGameController(Server.CLIENT_PLAYER, 1);
			}
			if (clickButton.getText().equals("2")) {
				dispose();
				if(player == 1)
					new MultiPlayerGameController(Server.SERVER_PLAYER, 2);
				else
					new MultiPlayerGameController(Server.CLIENT_PLAYER, 2);
			}
			if (clickButton.getText().equals("3")) {
				dispose();
				if(player == 1)
					new MultiPlayerGameController(Server.SERVER_PLAYER, 3);
				else
					new MultiPlayerGameController(Server.CLIENT_PLAYER, 3);
			}
		}
	}
}
