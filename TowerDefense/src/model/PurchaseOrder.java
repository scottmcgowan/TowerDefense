package model;

import java.io.Serializable;

import network.MultiPlayerShop.Item;

public class PurchaseOrder implements Serializable{
	private int player;
	private Item item;
	private int tile_x;
	private int tile_y;
	
	public PurchaseOrder(int p, Item i){
		player = p;
		setItem(i);
	}

	public PurchaseOrder(int p, Item i, int x, int y){
		player = p;
		setItem(i);
		tile_x = x;
		tile_y = y;
	}
	
	public int getPlayer() {
		return player;
	}
	
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
}
