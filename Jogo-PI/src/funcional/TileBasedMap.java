package funcional;


public interface TileBasedMap {
	
	public int getWidthInTiles();
	public int getHeightInTiles();
	public void pathFinderVisited(int x, int y);
	public boolean blocked(int mover, int x, int y);
	public float getCost(int mover, int sx, int sy, int tx, int ty);
}
