package GameStates;

import java.awt.*;
import java.awt.event.*;

import funcional.*;

public class MenuState extends GameState {

	private int currentChoice;

	private Rectangle[] botao;
	private String[] options = { "Start", "Quit",  "Voltar", "Hardcore", "Normal"};

	private boolean start;

	private static final int NUMBOTAO = 3;
	private Font titleFont;

	private Font font;

//	private Audio bgMusic;

	public MenuState(GameStateManager gsm) {

		this.gsm = gsm;
		this.start = false;
		this.currentChoice = -1;
		botao = new Rectangle[NUMBOTAO];

		for (int x = 0; x < botao.length; x++) {
			botao[x] = new Rectangle(950 / 2 - 100, 650 / 2 + x * 60, 200, 50);
		}

		try {
			titleFont = new Font("Century Gothic", Font.PLAIN, 100);

			font = new Font("Arial", Font.PLAIN, 40);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// bgMusic = new Audio("/Audio/MenuBG.mp3");
		// bgMusic.play();
	}

	public void init() {
	}

	public void update() {
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Renderer.WIDTH, Renderer.HEIGHT);
		// draw title
		g.setColor(Color.WHITE);
		g.setFont(titleFont);
		int x = 950 /2 - titleFont.getSize();
		int y = 0;
		for(String line : ("ToWar\nDefense").split("\n"))
			g.drawString(line, x, y += g.getFontMetrics().getHeight());

		// draw menu options
		g.setFont(font);
		if (!start) {
			for (int i = 0; i < botao.length - 1; i++) {
				if (i == currentChoice) {
					g.setColor(Color.WHITE);
				} else {
					g.setColor(Color.RED);
				}
				g.draw(botao[i]);
				g.drawString(options[i], botao[i].x, botao[i].y + font.getSize());
			}
		} else {
			for (int i = 0; i < botao.length; i++) {
				if (i == currentChoice) {
					g.setColor(Color.WHITE);
				} else {
					g.setColor(Color.RED);
				}
				g.draw(botao[i]);
				g.drawString(options[options.length - i - 1], botao[i].x, botao[i].y + font.getSize());
			}
		}

	}

	/** Listeners Overrides */
	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		for (int i = 0; i < botao.length; i++) {
			if (botao[i].contains(e.getPoint())) {
				currentChoice = i;
				return;
			}
		}
		currentChoice = -1;
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			switch (currentChoice) {
			case 0:
				if (!start) {
					start = true;
				} else {
					gsm.setState(GameStateManager.PLAY, false);					
				}
				break;
			case 1:
				if (!start) {
					System.exit(0);
				} else {
					gsm.setState(GameStateManager.PLAY, true);
				}
				break;
			case 2:
				if (!start) {

				} else {
					start = false;
				}
				break;
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