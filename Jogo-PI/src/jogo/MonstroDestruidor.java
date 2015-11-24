package jogo;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import AEstrela.PathFinder;
import GameStates.PlayState;
import funcional.Imagem;
import funcional.Renderer;

@SuppressWarnings("serial")
public class MonstroDestruidor extends Monstro {
	// Imagens para uso
	private BufferedImage[][] sprites;
	private int tipoTorre;
	private Torre target;

	private Imagem canhao;
	private AffineTransform aft;
	
	private int dano;

	// Tempo de ataque
	private double atqTime = 0;
	private double maxAtqTime = 1;

	public MonstroDestruidor(int tipoTorre, int vidaAdicional, double speedAdicional) {
		super(Mapa.DESTRUIDOR, 15 + vidaAdicional, 0.6 + (0.6 * speedAdicional), 50, 50, 20);
		this.tipoTorre = tipoTorre;
		this.dano = 5;

		// Carrega imagem
		try {
			BufferedImage spritesheet = ImageIO
					.read(getClass().getResourceAsStream("/Sprites/Inimigos/Destruidor.png"));

			sprites = new BufferedImage[2][1];
			for (int i = 0; i < sprites[0].length; i++) {
				sprites[0][i] = spritesheet.getSubimage(i * this.getWidth(), 0, this.getWidth(), this.getHeight());
			}
			
			sprites[1][0] = spritesheet.getSubimage(0, this.getHeight(), this.getWidth(), this.getHeight());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		imagem = new Imagem();
		imagem.setFrames(sprites[0]);
		imagem.setDelay(-1);
		
		canhao = new Imagem();
		canhao.setFrames(sprites[1]);
		canhao.setDelay(-1);
	}

	public int getTipoTorre() {
		return tipoTorre;
	}

	public void update(ArrayList<Torre> torres, PathFinder finder) {
		if (target == null) {
			super.update();
			aft = AffineTransform.getTranslateInstance(this.getX(), this.getY());
			for (Torre t : torres) {
				if (t.getTipo() == tipoTorre) {
					int dx = (int) ((t.getX() + t.getWidth() / 2) - (this.getX() + this.getWidth() / 2));
					int dy = (int) ((t.getY() + t.getHeight() / 2) - (this.getY() + this.getHeight() / 2));

					// Angulo entre os pontos
					double angulo = Math.atan2(dy, dx);
					
					if (Math.sqrt((dx * dx) + (dy * dy)) <= 75) {
						this.target = t;
						
						// Atualiza transformador da imagem
						aft.rotate(angulo, this.getWidth() / 2, this.getHeight() / 2);
						
						return;
					}
				}
			}
		} else {
			atacar(finder);
		}
	}

	// Ataca
	private void atacar(PathFinder finder) {
		if (atqTime < maxAtqTime) {
			atqTime += Renderer.deltaTime * PlayState.gameSpeed;
		} else {
			target.subVida(this.dano);
			if(target.isDead()) {
				Mapa.getIntance().setMapa(target.getX() / 50, target.getY() / 50, 0);
				target = null;
				this.atualizarCaminho(finder);
			}
			atqTime = 0;
		}
	}
	
	// Desenha
	public void draw(Graphics2D g) {
		super.draw(g);
		// Canhao
		g.drawImage(canhao.getImage(), aft, null);
	}
}
