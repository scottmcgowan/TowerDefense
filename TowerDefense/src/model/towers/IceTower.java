package model.towers;

import resources.Res;

public class IceTower extends Tower {

	public IceTower(int xPos, int yPos) {
		super(xPos, yPos, 80, Res.RANGE_ICE_1, Res.DAMAGE_ICE_1);
		// TODO: stuff
	}
	
	public int getID() {
		return Res.TOWER_ICE_TYPE;
	}
	
	public void upgrade() {
		if (level == 1) {
			super.setDamage(Res.DAMAGE_ICE_2);
			level++;
		}
		else if (level == 2) {
			super.setRange(Res.RANGE_ICE_2);
			level++;
		}
	}

}
