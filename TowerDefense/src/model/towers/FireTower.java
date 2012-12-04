package model.towers;

import resources.Res;

public class FireTower extends Tower {

	public FireTower(int xPos, int yPos) {
		super(xPos, yPos, 60, Res.RANGE_FIRE_1, Res.DAMAGE_FIRE_1);
		// TODO: stuff
	}
	
	public int getID() {
		return Res.TOWER_FIRE_TYPE;
	}

	public void upgrade() {
		if (level == 1) {
			super.setDamage(Res.DAMAGE_FIRE_2);
			level++;
		}
		else if (level == 2) {
			super.setRange(Res.RANGE_FIRE_2);
			level++;
		}
	}

}
