package jogo;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import AEstrela.PathFinder;
import funcional.Animation;

public class MonstroTerrestre extends Monstro {
	// colisao
	private BufferedImage[][] sprites;

	public MonstroTerrestre() {
		super(Mapa.OUTROS, 3, 5);
		
		// Carrega colisao
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

		animation = new Animation[4];
		for(int x = 0; x < animation.length; x++) {
			animation[x] = new Animation();
			animation[x].setFrames(sprites[x]);
			animation[x].setDelay(100);
		}
	}
}
