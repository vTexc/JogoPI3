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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.plaf.basic.BasicTabbedPaneUI.MouseHandler;

import AEstrela.*;
import jogo.*;

public class PlayState extends GameState {
	// Informaçoes do mapa
	private Mapa mapa;
	private boolean hardcore;
	
	//
	private GameStateManager gsm;
	
	// Objetos do jogo
	private ArrayList<Torre> torres;
	private ArrayList<Monstro> monstros;
	
	// Lista de compra das torres
	private ArrayList<Torre> listaTorres;
	
	// Informaçao de wave, recursos, etc
	private HUD hud;
	private Wave wave;
	
	private boolean placingTorre;
	private int selecionado;
	private Torre aux;
	private int selecionadoX;
	private int selecionadoY;
	
	private int mouseX;
	private int mouseY;
	// Caminho a ser percorrido
	private PathFinder finder;
	private Path pathT;
	private Path pathV;

	private Rectangle speedBotao;
	public static int gameSpeed;
	
	// Musica de fundo
	private Audio bgMusic;

	//Debugs
	private boolean debug = false;
	
	// Consturtor
	public PlayState(GameStateManager gsm, boolean hc) {
		this.gsm = gsm;
		this.hardcore = hc;
		this.gameSpeed = 1;
		this.speedBotao = new Rectangle(0, Renderer.HEIGHT - 50, 50, 50);
		init();
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

		pathT = finder.findPath(2, 0, 6, 18, 6);
		pathV = finder.findPath(3, 0, 6, 18, 6);
		
		this.selecionado = 0;	
		
		int MAX_TORRES = 3;
		listaTorres = new ArrayList<Torre>();
		listaTorres.add(new TorreTerrestre(Renderer.WIDTH/2 - (100 * MAX_TORRES) + (listaTorres.size()+1) * (MAX_TORRES * 50), Renderer.HEIGHT - 50));
		listaTorres.add(new TorreVoador(Renderer.WIDTH/2 - (100 * MAX_TORRES) + (listaTorres.size()+1) * (MAX_TORRES * 50), Renderer.HEIGHT - 50));
		listaTorres.add(new TorreSuporte(Renderer.WIDTH/2 - (100 * MAX_TORRES) + (listaTorres.size()+1) * (MAX_TORRES * 50), Renderer.HEIGHT - 50));
	}

	private void monstroUpdate() {
		for (Monstro m : monstros) {
			m.update(finder);
			if (m.isScreenOut() || m.isDead()) {
				monstros.remove(m);
			}
		}
	}
	
	private void torreUpdate() {
		for(Torre t: torres) {
			t.update(torres, monstros);
		}
	}
	
	// Atualiza informaçoes
	public void update() {
		wave.update(monstros, finder, hud);
		hud.update();
		torreUpdate();
		monstroUpdate();
		
	}

	// Desenha na tela
	public void draw(Graphics2D g) {
		// Background (Temporario)
		g.setColor(new Color(255, 211, 155));
		g.fillRect(0, 0, 950, 650);

		if(selecionado != 0) {
			g.setColor(aux.getColor());
			g.fillRect((int) aux.getImagem().getX(), (int)aux.getImagem().getY(), (int)aux.getImagem().getWidth(), (int)aux.getImagem().getHeight());
		}
		
		// Mapa
		mapa.draw(g);

		// Desenha HuD
		hud.draw(g);

		switch(gameSpeed) {
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
		g.fillRect(speedBotao.x, speedBotao.y, speedBotao.width, speedBotao.height);
		
		for(Torre t : listaTorres) {
			g.setColor(t.getColor());
			g.fillRect((int) t.getImagem().getX(), (int) t.getImagem().getY(), (int) t.getImagem().getWidth(), (int) t.getImagem().getHeight());
			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.PLAIN, 30));
			g.drawString(String.valueOf(t.getCusto()), t.getX() + 7, t.getY() + 37);
		}
		
		if (debug) {
			// Desenha caminho (Debug)
			for (int x = 0; x < mapa.getWidthInTiles(); x++) {
				for (int y = 0; y < mapa.getHeightInTiles(); y++) {
					if (pathT != null) {
						if (pathT.contains(x, y)) {
							g.setColor(Color.blue);
							g.fillRect((x * 50) + 35 / 2, (y * 50) + 35 / 2, 15, 15);
						}
					}
					if (pathV != null) {
						if (pathV.contains(x, y)) {
							g.setColor(Color.red);
							g.fillRect((x * 50) + 40 / 2, (y * 50) + 40 / 2, 10, 10);
						}
					}
				}
			}
		}
		// Desenha torres
		for (Torre t : torres) {
			t.draw(g);
		}
		// Desenha Monstros
		for (Monstro m : monstros) {
			m.draw(g);
		}
	}

	@Override
	public void keyPressed(int k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub

	}

	public void mouseDragged(MouseEvent e) {
		if(selecionado != 0) {
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
		
		ArrayList<Torre> aux = (ArrayList<Torre>) torres.clone();

		for (Torre t : aux) {
			if (t.getBounds().contains(e.getX(), e.getY())) {
				if (e.getButton() == MouseEvent.BUTTON2) {
					t.upgrade();
				}
				if (e.getButton() == MouseEvent.BUTTON3) {
					mapa.deleteTorre(x, y, finder, torres, monstros, t);

					if(debug) {
						pathT = finder.findPath(2, 0, 6, 18, 6);
						pathV = finder.findPath(3, 0, 6, 18, 6);
					}
				}
			}
		}
		
		if(speedBotao.getBounds().contains(e.getPoint())) {
			gameSpeed += 2;
			if(gameSpeed > 5)
				gameSpeed = 1;
		}
	}

	public void mousePressed(MouseEvent e) {
		int x = e.getX() / 50 * 50;
		int y = e.getY() / 50 * 50;

		for(Torre t : listaTorres) {
			if(t.getBounds().contains(e.getX(), e.getY())) {
				selecionadoX = e.getX();
				selecionadoY = e.getY();
				
				selecionado = t.getTipo();
				switch(t.getTipo()) {
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
				placingTorre = true;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		int x = e.getX() / 50 * 50;
		int y = e.getY() / 50 * 50;
		
		if(selecionado != 0) {
			mapa.placeTorre(selecionado, x, y, finder, torres, monstros);
			selecionado = 0;
			aux = null;
			
			if(debug) {
				pathT = finder.findPath(2, 0, 6, 18, 6);
				pathV = finder.findPath(3, 0, 6, 18, 6);
			}
		}
	}
}
