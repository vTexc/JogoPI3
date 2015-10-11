package global;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Renderer extends JPanel implements Runnable, Mouse {
	private final int B_WIDTH = 800;
	private final int B_HEIGHT = 600;
	private final int INITIAL_X = -40;
	private final int INITIAL_Y = -40;
	private final int DELAY = 15;
	
	private Image imagem;
	private Thread animator;
	private int x, y;
	
	public Renderer() {
		init();
	}
	
	public void init() {
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		setDoubleBuffered(true);
		
		loadImage();
		
		x = INITIAL_X;
		y = INITIAL_Y;
	}
	
	public void loadImage() {
		ImageIcon ii = new ImageIcon("smile.png");
		imagem = ii.getImage();
	}
	
	/** JPanel Overides **/
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawImage(g);
	}

	public void addNotify() {
		super.addNotify();
		
		animator = new Thread(this);
		animator.start();
	}
	
	public void drawImage(Graphics g) {
		g.drawImage(imagem, x, y, this);
		Toolkit.getDefaultToolkit().sync();
	}
	
	public void cycle() {
		x += 1;
		y += 1;
		
		if(y > B_HEIGHT) {
			y = INITIAL_Y;
			x = INITIAL_X;
		}
	}
	
	/** Runnable Overides **/
	public void run() {
		long beforeTime, timeDiff, sleep;
		
		beforeTime = System.currentTimeMillis();
		
		while(true) {
			//Chamar metodos de animção
			cycle();
			repaint();
			//----------------------------------------//
			
			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = DELAY - timeDiff;
			
			if(sleep < 0) {
				sleep = 2;
			}
			
			try {
				Thread.sleep(sleep);
			} catch(InterruptedException e) {
				System.out.println("Interrupted: " + e.getMessage());
			}
			
			beforeTime = System.currentTimeMillis();
		}
	}
}
