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
	// Vida da torre
	private int vida, vidaMax;
	// Status da torre
	// Raio de ataque
	private int rangeBase; // Base
	private int range; // 
	private int rangeAtual; // Atual
	// Level da torre
	private int upgrade;
	// Custo de venda
	private int vendaCusto;
	// Custo de compra
	private int custo;
	// Custo de upgrade
	private int upgradeCusto;
	// Tipo (Terrestre, Voador, Destruidor)
	private int tipo;
	// Verifica se mouse esta sobre esta torre
	private boolean mouseOver;
	// Imagem
	private Rectangle imagem;
	private Color color;
	
	// Construtor usando todas as informações
	public Torre(int tipo, int x, int y, int custo, int range, Color cor) {
		this.tipo = tipo;
		this.imagem = new Rectangle(x, y, 50, 50);
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
	// Construtor para compra
	public Torre(int tipo, double x, double y, int custo, Color cor) {
		this.tipo = tipo;
		this.color = cor;
		this.custo = custo;
		this.imagem = new Rectangle((int) x, (int) y, 50, 50);
		setBounds(imagem);
	}
	// Construtor para lista de compra
	public Torre(int tipo, Color cor) {
		this.tipo = tipo;
		this.imagem = new Rectangle(50, 50);
		setBounds(imagem);
		this.color = cor;
	}
	// Retorna cor (Temporario)
	public Color getColor() {
		return color;
	}
	// Retorna imagem da torre
	public Rectangle getImagem() {
		return imagem;
	}
	// Retorna tipo da torre
	public int getTipo() {
		return tipo;
	}
	// Retorna custo de compra
	public int getCusto() {
		return custo;
	}
	// Retorna custo de upgrade
	public int getUpgradeCusto() {
		return upgradeCusto;
	}
	// Altera custo de upgrade
	public void setUpgradeCusto(int custo) {
		this.upgradeCusto = custo;
	}
	// Retorna custo de venda
	public int getVendaCusto() {
		return vendaCusto;
	}
	// Altera custo de venda
	public void setVendaCusto(int custo) {
		this.vendaCusto = custo;
	}
	// Retorna range base
	public int getRangeBase() {
		return rangeBase;
	}
	// Retorna range
	public int getRange() {
		return range;
	}
	// Altera range
	public void setRange(int range) {
		this.range = range;
	}
	// Retorna range atual
	public int getRangeAtual() {
		return rangeAtual;
	}
	// Altera range da torre
	public void setRangeAtual(int range) {
		this.rangeAtual = range;
	}
	// Retorna upgrade
	public int getUpgrade() {
		return upgrade;
	}
	// Aumenta upgrade
	public void addUpgrade() {
		upgrade++;
	}
	// Verifica se da para dar upgrade
	public boolean isUpgradable() {
		if(upgrade < 6)
			return true;
		else
			return false;
	}
	// Seta true caso mouse esteja sobre a torre
	public void setMouseOver(boolean x) {
		this.mouseOver = x;
	}
	// Desenha a torre na tela
	public void draw(Graphics2D g) {
		g.setColor(getColor());
		g.fillRect(getImagem().x +5, getImagem().y+5, getImagem().width-10, getImagem().height-10);
		// Desenha range e informações de compra e venda
		// Caso mouse esteja sobre a torre
		if(mouseOver) {
			g.setColor(new Color(0.5f, 0.5f, 0.5f, 0.2f));
			g.fillOval(getX() - rangeAtual + getWidth() / 2, getY() - rangeAtual + getHeight() / 2, rangeAtual * 2, rangeAtual * 2);
			// Se der para dar upgrade, mostrar o valor de upgrade
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
		}
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.PLAIN, 50));
		g.drawString(String.valueOf(upgrade), getX() + 10, getY() + 45);
	}
	// Metodos abstratos
	public abstract int getDanoAtual();
	public abstract double getSuporteValue();
	public abstract void setSuporteValue(double x);
	public abstract void setSuporte(boolean suporte);
	public abstract void calculateRange(ArrayList<Monstro> monstros, ArrayList<Torre> torres);
	public abstract boolean upgrade();
	public abstract void update(ArrayList<Torre> torres, ArrayList<Monstro> monstros);
}
