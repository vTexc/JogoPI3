package jogo;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import funcional.Mouse;

public abstract class Torre implements Mouse {
	private int vida, vidaMax;
	private int posicaoX, posicaoY;
	private boolean destruida;
	private Rectangle imagem;
	private int arrayIndex;
	private boolean over;
	
	public Torre() {
		
	}
	
	public int getArrayIndex() {
		return arrayIndex;
	}

	public void setArrayIndex(int arrayIndex) {
		this.arrayIndex = arrayIndex;
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

	public int getPosicaoX() {
		return posicaoX;
	}

	public void setPosicaoX(int posicaoX) {
		this.posicaoX = posicaoX;
	}

	public int getPosicaoY() {
		return posicaoY;
	}

	public void setPosicaoY(int posicaoY) {
		this.posicaoY = posicaoY;
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
}
