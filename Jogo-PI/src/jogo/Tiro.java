package jogo;

import java.awt.*;

import javax.swing.*;

import GameStates.PlayState;
import funcional.Renderer;

public class Tiro extends JComponent {
	// Posição atual do tiro
	private int x;
	private int y;
	// Posição final do tiro
	private int xf;
	private int yf;
	// Retangulo de colisão da posição final
	private Rectangle posicaoFinal;
	// Monstro alvo
	private Monstro target;
	// Dano do tiro
	private int dano;
	// Imagem
	private Rectangle imagem;

	// Construtor
	public Tiro(int x, int y, int xf, int yf, int dano, Monstro target) {
		this.x = x;
		this.y = y;
		this.xf = xf;
		this.yf = yf;
		this.target = target;
		this.dano = dano;
		this.posicaoFinal = new Rectangle(xf - 20, yf - 20, 50, 50);

		this.imagem = new Rectangle(x, y, 10, 10);
		setBounds(this.imagem);
	}

	// Retorna o alvo do tiro
	public Monstro getTarget() {
		return target;
	}

	// Retorna dano do tiro
	public int getDano() {
		return dano;
	}

	// Verifica se o tiro chegou no final
	public boolean posicoaFinal() {
		return (posicaoFinal.getBounds().contains(this.getBounds()));
	}

	// Atualiza posição do tiro
	public void move() {
		// Diferença dos pontos
		int dx = (int) ((xf + 5) - (x + 5));
		int dy = (int) ((yf + 5) - (y + 5));
		// Angulo entre os pontos
		double angulo = Math.atan2(dy, dx);
		// Altera posiçãoatual em relação ao angulo
		this.x += (500 * (Math.cos(angulo) * Renderer.deltaTime / 1000)) * PlayState.gameSpeed;
		this.y += (500 * (Math.sin(angulo) * Renderer.deltaTime / 1000)) * PlayState.gameSpeed;
		// Seta nova caisa de colisão
		setBounds(x, y, imagem.width, imagem.height);
	}

	// Dsenha tiro
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
	}
}
