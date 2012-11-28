package network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import model.MultiPlayerShop.Item;
import model.MultiPlayerShop;

public class GameGUI_DEPRECATED extends JFrame implements Observer{

	public static void main(String[] args) {
		GameGUI_DEPRECATED gui = new GameGUI_DEPRECATED();
	}
	
	private JTextField chatBar = new JTextField("And I say hey!");
	public JTextArea textArea = new JTextArea();
	private JScrollPane scrollPane = new JScrollPane(textArea);
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	GameLogic game;

	public GameGUI_DEPRECATED() {
		game = new GameLogic();
		game.addObserver(this);
		
		setTitle("Game");
		setSize(350, 500);
		setLayout(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		chatBar.setSize(310, 20);
		chatBar.setLocation(13, 10);
		chatBar.setEditable(true);
		add(chatBar);

		textArea.setLocation(13, 40);
		textArea.setSize(310, 210);
		textArea.setEditable(false);
		textArea.setLineWrap(true);

		scrollPane.setSize(310, 210);
		scrollPane.setLocation(13, 40);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane);

		int placement = 260;
		// 70/5 = 14
		// int counter = 0;

		AllButtonListener listenerToAllButtons = new AllButtonListener();
		
		for (Item i : MultiPlayerShop.Item.values()) {
			JButton b = new JButton(i.name());
			buttons.add(b);
			b.setSize(270, 20);
			b.addActionListener(listenerToAllButtons);
			b.setLocation(30, placement);
			add(b);
			placement += 26;
		}

		chatBar.addActionListener(new ChatAction());
		repaint();
	}

	private class ChatAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String input = chatBar.getText();
			game.sendMessage(input);
			chatBar.setText("");
		}
	}

	private class AllButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent theEvent) {
			// Determine which of the five buttons was clicked
			JButton clickButton = (JButton) theEvent.getSource();
			String text = clickButton.getText();
			game.sendMessage(text);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("updateReceived");
		textArea.append((String)arg + "\n");
		repaint();
	}
}