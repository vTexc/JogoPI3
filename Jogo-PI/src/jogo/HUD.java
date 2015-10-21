package jogo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JLabel;

public class HUD {
	private int recursos;
	private int wave;
	private String sRecursos;
	private String sWave;
	private static HUD instancia;
	public JLabel labelWave;
	boolean griding;
	
	private Font font;
	
	private HUD() {
		wave = 1;
		recursos = 0;
		sRecursos = "Recursos : " + recursos;
		sWave = "Wave : " + wave;
		font = new Font("Arial", Font.PLAIN, 15);	
		griding = true;	
	}
	
	public static HUD getInstancia() {
		if(instancia == null) {
			initInstancia();
		}
		return instancia;
	}
	
	public static synchronized void initInstancia() {
		if(instancia == null) {
			instancia = new HUD();
		}
	}
	
	public int getRecursos() {
		return recursos;
	}

	public void setRecursos(int recursos) {
		this.recursos = recursos;
	}
	
	public void addRecursos(int recursos) {
		this.recursos += recursos;
	}
	
	public void subRecursos(int recursos) {
		this.recursos -= recursos;
	}
	
	public void nullRecursos() {
		this.recursos = 0;
	}
	
	public int getWave() {
		return wave;
	}

	public void setWave(int wave) {
		this.wave = wave;
	}
	
	public void addWave() {
		wave++;
	}
	
	public void update() {
		sRecursos = "Recursos : " + recursos;
		sWave = "Wave : " + wave;
	}
	
	public void draw(Graphics2D g) {
		if(griding) {
			g.setColor(Color.WHITE);
			for(int x = 0; x < 950; x += 50) {
				for(int y = 0; y < 650; y += 50) {
					g.drawLine(x, 0, x, 650);
					g.drawLine(0, y, 950, y);
				}
			}
		}
		g.setFont(font);
		g.setColor(Color.RED);
		g.drawString(sRecursos, 5, font.getSize());
		g.drawString(sWave, 950/2, font.getSize());
		
	}
}
