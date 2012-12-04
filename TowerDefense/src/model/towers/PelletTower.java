package model.towers;

import resources.Res;


public class PelletTower extends Tower{

	public PelletTower(int xPos, int yPos) {
		super(xPos, yPos, 40, 2.5);
		// TODO: stuff
	}
	
	public int getID() {
		return Res.TOWER_NO_TYPE;
	}

}
