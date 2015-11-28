package GameStates;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import funcional.*;

public class MenuState extends GameState {
	// Botao atual
	private int currentChoice;

	// Imagens
	private BufferedImage titulo;
	private BufferedImage bg;
	private Imagem botao[];

	// Verifica se o botao start foi clicado
	private boolean play;

	// Numero total de botoes
	private static final int NUMBOTAO = 5;

	// Musica de fundo
	private Audio bgMusic;

	// Construtor
	public MenuState(GameStateManager gsm) {

		// Inicializa variaveis
		this.gsm = gsm;
		this.play = false;
		this.currentChoice = -1; // Nenhum botao selecionado
		// Inicializa botao
		botao = new Imagem[NUMBOTAO];
		init();
		// Carregae começa musica de fundo
		bgMusic = GameStateManager.bgMusic[GameStateManager.MENU];
		bgMusic.loop(-1);
		bgMusic.play();
	}

	// Carrega imagens
	public void init() {
		// Suporte de imagens
		BufferedImage[][] sprites = null;

		try {
			// Carrega imagens de fundo e titulo
			titulo = ImageIO.read(getClass().getResourceAsStream("/Sprites/TituloWar.png"));
			bg = ImageIO.read(getClass().getResourceAsStream("/Sprites/MenuBG.jpg"));

			// Iniciliza matriz de imagens
			sprites = new BufferedImage[5][2];
			
			// Carrega todos as imagens dos botoes
			BufferedImage spritesheet1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/Buttons/Menu/play-menu.png"));
			for (int i = 0; i < 2; i++) {
				sprites[0][i] = spritesheet1.getSubimage(i * 200, 0, 200, 100);
			}
			BufferedImage spritesheet2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/Buttons/Menu/sair-menu.png"));
			for (int i = 0; i < 2; i++) {
				sprites[1][i] = spritesheet2.getSubimage(i * 200, 0, 200, 100);
			}
			BufferedImage spritesheet3 = ImageIO.read(getClass().getResourceAsStream("/Sprites/Buttons/Menu/normal-menu.png"));
			for (int i = 0; i < 2; i++) {
				sprites[2][i] = spritesheet3.getSubimage(i * 200, 0, 200, 100);
			}

			BufferedImage spritesheet4 = ImageIO.read(getClass().getResourceAsStream("/Sprites/Buttons/Menu/hardcore-menu.png"));
			for (int i = 0; i < 2; i++) {
				sprites[3][i] = spritesheet4.getSubimage(i * 200, 0, 200, 100);
			}

			BufferedImage spritesheet5 = ImageIO.read(getClass().getResourceAsStream("/Sprites/Buttons/Menu/voltar-menu.png"));
			for (int i = 0; i < 2; i++) {
				sprites[4][i] = spritesheet5.getSubimage(i * 200, 0, 200, 100);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Coloca as imagens nos botoes
		for (int i = 0, j = 0; i < 5; i++, j++) {
			if (i == 2)
				j = 0;
			botao[i] = new Imagem(100, Renderer.HEIGHT / 2 + j * 100, 200, 100);
			botao[i].setFrames(sprites[i]);
			botao[i].setDelay(-1);
		}
	}

	//
	public void update() {
	}

	// Desenha na tela
	public void draw(Graphics2D g) {
		// Desenha fundo
		g.drawImage(bg, -150, -150, null);
		// Desenha titulo
		g.drawImage(titulo, 0, 0, null);

		// Desenha opções
		if (!play) {
			for (int i = 0; i < 2; i++) {
				if(currentChoice == i)
					botao[i].setFrame(1);
				else
					botao[i].setFrame(0);
				
				g.drawImage(botao[i].getImage(), (int) botao[i].getColisao().getBounds().getX(),
						(int) botao[i].getColisao().getBounds().getY(), null);
			}
		} else {
			for (int i = 2; i < 5; i++) {
				if(currentChoice == i)
					botao[i].setFrame(1);
				else
					botao[i].setFrame(0);
				
				g.drawImage(botao[i].getImage(), (int) botao[i].getColisao().getBounds().getX(),
						(int) botao[i].getColisao().getBounds().getY(), null);
			}
		}

	}

	// Fecha audios
	private void closeAudios() {
		bgMusic.stop();
		bgMusic = null;
	}

	/** Listeners Overrides */
	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		if (!play) {
			for (int i = 0; i < 2; i++) {
				botao[i].setFrame(0);
				if (botao[i].contains(e.getPoint())) {
					currentChoice = i;
					GameStateManager.mouseMusic[0].play();
					return;
				}
			}
		} else {
			for (int i = 2; i < 5; i++) {
				if (botao[i].contains(e.getPoint())) {
					currentChoice = i;
					GameStateManager.mouseMusic[0].play();
					return;
				}
			}
		}
		currentChoice = -1;
	}

	public void mouseClicked(MouseEvent e) {
		for (int i = 0; i < 5; i++) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (currentChoice == i) {
					switch (currentChoice) {
					case 0: // Play
						if (!play)
							play = true;
						break;
					case 1: // Sair
						if (!play) {
							closeAudios();
							System.exit(0);
						}
						break;
					case 2: // Normal
						if (play) {
							closeAudios();
							gsm.setState(GameStateManager.PLAY, false);
						}
						break;
					case 3: // Hardcore
						if (play) {
							closeAudios();
							gsm.setState(GameStateManager.PLAY, true);
						}
						break;
					case 4: // Voltar
						if (play)
							play = false;
						break;
					}
				}
			}
		}
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
}