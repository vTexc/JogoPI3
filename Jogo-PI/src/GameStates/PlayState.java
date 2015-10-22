package GameStates;

import funcional.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.plaf.basic.BasicTabbedPaneUI.MouseHandler;

import AEstrela.*;
import jogo.*;

public class PlayState extends GameState {
	private Mapa mapa;
	private ArrayList<Torre> torres;
	private ArrayList<Monstro> monstros;

	private HUD hud;

	private PathFinder finder;
	private Path path;
	
	private Audio bgMusic;
	
	public PlayState(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}

	public void init() {
		mapa = new Mapa();
		torres = new ArrayList<Torre>();
		monstros = new ArrayList<Monstro>();
		
		hud = HUD.getInstancia();
		
//		bgMusic = new Audio("/Audio/JogoBG.mp3");
//		bgMusic.play();
		
		finder = new AStarPathFinder(mapa, 13*19, false);
	}

	public void update() {
		hud.update();
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, 950, 650);
		mapa.draw(g);
		for (int x=0;x<mapa.getWidthInTiles();x++) {
			for (int y=0;y<mapa.getHeightInTiles();y++) {
				if (path != null) {
					if (path.contains(x, y)) {
						g.setColor(Color.blue);
						g.fillRect((x*50)+4, (y*50)+4,42,42);
					}	
				}
			}
		}
		for(Torre t: torres) {
			g.setColor(Color.green);
			g.fillRect(t.getImagem().x, t.getImagem().y, t.getImagem().width, t.getImagem().height);
		}
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

	@Override
	public void mouseClicked(int k, int x, int y) {
		x = x/50 * 50;
		y = y/50 * 50;
//		int z = 
		if(k == MouseEvent.BUTTON1) {
			mapa.setMapa(x/50, y/50, 2);
			torres.add(new TorreTerrestre(x, y, torres.size()-1));
		}
		if(k == MouseEvent.BUTTON3) {
			mapa.setMapa(x/50, y/50, 0);
//			torres.remove(z);
		}
		
		path = finder.findPath(new UnitMover(2), 0, 6, 18, 6);
	}
}
