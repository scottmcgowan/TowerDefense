package model;

import java.io.Serializable;

//An entity that contains a message, the intended audience for the message, and a possible 
//purchaseOrder that comes with.

public class Delivery implements Serializable{
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
