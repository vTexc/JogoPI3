package jogo;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import funcional.Renderer;

public class TorreSuporte extends Torre {
	// Valor de aumento para torres
	private double suporteValue;
	
	// Valor de slow para monstros
	private double slowValue;

	// Construtor após compra
	public TorreSuporte(int x, int y) {
		super(6, x, y, 50, 125, Color.magenta);
		this.suporteValue = 0.3;
		this.slowValue = 0.3;
	}

	// Construtor durante compra
	public TorreSuporte(double x, double y) {
		super(6, x, y, 50, Color.magenta);
	}

	// Construtor antes da compra
	public TorreSuporte() {
		super(6, Color.magenta);
	}

	/** Overrides */
	// Calcula torres e monstros dentor do range
	// fazendo as alterações necessárias nos mesmos
	public void calculateRange(ArrayList<Monstro> monstros, ArrayList<Torre> torres) {
		for (Monstro m : monstros) {
			if (m.getX() > 0 && m.getX() < Renderer.WIDTH) {
				int dx = (int) ((m.getPosicaoX() + m.getWidth() / 2) - (this.getX() + this.getWidth() / 2));
				int dy = (int) ((m.getPosicaoY() + m.getHeight() / 2) - (this.getY() + this.getHeight() / 2));

				if (Math.sqrt((dx * dx) + (dy * dy)) <= this.getRangeAtual()) {
					m.setSlow(true);
					if (this.slowValue > m.getSlowValue())
						m.setSlowValue(this.slowValue);
				}
			}
		}
		for (Torre t : torres) {
			if (t.getTipo() != 6) {
				int dx = (int) ((t.getImagem().getX() + t.getImagem().getWidth() / 2)
						- (this.getX() + this.getWidth() / 2));
				int dy = (int) ((t.getImagem().getY() + t.getImagem().getHeight() / 2)
						- (this.getY() + this.getHeight() / 2));

				if (Math.sqrt((dx * dx) + (dy * dy)) <= this.getRangeAtual()) {
					t.setSuporte(true);
					if (this.suporteValue > t.getSuporteValue())
						t.setSuporteValue(this.suporteValue);
				}
			}
		}
	}

	// Alterações quando der upgrade
	public synchronized boolean upgrade() {
		if (getUpgrade() < 6 && (HUD.getInstancia().getRecursos() - this.getUpgradeCusto()) >= 0) {
			this.suporteValue += 0.02;
			this.slowValue += 0.05;
			this.setRange(this.getRange() + (this.getRangeBase() / 5));
			this.setVendaCusto(this.getUpgradeCusto());
			this.addUpgrade();
			HUD.getInstancia().subRecursos(this.getUpgradeCusto());
			this.setUpgradeCusto(this.getCusto() + this.getCusto() * (this.getUpgrade() + 1));
			return true;
		}
		return false;
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
