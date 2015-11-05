package AEstrela;

import funcional.TileBasedMap;

public class Heuristica {
	// Calcula heuristica
	public float getCost(TileBasedMap mapa, int mover, int x, int y, int tx, int ty) {
		float dx = tx - x;
		float dy = ty - y;
		
		/** Manhattan */
//		float result = (float) (dx + dy);
		
		/** Euclidiana */
//		float result = (float) (Math.sqrt((dx*dx)+(dy*dy)));
		
		/** Octile */
//		double F = Math.sqrt(2) - 1;
//		float result = (float) (dx < dy ? F * dx + dy : F * dy + dx);
		
		/** Chebyshev */
		float result = (float) Math.max(dx, dy);
		
		return result;
	}

}