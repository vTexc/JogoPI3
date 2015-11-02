package jogo;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;

import funcional.Mouse;

public abstract class Torre extends JComponent implements Mouse {
	// Hp da torre
	private int vida, vidaMax;
	// Status da torre
	private int dano;
	private int range;
	private int upgrade;
	
	private double atqTime = 1;
	private double maxAtqTime;
	
	private int custo;
	// Imagem
	private Rectangle imagem;
	private Color color;
	
	private Monstro target;
	
	private boolean selecionado;
	// Construtor
	public Torre(int x, int y, int custo, int dano, int range, double atqTime, Color cor) {
		this.setImagem(new Rectangle(x, y, 50, 50));
		this.dano = dano;
		this.color = cor;
		this.range = range;
		this.target = null;
		this.maxAtqTime = atqTime;
		this.custo = custo;
		setBounds(imagem);
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
	public int getDano() {
		return dano;
	}
	// Retorna rage da torre
	public int getRange() {
		return range;
	}
	// Altera range da torre
	public void setRange(int range) {
		this.range = range;
	}
	
	public Monstro getTarget() {
		return target;
	}
	
	public void setTarget(Monstro target) {
		this.target = target;
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
	
	public int getCusto() {
		return custo;
	}

	public abstract Monstro calculateRange(ArrayList<Monstro> monstros);
}
