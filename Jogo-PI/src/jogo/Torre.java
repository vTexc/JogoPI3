package jogo;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.*;

import funcional.Mouse;

public abstract class Torre extends JComponent implements Mouse {
	private int vida, vidaMax;
	private int dano;
	private double atqSpd;
	private int range;
	private boolean destruida;
	private Rectangle imagem;
	private int upgrade;
	private boolean selecionado;
	private Color color;
	
	public Torre(int x, int y, int range, Color cor) {
		this.setImagem(new Rectangle(x, y, 50, 50));
		this.color = cor;
		this.range = range;
		setBounds(imagem);
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public int getVidaMax() {
		return vidaMax;
	}

	public void setVidaMax(int vidaMax) {
		this.vidaMax = vidaMax;
	}

	public boolean isDestruida() {
		return destruida;
	}

	public void setDestruida(boolean destruida) {
		this.destruida = destruida;
	}

	public Rectangle getImagem() {
		return imagem;
	}

	public void setImagem(Rectangle imagem) {
		this.imagem = imagem;
	}
	
	;
	public int getDano() {
		return dano;
	}

	public void setDano(int dano) {
		this.dano = dano;
	}

	public double getAtqSpd() {
		return atqSpd;
	}

	public void setAtqSpd(double atqSpd) {
		this.atqSpd = atqSpd;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}	
}
