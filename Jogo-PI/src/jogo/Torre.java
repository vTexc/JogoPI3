package jogo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;

import funcional.Mouse;

public abstract class Torre extends JComponent implements Mouse {
	// Hp da torre
	private int vida, vidaMax;
	// Status da torre
	private int rangeBase;
	private int range;
	private int rangeAtual;
	private int upgrade;
	
	private int vendaCusto;
	private int custo;
	private int upgradeCusto;
	
	private int tipo;

	// Imagem
	private Rectangle imagem;
	private Color color;
	
	// Construtor
	public Torre(int tipo, int x, int y, int custo, int range, Color cor) {
		this.tipo = tipo;
		this.setImagem(new Rectangle(x, y, 50, 50));
		this.color = cor;
		this.rangeBase = range;
		this.range = range;
		this.rangeAtual = range;
		this.custo = custo;
		this.vendaCusto = custo;
		this.upgrade = 0;
		this.upgradeCusto = custo + custo * (upgrade+1);
		setBounds(imagem);
	}
	
	public Torre(int tipo, double x, double y, int custo, Color cor) {
		this.tipo = tipo;
		this.color = cor;
		this.custo = custo;
		this.setImagem(new Rectangle((int) x, (int) y, 50, 50));
		setBounds(imagem);
	}
	
	public Torre(int tipo, Color cor) {
		this.tipo = tipo;
		this.setImagem(new Rectangle(50, 50));
		setBounds(imagem);
		this.color = cor;
	}
	
	public int getTipo() {
		return tipo;
	}
	
	public int getUpgradeCusto() {
		return upgradeCusto;
	}
	
	public int getVendaCusto() {
		return vendaCusto;
	}
	
	// Retorna cor (Temporario)
	public Color getColor() {
		return color;
	}
	// Retorna vida atual da torre
	public int getVida() {
		return vida;
	}
	// altera vida atual da torre
	public void setVida(int vida) {
		this.vida = vida;
	}
	// Retorna imagem da torre
	public Rectangle getImagem() {
		return imagem;
	}
	// Altera imagem da torre
	public void setImagem(Rectangle imagem) {
		this.imagem = imagem;
	}
	
	// Retorna o dano da torre
	public int getDanoAtual() {
		return 0;
	}
	
	public void setDanoAtual(int danoAtual) {}
	
	// Retorna rage da torre
	public int getRangeAtual() {
		return rangeAtual;
	}

	public int getRangeBase() {
		return rangeBase;
	}
	
	// Altera range da torre
	public void setRangeAtual(int range) {
		this.rangeAtual = range;
	}
	
	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getDano() {
		return 0;
	}

	public void setDano(int dano) {}
	
	public boolean isUpgradable() {
		if(upgrade < 6)
			return true;
		else
			return false;
	}
	
	public double getAtqTime() {
		return 0;
	}
	
	public void setAtqTime(double atqTime) {}
	
	public double getMaxAtqTime() {
		return 0;
	}
	
	public void setMaxAtqTime(double maxAtqTime) {}
	
	public int getCusto() {
		return custo;
	}

	public void setUpgradeCusto(int upgradeCusto) {
		this.upgradeCusto = upgradeCusto;
	}
	
	public void setVendaCusto(int upgradeCusto) {
		this.vendaCusto = upgradeCusto;
	}
	
	public double getSuporteValue() {
		return 0;
	}
	
	public void setSuporteValue(double x) {}
	
	public void addSuporteQntd() {}
	
	public int getSuporteQntd() {
		return 0;
	}
	
	public void setSuporteQntd(int suporteQntd) {}
	
	public int getUpgrade() {
		return upgrade;
	}
	
	public void addUpgrade() {
		upgrade++;
	}
	
	public boolean isMouseOver(int x, int y) {
		if(getBounds().contains(x, y)) {
			return true;
		}
		return false;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(getColor());
		g.fillRect(getImagem().x +5, getImagem().y+5, getImagem().width-10, getImagem().height-10);
	
			g.setColor(new Color(0.5f, 0.5f, 0.5f, 0.2f));
			g.fillOval(getX() - getRangeAtual() + getWidth() / 2, getY() - getRangeAtual() + getHeight() / 2, getRangeAtual() * 2, getRangeAtual() * 2);
			if(isUpgradable()) {
				g.setColor(Color.black);
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				g.drawString("Upgrade" , getX() - 5, getY() - 30);
				g.drawString(String.valueOf(getUpgradeCusto()), getX() + 10, getY());
			}
			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.drawString(String.valueOf(getVendaCusto()), getX() + 13, getY() + 80);
			g.drawString("Venda", getX(), getY() + 100);

		g.setFont(new Font("Arial", Font.PLAIN, 50));
		g.drawString(String.valueOf(getUpgrade()), getX() + 10, getY() + 45);
	}
	
	public abstract boolean upgrade();
	public abstract void update(ArrayList<Torre> torres, ArrayList<Monstro> monstros);	
	public abstract void calculateRange(ArrayList<Monstro> monstros, ArrayList<Torre> torres);
}
