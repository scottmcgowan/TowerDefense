package model;

import java.io.Serializable;

//An entity that contains a message, the intended audience for the message, a possible 
//purchaseOrder that comes with, and possible messages about the outcome of the game.

public class Delivery implements Serializable{
	private String message;
	private PurchaseOrder po;
	public boolean messageForSelf;
	public boolean messageForOther;
	public int player;
	public boolean newGameReady = false;
	public boolean lose = false;
	public boolean tieMet = false;
	public boolean tied = false;
	public boolean pause = false;
	public boolean rate = false;
	
	public Delivery(String s, PurchaseOrder purchase, boolean self, boolean other, int p){
		message = s;
		po = purchase;
		player = p;
		messageForSelf = self;
		messageForOther = other;
	}
	
	public Delivery(String s, int p, boolean gameReady, boolean lost, boolean tieCondition, boolean tiedEnd, boolean self){
		message = s;
		player = p;
		newGameReady = gameReady;
		lose = lost;
		tieMet = tieCondition;
		tied = tiedEnd;
		messageForSelf = self;
		messageForOther = true;
	}
	
	public Delivery(String s, int p, boolean ps, boolean r){
		pause = ps;
		rate = r;
		message = s;
		player = p;
	}
	
	public String getMessage(){return message;}
	public PurchaseOrder getOrder(){return po;}
}
