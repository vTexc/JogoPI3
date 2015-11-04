package jogo;

import java.awt.*;
import java.awt.geom.AffineTransform;

import javax.swing.*;

import GameStates.PlayState;
import funcional.Renderer;

public class Tiro extends JComponent {
	// Posi��o atual do tiro
	private int x;
	private int y;
	
	// Posi��o final do tiro
	private int xf;
	private int yf;
	
	// Retangulo de colis�o da posi��o final
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
		this.posicaoFinal = new Rectangle(xf - 5, yf - 5, 10, 10);

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
		return (posicaoFinal.intersects(this.getBounds()));
	}

	// Atualiza posi��o do tiro
	public void move() {
		// Diferen�a dos pontos
		int dx = (int) ((xf + 5) - (x + 5));
		int dy = (int) ((yf + 5) - (y + 5));
		// Angulo entre os pontos
		double angulo = Math.atan2(dy, dx);
		// Altera posi��oatual em rela��o ao angulo
		this.x += (300 * (Math.cos(angulo) * Renderer.deltaTime / 1000)) * PlayState.gameSpeed;
		this.y += (300 * (Math.sin(angulo) * Renderer.deltaTime / 1000)) * PlayState.gameSpeed;
		// Seta nova caisa de colis�o
		
		imagem.setLocation(x, y);
		setBounds(x, y, imagem.width, imagem.height);
		posicaoFinal.setBounds(xf -  (int) Renderer.deltaTime, yf -  (int) Renderer.deltaTime , (int) Renderer.deltaTime  + 2 * PlayState.gameSpeed, (int) Renderer.deltaTime  + 2 * PlayState.gameSpeed);
	}

	// Dsenha tiro
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.draw(imagem);
		g.fill(imagem);
		g.setColor(Color.red);
		g.draw(posicaoFinal);
	}
}
