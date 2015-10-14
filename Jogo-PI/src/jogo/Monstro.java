package jogo;

import java.awt.Image;

public abstract class Monstro {
	private int vida;
    private float posicaoX, posicaoY;
    private Image imagem;
    
    public Monstro() {
    	
    }
    
    public void imprimir() {
    	
    }

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public float getPosicaoX() {
		return posicaoX;
	}

	public void setPosicaoX(float posicaoX) {
		this.posicaoX = posicaoX;
	}

	public float getPosicaoY() {
		return posicaoY;
	}

	public void setPosicaoY(float posicaoY) {
		this.posicaoY = posicaoY;
	}
}
