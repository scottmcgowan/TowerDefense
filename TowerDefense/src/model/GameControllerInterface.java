package model;

import java.util.ArrayList;

public interface GameControllerInterface{

	//public ArrayList<PurchaseOrder> listOrders;
	
	public void sendDelivery(Delivery d);
	public void addOrder(PurchaseOrder po);
	void gameLoop();
	abstract void gameUpdate();
	abstract void draw(ArrayList<Drawable> arr);
	abstract void processOrders();

}
