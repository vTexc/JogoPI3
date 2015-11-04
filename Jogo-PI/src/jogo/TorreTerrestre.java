package jogo;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;

import GameStates.PlayState;
import funcional.Renderer;

public class TorreTerrestre extends Torre {
	// Alvo atual
	private Monstro target;
	
	// Informações de suporte
	private boolean suporte;
	private double suporteValue;
	
	// Informações de dano da torre
	private int danoBase;
	private int dano;
	private int danoAtual;
	
	// Tempo de ataque
	private double atqTime = 1;
	private double maxAtqTime;
	
	// Lista dos tiros desta torre
	private ArrayList<Tiro> tiros;

	// Construtor após compra
	public TorreTerrestre(int x, int y, double atqTime, int dano) {
		super(4, x, y, 15, 75, Color.green);
		this.danoBase = dano;
		this.dano = dano;
		this.danoAtual = dano;
		this.maxAtqTime = atqTime;
		tiros = new ArrayList<Tiro>();
	}

	// Construtor durante compra
	public TorreTerrestre(double x, double y) {
		super(4, x, y, 15, Color.green);
	}

	// Construtor antes da compra
	public TorreTerrestre() {
		super(4, Color.green);
	}

	// Ataca
	private void atacar() {
		if (target == null) {
		} else {
			if (atqTime < maxAtqTime) {
				atqTime += 0.02 * PlayState.gameSpeed;
			} else {
				tiros.add(new Tiro(getX() + 25, getY() + 25, (int) target.getPosicaoX() + target.getWidth() / 2,
						(int) target.getPosicaoY() + target.getHeight() / 2, danoAtual, target));
				atqTime = 0;
			}
		}
	}

	// Atualiza informação dos tiros
	private void tirosUpdate() {
		for (Tiro t : tiros) {
			t.move();
			if (t.posicoaFinal()) {
				t.getTarget().subVida(t.getDano());
				tiros.remove(t);
			}
		}
	}

	// Desenha torre na tela
	public void draw(Graphics2D g) {
		super.draw(g);
		for (Tiro t : tiros) {
			t.draw(g);
		}
	}

	/** Overrides */
	// Retorna o dano atual da torre
	public int getDanoAtual() {
		return dano;
	}

	// Retorna valor se suporte atual
	public double getSuporteValue() {
		return suporteValue;
	}

	// Altera vvalor de suporte atual
	public void setSuporteValue(double x) {
		suporteValue = x;
	}

	// Altera se esta sendo suportado ou não
	public void setSuporte(boolean suporte) {
		this.suporte = suporte;
	}

	// Calcula monstros em área de ataque
	// Retorna para o mais próximo do final
	public void calculateRange(ArrayList<Monstro> monstros, ArrayList<Torre> torres) {
		for (Monstro m : monstros) {
			if (m.getTipo() == 2 && m.getX() > 0 && m.getX() < Renderer.WIDTH) {
				int dx = (int) ((m.getPosicaoX() + m.getWidth()) - (this.getX() + this.getWidth() / 2));
				int dy = (int) ((m.getPosicaoY() + m.getHeight()) - (this.getY() + this.getHeight() / 2));

				if (Math.sqrt((dx * dx) + (dy * dy)) <= this.getRangeAtual()) {
					this.target = m;
					return;
				}
			}
		}
		this.target = null;
		return;
	}

	// Alterações quando der upgrade
	public synchronized boolean upgrade() {
		if (getUpgrade() < 6 && (HUD.getInstancia().getRecursos() - this.getUpgradeCusto()) >= 0) {
			this.setVendaCusto(this.getUpgradeCusto());
			this.addUpgrade();
			HUD.getInstancia().subRecursos(this.getUpgradeCusto());
			this.setUpgradeCusto(this.getCusto() + this.getCusto() * (this.getUpgrade() + 1));
			this.setRange(this.getRange() + this.getRangeBase() / 5);
			this.dano += this.danoBase / 0.5;
			this.maxAtqTime -= 0.1;

			return true;
		}
		return false;
	}

	// Atualiza informação da torre
	public void update(ArrayList<Torre> torres, ArrayList<Monstro> monstros) {
		atacar();
		calculateRange(monstros, torres);
		tirosUpdate();

		if (suporte) {
			this.setRangeAtual((int) (this.getRange() + this.getRange() * suporteValue));
			this.danoAtual = (int) (this.dano + this.dano * suporteValue);
		} else {
			this.setRangeAtual(this.getRange());
			this.danoAtual = this.dano;
		}
		this.suporte = false;
	}
}
