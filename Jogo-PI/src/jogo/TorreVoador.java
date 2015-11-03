package jogo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class TorreVoador extends Torre {
	private Monstro target;
	private int suporteQntd;
	private double suporteValue;

	private int danoBase;
	private int dano;
	private int danoAtual;

	private double atqTime = 1;
	private double maxAtqTime;

	private ArrayList<Tiro> tiros;
	
	public TorreVoador(int x, int y, double atqTime, int dano) {
		super(5, x, y, 20, 100, Color.yellow);
		this.danoBase = dano;
		this.dano = dano;
		this.danoAtual = dano;
		this.maxAtqTime = atqTime;
		tiros = new ArrayList<Tiro>();
	}

	public TorreVoador(double x, double y) {
		super(5, x, y, 20, Color.yellow);
	}
	
	public TorreVoador() {
		super(5, Color.yellow);
	}
	
	// Retorna o dano da torre
	public int getDanoAtual() {
		return dano;
	}

	public void setDanoAtual(int danoAtual) {
		this.danoAtual = danoAtual;
	}

	public int getDano() {
		return dano;
	}

	public void setDano(int dano) {
		this.dano = dano;
	}
	public double getSuporteValue() {
		return suporteValue;
	}
	
	public void setSuporteValue(double x) {
		suporteValue = x;
	}
	
	public void addSuporteQntd() {
		this.suporteQntd++;
	}
	
	public int getSuporteQntd() {
		return suporteQntd;
	}
	
	public void setSuporteQntd(int suporteQntd) {
		this.suporteQntd = suporteQntd;		
	}

	public double getAtqTime() {
		return atqTime;
	}
	
	public void setAtqTime(double atqTime) {
		this.atqTime = atqTime;
	}
	
	public double getMaxAtqTime() {
		return maxAtqTime;
	}
	
	public void setMaxAtqTime(double maxAtqTime) {
		this.maxAtqTime = maxAtqTime;
	}
	
	public void calculateRange(ArrayList<Monstro> monstros, ArrayList<Torre> torres) {
		for(Monstro m: monstros) {
			if (m.getTipo() == 3) {
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
	
	public synchronized boolean upgrade() {
		if(getUpgrade() < 6 && (HUD.getInstancia().getRecursos() - this.getUpgradeCusto()) >= 0 ) {
			this.setVendaCusto(this.getUpgradeCusto());
			this.addUpgrade();
			HUD.getInstancia().subRecursos(this.getUpgradeCusto());
			this.setUpgradeCusto(this.getCusto() + this.getCusto() * (this.getUpgrade()+1));
			this.setRange(this.getRange() + this.getRangeBase()/5);
			this.dano += this.danoBase / 0.5;
			this.setMaxAtqTime(this.getMaxAtqTime() - 0.1);
			
			return true;
		}
		return false;
	}
	
	private void atacar() {
		if(target == null) {
		}
		else {
			if(getAtqTime() < getMaxAtqTime()) {
				setAtqTime(getAtqTime()+0.02);
			} else {
				tiros.add(new Tiro(getX() + 25, getY() + 25,(int) target.getPosicaoX() + target.getWidth()/2,(int) target.getPosicaoY() + target.getHeight()/2, danoAtual, target));
				setAtqTime(0);
			}
		}
	}

	private void tirosUpdate() {
		for(Tiro t: tiros) {
			t.move();
			if(t.getPosicaoFinal().contains(t.getBounds())) {
				tiros.remove(t);
				t.getTarget().subVida(t.getDano());
			}
		}
	}
	
	public void update(ArrayList<Torre> torres, ArrayList<Monstro> monstros) {
		calculateRange(monstros, torres);
		
		if(getSuporteQntd() != 0) {
			this.setRangeAtual((int) (this.getRange() + this.getRange() * suporteValue));
			this.setDanoAtual((int) (this.getDano() + this.getDano() * suporteValue));
		} else {
			this.setRangeAtual(this.getRange());
			this.setDanoAtual(this.getDano());
		}
		this.suporteQntd = 0;
		
		atacar();
		tirosUpdate();
	}

	public void draw(Graphics2D g) {
		super.draw(g);
		for (Tiro t : tiros) {
			t.draw(g);
		}
	}
}
