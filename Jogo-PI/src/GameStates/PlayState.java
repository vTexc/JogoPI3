/**
 * Tela do jogo
 * 
 */
package GameStates;

import funcional.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.plaf.basic.BasicTabbedPaneUI.MouseHandler;

import AEstrela.*;
import jogo.*;

public class PlayState extends GameState {
	// Informaçoes do mapa
	private Mapa mapa;

	// Objetos do jogo
	private ArrayList<Torre> torres;
	private ArrayList<Monstro> monstros;

	// Informaçao de wave, recursos, etc
	private HUD hud;

	// Caminho a ser percorrido
	private PathFinder finder;
	private Path pathT;
	private Path pathV;

	// Musica de fundo
	private Audio bgMusic;

	// Consturtor
	public PlayState(GameStateManager gsm) {
		this.gsm = gsm;
		init();
		monstros.add(new MonstroTerrestre());
		monstros.get(0).atualizarCaminho(finder);
	}

	// Inicializador
	public void init() {
		mapa = new Mapa(); // Cria mapa
		torres = new ArrayList<Torre>(); // Cria a lista para torres
		monstros = new ArrayList<Monstro>(); // Cria lista para monstros

		hud = HUD.getInstancia(); // Cria a hud

		// bgMusic = new Audio("/Audio/JogoBG.mp3");
		// bgMusic.play();

		// Define a base para a estrela
		// AStarPathFinder(Mapa , Total de Tiles, Mover Diagonal)
		finder = new AStarPathFinder(mapa, 13 * 19, false);

		pathT = finder.findPath(2, 0, 6, 18, 6);
		pathV = finder.findPath(3, 0, 6, 18, 6);
	}

	// Atualiza informaçoes
	public void update() {
		hud.update();

		ArrayList<Monstro> aux = (ArrayList<Monstro>) monstros.clone();

		for (Monstro m : aux) {
			m.update();
			if (m.isScreenOut() || m.isDead()) {
				aux.remove(m);
			}
		}
		monstros = aux;
	}

	// Desenha na tela
	public void draw(Graphics2D g) {
		// Background (Temporario)
		g.setColor(Color.black);
		g.fillRect(0, 0, 950, 650);
		// Mapa
		mapa.draw(g);
		// Desenha caminho (Debug)
		for (int x = 0; x < mapa.getWidthInTiles(); x++) {
			for (int y = 0; y < mapa.getHeightInTiles(); y++) {
				if (pathT != null) {
					if (pathT.contains(x, y)) {
						g.setColor(Color.blue);
						g.fillRect((x * 50) + 35/2, (y * 50) + 35/2, 15, 15);
					}
				}
				if (pathV != null) {
					if (pathV.contains(x, y)) {
						g.setColor(Color.red);
						g.fillRect((x * 50) + 40/2, (y * 50) + 40/2, 10, 10);
					}
				}
			}
		}
		// Desenha torres
		for (Torre t : torres) {
			g.setColor(t.getColor());
			g.drawOval(t.getImagem().x, t.getImagem().y, t.getRange(), t.getRange());
			g.fillRect(t.getImagem().x, t.getImagem().y, t.getImagem().width, t.getImagem().height);
		}
		// Desenha Monstros
		for (Monstro m : monstros) {
			g.setColor(Color.gray);
			g.fillRect((int) (m.getPosicaoX()), (int) (m.getPosicaoY()), (int) m.getImagem().getWidth(), (int) m.getImagem().getHeight());
		}

		// Desenha HuD
		hud.draw(g);
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
		// System.out.println("Arastou");
	}

	public void mouseMoved(MouseEvent e) {
		// System.out.println(e.getX() + " " + e.getY());
	}

	public void mouseClicked(MouseEvent e) {
		int x = e.getX() / 50 * 50;
		int y = e.getY() / 50 * 50;
		ArrayList<Torre> aux = (ArrayList<Torre>) torres.clone();

		for (Torre t : aux) {
			if (t.getBounds().contains(e.getX(), e.getY())) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					mapa.setMapa(x / 50, y / 50, 0);
					torres.remove(torres.indexOf(t));
					for (Monstro m : monstros) {
						m.atualizarCaminho(finder);
					}
				}
			}
		}

		if (e.getButton() == MouseEvent.BUTTON1 && !mapa.placeTorre(x / 50, y / 50)) {
			mapa.setMapa(x / 50, y / 50, 4);
			if (finder.findPath(2, 0, 6, 18, 6) != null) {
				torres.add(new TorreTerrestre(x, y));
				for (Monstro m : monstros) {
					m.atualizarCaminho(finder);
					if (!m.hasCaminho()) {
						mapa.setMapa(x / 50, y / 50, 0);
						torres.remove(torres.size() - 1);
						m.atualizarCaminho(finder);
					}
				}
			} else {
				mapa.setMapa(x / 50, y / 50, 0);
			}
		}

		pathT = finder.findPath(2, 0, 6, 18, 6);
		pathV = finder.findPath(3, 0, 6, 18, 6);
	}

	public void mousePressed(MouseEvent e) {
		int x = e.getX() / 50 * 50;
		int y = e.getY() / 50 * 50;
	}

	public void mouseReleased(MouseEvent e) {
		int x = e.getX() / 50 * 50;
		int y = e.getY() / 50 * 50;
		// System.out.println("Soltou");
	}

}
