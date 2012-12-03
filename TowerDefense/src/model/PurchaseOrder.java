package model;

import java.io.Serializable;

import network.MultiPlayerShop.Item;

public class PurchaseOrder implements Serializable{
	private int player;
	private Item item;
	private int tile_x;
	private int tile_y;
	
	public PurchaseOrder(int p, Item i){
		setPlayer(p);
		setItem(i);
	}

	public PurchaseOrder(int p, Item i, int x, int y){
		setPlayer(p);
		setItem(i);
		setTile_x(x);
		setTile_y(y);
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public int getTile_x() {
		return tile_x;
	}

	public void setTile_x(int tile_x) {
		this.tile_x = tile_x;
	}

	public int getTile_y() {
		return tile_y;
	}

	public void setTile_y(int tile_y) {
		this.tile_y = tile_y;
	}
}

