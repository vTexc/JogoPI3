package GameStates;

import funcional.Audio;
import funcional.Renderer;

import java.awt.Graphics2D;
import java.util.ArrayList;

import jogo.HUD;
import jogo.Mapa;
import jogo.Monstro;
import jogo.Torre;

public class PlayState extends GameState {
	private Mapa mapa;
	private ArrayList<Torre> torres;
	private ArrayList<Monstro> monstros;

	private HUD hud;
	
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
		
		bgMusic = new Audio("/Audio/JogoBG.mp3");
		bgMusic.play();
	}

	public void update() {
		hud.update();
	}

	public void draw(Graphics2D g) {
		mapa.draw(g);
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
}
