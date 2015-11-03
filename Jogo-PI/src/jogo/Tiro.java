package jogo;

import java.awt.*;

import javax.swing.*;

import GameStates.PlayState;
import funcional.Renderer;

public class Tiro extends JComponent {
	private int x;
	private int y;

	private int xf;
	private int yf;

	private Rectangle posicaoFinal;

	private Monstro target;

	private int dano;

	private Rectangle imagem;

	public Tiro() {

	}

	public Tiro(int x, int y, int xf, int yf, int dano, Monstro target) {
		this.x = x;
		this.y = y;
		this.xf = xf;
		this.yf = yf;
		this.target = target;
		this.dano = dano;
		this.setPosicaoFinal(new Rectangle(xf - 10, yf - 10, 30, 30));

		this.imagem = new Rectangle(x, y, 10, 10);
		setBounds(this.imagem);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getXf() {
		return xf;
	}

	public void setXf(int xf) {
		this.xf = xf;
	}

	public int getYf() {
		return yf;
	}

	public void setYf(int yf) {
		this.yf = yf;
	}

	public Rectangle getImagem() {
		return imagem;
	}

	public void setImagem(Rectangle imagem) {
		this.imagem = imagem;
	}

	public Monstro getTarget() {
		return target;
	}

	public void setTarget(Monstro target) {
		this.target = target;
	}

	public int getDano() {
		return dano;
	}

	public void setDano(int dano) {
		this.dano = dano;
	}

	public Rectangle getPosicaoFinal() {
		return posicaoFinal;
	}

	public void setPosicaoFinal(Rectangle posicaoFinal) {
		this.posicaoFinal = posicaoFinal;
	}

	public void move() {
		setBounds(x, y, imagem.width, imagem.height);

		int dx = (int) ((xf + 5) - (x + 5));
		int dy = (int) ((yf + 5) - (y + 5));

		double angulo = Math.atan2(dy, dx);

		this.x += (500 * (Math.cos(angulo) * Renderer.deltaTime / 1000)) * PlayState.gameSpeed;
		this.y += (500 * (Math.sin(angulo) * Renderer.deltaTime / 1000)) * PlayState.gameSpeed;
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
	}
}
