package network;

import javax.swing.JButton;

public class ShopButton extends JButton {
	private MultiPlayerShop.Item item;

	public ShopButton(String text, MultiPlayerShop.Item item) {
		super(text);
		this.setItem(item);
	}

	public MultiPlayerShop.Item getItem() {
		return item;
	}

	public void setItem(MultiPlayerShop.Item item) {
		this.item = item;
	}
	
}
