package jogo;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.*;

import funcional.Mouse;

public abstract class Torre extends JComponent implements Mouse {
	// Hp da torre
	private int vida, vidaMax;
	// Status da torre
	private int dano;
	private double atqSpd;
	private int range;
	private int upgrade;
	// Imagem
	private Rectangle imagem;
	private Color color;
	
	private boolean selecionado;
	// Construtor
	public Torre(int x, int y, int range, Color cor) {
		this.setImagem(new Rectangle(x, y, 50, 50));
		this.color = cor;
		this.range = range;
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
	//Retorna ataque speed da torre
	public double getAtqSpd() {
		return atqSpd;
	}
	// Altera ataque speed da dorre
	public void setAtqSpd(double atqSpd) {
		this.atqSpd = atqSpd;
	}
	// Retorna rage da torre
	public int getRange() {
		return range;
	}
	// Altera range da torre
	public void setRange(int range) {
		this.range = range;
	}	
}
