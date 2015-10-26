package AEstrela;

import funcional.TileBasedMap;

public interface AStarHeuristic {

	public float getCost(TileBasedMap map, int mover, int x, int y, int tx, int ty);
}