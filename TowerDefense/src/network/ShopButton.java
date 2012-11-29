package network;

import javax.swing.JButton;

public class ShopButton extends JButton {
	public MultiPlayerShop.Item item;

	public ShopButton(String text, MultiPlayerShop.Item item) {
		super(text);
		this.item = item;
	}
}
