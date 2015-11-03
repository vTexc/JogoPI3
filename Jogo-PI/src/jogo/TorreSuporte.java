package jogo;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class TorreSuporte extends Torre {
	private double suporteValue;
	private double slowValue;

	public TorreSuporte(int x, int y) {
		super(6, x, y, 50, 125, Color.magenta);
		this.suporteValue = 0.3;
		this.slowValue = 0.3;
	}

	public TorreSuporte(double x, double y) {
		super(6, x, y, 50, Color.magenta);
	}

	public TorreSuporte() {
		super(6, Color.magenta);
	}

	public double getSlowValue() {
		return slowValue;
	}

	public void calculateRange(ArrayList<Monstro> monstros, ArrayList<Torre> torres) {
		for (Monstro m : monstros) {
			int dx = (int) ((m.getPosicaoX() + m.getWidth()/2) - (this.getX() + this.getWidth() / 2));
			int dy = (int) ((m.getPosicaoY() + m.getHeight()/2) - (this.getY() + this.getHeight() / 2));

			if (Math.sqrt((dx * dx) + (dy * dy)) <= this.getRangeAtual()) {
				m.addSlowQntd();
				if(this.slowValue > m.getSlowValue())
					m.setSlowValue(this.slowValue);
			}
		}
		for (Torre t : torres) {
			if (t.getTipo() != 6) {
				int dx = (int) ((t.getImagem().getX() + t.getImagem().getWidth()/2) - (this.getX() + this.getWidth() / 2));
				int dy = (int) ((t.getImagem().getY() + t.getImagem().getHeight()/2) - (this.getY() + this.getHeight() / 2));

				if (Math.sqrt((dx * dx) + (dy * dy)) <= this.getRangeAtual()) {
					t.addSuporteQntd();
					if(this.suporteValue > t.getSuporteValue())
						t.setSuporteValue(this.suporteValue);
				}
			}
		}
	}
	
	public synchronized boolean upgrade() {
		if(getUpgrade() < 6 && (HUD.getInstancia().getRecursos() - this.getUpgradeCusto()) >= 0 ) {
			this.suporteValue += 0.02;
			this.slowValue += 0.02;
			this.setRange(this.getRange() + (this.getRangeBase() / 5));
			this.setVendaCusto(this.getUpgradeCusto());
			this.addUpgrade();
			HUD.getInstancia().subRecursos(this.getUpgradeCusto());
			this.setUpgradeCusto(this.getCusto() + this.getCusto()*(this.getUpgrade()+1));
			return true;
		}
		return false;
	}
	
	public void update(ArrayList<Torre> torres, ArrayList<Monstro> monstros) {
		this.calculateRange(monstros, torres);
		this.setRangeAtual(this.getRange());
		this.setDanoAtual(this.getDano());
	}
}
