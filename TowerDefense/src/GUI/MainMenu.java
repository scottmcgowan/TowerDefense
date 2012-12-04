package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainMenu extends JFrame {

	public MainMenu(){
		setSize(200,400);
		setLayout(new GridLayout(4,1,0,5));
		setVisible(true);
		
		JButton singlePlayer = new JButton("Single Player");
		JButton multiPlayerS = new JButton("MultiPlayer (Server)");
		JButton multiPlayerC = new JButton("MultiPlayer (Client)");
		JButton help = new JButton("Help");
		
		add(singlePlayer);
		add(multiPlayerS);
		add(multiPlayerC);
		add(help);
	}
	
	private class buttonAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			JButton clickButton = (JButton) arg0.getSource();
			if(clickButton.getText().equals("Single Player"));
		}
		
	}
}
