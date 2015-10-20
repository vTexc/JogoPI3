package global;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import jogo.Mapa;
import jogo.Menu;
import jogo.Monstro;
import jogo.Torre;

public class Renderer extends JPanel implements Runnable, Mouse, ComponentListener {
	private int B_WIDTH = 800;
	private int B_HEIGHT = 600;
	private final int DELAY = 15;
	private HUD hud;
	private Thread animator;
	private Mapa mapa;
	private Menu menu;
	
	public Renderer() {
		init();
	}
	
	public void init() {
		setLayout(null);
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		setDoubleBuffered(true);
		
		hud = HUD.getInstancia();
		hud.setLabelRecursosBounds(0, 0, 200, 20);
		hud.setLabelWaveBounds(B_WIDTH/2, 0, 200, 20);
		add(hud.getLabelRecursos());
		add(hud.getLabelWave());
		
		mapa = new Mapa();
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
		Toolkit.getDefaultToolkit().sync();
	}
	
	/** Runnable Overides **/
	public void run() {
		long beforeTime, timeDiff, sleep;
		
		beforeTime = System.currentTimeMillis();
		
		while(true) {
			//Chamar metodos de anim��o
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
	
	@Override
	public void componentResized(ComponentEvent e) {
		this.B_WIDTH = this.getWidth();
		this.B_HEIGHT = this.getHeight();
		hud.setLabelWaveBounds(B_WIDTH/2, 0, 200, 20);
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
}
