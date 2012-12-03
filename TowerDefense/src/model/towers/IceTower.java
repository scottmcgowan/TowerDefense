package model.towers;

import resources.Res;

public class IceTower extends Tower {

	public IceTower(int xPos, int yPos) {
		super(xPos, yPos);
		// TODO: stuff
	}
	
	public int getID() {
		return Res.TOWER_ICE_TYPE;
	}

}
