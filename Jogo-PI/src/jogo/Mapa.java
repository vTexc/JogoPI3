package jogo;

import global.Jogador;

import javax.swing.JLabel;

public class Mapa {
	private int wave;
	private JLabel labelWave;
	private static Mapa instancia;
	
	private Mapa() {
		wave = 1;
		this.labelWave = new JLabel();
	}
	
	public static Mapa getInstancia() {
		if(instancia == null) {
			initInstancia();
		}
		return instancia;
	}
	
	public static synchronized void initInstancia() {
		if(instancia == null) {
			instancia = new Mapa();
		}
	}
	
	public JLabel getLabelWave() {
		this.labelWave.setText("Wave : " + wave);
		return labelWave;
	}

	public void setLabelWave(JLabel labelWave) {
		this.labelWave = labelWave;
	}
	
	public void setLabelBounds(int x, int y, int w, int h) {
		this.labelWave.setBounds(x, y, w, h);
	}
}
