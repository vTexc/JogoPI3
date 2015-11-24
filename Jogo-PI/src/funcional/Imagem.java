package funcional;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public class Imagem {
	// Imagem carregada, separada em frames
	private BufferedImage[] frames;
	private int currentFrame;

	// Caixa de colisao
	private Shape colisao;

	// Tempo de mudança dos frames
	private long startTime;
	private double delay;

	// Verifica se ja rodou uma vez
	private boolean playedOnce;

	// Construtor para colisão circular
	public Imagem(int x, int y, int raio) {
		colisao = new Ellipse2D.Float(x, y, raio, raio);
	}

	// Construtor para colisão retangular
	public Imagem(int x, int y, int w, int h) {
		this();
		colisao = new Rectangle(x, y, w, h);
	}

	// Construtor sem colisão
	public Imagem() {
		playedOnce = false;
	}

	// Retorna caixa de colisão
	public Shape getColisao() {
		return colisao;
	}

	// Retorna true se o ponto esta na caixa
	public boolean contains(Point p) {
		return (colisao.contains(p));
	}

	// Seta os frames
	public void setFrames(BufferedImage[] frames) {
		this.frames = frames.clone();
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}

	// Seta o delay dos frames
	public void setDelay(double deltaTime) {
		delay = deltaTime;
	}

	// Retorna o delay dos frames
	public double getDelay() {
		return delay;
	}

	// Seta frame atual
	public void setFrame(int i) {
		currentFrame = i;
	}

	// Retorna frame atual
	public int getFrame() {
		return currentFrame;
	}

	// Retorna a imagem do frmae atual
	public BufferedImage getImage() {
		return frames[currentFrame];
	}

	// Verifica se ja passou por todos os frames pelo menos uma vez
	public boolean hasPlayedOnce() {
		return playedOnce;
	}

	// Atualiza informações
	public void update() {

		if (delay == -1)
			return;

		long elapsed = (System.nanoTime() - startTime) / 1000000;
		if (elapsed > delay) {
			currentFrame++;
			startTime = System.nanoTime();
		}
		
		if (currentFrame == frames.length) {
			currentFrame = 0;
			playedOnce = true;
		}

	}
}