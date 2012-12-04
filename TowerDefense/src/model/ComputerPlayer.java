package model;

import network.*;

/*A dummy AI class that will send out three waves of enemies over the course of 2, 7, and 12 seconds
after it is created, assuming the game runs at 60fps.*/

public class ComputerPlayer extends Player{

	private GameControllerInterface gc;
	private int counter = 0;
	public int wave_remaining = 6;
	
	public ComputerPlayer(GameControllerInterface gc){
		super();
		this.gc = gc;
	}
	
	public void update(){
		counter++;
		switch(counter){
			case 100:
				gc.addOrder(new PurchaseOrder(2,MultiPlayerShop.Item.FIVE_ENEMIES));
				gc.addOrder(new PurchaseOrder(2,MultiPlayerShop.Item.FIVE_ENEMIES));
				wave_remaining -=1;
				break;
			case 1300:
				gc.addOrder(new PurchaseOrder(2,MultiPlayerShop.Item.FIVE_ENEMIES));
				gc.addOrder(new PurchaseOrder(2,MultiPlayerShop.Item.FIVE_ENEMIES));
				gc.addOrder(new PurchaseOrder(2,MultiPlayerShop.Item.FIVE_SPEEDY_ENEMIES));
				wave_remaining -=1;
				break;
			case 2200:
				gc.addOrder(new PurchaseOrder(2,MultiPlayerShop.Item.FIVE_SPEEDY_ENEMIES));
				gc.addOrder(new PurchaseOrder(2,MultiPlayerShop.Item.FIVE_ENEMIES));
				gc.addOrder(new PurchaseOrder(2,MultiPlayerShop.Item.FIVE_SPEEDY_ENEMIES));
				gc.addOrder(new PurchaseOrder(2,MultiPlayerShop.Item.FIVE_ENEMIES));
				wave_remaining -=1;
				break;
			case 3800:
				gc.addOrder(new PurchaseOrder(2,MultiPlayerShop.Item.FIVE_BUFF_ENEMIES));
				gc.addOrder(new PurchaseOrder(2,MultiPlayerShop.Item.FIVE_BUFF_ENEMIES));;
				gc.addOrder(new PurchaseOrder(2,MultiPlayerShop.Item.FIVE_ENEMIES));
				wave_remaining -=1;
				break;
			case 5500:
				gc.addOrder(new PurchaseOrder(2,MultiPlayerShop.Item.FIVE_BUFF_ENEMIES));
				gc.addOrder(new PurchaseOrder(2,MultiPlayerShop.Item.FIVE_BUFF_ENEMIES));
				gc.addOrder(new PurchaseOrder(2,MultiPlayerShop.Item.FIVE_SPEEDY_ENEMIES));
				gc.addOrder(new PurchaseOrder(2,MultiPlayerShop.Item.FIVE_BUFF_ENEMIES));
				gc.addOrder(new PurchaseOrder(2,MultiPlayerShop.Item.FIVE_SPEEDY_ENEMIES));
				gc.addOrder(new PurchaseOrder(2,MultiPlayerShop.Item.FIVE_ENEMIES));
				wave_remaining -=1;
				break;
			case 7500:
				gc.addOrder(new PurchaseOrder(2,MultiPlayerShop.Item.FIVE_BUFF_ENEMIES));
				gc.addOrder(new PurchaseOrder(2,MultiPlayerShop.Item.FIVE_ENEMIES));
				gc.addOrder(new PurchaseOrder(2,MultiPlayerShop.Item.FIVE_BUFF_ENEMIES));
				gc.addOrder(new PurchaseOrder(2,MultiPlayerShop.Item.FIVE_ENEMIES));
				gc.addOrder(new PurchaseOrder(2,MultiPlayerShop.Item.FIVE_BUFF_ENEMIES));
				gc.addOrder(new PurchaseOrder(2,MultiPlayerShop.Item.FIVE_SPEEDY_ENEMIES));
				gc.addOrder(new PurchaseOrder(2,MultiPlayerShop.Item.FIVE_ENEMIES));
				gc.addOrder(new PurchaseOrder(2,MultiPlayerShop.Item.FIVE_SPEEDY_ENEMIES));
				wave_remaining -=1;
				break;
			case 8500:
				wave_remaining -=1;
				break;
		}
	}
}
