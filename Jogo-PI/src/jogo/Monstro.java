/**
 * Classe geral dos monstros
 * 
 */
package jogo;

import java.awt.*;
import java.util.*;

import javax.swing.JComponent;

import funcional.*;
import AEstrela.*;
import GameStates.*;

public abstract class Monstro extends JComponent {
	// HP do monstro
	private int vida, vidaMax;
	private Rectangle lifeBar;
	// Recurso ganho ao mata-lo
	private int recurso;
	// Posiçao e espaços na mapa
	private double posicaoX, posicaoY;
	// Velocidade de movimento
	private double speed;
	// Tipo do monstro (Voador, Terrestre ou Destruidor)
	private int tipo;
	// Imagem do monstro
	private Rectangle imagem;
	// Caminho a ser percorrido
	private Caminho caminho;
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
	private double slowValue;
	private boolean slow;
	private boolean morto;
	private boolean foraDaTela;

	// Construtor
	public Monstro(int tipo, int vida, int recurso) {
		this.tipo = tipo;
		this.imagem = new Rectangle(new Dimension(10, 20));
		this.posicaoX = (int) (-49) + (25 - imagem.getWidth() / 2);
		this.posicaoY = (int) (650 / 13 * 6) + (25 - imagem.getHeight() / 2);
		this.vidaMax = vida;
		this.vida = vida;
		this.recurso = recurso;
		this.direcao = DIREITA;
		this.speed = 1;
		this.lifeBar = new Rectangle((int) posicaoX, (int) posicaoY - 10, 10, 5);
		setBounds(imagem);
	}

	// Subtrai x da vida do monstro
	public void subVida(int x) {
		this.vida -= x;
		if (this.vida <= 0) {
			morto = true;
		}
	}

	// Retorna posicao x (Largura) atual do monstro
	public double getPosicaoX() {
		return posicaoX;
	}

	// Retorna posicao y (Altura) atual do monstro
	public double getPosicaoY() {
		return posicaoY;
	}

	// Retorna tipo do monstro
	public int getTipo() {
		return tipo;
	}

	// Verifica se tem caminho para o monstro
	public boolean hasCaminho() {
		return (caminho != null);
	}

	// Retorna o slow de movimento atual
	public double getSlowValue() {
		return slowValue;
	}

	// Seta valor de Slow do movimento
	public void setSlowValue(double x) {
		this.slowValue = x;
	}

	// Seta se esta sobre Slow
	public void setSlow(boolean x) {
		this.slow = x;
	}

	// Atualiza o caminho do monstro
	public void atualizarCaminho(PathFinder finder) {
		if (posicaoX / 50 < 18) {
			this.index = 1;
			this.caminho = finder.findPath(tipo, (int) posicaoX / 50, (int) posicaoY / 50, 18, 6);
			if (caminho != null)
				this.caminho.appendStep(19, 6);
		}
	}

	// Verifica estado atual do monstro (Vivo/Morto)
	public boolean isDead() {
		if (morto) {
			HUD.getInstancia().addRecursos(this.recurso);
		}
		return morto;
	}

	// Verifica se o monstro chegou ao final do caminho
	public boolean isScreenOut() {
		if (this.posicaoX > 950) {
			foraDaTela = true;
			HUD.getInstancia().subVidas();
		}
		return foraDaTela;
	}

	// Atualiza posicao atual do monstro
	// Usa o caminho como referencia
	private void andar() {
		setBounds((int) posicaoX, (int) posicaoY, imagem.width, imagem.height);
		// Distancias da posicao atual até o próximo passo do caminho
		double distX = (this.caminho.getX(index) * 50) + (25 - imagem.getWidth() / 2) - this.posicaoX;
		double distY = (this.caminho.getY(index) * 50) + (25 - imagem.getHeight() / 2) - this.posicaoY;

		// Pega próximo passo caso a soma das distancias seja
		// menor que velocidade do jogo
		if ((Math.abs(distX) + Math.abs(distY)) < PlayState.gameSpeed) {
			this.index++;
		}

		// Atualiza a posição atual do monstro
		double angulo = Math.atan2(distY, distX);
		this.posicaoX += (50 * (Math.cos(angulo) * Renderer.deltaTime / 1000) * this.speed) * PlayState.gameSpeed;
		this.posicaoY += (50 * (Math.sin(angulo) * Renderer.deltaTime / 1000) * this.speed) * PlayState.gameSpeed;

		// Define a direção que o monstro esta olhando
		angulo = Math.toDegrees(Math.atan2(distY, distX));

		if (angulo >= -45 && angulo <= 45)
			direcao = DIREITA;
		else if (angulo > 45 && angulo <= 135)
			direcao = BAIXO;
		else if (angulo < -45 && angulo > -135)
			direcao = CIMA;
		else
			direcao = ESQUERDA;
	}

	// Atualiza informações do monstro
	public void update(PathFinder finder) {
		andar();
		if (slow) {
			this.speed = 1 - slowValue;
		} else {
			this.speed = 1;
		}
		slow = false;
	}

	// Desenha o monstor na tela
	public void draw(Graphics2D g) {
		// Monstro
		g.setColor(Color.gray);
		g.fillRect((int) posicaoX, (int) posicaoY, (int) imagem.getWidth(), (int) imagem.getHeight());
		// Barra de vida
		g.setColor(Color.RED);
		g.fillRect((int) posicaoX - 5, (int) posicaoY - 7, 20, lifeBar.height);
		g.setColor(Color.green);
		g.fillRect((int) posicaoX - 5, (int) posicaoY - 7, 20 * vida / vidaMax, lifeBar.height);
	}
}
