package jogo;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.*;

import javax.imageio.ImageIO;

import GameStates.*;
import funcional.*;

public class TorreVoador extends Torre {
	// Alvo atual
	private Monstro target;

	// Imagem torre
	private static BufferedImage[][] sprites;
	private static Imagem cano;
	private AffineTransform at;

	// Angulo para imagem
	double angulo;

	// Informações de suporte
	private boolean suporte;
	private double suporteValue;

	// Informações de dano da torre
	private int dano;
	private int danoAtual;

	// Tempo de ataque
	private double atqTime = 1;
	private double maxAtqTime;

	// Lista dos tiros desta torre
	private ArrayList<Tiro> tiros;

	// Construtor após compra
	public TorreVoador(int x, int y, double atqTime, int dano) {
		super(Mapa.TORRE_V, x, y, 20, 100, 10);
		this.dano = dano;
		this.danoAtual = dano;
		this.maxAtqTime = atqTime;
		tiros = new ArrayList<Tiro>();

		if (sprites == null || cano == null) {
			try {
				BufferedImage spritesheet = ImageIO
						.read(getClass().getResourceAsStream("/Sprites/Torres/Torre_Voador.png"));

				sprites = new BufferedImage[2][1];
				sprites[0][0] = spritesheet.getSubimage(0, 0, this.getWidth(), this.getHeight());

				for (int i = 0; i < sprites[1].length; i++) {
					sprites[1][i] = spritesheet.getSubimage(i * this.getWidth(), this.getHeight(), this.getWidth(),
							this.getHeight());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			cano = new Imagem();
			cano.setFrames(sprites[1]);
			cano.setDelay(-1);
		}

		imagem = new Imagem();
		imagem.setFrames(sprites[0]);
		imagem.setDelay(-1);
	}

	// COnstrutor durante compra
	public TorreVoador(double x, double y) {
		super(Mapa.TORRE_V, (int) x, (int) y, 20);
	}

	// Construtor antes da compra
	public TorreVoador() {
		super(Mapa.TORRE_V, 20);
	}

	// Ataca
	private void atacar() {
		if (target == null) {
		} else {
			if (atqTime < maxAtqTime) {
				atqTime += (Renderer.deltaTime + Renderer.deltaTime / 2) * PlayState.gameSpeed;
			} else {
				tiros.add(new Tiro(getX() + 25, getY() + 25, (int) target.getX() + target.getWidth() / 2,
						(int) target.getY() + target.getHeight() / 2, this.getRangeAtual(), danoAtual, target));
				atqTime = 0;
			}
		}
	}

	// Atualiza informação dos tiros
	private void tirosUpdate() {
		for (Tiro t : tiros) {
			t.move();
			if (t.posicaoFinal()) {
				tiros.remove(t);
				t.getTarget().subVida(t.getDano());
			}
		}
	}

	// Desenha torre na tela
	public void draw(Graphics2D g) {
		super.draw(g);
		g.drawImage(cano.getImage(), at, null);
		for (Tiro t : tiros) {
			t.draw(g);
		}
	}

	/** Overrides */
	// Retorna o dano atual da torre
	public int getDanoAtual() {
		return dano;
	}

	// Retorna valor se suporte atual
	public double getSuporteValue() {
		return suporteValue;
	}

	// Altera vvalor de suporte atual
	public void setSuporteValue(double x) {
		suporteValue = x;
	}

	// Altera se esta sendo suportado ou não
	public void setSuporte(boolean suporte) {
		this.suporte = suporte;
	}

	// Calcula monstros em área de ataque
	// Retorna para o mais próximo do final
	public void calculateRange(ArrayList<Monstro> monstros, ArrayList<Torre> torres) {
		at = AffineTransform.getTranslateInstance(this.getX(), this.getY());
		for (Monstro m : monstros) {
			if (m.getTipo() == Mapa.VOADOR && m.intersects(getAlcance())) {
				int dx = (int) ((m.getX() + m.getWidth()) - (this.getX() + this.getWidth() / 2));
				int dy = (int) ((m.getY() + m.getHeight()) - (this.getY() + this.getHeight() / 2));

				this.target = m;

				// Angulo entre os pontos
				angulo = Math.atan2(dy, dx);

				return;
			}
		}
		this.target = null;
		return;
	}

	// Atualiza informação da torre
	public void update(ArrayList<Torre> torres, ArrayList<Monstro> monstros) {
		atacar();
		calculateRange(monstros, torres);
		tirosUpdate();

		// Atualiza transformador da imagem
		at.rotate(angulo, this.getWidth() / 2, this.getHeight() / 2);

		if (suporte) {
			this.setRangeAtual((int) (this.getRange() + this.getRange() * suporteValue));
			this.danoAtual = (int) (this.dano + this.dano * suporteValue);
		} else {
			this.setRangeAtual(this.getRange());
			this.danoAtual = this.dano;
		}
		
		setAlcance();
		this.suporte = false;
	}
}
