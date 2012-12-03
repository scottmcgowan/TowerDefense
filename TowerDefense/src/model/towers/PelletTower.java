package model.towers;

import resources.Res;


public class PelletTower extends Tower{

	public PelletTower(int xPos, int yPos) {
		super(xPos, yPos);
		// TODO: stuff
	}
	
	public int getID() {
		return Res.TOWER_NO_TYPE;
	}

}
