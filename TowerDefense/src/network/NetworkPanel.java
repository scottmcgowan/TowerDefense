package network;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import model.Delivery;

public class NetworkPanel extends JPanel implements Observer {

	private int player;

	private JTextField chatBar = new JTextField("And I say hey!");
	public JTextArea textArea = new JTextArea();
	private JScrollPane scrollPane = new JScrollPane(textArea);
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	private MultiPlayerGameController game;
	public static final int PANEL_WIDTH = 220;
	public static final int PANEL_HEIGHT = 372;

	public NetworkPanel(int p, MultiPlayerGameController game) {
		this.game = game;
		this.player = p;
		setLayout(null);
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setVisible(true);
		setFocusable(false);
		chatBar.setSize(PANEL_WIDTH, 20);
		chatBar.setLocation(0, 6);
		chatBar.setEditable(true);
		add(chatBar);

		textArea.setLocation(0, 36);
		textArea.setSize(PANEL_WIDTH, 332);
		textArea.setEditable(false);
		textArea.setLineWrap(true);

		scrollPane.setSize(PANEL_WIDTH, 332);
		scrollPane.setLocation(0, 36);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		// scrollPane.setAutoscrolls(true);
		add(scrollPane);

		int placement = 260;
		/*
		 * AllButtonListener listenerToAllButtons = new AllButtonListener();
		 * 
		 * for (MultiPlayerShop.Item i : MultiPlayerShop.Item.values()) {
		 * JButton b = new JButton(i.name()); buttons.add(b); b.setSize(270,
		 * 20); b.addActionListener(listenerToAllButtons); b.setLocation(30,
		 * placement); add(b); placement += 26; }
		 */

		chatBar.addActionListener(new ChatAction());
		repaint();
	}

	private class ChatAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String text = chatBar.getText();
			chatBar.setText("");
			if (text.equals("\\rate")) {
				game.updateRate(false);
			} else {
				String input = "Player " + player + "> " + text;
				// game.sendMessage(input);
				interpretDelivery(new Delivery(input, null, true, true, player));
			}
		}
	}

	/*
	 * private class AllButtonListener implements ActionListener {
	 * 
	 * public void actionPerformed(ActionEvent theEvent) { // Determine which of
	 * the five buttons was clicked JButton clickButton = (JButton)
	 * theEvent.getSource(); String text = clickButton.getText(); //
	 * game.sendMessage(text); interpretDelivery(new Delivery("Player " + player
	 * + " purchased " + text + ".", new PurchaseOrder(player, null), false,
	 * true, player)); } }
	 */

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("updateReceived");
		textArea.append((String) arg + "\n");
		textArea.selectAll();
		repaint();
	}

	public void interpretDelivery(Delivery d) {
		if (d.messageForOther) {
			game.sendDelivery(d);
		} else if (d.messageForSelf) {
			update(null, d.getMessage());
		}

	}
}
