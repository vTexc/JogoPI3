/**
 * Classe principal do jogo, onde fica a imagem a ser impressa na janela
 * 
 * Metodos:
 * 		Construtor - Inicializa a classe
 * 		AddNotify - Inicializa a thread
 * 		Init - Iniciliza o necessario para o funcionamento
 **/
package funcional;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

import javax.swing.*;

import GameStates.GameStateManager;

@SuppressWarnings("serial")
public class Renderer extends JPanel implements Runnable, Mouse {
	// Dimensoes
	public static int WIDTH = 950;
	public static int HEIGHT = 650;
	
	// Thread
	private Thread thread;
	private boolean running;
	
	// Controle de FPS pelo thread
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	public static int FRAMES;
	
	// Gerenciador do estado do jogo
	private GameStateManager gsm;
	
	// Variação do tempo entre frames
	public static double deltaTime;

	// Construtor
	public Renderer() {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT)); // Define o tamanho da
														// tela
		setFocusable(true);
		requestFocus();
		setLayout(null); // Define que a posiçao dos objeto nao eh predefinida
		init();
	}

	// Inicializa Thread
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addMouseListener(this);
			addMouseMotionListener(this);
			thread.start();
		}
	}

	// Cria o formato de imagem para impressao na tela
	private void init() {
		running = true; // Define que o programa esta rodando

		gsm = new GameStateManager(); // Inizializa o gerenciador
	}
	
	/** JPanel Overrides **/
	// Desenha na tela
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.draw((Graphics2D) g);
	}

	/** Runnable Overides **/
	// Inicia a thread
	public void run() {
		FRAMES = FPS;
		double lastFrame = 0.0;
		long start;
		long elapsed;
		long wait;

		// Game Loop
		while (running) {
			update();
			repaint();

			start = System.nanoTime();

			elapsed = System.nanoTime() - start;

			wait = targetTime - elapsed / 1000000;
			deltaTime = wait;
			lastFrame += deltaTime / 1000;
			
			System.out.println(deltaTime);
			if (wait < 0)
				wait = 5;

			try {
				Thread.sleep(wait);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (lastFrame >= 1) {
				FRAMES = (int) (1000 / wait);
				lastFrame = 0.0;
			}
		}
	}

	// Atualiza as informações que irão para tela
	private void update() {
		gsm.update();
	}

	// Desenha as informações na tela
	private void draw(Graphics2D g) {
		gsm.draw(g);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 15));
		g.drawString(String.valueOf(Renderer.FRAMES), 5, this.HEIGHT);
	}

	/** Listeners Overrides **/
	public void mouseDragged(MouseEvent e) {
		gsm.mouseDragged(e);
	}
	
	public void mouseMoved(MouseEvent e) {
		gsm.mouseMoved(e);
	}

	public void mouseClicked(MouseEvent e) {
		gsm.mouseClicked(e);
	}

	public void mousePressed(MouseEvent e) {
		gsm.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		gsm.mouseReleased(e);
	}

	public void mouseEntered(MouseEvent e) {
		gsm.mouseEntered(e);
	}

	public void mouseExited(MouseEvent e) {
		gsm.mouseExited(e);
	}
}
