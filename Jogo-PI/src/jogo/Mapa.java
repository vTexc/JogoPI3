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
	private static final int TORRE_T = 4;
	private static final int TORRE_V = 5;
	private static final int TORRE_S = 6;
	
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
	
	private void setMapa(int x, int y, int k) {
		mapa[y][x] = k;
	}
	
	private boolean placeTorre(int x, int y) {
		return (mapa[y][x] == TERRENO || mapa[y][x] == VOADOR);
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
	
	public void update(ArrayList<Monstro> monstros) {
		for(Monstro m: monstros) {
			int mX = (int) (m.getPosicaoX()/50);
			int mY = (int) (m.getPosicaoY()/50);
			
			mapa[mY][mX] = m.getTipo(); 
		}
	}
	
	public synchronized void placeTorre(int tipo, int x, int y, PathFinder finder, ArrayList<Torre> torres, ArrayList<Monstro> monstros) {
		Torre torre = null;
		
		if(tipo == TORRE_T)
			torre = new TorreTerrestre(x, y, 1, 1);
		else if(tipo == TORRE_V)
			torre = new TorreVoador(x, y, 1, 1);
		else if(tipo == TORRE_S)
			torre = new TorreSuporte(x, y);

		if(placeTorre(x / 50, y / 50)) {
			this.setMapa(x / 50, y / 50, torre.getTipo());
			if (finder.findPath(2, 0, 6, 18, 6) != null) {
				torres.add(torre);
				HUD.getInstancia().subRecursos(torres.get(torres.size() - 1).getCusto());
				if (HUD.getInstancia().getRecursos() < 0) {
					HUD.getInstancia().addRecursos(torres.get(torres.size() - 1).getCusto());
					torres.remove(torres.size() - 1);
					this.setMapa(x / 50, y / 50, 0);
				} else {
					for (Monstro m : monstros) {
						m.atualizarCaminho(finder);
						if (!m.hasCaminho()) {
							HUD.getInstancia().addRecursos(torres.get(torres.size() - 1).getCusto());
							setMapa(x / 50, y / 50, 0);
							torres.remove(torres.size() - 1);
							m.atualizarCaminho(finder);
						}
					}
				}
			} else {
				setMapa(x / 50, y / 50, 0);
			}
		}
	}
	
	public synchronized void deleteTorre(int x, int y, PathFinder finder, ArrayList<Torre> torres, ArrayList<Monstro> monstros, Torre t) {
		setMapa(x / 50, y / 50, 0);
		HUD.getInstancia().addRecursos(torres.get(torres.indexOf(t)).getVendaCusto());
		torres.remove(t);
		for (Monstro m : monstros) {
			m.atualizarCaminho(finder);
		}
	}
}
