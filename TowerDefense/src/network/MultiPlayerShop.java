package network;

import java.util.ArrayList;

public class MultiPlayerShop {

	//public String[] items = {"OOOO","XXXX","XOXO","OXOX"};
	
	public static final int TYPE_BUY_TOWER = 1;
	public static final int TYPE_UPGRADE_TOWER = 2;
	public static final int TYPE_PURCHASE_ENEMY = 3;
	
	/*
	public static enum OrderType{
		BUY_TOWER(1), 
		UPGRADE_TOWER(2), 
		PURCHASE_ENEMY(3);
		
		public int type;

        private OrderType(int type) {
                this.type = type;
        }
	}*/
	
	public static enum Item {
		ICE_TOWER(100, 1), 
		FIRE_TOWER(100, 1 ), 
		LIGHTNING_TOWER(100, 1), 
		FIVE_ENEMIES(100, 3), 
		FIVE_SPEEDY_ENEMIES(300, 3), 
		FIVE_BUFF_ENEMIES(400, 3), 
		UPGRADE_FIRE(300, 2), 
		UPGRADE_ICE(300, 2), 
		UPGRADE_LIGHTNING(300, 2),;
		public int value, type;

        private Item(int value, int type) {
                this.value = value;
                this.type = type;
        }
	}
	
}