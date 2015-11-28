/**
 * Classe geral dos monstros
 * 
 */
package jogo;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import funcional.*;
import AEstrela.*;
import GameStates.*;

public abstract class Monstro extends Componente implements Cloneable{
	// HP do monstro
	private double vida, vidaMax;
	private Rectangle lifeBar;

	// Recurso ganho ao mata-lo
	private int recurso;

	// Velocidade de movimento
	private double speed;
	private double speedBase;

	// Tipo do monstro (Voador, Terrestre ou Destruidor)
	private int tipo;

	// Imagem do monstro
	protected Imagem imagem;
	private AffineTransform at;

	// Caminho a ser percorrido
	private Caminho caminho;

	// Index atual do caminho a ser percorrido
	private int index;

	// Determina direcao em que monstro esta olhando
	private double direcao;

	// Estados do monstro
	private double slowValue;
	private boolean slow;
	private boolean morto;
	private boolean foraDaTela;

	// Construtor
	public Monstro(int tipo, int vida, double speed, int w, int h, int recurso) {
		super(w, h);
		this.tipo = tipo;
		this.setX(Mapa.getIntance().getEntradaX() * 50 - 25);
		this.setY(Mapa.getIntance().getEntradaY() * 50  + (50 - this.getHeight()));
		this.vidaMax = vida;
		this.vida = vida;
		this.recurso = recurso;
		this.direcao = 0;
		this.speedBase = speed;
		this.speed = speed;
		this.lifeBar = new Rectangle(this.getX(), this.getY() - 10, this.getWidth(), 5);
		setBounds(getX(), getY(), w, h);
	}

	// Subtrai x da vida do monstro
	public void subVida(int x) {
		this.vida -= x;
		if (this.vida <= 0) {
			morto = true;
		}
	}
	
	// Adiciona x na vida do monstro
	public void addVida(double vidaMax) {
		this.vidaMax += this.vidaMax * vidaMax;
		this.vida = this.vidaMax;
	}
	
	public void addVida(int vidaMax) {
		this.vidaMax += vidaMax;
		this.vida = this.vidaMax;
	}
	
	// Retorna VidaMax
	public double getVidaMax() {
		return vidaMax;
	}
	
	// Adiciona speed
	public void addSpeedBase(double speed) {
		this.speedBase += speed;
		this.speed = this.speedBase;
	}
	
	// Retorna speed
	public double getSpeedBase() {
		return speedBase;
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

	// Procura caminho
	public boolean buscarCaminho(PathFinder finder) {
		Caminho caminhoAux = finder.findPath(this.tipo, this.getX() / 50, this.getY() / 50, Mapa.getIntance().getSaidaX(), Mapa.getIntance().getSaidaY());
		return (caminhoAux != null);
	}
	
	// Atualiza o caminho do monstro
	public void atualizarCaminho(PathFinder finder) {
		if (getX() / 50 < Mapa.WIDTH && getX() / 50 >= 0 ) {
			this.index = 1;
			this.caminho = finder.findPath(tipo, getX() / 50, getY() / 50, Mapa.getIntance().getSaidaX(), Mapa.getIntance().getSaidaY());
			if (caminho != null)
				this.caminho.appendStep(Mapa.getIntance().getSaidaX() + 1, Mapa.getIntance().getSaidaY());
		}
	}

	// Verifica estado atual do monstro (Vivo/Morto)
	public boolean isDead() {
		if(morto) {
			HUD.getInstancia().addRecursos(recurso);
		}
		return morto;
	}

	// Verifica se o monstro chegou ao final do caminho
	public boolean isScreenOut() {
		if (this.getX() + this.getWidth() / 2 > Renderer.WIDTH) {
			foraDaTela = true;
			HUD.getInstancia().subVidas();
		}
		return foraDaTela;
	}

	// Atualiza posição atual do monstro
	// Usa o caminho da A*
	private void andar() {
		// Distancias da posicao atual até o próximo passo do caminho
		double distX = (this.caminho.getX(index) * 50) + (25 - (double) this.getWidth() / 2) - this.getXDouble();
		double distY = (this.caminho.getY(index) * 50) + (25 - (double) this.getHeight() / 2) - this.getYDouble();

		// Pega próximo passo caso a soma das distancias seja
		// menor que velocidade do jogo
		if ((Math.abs(distX) + Math.abs(distY)) < PlayState.gameSpeed * 3) {
			this.index++;
		}

		// Atualiza a posição atual do monstro
		direcao = Math.atan2(distY, distX);
		this.addX((50  * this.speed * (Math.cos(direcao) * Renderer.deltaTime)) * PlayState.gameSpeed);
		this.addY((50 * this.speed * (Math.sin(direcao) * Renderer.deltaTime)) * PlayState.gameSpeed);

		// Atualiza transformador da imagem
		at = AffineTransform.getTranslateInstance(getXDouble(), getYDouble());
		at.rotate(direcao, this.getWidth() / 2, this.getHeight() / 2);
	}

	// Atualiza informações do monstro
	public void update() {
		andar();
		if (slow) {
			this.speed = speedBase * slowValue;
		} else {
			this.speed = speedBase;
		}
		slow = false;

		// Atualiza barra de vida
		lifeBar.setLocation((int) getX(), (int) getY() - 7);

		imagem.update();
	}
	
	// Update destruidor
	public void update(ArrayList<Torre> torres, PathFinder finder) {}
	
	// Desenha o monstor na tela
	public void draw(Graphics2D g) {
		// Monstro
		g.drawImage(imagem.getImage(), at, null);
		// Barra de vida
		if (!morto) {
			g.setColor(Color.RED);
			g.draw(lifeBar);
			g.fill(lifeBar);
			g.setColor(Color.green);
			g.fillRect(lifeBar.x, lifeBar.y, (int) (getWidth() * vida / vidaMax), lifeBar.height);
			g.setColor(Color.black);
			g.draw(lifeBar);
		}
	}
	
	/** Cloneable Overrides*/
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
