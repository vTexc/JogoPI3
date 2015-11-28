package jogo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

import javax.imageio.ImageIO;

import funcional.Renderer;
import funcional.TileBasedMap;
import AEstrela.PathFinder;

public class Mapa implements TileBasedMap {
	// Singleton
	private static Mapa instance;

	// Total de grids
	public static final int WIDTH = 19;
	public static final int HEIGHT = 13;

	// Mapa
	private int[][][] mapa;
	private int maxMaps;
	private int currentMap;

	// Imagens
	private BufferedImage[] tileSet;

	// Valores determinantes da imagem
	public static final int ENTRADA = -2;
	public static final int SAIDA = -1;
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
			Scanner arquivo = new Scanner(getClass().getResourceAsStream("/map.map"));
			maxMaps = arquivo.nextInt();
			mapa = new int[maxMaps][WIDTH][HEIGHT];
			entradaX = new int[maxMaps];
			entradaY = new int[maxMaps];
			saidaX = new int[maxMaps];
			saidaY = new int[maxMaps];
			visitado = new boolean[WIDTH][HEIGHT];
			tileSet = new BufferedImage[8];
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/TileSet.png"));

			for (int y = 0; y < 8; y++) {
				tileSet[y] = spritesheet.getSubimage((y % 4) * 50, (y / 4) * 50, 50, 50);
			}

			for (int z = 0; z < maxMaps; z++) {
				for (int y = 0; y < HEIGHT; y++) {
					for (int x = 0; x < WIDTH; x++) {
						mapa[z][x][y] = arquivo.nextInt();
						if (mapa[z][x][y] == -2) {
							entradaX[z] = x;
							entradaY[z] = y;
						}
						if (mapa[z][x][y] == -1) {
							saidaX[z] = x;
							saidaY[z] = y;
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
	}

	// Reseta mapa atual
	public void reset() {
		instance = null;
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
		mapa[currentMap][x][y] = k;
	}

	// // Verifica se pode por torre em determinada posição
	public synchronized int placingTorre(int x, int y, Torre torre, PathFinder finder, ArrayList<Monstro> monstros) {
		if (HUD.getInstancia().getRecursos() - torre.getCusto() >= 0) {
			if (x >= 0 && x < Renderer.WIDTH && y >= 0 && y < Renderer.HEIGHT) {
				if (getTerrain(x / 50, y / 50) == Mapa.TERRENO) {
					this.setMapa(x / 50, y / 50, torre.getTipo());
					if (finder.findPath(TERRESTRE, Mapa.getIntance().getEntradaX(), Mapa.getIntance().getEntradaY(),
							Mapa.getIntance().getSaidaX(), Mapa.getIntance().getSaidaY()) == null) {
						this.setMapa(x / 50, y / 50, Mapa.TERRENO);
						return 0;
					} else {
						for (Monstro m : monstros) {
							if ((m.getTipo() != Mapa.VOADOR) && (!m.hasCaminho() || torre.intersects(m.getBounds()))) {
								this.setMapa(x / 50, y / 50, Mapa.TERRENO);
								return 0;
							}
						}
					}
					this.setMapa(x / 50, y / 50, Mapa.TERRENO);
				} else {
					return -1;
				}
			} else {
				return -1;
			}
		} else {
			return -1;
		}
		return 1;
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

		// Adiciona torre ao mapa e à lista
		this.setMapa(x / 50, y / 50, torre.getTipo());
		torres.add(torre);
		// Verifica se algum monstro via perder o caminho
		for (Monstro m : monstros) {
			m.atualizarCaminho(finder);
		}
		// Diminui recurso
		HUD.getInstancia().subRecursos(torre.getCusto());
	}

	// Deleta torre de determinada posição
	public synchronized void deleteTorre(int x, int y, PathFinder finder, ArrayList<Torre> torres,
			ArrayList<Monstro> monstros, Torre t) {
		// Seta o campo para terreno
		setMapa(x / 50, y / 50, Mapa.TERRENO);
		// Recupera recurso
		HUD.getInstancia().addRecursos(torres.get(torres.indexOf(t)).getVendaCusto());
		torres.remove(t);
		// Atualiza caminho dos monstros
		for (Monstro m : monstros) {
			m.atualizarCaminho(finder);
		}
	}

	// Desenha mapa na tela
	public void draw(Graphics2D g) {
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				g.drawImage(tileSet[3], x * 50, y * 50, 50, 50, null);
				if (mapa[currentMap][x][y] == Mapa.MURO) {
					g.drawImage(tileSet[0], x * 50, y * 50, 50, 50, null);
					if (x != WIDTH - 1 ? mapa[currentMap][x + 1][y] == Mapa.MURO : false) {
						g.drawImage(tileSet[7], x * 50, y * 50, 50, 50, null);
					}
					if (y != HEIGHT - 1 ? mapa[currentMap][x][y + 1] == Mapa.MURO : false) {
						g.drawImage(tileSet[6], x * 50, y * 50, 50, 50, null);
					}
					if (x != 0 ? mapa[currentMap][x - 1][y] == Mapa.MURO : false) {
						g.drawImage(tileSet[5], x * 50, y * 50, 50, 50, null);
					}
					if (y != 0 ? mapa[currentMap][x][y - 1] == Mapa.MURO : false) {
						g.drawImage(tileSet[4], x * 50, y * 50, 50, 50, null);
					}
				} else {
					if (mapa[currentMap][x][y] == Mapa.SAIDA) {
						g.drawImage(tileSet[2], x * 50, y * 50, 50, 50, null);
					} else if (mapa[currentMap][x][y] == Mapa.ENTRADA) {		
						g.drawImage(tileSet[1], x * 50, y * 50, 50, 50, null);				
					}
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
			return (mapa[currentMap][x][y] == Mapa.MURO);
		}

		if (mover == Mapa.TERRESTRE || mover == Mapa.DESTRUIDOR) {
			return (mapa[currentMap][x][y] != Mapa.TERRENO && mapa[currentMap][x][y] != Mapa.SAIDA && mapa[currentMap][x][y] != Mapa.ENTRADA);
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
