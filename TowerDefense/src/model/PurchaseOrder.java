package model;

import java.io.Serializable;

import model.MultiPlayerShop.Item;

public class PurchaseOrder implements Serializable{
	private int player;
	private Item item;
	private int tile_x;
	private int tile_y;
	
	public PurchaseOrder(int p, Item i){
		player = p;
		item = i;
	}

	public PurchaseOrder(int p, Item i, int x, int y){
		player = p;
		item = i;
		tile_x = x;
		tile_y = y;
	}
}
