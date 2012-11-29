package network;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import GUI.GameCanvas;
import GUI.Map.Tile;

import network.MultiPlayerGameControllerSkel;

import model.Delivery;
import model.GameControllerInterface;
import model.PurchaseOrder;
import model.towers.Tower;

public class MultiPlayerShopPanel extends JPanel{
	GameCanvas canvas;
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	public static final int PANEL_WIDTH = 600;
	public static final int PANEL_HEIGHT = 100;
	private GameControllerInterface game;
	private int player;

	// GUI for the shop buttons
	public MultiPlayerShopPanel(int p, GameControllerInterface game) {
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

	public void updateButtons(int tileX, int tileY, int tileType) {
		// TODO Auto-generated method stub
		for(JButton b:buttons){
			b.setEnabled(true);
		}
		if(tileType==Tower.UNBUILDABLE){
			buttons.get(0).setEnabled(false);
			buttons.get(1).setEnabled(false);
			buttons.get(2).setEnabled(false);
			buttons.get(3).setEnabled(false);
			buttons.get(4).setEnabled(false);
			buttons.get(5).setEnabled(false);
		}else if(tileType==Tower.EMPTY){
			buttons.get(3).setEnabled(false);
			buttons.get(4).setEnabled(false);
			buttons.get(5).setEnabled(false);
		}else{
			buttons.get(0).setEnabled(false);
			buttons.get(1).setEnabled(false);
			buttons.get(2).setEnabled(false);
			buttons.get(3).setEnabled(false);
			buttons.get(4).setEnabled(false);
			buttons.get(5).setEnabled(false);
			buttons.get(tileType+2).setEnabled(true);
		}
		repaint();
	}
}