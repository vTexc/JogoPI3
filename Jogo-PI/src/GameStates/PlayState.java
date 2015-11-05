/**
 * Tela do jogo
 * 
 */
package GameStates;

import funcional.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.plaf.basic.BasicTabbedPaneUI.MouseHandler;
import javax.swing.text.StyledEditorKit.FontSizeAction;

import AEstrela.*;
import jogo.*;

public class PlayState extends GameState {
	// Informaçoes do mapa
	private Mapa mapa;

	// Informações do jogo
	private boolean hardcore;
	private boolean pausado;

	// Gerenciador de estados do jogo
	private GameStateManager gsm;

	// Objetos do jogo
	private ArrayList<Torre> torres;
	private ArrayList<Monstro> monstros;

	// Lista de compra das torres
	private ArrayList<Torre> listaTorres;

	// Informação de compra
	private int selecionado;
	private int selecionadoX;
	private int selecionadoY;
	private Torre aux;

	// Informaçao de wave, recursos, etc
	private HUD hud;
	private Wave wave;

	// Posição do mouse na tela
	private int mouseX;
	private int mouseY;

	// Caminho a ser percorrido
	private PathFinder finder;

	// Botões
	private Rectangle pauseBotao;
	// private Rectangle speedBotao;
	private Animation speedBotao;
	private int speedAtual;

	// Velocidade do jogo
	public static int gameSpeed;

	// Musica de fundo
	private Audio bgMusic;

	// Construtor
	public PlayState(GameStateManager gsm, boolean hc) {
		this.gsm = gsm;
		this.hardcore = hc;
		this.gameSpeed = 1;
		speedBotao = new Animation();
		speedBotao.setFrames(loadImage("Sprites/Buttons/Speed.png", 3));
		speedBotao.setDelay(-1);
		speedBotao.setBounds(0, Renderer.HEIGHT - 50, 50, 50);
		this.pauseBotao = new Rectangle(50, Renderer.HEIGHT - 50, 50, 50);
		init();
	}

