package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.GameControllerInterface;
import model.PurchaseOrder;
import model.SinglePlayerShop;
import model.SinglePlayerShop.Item;

public class SinglePlayerShopPanel extends JPanel {
	private int player;
	private GameControllerInterface game;
	GameCanvas canvas;
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	public static final int PANEL_WIDTH = 600;
	public static final int PANEL_HEIGHT = 50;

	// GUI for the shop buttons
	public SinglePlayerShopPanel(int p, GameControllerInterface g) {
		player = p;
		game = g;
		setLayout(new GridLayout(2, 3, 5, 5));
		setSize(PANEL_WIDTH, PANEL_HEIGHT);

		AllButtonListener listenerToAllButtons = new AllButtonListener();

		for (SinglePlayerShop.Item i : SinglePlayerShop.Item.values()) {
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
			JButton clickButton = (JButton) theEvent.getSource();
			Item i;
			String text = clickButton.getText().substring(0,
					clickButton.getText().length() - 7);
			
			// Write switch case

			// PurchaseOrder boughtItem = new PurchaseOrder(player, i);
		}
	}
	
	public void updateWithMoney(int money) {
		
	}
}
