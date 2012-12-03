package model.towers;

import resources.Res;

public class LightningTower extends Tower{
	
	public LightningTower(int xPos, int yPos) {
		super(xPos, yPos);
		// TODO: stuff
	}
	
	public int getID() {
		return Res.TOWER_LIGHTNING_TYPE;
	}
	
}
