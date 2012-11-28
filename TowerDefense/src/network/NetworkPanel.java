<<<<<<< HEAD
package network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import model.Delivery;
import model.PurchaseOrder;
import model.MultiPlayerShop;
import model.MultiPlayerShop.Item;

public class NetworkPanel extends JPanel implements Observer {

	public int player;

	private JTextField chatBar = new JTextField("And I say hey!");
	public JTextArea textArea = new JTextArea();
	private JScrollPane scrollPane = new JScrollPane(textArea);
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	GameLogic game;
	
	public NetworkPanel(int p) {
		player = p;
		game = new GameLogic();
		game.addObserver(this);
		setLayout(null);
		setSize(350, 500);
		setVisible(true);

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
	}

	private class ChatAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String input = chatBar.getText();
			// game.sendMessage(input);
			interpretDelivery(new Delivery(input, null, true, true, player));
			chatBar.setText("");
		}
	}

	

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("updateReceived");
		textArea.append((String) arg + "\n");
		repaint();
	}

	public void interpretDelivery(Delivery d) {
		if (d.messageForOther) {
			game.sendDelivery(d);
		} else if (d.messageForSelf) {
			update(null, d.getMessage());
		}
	}
=======
package network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import model.Delivery;
import model.PurchaseOrder;
import model.MultiPlayerShop;
import model.MultiPlayerShop.Item;

public class NetworkPanel extends JPanel implements Observer {

	public int player;

	private JTextField chatBar = new JTextField("And I say hey!");
	public JTextArea textArea = new JTextArea();
	private JScrollPane scrollPane = new JScrollPane(textArea);
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	GameControllerSkel game;
	
	public NetworkPanel(GameControllerSkel game) {
		this.game = game;
		setLayout(null);
		setSize(350, 500);
		setVisible(true);

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
		/*
		AllButtonListener listenerToAllButtons = new AllButtonListener();

		for (MultiPlayerShop.Item i : MultiPlayerShop.Item.values()) {
			JButton b = new JButton(i.name());
			buttons.add(b);
			b.setSize(270, 20);
			b.addActionListener(listenerToAllButtons);
			b.setLocation(30, placement);
			add(b);
			placement += 26;
		}*/

		chatBar.addActionListener(new ChatAction());
		repaint();
	}

	private class ChatAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String input = chatBar.getText();
			// game.sendMessage(input);
			interpretDelivery(new Delivery(input, null, true, true, player));
			chatBar.setText("");
		}
	}

	/*
	private class AllButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent theEvent) {
			// Determine which of the five buttons was clicked
			JButton clickButton = (JButton) theEvent.getSource();
			String text = clickButton.getText();
			// game.sendMessage(text);
			interpretDelivery(new Delivery("Player " + player + " purchased "
					+ text + ".", new PurchaseOrder(player, null), false, true,
					player));
		}
	}*/

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("updateReceived");
		textArea.append((String) arg + "\n");
		repaint();
	}

	public void interpretDelivery(Delivery d) {
		if (d.messageForOther) {
			game.sendDelivery(d);
		} else if (d.messageForSelf) {
			update(null, d.getMessage());
		}
	}
>>>>>>> Networking Code Refactor
}