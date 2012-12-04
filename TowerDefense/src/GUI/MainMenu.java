package GUI;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class MainMenu extends JFrame {

	JPanel buttons; 
	ArrayList<JButton> listButtons = new ArrayList<JButton>();
	
	public static void main(String[] args) {
		new MainMenu();
	}

	public MainMenu() {
		setSize(644, 352);
		setTitle("Main Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		
		buttons = new JPanel();
		buttons.setOpaque(false);
		buttons.setLayout(new GridLayout(4, 1, 0, 5));

		JButton singlePlayer = new JButton("Single Player");
		JButton multiPlayerS = new JButton("MultiPlayer (Server)");
		JButton multiPlayerC = new JButton("MultiPlayer (Client)");
		JButton help = new JButton("Help");

		singlePlayer.addActionListener(new buttonAction());
		multiPlayerS.addActionListener(new buttonAction());
		multiPlayerC.addActionListener(new buttonAction());
		help.addActionListener(new buttonAction());

		listButtons.add(singlePlayer);
		listButtons.add(multiPlayerS);
		listButtons.add(multiPlayerC);
		listButtons.add(help);
		buttons.add(singlePlayer);
		buttons.add(multiPlayerS);
		buttons.add(multiPlayerC);
		buttons.add(help);

		this.add(buttons);

		this.setLocationRelativeTo(null);
		buttons.repaint();
		setVisible(true);
	}
	


		public void paint(Graphics g) {
			Graphics2D gr = (Graphics2D) g;
			try {
				paintComponents(g);
				gr.drawImage(ImageIO.read(new File("images/Shovelware.png")),
						0, 0, this);
				for(JButton b:listButtons){
					b.repaint();
				}
			} catch (IOException e) {
			}
		}


	private class buttonAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			MiscOptions options = new MiscOptions();
			JButton clickButton = (JButton) arg0.getSource();
			if (clickButton.getText().equals("Single Player")) {
				dispose();
				new MapSelection(0);
			}
			if (clickButton.getText().equals("MultiPlayer (Server)")) {
				dispose();
				new MapSelection(1);
			}
			if (clickButton.getText().equals("MultiPlayer (Client)")) {
				dispose();
				new MapSelection(2);
			}
			if (clickButton.getText().equals("Help")){
				options.setHelpMessage(clickButton);}
		}
	}
}
