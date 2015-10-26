package jogo;

import java.awt.*;

import javax.swing.JComponent;

import funcional.Janela;
import AEstrela.*;

public abstract class Monstro extends JComponent {
	//HP do monstro
	private int vida, vidaMax;
	//Posiçao e espaços na matriz
    private double posicaoX, posicaoY;
    private int lastX, lastY;
    private double speed;
    //Imagem do monstro
    private Rectangle imagem;
    //Define prioridade no movimento
    private int mover;
    //Caminho a ser percorrido
    private Path caminho;
    //Index atual do caminho a ser percorrido
	private int index ;
	//Determina direcao em que monstro esta olhando
	private int direcao;
	//Predefine direçoes
	private static final int CIMA = 0;
	private static final int BAIXO = 1;
	private static final int ESQUERDA = 2;
	private static final int DIREITA = 3;
	// Verifica se esta sobre efeito
	private boolean slow;
	private boolean screenOut;
	
    public Monstro(int x) {
    	this.imagem = new Rectangle(new Dimension(10, 20));
    	this.posicaoX = (int) (-49);
    	this.posicaoY = (int) (650/13 * 6);
    	this.mover = x;
    	this.direcao = DIREITA;
    	this.speed = 1;
    	setBounds(imagem);
    }
    
	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public void subVida(int x) {
		this.vida -= x;
	}
	
	public double getPosicaoX() {
		return posicaoX;
	}

	public void setPosicaoX(int posicaoX) {
		this.posicaoX = posicaoX;
	}

	public double getPosicaoY() {
		return posicaoY;
	}

	public void setPosicaoY(int posicaoY) {
		this.posicaoY = posicaoY;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setCaminho(Path caminho) {
		this.caminho = caminho;
	}
	
	public int getMover() {
		return mover;
	}

	public void setMover(int mover) {
		this.mover = mover;
	}
	
	public boolean hasCaminho() {
		return (caminho != null);
	}
	
	public void atualizarCaminho(PathFinder finder) {
		if(posicaoX/50 < 18 && posicaoY/50 < 6) {
			this.index = 1;
			this.caminho = finder.findPath(mover, (int) posicaoX/50, (int) posicaoY/50, 18, 6);
			this.caminho.appendStep(19, 6);
		}
	}
	
	public Rectangle getImagem() {
		return imagem;
	}

	public void setImagem(Rectangle imagem) {
		this.imagem = imagem;
	}
	
	public boolean isDead() {
		return (this.vida <= 0);
	}
	public boolean isScreenOut() {
		return (this.posicaoX > 950);
	}
	
	public void update() {
		if(!slow) {
			
		} else {
			
		}
	}
	
	public void andar() {
		// Distancias da posicao atual até o próximo tile
		double distX = (this.caminho.getX(index)*50) - this.posicaoX;
		double distY = (this.caminho.getY(index)*50) - this.posicaoY;
		
		// Caso a distancia seje menor que 1, pega proximo tile da lista
		if ((Math.abs(distX)+Math.abs(distY))<1) {
			this.index++;
		}
		// Calcula a direcao atual
		double angulo = Math.atan2(distY, distX);
		System.out.println(angulo);
		this.posicaoX += this.speed * Math.cos(angulo);
		this.posicaoY += this.speed * Math.sin(angulo);
		this.direcao = (int) (angulo/Math.PI*180-90);
	}
}
