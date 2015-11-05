package jogo;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import funcional.Imagem;

@SuppressWarnings("serial")
public class MonstroTerrestre extends Monstro {
	// Imagens para uso
	private BufferedImage[][] sprites;

	public MonstroTerrestre() {
		super(Mapa.OUTROS, 3, 5);
		
		// Carrega imagem
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Inimigos/Terrestre.png"));

			sprites = new BufferedImage[4][4];
			for (int i = 0; i < sprites.length; i++) {
					sprites[0][i] = spritesheet.getSubimage(i * colisao.width, 0, colisao.width, colisao.height);
					sprites[1][i] = spritesheet.getSubimage(i * colisao.width, 1 * colisao.height, colisao.width, colisao.height);
					sprites[2][i] = spritesheet.getSubimage(i * colisao.width, 2 * colisao.height, colisao.width, colisao.height);
					sprites[3][i] = spritesheet.getSubimage(i * colisao.width, 3 * colisao.height, colisao.width, colisao.height);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		imagem = new Imagem[4];
		for(int x = 0; x < imagem.length; x++) {
			imagem[x] = new Imagem();
			imagem[x].setFrames(sprites[x]);
			imagem[x].setDelay(100);
		}
	}
}
