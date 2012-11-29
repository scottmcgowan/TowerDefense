package network;

import java.util.ArrayList;

import model.towers.Tower;

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
		FIRE_TOWER(100, TYPE_BUY_TOWER, Tower.FIRE_TYPE), 
		ICE_TOWER(100, TYPE_BUY_TOWER, Tower.ICE_TYPE), 
		LIGHTNING_TOWER(100, TYPE_BUY_TOWER, Tower.LIGHTNING_TYPE), 
		UPGRADE_FIRE(300, TYPE_UPGRADE_TOWER, Tower.FIRE_TYPE), 
		UPGRADE_ICE(300, TYPE_UPGRADE_TOWER, Tower.ICE_TYPE), 
		UPGRADE_LIGHTNING(300, TYPE_UPGRADE_TOWER, Tower.LIGHTNING_TYPE),
		FIVE_ENEMIES(100, TYPE_PURCHASE_ENEMY, Tower.NO_TYPE), 
		FIVE_SPEEDY_ENEMIES(300, TYPE_PURCHASE_ENEMY, Tower.NO_TYPE), 
		FIVE_BUFF_ENEMIES(400, TYPE_PURCHASE_ENEMY, Tower.NO_TYPE) ;
		public int value, type, towerType;

        private Item(int value, int type, int towerType) {
                this.value = value;
                this.type = type;
                this.towerType = towerType;
        }
	}
	
}