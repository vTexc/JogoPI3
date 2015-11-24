package jogo;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

import javax.imageio.*;

import GameStates.*;
import funcional.*;

public class Tiro {
	// Posição atual do tiro
	private int x;
	private int y;

	// Posição final do tiro
	private int xf;
	private int yf;

	// Distancias para calculo
	private int dist; // Raio da torre
	private double distF; // Distancia da torre até o monstro

	// Tempo em tela
	private double time;

	// Monstro alvo
	private Monstro target;

	// Dano do tiro
	private int dano;

	// Imagem
	private BufferedImage imagem;
	private AffineTransform at;

	// Construtor
	public Tiro(int x, int y, int xf, int yf, int dist, int dano, Monstro target) {
		this.x = x;
		this.y = y;
		this.xf = xf;
		this.yf = yf;
		this.target = target;
		this.dano = dano;
		this.time = 0;
		this.dist = dist;
		this.distF = Math.sqrt(((xf - x) * (xf - x)) + ((yf - y) * (yf - y)));

		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Torres/Tiro.png"));
			imagem = spritesheet.getSubimage(0, 0, 9, 12);

		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public boolean posicaoFinal() {
		time += Renderer.deltaTime * PlayState.gameSpeed;
		if (time > (distF / dist) / 5) {
			time = 0;
			return true;
		}
		return false;
	}

	// Atualiza posição do tiro
	public void move() {
		// Diferença dos pontos
		int dx = (int) ((xf + 5) - (x + 5));
		int dy = (int) ((yf + 5) - (y + 5));
		// Angulo entre os pontos
		double angulo = Math.atan2(dy, dx);
		// Altera posiçãoatual em relação ao angulo
		this.x += ((dist * 5) * (Math.cos(angulo) * Renderer.deltaTime)) * PlayState.gameSpeed;
		this.y += ((dist * 5) * (Math.sin(angulo) * Renderer.deltaTime)) * PlayState.gameSpeed;

		// Atualiza transformador da imagem
		at = AffineTransform.getTranslateInstance(x, y);
		at.rotate(angulo, 4, 6);
	}

	// Dsenha tiro
	public void draw(Graphics2D g) {
		g.drawImage(imagem, at, null);
	}
}
