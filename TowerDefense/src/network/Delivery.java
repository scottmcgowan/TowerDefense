package network;

public class Delivery {
	private String message;
	private PurchaseOrder po;
	public boolean messageForSelf;
	public boolean messageForOther;
	public int player;
	
	public Delivery(String s, PurchaseOrder purchase, boolean self, boolean other, int p){
		message = s;
		po = purchase;
		player = p;
		messageForSelf = self;
		messageForOther = other;
	}
	
	public String getMessage(){return message;}
	public PurchaseOrder getOrder(){return po;}
}
