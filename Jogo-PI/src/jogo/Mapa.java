package jogo;

import java.awt.*;
import java.awt.image.*;
import java.util.*;

import javax.imageio.ImageIO;

import funcional.TileBasedMap;
import AEstrela.PathFinder;

public class Mapa implements TileBasedMap {
	// Total de grids
	private static final int WIDTH = 19;
	private static final int HEIGHT = 13;

	// Valores determinantes do espaço da matriz
	public static final int TERRENO = 0;
	public static final int MURO = 1;
	public static final int OUTROS = 2;
	public static final int VOADOR = 3;
	public static final int TORRE_T = 4;
	public static final int TORRE_V = 5;
	public static final int TORRE_S = 6;
	// Mapa
	private int[][] mapa;
	// Tiles visitados
	// Usado para A*
	private boolean[][] visited;

	// Construtor
	public Mapa() {
		mapa = new int[HEIGHT][WIDTH];
		visited = new boolean[WIDTH][HEIGHT];

		for (int x = 0; x < HEIGHT; x++)
			for (int y = 0; y < WIDTH; y++)
				if ((x == 0 || y == 0 || x == 12 || y == 18) && x != 6)
					mapa[x][y] = MURO;
	}

	// Altera determinada posição do mapa
	private void setMapa(int x, int y, int k) {
		mapa[y][x] = k;
	}

	// Verifica se pode por torre ou não
	private boolean placeTorre(int x, int y) {
		return (mapa[y][x] == TERRENO);
	}

	// Coloca torre em determinado local do mapa
	public synchronized void placeTorre(int tipo, int x, int y, PathFinder finder, ArrayList<Torre> torres,
			ArrayList<Monstro> monstros) {
		Torre torre = null;
		// Pega a torre que esta sendo criada
		if (tipo == TORRE_T)
			torre = new TorreTerrestre(x, y, 1, 1);
		else if (tipo == TORRE_V)
			torre = new TorreVoador(x, y, 1, 1);
		else if (tipo == TORRE_S)
			torre = new TorreSuporte(x, y);

		// Verifica se pode por torre na posição atual
		if (placeTorre(x / 50, y / 50)) {
			this.setMapa(x / 50, y / 50, torre.getTipo());
			// Procura caminho
			// Adiciona se achar caminho
			if (finder.findPath(2, 0, 6, 18, 6) != null) {
				// Verifica se tem recursos suficientes
				if (HUD.getInstancia().getRecursos() - torre.getCusto() >= 0) {
					// Adiciona torre
					torres.add(torre);
					// Verifica se algum monstro via perder o caminho
					for (Monstro m : monstros) {
						m.atualizarCaminho(finder);
						// Verifica se a torre esta sobre um monstro
						if (!m.hasCaminho() || torre.getBounds().contains(m.getBounds())) {
							setMapa(x / 50, y / 50, 0);
							torres.remove(torres.size() - 1);
							m.atualizarCaminho(finder);
							return;
						}
					}
					// Diminui recurso
					HUD.getInstancia().subRecursos(torre.getCusto());
				}
			} else {
				setMapa(x / 50, y / 50, 0);
			}
		}
	}

	// Deleta torre da posição dada
	public synchronized void deleteTorre(int x, int y, PathFinder finder, ArrayList<Torre> torres,
			ArrayList<Monstro> monstros, Torre t) {
		setMapa(x / 50, y / 50, 0);
		torres.remove(t);
		// Atualiza caminho dos monstros
		for (Monstro m : monstros) {
			m.atualizarCaminho(finder);
		}
		// Recupera recurso
		HUD.getInstancia().addRecursos(torres.get(torres.indexOf(t)).getVendaCusto());
	}

	// Desenha mapa na tela
	public void draw(Graphics2D g) {
		for (int x = 0; x < HEIGHT; x++)
			for (int y = 0; y < WIDTH; y++)
				if (mapa[x][y] == MURO) {
					g.setColor(Color.gray);
					g.fillRect(y * 50, x * 50, 50, 50);
				}
	}

	/** Overrides / Metodos usados pela A* */
	// Limpa matriz de visitados
	public void clearVisited() {
		for (int x = 0; x < getWidthInTiles(); x++) {
			for (int y = 0; y < getHeightInTiles(); y++) {
				visited[x][y] = false;
			}
		}
	}

	// Verifica se ja foi visitado
	public boolean visited(int x, int y) {
		return visited[x][y];
	}

	// Retorna tipo do terreno na determinada posição
	public int getTerrain(int x, int y) {
		return mapa[x][y];
	}

	// Retorna se o caminho esta bloqueado ou não
	// para dado tipo
	public boolean blocked(int mover, int x, int y) {
		if (mover == VOADOR) {
			return mapa[y][x] == MURO;
		}

		if (mover == OUTROS) {
			return mapa[y][x] != TERRENO;
		}

		return true;
	}

	// Retorna custo
	public float getCost(int mover, int sx, int sy, int tx, int ty) {
		return 1;
	}

	// Reotorna total de tiles de altura
	public int getHeightInTiles() {
		return HEIGHT;
	}

	// Reotorna total de tiles de largura
	public int getWidthInTiles() {
		return WIDTH;
	}

	// Serifica se ja foi visitado
	public void pathFinderVisited(int x, int y) {
		visited[x][y] = true;
	}
}
