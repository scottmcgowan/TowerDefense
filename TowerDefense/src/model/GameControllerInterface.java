package model;

import java.util.ArrayList;

import GUI.Map;

import network.MultiPlayerShop;

public interface GameControllerInterface{

	//public ArrayList<PurchaseOrder> listOrders;
	
	public void sendDelivery(Delivery d);
	public void addOrder(PurchaseOrder po);
	void gameLoop();
	void gameUpdate();
	void draw(ArrayList<Drawable> arr);
	void processOrders();
	void drawHealthBars();
	public void notifyShopOfSelection(int tileX, int tileY, Map.Tile tile);
	void updateShopWithCurrentMoney();

}
