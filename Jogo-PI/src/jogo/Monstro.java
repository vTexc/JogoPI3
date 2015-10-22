package jogo;

import java.awt.*;

public abstract class Monstro {
	private int vida, vidaMax;
    private float posicaoX, posicaoY;
    private boolean morto;
    private Rectangle imagem;
    
    public Monstro() {
    	imagem = new Rectangle(new Dimension(10, 20));
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
