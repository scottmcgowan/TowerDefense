package network;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import GUI.GameCanvas;

import network.GameControllerSkel;

import model.Delivery;
import model.PurchaseOrder;

public class MultiPlayerShopPanel extends JPanel{
	GameCanvas canvas;
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	public static final int PANEL_WIDTH = 600;
	public static final int PANEL_HEIGHT = 100;
	private GameControllerSkel game;
	private int player;

	// GUI for the shop buttons
	public MultiPlayerShopPanel(int p, GameControllerSkel game) {
		this.game = game;
		this.player = p;
		setLayout(new GridLayout(3, 9, 5, 5));
		setSize(PANEL_WIDTH, PANEL_HEIGHT);

		AllButtonListener listenerToAllButtons = new AllButtonListener();

		for (MultiPlayerShop.Item i : MultiPlayerShop.Item.values()) {
			JButton b = new JButton(i.name() + "  " + "(" + i.value + ")");
			buttons.add(b);
			b.addActionListener(listenerToAllButtons);
			add(b);
		}
		repaint();
	}

	public void connectToMap(GameCanvas game) {
		this.canvas = game;
	}

	private class AllButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent theEvent) {
			// Determine which of the five buttons was clicked
			JButton clickButton = (JButton) theEvent.getSource();
			String text = clickButton.getText().substring(0,
					clickButton.getText().length() - 7);
			// game.sendMessage(text);
			canvas.setSelected(text);

			if (canvas.getClicked() != null)
				canvas.purchase();

			interpretDelivery(new Delivery("Player " + player + " purchased "
					+ text + ".", new PurchaseOrder(player, null), false, true,
					player));
		}
	}
	
	public void interpretDelivery(Delivery d) {
			game.sendDelivery(d);
	}
}