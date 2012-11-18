package network;

import java.util.ArrayList;

public class Shop {

	//public String[] items = {"OOOO","XXXX","XOXO","OXOX"};
	
	
	public static enum Item {
		OOOO(1), XXXX(2), XOXO(3), OXOX(4);
		public int value;

        private Item(int value) {
                this.value = value;
        }
	}
	
	
}