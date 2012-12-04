package network;

import javax.swing.JButton;

import model.SinglePlayerShop;

public class ShopButton extends JButton {
	private SinglePlayerShop.Item spItem;
	private MultiPlayerShop.Item item;
	
	public ShopButton(String stuff, SinglePlayerShop.Item item) {
		super(stuff);
		this.setSPItem(item);
	}
	
	public ShopButton(String text, MultiPlayerShop.Item item) {
		super(text);
		this.setItem(item);
	}
	
	public SinglePlayerShop.Item getSPItem() {
		return spItem;
	}
	
	public void setSPItem(SinglePlayerShop.Item item) {
		spItem = item;
	}

	public MultiPlayerShop.Item getItem() {
		return item;
	}

	public void setItem(MultiPlayerShop.Item item) {
		this.item = item;
	}
	
}
