package jogo;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import funcional.Imagem;

public class MonstroTerrestre extends Monstro {
	// Imagens para uso
	private static BufferedImage[] sprites;

	public MonstroTerrestre(int vidaAdicional, double speedAdicional) {
		super(Mapa.TERRESTRE, 2 + (2 * vidaAdicional), 1 + (1 * speedAdicional), 30, 30, 2);

		// Carrega imagem
		if (sprites == null) {
			try {
				BufferedImage spritesheet = ImageIO
						.read(getClass().getResourceAsStream("/Sprites/Inimigos/Terrestre.png"));

				sprites = new BufferedImage[4];
				for (int i = 0; i < sprites.length; i++) {
					sprites[i] = spritesheet.getSubimage(i * this.getWidth(), 0, this.getWidth(), this.getHeight());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		imagem = new Imagem();
		imagem.setFrames(sprites);
		imagem.setDelay(100);
	}
}
