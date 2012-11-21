package GUI;

public class Map {

	public enum Tile {
		ENVIRONMENT, START, GOAL, PATH, TOWER
	}

	private int rowCount = 12;
	private int colCount = 16;
	Tile[][] map = new Tile[rowCount][colCount];
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
						map[row][col] = Tile.PATH;
					if (path[row][col] == 2)
						map[row][col] = Tile.START;
					if (path[row][col] == 3)
						map[row][col] = Tile.GOAL;
				}
				else
					map[row][col] = Tile.ENVIRONMENT;
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