package jogo;

import java.awt.image.BufferedImage;
import java.util.*;

import javax.imageio.ImageIO;

import funcional.*;

public class TorreSuporte extends Torre {
	// Imagem torre
	private static BufferedImage[] sprites;

	// Valor de aumento para torres
	private double suporteValue;

	// Valor de slow para monstros
	private double slowValue;

	// Construtor após compra
	public TorreSuporte(int x, int y) {
		super(Mapa.TORRE_S, x, y, 50, 125, 5);
		this.suporteValue = 0.3;
		this.slowValue = 0.3;

		if (sprites == null) {
			try {
				BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Torres/Torre_Suporte.png"));

				sprites = new BufferedImage[1];
				for (int i = 0; i < sprites.length; i++) {
					sprites[i] = spritesheet.getSubimage(i * this.getWidth(), 0, this.getWidth(), this.getHeight());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		imagem = new Imagem();
		imagem.setFrames(sprites);
		imagem.setDelay(-1);
	}

	// Construtor durante compra
	public TorreSuporte(double x, double y) {
		super(Mapa.TORRE_S, (int) x, (int) y, 50);
	}

	// Construtor antes da compra
	public TorreSuporte() {
		super(Mapa.TORRE_S, 50);
	}

	/** Overrides */
	// Calcula torres e monstros dentor do range
	// fazendo as alterações necessárias nos mesmos
	public void calculateRange(ArrayList<Monstro> monstros, ArrayList<Torre> torres) {
		for (Monstro m : monstros) {
			if (m.intersects(getAlcance())) {
				m.setSlow(true);
				if (this.slowValue > m.getSlowValue())
					m.setSlowValue(this.slowValue);
			}
		}
		for (Torre t : torres) {
			if (t.getTipo() != 6 && t.intersects(getAlcance())) {
				t.setSuporte(true);
				if (this.suporteValue > t.getSuporteValue())
					t.setSuporteValue(this.suporteValue);
			}
		}
	}

	// Atualiza informação da torre
	public void update(ArrayList<Torre> torres, ArrayList<Monstro> monstros) {
		this.calculateRange(monstros, torres);
		this.setRangeAtual(this.getRange());
	}

	/** Overrides - Desnecessários para esta torre */
	public int getDanoAtual() {
		return 0;
	}

	public void setSuporteValue(double x) {
	}

	public void setSuporte(boolean suporte) {
	}

	public double getSuporteValue() {
		return 0;
	}
}
