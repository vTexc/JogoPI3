package jogo;

import java.awt.*;
import java.awt.image.*;
import java.util.*;

import javax.imageio.ImageIO;

import funcional.TileBasedMap;
import AEstrela.PathFinder;

public class Mapa implements TileBasedMap {
	private static final int WIDTH = 19;
	private static final int HEIGHT = 13;
	
	/**Determina o que existe naqele espaço da matriz**/
	private static final int TERRENO = 0;
	private static final int MURO = 1;
	private static final int OUTROS = 2;
	private static final int VOADOR = 3;
	private static final int TORRE = 4;
	
	private int[][] mapa;
	
	private boolean[][] visited;
	
	public Mapa() {
		mapa = new int[HEIGHT][WIDTH];
		visited = new boolean[WIDTH][HEIGHT];
		
		for(int x = 0; x < HEIGHT; x++)
			for(int y = 0; y < WIDTH; y++)
				if((x == 0 || y == 0 || x == 12 || y == 18) && x != 6)
				mapa[x][y] = MURO;
	}
	
	public void loadImage() {
	}
	
	public void setMapa(int x, int y, int k) {
		mapa[y][x] = k;
	}
	
	public boolean placeTorre(int x, int y) {
		return (mapa[y][x] == TORRE || mapa[y][x] == MURO || mapa[y][x] == OUTROS || mapa[y][x] == VOADOR);
	}
	
	public void reset() {
	}
	
	public void draw(Graphics2D g) {
		for(int x = 0; x < HEIGHT; x++)
			for(int y = 0; y < WIDTH; y++)
				if(mapa[x][y] == MURO) {
					g.setColor(Color.gray);
					g.fillRect(y*50, x*50, 50, 50);
				}
	}
	
	public void clearVisited() {
		for (int x = 0; x < getWidthInTiles(); x++) {
			for (int y = 0; y < getHeightInTiles(); y++) {
				visited[x][y] = false;
			}
		}
	}
	
	/**
	 * @see TileBasedMap#visited(int, int)
	 */
	public boolean visited(int x, int y) {
		return visited[x][y];
	}
	
	/**
	 * Get the terrain at a given location
	 * 
	 * @param x The x coordinate of the terrain tile to retrieve
	 * @param y The y coordinate of the terrain tile to retrieve
	 * @return The terrain tile at the given location
	 */
	public int getTerrain(int x, int y) {
		return mapa[x][y];
	}
	
	/**
	 * @see TileBasedMap#blocked(Mover, int, int)
	 */
	public boolean blocked(int mover, int x, int y) {
		if(mover == VOADOR) {
			return mapa[y][x] == MURO;
		}
		
		if(mover == OUTROS) {
			return mapa[y][x] != TERRENO;
		}
		
		return true;
	}

	/**
	 * @see TileBasedMap#getCost(Mover, int, int, int, int)
	 */
	public float getCost(int mover, int sx, int sy, int tx, int ty) {
		return 1;
	}

	/**
	 * @see TileBasedMap#getHeightInTiles()
	 */
	public int getHeightInTiles() {
		return HEIGHT;
	}

	/**
	 * @see TileBasedMap#getWidthInTiles()
	 */
	public int getWidthInTiles() {
		return WIDTH;
	}

	/**
	 * @see TileBasedMap#pathFinderVisited(int, int)
	 */
	public void pathFinderVisited(int x, int y) {
		visited[x][y] = true;
	}
}
