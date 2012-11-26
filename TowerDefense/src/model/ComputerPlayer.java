package model;

import network.*;

/*A dummy AI class that will send out three waves of enemies over the course of 2, 7, and 12 seconds
after it is created, assuming the game runs at 60fps.*/

public class ComputerPlayer extends Player{

	private GameControllerSkel gc;
	private int counter = 0;
	
	public ComputerPlayer(GameControllerSkel gc){
		super();
		this.gc = gc;
	}
	
	public void update(){
		counter++;
		switch(counter){
			case 120:
				gc.addOrder(new PurchaseOrder(2,Shop.Item.FIVE_ENEMIES));
				break;
			case 420:
				gc.addOrder(new PurchaseOrder(2,Shop.Item.FIVE_SPEEDY_ENEMIES));
				break;
			case 720:
				gc.addOrder(new PurchaseOrder(2,Shop.Item.FIVE_BUFF_ENEMIES));
				break;
		}
	}
}
