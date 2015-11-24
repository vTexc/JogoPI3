package jogo;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import GameStates.*;
import funcional.Imagem;
import funcional.Renderer;

public class HUD {
	// Instancia do hud (Singleton)
	private static HUD instancia;

	// Informações de recurso, vida e wave
	private int recursos;
	private int wave;
	private int vidas;

	// Imagens da hud
	private BufferedImage[] sprites;

	// Strings de impressão na tela
	private String sRecursos;
	private String sWave;
	private String sVidas;
	private String sTempoEspera;

	// Estados
	private boolean griding;
	// private boolean gameOver;

	// Font de impressão
	private Font font;

	// Gerenciador de estados do jogo
	private GameStateManager gsm;

	// Construtor do singleton normal
	private HUD() {
		wave = 0;
		recursos = 30;
		vidas = 20;
		font = new Font("Arial", Font.PLAIN, 25);

		griding = false;
		loadImage();
	}

	// Construtor do singleton com GameStateManager
	private HUD(GameStateManager gsm) {
		this();
		this.gsm = gsm;
	}

	// Retorna instancia do hud normal
	public static HUD getInstancia() {
		if (instancia == null) {
			initInstancia();
		}
		return instancia;
	}

	// Retorna instancia do hud com GameStateManager
	public static HUD getInstancia(GameStateManager gsm) {
		if (instancia == null) {
			initInstancia(gsm);
		}
		return instancia;
	}

	// Carregar imagem
	private void loadImage() {
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/HUD.png"));

			sprites = new BufferedImage[2];
			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * 50, 0, 50, 50);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Inicializa hud normal
	private static synchronized void initInstancia() {
		if (instancia == null) {
			instancia = new HUD();
		}
	}

	// Inizializa hud com GameStateManager
	private static synchronized void initInstancia(GameStateManager gsm) {
		if (instancia == null) {
			instancia = new HUD(gsm);
		}
	}

	// Retorna vidas
	public int getVidas() {
		return vidas;
	}
	
	// Reseta informações do hud
	public void reset() {
		wave = 0;
		recursos = 30;
		vidas = 20;
		griding = false;
		Mapa.getIntance().reset();
	}

	// Retorna recursos
	public int getRecursos() {
		return recursos;
	}

	// Adiciona x recursos
	public void addRecursos(int x) {
		this.recursos += x;
	}

	// Subtrai x recursos
	public void subRecursos(int x) {
		this.recursos -= x;
	}

	// Retorna wave atual
	public int getWave() {
		return wave;
	}

	// Adiciona 1 wave
	public void addWave() {
		wave++;
	}

	// Subtrai 1 vida
	public void subVidas() {
		this.vidas--;
	}

	// Atualiza informações da hud
	public void update() {
		sRecursos = String.format("%06d", recursos);
		sWave = "Wave : " + String.format("%03d", wave);
		sVidas = String.format("%02d", vidas);
		sTempoEspera = String.format("%.01f", Wave.getTempoAtual());
		// Caso GameOver
		if (vidas <= 0) {
			reset();
			gsm.setState(GameStateManager.MENU);
		}
	}

	// Desenha hud na tela
	public void draw(Graphics2D g) {
		if (griding) {
			g.setColor(Color.WHITE);
			for (int x = 50; x < 901; x += 50) {
				for (int y = 50; y < 601; y += 50) {
					g.drawLine(x, 50, x, 600);
					g.drawLine(50, y, 900, y);
				}
			}
		}
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawImage(sprites[0], 0, 0, 50, 50, null);
		g.drawString(sRecursos, 50, 30);
		g.drawString(sWave, 950 / 2 - font.getSize() * 2, font.getSize());
		g.drawString(sVidas, Renderer.WIDTH - 87, 30);
		g.drawImage(sprites[1], Renderer.WIDTH - 50, 0, 50, 50, null);
		if (!Wave.getHardcore() && !Wave.getSpawnar()) {
			g.setFont(new Font("Arial", Font.PLAIN, 15));
			g.drawString(sTempoEspera, Renderer.WIDTH / 2, 50);
		}
	}
}
