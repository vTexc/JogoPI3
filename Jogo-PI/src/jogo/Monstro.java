package jogo;

import java.awt.Image;
import java.awt.Rectangle;

public abstract class Monstro {
	private int vida;
    private float posicaoX, posicaoY;
    private Rectangle imagem;
    
    public Monstro() {
    	imagem = new Rectangle();
    	imagem.setBounds(0, 600, 20, 20);
    }
    
	public Rectangle getImagem() {
		return imagem;
	}

	public void setImagem(Rectangle imagem) {
		this.imagem = imagem;
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
