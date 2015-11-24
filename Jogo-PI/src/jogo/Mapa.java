package jogo;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

import javax.imageio.ImageIO;

import funcional.TileBasedMap;
import AEstrela.PathFinder;

public class Mapa implements TileBasedMap {
	// Singleton
	private static Mapa instance;

	// Total de grids
	public static final int WIDTH = 19;
	public static final int HEIGHT = 13;

	// Mapa
	private int[][] mapaSemAlteracao;
	private int[][][] mapa;
	private int maxMaps;
	private int currentMap;

	// Imagens
	private BufferedImage[] tileSet;

	// Valores determinantes da imagem
	public static final int ENTRADA_SAIDA = -1;
	public static final int TERRENO = 0;
	public static final int MURO = 1;
	public static final int DESTRUIDOR = 2;
	public static final int TERRESTRE = 3;
	public static final int VOADOR = 4;
	public static final int TORRE_T = 5;
	public static final int TORRE_V = 6;
	public static final int TORRE_S = 7;

	// Ponto de entrada e saída
	private static int[] entradaX, entradaY;
	private static int[] saidaX, saidaY;

	// Tiles visitados
	// Usado para A*
	private boolean[][] visitado;

	// Construtor
	private Mapa() {
		try {
			Scanner arquivo = new Scanner(getClass().getResourceAsStream("/map.txt"));
			maxMaps = arquivo.nextInt();
			mapa = new int[maxMaps][HEIGHT][WIDTH];
			entradaX = new int[maxMaps];
			entradaY = new int[maxMaps];
			saidaX = new int[maxMaps];
			saidaY = new int[maxMaps];
			visitado = new boolean[WIDTH][HEIGHT];
			tileSet = new BufferedImage[1];
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/TileSet.png"));

			tileSet[0] = spritesheet.getSubimage(0, 0, 50, 50);

			for (int z = 0; z < maxMaps; z++) {
				for (int x = 0; x < HEIGHT; x++) {
					for (int y = 0; y < WIDTH; y++) {
						mapa[z][x][y] = arquivo.nextInt();
						if (mapa[z][x][y] == -1) {
							if (y == 0) {
								entradaX[z] = x;
								entradaY[z] = y;
							} else {
								saidaX[z] = x;
								saidaY[z] = y;
							}
						}
					}
				}
			}

			currentMap();
			arquivo.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Retorna instancia do mapa
	public static synchronized Mapa getIntance() {
		if (instance == null) {
			instance = new Mapa();
		}
		return instance;
	}

	// Escolhe mapa
	public void currentMap() {
		Random rand = new Random();
		currentMap = rand.nextInt(maxMaps);
		mapaSemAlteracao = mapa[currentMap];
	}

	// Reseta mapa atual
	public void reset() {
		mapa[currentMap] = mapaSemAlteracao;
	}

	// Retorna valor de entrada
	public int getEntradaX() {
		return entradaX[currentMap];
	}

	public int getEntradaY() {
		return entradaY[currentMap];
	}

	// Retorna valor de saida
	public int getSaidaX() {
		return saidaX[currentMap];
	}

	public int getSaidaY() {
		return saidaY[currentMap];
	}

	// Altera determinada posição do mapa
	public void setMapa(int x, int y, int k) {
		mapa[currentMap][y][x] = k;
	}

	// Verifica se pode por torre ou não
	private boolean placeTorre(int x, int y) {
		return (mapa[currentMap][y][x] == Mapa.TERRENO);
	}

	// Coloca torre em determinado local do mapa
	public synchronized void placeTorre(int tipo, int x, int y, PathFinder finder, ArrayList<Torre> torres,
			ArrayList<Monstro> monstros) {
		Torre torre = null;
		// Pega a torre que esta sendo criada
		if (tipo == Mapa.TORRE_T)
			torre = new TorreTerrestre(x, y, 1, 1);
		else if (tipo == Mapa.TORRE_V)
			torre = new TorreVoador(x, y, 1, 1);
		else if (tipo == Mapa.TORRE_S)
			torre = new TorreSuporte(x, y);

		// Verifica se pode por torre na posição atual
		if (placeTorre(x / 50, y / 50)) {
			this.setMapa(x / 50, y / 50, torre.getTipo());
			// Procura caminho
			// Adiciona se achar caminho
			if (finder.findPath(TERRESTRE, Mapa.getIntance().getEntradaY(), Mapa.getIntance().getEntradaX(),
					Mapa.getIntance().getSaidaY(), Mapa.getIntance().getSaidaX()) != null) {
				// Verifica se tem recursos suficientes
				if (HUD.getInstancia().getRecursos() - torre.getCusto() >= 0) {
					// Adiciona torre
					torres.add(torre);
					// Verifica se algum monstro via perder o caminho
					for (Monstro m : monstros) {
						m.atualizarCaminho(finder);
						// Verifica se a torre esta sobre um monstro
						if ((m.getTipo() != Mapa.VOADOR) && (!m.hasCaminho() || torre.intersects(m.getBounds()))) {
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

	// Deleta torre de determinada posição
	public synchronized void deleteTorre(int x, int y, PathFinder finder, ArrayList<Torre> torres,
			ArrayList<Monstro> monstros, Torre t) {
		setMapa(x / 50, y / 50, 0);
		HUD.getInstancia().addRecursos(torres.get(torres.indexOf(t)).getVendaCusto());
		torres.remove(t);
		// Atualiza caminho dos monstros
		for (Monstro m : monstros) {
			m.atualizarCaminho(finder);
		}
		// Recupera recurso
	}

	// Desenha mapa na tela
	public void draw(Graphics2D g) {
		for (int x = 0; x < HEIGHT; x++) {
			for (int y = 0; y < WIDTH; y++) {
				g.drawImage(tileSet[0], y * 50, x * 50, 50, 50, null);
				if (mapa[currentMap][x][y] == Mapa.MURO) {
					g.setColor(Color.gray);
					g.fillRect(y * 50, x * 50, 50, 50);
				} else if (mapa[currentMap][x][y] == Mapa.ENTRADA_SAIDA) {
					g.setColor(new Color(255, 111, 155));
					g.fillRect(y * 50, x * 50, 50, 50);
				} else if (mapa[currentMap][x][y] == Mapa.ENTRADA_SAIDA) {
					g.setColor(new Color(255, 111, 155));
					g.fillRect(y * 50, x * 50, 50, 50);
				}
			}
		}
	}

	/** Overrides / Metodos usados pela A* */
	// Limpa matriz de visitados
	public void clearVisited() {
		for (int x = 0; x < getWidthInTiles(); x++) {
			for (int y = 0; y < getHeightInTiles(); y++) {
				visitado[x][y] = false;
			}
		}
	}

	// Verifica se ja foi visitado
	public boolean visited(int x, int y) {
		return visitado[x][y];
	}

	// Retorna tipo do terreno na determinada posição
	public int getTerrain(int x, int y) {
		return mapa[currentMap][x][y];
	}

	// Retorna se o caminho esta bloqueado ou não
	// para dado tipo
	public boolean blocked(int mover, int x, int y) {
		if (mover == Mapa.VOADOR) {
			return (mapa[currentMap][y][x] == Mapa.MURO);
		}

		if (mover == Mapa.TERRESTRE || mover == Mapa.DESTRUIDOR) {
			return (mapa[currentMap][y][x] != Mapa.TERRENO && mapa[currentMap][y][x] != Mapa.ENTRADA_SAIDA);
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

	// Verifica se ja foi visitado
	public void pathFinderVisited(int x, int y) {
		visitado[x][y] = true;
	}
}
