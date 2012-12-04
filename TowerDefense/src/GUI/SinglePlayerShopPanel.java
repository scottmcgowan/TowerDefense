package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import resources.Res;
import model.SinglePlayerGameController;
import model.PurchaseOrder;
import network.MultiPlayerShop;
import network.ShopButton;

public class SinglePlayerShopPanel extends JPanel {
	BackgroundPanel background;
	private ArrayList<ShopButton> buttons = new ArrayList<ShopButton>();
	public static final int PANEL_WIDTH = 600;
	public static final int PANEL_HEIGHT = 50;
	private SinglePlayerGameController game;
	private int playerInt, currentTileX, currentTileY, tile_type = Res.SPACE_UNBUILDABLE;

	// GUI for the shop buttons
	public SinglePlayerShopPanel(SinglePlayerGameController game, int player) {
		playerInt = player;
		this.game = game;
		setLayout(new GridLayout(2, 3, 5, 5));
		setSize(PANEL_WIDTH, PANEL_HEIGHT);

		AllButtonListener listenerToAllButtons = new AllButtonListener();

		for (MultiPlayerShop.Item i : MultiPlayerShop.Item.values()) {
			if (i.type != 3) {
				ShopButton b = new ShopButton(i.name() + "  " + "(" + i.value + ")", i);
				buttons.add(b);
				b.setFocusable(false);
				b.addActionListener(listenerToAllButtons);
				add(b);
			}
		}
		repaint();
	}

	public void connectToMap(GameCanvas canvas) {
		background = canvas.getBackgroundPanel();
	}

	private class AllButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent theEvent) {
			// Determine which of the five buttons was clicked
			ShopButton clickButton = (ShopButton) theEvent.getSource();
			String text = clickButton.getText().substring(0,
					clickButton.getText().length() - 7);
			// game.sendMessage(text);
			
			PurchaseOrder boughtItem = new PurchaseOrder(playerInt, clickButton.getItem());
			int itemType = clickButton.getItem().type;
			int purchase = MultiPlayerShop.TYPE_BUY_TOWER;
			int upgrade = MultiPlayerShop.TYPE_UPGRADE_TOWER;
			if (itemType == purchase || itemType == upgrade) {
				boughtItem.setTile_x(currentTileX);
				boughtItem.setTile_y(currentTileY);
			}
			game.addOrder(boughtItem);
		}
	}
	
	public void updateWithMoney(int money) {
		// TODO Auto-generated method stub
		updateButtons(currentTileX, currentTileY, tile_type);
		for (ShopButton b : buttons) {
			if (b.getItem().value > money)
				b.setEnabled(false);
		}
	}
	
	public void updateButtons(int x, int y, int type) {
		currentTileX = x;
		currentTileY = y;
		tile_type = type;
		for (ShopButton b : buttons) {
			b.setEnabled(true);
		}
		if (type == Res.SPACE_UNBUILDABLE) {
			for (ShopButton b : buttons) {
				if (b.getItem().type == MultiPlayerShop.TYPE_BUY_TOWER)
					b.setEnabled(false);
				if (b.getItem().type == MultiPlayerShop.TYPE_UPGRADE_TOWER)
					b.setEnabled(false);
			}
		} else if (type == Res.SPACE_EMPTY) {
			for (ShopButton b : buttons) {
				if (b.getItem().type == MultiPlayerShop.TYPE_UPGRADE_TOWER)
					b.setEnabled(false);
			}
		} else {
			for (ShopButton b : buttons) {
				if (b.getItem().type == MultiPlayerShop.TYPE_BUY_TOWER)
					b.setEnabled(false);
				if (b.getItem().type == MultiPlayerShop.TYPE_UPGRADE_TOWER) {
					if (b.getItem().towerType == type) {
						b.setEnabled(true);
					} else {
						b.setEnabled(false);
					}
				}
			}
		}
		repaint();
	}
}
