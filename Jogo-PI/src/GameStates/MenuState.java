package GameStates;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import funcional.Audio;

public class MenuState extends GameState {
	
	private int currentChoice = 0;

	private Rectangle[] botao;
	private static final int NUMBOTAO = 4;
	private Font titleFont;
	
	private Font font;
	
	private Audio bgMusic;
	
	public MenuState(GameStateManager gsm) {
		
		this.gsm = gsm;
		
		botao = new Rectangle[NUMBOTAO];
		
		for(int x = 0; x < botao.length; x++) {
			botao[x] = new Rectangle(950/2 , 650/2 + x*60, 100,50);
		}
		
		try {
			titleFont = new Font(
					"Century Gothic",
					Font.PLAIN,
					100);
			
			font = new Font("Arial", Font.PLAIN, 40);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
//		bgMusic = new Audio("/Audio/MenuBG.mp3");
//		bgMusic.play();
	}
	
	public void init() {}
	
	public void update() {}
	
	public void draw(Graphics2D g) {
		
		// draw title
		g.setColor(Color.WHITE);
		g.setFont(titleFont);
		g.drawString("Troia", 950/2 - titleFont.getSize(), titleFont.getSize());
		
		// draw menu options
		g.setFont(font);
		for(int i = 0; i < botao.length; i++) {
			if(i == currentChoice) {
				g.setColor(Color.WHITE);
			}
			else {
				g.setColor(Color.RED);
			}
			g.draw(botao[i]);
		}
		
	}
	
	private void select() {
		if(currentChoice == 0) {
//			bgMusic.stop();
			gsm.setState(GameStateManager.PLAY);
		}
		if(currentChoice == 1) {
			// options
		}

		if(currentChoice == 2) {
			// credit
		}
		if(currentChoice == 3) {
//			bgMusic.stop();
			System.exit(0);
		}
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			select();
		}
		if(k == KeyEvent.VK_UP) {
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = botao.length - 1;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if(currentChoice == botao.length) {
				currentChoice = 0;
			}
		}
	}
	public void keyReleased(int k) {}

	@Override
	public void mouseClicked(int k) {
		if(k == MouseEvent.BUTTON1) {
			gsm.setState(GameStateManager.PLAY);
			
		}
		if(k == MouseEvent.BUTTON2) {
			
		}
		if(k == MouseEvent.BUTTON3) {
			
		}
	}
	
}