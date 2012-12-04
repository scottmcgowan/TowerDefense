package model.towers;

import resources.Res;

public class FireTower extends Tower {

	public FireTower(int xPos, int yPos) {
		super(xPos, yPos, 60, 2.0);
		// TODO: stuff
	}
	
	public int getID() {
		return Res.TOWER_FIRE_TYPE;
	}

}
