/**
 * Classe geral dos monstros
 * 
 */
package jogo;

import java.awt.*;

import javax.swing.JComponent;

import funcional.Janela;
import funcional.Renderer;
import AEstrela.*;

public abstract class Monstro extends JComponent {
	// HP do monstro
	private int vida, vidaMax;
	private int recurso;
	// Posiçao e espaços na matriz
	private double posicaoX, posicaoY;
	private double speed;
	// Imagem do monstro
	private Rectangle imagem;
	// Define prioridade no movimento
	private int mover;
	// Caminho a ser percorrido
	private Path caminho;
	// Index atual do caminho a ser percorrido
	private int index;
	// Determina direcao em que monstro esta olhando
	private int direcao;
	// Predefine direçoes
	private static final int CIMA = 0;
	private static final int BAIXO = 1;
	private static final int ESQUERDA = 2;
	private static final int DIREITA = 3;
	// Verifica status do monstro
	private boolean slow;
	private boolean morto;
	private boolean foraDaTela;
	
	//Construtor
	public Monstro(int x, int vida, int recurso) {
		this.imagem = new Rectangle(new Dimension(10, 20));
		this.posicaoX = (int) (-49) + (25 - getImagem().getWidth() / 2);
		this.posicaoY = (int) (650 / 13 * 6) + (25 - getImagem().getHeight() / 2);
		this.vida = vida;
		this.recurso = recurso;
		this.mover = x;
		this.direcao = DIREITA;
		this.speed = 1;
		setBounds(imagem);
	}
	// Retorna vida atual do monstro
	public int getVida() {
		return vida;
	}
	//Subtrai x da vida do monstro
	public void subVida(int x) {
		this.vida -= x;
		if(this.vida <= 0) {
			morto = true;
		}
	}
	//Retorna posicao x (Largura) atual do monstro 
	public double getPosicaoX() {
		return posicaoX;
	}
	//Altera posicao x (Largura) atual do monstro 
	public void setPosicaoX(int posicaoX) {
		this.posicaoX = posicaoX;
	}
	//Retorna posicao y (Altura) atual do monstro 
	public double getPosicaoY() {
		return posicaoY;
	}
	//Altera posicao y (Altura) atual do monstro 
	public void setPosicaoY(int posicaoY) {
		this.posicaoY = posicaoY;
	}
	// Retorna o tipo do monstro
	public int getMover() {
		return mover;
	}
	// Verifica se tem caminho ateh o final para o mosntro
	public boolean hasCaminho() {
		return (caminho != null);
	}
	// Atualiza o caminho do monstro
	public void atualizarCaminho(PathFinder finder) {
		if (posicaoX / 50 < 18) {
			this.index = 1;
			this.caminho = finder.findPath(mover, (int) posicaoX / 50, (int) posicaoY / 50, 18, 6);
			if(caminho != null)
				this.caminho.appendStep(19, 6);
		}
	}
	// Retorna imagem do monstro
	public Rectangle getImagem() {
		return imagem;
	}
	// Verifica estado atual do monstro (Vivo/Morto)
	public boolean isDead() {
		if(morto) {
			HUD.getInstancia().addRecursos(this.recurso);
		}
		return morto;
	}
	// Verifica se o monstro chegou ao final do caminho
	public boolean isScreenOut() {
		if(this.posicaoX > 950) {
			foraDaTela = true;
			HUD.getInstancia().subVidas();
		}
		return foraDaTela;
	}
	// Atualiza informações do monstro
	public void update(PathFinder finder) {
		this.andar();
		if (!slow) {

		} else {

		}
		this.atualizarCaminho(finder);
	}
	// Atualiza posicao atual do monstro
	// Usa o caminho como referencia
	private void andar() {
		// Distancias da posicao atual até o próximo tile
		double distX = (this.caminho.getX(index) * 50) + (25 - getImagem().getWidth() / 2) - this.posicaoX;
		double distY = (this.caminho.getY(index) * 50) + (25 - getImagem().getHeight() / 2) - this.posicaoY;

		// Caso a distancia seje menor que 1, pega proximo tile da lista
		if ((Math.abs(distX) + Math.abs(distY)) < 1) {
			this.index++;
		}
		
		// Calcula a direcao atual
		double angulo = Math.atan2(distY, distX);
		this.posicaoX += 50 * (Math.cos(angulo) * Renderer.deltaTime/1000) * this.speed;
		this.posicaoY += 50 * (Math.sin(angulo) * Renderer.deltaTime/1000) * this.speed;
	}
}
