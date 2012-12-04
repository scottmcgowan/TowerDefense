package model.towers;

import resources.Res;

public class LightningTower extends Tower{
	
	public LightningTower(int xPos, int yPos) {
		super(xPos, yPos, Res.RATE_LIGHTNING_1, Res.RANGE_LIGHTNING_1, Res.DAMAGE_LIGHTNING_1);
		// TODO: stuff
	}
	
	public int getID() {
		return Res.TOWER_LIGHTNING_TYPE;
	}
	
	public void upgrade() {
		if (level == 1) {
			super.setRange(Res.RANGE_LIGHTNING_2);
			level++;
		}
		else if (level == 2) {
			super.setDamage(Res.DAMAGE_LIGHTNING_2);
			level++;
		}
	}
	
}
