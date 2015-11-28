package jogo;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import funcional.Imagem;
import funcional.Renderer;

public class MonstroVoador extends Monstro {
	// Imagens para uso
	private static BufferedImage[] sprites;

	public MonstroVoador(int vidaAdicional, double speedAdicional) {
		super(Mapa.VOADOR, 3 + (3 * vidaAdicional), 1.4 + (1.4 * speedAdicional), 50, 50, 3);

		// Carrega imagem
		if (sprites == null) {
			try {
				BufferedImage spritesheet = ImageIO
						.read(getClass().getResourceAsStream("/Sprites/Inimigos/Voador.png"));

				sprites = new BufferedImage[3];
				for (int i = 0; i < sprites.length; i++) {
					sprites[i] = spritesheet.getSubimage(i * this.getWidth(), 0, this.getWidth(), this.getHeight());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		imagem = new Imagem();
		imagem.setFrames(sprites);
		imagem.setDelay(Renderer.deltaTime);
	}
}
