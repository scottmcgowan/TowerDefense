package model.towers;

import resources.Res;


public class PelletTower extends Tower{

	public PelletTower(int xPos, int yPos) {
		super(xPos, yPos, 40, Res.RANGE_PELLET_1, Res.DAMAGE_PELLET_1);
		// TODO: stuff
	}
	
	public int getID() {
		return Res.TOWER_NO_TYPE;
	}
	
	public void upgrade() {
		if (level == 1) {
			super.setDamage(Res.DAMAGE_PELLET_2);
			level++;
		}
		else if (level == 2) {
			super.setRange(Res.RANGE_PELLET_2);
			level++;
		}
	}

}
