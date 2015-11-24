/**
 * Tela do jogo
 * 
 */
package GameStates;

import funcional.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

import javax.imageio.*;

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

	// Informação de compra
	private int selecionado;
	private int selecionadoX;
	private int selecionadoY;
	private Torre aux;
	private boolean placingTorre;

	// Informaçao de wave, recursos, etc
	private HUD hud;
	private Wave wave;

	// Posição do mouse na tela
	private int mouseX;
	private int mouseY;

	// Caminho a ser percorrido
	private PathFinder finder;

	/* Imagens */
	// Botões
	private Imagem pauseBotao;
	private Imagem speedBotao;
	private Imagem goBotao;
	private Imagem homeBotao;
	private Imagem pauseBg;
	
	// Lista de compra das torres
	private ArrayList<Imagem> listaTorres;

	// Velocidade do jogo
	public static int gameSpeed;

	// Musica de fundo
//	private Audio bgMusic;

	// Construtor
	public PlayState(GameStateManager gsm, boolean hc) {
		this.gsm = gsm;
		this.hardcore = hc;
		PlayState.gameSpeed = 1;
		this.speedBotao = new Imagem(0, Renderer.HEIGHT - 50, 50);
		loadImage("/Sprites/Buttons/Speed.png", 3, speedBotao, 50, 50);
		this.pauseBotao = new Imagem(50, Renderer.HEIGHT - 50, 50);
		loadImage("/Sprites/Buttons/Pause.png", 2, pauseBotao, 50, 50);
		this.goBotao = new Imagem(0, Renderer.HEIGHT - 100, 50);
		loadImage("/Sprites/Buttons/Go.png", 1, goBotao, 50, 50);
		this.homeBotao = new Imagem(Renderer.WIDTH - 50, Renderer.HEIGHT - 50, 50);
		loadImage("/Sprites/Buttons/Home.png", 1, homeBotao, 50, 50);
		this.pauseBg = new Imagem(0, 0, Renderer.WIDTH, Renderer.HEIGHT);
		loadImage("/Sprites/BgPause.png", 1, pauseBg, Renderer.WIDTH, Renderer.HEIGHT);
		init();
	}
	
	// Carrega determinada imagem com frames
	private void loadImage(String path, int frames, Imagem imagem, int w, int h) {
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(path));
			BufferedImage[] sprites = new BufferedImage[frames];

			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * w, 0, w, h);
			}

			imagem.setFrames(sprites);
			imagem.setDelay(-1);

			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	// Inicializador
	public void init() {
		mapa = Mapa.getIntance(); // Cria mapa
		torres = new ArrayList<Torre>(); // Cria a lista para torres
		monstros = new ArrayList<Monstro>(); // Cria lista para monstros
		
		wave = new Wave(hardcore);
		hud = HUD.getInstancia(gsm); // Cria a hud

		// bgMusic = new Audio("/Audio/JogoBG.mp3");
		// bgMusic.play();

		// Define a base para a estrela
		// AStarPathFinder(Mapa , Total de Tiles, Mover Diagonal)
		finder = new PathFinder(mapa, mapa.HEIGHT * mapa.WIDTH, false);

		this.selecionado = -1;

		// Inicia lista de compra
		int MAX_TORRES = 3; // Maximo de torres
		String[] t1 = {
				"/Sprites/Buttons/Compra_Torre-t.png",
				"/Sprites/Buttons/Compra_Torre-v.png",
				"/Sprites/Buttons/Compra_Torre-s.png",
		};
		listaTorres = new ArrayList<Imagem>();
		for (int x = 0; x < MAX_TORRES; x++) {
			listaTorres.add(new Imagem(Renderer.WIDTH / 2 - (100 * MAX_TORRES) + (x + 1) * (MAX_TORRES * 50), Renderer.HEIGHT - 50, 50));
			loadImage(t1[x], 2, listaTorres.get(x), 50, 50);
		}
	}

	// Reseta jogo
	private void reset() {
		hud.reset();
	}
	
	// Atualiza informações dos monstros
	private void monstroUpdate() {
		for (Monstro m : monstros) {
			if(m.getTipo() == Mapa.DESTRUIDOR) {
				m.update(torres, finder);
			} else {
				m.update();
			}
			if (m.isScreenOut() || m.isDead()) {
				monstros.remove(m);
			}
		}
	}

	// Atualiza informações das torres
	private void torreUpdate() {
		for (Torre t : torres) {
			t.update(torres, monstros);
			if(!placingTorre) {
				if (t.getBounds().contains(mouseX, mouseY)) {
					if(!t.getMouseOver())
						Collections.swap(torres, torres.indexOf(t), torres.size()-1);
					
					t.setMouseOver(true);
				} else {
					t.setMouseOver(false);
				}
			}
			
			if(t.isDead()) {
				mapa.setMapa(t.getX() / 50, t.getY() / 50, 0);
				torres.remove(t);
			}
		}
	}

	// Atualiza informaçoes
	public void update() {
		if (!pausado) {
			wave.update(monstros, torres, finder, hud);
			hud.update();
			torreUpdate();
			monstroUpdate();
		}
	}
	
	// Desenha na tela
	public void draw(Graphics2D g) {
		// Mapa
		mapa.draw(g);
		
		if (selecionado >= 0) {
			g.setColor(aux.getColor());
			g.draw(aux.getImagem());
			g.fill(aux.getImagem());
		}


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
		for (Imagem t : listaTorres) {
			switch(listaTorres.indexOf(t)) {
				case 0:
					if(hud.getRecursos() - 15 >= 0) {
						t.setFrame(0);
					}else {
						t.setFrame(1);
					}
					break;
				case 1:
					if(hud.getRecursos() - 20 >= 0) {
						t.setFrame(0);
					}else {
						t.setFrame(1);
					}
					break;
				case 2:
					if(hud.getRecursos() - 50 >= 0) {
						t.setFrame(0);
					}else {
						t.setFrame(1);
					}
					break;
			}
			g.drawImage(t.getImage(), t.getColisao().getBounds().x, t.getColisao().getBounds().y, null);
		}
		
		if(monstros.size() == 0 && !wave.getHardcore() && !wave.getSpawnar()) {
			// GO
			g.drawImage(goBotao.getImage(), goBotao.getColisao().getBounds().x, goBotao.getColisao().getBounds().y, null);
		}
		
		// Caso jogo esteja pausado
		if (pausado) {
			g.drawImage(pauseBg.getImage(), 0, 0, null);
			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.PLAIN, 200));
			g.drawString("Pausado", (int) (Renderer.WIDTH / 2 - g.getFontMetrics().getStringBounds("Pausado", g).getWidth() / 2), Renderer.HEIGHT / 3);
			
			g.drawImage(homeBotao.getImage(), homeBotao.getColisao().getBounds().x, homeBotao.getColisao().getBounds().y, null);
		}

		// Desenha botoes
		// Velocidade
		g.drawImage(speedBotao.getImage(), speedBotao.getColisao().getBounds().x, speedBotao.getColisao().getBounds().y, null);
		// Pause
		g.drawImage(pauseBotao.getImage(), pauseBotao.getColisao().getBounds().x, pauseBotao.getColisao().getBounds().y, null);
	}

	/** Listeners Overrides */
	public void mouseDragged(MouseEvent e) {
		if (placingTorre) {
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
				if (t.getMouseOver()) {
					if (e.getButton() == MouseEvent.BUTTON2) {
						t.upgrade();
					}
					if (e.getButton() == MouseEvent.BUTTON3) {
						mapa.deleteTorre(x, y, finder, torres, monstros, t);
					}
				}
			}

			if(monstros.size() == 0 && !wave.getSpawnar() && goBotao.contains(e.getPoint())) {
				wave.setTempoAtual(wave.getTempoEspera());
			}
			
			if (pauseBotao.contains(e.getPoint())) {
				pauseBotao.setFrame(1);
				pausado = true;
			}
		} else {
			if (pauseBotao.contains(e.getPoint())) {
				pauseBotao.setFrame(0);
				pausado = false;
			}
			
			if(homeBotao.contains(e.getPoint())) {
				reset();
				gsm.setState(GameStateManager.MENU);
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
		if (!pausado) {
			for (Imagem t : listaTorres) {
				if (t.contains(e.getPoint())) {
					selecionadoX = e.getX();
					selecionadoY = e.getY();

					selecionado = listaTorres.indexOf(t);
					placingTorre = true;
					switch (selecionado) {
						case 0:
							aux = new TorreTerrestre();
							break;
						case 1:
							aux = new TorreVoador();
							break;
						case 2:
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

		if (placingTorre) {
			mapa.placeTorre(aux.getTipo(), x, y, finder, torres, monstros);
			selecionado = -1;
			aux = null;
			placingTorre = false;
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
}
