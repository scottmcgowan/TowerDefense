package network;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import resources.Res;

import GUI.GameCanvas;
import GUI.Map.Tile;

import network.MultiPlayerGameControllerSkel;

import model.Delivery;
import model.GameControllerInterface;
import model.PurchaseOrder;
import model.towers.Tower;

public class MultiPlayerShopPanel extends JPanel {
	GameCanvas canvas;
	private ArrayList<ShopButton> buttons = new ArrayList<ShopButton>();
	public static final int PANEL_WIDTH = 600;
	public static final int PANEL_HEIGHT = 100;
	private GameControllerInterface game;
	private int player;
	private int currentTileX;
	private int currentTileY;

	// GUI for the shop buttons
	public MultiPlayerShopPanel(int p, GameControllerInterface game) {
		this.game = game;
		this.player = p;
		setLayout(new GridLayout(3, 9, 5, 5));
		setSize(PANEL_WIDTH, PANEL_HEIGHT);

		AllButtonListener listenerToAllButtons = new AllButtonListener();

		for (MultiPlayerShop.Item i : MultiPlayerShop.Item.values()) {
			ShopButton b = new ShopButton(
					i.name() + "  " + "(" + i.value + ")", i);
			buttons.add(b);
			b.addActionListener(listenerToAllButtons);
			if (b.getItem().type != MultiPlayerShop.TYPE_PURCHASE_ENEMY) {
				b.setEnabled(false);
			}
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
			ShopButton clickButton = (ShopButton) theEvent.getSource();
			String text = clickButton.getText().substring(0,
					clickButton.getText().length() - 7);
			// game.sendMessage(text);
			canvas.setSelected(text);

			if (canvas.getClicked() != null)
				canvas.purchase();

			PurchaseOrder boughtItem = new PurchaseOrder(player,
					clickButton.getItem());
			if (clickButton.getItem().type == MultiPlayerShop.TYPE_BUY_TOWER) {
				boughtItem.setTile_x(currentTileX);
				boughtItem.setTile_y(currentTileY);
			} else if (clickButton.getItem().type == MultiPlayerShop.TYPE_UPGRADE_TOWER) {
				boughtItem.setTile_x(currentTileX);
				boughtItem.setTile_y(currentTileY);
			}
			interpretDelivery(new Delivery("Player " + player + " purchased "
					+ text + ".", boughtItem, false, true, player));
			// false, true

		}
	}

	public void interpretDelivery(Delivery d) {
		game.sendDelivery(d);
	}

	public void updateButtons(int tileX, int tileY, int tileType) {
		// TODO Auto-generated method stub
		currentTileX = tileX;
		currentTileY = tileY;
		for (ShopButton b : buttons) {
			b.setEnabled(true);
		}
		if (tileType == Res.SPACE_UNBUILDABLE) {
			for (ShopButton b : buttons) {
				if (b.getItem().type == MultiPlayerShop.TYPE_BUY_TOWER)
					b.setEnabled(false);
				if (b.getItem().type == MultiPlayerShop.TYPE_UPGRADE_TOWER)
					b.setEnabled(false);
			}
		} else if (tileType == Res.SPACE_EMPTY) {
			for (ShopButton b : buttons) {
				if (b.getItem().type == MultiPlayerShop.TYPE_UPGRADE_TOWER)
					b.setEnabled(false);
			}
		} else {
			for (ShopButton b : buttons) {
				if (b.getItem().type == MultiPlayerShop.TYPE_BUY_TOWER)
					b.setEnabled(false);
				if (b.getItem().type == MultiPlayerShop.TYPE_UPGRADE_TOWER) {
					if (b.getItem().towerType == tileType) {
						b.setEnabled(true);
					} else {
						b.setEnabled(false);
					}
				}
			}
		}
		repaint();
	}

	public void updateWithMoney(int money) {
		// TODO Auto-generated method stub
		for (ShopButton b : buttons) {
			if (b.getItem().value >= money)
				b.setEnabled(false);
		}
	}

}