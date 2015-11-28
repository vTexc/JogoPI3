package jogo;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

import funcional.Componente;
import funcional.Imagem;

public abstract class Torre extends Componente {
	// Vida da torre
	private double vida, vidaMax;

	// Barra de vida
	private Rectangle lifeBar;

	// Colisao do range
	private Ellipse2D alcance;
	
	// Status da torre
	// Raio de ataque
	private int rangeBase; // Base
	private int range; // Auxiliar
	private int rangeAtual; // Atual

	// Custo de venda
	private int vendaCusto;

	// Custo de compra
	private int custo;

	// Custo de upgrade
	private int recoverCost;

	// Tipo (Terrestre, Voador, Destruidor)
	private int tipo;

	// Verifica se mouse esta sobre esta torre
	private boolean mouseOver;

	// Imagens
	protected Imagem imagem;
	private static BufferedImage vendaBotao;
	private static BufferedImage recoverBotao;

	// Construtor usando todas as informações
	public Torre(int tipo, int x, int y, int custo, int range, int vida) {
		super(50, 50);
		this.vida = vida;
		this.vidaMax = vida;
		this.tipo = tipo;
		this.rangeBase = range;
		this.range = range;
		this.rangeAtual = range;
		this.custo = custo;
		this.vendaCusto = (int) (custo * 0.67);
		this.recoverCost = (int) (custo + custo * 0.8);
		setBounds(x, y, this.getWidth(), this.getHeight());
		alcance = new Ellipse2D.Float();
		this.lifeBar = new Rectangle(this.getX(), this.getY() + 40, this.getWidth(), 5);
		
		try {
			vendaBotao = ImageIO.read(getClass().getResourceAsStream("/Sprites/Torres/sell.png"));
			recoverBotao = ImageIO.read(getClass().getResourceAsStream("/Sprites/Torres/recover.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setAlcance();
	}

	// Construtor para compra
	public Torre(int tipo, int x, int y, int custo) {
		super(50, 50);
		this.tipo = tipo;
		this.custo = custo;
		setBounds(x, y, this.getWidth(), this.getHeight());
	}

	// Construtor para lista de compra
	public Torre(int tipo, int custo) {
		super(50, 50);
		this.tipo = tipo;
		this.custo = custo;
	}

	// Subtrai vida
	public void subVida(int x) {
		this.vida -= x;
	}

	// Retorna tipo da torre
	public int getTipo() {
		return tipo;
	}

	// Retorna custo de compra
	public int getCusto() {
		return custo;
	}

	// Retorna custo de recover
	public int getRecoverCost() {
		return recoverCost;
	}

	// Retorna custo de venda
	public int getVendaCusto() {
		return vendaCusto;
	}

	// Retorna range base
	public int getRangeBase() {
		return rangeBase;
	}

	// Retorna range
	public int getRange() {
		return range;
	}

	// Altera range
	public void setRange(int range) {
		this.range = range;
	}

	// Retorna range atual
	public int getRangeAtual() {
		return rangeAtual;
	}

	// Altera range da torre
	public void setRangeAtual(int range) {
		this.rangeAtual = range;
	}

	// Seta true caso mouse esteja sobre a torre
	public void setMouseOver(boolean x) {
		this.mouseOver = x;
	}

	// Retorna o valor do mouseOver
	public boolean getMouseOver() {
		return mouseOver;
	}

	// Retorna se torre esta destruida ou nao
	public boolean isDead() {
		return (vida <= 0);
	}

	// Altera alcance
	public void setAlcance() {
		alcance.setFrame(getX() - rangeAtual + getWidth() / 2, getY() - rangeAtual + getHeight() / 2, rangeAtual * 2, rangeAtual * 2);
	}
	
	// Retorna alcance
	public Ellipse2D getAlcance() {
		return alcance;
	}
	
	// Atualiza informaçoes da torre
	public void recover() {
		if(this.vida != this.vidaMax && HUD.getInstancia().getRecursos() - this.recoverCost >= 0) {
			this.vidaMax++;
			this.vida = this.vidaMax;
			recoverCost += 2;
		}
	}
	
	// Desenha a torre na tela
	public void draw(Graphics2D g) {
		// Desenha torre
		g.drawImage(imagem.getImage(), this.getX(), this.getY(), null);
		// Desenha range e informações de compra e venda
		// Caso mouse esteja sobre a torre
		if (mouseOver) {
			g.setColor(new Color(0.5f, 0.5f, 0.5f, 0.7f));
			g.draw(getAlcance());
			g.setColor(new Color(0.5f, 0.5f, 0.5f, 0.2f));
			g.fill(getAlcance());
			
			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.drawString(String.valueOf(getRecoverCost()), getX() + 13, getY());
			g.drawImage(recoverBotao, getX() + getWidth() / 2 - 15, getY() - 45, null);
			g.drawString(String.valueOf(getVendaCusto()), getX() + 13, getY() + getHeight() + 10);
			g.drawImage(vendaBotao, getX() + getWidth() / 2 - 15, getY() + getHeight() / 2 + 35, null);
		}

		// Barra de vida da torre
		if (vida != vidaMax) {
			g.setColor(Color.RED);
			g.draw(lifeBar);
			g.fill(lifeBar);
			g.setColor(Color.green);
			g.fillRect(lifeBar.x, lifeBar.y, (int) (getWidth() * vida / vidaMax), lifeBar.height);
			g.setColor(Color.black);
			g.draw(lifeBar);
		}
	}

	/** Metodos abstratos */
	public abstract int getDanoAtual();

	public abstract double getSuporteValue();

	public abstract void setSuporteValue(double x);

	public abstract void setSuporte(boolean suporte);

	public abstract void calculateRange(ArrayList<Monstro> monstros, ArrayList<Torre> torres);

	public abstract void update(ArrayList<Torre> torres, ArrayList<Monstro> monstros);
}
