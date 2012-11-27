package GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GameGUI extends JFrame {

	// Parameters for the JPanel
	static final int JFRAME_WIDTH = 800;
	static final int JFRAME_HEIGHT = 600;

	private JPanel currentView;
	private JPanel gameView;
	private JPanel menuView;
	private GameCanvas canvas;
	private ShopGUI shop;

	// Constructor to initialize the UI components and game objects
	public GameGUI() {
		// Initialize the game objects
		gameInit();
		this.setLayout(null);
		this.setPreferredSize(new Dimension(JFRAME_WIDTH, JFRAME_HEIGHT));

		// UI components
		gameView = new JPanel();
//		gameView.setLayout(new FlowLayout());
		// Other UI components such as button, score board, if any.
		// ......

		// this.add(GameView);
		menuView = new Menu();
		menuView.setSize(JFRAME_WIDTH, JFRAME_HEIGHT);

		add(menuView);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setTitle("MY GAME");
		this.setVisible(true);
	}

	public class Menu extends JPanel {

		private JPanel MenuButtons = new JPanel(new GridLayout(4, 1, 0, 5));
		public Menu() {
			this.setLayout(null);

			MenuButtons.setSize(200, 300);
			MenuButtons.setLocation(JFRAME_WIDTH-250, JFRAME_HEIGHT-475);

			JButton singlePlayer = new JButton("Single Player");
			JButton serverPlayer = new JButton("Multiplayer (Server)");
			JButton clientPlayer = new JButton("Multiplayer (Client)");
			JButton help = new JButton("Help");

			singlePlayer.addActionListener(new buttonListener());
			serverPlayer.addActionListener(new buttonListener());
			clientPlayer.addActionListener(new buttonListener());
			help.addActionListener(new buttonListener());

			MenuButtons.add(singlePlayer);
			MenuButtons.add(serverPlayer);
			MenuButtons.add(clientPlayer);
			MenuButtons.add(help);

			this.add(MenuButtons);
		}

		private class buttonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				JButton button = (JButton) arg0.getSource();
				if (button.getText().equals("Single Player")) {
					shop = new ShopGUI();
					shop.setLocation(10, 400);

					canvas = new GameCanvas();
					canvas.setLocation(0, 0);
					shop.connectToMap(canvas);

					gameView.add(shop);
					gameView.add(canvas);

					remove(menuView);
					remove(MenuButtons);
					repaint();
					
					gameView.setLayout(null);
					gameView.setSize(JFRAME_WIDTH, JFRAME_HEIGHT);
					add(gameView);
					gameView.revalidate();
					gameView.repaint();
				}
			}
		}
	}

	// ------ All the game related codes here ------
	// Initialize all the game objects, run only once.
	public void gameInit() {
	}
	// Start and re-start the game.
	public void gameStart() {
	}
	// Shutdown the game, clean up code that runs only once.
	public void gameShutdown() {
	}
	// One step of the game.
	public void gameUpdate() {
	}
	// Refresh the display after each step.
	// Use (Graphics g) as argument if you are not using Java 2D.
	public void gameDraw(Graphics2D g2d) {
	}
	// main
	public static void main(String[] args) {
		// Use the event dispatch thread to build the UI for thread-safety.
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GameGUI();
			}
		});
	}
}