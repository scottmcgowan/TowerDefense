package GUI;

import model.towers.Tower;

public class Map {

	public enum Tile {
		ENVIRONMENT(Tower.EMPTY), 
		START(Tower.UNBUILDABLE), 
		GOAL(Tower.UNBUILDABLE), 
		PATH(Tower.UNBUILDABLE),
		ICE_TOWER(Tower.ICE_TYPE), 
		FIRE_TOWER(Tower.FIRE_TYPE), 
		LIGHTNING_TOWER(Tower.LIGHTNING_TYPE);
		
		public int tileType;
		
		private Tile(int tileType){
			this.tileType = tileType;
		}
	}

	private int rowCount = 12;
	private int colCount = 16;
	Tile[][] tileMap = new Tile[rowCount][colCount];
	int[][] path =  new int[rowCount][colCount];

	public Map() {
		//Initialize path
		for(int row = 0; row < path.length; row++) {
			for(int col = 0; col < path[row].length; col++) {
				path[row][col] = 0;
			}
		}

		//Initialize Map
		setPath(path);
	}

	public void setPath(int[][] path) {
		this.path = path;

		for(int row = 0; row < path.length; row++) {
			for(int col = 0; col < path[row].length; col++) {
				if(path[row][col] != 0) {
					if (path[row][col] == 1)
						tileMap[row][col] = Tile.PATH;
					if (path[row][col] == 2)
						tileMap[row][col] = Tile.START;
					if (path[row][col] == 3)
						tileMap[row][col] = Tile.GOAL;
				}
				else
					tileMap[row][col] = Tile.ENVIRONMENT;
			}
		}
	}
	
	public int getRow() {
		return rowCount;
	}

	public int getCol() {
		return colCount;
	}
}