	// Carrega determinada imagem
	private void loadImage(String image, int frames) {
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(image));
			sprites = new BufferedImage[frames];
			
			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * 50, 0, 50, 50);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Inicializador
	public void init() {
		mapa = new Mapa(); // Cria mapa
		torres = new ArrayList<Torre>(); // Cria a lista para torres
		monstros = new ArrayList<Monstro>(); // Cria lista para monstros

		wave = new Wave(hardcore);
		hud = HUD.getInstancia(gsm); // Cria a hud

		// bgMusic = new Audio("/Audio/JogoBG.mp3");
		// bgMusic.play();

		// Define a base para a estrela
		// AStarPathFinder(Mapa , Total de Tiles, Mover Diagonal)
		finder = new AStarPathFinder(mapa, 13 * 19, false);

		this.selecionado = 0;

		// Inicia lista de compra
		int MAX_TORRES = 3; // Maximo de torres
		listaTorres = new ArrayList<Torre>();
		listaTorres.add(new TorreTerrestre(
				Renderer.WIDTH / 2 - (100 * MAX_TORRES) + (listaTorres.size() + 1) * (MAX_TORRES * 50),
				Renderer.HEIGHT - 50));
		listaTorres.add(
				new TorreVoador(Renderer.WIDTH / 2 - (100 * MAX_TORRES) + (listaTorres.size() + 1) * (MAX_TORRES * 50),
						Renderer.HEIGHT - 50));
		listaTorres.add(
				new TorreSuporte(Renderer.WIDTH / 2 - (100 * MAX_TORRES) + (listaTorres.size() + 1) * (MAX_TORRES * 50),
						Renderer.HEIGHT - 50));
	}

	// Atualiza imagens
	public void animationUpdate() {
		speedBotao.update();
	}
	
	// Atualiza informações dos monstros
	private void monstroUpdate() {
		for (Monstro m : monstros) {
			m.update(finder);
			if (m.isScreenOut() || m.isDead()) {
				monstros.remove(m);
			}
		}
	}

	// Atualiza informações das torres
	private void torreUpdate() {
		for (Torre t : torres) {
			t.update(torres, monstros);
			if (t.getBounds().contains(mouseX, mouseY)) {
				t.setMouseOver(true);
			} else {
				t.setMouseOver(false);
			}
		}
	}

	// Atualiza informaçoes
	public void update() {
		if (!pausado) {
			wave.update(monstros, finder, hud);
			hud.update();
			torreUpdate();
			monstroUpdate();
		}
	}

	// Desenha na tela
	public void draw(Graphics2D g) {
		// Background (Temporario)
		g.setColor(new Color(255, 211, 155));
		g.fillRect(0, 0, 950, 650);

		if (selecionado != 0) {
			g.setColor(aux.getColor());
			g.draw(aux.getImagem());
			g.fill(aux.getImagem());
		}

		// Mapa
		mapa.draw(g);

		// Desenha HuD
		hud.draw(g);

		// Desenha torres
		for (Torre t : torres) {
			t.draw(g);
		}
		// Desenha Monstros
		for (Monstro m : monstros) {
			m.draw(g);
		}
		// Desenha botões
		for (Torre t : listaTorres) {
			g.setColor(t.getColor());
			g.draw(t.getImagem());
			g.fill(t.getImagem());
			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.PLAIN, 30));
			g.drawString(String.valueOf(t.getCusto()), t.getX() + 7, t.getY() + 37);
		}

		switch (gameSpeed) {
		case 1:
			g.setColor(Color.GREEN);
			break;
		case 3:
			g.setColor(Color.YELLOW);
			break;
		case 5:
			g.setColor(Color.red);
			break;
		}
		g.drawImage(speedBotao.getImage(), 0, Renderer.HEIGHT - 50, null);

		g.setColor(Color.BLACK);
		g.fillRect(pauseBotao.x, pauseBotao.y, pauseBotao.width, pauseBotao.height);

		if (pausado) {
			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.PLAIN, 200));
			g.drawString("Pausado",
					(int) (Renderer.WIDTH / 2 - g.getFontMetrics().getStringBounds("Pausado", g).getWidth() / 2),
					Renderer.HEIGHT / 3);
		}
	}

	/** Listeners Overrides */
	public void mouseDragged(MouseEvent e) {
		if (selecionado != 0) {
			selecionadoX = e.getX() / 50 * 50;
			selecionadoY = e.getY() / 50 * 50;
			aux.getImagem().x = selecionadoX;
			aux.getImagem().y = selecionadoY;
		}
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();

	}

	public void mouseClicked(MouseEvent e) {
		int x = e.getX() / 50 * 50;
		int y = e.getY() / 50 * 50;

		if (!pausado) {
			ArrayList<Torre> aux = (ArrayList<Torre>) torres.clone();

			for (Torre t : aux) {
				if (t.getBounds().contains(e.getPoint())) {
					if (e.getButton() == MouseEvent.BUTTON2) {
						t.upgrade();
					}
					if (e.getButton() == MouseEvent.BUTTON3) {
						mapa.deleteTorre(x, y, finder, torres, monstros, t);
					}
				}
			}

			if (pauseBotao.getBounds().contains(e.getPoint())) {
				pausado = true;
			}
		} else {
			if (pauseBotao.getBounds().contains(e.getPoint())) {
				pausado = false;
			}
		}

		if (speedBotao.contains(e.getPoint())) {
			gameSpeed += 2;
			speedBotao.setFrame(speedBotao.getFrame() + 1);
			if (gameSpeed > 5) {
				gameSpeed = 1;
				speedBotao.setFrame(0);
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		int x = e.getX() / 50 * 50;
		int y = e.getY() / 50 * 50;

		if (!pausado) {
			for (Torre t : listaTorres) {
				if (t.getBounds().contains(e.getPoint())) {
					selecionadoX = e.getX();
					selecionadoY = e.getY();

					selecionado = t.getTipo();
					switch (t.getTipo()) {
					case 4:
						aux = new TorreTerrestre();
						break;
					case 5:
						aux = new TorreVoador();
						break;
					case 6:
						aux = new TorreSuporte();
						break;
					}
				}
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		int x = e.getX() / 50 * 50;
		int y = e.getY() / 50 * 50;

		if (selecionado != 0) {
			mapa.placeTorre(selecionado, x, y, finder, torres, monstros);
			selecionado = 0;
			aux = null;
		}
	}

	public void mouseEntered(MouseEvent e) {
		this.pausado = false;
	}

	public void mouseExited(MouseEvent e) {
		this.pausado = true;
	}
}
